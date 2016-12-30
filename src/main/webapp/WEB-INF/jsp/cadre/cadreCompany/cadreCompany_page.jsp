<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <a class="popupBtn btn btn-warning btn-sm"
       data-width="800"
       data-url="${ctx}/hf_content?code=${HF_CADRE_COMPANY}">
        <i class="fa fa-info-circle"></i> 填写说明</a>
    <shiro:hasPermission name="cadreCompany:edit">
        <a class="popupBtn btn btn-success btn-sm"
           data-url="${ctx}/cadreCompany_au?cadreId=${param.cadreId}"><i class="fa fa-plus"></i>
            添加</a>
        <a class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/cadreCompany_au"
           data-grid-id="#jqGrid_cadreCompany"
           data-querystr="&cadreId=${param.cadreId}"><i class="fa fa-edit"></i>
            修改</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="cadreCompany:del">
        <button data-url="${ctx}/cadreCompany_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid_cadreCompany"
                data-querystr="cadreId=${param.cadreId}"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-times"></i> 删除
        </button>
    </shiro:hasPermission>
</div>
    </c:if>
<div class="space-4"></div>
<table id="jqGrid_cadreCompany" class="jqGrid2"></table>
<div id="jqGridPager_cadreCompany"></div>
<div class="footer-margin"/>
<script>

    $("#jqGrid_cadreCompany").jqGrid({
        <c:if test="${!cm:isPermitted(PERMISSION_CADREADMIN) && !hasDirectModifyCadreAuth}">
        multiselect:false,
        </c:if>
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreCompany",
        url: '${ctx}/cadreCompany_data?${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label:'兼职类型', name: 'type', width: 140, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '';
                var ret = _cMap.CADRE_COMPANY_TYPE_MAP[cellvalue];
                if(cellvalue=='${CADRE_COMPANY_TYPE_OTHER}'){
                    if(rowObject.typeOther!=''){
                        ret = ret + ":"+  rowObject.typeOther;
                    }
                }
                return ret;
            },frozen:true},
            {label: '是否取酬', name: 'hasPay', formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return "";
                return cellvalue?"是":"否";
            }, width:80,frozen:true},
            {label: '兼职起始时间', name: 'startTime', width: 120, formatter: 'date', formatoptions: {newformat: 'Y.m'},frozen:true },
            {label: '兼职单位及职务', name: 'unit', width: 350},
            {label: '报批单位', name: 'reportUnit', width: 280},
            {label: '批复文件', name: 'paper', width: 250,
                formatter: function (cellvalue, options, rowObject) {
                    if(rowObject.paper==undefined) return '-';
                    return '<a href="${ctx}/attach/download?path={0}&filename={1}">{1}</a>'
                            .format(encodeURI(rowObject.paper),rowObject.paperFilename);
                }},
            {label: '备注', name: 'remark', width: 350}
        ]
    }).on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid2');
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>