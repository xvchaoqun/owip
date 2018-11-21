package domain.sc.scMatter;

import java.io.Serializable;
import java.util.Date;

public class ScMatterView implements Serializable {
    private Integer id;

    private Integer year;

    private Boolean type;

    private Date drawTime;

    private Date handTime;

    private String remark;

    private Boolean isDeleted;

    private Integer itemCount;

    private Integer handItemCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Date getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(Date drawTime) {
        this.drawTime = drawTime;
    }

    public Date getHandTime() {
        return handTime;
    }

    public void setHandTime(Date handTime) {
        this.handTime = handTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getHandItemCount() {
        return handItemCount;
    }

    public void setHandItemCount(Integer handItemCount) {
        this.handItemCount = handItemCount;
    }
}