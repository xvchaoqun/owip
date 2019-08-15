package persistence.pm;

import java.math.BigDecimal;

/**
 * Created by lm on 2018/1/10.
 */
public class PmQuarterCountBean {

    private Integer branchCount;

    private Integer isExcludeCount;

    private Integer cludeCount;


    public Integer getBranchCount() {
        return branchCount;
    }

    public void setBranchCount(Integer branchCount) {
        this.branchCount = branchCount;
    }

    public Integer getIsExcludeCount() {
        return isExcludeCount;
    }

    public void setIsExcludeCount(Integer isExcludeCount) {
        this.isExcludeCount = isExcludeCount;
    }

    public Integer getCludeCount() {
        return cludeCount;
    }

    public void setCludeCount(Integer cludeCount) {
        this.cludeCount = cludeCount;
    }
}
