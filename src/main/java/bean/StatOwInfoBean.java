package bean;

import java.util.Date;

public class StatOwInfoBean {

    private Integer branchId;
    private Integer partyId;
    private String branchName;//党支部名称
    private Integer types;//党支部类型
    private String realName;//支部书记
    private byte gender;//性别  1：男 2：女
    private String nation;//民族
    private Date growTime;//入党时间
    private Date birth;//出生年月
    private String proPost;//职称
    private String title;//职务

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Date getGrowTime() {
        return growTime;
    }

    public void setGrowTime(Date growTime) {
        this.growTime = growTime;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getProPost() {
        return proPost;
    }

    public void setProPost(String proPost) {
        this.proPost = proPost;
    }
}
