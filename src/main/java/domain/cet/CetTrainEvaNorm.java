package domain.cet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

public class CetTrainEvaNorm implements Serializable {

    private List<CetTrainEvaNorm> subNorms;

    @JsonIgnore
    private CetTrainEvaNorm topNorm;
    @JsonIgnore
    private int topIndex;
    @JsonIgnore
    private int subIndex;

    public List<CetTrainEvaNorm> getSubNorms() {
        return subNorms;
    }

    public void setSubNorms(List<CetTrainEvaNorm> subNorms) {
        this.subNorms = subNorms;
    }

    public CetTrainEvaNorm getTopNorm() {
        return topNorm;
    }

    public void setTopNorm(CetTrainEvaNorm topNorm) {
        this.topNorm = topNorm;
    }

    public int getTopIndex() {
        return topIndex;
    }

    public void setTopIndex(int topIndex) {
        this.topIndex = topIndex;
    }

    public int getSubIndex() {
        return subIndex;
    }

    public void setSubIndex(int subIndex) {
        this.subIndex = subIndex;
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