package domain.cet;

import domain.sys.SysUserView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import persistence.cet.CetDiscussMapper;
import service.cet.CetPartySchoolService;
import service.cet.CetPartyService;
import sys.constants.CetConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CetDiscussGroup implements Serializable {

    public List<SysUserView> getHoldUsers(){
        if(StringUtils.isBlank(holdUserIds)) return null;

        List<SysUserView> uvs = new ArrayList<>();
        for (String _userId : holdUserIds.split(",")) {

            uvs.add(CmTag.getUserById(Integer.valueOf(_userId)));
        }

        return uvs;
    }

    public List<SysUserView> getLinkUsers(){
        if(StringUtils.isBlank(linkUserIds)) return null;

        List<SysUserView> uvs = new ArrayList<>();
        for (String _userId : linkUserIds.split(",")) {

            uvs.add(CmTag.getUserById(Integer.valueOf(_userId)));
        }

        return uvs;
    }

    public SysUserView getAdminUser(){
        return CmTag.getUserById(adminUserId);
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

    private String holdUserIds;

    private String linkUserIds;

    private String subject;

    private Boolean subjectCanModify;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
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

    public String getHoldUserIds() {
        return holdUserIds;
    }

    public void setHoldUserIds(String holdUserIds) {
        this.holdUserIds = holdUserIds == null ? null : holdUserIds.trim();
    }

    public String getLinkUserIds() {
        return linkUserIds;
    }

    public void setLinkUserIds(String linkUserIds) {
        this.linkUserIds = linkUserIds == null ? null : linkUserIds.trim();
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