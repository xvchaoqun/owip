package domain.sc.scBorder;

import java.io.Serializable;

public class ScBorderItem implements Serializable {
    private Integer id;

    private Integer borderId;

    private Byte type;

    private Integer cadreId;

    private String title;

    private Integer adminLevel;

    private String dispatchCadreIds;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBorderId() {
        return borderId;
    }

    public void setBorderId(Integer borderId) {
        this.borderId = borderId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getDispatchCadreIds() {
        return dispatchCadreIds;
    }

    public void setDispatchCadreIds(String dispatchCadreIds) {
        this.dispatchCadreIds = dispatchCadreIds == null ? null : dispatchCadreIds.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}