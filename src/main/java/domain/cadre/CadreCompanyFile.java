package domain.cadre;

import domain.dispatch.DispatchWorkFile;
import persistence.dispatch.DispatchWorkFileMapper;
import sys.tags.CmTag;

import java.io.Serializable;

public class CadreCompanyFile implements Serializable {

    public DispatchWorkFile getDwf(){
        if(dispatchWorkFileId==null) return null;
        return CmTag.getBean(DispatchWorkFileMapper.class).selectByPrimaryKey(dispatchWorkFileId);
    }
    private Integer id;

    private Boolean type;

    private Integer dispatchWorkFileId;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public Integer getDispatchWorkFileId() {
        return dispatchWorkFileId;
    }

    public void setDispatchWorkFileId(Integer dispatchWorkFileId) {
        this.dispatchWorkFileId = dispatchWorkFileId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}