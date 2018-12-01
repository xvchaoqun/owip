package domain.unit;

import org.springframework.format.annotation.DateTimeFormat;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class UnitTeamPlan implements Serializable {
    private Integer id;

    private Integer unitTeamId;

    private String mainPosts;

    private String vicePosts;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date startDate;

    @DateTimeFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date endDate;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitTeamId() {
        return unitTeamId;
    }

    public void setUnitTeamId(Integer unitTeamId) {
        this.unitTeamId = unitTeamId;
    }

    public String getMainPosts() {
        return mainPosts;
    }

    public void setMainPosts(String mainPosts) {
        this.mainPosts = mainPosts == null ? null : mainPosts.trim();
    }

    public String getVicePosts() {
        return vicePosts;
    }

    public void setVicePosts(String vicePosts) {
        this.vicePosts = vicePosts == null ? null : vicePosts.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}