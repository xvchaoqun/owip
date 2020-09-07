package domain.pmd;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class PmdOrder implements Serializable {

    // 收银台参数，确认订单时赋值
    private Map<String, String> formMap;

    public Map<String, String> getFormMap() {
        return formMap;
    }

    public void setFormMap(Map<String, String> formMap) {
        this.formMap = formMap;
    }

    private String sn;

    private Integer memberId;

    private Boolean isBatch;

    private String payMonth;

    private String payer;

    private String payername;

    private String amt;

    private Byte payWay;

    private String params;

    private String sign;

    private Integer userId;

    private Boolean isSuccess;

    private Boolean isClosed;

    private Date createTime;

    private String ip;

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

    public Boolean getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(Boolean isBatch) {
        this.isBatch = isBatch;
    }

    public String getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(String payMonth) {
        this.payMonth = payMonth == null ? null : payMonth.trim();
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.trim();
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

    public Byte getPayWay() {
        return payWay;
    }

    public void setPayWay(Byte payWay) {
        this.payWay = payWay;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
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
}