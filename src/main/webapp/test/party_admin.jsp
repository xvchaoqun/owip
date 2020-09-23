<%@ page import="domain.party.*" %>
<%@ page import="domain.sys.SysUserView" %>
<%@ page import="persistence.party.BranchMemberGroupMapper" %>
<%@ page import="persistence.party.BranchMemberMapper" %>
<%@ page import="persistence.party.PartyMemberGroupMapper" %>
<%@ page import="persistence.party.PartyMemberMapper" %>
<%@ page import="persistence.party.common.IPartyMapper" %>
<%@ page import="service.party.BranchAdminService" %>
<%@ page import="service.party.PartyAdminService" %>
<%@ page import="service.sys.SysUserService" %>
<%@ page import="sys.constants.RoleConstants" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.List" %>
<%@ page import="persistence.party.common.OwAdmin" %>
<%@ page import="org.apache.ibatis.session.RowBounds" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>支委委员、党委委员更新管理员</title>
</head>
<body>
<%
    SysUserService sysUserService = CmTag.getBean(SysUserService.class);
    List<SysUserView> sysUsers = sysUserService.findByRole(RoleConstants.ROLE_PARTYADMIN);
    for (SysUserView sysUser : sysUsers) {
        sysUserService.delRole(sysUser.getUserId(), RoleConstants.ROLE_PARTYADMIN);
    }

    IPartyMapper iPartyMapper = CmTag.getBean(IPartyMapper.class);
    OwAdmin search = new OwAdmin();
    List<OwAdmin> owAdmins = iPartyMapper.selectPartyAdminList(search, new RowBounds());
    for (OwAdmin owAdmin : owAdmins) {
        sysUserService.addRole(owAdmin.getUserId(), RoleConstants.ROLE_PARTYADMIN);
    }

    sysUserService = CmTag.getBean(SysUserService.class);
    sysUsers = sysUserService.findByRole(RoleConstants.ROLE_BRANCHADMIN);
    for (SysUserView sysUser : sysUsers) {
        sysUserService.delRole(sysUser.getUserId(), RoleConstants.ROLE_BRANCHADMIN);
    }

    search = new OwAdmin();
    owAdmins = iPartyMapper.selectBranchAdminList(search, new RowBounds());
    for (OwAdmin owAdmin : owAdmins) {
        sysUserService.addRole(owAdmin.getUserId(), RoleConstants.ROLE_BRANCHADMIN);
    }

    /*PartyMemberMapper partyMemberMapper = CmTag.getBean(PartyMemberMapper.class);
    PartyMemberGroupMapper partyMemberGroupMapper = CmTag.getBean(PartyMemberGroupMapper.class);
    PartyAdminService partyAdminService = CmTag.getBean(PartyAdminService.class);
    List<PartyMember> partyMembers = partyMemberMapper.selectByExample(new PartyMemberExample());
    for (PartyMember partyMember : partyMembers) {

        PartyMemberGroup partyMemberGroup = partyMemberGroupMapper.selectByPrimaryKey(partyMember.getGroupId());

        if(partyMember.getIsAdmin() && !partyMemberGroup.getIsDeleted()){
            partyAdminService.setPartyAdmin(partyMember.getId(), true);
        }else{
            partyAdminService.setPartyAdmin(partyMember.getId(), false);
        }
    }

    sysUserService = CmTag.getBean(SysUserService.class);
    sysUsers = sysUserService.findByRole(RoleConstants.ROLE_BRANCHADMIN);
    for (SysUserView sysUser : sysUsers) {
        sysUserService.delRole(sysUser.getUserId(), RoleConstants.ROLE_BRANCHADMIN);
    }

    BranchMemberMapper branchMemberMapper = CmTag.getBean(BranchMemberMapper.class);
    BranchMemberGroupMapper branchMemberGroupMapper = CmTag.getBean(BranchMemberGroupMapper.class);
    BranchAdminService branchAdminService = CmTag.getBean(BranchAdminService.class);
    List<BranchMember> branchMembers = branchMemberMapper.selectByExample(new BranchMemberExample());
    for (BranchMember branchMember : branchMembers) {

        BranchMemberGroup branchMemberGroup = branchMemberGroupMapper.selectByPrimaryKey(branchMember.getGroupId());

        if(branchMember.getIsAdmin() && !branchMemberGroup.getIsDeleted()){
            branchAdminService.setBranchAdmin(branchMember.getId(), true);
        }else{
            branchAdminService.setBranchAdmin(branchMember.getId(), false);
        }
    }*/
%>
</body>
</html>
