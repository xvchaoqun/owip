<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="sys.utils.FtpUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    Logger logger = LoggerFactory.getLogger("ftptest");

    String hostname = "172.16.212.56";
    int port = 2019;
    String username = "dangfei";
    String password = "df20190107";
    String pathname = "/";
    String filename = "partyfee-20191212.txt";
    String localpath = "/tmp";

    FtpUtils ftp = new FtpUtils(hostname, port, username, password);
    ftp.downloadFile(pathname, filename, localpath);

    /*FTPClient ftpClient = new FTPClient();

    ftpClient.setControlEncoding("utf-8");

    logger.info("connecting ftp://{}:{}", hostname, port);

    ftpClient.connect(hostname, port);
    ftpClient.login(username, password);

    int replyCode = ftpClient.getReplyCode();
    if (!FTPReply.isPositiveCompletion(replyCode)) {
        logger.info("connect failed, ftp://{}:{}, {}", hostname, port, ftpClient.getReplyString());

        throw new RuntimeException("ftp connect failed.");
    }

    //ftpClient.enterLocalPassiveMode();
    logger.info("connect success, ftp://{}:{}", hostname, port);

    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
    ftpClient.enterLocalActiveMode();

    logger.info("开始下载文件");
    //切换FTP目录
    boolean flag = ftpClient.changeWorkingDirectory(pathname);
    if (!flag) {
        logger.error("文件路径{}不存在", pathname);
        return;
    }

    FTPFile[] ftpFiles = ftpClient.listFiles();
    logger.info("文件 :" + ftpFiles.length);
    OutputStream os = null;
    for (FTPFile file : ftpFiles) {
        if (filename.equalsIgnoreCase(file.getName())) {
            File localFile = new File(localpath + "/" + file.getName());
            os = new FileOutputStream(localFile);
            ftpClient.retrieveFile(file.getName(), os);
            os.close();
        }
    }

    os.close();
    ftpClient.logout();
    logger.info("下载文件成功");
    ftpClient.disconnect();*/
%>
</body>
</html>
