<%@ page import="sys.utils.JSONUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String queryString = request.getQueryString();
    System.out.println("test queryString=" + queryString);
    System.out.println("test request.getParameterMap()=" + JSONUtils.toString(request.getParameterMap(), false));



%>
