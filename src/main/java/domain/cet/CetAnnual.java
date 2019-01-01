package domain.cet;

import java.io.Serializable;

public class CetAnnual implements Serializable {
    private Integer id;

    private Integer year;

    private Integer traineeTypeId;

    private Integer objCount;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTraineeTypeId() {
        return traineeTypeId;
    }

    public void setTraineeTypeId(Integer traineeTypeId) {
        this.traineeTypeId = traineeTypeId;
    }

    public Integer getObjCount() {
        return objCount;
    }

    public void setObjCount(Integer objCount) {
        this.objCount = objCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}