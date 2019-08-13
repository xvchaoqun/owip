<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['adFormType']}" var="_p_adFormType"/>
<div class="tabbable myTableDiv">
    <shiro:hasPermission name="${PERMISSION_CADREONLYVIEW}">
            <a href="javascript:;" class="downloadBtn btn btn-primary" data-url="${ctx}/cadreAdform_download?isWord=1&cadreId=${param.cadreId}">
                <i class="ace-icon fa fa-download "></i>
                下载(WORD)
            </a>
            <a href="javascript:;" class="downloadBtn btn btn-success" data-url="${ctx}/cadreAdform_download?cadreId=${param.cadreId}">
                <i class="ace-icon fa fa-download "></i>
                下载(中组部格式)
            </a>
    </shiro:hasPermission>
    <shiro:lacksPermission name="${PERMISSION_CADREONLYVIEW}">
        <ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
            <li class="${type==1?"active":""}">
                <a href="javascript:;" onclick="_innerPage(1)"><i class="fa fa-newspaper-o"></i> 最新审批表</a>
            </li>
            <%--<li class="${type==2?"active":""}">
                <a href="javascript:;" onclick="_innerPage(2)"><i class="fa fa-flag"></i> 存入档案审批表</a>
            </li>--%>
            <c:if test="${type==1}">
                <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
                    <a href="javascript:;" class="downloadBtn btn btn-info btn-sm"
                       data-url="${ctx}/cadreAdform_download?isWord=1&cadreId=${param.cadreId}">
                        <i class="ace-icon fa fa-download "></i>
                        下载(WORD)
                    </a>
                    <a href="javascript:;" class="downloadBtn btn btn-success btn-sm"
                       data-url="${ctx}/cadreAdform_download?cadreId=${param.cadreId}">
                        <i class="ace-icon fa fa-download "></i>
                        下载(中组部格式)
                    </a>
                </div>
                <div class="btn-group pull-right" style="padding: 5px 15px">
                    <button data-toggle="dropdown"
                            class="btn btn-primary btn-sm dropdown-toggle tooltip-success">
                        <i class="fa fa-download"></i> 所有(WORD) <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu dropdown-success" role="menu">
                        <c:forEach items="<%=CadreConstants.CADRE_ADFORMTYPE_MAP%>" var="adFormType" varStatus="vs">
                        <li>
                            <a href="javascript:;" class="downloadBtn"
                               data-url="${ctx}/cadreAdform_download?isWord=1&adFormType=${adFormType.key}&cadreId=${param.cadreId}">
                                <i class="fa fa-file-excel-o"></i> ${adFormType.value}${_p_adFormType==adFormType.key?'(系统默认)':''}</a>
                        </li>
                        <c:if test="${!vs.last}">
                        <li role="separator" class="divider"></li>
                        </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
        </ul>
    </shiro:lacksPermission>
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