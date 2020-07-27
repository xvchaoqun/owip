<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="space-4"></div>
<div class="row" style="width: 1400px">
    <div style="width: 770px;float: left;margin-right: 25px">
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title">
            任职资格
            <c:if test="${not empty crsPost.qualification}">
            <div class="pull-right" style="margin-right: 10px">
                <shiro:hasPermission name="crsPost:edit">
                <button type="button"
                        data-load-el="#qualification-content"
                        data-url="${ctx}/crsPost_templateContent?id=${param.id}&type=${CRS_TEMPLATE_TYPE_POST}"
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
        <div class="widget-main" id="qualification-content" style="min-height: 570px">
            ${cm:htmlUnescape(crsPost.qualification)}
            <c:if test="${empty crsPost.qualification}">
                <c:import url="${ctx}/crsPost_templateContent?id=${param.id}&type=${CRS_TEMPLATE_TYPE_POST}"/>
            </c:if>
        </div>
    </div>
</div>
    </div>
    <div style="width: 550px; float:left">
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title">
            岗位具体要求（用于资格审核）
            <c:if test="${not empty crsPostRequire}">
            <div class="pull-right" style="margin-right: 10px">
                <shiro:hasPermission name="crsPost:edit">
                <button type="button"
                        data-load-el="#require-content"
                        data-url="${ctx}/crsPost_detail/step1_require_au?id=${param.id}"
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
        <div class="widget-main" id="require-content">
            <c:if test="${empty crsPostRequire}">
                <c:import url="${ctx}/crsPost_detail/step1_require_au?id=${param.id}"/>
            </c:if>
            <c:if test="${not empty crsPostRequire}">
            <div style="font-size: 16px; font-weight: bolder">${crsPostRequire.name}：</div>
            <jsp:include page="/WEB-INF/jsp/crs/crsPostRequire/postRequire.jsp"/>
            </c:if>
        </div>
    </div>
</div>
        </div>
</div>