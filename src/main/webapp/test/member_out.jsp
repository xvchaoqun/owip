<%@ page import="service.member.MemberOutService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="persistence.member.MemberOutMapper" %>
<%@ page import="domain.member.MemberOutExample" %>
<%@ page import="domain.member.MemberOut" %>
<%@ page import="java.util.List" %>
<%@ page import="sys.utils.DateUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    MemberOutService memberOutService = CmTag.getBean(MemberOutService.class);
    MemberOutMapper memberOutMapper = CmTag.getBean(MemberOutMapper.class);

    MemberOutExample example = new MemberOutExample();
    example.createCriteria().andSnIsNull();
    List<MemberOut> memberOuts = memberOutMapper.selectByExample(example);

    for (MemberOut memberOut : memberOuts) {

        MemberOut record = new MemberOut();
        record.setId(memberOut.getId());
        int year = DateUtils.getYear(memberOut.getApplyTime());
        if(year==-1){
            year = DateUtils.getYear(memberOut.getHandleTime());
        }
        record.setYear(year);
        record.setSn(memberOutService.genSn(year));

        memberOutMapper.updateByPrimaryKeySelective(record);
    }

    out.write("done");

%>
</body>
</html>
