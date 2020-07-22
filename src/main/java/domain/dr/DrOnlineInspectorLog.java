package domain.dr;

import domain.unit.UnitPost;
import org.apache.commons.lang3.StringUtils;
import persistence.dr.DrOnlineInspectorTypeMapper;
import persistence.unit.UnitPostMapper;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrOnlineInspectorLog implements Serializable {
    public DrOnlineInspectorType getInspectorType(){
        DrOnlineInspectorTypeMapper inspectorTypeMapper = CmTag.getBean(DrOnlineInspectorTypeMapper.class);
        return inspectorTypeMapper.selectByPrimaryKey(typeId);
    }
    UnitPostMapper unitPostMapper = CmTag.getBean(UnitPostMapper.class);
    public List<UnitPost> getUnitPosts(){
        List<UnitPost> unitPosts = new ArrayList<>();
        if (StringUtils.isNotBlank(postIds)){
            for (String postId : postIds.split(",")){
                unitPosts.add(unitPostMapper.selectByPrimaryKey(Integer.valueOf(postId)));
            }
        }
        return unitPosts;
    }
    private Integer id;

    private Integer onlineId;

    private Integer typeId;

    private String postIds;

    private Integer unitId;

    private String remark;

    private Integer totalCount;

    private Integer finishCount;

    private Integer pubCount;

    private Date createTime;

    private Integer exportCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getPostIds() {
        return postIds;
    }

    public void setPostIds(String postIds) {
        this.postIds = postIds == null ? null : postIds.trim();
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    public Integer getPubCount() {
        return pubCount;
    }

    public void setPubCount(Integer pubCount) {
        this.pubCount = pubCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getExportCount() {
        return exportCount;
    }

    public void setExportCount(Integer exportCount) {
        this.exportCount = exportCount;
    }
}