<%@ page import="org.joda.time.DateTime" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="service.pmd.PmdOrderLogService" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="sys.utils.DateUtils" %>
<%@ page import="sys.utils.FtpUtils" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    Logger logger = LoggerFactory.getLogger("ftptest");

    String month = DateUtils.formatDate(new Date(), DateUtils.YYYYMM);
    Date payMonth = DateUtils.parseDate(month, DateUtils.YYYYMM);

    String hostname = "172.16.212.56";
    int port = 2019;
    String username = "dangfei";
    String password = "df20190107";

    String pathname = "/";
    String downloadPath = "/tmp/" + month;

    FtpUtils ftp = new FtpUtils(hostname, port, username, password);

    DateTime dt = new DateTime(payMonth);

    DateTime today = new DateTime(new Date());
    int num = today.getDayOfMonth();
    for (int i = 0; i < num; i++) {

        DateTime _dt = dt.plusDays(i);

        String filename = String.format("partyfee-%s.txt", DateUtils.formatDate(_dt.toDate(), "yyyyMMdd"));
        boolean ret = ftp.downloadFile(pathname, filename, downloadPath);

        logger.info("下载" + filename + ": " + ret);
    }

    PmdOrderLogService pmdOrderLogService = CmTag.getBean(PmdOrderLogService.class);
    File file = new File(downloadPath);
    int count = 0;
    if (file.isDirectory()) {
        File[] files = file.listFiles();
        for (File txt : files) {
            try {
                String name = txt.getName();
                count += pmdOrderLogService.loadFile(downloadPath + "/" + name, "r", name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    logger.info("总计：" + count + "条记录");
%>
</body>
</html>
