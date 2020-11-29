<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=OwConstants.OW_ENTER_APPLY_TYPE_MAP%>" var="OW_ENTER_APPLY_TYPE_MAP"/>
<c:set value="<%=OwConstants.OW_ENTER_APPLY_STATUS_MAP%>" var="OW_ENTER_APPLY_STATUS_MAP"/>

<div class="row">
  <div class="col-xs-12">
    <div id="body-content">
        <div style="width: 900px">
     <c:if test="${empty member}">
      <div class="well">
        <blockquote>
        <h4 class="green bolder bigger-150">党员发展申请</h4>
        学生或教职工党员发展申请入口。
        <button data-url="${ctx}/user/memberApply" class="openView btn btn-success" type="button">
          <i class="ace-icon fa fa-forward bigger-110"></i>
          进入
        </button></blockquote>
      </div>
     </c:if>
     <c:if test="${empty member || member.status==MEMBER_STATUS_TRANSFER}">
      <div class="well ">
        <blockquote>
        <h4 class="blue bolder bigger-150">组织关系转入</h4>
        学生或教职工组织关系转入入口。


        <button data-url="${ctx}/user/memberIn" class="openView btn btn-info" type="button">
          <i class="ace-icon fa fa-forward bigger-110"></i>
          进入
        </button>
          </blockquote>
      </div>
      </c:if>
            <c:if test="${empty member}">

                <div class="well">
                    <blockquote><h4 class="orange2 bolder bigger-150">留学归国人员恢复组织生活</h4>
                        填写恢复组织生活申请入口。

                        <button data-url="${ctx}/user/memberReturn" class="openView btn btn-warning" type="button">
                            <i class="ace-icon fa fa-forward bigger-110"></i>
                            进入
                        </button>
                    </blockquote>
                </div>
            </c:if>
      <div class="well">
        <blockquote>
        <h4 class="red bolder bigger-150">流入党员申请</h4>
        流入党员申请入口。

        <button data-url="${ctx}/user/memberInflow" class="openView btn btn-danger" type="button">
          <i class="ace-icon fa fa-forward bigger-110"></i>
          进入
        </button>
          </blockquote>
      </div>
      </div>

    </div>
    <div id="body-content-view"></div>
    </div>
</div>
