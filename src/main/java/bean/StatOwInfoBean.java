package bean;

import java.util.Date;

public class StatOwInfoBean {

    private Integer branchId;
    private Integer partyId;
    private String branchName;//党支部名称
    private String types;//党支部类型

    private String partyName;
    private Integer classId;//党组织类别
    private String postTypeIds;//党内职务
    private Integer unitTypeId;//单位性质
    private String unitCode;//单位编号

    private Integer userId;
    private String realName; // 人员姓名
    private String code;// 学工号
    private Byte userType;
    private String idcard; // 身份证号
    private Byte gender;//性别  1：男 2：女
    private String nation;//民族
    private String nativePlace;// 籍贯
    private Date birth;//出生年月
    private String mailingAddress;

    private Byte politicalStatus;//党籍状态
    private Date growTime;//入党时间
    private Date positiveTime;//转正时间
    private Byte status;//党员类型

    private Byte stage;//入党成熟度（所在阶段）
    private Date applyTime;//提交书面申请书时间
    private Date activeTime;//确定为入党积极分子时间
    private Date candidateTime;//确定为发展对象时间
    private String enrolYear;//招生年度

    private String proPost;//职称
    private String proPostLevel;//职称级别
    private String title;//职务/党政职务
    private String adminLevelCode;//党政职务级别对应的code
    private String adminLevelName; //党政职务级别

    private String mobile; //手机号
    private String identityType; // 个人身份
    private String school;//毕业学校
    private String major;//专业
    private String education;
    private String degree; // 学位

    private Date tranTime; // 应换届时间
    private Date actualTranTime;//实际换届时间

    private Date assignDate; // 任职时间
    private int positiveCount; //正式党员人数
    private int growCount; //预备党员人数
    private int applyCount; //入党申请人人数
    private int activeCount;//入党积极分子人数
    private int devCount;//发展对象人数
    private int jzgCount;//在职教职工数量
    private int retireCount;//离退休党员数量
    private int stuCount;//学生党员数量

    private Integer partySortOrder;
    private Integer branchSortOrder;

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getPostTypeIds() {
        return postTypeIds;
    }

    public void setPostTypeIds(String postTypeIds) {
        this.postTypeIds = postTypeIds;
    }

    public Integer getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(Integer unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
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
        this.nation = nation;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public Byte getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(Byte politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public Date getGrowTime() {
        return growTime;
    }

    public void setGrowTime(Date growTime) {
        this.growTime = growTime;
    }

    public Date getPositiveTime() {
        return positiveTime;
    }

    public void setPositiveTime(Date positiveTime) {
        this.positiveTime = positiveTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getStage() {
        return stage;
    }

    public void setStage(Byte stage) {
        this.stage = stage;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getCandidateTime() {
        return candidateTime;
    }

    public void setCandidateTime(Date candidateTime) {
        this.candidateTime = candidateTime;
    }

    public String getEnrolYear() {
        return enrolYear;
    }

    public void setEnrolYear(String enrolYear) {
        this.enrolYear = enrolYear;
    }

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost;
    }

    public String getProPostLevel() {
        return proPostLevel;
    }

    public void setProPostLevel(String proPostLevel) {
        this.proPostLevel = proPostLevel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdminLevelCode() {
        return adminLevelCode;
    }

    public void setAdminLevelCode(String adminLevelCode) {
        this.adminLevelCode = adminLevelCode;
    }

    public String getAdminLevelName() {
        return adminLevelName;
    }

    public void setAdminLevelName(String adminLevelName) {
        this.adminLevelName = adminLevelName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }

    public Date getActualTranTime() {
        return actualTranTime;
    }

    public void setActualTranTime(Date actualTranTime) {
        this.actualTranTime = actualTranTime;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public int getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(int positiveCount) {
        this.positiveCount = positiveCount;
    }

    public int getGrowCount() {
        return growCount;
    }

    public void setGrowCount(int growCount) {
        this.growCount = growCount;
    }

    public int getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
    }

    public int getActiveCount() {
        return activeCount;
    }

    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    public int getDevCount() {
        return devCount;
    }

    public void setDevCount(int devCount) {
        this.devCount = devCount;
    }

    public int getJzgCount() {
        return jzgCount;
    }

    public void setJzgCount(int jzgCount) {
        this.jzgCount = jzgCount;
    }

    public int getRetireCount() {
        return retireCount;
    }

    public void setRetireCount(int retireCount) {
        this.retireCount = retireCount;
    }

    public int getStuCount() {
        return stuCount;
    }

    public void setStuCount(int stuCount) {
        this.stuCount = stuCount;
    }

    public Integer getPartySortOrder() {
        return partySortOrder;
    }

    public void setPartySortOrder(Integer partySortOrder) {
        this.partySortOrder = partySortOrder;
    }

    public Integer getBranchSortOrder() {
        return branchSortOrder;
    }

    public void setBranchSortOrder(Integer branchSortOrder) {
        this.branchSortOrder = branchSortOrder;
    }
}
