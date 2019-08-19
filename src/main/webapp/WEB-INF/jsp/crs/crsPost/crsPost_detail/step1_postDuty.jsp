<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="space-4"></div>

<div class="widget-box" style="width: 770px">
    <div class="widget-header">
        <h4 class="widget-title">
            <c:if test="${not empty crsPost.postDuty}">
            <div class="pull-right" style="margin-right: 10px">
                <shiro:hasPermission name="crsPost:edit">
                <button type="button"
                        data-load-el="#requirement-content"
                        data-url="${ctx}/crsPost_templateContent?id=${param.id}&type=${CRS_TEMPLATE_TYPE_POST_DUTY}"
                        class="loadPage btn btn-xs btn-success">
                    <i class="ace-icon fa fa-edit"></i>
                    编辑
                </button>
                </shiro:hasPermission>
            </div>
            </c:if>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main" id="requirement-content" style="min-height: 570px">
            ${crsPost.postDuty}
            <c:if test="${fn:trim(crsPost.postDuty)==''}">
                <c:import url="${ctx}/crsPost_templateContent?id=${param.id}&type=${CRS_TEMPLATE_TYPE_POST_DUTY}"/>
            </c:if>
        </div>
    </div>
</div>