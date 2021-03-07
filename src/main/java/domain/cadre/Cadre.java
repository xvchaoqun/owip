package domain.cadre;

import domain.sys.SysUserView;
import org.springframework.format.annotation.DateTimeFormat;
import sys.tags.CmTag;
import sys.utils.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class Cadre implements Serializable {
    public SysUserView getUser(){

        return CmTag.getUserById(userId);
    }
    private Integer id;

    private Integer userId;

    private Integer type;

    private Boolean hasCrp;

    private Boolean isDouble;

    private String doubleUnitIds;

    private Integer state;

    private String title;

    private Boolean isOutside;

    private Integer dispatchCadreId;

    private String label;

    private String profile;

    private String remark;

    private String originalPost;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date appointDate;

    @DateTimeFormat(pattern = DateUtils.YYYYMMDD_DOT)
    private Date deposeDate;

    private Integer sortOrder;

    private Byte status;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getHasCrp() {
        return hasCrp;
    }

    public void setHasCrp(Boolean hasCrp) {
        this.hasCrp = hasCrp;
    }

    public Boolean getIsDouble() {
        return isDouble;
    }

    public void setIsDouble(Boolean isDouble) {
        this.isDouble = isDouble;
    }

    public String getDoubleUnitIds() {
        return doubleUnitIds;
    }

    public void setDoubleUnitIds(String doubleUnitIds) {
        this.doubleUnitIds = doubleUnitIds == null ? null : doubleUnitIds.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Boolean getIsOutside() {
        return isOutside;
    }

    public void setIsOutside(Boolean isOutside) {
        this.isOutside = isOutside;
    }

    public Integer getDispatchCadreId() {
        return dispatchCadreId;
    }

    public void setDispatchCadreId(Integer dispatchCadreId) {
        this.dispatchCadreId = dispatchCadreId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile == null ? null : profile.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOriginalPost() {
        return originalPost;
    }

    public void setOriginalPost(String originalPost) {
        this.originalPost = originalPost == null ? null : originalPost.trim();
    }

    public Date getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(Date appointDate) {
        this.appointDate = appointDate;
    }

    public Date getDeposeDate() {
        return deposeDate;
    }

    public void setDeposeDate(Date deposeDate) {
        this.deposeDate = deposeDate;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}