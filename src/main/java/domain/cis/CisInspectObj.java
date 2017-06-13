package domain.cis;

import domain.cadre.CadreView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CisInspectObj implements Serializable {

    public CadreView getCadre(){

        return CmTag.getCadreById(cadreId);
    }
    public CisInspectorView getChiefInspector(){

        return CmTag.getCisInspector(chiefInspectorId);
    }

    public List<CisInspectorView> getInspectors(){

        return CmTag.getCisInspectors(id);
    }

    private Integer id;

    private Integer year;

    private Integer typeId;

    private Integer seq;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inspectDate;

    private Integer cadreId;

    private Byte inspectorType;

    private String otherInspectorType;

    private Integer chiefInspectorId;

    private Integer talkUserCount;

    private String post;

    private String assignPost;

    private String summary;

    private String logFile;

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

    public Integer getChiefInspectorId() {
        return chiefInspectorId;
    }

    public void setChiefInspectorId(Integer chiefInspectorId) {
        this.chiefInspectorId = chiefInspectorId;
    }

    public Integer getTalkUserCount() {
        return talkUserCount;
    }

    public void setTalkUserCount(Integer talkUserCount) {
        this.talkUserCount = talkUserCount;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getAssignPost() {
        return assignPost;
    }

    public void setAssignPost(String assignPost) {
        this.assignPost = assignPost == null ? null : assignPost.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile == null ? null : logFile.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}