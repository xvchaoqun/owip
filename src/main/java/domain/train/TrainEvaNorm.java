package domain.train;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

public class TrainEvaNorm implements Serializable {

    private List<TrainEvaNorm> subNorms;

    @JsonIgnore
    private TrainEvaNorm topNorm;

    public List<TrainEvaNorm> getSubNorms() {
        return subNorms;
    }

    public void setSubNorms(List<TrainEvaNorm> subNorms) {
        this.subNorms = subNorms;
    }

    public TrainEvaNorm getTopNorm() {
        return topNorm;
    }

    public void setTopNorm(TrainEvaNorm topNorm) {
        this.topNorm = topNorm;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private Integer id;

    private Integer evaTableId;

    private Integer fid;

    private Integer normNum;

    private String name;

    private String remark;

    private Integer sortOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEvaTableId() {
        return evaTableId;
    }

    public void setEvaTableId(Integer evaTableId) {
        this.evaTableId = evaTableId;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getNormNum() {
        return normNum;
    }

    public void setNormNum(Integer normNum) {
        this.normNum = normNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}