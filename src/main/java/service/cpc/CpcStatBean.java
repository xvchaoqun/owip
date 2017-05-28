package service.cpc;

/**
 * Created by fafa on 2017/5/27.
 */
public class CpcStatBean {

    private Integer adminLevelId;
    private boolean isMainPost;
    private long num;

    public Integer getAdminLevelId() {
        return adminLevelId;
    }

    public void setAdminLevelId(Integer adminLevelId) {
        this.adminLevelId = adminLevelId;
    }

    public boolean isMainPost() {
        return isMainPost;
    }

    public void setIsMainPost(boolean isMainPost) {
        this.isMainPost = isMainPost;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }
}
