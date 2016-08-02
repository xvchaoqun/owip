package bean;

import domain.party.Branch;
import domain.party.Party;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fafa on 2015/12/11.
 */
// 用户通用字段
public class UserBean implements Serializable{

    // 平台用户基础信息
    public Integer userId;
    public String code; // 学工号
    public String username;
    public String realname;
    public Byte type; // 类别：教师、学生
    public Byte gender;
    public Date birth;
    public String nation; // 民族
    public String idcard;
    public String nativePlace; // 籍贯
    public String mobile; // 联系电话

    //+++++++以下是党员基础信息
    public Byte politicalStatus; // 政治面貌
    public Date growTime; // 入党时间
    public Integer partyId;
    public Integer branchId;
    public Party party;
    public Branch branch;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
