package domain;

import java.io.Serializable;
import java.util.Date;

public class CadreSubWork implements Serializable {
    private Integer id;

    private Integer cadreId;

    private String post;

    private Date postTime;

    private String dispatchs;

    private Date startTime;

    private Integer dispatchCadreId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCadreId() {
        return cadreId;
    }

    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getDispatchs() {
        return dispatchs;
    }

    public void setDispatchs(String dispatchs) {
        this.dispatchs = dispatchs == null ? null : dispatchs.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getDispatchCadreId() {
        return dispatchCadreId;
    }

    public void setDispatchCadreId(Integer dispatchCadreId) {
        this.dispatchCadreId = dispatchCadreId;
    }
}