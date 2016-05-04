package bean;

import shiro.ShiroUser;

import java.util.Date;

/**
 * Created by fafa on 2016/5/2.
 */
public class LoginUser {

    private String ip; //登录ip
    private ShiroUser shiroUser;
    private Date lastAccessTime;
    private Date startTimestamp;
    private long timeOut;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
}
