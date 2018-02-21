package bean;

import domain.cadre.CadreFamliy;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by fafa on 2016/10/29.
 * 干部任免审批表要素
 */
public class CadreAdform  implements Serializable {

    private Integer cadreId;
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
    // 民族
    private String nation;
    // 籍贯
    private String nativePlace;
    // 出生地
    private String homeplace;
    // 入党时间
    private Date growTime;
    // 参加工作时间
    private Date workTime;
    // 健康状况
    private String health;
    // 专业技术职务
    private String proPost;
    // 熟悉专业有何特长
    private String specialty;

    // 全日制教育-最高学历
    private String edu;
    // 全日制教育-最高学位
    private String degree;
    // 全日制教育-毕业院校系及专业
    private String schoolDepMajor;
    // 在职教育-最高学历
    private String inDegree;
    // 在职教育-毕业院校系及专业
    private String inSchoolDepMajor;

    // 现任职务
    private String post;
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
    // 培训情况
    private String trainDesc;
    // 主要家庭成员及社会关系
    private List<CadreFamliy> cadreFamliys;

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

    public String getSchoolDepMajor() {
        return schoolDepMajor;
    }

    public void setSchoolDepMajor(String schoolDepMajor) {
        this.schoolDepMajor = schoolDepMajor;
    }

    public String getInDegree() {
        return inDegree;
    }

    public void setInDegree(String inDegree) {
        this.inDegree = inDegree;
    }

    public String getInSchoolDepMajor() {
        return inSchoolDepMajor;
    }

    public void setInSchoolDepMajor(String inSchoolDepMajor) {
        this.inSchoolDepMajor = inSchoolDepMajor;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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

    public String getTrainDesc() {
        return trainDesc;
    }

    public void setTrainDesc(String trainDesc) {
        this.trainDesc = trainDesc;
    }

    public List<CadreFamliy> getCadreFamliys() {
        return cadreFamliys;
    }

    public void setCadreFamliys(List<CadreFamliy> cadreFamliys) {
        this.cadreFamliys = cadreFamliys;
    }
}
