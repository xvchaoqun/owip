<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<div class="row">
  <div class="col-xs-12">
    <div id="body-content">
      <div class="col-xs-10 col-xs-offset-1" style="padding-top: 50px;">
      <div class="well">
        <blockquote>
        <h4 class="green bolder bigger-150">申请入党</h4>
        申请入党申请入党申请入党申请入党。
        <button data-url="${ctx}/user/memberApply" class="openView btn btn-success" type="button">
          <i class="ace-icon fa fa-forward bigger-110"></i>
          进入
        </button></blockquote>
      </div>
      <div class="well">
        <blockquote><h4 class="orange2 bolder bigger-150">留学归国党员申请</h4>
        留学归国党员申请留学归国党员申请留学归国党员申请。

        <button data-url="${ctx}/user/memberReturn" class="openView btn btn-warning" type="button">
          <i class="ace-icon fa fa-forward bigger-110"></i>
          进入
        </button>
          </blockquote>
      </div>

      <div class="well ">
        <blockquote>
        <h4 class="blue bolder bigger-150">组织关系转入</h4>
        转入党员申请转入党员申请转入党员申请。


        <button data-url="${ctx}/user/memberIn" class="openView btn btn-info" type="button">
          <i class="ace-icon fa fa-forward bigger-110"></i>
          进入
        </button>
          </blockquote>
      </div>
      <div class="well">
        <blockquote>
        <h4 class="red bolder bigger-150">流入党员申请</h4>
        流入党员申请流入党员申请流入党员申请流入党员申请。

        <button data-url="${ctx}/user/memberInflow" class="openView btn btn-danger" type="button">
          <i class="ace-icon fa fa-forward bigger-110"></i>
          进入
        </button>
          </blockquote>
      </div>
      </div>

    </div>
    <div id="item-content"></div>
    </div>
</div>
