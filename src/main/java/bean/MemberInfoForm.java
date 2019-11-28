package bean;

import java.util.Date;

public class MemberInfoForm {

    //是否是一线书记
    private Boolean isPrefessionalSecretary;
    //是否是干部
    private Boolean isCadre;
    //干部id
    private Integer cadreId;
    //真实姓名
    private String realname;
    //性别
    private Byte gender;
    //生日
    private Date birth;
    //年龄
    private Integer age;
    // 头像 base64
    private String avatar;

    private Integer avatarWidth;

    private Integer avatarHeight;
    // 民族
    private String nation;
    // 籍贯
    private String nativePlace;
    //工作证号
    private String code;
    // 中共党员加入时间
    private Date growTime;
    // 参加工作时间
    private Date workTime;
    // 专业技术职务
    private String proPost;
    //是否是党支部书记
    private Boolean isBranchSecretary;
    //是否是党委委员或者支部委员
    private Boolean isMember;
    // 全日制教育-最高学历
    private String edu;
    // 全日制教育-最高学位
    private String degree;
    /*
     全日制教育-学历学位毕业学校是否一致
     如果学历学位毕业学校一致（或只有学历或只有学位），则schoolDepMajor1是学校和院系，schoolDepMajor2是专业
    */
    private boolean sameSchool;
    // 全日制教育-学历毕业院校系及专业
    private String schoolDepMajor1;
    // 全日制教育-学位毕业院校系及专业
    private String schoolDepMajor2;

    // 在职教育-最高学历
    private String inEdu;
    // 在职教育-最高学位
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
    //所在党组织
    private String partyName;
    //党委委员任现职时间
    private String pmAssignDate;
    //支部委员任现职时间
    private String bmAssignDate;
    //行政职务
    private String post;
    //联系方式
    private String mobile;
    // 简历
    private String resumeDesc;
    // 奖惩情况
    private String reward;
    // 年度考核结果
    private String ces;


    public String getBmAssignDate() {
        return bmAssignDate;
    }

    public void setBmAssignDate(String bmAssignDate) {
        this.bmAssignDate = bmAssignDate;
    }

    public Boolean getPrefessionalSecretary() {
        return isPrefessionalSecretary;
    }

    public void setPrefessionalSecretary(Boolean prefessionalSecretary) {
        isPrefessionalSecretary = prefessionalSecretary;
    }

    public Boolean getCadre() {
        return isCadre;
    }

    public void setCadre(Boolean cadre) {
        isCadre = cadre;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost;
    }

    public Boolean getBranchSecretary() {
        return isBranchSecretary;
    }

    public void setBranchSecretary(Boolean branchSecretary) {
        isBranchSecretary = branchSecretary;
    }

    public Boolean getMember() {
        return isMember;
    }

    public void setMember(Boolean member) {
        isMember = member;
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

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPmAssignDate() {
        return pmAssignDate;
    }

    public void setPmAssignDate(String pmAssignDate) {
        this.pmAssignDate = pmAssignDate;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getCes() {
        return ces;
    }

    public void setCes(String ces) {
        this.ces = ces;
    }
}
