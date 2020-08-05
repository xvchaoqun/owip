package sys.tags;

public class AuthBean {

    Integer signUserId; // 资源签名人（当前登录用户ID）

    Integer authUserId; // 资源使用人
    String permissions; // 资源使用权限
    String method; // 资源使用权限方法
    String params; // 资源使用权限方法的参数

    public Integer getSignUserId() {
        return signUserId;
    }

    public AuthBean setSignUserId(Integer signUserId) {
        this.signUserId = signUserId;
        return this;
    }

    public Integer getAuthUserId() {
        return authUserId;
    }

    public AuthBean setAuthUserId(Integer authUserId) {
        this.authUserId = authUserId;
        return this;
    }

    public String getPermissions() {
        return permissions;
    }

    public AuthBean setPermissions(String permissions) {
        this.permissions = permissions;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public AuthBean setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getParams() {
        return params;
    }

    public AuthBean setParams(String params) {
        this.params = params;
        return this;
    }

    @Override
    public String toString() {

        return "AuthBean{" +
                "signUserId=" + signUserId +
                ", authUserId=" + authUserId +
                ", permissions='" + permissions + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
