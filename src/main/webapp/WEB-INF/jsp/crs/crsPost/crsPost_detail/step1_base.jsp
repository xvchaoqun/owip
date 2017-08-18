<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>

<div class="widget-box" style="width: 770px">
    <div class="widget-header">
        <h4 class="smaller">
            <c:if test="${not empty crsPost.requirement}">
            <div class="pull-right" style="margin-right: 10px">
                <button type="button"
                        data-load-el="#requirement-content"
                        data-url="${ctx}/crsPost_requirement?id=${param.id}"
                        class="loadPage btn btn-xs btn-success">
                    <i class="ace-icon fa fa-edit"></i>
                    编辑
                </button>
            </div>
            </c:if>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main" id="requirement-content" style="min-height: 570px">
            ${crsPost.requirement}
            <c:if test="${fn:trim(crsPost.requirement)==''}">
                <c:import url="${ctx}/crsPost_requirement?id=${param.id}"/>
            </c:if>
        </div>
    </div>
</div>