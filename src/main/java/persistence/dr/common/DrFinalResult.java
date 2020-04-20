package persistence.dr.common;

import domain.dr.DrOnlinePostView;
import domain.dr.DrOnlinePostViewExample;
import persistence.dr.DrOnlinePostViewMapper;
import sys.tags.CmTag;

import java.util.List;

public class DrFinalResult {

    public DrOnlinePostView getPost(){

        DrOnlinePostViewMapper drOnlinePostViewMapper = CmTag.getBean(DrOnlinePostViewMapper.class);
        DrOnlinePostViewExample example = new DrOnlinePostViewExample();
        example.createCriteria().andIdEqualTo(postId);
        List<DrOnlinePostView> posts = drOnlinePostViewMapper.selectByExample(example);

        return posts.get(0);
    }

    public Integer onlineId;//批次
    public Integer postId;//推荐职务
    public String candidate;//推荐人姓名
    public Integer options;//票数
    public Integer pubCounts;//发布数量
    public Integer finishCounts;//完成数量
    public String scoreRate;//得票比率

    public Integer getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public Integer getOptions() {
        return options;
    }

    public void setOptions(Integer options) {
        this.options = options;
    }

    public Integer getPubCounts() {
        return pubCounts;
    }

    public void setPubCounts(Integer pubCounts) {
        this.pubCounts = pubCounts;
    }

    public Integer getFinishCounts() {
        return finishCounts;
    }

    public void setFinishCounts(Integer finishCounts) {
        this.finishCounts = finishCounts;
    }

    public String getScoreRate() {
        return scoreRate;
    }

    public void setScoreRate(String scoreRate) {
        this.scoreRate = scoreRate;
    }

    @Override
    public String toString() {
        return "DrFinalResult{" +
                "onlineId=" + onlineId +
                ", postId=" + postId +
                ", candidate='" + candidate + '\'' +
                ", options=" + options +
                ", pubCounts=" + pubCounts +
                ", finishCounts=" + finishCounts +
                ", scoreRate='" + scoreRate + '\'' +
                '}';
    }
}
