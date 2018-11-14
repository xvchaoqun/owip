package domain.sc.scCommittee;

import java.io.Serializable;
import java.util.Date;

public class ScCommitteeTopicCadre implements Serializable {
    private Integer id;

    private Integer topicId;

    private Integer cadreId;

    private String originalPost;

    private Date originalPostTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public String getOriginalPost() {
        return originalPost;
    }

    public void setOriginalPost(String originalPost) {
        this.originalPost = originalPost == null ? null : originalPost.trim();
    }

    public Date getOriginalPostTime() {
        return originalPostTime;
    }

    public void setOriginalPostTime(Date originalPostTime) {
        this.originalPostTime = originalPostTime;
    }
}