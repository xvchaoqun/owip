package sys.spring;

public class UserRes {

    String res; // 资源
    Integer signUserId; // 资源签名人（当前登录用户ID）

    Integer authUserId; // 资源使用人
    String permissions; // 资源使用权限
    String method; // 资源使用权限方法
    String params; // 资源使用权限方法的参数

    public String getRes() {
        return res;
    }

    public UserRes setRes(String res) {
        this.res = res;
        return this;
    }

    public Integer getSignUserId() {
        return signUserId;
    }

    public UserRes setSignUserId(Integer signUserId) {
        this.signUserId = signUserId;
        return this;
    }

    public Integer getAuthUserId() {
        return authUserId;
    }

    public UserRes setAuthUserId(Integer authUserId) {
        this.authUserId = authUserId;
        return this;
    }

    public String getPermissions() {
        return permissions;
    }

    public UserRes setPermissions(String permissions) {
        this.permissions = permissions;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public UserRes setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getParams() {
        return params;
    }

    public UserRes setParams(String params) {
        this.params = params;
        return this;
    }

    @Override
    public String toString() {
        return "UserRes{" +
                "res='" + res + '\'' +
                ", signUserId=" + signUserId +
                ", authUserId=" + authUserId +
                ", permissions='" + permissions + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
