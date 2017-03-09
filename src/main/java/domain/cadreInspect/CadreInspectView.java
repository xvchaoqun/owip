package domain.cadreInspect;

import domain.cadre.CadreAdditionalPost;
import domain.cadre.CadreAdminLevel;
import domain.cadre.CadrePost;
import domain.dispatch.Dispatch;
import domain.dispatch.DispatchCadre;
import domain.sys.SysUserView;
import domain.unit.Unit;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CadreInspectView implements Serializable {
    public SysUserView getUser(){
        return CmTag.getUserById(userId);
    }
    public Unit getUnit(){
        return CmTag.getUnit(unitId);
    }
    // 主职
    public CadrePost getMainCadrePost(){
        return CmTag.getCadreMainCadrePostById(mainCadrePostId);
    }
    // 现任职务
    public CadreAdminLevel getPresentAdminLevel() {
        return CmTag.getPresentByCadreId(id);
    }
    // 兼审单位
    public List<CadreAdditionalPost> getCadreAdditionalPosts(){
        return CmTag.getCadreAdditionalPosts(id);
    }

    // 离任文件
    public Dispatch getDispatch(){
        if(dispatchCadreId!=null){
            DispatchCadre dispatchCadre = CmTag.getDispatchCadre(dispatchCadreId);
            if(dispatchCadre!=null)
                return CmTag.getDispatch(dispatchCadre.getDispatchId());
        }
        return null;
    }
    private Integer inspectId;

    private Byte inspectType;

    private Byte inspectStatus;

    private String inspectRemark;

    private Integer inspectSortOrder;

    private Integer id;

    private Integer userId;

    private Integer typeId;

    private Integer postId;

    private Integer unitId;

    private String title;

    private Integer dispatchCadreId;

    private String post;

    private Integer dpTypeId;

    private Date dpAddTime;

    private String dpPost;

    private String dpRemark;

    private Boolean isDp;

    private String remark;

    private Integer sortOrder;

    private Byte status;

    private String msgTitle;

    private String mobile;

    private String phone;

    private String homePhone;

    private String email;

    private String realname;

    private Byte gender;

    private String nation;

    private String nativePlace;

    private String idcard;

    private Date birth;

    private Integer partyId;

    private Integer branchId;

    private Date growTime;

    private Byte memberStatus;

    private Integer eduId;

    private Date finishTime;

    private Integer learnStyle;

    private String school;

    private String dep;

    private Byte schoolType;

    private String major;

    private String degree;

    private String postClass;

    private String subPostClass;

    private String mainPostLevel;

    private Date proPostTime;

    private String proPostLevel;

    private Date proPostLevelTime;

    private String proPost;

    private String manageLevel;

    private Date manageLevelTime;

    private Date arriveTime;

    private Date workStartTime;

    private Integer mainCadrePostId;

    private Boolean isDouble;

    private Integer doubleUnitId;

    private String adminLevelCode;

    private String adminLevelName;

    private String maxCeEduAttr;

    private String maxCeEduCode;

    private String maxCeEduName;

    private String unitName;

    private Integer unitTypeId;

    private String unitTypeCode;

    private String unitTypeName;

    private String unitTypeAttr;

    private static final long serialVersionUID = 1L;

    public Integer getInspectId() {
        return inspectId;
    }

    public void setInspectId(Integer inspectId) {
        this.inspectId = inspectId;
    }

    public Byte getInspectType() {
        return inspectType;
    }

    public void setInspectType(Byte inspectType) {
        this.inspectType = inspectType;
    }

    public Byte getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(Byte inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    public String getInspectRemark() {
        return inspectRemark;
    }

    public void setInspectRemark(String inspectRemark) {
        this.inspectRemark = inspectRemark == null ? null : inspectRemark.trim();
    }

    public Integer getInspectSortOrder() {
        return inspectSortOrder;
    }

    public void setInspectSortOrder(Integer inspectSortOrder) {
        this.inspectSortOrder = inspectSortOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getDispatchCadreId() {
        return dispatchCadreId;
    }

    public void setDispatchCadreId(Integer dispatchCadreId) {
        this.dispatchCadreId = dispatchCadreId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Integer getDpTypeId() {
        return dpTypeId;
    }

    public void setDpTypeId(Integer dpTypeId) {
        this.dpTypeId = dpTypeId;
    }

    public Date getDpAddTime() {
        return dpAddTime;
    }

    public void setDpAddTime(Date dpAddTime) {
        this.dpAddTime = dpAddTime;
    }

    public String getDpPost() {
        return dpPost;
    }

    public void setDpPost(String dpPost) {
        this.dpPost = dpPost == null ? null : dpPost.trim();
    }

    public String getDpRemark() {
        return dpRemark;
    }

    public void setDpRemark(String dpRemark) {
        this.dpRemark = dpRemark == null ? null : dpRemark.trim();
    }

    public Boolean getIsDp() {
        return isDp;
    }

    public void setIsDp(Boolean isDp) {
        this.isDp = isDp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle == null ? null : msgTitle.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone == null ? null : homePhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Date getGrowTime() {
        return growTime;
    }

    public void setGrowTime(Date growTime) {
        this.growTime = growTime;
    }

    public Byte getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Byte memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Integer getEduId() {
        return eduId;
    }

    public void setEduId(Integer eduId) {
        this.eduId = eduId;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getLearnStyle() {
        return learnStyle;
    }

    public void setLearnStyle(Integer learnStyle) {
        this.learnStyle = learnStyle;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep == null ? null : dep.trim();
    }

    public Byte getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(Byte schoolType) {
        this.schoolType = schoolType;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public String getPostClass() {
        return postClass;
    }

    public void setPostClass(String postClass) {
        this.postClass = postClass == null ? null : postClass.trim();
    }

    public String getSubPostClass() {
        return subPostClass;
    }

    public void setSubPostClass(String subPostClass) {
        this.subPostClass = subPostClass == null ? null : subPostClass.trim();
    }

    public String getMainPostLevel() {
        return mainPostLevel;
    }

    public void setMainPostLevel(String mainPostLevel) {
        this.mainPostLevel = mainPostLevel == null ? null : mainPostLevel.trim();
    }

    public Date getProPostTime() {
        return proPostTime;
    }

    public void setProPostTime(Date proPostTime) {
        this.proPostTime = proPostTime;
    }

    public String getProPostLevel() {
        return proPostLevel;
    }

    public void setProPostLevel(String proPostLevel) {
        this.proPostLevel = proPostLevel == null ? null : proPostLevel.trim();
    }

    public Date getProPostLevelTime() {
        return proPostLevelTime;
    }

    public void setProPostLevelTime(Date proPostLevelTime) {
        this.proPostLevelTime = proPostLevelTime;
    }

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost == null ? null : proPost.trim();
    }

    public String getManageLevel() {
        return manageLevel;
    }

    public void setManageLevel(String manageLevel) {
        this.manageLevel = manageLevel == null ? null : manageLevel.trim();
    }

    public Date getManageLevelTime() {
        return manageLevelTime;
    }

    public void setManageLevelTime(Date manageLevelTime) {
        this.manageLevelTime = manageLevelTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }

    public Integer getMainCadrePostId() {
        return mainCadrePostId;
    }

    public void setMainCadrePostId(Integer mainCadrePostId) {
        this.mainCadrePostId = mainCadrePostId;
    }

    public Boolean getIsDouble() {
        return isDouble;
    }

    public void setIsDouble(Boolean isDouble) {
        this.isDouble = isDouble;
    }

    public Integer getDoubleUnitId() {
        return doubleUnitId;
    }

    public void setDoubleUnitId(Integer doubleUnitId) {
        this.doubleUnitId = doubleUnitId;
    }

    public String getAdminLevelCode() {
        return adminLevelCode;
    }

    public void setAdminLevelCode(String adminLevelCode) {
        this.adminLevelCode = adminLevelCode == null ? null : adminLevelCode.trim();
    }

    public String getAdminLevelName() {
        return adminLevelName;
    }

    public void setAdminLevelName(String adminLevelName) {
        this.adminLevelName = adminLevelName == null ? null : adminLevelName.trim();
    }

    public String getMaxCeEduAttr() {
        return maxCeEduAttr;
    }

    public void setMaxCeEduAttr(String maxCeEduAttr) {
        this.maxCeEduAttr = maxCeEduAttr == null ? null : maxCeEduAttr.trim();
    }

    public String getMaxCeEduCode() {
        return maxCeEduCode;
    }

    public void setMaxCeEduCode(String maxCeEduCode) {
        this.maxCeEduCode = maxCeEduCode == null ? null : maxCeEduCode.trim();
    }

    public String getMaxCeEduName() {
        return maxCeEduName;
    }

    public void setMaxCeEduName(String maxCeEduName) {
        this.maxCeEduName = maxCeEduName == null ? null : maxCeEduName.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public String getUnitTypeCode() {
        return unitTypeCode;
    }

    public void setUnitTypeCode(String unitTypeCode) {
        this.unitTypeCode = unitTypeCode == null ? null : unitTypeCode.trim();
    }

    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName == null ? null : unitTypeName.trim();
    }

    public String getUnitTypeAttr() {
        return unitTypeAttr;
    }

    public void setUnitTypeAttr(String unitTypeAttr) {
        this.unitTypeAttr = unitTypeAttr == null ? null : unitTypeAttr.trim();
    }
}