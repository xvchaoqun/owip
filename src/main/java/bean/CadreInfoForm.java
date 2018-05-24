package bean;

import domain.cadre.CadreCompany;
import domain.cadre.CadreFamily;
import domain.cadre.CadreFamilyAbroad;

import java.util.Date;
import java.util.List;

/**
 * 干部信息
 */
public class CadreInfoForm {

    private Integer cadreId;
    // 工作账号
    private String code;
    // 真实姓名
    private String realname;
    // 性别
    private Byte gender;
    // 出生日期
    private Date birth;
    // 年龄
    private Integer age;
    // 头像 base64
    private String avatar;

    private Integer avatarWidth;

    private Integer avatarHeight;
    // 民族
    private String nation;
    // 籍贯
    private String nativePlace;
    // 出生地
    private String homeplace;
    // 党派
    private Integer cadreDpType;
    // 党派加入时间
    private Date growTime;
    // 参加工作时间
    private Date workTime;
    // 健康状况
    private String health;
    // 专业技术职务
    private String proPost;
    // 评定时间
    private Date proPostTime;
    // 熟悉专业有何特长
    private String specialty;

    // 全日制教育-最高学历
    private String edu;
    // 全日制教育-学位
    private String degree;
    // 全日制教育-毕业院校
    private String school;
    // 全日制教育-系及专业
    private String depMajor;
    // 全日制教育-毕业院校系及专业
    private String schoolDepMajor;
    // 在职教育-最高学历
    private String inEdu;
    // 在职教育-最高学历
    private String inDegree;
    // 在职教育-毕业院校
    private String inSchool;
    // 在职教育-系及专业
    private String inDepMajor;
    // 在职教育-毕业院校系及专业
    private String inSchoolDepMajor;

    // 硕士研究生导师信息
    private String masterTutor;
    // 博士研究生导师
    private String doctorTutor;
    // 工作单位及现任职务
    private String post;
    // 行政级别
    private Integer adminLevel;
    // 管理岗位等级
    private String manageLevel;
    // 管理岗位等级
    private Date manageLevelTime;

    // 身份证号
    private String idCard;
    // 户籍地
    private String household;
    // 院系工作经历
    private String depWork;
    // 主要社会或学术兼职
    private String parttime;

    // 拟任职务
    private String inPost;
    // 拟免职务
    private String prePost;

    // 奖惩情况
    private String reward;
    // 年度考核结果
    private String ces;
    // 任免理由
    private String reason;

    // 学习经历
    private String learnDesc;
    // 工作经历
    private String workDesc;
    // 任职经历
    private String postDesc;
    // 培训情况
    private String trainDesc;
    // 教学情况
    private String teachDesc;
    // 科研情况
    private String researchDesc;
    // 其他奖励情况
    private String otherRewardDesc;

    // 手机
    private String mobile;
    // 办公电话
    private String phone;
    // 电子邮箱
    private String email;
    // 家庭电话
    private String homePhone;

    // 企业兼职情况
    private List<CadreCompany> cadreCompanies;
    // 主要家庭成员及社会关系
    private List<CadreFamily> cadreFamilys;
    // 家庭成员移居国（境）外的情况
    private List<CadreFamilyAbroad> cadreFamilyAbroads;

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getAvatarWidth() {
        return avatarWidth;
    }

    public void setAvatarWidth(Integer avatarWidth) {
        this.avatarWidth = avatarWidth;
    }

    public Integer getAvatarHeight() {
        return avatarHeight;
    }

    public void setAvatarHeight(Integer avatarHeight) {
        this.avatarHeight = avatarHeight;
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

    public String getHomeplace() {
        return homeplace;
    }

    public void setHomeplace(String homeplace) {
        this.homeplace = homeplace;
    }

    public Integer getCadreDpType() {
        return cadreDpType;
    }

    public void setCadreDpType(Integer cadreDpType) {
        this.cadreDpType = cadreDpType;
    }

    public Date getGrowTime() {
        return growTime;
    }

    public void setGrowTime(Date growTime) {
        this.growTime = growTime;
    }

    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost;
    }

    public Date getProPostTime() {
        return proPostTime;
    }

    public void setProPostTime(Date proPostTime) {
        this.proPostTime = proPostTime;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDepMajor() {
        return depMajor;
    }

    public void setDepMajor(String depMajor) {
        this.depMajor = depMajor;
    }

    public String getSchoolDepMajor() {
        return schoolDepMajor;
    }

    public void setSchoolDepMajor(String schoolDepMajor) {
        this.schoolDepMajor = schoolDepMajor;
    }

    public String getInEdu() {
        return inEdu;
    }

    public void setInEdu(String inEdu) {
        this.inEdu = inEdu;
    }

    public String getInDegree() {
        return inDegree;
    }

    public void setInDegree(String inDegree) {
        this.inDegree = inDegree;
    }

    public String getInSchool() {
        return inSchool;
    }

    public void setInSchool(String inSchool) {
        this.inSchool = inSchool;
    }

    public String getInDepMajor() {
        return inDepMajor;
    }

    public void setInDepMajor(String inDepMajor) {
        this.inDepMajor = inDepMajor;
    }

    public String getInSchoolDepMajor() {
        return inSchoolDepMajor;
    }

    public void setInSchoolDepMajor(String inSchoolDepMajor) {
        this.inSchoolDepMajor = inSchoolDepMajor;
    }

    public String getMasterTutor() {
        return masterTutor;
    }

    public void setMasterTutor(String masterTutor) {
        this.masterTutor = masterTutor;
    }

    public String getDoctorTutor() {
        return doctorTutor;
    }

    public void setDoctorTutor(String doctorTutor) {
        this.doctorTutor = doctorTutor;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getManageLevel() {
        return manageLevel;
    }

    public void setManageLevel(String manageLevel) {
        this.manageLevel = manageLevel;
    }

    public Date getManageLevelTime() {
        return manageLevelTime;
    }

    public void setManageLevelTime(Date manageLevelTime) {
        this.manageLevelTime = manageLevelTime;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getHousehold() {
        return household;
    }

    public void setHousehold(String household) {
        this.household = household;
    }

    public String getDepWork() {
        return depWork;
    }

    public void setDepWork(String depWork) {
        this.depWork = depWork;
    }

    public String getParttime() {
        return parttime;
    }

    public void setParttime(String parttime) {
        this.parttime = parttime;
    }

    public String getInPost() {
        return inPost;
    }

    public void setInPost(String inPost) {
        this.inPost = inPost;
    }

    public String getPrePost() {
        return prePost;
    }

    public void setPrePost(String prePost) {
        this.prePost = prePost;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getCes() {
        return ces;
    }

    public void setCes(String ces) {
        this.ces = ces;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLearnDesc() {
        return learnDesc;
    }

    public void setLearnDesc(String learnDesc) {
        this.learnDesc = learnDesc;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getTrainDesc() {
        return trainDesc;
    }

    public void setTrainDesc(String trainDesc) {
        this.trainDesc = trainDesc;
    }

    public String getTeachDesc() {
        return teachDesc;
    }

    public void setTeachDesc(String teachDesc) {
        this.teachDesc = teachDesc;
    }

    public String getResearchDesc() {
        return researchDesc;
    }

    public void setResearchDesc(String researchDesc) {
        this.researchDesc = researchDesc;
    }

    public String getOtherRewardDesc() {
        return otherRewardDesc;
    }

    public void setOtherRewardDesc(String otherRewardDesc) {
        this.otherRewardDesc = otherRewardDesc;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public List<CadreCompany> getCadreCompanies() {
        return cadreCompanies;
    }

    public void setCadreCompanies(List<CadreCompany> cadreCompanies) {
        this.cadreCompanies = cadreCompanies;
    }

    public List<CadreFamily> getCadreFamilys() {
        return cadreFamilys;
    }

    public void setCadreFamilys(List<CadreFamily> cadreFamilys) {
        this.cadreFamilys = cadreFamilys;
    }

    public List<CadreFamilyAbroad> getCadreFamilyAbroads() {
        return cadreFamilyAbroads;
    }

    public void setCadreFamilyAbroads(List<CadreFamilyAbroad> cadreFamilyAbroads) {
        this.cadreFamilyAbroads = cadreFamilyAbroads;
    }
}
