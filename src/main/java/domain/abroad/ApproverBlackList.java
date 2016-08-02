package domain.abroad;

import java.io.Serializable;

public class ApproverBlackList implements Serializable {
    private Integer id;

    private Integer cadreId;

    private Integer approverTypeId;

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

    public Integer getApproverTypeId() {
        return approverTypeId;
    }

    public void setApproverTypeId(Integer approverTypeId) {
        this.approverTypeId = approverTypeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}