package domain.cadre;

import java.io.Serializable;
import java.util.Date;

public class CadrePostPro implements Serializable {
    private Integer id;

    private Integer cadreId;

    private Boolean isCurrent;

    private Integer type;

    private Integer post;

    private Date holdTime;

    private Integer level;

    private Date gradeTime;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public Date getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(Date holdTime) {
        this.holdTime = holdTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getGradeTime() {
        return gradeTime;
    }

    public void setGradeTime(Date gradeTime) {
        this.gradeTime = gradeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}