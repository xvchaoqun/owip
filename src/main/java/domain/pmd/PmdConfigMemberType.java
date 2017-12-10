package domain.pmd;

import persistence.pmd.PmdNormMapper;
import sys.tags.CmTag;

import java.io.Serializable;

public class PmdConfigMemberType implements Serializable {

    public PmdNorm getPmdNorm(){

        PmdNormMapper pmdNormMapper = CmTag.getBean(PmdNormMapper.class);
        return pmdNormMapper.selectByPrimaryKey(normId);
    }

    private Integer id;

    private Byte type;

    private String name;

    private Integer normId;

    private Boolean isAuto;

    private Integer sortOrder;

    private Boolean isDeleted;

    private String remark;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getNormId() {
        return normId;
    }

    public void setNormId(Integer normId) {
        this.normId = normId;
    }

    public Boolean getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(Boolean isAuto) {
        this.isAuto = isAuto;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}