package nl.knaw.dans.datatags.service.core.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import nl.knaw.dans.datatags.service.generated.api.TagMyFileApi;
import nl.knaw.dans.datatags.service.generated.model.Action;
import nl.knaw.dans.datatags.service.generated.model.DataTagSchemaNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.definition.TransitionDefinition;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.ActionState;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.engine.impl.FlowExecutionImplFactory;
import org.springframework.webflow.execution.FlowExecution;
import org.springframework.webflow.execution.FlowExecutionFactory;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-01-28T14:32:29.934+01:00[Europe/Amsterdam]")
@Controller
public class TagMyFileApiController implements TagMyFileApi {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TagMyFileApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return Optional.ofNullable(objectMapper);
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    private MessageSource messageSource;

    @Override
    public ResponseEntity<DataTagSchemaNode> addTag(@ApiParam(value = "todo",required=true) @PathVariable("schema-name") String schemaName
            ,@ApiParam(value = "todo",required=true) @PathVariable("version") String version
            ,@ApiParam(value = "The url of Dataverse/B2Share" ,required=true) @RequestHeader(value="sourceUrl", required=true) String sourceUrl
            ,@NotNull @ApiParam(value = "The source pid of object", required = true) @Valid @RequestParam(value = "sourcePid", required = true) String sourcePid
            ,@NotNull @ApiParam(value = "todo", required = true) @Valid @RequestParam(value = "nodeName", required = true) String nodeName
            ,@NotNull @ApiParam(value = "todo", required = true) @Valid @RequestParam(value = "answer", required = true) String answer
            ,@NotNull @ApiParam(value = "todo", required = true) @Valid @RequestParam(value = "fileId", required = true) List<Long> fileId
            ,@ApiParam(value = "The key to post end result from DataTags service" ) @RequestHeader(value="sourceKey", required=false) String sourceKey
    ) {
        RequestContext requestContext = RequestContextHolder.getRequestContext();
        ServletContext sc = request.getSession().getServletContext();
        ExternalContext ec = new ServletExternalContext(sc, request, null);
        FlowDefinitionRegistry fdr = (FlowDefinitionRegistry) context.getBean("flowRegistry");
        FlowDefinition fd = fdr.getFlowDefinition(schemaName + "/" + version);
        if (fd == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        FlowExecutionFactory factory = new FlowExecutionImplFactory();
        FlowExecution fe = factory.createFlowExecution(fd);
//        fe.start(null, ec);

        StateDefinition sdd = fd.getState(nodeName);
        if (sdd == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ViewState vs = null;
        ActionState as = null;
        TransitionDefinition[] tds;
        if (sdd instanceof ViewState) {
            vs = (ViewState) sdd;
            tds = vs.getTransitions();
        } else if (sdd instanceof ActionState) {
            as = (ActionState) sdd;
            tds = as.getTransitions();
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        DataTagSchemaNode dtsn = new DataTagSchemaNode();
        dtsn.setNodeName(nodeName);
        String prefix = schemaName + "." + version + "." + nodeName + ".";
        dtsn.setTitle(getMessage(prefix + "title", "en"));
        dtsn.setSubTitle(getMessage(prefix + "subtitle", "en"));
        dtsn.setContentText(getMessage(prefix + "contentText", "en"));
        dtsn.setQuestion(getMessage(prefix + "question", "en"));
        List<Action> actions = new ArrayList<>();
        for (TransitionDefinition td : tds) {
            String targetStateId = td.getTargetStateId();
            String tid = td.getId();
            Action action = new Action();
            action.setName(tid);
            action.setLabel(getMessage(prefix + tid, "en"));
            action.setTarget(targetStateId);
            actions.add(action);
        }
        dtsn.setAnswers(actions);

        if (getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            //if (getAcceptHeader().get().contains("application/json")) {
            try {

                return new ResponseEntity<>(getObjectMapper().get().readValue(objectMapper.writeValueAsString(dtsn), DataTagSchemaNode.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
//        } else {
//            log.warn("ObjectMapper or HttpServletRequest not configured in default TagMyFileApi interface so no example is generated");
//        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    private String getMessage(String key, String lang) {
        try {
            return messageSource.getMessage(key, null, new Locale(lang));
        } catch (Exception e) {
            return "";
        }

    }

    private static String sendPOST(String url) throws IOException {

        String result = "";
        HttpPost post = new HttpPost(url);

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"name\":\"mkyong\",");
        json.append("\"notes\":\"hello\"");
        json.append("}");

        // send a JSON data
        post.setEntity(new StringEntity(json.toString()));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = EntityUtils.toString(response.getEntity());
        }

        return result;
    }
}
