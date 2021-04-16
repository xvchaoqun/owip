<%@ page import="sys.tags.CmTag" %>
<%@ page import="persistence.member.MemberViewMapper" %>
<%@ page import="domain.member.MemberViewExample" %>
<%@ page import="domain.member.MemberView" %>
<%@ page import="java.util.List" %>
<%@ page import="persistence.sys.SysUserInfoMapper" %>
<%@ page import="domain.sys.SysUserInfo" %>
<%@ page import="sys.utils.IdcardUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="sys.constants.SystemConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>根据身份证更新党员的性别和出生年月（空缺的）</title>
</head>
<body>
  <%--
  select idcard, gender, right(idcard, 1), (right(idcard, 1)+1)%2+1  from sys_user_info
  where length(idcard)=15 and (right(idcard, 1)+1)%2+1 != gender and idcard REGEXP '(^[0-9]+.[0-9]+$)|(^[0-9]$)' and user_id in(select user_id from ow_member);

    select idcard, gender, (left(right(idcard, 2),1)+1)%2+1 from sys_user_info
  where length(idcard)=18 and (left(right(idcard, 2),1)+1)%2+1 != gender and idcard REGEXP '(^[0-9]+.[0-9]+$)|(^[0-9]$)' and user_id in(select user_id from ow_member);
--%>
<%
    MemberViewMapper memberViewMapper = CmTag.getBean(MemberViewMapper.class);
    SysUserInfoMapper sysUserInfoMapper = CmTag.getBean(SysUserInfoMapper.class);

    MemberViewExample example = new MemberViewExample();
    example.or().andIdcardIsNotNull().andBirthIsNull();
    example.or().andIdcardIsNotNull().andGenderIsNull();

    List<MemberView> members = memberViewMapper.selectByExample(example);

    int count = 0;
    for (int i = 0; i < members.size(); i++) {

        int userId = members.get(i).getUserId();
        SysUserInfo ui = sysUserInfoMapper.selectByPrimaryKey(userId);
        if(ui!=null){
            String idcard = ui.getIdcard();
            if(IdcardUtils.valid(idcard)) {

                SysUserInfo record = new SysUserInfo();
                record.setUserId(userId);
                if (ui.getBirth() == null) {
                    record.setBirth(IdcardUtils.getBirth(idcard));
                }
                if (ui.getGender() == null) {
                    String gender = IdcardUtils.getGender(idcard);
                    record.setGender(StringUtils.contains(gender, "男") ? SystemConstants.GENDER_MALE : SystemConstants.GENDER_FEMALE);
                }
                count++;
                sysUserInfoMapper.updateByPrimaryKeySelective(record);
            }
        }
    }

    out.write("done " + count + " " + System.currentTimeMillis());
%>

</body>
</html>
