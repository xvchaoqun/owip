package domain.cadre;

import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CadreFamily implements Serializable {
    public String getFamilyTitle(){
        return (title!=null)? CmTag.getMetaType(title).getName():null;
    }
    public String getFamilyPolitical() {
        return (politicalStatus!=null)? CmTag.getMetaType(politicalStatus).getName():null;
    }

    private Integer id;

    private Integer cadreId;

    private Integer title;

    private String realname;

    private Date birthday;

    private Boolean withGod;

    private Integer politicalStatus;

    private String unit;

    private Integer sortOrder;

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

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
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

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}