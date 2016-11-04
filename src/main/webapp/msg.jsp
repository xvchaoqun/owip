<%@ page import="domain.abroad.Passport" %>
<%@ page import="domain.abroad.PassportDraw" %>
<%@ page import="domain.abroad.PassportDrawExample" %>
<%@ page import="domain.sys.SysUser" %>
<%@ page import="org.apache.commons.lang3.BooleanUtils" %>
<%@ page import="org.joda.time.DateTime" %>
<%@ page import="org.joda.time.Period" %>
<%@ page import="org.joda.time.PeriodType" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="persistence.abroad.PassportDrawMapper" %>
<%@ page import="sys.constants.SystemConstants" %>
<%@ page import="sys.service.ApplicationContextSupport" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.sys.SysUserView" %>
<%@ page import="org.apache.http.entity.StringEntity" %>
<%@ page import="org.apache.http.impl.client.CloseableHttpClient" %>
<%@ page import="org.apache.http.impl.client.HttpClients" %>
<%@ page import="org.apache.http.client.methods.HttpPost" %>
<%@ page import="org.apache.http.client.methods.CloseableHttpResponse" %>
<%@ page import="org.apache.http.util.EntityUtils" %>
<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2016/3/14
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
  String url="https://weixin.bnu.edu.cn/sms/massms.php?id=2";
  String formStatusData="{\"mobile\":\"13810487549\", \"content\":\"孙秋瑞同志，您好！您提交的领取使用大陆居民往来台湾通行证的申请（编码为：D34）已通过审批，请派人携带有效证件（教工卡、学生卡或身份证）并凭此短信到组织部（主楼A306）领取证件。谢谢！\"}";
  StringEntity params =new StringEntity(formStatusData,"UTF-8");
  CloseableHttpClient httpclient = HttpClients.createDefault();
  HttpPost httppost = new HttpPost(url);
  httppost.setEntity(params);
  httppost.addHeader("content-type", "application/json");
  CloseableHttpResponse res = httpclient.execute(httppost);
  out.println(EntityUtils.toString(res.getEntity()));
 /* ApplicationContext context = ApplicationContextSupport.getContext();
  PassportDrawMapper passportDrawMapper = (PassportDrawMapper) context.getBean("passportDrawMapper");

  int count = 0;
  Date today = new Date();
  // 查找已领取证件，但还未归还（该证件昨天应归还）的记录
  PassportDrawExample example = new PassportDrawExample();
  example.createCriteria().andDrawStatusEqualTo(SystemConstants.PASSPORT_DRAW_DRAW_STATUS_DRAW).andReturnDateLessThan(today);
  List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);
  for (PassportDraw passportDraw : passportDraws) {

    Passport passport = passportDraw.getPassport();
    if(passport.getType()==SystemConstants.PASSPORT_TYPE_KEEP
            || (passport.getType()==SystemConstants.PASSPORT_TYPE_CANCEL
            && BooleanUtils.isFalse(passport.getCancelConfirm()))) { // 集中管理的 或 未确认的取消集中管理证件，才需要短信提醒

      Date returnDate = passportDraw.getReturnDate(); // 应归还时间
      Period p = new Period(new DateTime(returnDate), new DateTime(today), PeriodType.days());
      int days = p.getDays();
      if ((days - 1) % 3 == 0) {  // 间隔第1,4,7...天应发短信提醒

        SysUserView user = passportDraw.getUser();
        out.write(user.getRealname() + " " + user.getCode() + " " + passportDraw.getReturnDate() + "<br/>");
      }
    }
  }*/
%>
</body>
</html>
