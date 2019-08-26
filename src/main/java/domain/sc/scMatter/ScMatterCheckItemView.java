package domain.sc.scMatter;

import domain.sc.scRecord.ScRecordView;
import domain.sc.scRecord.ScRecordViewExample;
import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import persistence.sc.IScMapper;
import persistence.sc.scRecord.ScRecordViewMapper;
import sys.tags.CmTag;
import sys.utils.NumberUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScMatterCheckItemView implements Serializable {

    public SysUserView getRecordUser(){ return CmTag.getUserById(recordUserId);}

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

    private Integer id;

    private Integer checkId;

    private Integer userId;

    private Integer recordId;

    private String recordIds;

    private Integer recordUserId;

    private Date compareDate;

    private String resultType;

    private String selfFile;

    private Byte confirmType;

    private Date confirmDate;

    private String checkReason;

    private String handleType;

    private String checkFile;

    private String owHandleType;

    private Date owHandleDate;

    private String owHandleFile;

    private Date owAffectDate;

    private String remark;

    private Integer year;

    private Boolean isRandom;

    private Date checkDate;

    private Integer num;

    private String code;

    private String realname;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(Integer recordUserId) {
        this.recordUserId = recordUserId;
    }

    public Date getCompareDate() {
        return compareDate;
    }

    public void setCompareDate(Date compareDate) {
        this.compareDate = compareDate;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType == null ? null : resultType.trim();
    }

    public String getSelfFile() {
        return selfFile;
    }

    public void setSelfFile(String selfFile) {
        this.selfFile = selfFile == null ? null : selfFile.trim();
    }

    public Byte getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(Byte confirmType) {
        this.confirmType = confirmType;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String checkReason) {
        this.checkReason = checkReason == null ? null : checkReason.trim();
    }

    public String getHandleType() {
        return handleType;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType == null ? null : handleType.trim();
    }

    public String getCheckFile() {
        return checkFile;
    }

    public void setCheckFile(String checkFile) {
        this.checkFile = checkFile == null ? null : checkFile.trim();
    }

    public String getOwHandleType() {
        return owHandleType;
    }

    public void setOwHandleType(String owHandleType) {
        this.owHandleType = owHandleType == null ? null : owHandleType.trim();
    }

    public Date getOwHandleDate() {
        return owHandleDate;
    }

    public void setOwHandleDate(Date owHandleDate) {
        this.owHandleDate = owHandleDate;
    }

    public String getOwHandleFile() {
        return owHandleFile;
    }

    public void setOwHandleFile(String owHandleFile) {
        this.owHandleFile = owHandleFile == null ? null : owHandleFile.trim();
    }

    public Date getOwAffectDate() {
        return owAffectDate;
    }

    public void setOwAffectDate(Date owAffectDate) {
        this.owAffectDate = owAffectDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getIsRandom() {
        return isRandom;
    }

    public void setIsRandom(Boolean isRandom) {
        this.isRandom = isRandom;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }
}