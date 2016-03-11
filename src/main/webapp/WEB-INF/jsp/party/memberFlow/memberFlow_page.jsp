<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="body-content">
<div class="row">
  <div class="col-xs-12">
    <!-- PAGE CONTENT BEGINS -->
      <div class="tabbable">
        <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
          <li  class="<c:if test="${cls==1}">active</c:if>">
          <a href="?cls=1"><i class="fa fa-sign-out"></i> 流出党员</a>
          </li>
          <li  class="<c:if test="${cls==2}">active</c:if>">
          <a href="?cls=2"><i class="fa fa-sign-in"></i> 流入党员</a>
          </li>
        </ul>

        <div class="tab-content">
          <div id="home4" class="tab-pane in active">
                <c:import url="/memberFlow_byType?cls=${cls}"/>
            </div>
          </div>
        </div>
    </div>
  </div>
</div><div id="item-content"></div>