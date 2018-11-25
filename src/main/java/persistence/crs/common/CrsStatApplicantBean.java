package persistence.crs.common;

/**
 * Created by lm on 2017/10/25.
 */
public class CrsStatApplicantBean {

    private Integer userId;
    private Integer cadreId;
    private String realname;
    private String code;
    private String title;
    private Integer totalCount;
    private Integer passCount;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getCadreId() {
        return cadreId;
    }
    
    public void setCadreId(Integer cadreId) {
        this.cadreId = cadreId;
    }
    
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPassCount() {
        return passCount;
    }

    public void setPassCount(Integer passCount) {
        this.passCount = passCount;
    }
}
