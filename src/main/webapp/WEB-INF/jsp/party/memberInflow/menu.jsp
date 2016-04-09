<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="dropdown <c:if test="${cls==1||cls==2||cls==3}">active</c:if>" >
    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
      <i class="fa fa-sign-out"></i> 流出党员${cls==1?"(未完成审核)":(cls==2)?"(已完成审核)":(cls==3)?"(申请未通过)":""}
      <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
    </a>
    <ul class="dropdown-menu dropdown-info" style="min-width: 200px">
      <li>
        <a href="?cls=1">未完成审核</a>
      </li>
      <li>
        <a href="?cls=2">已完成审核</a>
      </li>
      <li>
        <a href="?cls=3">申请未通过</a>
      </li>
    </ul>
  </li>

  <li class="dropdown <c:if test="${cls==4||cls==5||cls==6}">active</c:if>" >
    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
      <i class="fa fa-sign-in"></i> 流入党员${cls==4?"(未完成审核)":(cls==5)?"(已完成审核)":(cls==6)?"(申请未通过)":""}
      <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
    </a>
    <ul class="dropdown-menu dropdown-info" style="min-width: 200px">
      <li>
        <a href="?cls=4">未完成审核</a>
      </li>
      <li>
        <a href="?cls=5">已完成审核</a>
      </li>
      <li>
        <a href="?cls=6">申请未通过</a>
      </li>
    </ul>
  </li>
</ul>