<%@ page import="domain.party.PartyMember" %>
<%@ page import="domain.party.PartyMemberExample" %>
<%@ page import="persistence.party.BranchMemberMapper" %>
<%@ page import="persistence.party.PartyMemberMapper" %>
<%@ page import="service.cadre.CadreService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.party.BranchMemberExample" %>
<%@ page import="domain.party.BranchMember" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>支委委员、党委委员添加干部档案</title>
</head>
<body>
<%
    CadreService cadreService = CmTag.getBean(CadreService.class);
    PartyMemberMapper partyMemberMapper = CmTag.getBean(PartyMemberMapper.class);
    List<PartyMember> partyMembers = partyMemberMapper.selectByExample(new PartyMemberExample());
    for (PartyMember partyMember : partyMembers) {
       if(CmTag.getCadre(partyMember.getUserId())==null) {
            cadreService.addTempCadre(partyMember.getUserId());
        }
    }

    BranchMemberMapper branchMemberMapper = CmTag.getBean(BranchMemberMapper.class);
    List<BranchMember> branchMembers = branchMemberMapper.selectByExample(new BranchMemberExample());
    for (BranchMember branchMember : branchMembers) {
        if(CmTag.getCadre(branchMember.getUserId())==null) {
            cadreService.addTempCadre(branchMember.getUserId());
        }
    }
%>
</body>
</html>
