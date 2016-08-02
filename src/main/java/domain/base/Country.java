package domain.base;

import java.io.Serializable;

public class Country implements Serializable {
    private Integer id;

    private String abbr;

    private String cninfo;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr == null ? null : abbr.trim();
    }

    public String getCninfo() {
        return cninfo;
    }

    public void setCninfo(String cninfo) {
        this.cninfo = cninfo == null ? null : cninfo.trim();
    }
}