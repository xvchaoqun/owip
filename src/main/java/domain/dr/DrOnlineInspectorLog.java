package domain.dr;

import org.apache.commons.lang3.StringUtils;
import persistence.dr.DrOnlineInspectorTypeMapper;
import persistence.dr.DrOnlinePostMapper;
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
    DrOnlinePostMapper drOnlinePostMapper = CmTag.getBean(DrOnlinePostMapper.class);
    public  List<DrOnlinePost> getdrOnlinePost(){
        List<DrOnlinePost> posts = new ArrayList<>();
        if (StringUtils.isNotBlank(postIds)){
            for (String postId : postIds.split(",")){
                DrOnlinePostExample example = new DrOnlinePostExample();
                example.createCriteria().andIdEqualTo(Integer.valueOf(postId));
                List<DrOnlinePost> drOnlinePosts =drOnlinePostMapper.selectByExample(example);
                if (drOnlinePosts != null && drOnlinePosts.size() > 0)
                    posts.add(drOnlinePosts.get(0));
            }
        }
        return posts;
    }
    private Integer id;

    private Integer onlineId;

    private Integer typeId;

    private String postIds;

    private Integer unitId;

    private String remark;

    private Integer totalCount;

    private Integer finishCount;

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