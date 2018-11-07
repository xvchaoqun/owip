<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv">
            <div class="jqgrid-vertical-offset buttons">
                <a class="openView btn btn-info btn-sm"
                   data-url="${ctx}/sysRole_au"
                   data-open-by="page"><i class="fa fa-plus"></i> 添加角色</a>

                <button class="jqEditBtn btn btn-warning btn-sm" data-url="${ctx}/sysRole_au"
                        data-open-by="page">
                    <i class="fa fa-edit"></i> 更新权限
                </button>

                <button id="userHoldBtn" class="jqItemBtn btn btn-success btn-sm"
                        data-url="${ctx}/sysRole_updateIsSysHold"
                        data-callback="_reload"
                        data-msg="确定修改该角色为手动设定吗？">
                    <i class="fa fa-unlock"></i> 手动设定
                </button>
                <button id="sysHoldBtn" class="jqItemBtn btn btn-primary btn-sm"
                        data-url="${ctx}/sysRole_updateIsSysHold"
                        data-callback="_reload"
                        data-msg="确定修改该角色为系统自动维护(设置后不允许手动给某个账号指定该角色）">
                    <i class="fa fa-lock"></i> 系统自动设定
                </button>
                <button class="jqBatchBtn btn btn-danger btn-sm"
                        data-url="${ctx}/sysRole_del" data-title="删除"
                        data-msg="确定删除这{0}个角色吗？" data-callback="_reload"><i class="fa fa-trash"></i> 删除</button>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"> </table>
            <div id="jqGridPager"> </div>
        </div><div id="body-content-view"></div>
    </div>
</div>

<script>
    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/sysRole_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '系统代码', name: 'role', width:200, align:'left'},
            { label: '角色名称', name: 'description', width: 300, align:'left'},
            {
                label: '排序', align: 'center', width: 90, index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: "${ctx}/sysRole_changeOrder"}
            },
            { label: '设定级别', name: 'isSysHold', width: 120, formatter: $.jgrid.formatter.TRUEFALSE,
                formatoptions:{on:'<span class="text-danger bolder">系统自动设定</span>',
                    off:'<span class="text-success bolder">手动设定</span>'}},
            {label:'权限拥有人', name:'userCount', width: 110, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '-'
                return ('<button class="popupBtn btn btn-primary btn-xs" data-width="950" data-url="${ctx}/sysRole_users?roleId={0}">' +
                    '<i class="fa fa-search"></i> 查看({1})</button>')
                    .format(rowObject.id, cellvalue);
            }},
            { label: '备注', name: 'remark', width: 850, align:'left', formatter: $.jgrid.formatter.NoMultiSpace},
            {name:'_isSysHold', hidden:true, formatter:function(cellvalue, options, rowObject){
                return rowObject.isSysHold;
            }}
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");
        if (ids.length > 1) {
            $("#startBtn,#stopBtn").prop("disabled", true);
        } else if (ids.length == 1) {

            var rowData = $(grid).getRowData(ids[0]);
            var isSysHold = (rowData._isSysHold=='true');

            $("#userHoldBtn").prop("disabled", !isSysHold);
            $("#sysHoldBtn").prop("disabled", isSysHold);
        }
    }
</script>