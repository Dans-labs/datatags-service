package nl.knaw.dans.datatags.service.core.model;

import java.io.Serializable;

public class RegisterModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private DataTagInfoHolder dataTagInfoHolder;

    public DataTagInfoHolder getDataTagInfoHolder() {
        return dataTagInfoHolder;
    }

    public void setDataTagInfoHolder(DataTagInfoHolder dataTagInfoHolder) {
        this.dataTagInfoHolder = dataTagInfoHolder;
    }
}