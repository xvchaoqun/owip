package shiro;

import persistence.abroad.common.ApproverTypeBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by fafa on 2015/8/18.
 */
public class ShiroUser implements Serializable {

    private Integer id;
    private String username;
    private String code;
    private String realname;
    private Byte type;

    private transient Set<String> roles;
    // 网站权限
    @JsonIgnore
    private transient Set<String> permissions;
    // 手机端权限
    @JsonIgnore
    private transient Set<String> mPermissions;

    @JsonIgnore
    private transient ApproverTypeBean approverTypeBean; // 干部审批权限

    public ShiroUser(Integer id, String username, String code, String realname, Byte type) {
        this.id = id;
        this.username = username;
        this.code = code;
        this.realname = realname;
        this.type = type;
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


    public void setRoles(Set<String> roles) {

        this.roles = roles;
    }
    public Set<String> getRoles() {

        if (roles == null) roles = CmTag.findRoles(username);
        return roles;
    }

    public Set<String> getPermissions() {

        return CmTag.findPermissions(username, false);
    }

    public Set<String> getmPermissions() {

        return CmTag.findPermissions(username, true);
    }

    public ApproverTypeBean getApproverTypeBean() {

        if (approverTypeBean == null) approverTypeBean = CmTag.getApproverTypeBean(id);
        return approverTypeBean;
    }

}
