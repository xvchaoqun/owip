<%@ page import="java.io.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>cmd</title>
    <script src='/assets/js/jquery.js'></script>
    <script src='/extend/js/nprogress.js'></script>

    <link rel="stylesheet" href="/extend/css/nprogress.css"/>
    <script>
        function _restart(){
                NProgress.start();
            /*$.post("restart.jsp",function(){
                NProgress.done();
                location.reload();

            });*/
        }
    </script>
</head>
<body>
  <table width="950">
    <tr>
        <td height="50">
            <input type="button" onclick="_restart()" value="重启">
        </td>
    </tr>
<%

    String[] cmd = {
           "tail -n20 /data/web/tomcat/apache-tomcat-8.0.15-ces/logs/catalina.out"
    };
    
    try {
        Process process = Runtime.getRuntime().exec(
                new String[]{
                        "sh",
                        "-c",
                        cmd[0]});
        BufferedReader inputBufferedReader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), "UTF-8"));
        String line = null;
        int odd = 0;
        while ((line = inputBufferedReader.readLine()) != null) {
            //System.out.println("++++++++++" + line);
            if (odd++ % 2 == 0) {
                out.write("<tr bgcolor=\"#e8e8e8\">");
            } else {
                out.write("<tr>");
            }
            out.write("<td height=\"20\">  " + line + "</td></tr>");
        }
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
%>
    <tr>
        <td height="40">
            <a href="void(0)" onclick="javascript:window.close()">关闭</a>
        </td>
    </tr>
</table>
</body>
</html>