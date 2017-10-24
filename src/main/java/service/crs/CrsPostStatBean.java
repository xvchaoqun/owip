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
    private Integer statFirstUserId;
    private Integer statSecondUserId;
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

    public Integer getStatFirstUserId() {
        return statFirstUserId;
    }

    public void setStatFirstUserId(Integer statFirstUserId) {
        this.statFirstUserId = statFirstUserId;
    }

    public Integer getStatSecondUserId() {
        return statSecondUserId;
    }

    public void setStatSecondUserId(Integer statSecondUserId) {
        this.statSecondUserId = statSecondUserId;
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
