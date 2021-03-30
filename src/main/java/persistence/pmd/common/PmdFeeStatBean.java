package persistence.pmd.common;

import java.math.BigDecimal;

public class PmdFeeStatBean {

    // PMD_USER_TYPE
    private Byte userType;

    private Integer num;

    private BigDecimal amt;

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }
}
