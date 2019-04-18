package domain.member;

import java.io.Serializable;

public class ApplySn implements Serializable {
    private Long sn;

    private Integer rangeId;

    private Boolean isUsed;

    private static final long serialVersionUID = 1L;

    public Long getSn() {
        return sn;
    }

    public void setSn(Long sn) {
        this.sn = sn;
    }

    public Integer getRangeId() {
        return rangeId;
    }

    public void setRangeId(Integer rangeId) {
        this.rangeId = rangeId;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }
}