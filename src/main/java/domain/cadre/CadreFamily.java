package domain.cadre;

import java.io.Serializable;
import java.util.Date;

public class CadreFamily implements Serializable {
    private Integer id;

    private Integer cadreId;

    private Byte title;

    private String realname;

    private Date birthday;

    private Boolean withGod;

    private Integer politicalStatus;

    private String unit;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Byte getTitle() {
        return title;
    }

    public void setTitle(Byte title) {
        this.title = title;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getWithGod() {
        return withGod;
    }

    public void setWithGod(Boolean withGod) {
        this.withGod = withGod;
    }

    public Integer getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(Integer politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}