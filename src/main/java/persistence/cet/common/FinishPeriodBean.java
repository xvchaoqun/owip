package persistence.cet.common;

import java.math.BigDecimal;

/**
 * Created by lm on 2018/5/30.
 */
public class FinishPeriodBean {

    Integer objId;
    BigDecimal period;

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }
}
