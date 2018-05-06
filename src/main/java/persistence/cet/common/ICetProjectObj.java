package persistence.cet.common;

import domain.cet.CetProjectObj;

import java.math.BigDecimal;

/**
 * Created by lm on 2018/5/6.
 */
public class ICetProjectObj extends CetProjectObj {

    // 已完成学时数
    private BigDecimal finishPeriod;

    public BigDecimal getFinishPeriod() {
        return finishPeriod;
    }

    public void setFinishPeriod(BigDecimal finishPeriod) {
        this.finishPeriod = finishPeriod;
    }
}
