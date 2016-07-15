package domain.cadre;

import java.io.Serializable;

public class CadreInfoWithBLOBs extends CadreInfo implements Serializable {
    private String work;

    private String parttime;

    private static final long serialVersionUID = 1L;

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work == null ? null : work.trim();
    }

    public String getParttime() {
        return parttime;
    }

    public void setParttime(String parttime) {
        this.parttime = parttime == null ? null : parttime.trim();
    }
}