package domain.cet;

import java.io.Serializable;

public class CetTrainWithBLOBs extends CetTrain implements Serializable {
    private String summary;

    private String evaNote;

    private static final long serialVersionUID = 1L;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getEvaNote() {
        return evaNote;
    }

    public void setEvaNote(String evaNote) {
        this.evaNote = evaNote == null ? null : evaNote.trim();
    }
}