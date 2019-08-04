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

                String imgPath = pdfFilePath + ".jpg";
                //if(!FileUtils.exists(imgPath)){

                try {
                    PdfUtils.pdf2jpg(pdfFilePath, 300, PropertiesUtils.getString("gs.command"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                logger.info(count + "/" + total + ":" + imgPath);
                //}
            }

            example = new DispatchExample();
            example.createCriteria().andPptIsNotNull();
            dispatches = dispatchMapper.selectByExample(example);
            total = dispatches.size();
            for (Dispatch dispatch : dispatches) {

                count++;
                String path = springProps.uploadPath + dispatch.getPpt();
                if (!FileUtils.exists(path)) continue;

                String ext = FileUtils.getExtention(path);
                path = FileUtils.getFileName(path) + (StringUtils.equalsIgnoreCase(ext, ".pdf") ? ext : ".pdf");
                String imgPath = path + ".jpg";
                //if(!FileUtils.exists(imgPath)){

                try {
                    PdfUtils.pdf2jpg(path, 300, PropertiesUtils.getString("gs.command"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                logger.info(count + "/" + total + "(ppt):" + imgPath);
                //}
            }

            logger.info("finished.");
        }
    });

    t.start();
%>
</body>
</html>
