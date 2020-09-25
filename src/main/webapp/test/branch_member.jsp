<%@ page import="domain.party.*" %>
<%@ page import="persistence.party.BranchMemberMapper" %>
<%@ page import="service.party.BranchMemberService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>支委委员兼职数据合并处理</title>
</head>
<body>
<%
    BranchMemberService BranchMemberService = CmTag.getBean(BranchMemberService.class);
    BranchMemberMapper branchMemberMapper = CmTag.getBean(BranchMemberMapper.class);

    List<BranchMember> branchMembers = branchMemberMapper.selectByExample(new BranchMemberExample());

    for (BranchMember branchMember : branchMembers) {
        BranchMemberExample example = new BranchMemberExample();
        example.createCriteria().andGroupIdEqualTo(branchMember.getGroupId())
                .andUserIdEqualTo(branchMember.getUserId()).andIsHistoryEqualTo(false);
        List<BranchMember> oldRecords = branchMemberMapper.selectByExample(example);
        if(oldRecords.size()>1){
            Set<String> types = new HashSet<>();
            for (BranchMember oldRecord : oldRecords) {
                types.add(oldRecord.getTypes());
            }
            branchMember.setId(null);
            branchMember.setTypes(StringUtils.join(types,","));

            BranchMemberService.insertSelective(branchMember,branchMember.getIsAdmin());
            Integer[] delIds = oldRecords.stream().map(BranchMember::getId).collect(Collectors.toList())
                    .stream().toArray(Integer[]::new);
            BranchMemberService.batchDel(delIds);
        }
    }
%>
</body>
</html>
