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

    /**
     * 特殊的用户权限过滤
     */
    public Set<String> filterMenus(ApproverTypeBean approverTypeBean, Set<String> userRoles, Set<String> userPermissions) {

        if (userRoles.contains(SystemConstants.ROLE_CADRE)) {
            Cadre cadre = CmTag.getCadreByUserId(id);

            //考察对象和离任中层干部不可以看到因私出国申请，现任干部和离任校领导可以
            if(cadre==null || (cadre.getStatus() != SystemConstants.CADRE_STATUS_MIDDLE
                    && cadre.getStatus() != SystemConstants.CADRE_STATUS_LEADER
                    && cadre.getStatus() != SystemConstants.CADRE_STATUS_LEADER_LEAVE)){
                userPermissions.remove("abroad:user"); // 因私出国境申请（干部目录）
                userPermissions.remove("userApplySelf:*"); // 申请因私出国境（干部）
                userPermissions.remove("userPassportDraw:*"); // 申请使用证件（干部）
                userPermissions.remove("userPassportApply:*"); // 因私出国境证件（干部）
            }

            if (approverTypeBean != null && cadre!= null) {
                MetaType leaderPostType = CmTag.getMetaTypeByCode("mt_leader");
                if (cadre.getPostId() != null && cadre.getPostId().intValue() == leaderPostType.getId()) {
                    // 没有职务属性或干部的职务属性为校领导的，没有(userApplySelf:*， userPassportDraw:*)
                    userPermissions.remove("userApplySelf:*");
                    userPermissions.remove("userPassportDraw:*");
                }
            }

            // 没有审批权限的干部，没有（abroad:admin（目录）, applySelf:approvalList)
            if (cadre==null || (cadre.getStatus() != SystemConstants.CADRE_STATUS_MIDDLE
                    && cadre.getStatus() != SystemConstants.CADRE_STATUS_LEADER) || approverTypeBean == null ||
                    !(approverTypeBean.getMainPostUnitIds().size()>0
                            || approverTypeBean.isManagerLeader()
                            || approverTypeBean.isApprover())) {

                userPermissions.remove("applySelf:approvalList"); // 因私出国境审批
                if (!userRoles.contains(SystemConstants.ROLE_CADREADMIN)) {
                    // 干部管理员 需要目录，普通干部不需要
                    userPermissions.remove("abroad:admin"); // 因私出国境审批（目录）
                }
            }

            // 非干部管理员账号如果有直接修改本人干部信息的权限，则不能看到“干部信息修改申请”菜单
            boolean hasDirectModifyCadreAuth = CmTag.hasDirectModifyCadreAuth(cadre.getId());
            if(!userRoles.contains(SystemConstants.ROLE_CADREADMIN) && hasDirectModifyCadreAuth){

                userPermissions.remove("modifyCadreInfo:menu");
            }
        }

        return userPermissions;
    }

    public Set<String> getRoles() {

        if (roles == null) roles = CmTag.findRoles(username);
        return roles;
    }

    public Set<String> getPermissions() {

        if (permissions == null) {
            Set<String> _p = CmTag.findPermissions(username);
            Set<String> _permissions = new HashSet<>(); /// 拷贝， 防止缓存被篡改
            _permissions.addAll(_p);
            _permissions = filterMenus(getApproverTypeBean(), getRoles(), _permissions);
            permissions = _permissions;
        }
        return permissions;
    }

    public ApproverTypeBean getApproverTypeBean() {

        if (approverTypeBean == null) approverTypeBean = CmTag.getApproverTypeBean(id);
        return approverTypeBean;
    }

}
