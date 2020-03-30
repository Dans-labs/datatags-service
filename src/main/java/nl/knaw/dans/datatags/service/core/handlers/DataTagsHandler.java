package nl.knaw.dans.datatags.service.core.handlers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.knaw.dans.datatags.service.core.model.*;
import nl.knaw.dans.datatags.service.utils.DataTagsHelper;
import nl.knaw.dans.datatags.service.utils.QueryParamsValidator;
import nl.knaw.dans.datatags.service.utils.StringUtil;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class DataTagsHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DataTagsHandler.class);
    @Autowired
    Environment env;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private QueryParamsValidator queryParamsValidator;

    public RegisterModel init() {
        return new RegisterModel();
    }
    public void initDataTagInfoHolder(RegisterModel registerModel, RequestContext requestContext, DataTagInfoHolder dti, String dataTagSchemeVersion) {
        ProvenanceContainer pc = initProvenanceContainer( requestContext, dataTagSchemeVersion);
//        Can I control the HTTP headers sent by window.open (cross browser)? NO!
        String encodedEncryptedQ = requestContext.getRequestParameters().get("q");
        if (encodedEncryptedQ != null && !encodedEncryptedQ.isEmpty()) {
            try {
                String encryptedQ = URLDecoder.decode(encodedEncryptedQ, "UTF-8");
                String q = StringUtil.decrypt(encryptedQ, env.getProperty("datatag.password.dataversenl"));
                if (queryParamsValidator.isDataTagValid(q)) {
                    dti.setPersist(true);
                    ObjectMapper mapper = new ObjectMapper();
                    QueryParams queryParams = mapper.readValue(q, QueryParams.class);
                    dti.setQueryParams(queryParams);
                }
            } catch (JsonProcessingException | UnsupportedEncodingException e) {
                LOG.error(e.getMessage());
            }
        } else {
            if (dti.getProvenanceContainer() != null) {
                dti.getProvenanceContainer().getQuestionAnswerList().clear();
            }
        }
        dti.setProvenanceContainer(pc);
        registerModel.setDataTagInfoHolder(dti);
    }

    public void addQA(RegisterModel registerModel, RequestContext requestContext, DataTagInfoHolder dti){
        List<QA> qaList = dti.getProvenanceContainer().getQuestionAnswerList();
        StateDefinition sd = requestContext.getCurrentState();
        String question = getMessage(sd.getId() + ".question", "en");
        Event e = requestContext.getCurrentEvent();
        String answer = getMessage(sd.getId() + "." + e.getId(), "en");
        if (qaList != null) {
            if (e.getId().equals("back")) {
                int listNumber = qaList.size() - 1;
                dti.setNote(qaList.get(listNumber).getNote());
                qaList.remove(listNumber);
            }
            if (!(e.getId().equals("confirm") || e.getId().equals("back"))) {
                String note = dti.getNote();
                if (note!=null)
                    note = note.trim();
                qaList.add(new QA(question, answer, note));
                dti.setNote("");
            }
        }
    }

    private ProvenanceContainer initProvenanceContainer(RequestContext requestContext, String dataTagSchemeVersion) {
        ExternalContext ex = requestContext.getExternalContext();
        String path[] = requestContext.getActiveFlow().getId().split("/");
        List<QA> qaList = new ArrayList<QA>();
        ProvenanceContainer pc = new ProvenanceContainer();
        pc.setSchemaName(path[0].toUpperCase());
        pc.setSchemaVersion(path[1]);
        pc.setSchemaPid(dataTagSchemeVersion);
        pc.setDataTagsServiceUrl(((HttpServletRequest)ex.getNativeRequest()).getRequestURL().toString());
        pc.setQuestionAnswerList(qaList);

        return pc;
    }
    private String getMessage(String key, String lang) {
        try {
            return messageSource.getMessage(key, null, new Locale(lang));
        } catch (Exception e) {
            return "";
        }

    }

    public void determineTag(RegisterModel registerModel, RequestContext requestContext) {
            ProvenanceContainer pc = registerModel.getDataTagInfoHolder().getProvenanceContainer();
            String tagValue = getMessage(requestContext.getCurrentState().getId() + "." + requestContext.getCurrentEvent().getId() + ".value", "en");
            if (pc.getTag() == null || (tagValue.compareTo(pc.getTag()) > 0))
                pc.setTag(tagValue);
//        }
    }
    public void calculateTag(RegisterModel registerModel, String tagPrefix) {
            String tagValue = registerModel.getDataTagInfoHolder().getProvenanceContainer().getTag();
            String tagName = getMessage(tagPrefix + ".name." + tagValue, "en");
            registerModel.getDataTagInfoHolder().getProvenanceContainer().setTag(tagName);
            String tagColorCode = getMessage(tagPrefix + ".colorCode." + tagName.toLowerCase(), "en");
            registerModel.getDataTagInfoHolder().getProvenanceContainer().setColorCode(tagColorCode);
    }
    public void setTag(RegisterModel registerModel, String tag, String colorCode) {
            registerModel.getDataTagInfoHolder().getProvenanceContainer().setTag(tag);
            registerModel.getDataTagInfoHolder().getProvenanceContainer().setColorCode(colorCode);
    }

    public String confirm(RegisterModel registerModel) {
        DataTagInfoHolder dataTagInfoHolder = registerModel.getDataTagInfoHolder();
        if(dataTagInfoHolder.isPersist()) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataTagInfoHolder.getProvenanceContainer());
            LOG.debug(jsonString);
            dataTagInfoHolder.setJsonProvenance(jsonString);

            Long[] fileids = dataTagInfoHolder.getQueryParams().getFileIds();
            String fid = "";
            for (Long fileId : fileids) {
                fid += "id=" + fileId + "&";
            }
            String targetPostMetadataUrl = dataTagInfoHolder.getQueryParams().getSourceUrl() + "/api/files/datatags-json?" + fid + "expiredDateTime=" + dataTagInfoHolder.getQueryParams().getExpiredDateTime() ;
            LOG.debug(targetPostMetadataUrl);
            DataTagsHelper dth = new DataTagsHelper(targetPostMetadataUrl, dataTagInfoHolder.getQueryParams().getApiKey(),jsonString);
            dataTagInfoHolder.setConfirmationResponseCode(dth.getResponseCode());
            dataTagInfoHolder.setConfirmationResponseMessage(dth.getResponseMessage());
            registerModel.getDataTagInfoHolder().setProvenanceContainer(null);
            if(dth.getResponseCode() == Response.SC_OK)
                return "success";
            else
                return "error-" + dth.getResponseCode();

        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage());
        }
        dataTagInfoHolder.setPersist(false);
        }
        return "error";
    }
}
