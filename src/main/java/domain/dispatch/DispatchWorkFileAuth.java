package domain.dispatch;

import java.io.Serializable;

public class DispatchWorkFileAuth implements Serializable {
    private Integer id;

    private Integer workFileId;

    private Integer postType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkFileId() {
        return workFileId;
    }

    public void setWorkFileId(Integer workFileId) {
        this.workFileId = workFileId;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }
}