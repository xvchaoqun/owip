package domain.sc.scMatter;

import java.io.Serializable;

public class ScMatterAccessItem implements Serializable {
    private Integer id;

    private Integer accessId;

    private Integer matterItemId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccessId() {
        return accessId;
    }

    public void setAccessId(Integer accessId) {
        this.accessId = accessId;
    }

    public Integer getMatterItemId() {
        return matterItemId;
    }

    public void setMatterItemId(Integer matterItemId) {
        this.matterItemId = matterItemId;
    }
}