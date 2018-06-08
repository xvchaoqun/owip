<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="tabbable myTableDiv">
    <shiro:hasRole name="${ROLE_ONLY_CADRE_VIEW}">
            <a class="btn btn-primary" href="${ctx}/cadreAdform_download?cadreId=${param.cadreId}">
                <i class="ace-icon fa fa-download "></i>
                下载(WORD)
            </a>
            <a class="btn btn-success" href="${ctx}/cadreAdform_zzb?cadreId=${param.cadreId}">
                <i class="ace-icon fa fa-download "></i>
                下载(中组部格式)
            </a>
    </shiro:hasRole>
    <shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
        <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
            <li class="${type==1?"active":""}">
                <a href="javascript:" onclick="_innerPage(1)"><i class="fa fa-flag"></i> 最新审批表</a>
            </li>
            <li class="${type==2?"active":""}">
                <a href="javascript:" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 存入档案审批表</a>
            </li>
            <c:if test="${type==1}">
            <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
                <a class="btn btn-info btn-sm" href="${ctx}/cadreAdform_download?cadreId=${param.cadreId}">
                    <i class="ace-icon fa fa-download "></i>
                    下载(WORD)
                </a>
                <a class="btn btn-success btn-sm" href="${ctx}/cadreAdform_zzb?cadreId=${param.cadreId}">
                    <i class="ace-icon fa fa-download "></i>
                    下载(中组部格式)
                </a>
            </div>
            </c:if>
        </ul>
    </shiro:lacksRole>
    <c:if test="${type==1}">
        <jsp:include page="adForm.jsp"/>

    </c:if>
    <c:if test="${type==2}">

    </c:if>
</div>

<script>
    function _innerPage(type) {
        $("#view-box .tab-content").loadPage("${ctx}/cadreAdform_page?cadreId=${param.cadreId}&type=" + type)
    }
</script>