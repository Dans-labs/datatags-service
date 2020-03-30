package nl.knaw.dans.datatags.service.core.model;

import java.io.Serializable;

public class QueryParams implements Serializable {
    private String apiKey;
    private String sourceUrl;
    private String persistentId;
    private String version;
    private String expiredDateTime;
    private Long[] fileIds;

    public String getApiKey() {
        return apiKey;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getPersistentId() {
        return persistentId;
    }

    public String getVersion() {
        return version;
    }

    public String getExpiredDateTime() {
        return expiredDateTime;
    }

    public Long[] getFileIds() {
        return fileIds;
    }
}
