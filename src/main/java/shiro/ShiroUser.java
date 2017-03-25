package shiro;

import bean.ApproverTypeBean;
import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.cadre.Cadre;
import domain.base.MetaType;
import sys.constants.SystemConstants;
import sys.tags.CmTag;

import java.io.Serializable;
import java.util.HashSet;
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
    @JsonIgnore
    private transient Set<String> permissions;

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



    public Set<String> getRoles() {

        if (roles == null) roles = CmTag.findRoles(username);
        return roles;
    }

    public Set<String> getPermissions() {

        /*if (permissions == null) {
            Set<String> _p = CmTag.findPermissions(username);
            Set<String> _permissions = new HashSet<>(); /// 拷贝， 防止缓存被篡改
            _permissions.addAll(_p);
            _permissions = filterMenus(getApproverTypeBean(), getRoles(), _permissions);
            permissions = _permissions;
        }*/
        return CmTag.findPermissions(username);
    }

    public ApproverTypeBean getApproverTypeBean() {

        if (approverTypeBean == null) approverTypeBean = CmTag.getApproverTypeBean(id);
        return approverTypeBean;
    }

}
