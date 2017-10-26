<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==1?'active':''}">
    <a href="javascript:;" class="loadPage"
       data-url="${ctx}/crsStat?cls=1"><i
            class="fa fa-th${cls==1?'-large':''}"></i> 历次招聘会前两名汇总</a>
  </li>
  <li class="${cls==2?'active':''}">
    <a href="javascript:;" class="loadPage"
       data-url="${ctx}/crsStat?cls=2"><i
            class="fa fa-th${cls==2?'-large':''}"></i> 应聘人报名次数统计</a>
  </li>
</ul>