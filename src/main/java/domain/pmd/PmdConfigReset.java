package domain.pmd;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class PmdConfigReset implements Serializable {
    private Integer id;

    private Integer userId;

    @DateTimeFormat(pattern = "yyyy.MM")
    private Date salaryMonth;

    private Date createTime;

    private String ip;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(Date salaryMonth) {
        this.salaryMonth = salaryMonth;
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