package domain.sc.scSubsidy;

import domain.dispatch.Dispatch;
import persistence.dispatch.DispatchMapper;
import persistence.sc.scSubsidy.ScSubsidyMapper;
import sys.tags.CmTag;

import java.io.Serializable;

public class ScSubsidyDispatch implements Serializable {

    public ScSubsidy getSubsidy(){

        return CmTag.getBean(ScSubsidyMapper.class).selectByPrimaryKey(subsidyId);
    }

    public Dispatch getDispatch(){

        return CmTag.getBean(DispatchMapper.class).selectByPrimaryKey(dispatchId);
    }

    private Integer id;

    private Integer subsidyId;

    private Integer dispatchId;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubsidyId() {
        return subsidyId;
    }

    public void setSubsidyId(Integer subsidyId) {
        this.subsidyId = subsidyId;
    }

    public Integer getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Integer dispatchId) {
        this.dispatchId = dispatchId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}