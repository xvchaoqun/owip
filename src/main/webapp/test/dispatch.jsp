<%@ page import="domain.dispatch.Dispatch" %>
<%@ page import="domain.dispatch.DispatchExample" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="persistence.dispatch.DispatchMapper" %>
<%@ page import="service.SpringProps" %>
<%@ page import="sys.tags.CmTag" %>
<%@ page import="sys.utils.FileUtils" %>
<%@ page import="sys.utils.PdfUtils" %>
<%@ page import="sys.utils.PropertiesUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>批量生成任免文件图片</title>
</head>
<body>
<%
    final boolean flush = StringUtils.equals(request.getParameter("flush"), "1");

    Thread t = new Thread(new Runnable() {
        public void run() {
            SpringProps springProps = CmTag.getBean(SpringProps.class);
            DispatchMapper dispatchMapper = CmTag.getBean(DispatchMapper.class);

            Logger logger = LoggerFactory.getLogger("dipatch-test");
            int count = 0;
            DispatchExample example = new DispatchExample();
            example.createCriteria().andFileIsNotNull();
            List<Dispatch> dispatches = dispatchMapper.selectByExample(example);
            int total = dispatches.size();
            for (Dispatch dispatch : dispatches) {

                count++;
                String pdfFilePath = springProps.uploadPath + dispatch.getFile();
                if (!FileUtils.exists(pdfFilePath)) continue;

                FileUtils.delFile(pdfFilePath + ".jpg"); // 删除原整张图片

                String imgPath = pdfFilePath + "-001.jpg";
                if (flush || !FileUtils.exists(imgPath)) {

                    logger.info(dispatch.getDispatchCode());
                    try {
                        PdfUtils.pdf2jpg(pdfFilePath, 150, PropertiesUtils.getString("gs.command"), 1);// 生成第一页
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    logger.info(count + "/" + total + ":" + imgPath);
                }

            }

            logger.info("finished.");
        }
    });

    t.start();
%>
</body>
</html>
