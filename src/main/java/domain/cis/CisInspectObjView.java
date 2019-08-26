package domain.cis;

import domain.cadre.CadreView;
import domain.sc.scRecord.ScRecordView;
import domain.sc.scRecord.ScRecordViewExample;
import domain.sys.SysUserView;
import domain.unit.UnitPost;
import org.apache.commons.lang3.StringUtils;
import persistence.sc.IScMapper;
import persistence.sc.scRecord.ScRecordViewMapper;
import persistence.unit.UnitPostMapper;
import sys.helper.CisHelper;
import sys.tags.CmTag;
import sys.utils.DateUtils;
import sys.utils.NumberUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CisInspectObjView implements Serializable {

    public SysUserView getRecordUser(){ return CmTag.getUserById(recordUserId);}

    public UnitPost getUnitPost(){
        if(unitPostId==null) return null;
        return CmTag.getBean(UnitPostMapper.class).selectByPrimaryKey(unitPostId);
    }

    public ScRecordView getScRecord(){

        if(recordId==null) return null;
        IScMapper iScMapper = CmTag.getBean(IScMapper.class);
        if(iScMapper==null) return null;
        return iScMapper.getScRecordView(recordId);
    }

    public List<ScRecordView> getScRecords(){

        if(StringUtils.isBlank(recordIds)) return null;
        ScRecordViewMapper scRecordViewMapper = CmTag.getBean(ScRecordViewMapper.class);
        if(scRecordViewMapper==null) return null;

        ScRecordViewExample example = new ScRecordViewExample();
        example.createCriteria().andIdIn(new ArrayList<>(NumberUtils.toIntSet(recordIds, ",")));
        return scRecordViewMapper.selectByExample(example);
    }

    public String getSn(){
        String type = CmTag.getMetaType(typeId).getName();
        return String.format("%s〔%s%02d〕号", type, DateUtils.formatDate(inspectDate, DateUtils.YYYYMMDD), seq);
    }

    public CadreView getCadre(){

        return CmTag.getCadreById(cadreId);
    }

    public CisInspector getChiefInspector(){

        return CisHelper.getCisInspector(chiefInspectorId);
    }

    public List<CisInspector> getInspectors(){

        return CisHelper.getCisInspectors(id);
    }

    private Integer id;

    private Integer recordId;

    private String recordIds;

    private Integer unitPostId;

    private Integer year;

    private Integer typeId;

    private Integer seq;

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

    private String report;

    private Integer recordUserId;

    private String remark;

    private Integer archiveId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getRecordIds() {
        return recordIds;
    }

    public void setRecordIds(String recordIds) {
        this.recordIds = recordIds == null ? null : recordIds.trim();
    }

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
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

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? null : report.trim();
    }

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Integer archiveId) {
        this.archiveId = archiveId;
    }
}