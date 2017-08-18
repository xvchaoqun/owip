package service.crs;

/**
 * Created by lm on 2017/8/16.
 */
public class CrsApplicatStatBean {

    private Integer applicantId;
    private Integer firstCount;
    private Integer secondCount;

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public Integer getFirstCount() {
        return firstCount;
    }

    public void setFirstCount(int firstCount) {
        this.firstCount = firstCount;
    }

    public Integer getSecondCount() {
        return secondCount;
    }

    public void setSecondCount(int secondCount) {
        this.secondCount = secondCount;
    }
}
