package domain.sc.scMatter;

import org.springframework.format.annotation.DateTimeFormat;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class ScMatterItem implements Serializable {
    private Integer id;

    private Integer matterId;

    private Integer userId;

    private String title;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date realHandTime;
    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date fillTime;

    private String remark;

    private Integer transferId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatterId() {
        return matterId;
    }

    public void setMatterId(Integer matterId) {
        this.matterId = matterId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getRealHandTime() {
        return realHandTime;
    }

    public void setRealHandTime(Date realHandTime) {
        this.realHandTime = realHandTime;
    }

    public Date getFillTime() {
        return fillTime;
    }

    public void setFillTime(Date fillTime) {
        this.fillTime = fillTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }
}