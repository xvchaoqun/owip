package ext.pay;

import java.util.Map;

public class OrderFormBean {

    private String tranamt;
    private String account;
    private String sno;
    private String toaccount;
    private String thirdsystem;
    private String thirdorderid;
    private String ordertype;
    private String orderdesc;
    private String praram1;
    private String thirdurl;
    private String sign;
    private Map<String, Object> paramMap;
    
    @Override
    public String toString() {
        return "OrderFormBean{" +
                "tranamt=" + tranamt +
                ", account='" + account + '\'' +
                ", sno='" + sno + '\'' +
                ", toaccount='" + toaccount + '\'' +
                ", thirdsystem='" + thirdsystem + '\'' +
                ", thirdorderid='" + thirdorderid + '\'' +
                ", ordertype='" + ordertype + '\'' +
                ", orderdesc='" + orderdesc + '\'' +
                ", praram1='" + praram1 + '\'' +
                ", thirdurl='" + thirdurl + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
    
    public String getTranamt() {
        return tranamt;
    }
    
    public void setTranamt(String tranamt) {
        this.tranamt = tranamt;
    }
    
    public String getAccount() {
        return account;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getSno() {
        return sno;
    }
    
    public void setSno(String sno) {
        this.sno = sno;
    }
    
    public String getToaccount() {
        return toaccount;
    }
    
    public void setToaccount(String toaccount) {
        this.toaccount = toaccount;
    }
    
    public String getThirdsystem() {
        return thirdsystem;
    }
    
    public void setThirdsystem(String thirdsystem) {
        this.thirdsystem = thirdsystem;
    }
    
    public String getThirdorderid() {
        return thirdorderid;
    }
    
    public void setThirdorderid(String thirdorderid) {
        this.thirdorderid = thirdorderid;
    }
    
    public String getOrdertype() {
        return ordertype;
    }
    
    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }
    
    public String getOrderdesc() {
        return orderdesc;
    }
    
    public void setOrderdesc(String orderdesc) {
        this.orderdesc = orderdesc;
    }
    
    public String getPraram1() {
        return praram1;
    }
    
    public void setPraram1(String praram1) {
        this.praram1 = praram1;
    }
    
    public String getThirdurl() {
        return thirdurl;
    }
    
    public void setThirdurl(String thirdurl) {
        this.thirdurl = thirdurl;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
