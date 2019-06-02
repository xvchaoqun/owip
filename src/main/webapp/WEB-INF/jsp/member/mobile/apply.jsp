<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=OwConstants.OW_ENTER_APPLY_TYPE_MAP%>" var="OW_ENTER_APPLY_TYPE_MAP"/>
<c:set value="<%=OwConstants.OW_ENTER_APPLY_STATUS_MAP%>" var="OW_ENTER_APPLY_STATUS_MAP"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="col-xs-12">
                <c:if test="${empty member}">
                    <blockquote>
                        <div class="green bolder">申请入党</div>
                        学生或教职工申请入党入口。
                        <button data-url="${ctx}/m/memberApply?isMobile=1" class="openView btn btn-sm btn-success" data-open-by="page"
                                type="button">
                            <i class="ace-icon fa fa-forward bigger-110"></i>
                            进入
                        </button>
                    </blockquote>
                </c:if>
                <c:if test="${empty member || member.status==MEMBER_STATUS_TRANSFER}">
                    <blockquote>
                        <div class="blue bolder">组织关系转入</div>
                        学生或教职工组织关系转入入口。


                        <button data-url="${ctx}/m/memberIn?isMobile=1" class="openView btn btn-sm btn-info" data-open-by="page"
                                type="button">
                            <i class="ace-icon fa fa-forward bigger-110"></i>
                            进入
                        </button>
                    </blockquote>
                </c:if>
                <c:if test="${empty member}">
                    <blockquote><div class="orange2 bolder">留学归国人员恢复组织生活</div>
                        填写恢复组织生活申请入口。

                        <button data-url="${ctx}/m/memberReturn?isMobile=1" class="openView btn btn-sm btn-warning" data-open-by="page"
                                type="button">
                            <i class="ace-icon fa fa-forward bigger-110"></i>
                            进入
                        </button>
                    </blockquote>
                </c:if>
                <blockquote>
                    <div class="red bolder">流入党员申请</div>
                    流入党员申请入口。

                    <button data-url="${ctx}/m/memberInflow?isMobile=1" class="openView btn btn-sm btn-danger" data-open-by="page"
                            type="button">
                        <i class="ace-icon fa fa-forward bigger-110"></i>
                        进入
                    </button>
                </blockquote>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
