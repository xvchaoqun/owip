<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row">
  <div class="col-xs-12">
    <div id="body-content">
        <div class="${fn:length(applyList)>0?"col-xs-8":"col-xs-12"}">
     <c:if test="${empty member}">
      <div class="well">
        <blockquote>
        <h4 class="green bolder bigger-150">申请入党</h4>
        学生或教职工申请入党入口。
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
        <c:if test="${fn:length(applyList)>0}">
        <div class="col-xs-4">
          <blockquote>
            <h4 class="green bolder bigger-150">申请记录</h4>
            <div style="height: 600px;overflow-y: auto">
              <c:forEach var="apply" items="${applyList}">
            <hr/>
            <dt>
                ${ENTER_APPLY_TYPE_MAP.get(apply.type)}
                <dd>
              <ul>
                <li>提交申请时间：${cm:formatDate(apply.createTime,'yyyy-MM-dd HH:mm')}</li>
                <li>申请状态：${ENTER_APPLY_STATUS_MAP.get(apply.status)}</li>
                <c:if test="${not empty apply.remark}">
                <li>备注：${apply.remark}</li>
                </c:if>
                <li>撤销时间：${cm:formatDate(apply.backTime,'yyyy-MM-dd HH:mm')}</li>
              </ul>
              </dd>
            </dt>
            </c:forEach>
            </div>
          </blockquote>
        </div>
        </c:if>
    </div>
    <div id="body-content-view"></div>
    </div>
</div>
