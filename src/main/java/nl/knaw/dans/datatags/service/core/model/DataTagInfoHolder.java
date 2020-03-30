package nl.knaw.dans.datatags.service.core.model;

import java.io.Serializable;

public class DataTagInfoHolder implements Serializable {
    private boolean persist;
    private ProvenanceContainer provenanceContainer;
    private QueryParams queryParams;
    private String jsonProvenance;
    private String note;
    private int confirmationResponseCode;
    private String confirmationResponseMessage;

    public String getJsonProvenance() {
        return jsonProvenance;
    }

    public void setJsonProvenance(String jsonProvenance) {
        this.jsonProvenance = jsonProvenance;
    }

    public QueryParams getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(QueryParams queryParams) {
        this.queryParams = queryParams;
    }

    public ProvenanceContainer getProvenanceContainer() {
        return provenanceContainer;
    }

    public void setProvenanceContainer(ProvenanceContainer provenanceContainer) {
        this.provenanceContainer = provenanceContainer;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getConfirmationResponseCode() {
        return confirmationResponseCode;
    }

    public void setConfirmationResponseCode(int confirmationResponseCode) {
        this.confirmationResponseCode = confirmationResponseCode;
    }

    public String getConfirmationResponseMessage() {
        return confirmationResponseMessage;
    }

    public void setConfirmationResponseMessage(String confirmationResponseMessage) {
        this.confirmationResponseMessage = confirmationResponseMessage;
    }

    public boolean isPersist() {
        return persist;
    }

    public void setPersist(boolean persist) {
        this.persist = persist;
    }
}
