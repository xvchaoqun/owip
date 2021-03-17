<%@ page import="org.apache.http.client.methods.CloseableHttpResponse" %>
<%@ page import="org.apache.http.client.methods.HttpPost" %>
<%@ page import="org.apache.http.entity.StringEntity" %>
<%@ page import="org.apache.http.impl.client.CloseableHttpClient" %>
<%@ page import="org.apache.http.impl.client.HttpClients" %>
<%@ page import="org.apache.http.util.EntityUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
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
    String mobile = StringUtils.defaultIfBlank(request.getParameter("m"), "17611167552");
    String msg = StringUtils.defaultIfBlank(request.getParameter("msg"), "您好！这是测试短信，勿回！谢谢！");

    //out.println("user-agent = " + request.getHeader("user-agent"));
    //out.println("<br/>User-Agent = " + request.getHeader("User-Agent"));

    String url = "https://weixin.bnu.edu.cn/sms/massms.php?id=2";
    out.println("<br/>发送短信接口："+ url);
    String formStatusData = "{\"mobile\":\"" + mobile + "\", \"content\":\"" + msg + "\"}";
    out.println("<br/>发送短信参数："+ formStatusData);
    StringEntity params = new StringEntity(formStatusData, "UTF-8");
    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpPost httppost = new HttpPost(url);
    httppost.setEntity(params);
    httppost.addHeader("content-type", "application/json");
    CloseableHttpResponse res = httpclient.execute(httppost);
    out.println("<br/>返回：" + EntityUtils.toString(res.getEntity()));
 /* ApplicationContext context = ApplicationContextSupport.getContext();
  PassportDrawMapper passportDrawMapper = (PassportDrawMapper) context.getBean("passportDrawMapper");

  int count = 0;
  Date today = new Date();
  // 查找已领取证件，但还未归还（该证件昨天应归还）的记录
  PassportDrawExample example = new PassportDrawExample();
  example.createCriteria().andDrawStatusEqualTo(AbroadConstants.ABROAD_PASSPORT_DRAW_DRAW_STATUS_DRAW).andReturnDateLessThan(today);
  List<PassportDraw> passportDraws = passportDrawMapper.selectByExample(example);
  for (PassportDraw passportDraw : passportDraws) {

    Passport passport = passportDraw.getPassport();
    if(passport.getType()==AbroadConstants.ABROAD_PASSPORT_TYPE_KEEP
            || (passport.getType()==AbroadConstants.ABROAD_PASSPORT_TYPE_CANCEL
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
