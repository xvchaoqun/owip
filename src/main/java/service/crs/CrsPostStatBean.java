package service.crs;

import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2017/8/16.
 */
public class CrsPostStatBean {

    private int postId;
    private Integer statExpertCount;
    private Integer statGiveCount;
    private Integer statBackCount;
    private Integer firstUserId;
    private Integer secondUserId;
    private Date statDate;
    private List<CrsApplicatStatBean> applicatStatBeans;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Integer getStatExpertCount() {
        return statExpertCount;
    }

    public void setStatExpertCount(int statExpertCount) {
        this.statExpertCount = statExpertCount;
    }

    public Integer getStatGiveCount() {
        return statGiveCount;
    }

    public void setStatGiveCount(int statGiveCount) {
        this.statGiveCount = statGiveCount;
    }

    public Integer getStatBackCount() {
        return statBackCount;
    }

    public void setStatBackCount(int statBackCount) {
        this.statBackCount = statBackCount;
    }

    public Integer getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Integer secondUserId) {
        this.secondUserId = secondUserId;
    }

    public Integer getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(Integer firstUserId) {
        this.firstUserId = firstUserId;
    }

    public Date getStatDate() {
        return statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    public List<CrsApplicatStatBean> getApplicatStatBeans() {
        return applicatStatBeans;
    }

    public void setApplicatStatBeans(List<CrsApplicatStatBean> applicatStatBeans) {
        this.applicatStatBeans = applicatStatBeans;
    }
}
