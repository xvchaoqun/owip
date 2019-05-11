<%@ page import="domain.sys.*" %>
<%@ page import="net.sf.ehcache.CacheManager" %>
<%@ page import="org.apache.ibatis.session.RowBounds" %>
<%@ page import="persistence.sys.SysRoleMapper" %>
<%@ page import="persistence.sys.SysUserMapper" %>
<%@ page import="service.sys.SysResourceService" %>
<%@ page import="service.sys.SysRoleService" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page import="sys.constants.SystemConstants" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.*" %>
<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="net.sf.ehcache.Element" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="org.springframework.cache.interceptor.SimpleKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    private Set<SysRole> _findRoles(String username) {

        Set<SysRole> roles = new HashSet<>();
        SysUserService sysUserService = CmTag.getBean(SysUserService.class);
        SysRoleService sysRoleService = CmTag.getBean(SysRoleService.class);
        SysUserMapper sysUserMapper = CmTag.getBean(SysUserMapper.class);
        //SysUser user = findByUsername(username); 此处不可以调用缓存，否则清理了UserPermissions缓存，还要清理用户缓存
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<SysUser> users = sysUserMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (users.size() == 0) return roles;

        SysUser user = users.get(0);
        Set<Integer> roleIds = sysUserService.getUserRoleIdSet(user.getRoleIds());
        Map<Integer, SysRole> roleMap = sysRoleService.findAll();
        for (Integer roleId : roleIds) {
            SysRole role = roleMap.get(roleId);

            System.out.println(role.getRole() + "= " + role.getResourceIds());

            if (role != null)
                roles.add(role);
        }

        return roles;
    }

    // 根据账号查找所拥有的全部资源
    private List<SysResource> findResources(String username, boolean isMobile) {

        List<SysResource> resources = new ArrayList<>();
        Set<SysRole> roles = _findRoles(username);
        List<Integer> resourceIds = new ArrayList<Integer>();

        for (SysRole role : roles) {

            String resourceIdsStr = isMobile ? role.getmResourceIds() : role.getResourceIds();

            //System.out.println(role.getRole() + "= " + resourceIdsStr);

            if (org.apache.commons.lang.StringUtils.isBlank(resourceIdsStr)) continue;

            String[] resourceIdStrs = resourceIdsStr.split(SystemConstants.USER_ROLEIDS_SEPARTOR);
            for (String resourceIdStr : resourceIdStrs) {
                if (org.apache.commons.lang.StringUtils.isEmpty(resourceIdStr)) {
                    continue;
                }
                resourceIds.add(Integer.valueOf(resourceIdStr));
            }
        }

        //System.out.println("resourceIds = " + resourceIds);

        if (resourceIds.size() == 0) return resources;
        SysResourceService sysResourceService = CmTag.getBean(SysResourceService.class);
        Map<Integer, SysResource> resourceMap = sysResourceService.getSortedSysResources(isMobile);
        for (Integer resourceId : resourceIds) {
            SysResource resource = resourceMap.get(resourceId);
            if (resource != null)
                resources.add(resource);
        }
        return resources;
    }

    public SysRole getByRole(String role) {

        SysRoleMapper sysRoleMapper = CmTag.getBean(SysRoleMapper.class);
        SysRoleExample example = new SysRoleExample();
        example.createCriteria().andRoleEqualTo(role);
        List<SysRole> sysRoles = sysRoleMapper.selectByExample(example);
        if (sysRoles.size() > 0) return sysRoles.get(0);
        return null;
    }
%>
<%
    String r = StringUtils.defaultIfBlank(request.getParameter("r"), "partyAdmin");

    SysRoleService sysRoleService = CmTag.getBean(SysRoleService.class);
    Map<Integer, SysRole> roleMap = sysRoleService.findAll();
    for (SysRole role : roleMap.values()) {
        if(StringUtils.equals(role.getRole(), r))
        out.println(role.getRole() + "= " + role.getResourceIds() + "<br/>");
    }
    out.println("==================<br/>");

    SysRoleMapper sysRoleMapper = CmTag.getBean(SysRoleMapper.class);
    SysRoleExample example = new SysRoleExample();
    example.setOrderByClause("sort_order desc");
    List<SysRole> sysRoles = sysRoleMapper.selectByExample(example);
    for (SysRole role : sysRoles) {
        if(StringUtils.equals(role.getRole(), r))
        out.println(role.getRole() + "= " + role.getResourceIds() + "<br/>");
    }
%>
