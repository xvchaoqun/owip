package domain.cis;

import domain.cadre.Cadre;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CisInspectObj implements Serializable {

    public Cadre getCadre(){

        return CmTag.getCadreById(cadreId);
    }
    public Cadre getChiefCadre(){

        return CmTag.getCadreById(chiefCadreId);
    }

    public List<CisInspectorView> getInspectors(){

        return CmTag.getCisInspectors(id);
    }

    private Integer id;

    private Integer year;

    private Integer typeId;

    private Integer seq;

    private Date inspectDate;

    private Integer cadreId;

    private Byte inspectorType;

    private String otherInspectorType;

    private Integer chiefCadreId;

    private Integer talkUserCount;

    private String summary;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Date getInspectDate() {
        return inspectDate;
    }

    public void setInspectDate(Date inspectDate) {
        this.inspectDate = inspectDate;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Byte getInspectorType() {
        return inspectorType;
    }

    public void setInspectorType(Byte inspectorType) {
        this.inspectorType = inspectorType;
    }

    public String getOtherInspectorType() {
        return otherInspectorType;
    }

    public void setOtherInspectorType(String otherInspectorType) {
        this.otherInspectorType = otherInspectorType == null ? null : otherInspectorType.trim();
    }

    public Integer getChiefCadreId() {
        return chiefCadreId;
    }

    public void setChiefCadreId(Integer chiefCadreId) {
        this.chiefCadreId = chiefCadreId;
    }

    public Integer getTalkUserCount() {
        return talkUserCount;
    }

    public void setTalkUserCount(Integer talkUserCount) {
        this.talkUserCount = talkUserCount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}