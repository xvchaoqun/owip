package domain.pmd;

import java.io.Serializable;
import java.math.BigDecimal;

public class PmdOrderItem implements Serializable {
    private Integer id;

    private String sn;

    private Integer memberId;

    private BigDecimal duePay;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getDuePay() {
        return duePay;
    }

    public void setDuePay(BigDecimal duePay) {
        this.duePay = duePay;
    }
}