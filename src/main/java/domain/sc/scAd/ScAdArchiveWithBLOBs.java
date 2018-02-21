package domain.sc.scAd;

import java.io.Serializable;

public class ScAdArchiveWithBLOBs extends ScAdArchive implements Serializable {
    private String adform;

    private String cis;

    private static final long serialVersionUID = 1L;

    public String getAdform() {
        return adform;
    }

    public void setAdform(String adform) {
        this.adform = adform == null ? null : adform.trim();
    }

    public String getCis() {
        return cis;
    }

    public void setCis(String cis) {
        this.cis = cis == null ? null : cis.trim();
    }
}