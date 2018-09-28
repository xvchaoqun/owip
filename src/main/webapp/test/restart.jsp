<%@ page import="java.io.IOException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String[] cmd = {
          "sudo /etc/init.d/jsvc-ces restart"
  };
  try {
    Runtime.getRuntime().exec(
            new String[]{
                    "sh",
                    "-c",
                    cmd[0]});

  } catch (IOException e) {
    e.printStackTrace();
  }
%>