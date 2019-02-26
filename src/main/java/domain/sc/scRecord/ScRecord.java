package domain.sc.scRecord;

import java.io.Serializable;

public class ScRecord implements Serializable {

    public String getCode(){
        return "纪实〔"+seq+"〕号";
    }

    private Integer id;

    private Short year;

    private String seq;

    private Integer motionId;

    private Byte status;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq == null ? null : seq.trim();
    }

    public Integer getMotionId() {
        return motionId;
    }

    public void setMotionId(Integer motionId) {
        this.motionId = motionId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}