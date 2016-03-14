package domain;

import java.io.Serializable;

public class SysConfig implements Serializable {
    private Integer id;

    private String applySelfNote;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplySelfNote() {
        return applySelfNote;
    }

    public void setApplySelfNote(String applySelfNote) {
        this.applySelfNote = applySelfNote == null ? null : applySelfNote.trim();
    }
}