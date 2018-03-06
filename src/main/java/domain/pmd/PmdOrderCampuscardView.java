package domain.pmd;

import java.io.Serializable;
import java.util.Date;

public class PmdOrderCampuscardView implements Serializable {
    private String sn;

    private Integer memberId;

    private String paycode;

    private String payer;

    private String payertype;

    private String payername;

    private String amt;

    private String macc;

    private String commnet;

    private String snoIdName;

    private String sign;

    private Integer userId;

    private Boolean isSuccess;

    private Boolean isClosed;

    private Date createTime;

    private String ip;

    private Date payMonth;

    private Integer memberUserId;

    private String realname;

    private String code;

    private static final long serialVersionUID = 1L;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPaycode() {
        return paycode;
    }

    public void setPaycode(String paycode) {
        this.paycode = paycode == null ? null : paycode.trim();
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.trim();
    }

    public String getPayertype() {
        return payertype;
    }

    public void setPayertype(String payertype) {
        this.payertype = payertype == null ? null : payertype.trim();
    }

    public String getPayername() {
        return payername;
    }

    public void setPayername(String payername) {
        this.payername = payername == null ? null : payername.trim();
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt == null ? null : amt.trim();
    }

    public String getMacc() {
        return macc;
    }

    public void setMacc(String macc) {
        this.macc = macc == null ? null : macc.trim();
    }

    public String getCommnet() {
        return commnet;
    }

    public void setCommnet(String commnet) {
        this.commnet = commnet == null ? null : commnet.trim();
    }

    public String getSnoIdName() {
        return snoIdName;
    }

    public void setSnoIdName(String snoIdName) {
        this.snoIdName = snoIdName == null ? null : snoIdName.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(Date payMonth) {
        this.payMonth = payMonth;
    }

    public Integer getMemberUserId() {
        return memberUserId;
    }

    public void setMemberUserId(Integer memberUserId) {
        this.memberUserId = memberUserId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}