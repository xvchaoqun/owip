<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="closeView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">认定记录</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
            </div>
        </div>
        <!-- /.widget-main -->
    </div>
    <!-- /.widget-body -->
</div>
<!-- /.widget-box -->
<script>
    $("#jqGrid2").jqGrid({
        multiselect: false,
        pager: null,
        datatype: "local",
        data: ${cm:toJSONArray(records)},
        colModel: [
            {label: '提交人', name: 'submitUser.realname'},
            {label: '提交IP', name: 'submitIp'},
            {label: '提交时间', name: 'submitTime', width: 150},
            {label: '修改人', name: 'updateUser.realname'},
            {label: '修改IP', name: 'updateIp'},
            {label: '修改时间', name: 'updateTime', width: 150},
            {label: '状态', name: 'status', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.VERIFY_STATUS_MAP[cellvalue]
            }},
            {label: '工作证号', name: 'cadre.user.code', frozen: true},
            {
                label: '姓名',
                name: 'cadre.user.realname',
                width: 120,
                formatter: function (cellvalue, options, rowObject) {
                    return '<a href="javascript:;" class="openView" data-url="${ctx}/cadre_view?cadreId={0}">{1}</a>'
                            .format(rowObject.cadre.id, cellvalue);
                },
                frozen: true
            },
            {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 350},
            {
                label: '认定类别', name: 'type', width: 220, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.VERIFY_WORK_TIME_TYPE_MAP[cellvalue]
            }
            },
            {label: '认定前参加工作时间', width: 180, name: 'oldWorkTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '认定后参加工作时间', width: 180, name: 'verifyWorkTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '材料名称', name: 'materialName', width: 200},
            {label: '形成时间', name: 'materialTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {
                label: '记载的参加工作时间',
                width: 150,
                name: 'materialWorkTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {label: '形成时间', name: 'adTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '备注', name: 'remark', width: 500}
        ],
        rowattr: function (rowData, currentObj, rowId) {
            if (rowData.status == '${VERIFY_STATUS_DEL}') {
                //console.log(rowData)
                return {'class': 'danger'}
            }
        }
    })
    $(window).triggerHandler('resize.jqGrid2');
</script>