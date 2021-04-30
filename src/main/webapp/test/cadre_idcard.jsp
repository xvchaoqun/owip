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
<%@ page import="persistence.cadre.CadreViewMapper" %>
<%@ page import="domain.cadre.CadreViewExample" %>
<%@ page import="domain.cadre.CadreView" %>
<%@ page import="sys.utils.DateUtils" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>根据身份证更新干部的出生日期（不一致的）</title>
</head>
<body>

<%
    CadreViewMapper cadreViewMapper = CmTag.getBean(CadreViewMapper.class);
    SysUserInfoMapper sysUserInfoMapper = CmTag.getBean(SysUserInfoMapper.class);

    CadreViewExample example = new CadreViewExample();

    List<CadreView> cadres = cadreViewMapper.selectByExample(example);

    int count = 0;
    for (int i = 0; i < cadres.size(); i++) {

        int userId = cadres.get(i).getUserId();
        SysUserInfo ui = sysUserInfoMapper.selectByPrimaryKey(userId);
        if(ui!=null && ui.getIdcard()!=null){
            String idcard = ui.getIdcard();
            if(IdcardUtils.valid(idcard)) {

                Date idcardBirth = IdcardUtils.getBirth(idcard);

                //idcardBirth = DateUtils.getFirstDayOfMonth(idcardBirth); // 仅显示月份的出生日期

                SysUserInfo record = new SysUserInfo();
                record.setUserId(userId);
                record.setRealname(ui.getRealname());
                if (ui.getBirth() == null) {
                    record.setBirth(idcardBirth);
                }else{

                    if(ui.getBirth().compareTo(idcardBirth)!=0){
                        out.write(ui.getRealname()
                                + " " + DateUtils.formatDate(ui.getBirth(), DateUtils.YYYYMMDD)
                                + " " + DateUtils.formatDate(idcardBirth, DateUtils.YYYYMMDD)
                                + "<br/>");

                        record.setBirth(idcardBirth);
                    }
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
