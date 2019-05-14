<%@ page import="domain.sys.SysUser" %>
<%@ page import="domain.sys.SysUserView" %>
<%@ page import="persistence.sys.SysUserMapper" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.Date" %>
<%@ page import="service.global.CacheService" %>
<%@ page import="org.springframework.aop.support.AopUtils" %>
<%@ page import="service.global.CacheHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    SysUserService sysUserService = CmTag.getBean(SysUserService.class);
    CacheHelper cacheHelper = CmTag.getBean(CacheHelper.class);
    SysUserMapper sysUserMapper = CmTag.getBean(SysUserMapper.class);

    SysUserView sysUser = sysUserService.findByUsername("zzbgz");

    SysUser _sysUser = new SysUser();
    _sysUser.setId(sysUser.getId());
    _sysUser.setCreateTime(new Date());
    sysUserService.updateByPrimaryKeySelective(_sysUser);


    cacheHelper.clearUserCache(sysUser);
    System.out.println("testCache.jsp System.currentTimeMillis() = " + System.currentTimeMillis());

    SysUserView sysUser2 = sysUserService.findByUsername("zzbgz");



    SysUser sysUser3 = sysUserMapper.selectByPrimaryKey(sysUser.getId());
    out.write(sysUser.getCreateTime() + "=====" + sysUser2.getCreateTime() + "====" + sysUser3.getCreateTime());
%>
</body>
</html>
