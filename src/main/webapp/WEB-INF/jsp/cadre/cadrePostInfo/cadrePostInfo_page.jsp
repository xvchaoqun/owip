<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="${type==1?"active":""}">
        <a href="javascript:" onclick="_innerPage2(1)"><i class="fa fa-flag"></i> 专技岗位过程信息</a>
    </li>
    <li class="${type==2?"active":""}">
        <a href="javascript:" onclick="_innerPage2(2)"><i class="fa fa-flag"></i> 管理岗位过程信息</a>
    </li>
    <li class="${type==3?"active":""}">
        <a href="javascript:" onclick="_innerPage2(3)"><i class="fa fa-flag"></i> 工勤岗位过程信息</a>
    </li>
</ul>

<c:if test="${type==1}">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <div class="space-4"></div>
        <div class="jqgrid-vertical-offset buttons">
            <shiro:hasPermission name="cadrePostInfo:edit">
                <a class="popupBtn btn btn-success btn-sm"
                   data-url="${ctx}/cadrePostPro_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                    添加</a>
                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                   data-url="${ctx}/cadrePostPro_au"
                   data-grid-id="#jqGrid_cadrePostPro"
                   data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                    修改</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="cadrePostInfo:del">
                <button data-url="${ctx}/cadrePostPro_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid_cadrePostPro"
                        data-querystr="cadreId=${param.cadreId}"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-times"></i> 删除
                </button>
            </shiro:hasPermission>
        </div>
    </c:if>
    <div class="space-4"></div>
    <table id="jqGrid_cadrePostPro" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadrePostPro"></div>
</c:if>
<c:if test="${type==2}">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <div class="space-4"></div>
        <div class="jqgrid-vertical-offset buttons">
            <shiro:hasPermission name="cadrePostInfo:edit">
                <a class="popupBtn btn btn-success btn-sm"
                   data-url="${ctx}/cadrePostAdmin_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                    添加</a>
                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                   data-url="${ctx}/cadrePostAdmin_au"
                   data-grid-id="#jqGrid_cadrePostAdmin"
                   data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                    修改</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="cadrePostInfo:del">
                <button data-url="${ctx}/cadrePostAdmin_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid_cadrePostAdmin"
                        data-querystr="cadreId=${param.cadreId}"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-times"></i> 删除
                </button>
            </shiro:hasPermission>
        </div>
    </c:if>
    <div class="space-4"></div>
    <table id="jqGrid_cadrePostAdmin" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadrePostAdmin"></div>
</c:if>
<c:if test="${type==3}">
    <c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
        <div class="space-4"></div>
        <div class="jqgrid-vertical-offset buttons">
            <shiro:hasPermission name="cadrePostInfo:edit">
                <a class="popupBtn btn btn-success btn-sm"
                   data-url="${ctx}/cadrePostWork_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
                    添加</a>
                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                   data-url="${ctx}/cadrePostWork_au"
                   data-grid-id="#jqGrid_cadrePostWork"
                   data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
                    修改</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="cadrePostInfo:del">
                <button data-url="${ctx}/cadrePostWork_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？"
                        data-grid-id="#jqGrid_cadrePostWork"
                        data-querystr="cadreId=${param.cadreId}"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-times"></i> 删除
                </button>
            </shiro:hasPermission>
        </div>
    </c:if>
    <div class="space-4"></div>
    <table id="jqGrid_cadrePostWork" data-width-reduce="60" class="jqGrid2"></table>
    <div id="jqGridPager_cadrePostWork"></div>
</c:if>
<script>
    function _innerPage2(type) {
        $("#view-box .tab-content").load("${ctx}/cadrePostInfo_page?cadreId=${param.cadreId}&type=" + type)
    }

    <c:if test="${type==1}">
    $("#jqGrid_cadrePostPro").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadrePostPro",
        url: '${ctx}/cadrePostPro_data?cadreId=${param.cadreId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '是否当前专技岗位', width: 150, name: 'isCurrent',formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? "是" : "否";
            }},
            {label: '岗位类别', width: 120, name: 'type', formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined|| _cMap.metaTypeMap[cellvalue]==undefined) return ''
                return _cMap.metaTypeMap[cellvalue].name
            }},
            {label: '职级', name: 'postLevel'},
            {label: '专业技术职务', name: 'post', width: 250, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined || _cMap.metaTypeMap[cellvalue]==undefined) return ''
                return _cMap.metaTypeMap[cellvalue].name
            }},
            {label: '专技职务任职时间', name: 'holdTime', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '专技岗位等级', name: 'level', width: 160, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined || _cMap.metaTypeMap[cellvalue]==undefined) return ''
                return _cMap.metaTypeMap[cellvalue].name
            }},
            {label: '专技岗位分级时间', name: 'gradeTime', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '专技岗位备注', name: 'remark', width: 350}
        ]
    });
    </c:if>
    <c:if test="${type==2}">
    $("#jqGrid_cadrePostAdmin").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadrePostAdmin",
        url: '${ctx}/cadrePostAdmin_data?cadreId=${param.cadreId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '是否当前管理岗位', width: 150, name: 'isCurrent',formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? "是" : "否";
            }},
            {label: '管理岗位等级', name: 'level', width: 150, formatter: function (cellvalue, options, rowObject) {
                return _cMap.metaTypeMap[cellvalue].name
            }},
            {label: '管理岗位分级时间', name: 'gradeTime', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '管理岗位备注', name: 'remark', width: 350}
        ]
    });
    </c:if>
    <c:if test="${type==3}">
    $("#jqGrid_cadrePostWork").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadrePostWork",
        url: '${ctx}/cadrePostWork_data?cadreId=${param.cadreId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '是否当前工勤岗位', width: 150, name: 'isCurrent',formatter: function (cellvalue, options, rowObject) {
                return cellvalue ? "是" : "否";
            }},
            {label: '工勤岗位等级', name: 'level', width: 150, formatter: function (cellvalue, options, rowObject) {
                return _cMap.metaTypeMap[cellvalue].name
            }},
            {label: '工勤岗位分级时间', name: 'gradeTime', width: 150, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '工勤岗位备注', name: 'remark', width: 350}
        ]
    });
    </c:if>
    $(window).triggerHandler('resize.jqGrid2');
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>