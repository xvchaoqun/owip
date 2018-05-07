package persistence.cet.common;

import domain.cet.CetDiscussGroup;

import java.math.BigDecimal;

/**
 * Created by lm on 2018/5/7.
 */
public class ICetDiscussGroup extends CetDiscussGroup {

    private Integer planId;
    private BigDecimal period;
    private Integer userId;
    private Boolean isFinished;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public BigDecimal getPeriod() {
        return period;
    }

    public void setPeriod(BigDecimal period) {
        this.period = period;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }
}
