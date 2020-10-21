<%@ page import="sys.tags.CmTag" %>
<%@ page import="service.pmd.PmdMonthService" %>
<%@ page import="persistence.pmd.PmdMemberMapper" %>
<%@ page import="domain.pmd.PmdMemberExample" %>
<%@ page import="sys.constants.PmdConstants" %>
<%@ page import="domain.pmd.PmdMember" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    // 批量更新学生的缴费金额为0.2

    PmdMonthService pmdMonthService = CmTag.getBean(PmdMonthService.class);
    PmdMemberMapper pmdMemberMapper = CmTag.getBean(PmdMemberMapper.class);

    PmdMemberExample example = new PmdMemberExample();
    example.createCriteria().andHasPayEqualTo(false).andTypeEqualTo(PmdConstants.PMD_MEMBER_TYPE_STUDENT);

    List<PmdMember> pmdMembers = pmdMemberMapper.selectByExample(example);

    for (PmdMember pmdMember : pmdMembers) {

        pmdMonthService.addOrResetPmdMember(pmdMember.getUserId(), pmdMember.getId());
    }

%>
</body>
</html>
