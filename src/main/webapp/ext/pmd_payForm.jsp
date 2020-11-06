<%@ page import="sys.utils.HttpRequestDeviceUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pmd/constants.jsp"%>
<%
    boolean isMobile = HttpRequestDeviceUtils.isMobileDevice(request);
    request.setAttribute("isMobile", isMobile);
%>
<form action="${ctx}/page/pmd_payForm.jsp" <c:if test="${!isMobile}">target="_blank"</c:if> method="get">
    <input type="hidden" name="tranamt" value="{{=order.tranamt}}"/>
    <input type="hidden" name="account" value="{{=order.account}}"/>
    <input type="hidden" name="sno" value="{{=order.sno}}"/>
    <input type="hidden" name="toaccount" value="{{=order.toaccount}}"/>
    <input type="hidden" name="thirdsystem" value="{{=order.thirdsystem}}"/>
    <input type="hidden" name="thirdorderid" value="{{=order.thirdorderid}}"/>
    <input type="hidden" name="ordertype" value="{{=order.ordertype}}"/>
    <input type="hidden" name="orderdesc" value="{{=order.orderdesc}}"/>
    <input type="hidden" name="praram1" value="{{=order.praram1}}"/>
    <input type="hidden" name="thirdurl" value="{{=formMap.thirdurl}}"/>
    <input type="hidden" name="sign" value="{{=order.sign}}"/>
</form>