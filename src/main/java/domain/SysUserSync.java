package domain;

import sys.constants.SystemConstants;

import java.io.Serializable;
import java.util.Date;

public class SysUserSync implements Serializable {

    public String getTypeName(){
        return SystemConstants.USER_SOURCE_MAP.get(type);
    }

    private Integer id;

    private Byte type;

    private Integer userId;

    private Boolean autoStart;

    private Boolean autoStop;

    private Boolean isStop;

    private Integer totalPage;

    private Integer totalCount;

    private Integer currentPage;

    private Integer currentCount;

    private Integer insertCount;

    private Integer updateCount;

    private Date startTime;

    private Date endTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getAutoStart() {
        return autoStart;
    }

    public void setAutoStart(Boolean autoStart) {
        this.autoStart = autoStart;
    }

    public Boolean getAutoStop() {
        return autoStop;
    }

    public void setAutoStop(Boolean autoStop) {
        this.autoStop = autoStop;
    }

    public Boolean getIsStop() {
        return isStop;
    }

    public void setIsStop(Boolean isStop) {
        this.isStop = isStop;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Integer currentCount) {
        this.currentCount = currentCount;
    }

    public Integer getInsertCount() {
        return insertCount;
    }

    public void setInsertCount(Integer insertCount) {
        this.insertCount = insertCount;
    }

    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}