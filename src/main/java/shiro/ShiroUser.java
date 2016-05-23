package shiro;

import bean.ApproverTypeBean;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

/**
 * Created by fafa on 2015/8/18.
 */
public class ShiroUser {

    private Integer id;
    private String username;
    private String code;
    private String realname;
    private Byte type;

    private Set<String> roles;
    @JsonIgnore
    private Set<String> permissions;

    @JsonIgnore
    private ApproverTypeBean approverTypeBean; // 干部审批权限

    public ShiroUser(Integer id, String username, String code, String realname, Byte type, Set<String> roles, Set<String> permissions, ApproverTypeBean approverTypeBean) {
        this.id = id;
        this.username = username;
        this.code = code;
        this.realname = realname;
        this.type = type;
        this.roles = roles;
        this.permissions = permissions;
        this.approverTypeBean = approverTypeBean;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public ApproverTypeBean getApproverTypeBean() {
        return approverTypeBean;
    }

    public void setApproverTypeBean(ApproverTypeBean approverTypeBean) {
        this.approverTypeBean = approverTypeBean;
    }
}
