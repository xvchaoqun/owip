package domain.train;

import sys.tags.CmTag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrainEvaTable implements Serializable {

    // 返回有序的评估内容列表，即一级指标列表
    public List<TrainEvaNorm> getNorms(){

        Map<Integer, TrainEvaNorm> trainEvaTableNorms = CmTag.getTrainEvaNorms(id);
        return new ArrayList<>(trainEvaTableNorms.values());
    }

    // 返回有序的二级指标列表（如果评估内容下无二级指标，则该评估内容也包含在此列表内）
    public List<TrainEvaNorm> getNormList(){

        List<TrainEvaNorm> normList = new ArrayList<>();
        List<TrainEvaNorm> norms = getNorms();
        for (TrainEvaNorm norm : norms) {
            if(norm.getNormNum()>0){
                normList.addAll(norm.getSubNorms());
            }else{
                normList.add(norm);
            }
        }
        return normList;
    }

    public List<TrainEvaRank> getRanks(){

        Map<Integer, TrainEvaRank> trainEvaTableRanks = CmTag.getTrainEvaRanks(id);
        return new ArrayList<>(trainEvaTableRanks.values());
    }

    public int getNormNum(){

        int normNum = 0;
        for (TrainEvaNorm trainEvaNorm : getNorms()) {
            normNum += trainEvaNorm.getNormNum()>0?trainEvaNorm.getNormNum():1;
        }

        return normNum;
    }

    public int getRankNum(){

        return getRanks().size();
    }

    private Integer id;

    private String name;

    private String remark;

    private Integer sortOrder;

    private Byte status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}