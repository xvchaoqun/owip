package domain.dr;

import domain.unit.UnitPost;
import persistence.dr.DrOnlineMapper;
import persistence.unit.UnitPostMapper;
import sys.tags.CmTag;

import java.io.Serializable;

public class DrOnlinePost implements Serializable {

    public DrOnline getDrOnline(){
        DrOnlineMapper drOnlineMapper = CmTag.getBean(DrOnlineMapper.class);
        DrOnline drOnline = drOnlineMapper.selectByPrimaryKey(onlineId);
        return drOnline;
    }

    public UnitPost getUnitPost(){
        UnitPostMapper unitPostMapper = CmTag.getBean(UnitPostMapper.class);
        return unitPostMapper.selectByPrimaryKey(unitPostId);
    }

    private Integer id;

    private Integer unitPostId;

    private String name;

    private Integer onlineId;

    private String candidates;

    private Integer headCount;

    private Integer minCount;

    private Integer sortOrder;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUnitPostId() {
        return unitPostId;
    }

    public void setUnitPostId(Integer unitPostId) {
        this.unitPostId = unitPostId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public String getCandidates() {
        return candidates;
    }

    public void setCandidates(String candidates) {
        this.candidates = candidates == null ? null : candidates.trim();
    }

    public Integer getHeadCount() {
        return headCount;
    }

    public void setHeadCount(Integer headCount) {
        this.headCount = headCount;
    }

    public Integer getMinCount() {
        return minCount;
    }

    public void setMinCount(Integer minCount) {
        this.minCount = minCount;
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