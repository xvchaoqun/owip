package domain.cet;

import domain.sys.SysUserView;
import persistence.cet.CetDiscussMapper;
import service.cet.CetPartySchoolService;
import service.cet.CetPartyService;
import service.cet.CetUnitService;
import sys.constants.CetConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;

public class CetDiscussGroup implements Serializable {

    public SysUserView getHoldUser(){
        return CmTag.getUserById(holdUserId);
    }
    public SysUserView getAdminUser(){
        return CmTag.getUserById(adminUserId);
    }

    public CetPartyView getCetParty(){

        if(unitId==null) return null;

        CetDiscussMapper cetDiscussMapper = CmTag.getBean(CetDiscussMapper.class);
        CetDiscuss cetDiscuss = cetDiscussMapper.selectByPrimaryKey(discussId);
        if(cetDiscuss.getUnitType()== CetConstants.CET_DISCUSS_UNIT_TYPE_PARTY){

            CetPartyService cetPartyService = CmTag.getBean(CetPartyService.class);
            return cetPartyService.getView(unitId);
        }

        return null;
    }

    public CetUnitView getCetUnit(){

        if(unitId==null) return null;

        CetDiscussMapper cetDiscussMapper = CmTag.getBean(CetDiscussMapper.class);
        CetDiscuss cetDiscuss = cetDiscussMapper.selectByPrimaryKey(discussId);
        if(cetDiscuss.getUnitType()== CetConstants.CET_DISCUSS_UNIT_TYPE_UNIT){

            CetUnitService cetUnitService = CmTag.getBean(CetUnitService.class);
            return cetUnitService.getView(unitId);
        }

        return null;
    }

    public CetPartySchoolView getCetPartySchool(){

        if(unitId==null) return null;

        CetDiscussMapper cetDiscussMapper = CmTag.getBean(CetDiscussMapper.class);
        CetDiscuss cetDiscuss = cetDiscussMapper.selectByPrimaryKey(discussId);
        if(cetDiscuss.getUnitType()== CetConstants.CET_DISCUSS_UNIT_TYPE_PARTY_SCHOOL){

            CetPartySchoolService cetPartySchoolService = CmTag.getBean(CetPartySchoolService.class);
            return cetPartySchoolService.getView(unitId);
        }

        return null;
    }

    private Integer id;

    private Integer discussId;

    private String name;

    private Integer holdUserId;

    private String subject;

    private Boolean subjectCanModify;

    private Date discussTime;

    private String discussAddress;

    private Integer unitId;

    private Integer adminUserId;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDiscussId() {
        return discussId;
    }

    public void setDiscussId(Integer discussId) {
        this.discussId = discussId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getHoldUserId() {
        return holdUserId;
    }

    public void setHoldUserId(Integer holdUserId) {
        this.holdUserId = holdUserId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public Boolean getSubjectCanModify() {
        return subjectCanModify;
    }

    public void setSubjectCanModify(Boolean subjectCanModify) {
        this.subjectCanModify = subjectCanModify;
    }

    public Date getDiscussTime() {
        return discussTime;
    }

    public void setDiscussTime(Date discussTime) {
        this.discussTime = discussTime;
    }

    public String getDiscussAddress() {
        return discussAddress;
    }

    public void setDiscussAddress(String discussAddress) {
        this.discussAddress = discussAddress == null ? null : discussAddress.trim();
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}