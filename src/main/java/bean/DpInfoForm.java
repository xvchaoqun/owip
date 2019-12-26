package bean;

import domain.dp.DpFamily;
import domain.dp.DpParty;
import sys.helper.DpPartyHelper;

import java.util.Date;
import java.util.List;

/*
* 党派成员信息表
* */
public class DpInfoForm {

    public DpParty getDpParty(){return DpPartyHelper.getDpPartyByPartyId(dpPartyId); }

    private Integer userId;
    //工作证号
    private String code;
    //真实姓名
    private String realname;
    //性别
    private Byte gender;
    //出生日期
    private Date birth;
    //年龄
    private Integer age;
    //头像
    private String avatar;

    private Integer avatarWidth;

    private Integer avatarHeight;
    //民族
    private String nation;
    //籍贯
    private String nativePlace;
    //出生地
    private String homePlace;
    //参加党派
    private Integer dpPartyId;
    //加入党派时间
    private Date dpGrowTime;
    //参加工作时间
    private Date workTime;
    //健康状况
    private String health;
    //专业技术职务
    private String proPost;
    //熟悉专业有何特长
    private String specialty;

    //全日制教育-最高学历
    private String edu;
    //全日制教育-最高学位
    private String degree;
    /*
     全日制教育-学历学位毕业学校是否一致
     如果学历学位毕业学校一致（或只有学历或只有学位），则schoolDepMajor1是学校和院系，schoolDepMajor2是专业
    */
    private boolean sameSchool;
    //全日制教育-学历毕业院校系及专业
    private String schoolDepMajor1;
    //全日制教育-学位毕业院校系及专业
    private String schoolDepMajor2;

    //在职教育-最高学历
    private String inEdu;
    //在职教育-最高学位
    private String inDegree;
    /*
     在职教育-学历学位毕业学校是否一致
     如果学历学位毕业学校一致（或只有学历或只有学位），则inSchoolDepMajor1是学校和院系，inSchoolDepMajor2是专业
    */
    private boolean sameInSchool;
    // 在职教育-学历毕业院校系及专业
    private String inSchoolDepMajor1;
    // 在职教育-学位毕业院校系及专业
    private String inSchoolDepMajor2;

    //现任职务
    private String title;
    //社会兼职
    private String partTimeJob;
    //简历
    private String resumeDesc;
    //主要荣誉（党派奖励情况）
    private String reward;
    //其他奖励情况
    private String otherReward;
    //年度考核结果
    private String eva;
    //培训情况
    private String trainDesc;
    //主要学术成果工作业绩
    private String achievements;
    //主要家庭成员及社会关系
    private List<DpFamily> dpFamilies;
    //通讯地址
    private String address;
    //邮政编码
    private String postalCode;
    //手机
    private String mobile;
    //联系电话
    private String phone;
    //电子邮件
    private String email;
    //传真
    private String fax;

    public String getTrainDesc() {
        return trainDesc;
    }

    public void setTrainDesc(String trainDesc) {
        this.trainDesc = trainDesc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDpPartyId() {
        return dpPartyId;
    }

    public void setDpPartyId(Integer dpPartyId) {
        this.dpPartyId = dpPartyId;
    }

    public Date getDpGrowTime() {
        return dpGrowTime;
    }

    public void setDpGrowTime(Date dpGrowTime) {
        this.dpGrowTime = dpGrowTime;
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

    public boolean isSameSchool() {
        return sameSchool;
    }

    public void setSameSchool(boolean sameSchool) {
        this.sameSchool = sameSchool;
    }

    public String getSchoolDepMajor1() {
        return schoolDepMajor1;
    }

    public void setSchoolDepMajor1(String schoolDepMajor1) {
        this.schoolDepMajor1 = schoolDepMajor1;
    }

    public String getSchoolDepMajor2() {
        return schoolDepMajor2;
    }

    public void setSchoolDepMajor2(String schoolDepMajor2) {
        this.schoolDepMajor2 = schoolDepMajor2;
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

    public boolean isSameInSchool() {
        return sameInSchool;
    }

    public void setSameInSchool(boolean sameInSchool) {
        this.sameInSchool = sameInSchool;
    }

    public String getInSchoolDepMajor1() {
        return inSchoolDepMajor1;
    }

    public void setInSchoolDepMajor1(String inSchoolDepMajor1) {
        this.inSchoolDepMajor1 = inSchoolDepMajor1;
    }

    public String getInSchoolDepMajor2() {
        return inSchoolDepMajor2;
    }

    public void setInSchoolDepMajor2(String inSchoolDepMajor2) {
        this.inSchoolDepMajor2 = inSchoolDepMajor2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPartTimeJob() {
        return partTimeJob;
    }

    public void setPartTimeJob(String partTimeJob) {
        this.partTimeJob = partTimeJob;
    }

    public String getResumeDesc() {
        return resumeDesc;
    }

    public void setResumeDesc(String resumeDesc) {
        this.resumeDesc = resumeDesc;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getOtherReward() {
        return otherReward;
    }

    public void setOtherReward(String otherReward) {
        this.otherReward = otherReward;
    }

    public String getEva() {
        return eva;
    }

    public void setEva(String eva) {
        this.eva = eva;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public List<DpFamily> getDpFamilies() {
        return dpFamilies;
    }

    public void setDpFamilies(List<DpFamily> dpFamilies) {
        this.dpFamilies = dpFamilies;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getHomePlace() {
        return homePlace;
    }

    public void setHomePlace(String homePlace) {
        this.homePlace = homePlace;
    }
}
