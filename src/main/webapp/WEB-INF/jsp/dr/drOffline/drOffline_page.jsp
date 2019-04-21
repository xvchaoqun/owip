<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.type ||not empty param.recommendDate || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="drOffline:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/drOffline_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/drOffline_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <button id="inspectorEditBtn" class="jqOpenViewBtn btn btn-info btn-sm"
                            data-url="${ctx}/drOffline_selectMembers"
                            data-grid-id="#jqGrid"
                            data-id-name="offlineId"><i class="fa fa-edit"></i>
                        编辑推荐组成员</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="drOffline:del">
                    <button data-url="${ctx}/drOffline_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/drOffline_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
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
                            <label>年份</label>
                            <input class="form-control search-query" name="year" type="text" value="${param.year}"
                                   placeholder="请输入年份">
                        </div>
                        <div class="form-group">
                            <label>推荐类型</label>
                            <input class="form-control search-query" name="type" type="text" value="${param.type}"
                                   placeholder="请输入推荐类型">
                        </div>
                        <div class="form-group">
                            <label>推荐日期</label>
                            <input class="form-control search-query" name="recommendDate" type="text" value="${param.recommendDate}"
                                   placeholder="请输入推荐日期">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/drOffline"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/drOffline"
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
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/drOffline_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '年份',name: 'year', frozen:true},
                { label: '编号',name: 'code', width: 210, frozen:true},
                {
                    label: '推荐结果', name: '_result', formatter: function (cellvalue, options, rowObject) {
                        if (rowObject.needVoterType !=undefined){
                            var str = '';
                            str +='<button class="openView btn btn-info btn-xs" data-url="${ctx}drOffline_result?id={0}"><i class="fa fa-search"></i> 查看</button>'
                                .format(rowObject.id) + "&nbsp;";
                            return  str + '<button class="downloadBtn btn btn-success btn-xs" data-url="${ctx}/drOffline_result_export?offlineId={0}"><i class="fa fa-download"></i> 导出</button>'
                                .format(rowObject.id);
                        } else return '<button class="openView btn btn-primary btn-xs" data-url="${ctx}/drOffline_result?id={0}"><i class="fa fa-edit"></i> 编辑</button>'
                            .format(rowObject.id)
                    }, width: 150
                },
                { label: '推荐类型',name: 'type', width: 85, formatter: $.jgrid.formatter.MetaType},
                { label: '推荐日期',name: 'recommendDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
                { label: '推荐岗位',name: 'postName', width: 280, align: 'left'},
                { label: '分管工作',name: 'job', width: 180, align: 'left'},
                {label: '行政级别', name: 'adminLevel', width: 85, formatter: $.jgrid.formatter.MetaType},
                {label: '职务属性', name: 'postType', width: 120, formatter: $.jgrid.formatter.MetaType},
                {label: '所属单位', name: 'unitId', width: 200, align: 'left', formatter: $.jgrid.formatter.unit},
                {label: '单位类型', name: 'unitType', width: 120, formatter: $.jgrid.formatter.MetaType},
                {label: '所属纪实', name: 'srCode', width: 190},
                {label: '推荐组负责人', name: 'chiefMember.user.realname', width: 120, formatter: function (cellvalue, options, rowObject) {

                    var val = $.trim(cellvalue);
                    if(val=='') return '--'
                    if(rowObject.chiefMember.status!='<%=DrConstants.DR_MEMBER_STATUS_NOW%>'){
                        return '<span class="delete">{0}</span>'.format(val)
                    }
                    return val
                }},
                {
                    label: '推荐组成员', name: 'drMembers', formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == undefined || cellvalue.length == 0) return '--';
                        var names = []
                        cellvalue.forEach(function(drMember, i){
                            if (drMember.user.realname)
                                names.push(drMember.user.realname)
                        })

                        return names.join("，")
                    }, width: 250
                },
                { label: '推荐票样',name: 'ballotSample', width: 150, formatter: function (cellvalue, options, rowObject) {

                        var hasUpload = $.trim(rowObject.ballotSample)!='';
                        var str= ('<button class="popupBtn btn btn-primary btn-xs" '
                            + 'data-url="${ctx}/dfOffline_uploadBallotSample?offlineId={0}">'
                            + '<i class="fa fa-upload"></i> {1}</button>')
                            .format(rowObject.id, hasUpload?"更新":"上传");
                        if(hasUpload){
                            str += ('&nbsp;&nbsp;<button class="downloadBtn btn btn-xs btn-success" ' +
                             'data-url="${ctx}/attach/download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button>')
                                .format(rowObject.ballotSample, "推荐票样")
                        }
                        return str;
                }},
                {label: '备注', name: 'remark', width: 350}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>