package nl.knaw.dans.datatags.service.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProvenanceContainer implements Serializable {
    private String schemaName;
    private String schemaVersion;
    private String schemaPid;
    private String dataTagsServiceUrl;
    private String tag;
    private String colorCode;
    private LocalDateTime outcomeTime;//issued time?
    private List<QA> questionAnswerList;

    public ProvenanceContainer() {
    }

    public ProvenanceContainer(String schemaName, String schemaVersion, String schemaPid, String dataTagsServiceUrl, String tag, String colorCode, List<QA> questionAnswerList) {
        this.schemaName = schemaName;
        this.schemaVersion = schemaVersion;
        this.schemaPid = schemaPid;
        this.dataTagsServiceUrl = dataTagsServiceUrl;
        this.tag = tag;
        this.colorCode = colorCode;
        this.questionAnswerList = questionAnswerList;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getSchemaPid() {
        return schemaPid;
    }

    public void setSchemaPid(String schemaPid) {
        this.schemaPid = schemaPid;
    }

    public String getDataTagsServiceUrl() {
        return dataTagsServiceUrl;
    }

    public void setDataTagsServiceUrl(String dataTagsServiceUrl) {
        this.dataTagsServiceUrl = dataTagsServiceUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public List<QA> getQuestionAnswerList() {
        return questionAnswerList;
    }

    public void setQuestionAnswerList(List<QA> questionAnswerList) {
        this.questionAnswerList = questionAnswerList;
    }

    public String getOutcomeTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
}
