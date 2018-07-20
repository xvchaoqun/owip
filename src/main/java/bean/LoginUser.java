package bean;

import shiro.ShiroUser;

import java.util.Date;

/**
 * Created by fafa on 2016/5/2.
 */
public class LoginUser {

    private String sid; //session id
    private String ip; //登录ip
    private String country;
    private String area;
    private String userAgent;
    private ShiroUser shiroUser;
    private Date lastAccessTime;
    private Date startTimestamp;
    private long timeOut;
    private String switchUser; // 切换登录主账号

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public ShiroUser getShiroUser() {
        return shiroUser;
    }

    public void setShiroUser(ShiroUser shiroUser) {
        this.shiroUser = shiroUser;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public String getSwitchUser() {
        return switchUser;
    }

    public void setSwitchUser(String switchUser) {
        this.switchUser = switchUser;
    }
}
