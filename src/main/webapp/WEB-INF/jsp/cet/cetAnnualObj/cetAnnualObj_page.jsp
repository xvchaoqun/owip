<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:set var="_query"
       value="${not empty param.recordId ||not empty param.userId || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">
    <c:if test="${!isQuit}">
    <shiro:hasPermission name="cetAnnualObj:edit">
        <button class="popupBtn btn btn-success btn-sm"
                data-url="${ctx}/cet/cetAnnualObj_add?annualId=${param.annualId}">
            <i class="fa fa-plus"></i> 添加
        </button>
       <button data-url="${ctx}/cet/cetAnnualObj_quit?isQuit=1"
                data-title="退出"
                data-msg="确定将这{0}个人员转移到“退出培训人员”？"
                data-grid-id="#jqGrid2"
                data-callback="_detailReload"
                class="jqBatchBtn btn btn-primary btn-sm">
            <i class="fa fa-sign-out"></i> 退出
        </button>

        <button class="popupBtn btn btn-info btn-sm"
                data-url="${ctx}/cet/cetAnnualObj_batchRequire?annualId=${param.annualId}">
            <i class="fa fa-users"></i>
            批量设定年度学习任务
        </button>
        <button class="jqOpenViewBtn btn btn-warning btn-sm"
                data-url="${ctx}/cet/cetAnnualObj_singleRequire"
                data-grid-id="#jqGrid2"><i class="fa fa-user"></i>
            修改年度学习任务
        </button>

        <button disabled class="btn btn-primary btn-sm"
                data-url="${ctx}/cet/cetAnnualObj_sync?annualId=${param.annualId}">
            <i class="fa fa-refresh"></i>
            同步培训对象信息
        </button>
        <button disabled class="btn btn-primary btn-sm"
                data-url="${ctx}/cet/cetAnnualObj_sync?annualId=${param.annualId}">
            <i class="fa fa-archive"></i>
            正式归档
        </button>
        <button disabled class="btn btn-primary btn-sm"
                data-url="${ctx}/cet/cetAnnualObj_sync?annualId=${param.annualId}">
            <i class="fa fa-archive"></i>
            取消归档
        </button>
    </shiro:hasPermission>


    <%-- <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
             data-url="${ctx}/cet/cetAnnualObj_data"
             data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
         <i class="fa fa-download"></i> 导出
     </button>--%>
        </c:if>
    <c:if test="${isQuit}">
    <shiro:hasPermission name="cetProjectObj:edit">
        <button data-url="${ctx}/cet/cetAnnualObj_quit?isQuit=0"
                data-title="返回培训对象"
                data-msg="确定将这{0}个人员转移到“培训对象”？"
                data-grid-id="#jqGrid2"
                data-callback="_detailReload"
                class="jqBatchBtn btn btn-success btn-sm">
            <i class="fa fa-reply"></i> 返回培训对象
        </button>
    </shiro:hasPermission>
    </c:if>
    <button class="jqOpenViewBtn btn btn-info btn-sm"
            data-grid-id="#jqGrid2"
            data-url="${ctx}/sysApprovalLog"
            data-width="850"
            data-querystr="&displayType=1&hideStatus=1&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_CET_ANNUAL%>">
        <i class="fa fa-history"></i> 操作记录
    </button>
    <shiro:hasPermission name="cetAnnualObj:del">
        <button data-url="${ctx}/cet/cetAnnualObj_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
    </shiro:hasPermission>
</div>
<div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
    <div class="widget-header">
        <h4 class="widget-title">搜索</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <form class="form-inline search-form" id="searchForm">
                <div class="form-group">
                    <label>所属档案</label>
                    <input class="form-control search-query" name="recordId" type="text"
                           value="${param.recordId}"
                           placeholder="请输入所属档案">
                </div>
                <div class="form-group">
                    <label>培训对象</label>
                    <input class="form-control search-query" name="userId" type="text"
                           value="${param.userId}"
                           placeholder="请输入培训对象">
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-url="${ctx}/cet/cetAnnualObj"
                       data-target="#page-content"
                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="resetBtn btn btn-warning btn-sm"
                                data-url="${ctx}/cet/cetAnnualObj"
                                data-target="#page-content">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped"></table>
<div id="jqGridPager2"></div>
<style>
    span.overflow{
        color: red;
    }
</style>
<script>
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        page:"${param._pageNo}",
        rownumbers: true,
        url: '${ctx}/cet/cetAnnualObj_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '详情',name: '_detail', width: 80, formatter: function (cellvalue, options, rowObject) {

                var pageNo = $(this).getGridParam("page");
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/cet/cetAnnualObj_detail?objId={0}&_pageNo={1}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id, pageNo);
            }, frozen: true},
            {label: '工作证号', name: 'user.code', frozen: true},
            {label: '姓名', name: 'user.realname', frozen: true},
            {label: '时任单位及职务', name: 'title', align: 'left', width: 350},
            {label: '行政级别', name: 'adminLevel', formatter: $.jgrid.formatter.MetaType},
            {label: '职务属性', name: 'postType', width: 150, formatter: $.jgrid.formatter.MetaType},
            {label: '任现职时间', name: 'lpWorkTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '年度学习任务', name: 'period'},
            {
                label: '已完成学时数', name: 'finishPeriod', formatter: function (cellvalue, options, rowObject) {

                    return getFinishPeriod(rowObject);
                }
            },
            { label: '完成百分比',name: '_finish',formatter: function (cellvalue, options, rowObject) {
                if(Math.trimToZero(rowObject.period)==0) return '-'
                var progress = Math.formatFloat(getFinishPeriod(rowObject)*100/rowObject.period, 1) + "%";
               return ('<div class="progress progress-striped pos-rel" data-percent="{0}">' +
                '<div class="progress-bar progress-bar-success" style="width:{0};"></div></div>').format(progress)
            }},
            {
                label: '党校专题培训<br/>完成学时数', name: 'specialPeriod', width: 110
                , formatter: function (cellvalue, options, rowObject) {
                    var finish = (rowObject.hasArchived)?Math.trimToZero(cellvalue)
                        :Math.trimToZero(rowObject.r.specialPeriod);
                    var max = Math.trimToZero(rowObject.maxSpecialPeriod);
                    return _overflow(finish, max)
                }
            },
            {
                label: '党校日常培训<br/>完成学时数', name: 'dailyPeriod', width: 110
                , formatter: function (cellvalue, options, rowObject) {
                    var finish = (rowObject.hasArchived)?Math.trimToZero(cellvalue)
                        :Math.trimToZero(rowObject.r.dailyPeriod);
                    var max = Math.trimToZero(rowObject.maxDailyPeriod);
                    return _overflow(finish, max)
                }
            },
            {
                label: '二级党校/党委培训<br/>完成学时数', name: 'partyPeriod', width: 130
                , formatter: function (cellvalue, options, rowObject) {
                    var finish = (rowObject.hasArchived)?Math.trimToZero(cellvalue)
                        :Math.trimToZero(rowObject.r.partyPeriod);
                    var max = Math.trimToZero(rowObject.maxPartyPeriod);
                    return _overflow(finish, max)
                }
            },
            {
                label: '二级单位培训<br/>完成学时数', name: 'unitPeriod'
                , formatter: function (cellvalue, options, rowObject) {
                    var finish = (rowObject.hasArchived)?Math.trimToZero(cellvalue)
                        :Math.trimToZero(rowObject.r.unitPeriod);
                    var max = Math.trimToZero(rowObject.maxUnitPeriod);
                    return _overflow(finish, max)
                }
            },
            {
                label: '上级调训<br/>完成学时数', name: 'upperPeriod'
                , formatter: function (cellvalue, options, rowObject) {
                    var finish = (rowObject.hasArchived)?Math.trimToZero(cellvalue)
                        :Math.trimToZero(rowObject.r.upperPeriod);
                    var max = Math.trimToZero(rowObject.maxUpperPeriod);
                    return _overflow(finish, max)
                }
            },
            {label: '联系方式', name: 'user.mobile', width: 110},
            {label: '电子邮箱', name: 'user.email', width: 180, align: 'left'},
            {label: '备注', name: 'remark', width: 250},
        ]
    }).jqGrid("setFrozenColumns");

    function _overflow(finish, max) {
        if(max<=0) return finish;
        return '<span class="'+ (finish>max?"overflow":"") +'" title="完成学时数/上限">' + finish + "/" + max + '</span>'
    }

    function getFinishPeriod(rowObject){

        var finish = Math.trimToZero(rowObject.finishPeriod);
        if (!rowObject.hasArchived) {
            //console.log(rowObject.r)
            finish = 0;
            if (rowObject.maxSpecialPeriod > 0)
                finish += Math.min(Math.trimToZero(rowObject.r.specialPeriod),
                    Math.trimToZero(rowObject.maxSpecialPeriod));
            else
                finish += Math.trimToZero(rowObject.r.specialPeriod);

            if (rowObject.maxDailyPeriod > 0)
                finish += Math.min(Math.trimToZero(rowObject.r.dailyPeriod),
                    Math.trimToZero(rowObject.maxDailyPeriod));
            else
                finish += Math.trimToZero(rowObject.r.dailyPeriod);


            if (rowObject.maxPartyPeriod > 0)
                finish += Math.min(Math.trimToZero(rowObject.r.partyPeriod),
                    Math.trimToZero(rowObject.maxPartyPeriod));
            else
                finish += Math.trimToZero(rowObject.r.partyPeriod);

            if (rowObject.maxUnitPeriod > 0)
                finish += Math.min(Math.trimToZero(rowObject.r.unitPeriod),
                    Math.trimToZero(rowObject.maxUnitPeriod));
            else
                finish += Math.trimToZero(rowObject.r.unitPeriod);

            if (rowObject.maxUpperPeriod > 0)
                finish += Math.min(Math.trimToZero(rowObject.r.upperPeriod),
                    Math.trimToZero(rowObject.maxUpperPeriod));
            else
                finish += Math.trimToZero(rowObject.r.upperPeriod);
        }

        return finish;
    }
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>