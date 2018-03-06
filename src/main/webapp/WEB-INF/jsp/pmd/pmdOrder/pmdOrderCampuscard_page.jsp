<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">${uv.realname}-历史支付订单</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="jqgrid-vertical-offset buttons">
                </div>
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    function _reload2(){
        //SysMsg.info("操作成功。");
        $("#jqGrid2").trigger("reloadGrid");
    }
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        url: '${ctx}/pmd/pmdOrderCampuscard_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '订单号',name: 'sn', width:150},
            { label: '订单状态',name: 'isClosed', width:150, formatter: function (cellvalue, options, rowObject) {

                if(rowObject.isClosed) return '已关闭'
                return ('<button class="popupBtn btn btn-info btn-xs" ' +
                'data-url="${ctx}/pmd/pmdOrderCampuscard_query?sn={0}&code={1}"><i class="fa fa-search"></i> 查询支付接口</button>')
                        .format(rowObject.sn, rowObject.payer);
            }},
            { label: '关闭订单',name: '_close', width:150, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.isSuccess) return '-'
                if(rowObject.isClosed) return '已关闭'
                return ('<button class="confirm btn btn-danger btn-xs" data-title="关闭订单" data-msg="确定关闭订单？（请确保订单未支付后操作）" ' +
                'data-callback="_reload2" data-url="${ctx}/pmd/pmdOrderCampuscard_closeTrade?sn={0}"><i class="fa fa-close"></i> 关闭订单</button>')
                        .format(rowObject.sn);
            }},
            { label: '缴费人账号',name: 'payer', width:120},
            { label: '缴费人账号类型',name: 'payertype', width:150, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return ''
                if(cellvalue==1) return '学工号';
                return cellvalue;
            }},
            { label: '缴费人姓名',name: 'payername'},
            { label: '该次缴费金额',name: 'amt'},
            { label: '收费商户账号',name: 'macc'},
            { label: '缴费说明',name: 'commnet'},
            { label: '提交人的学工号',name: 'snoIdName', width:150},
            { label: '支付状态',name: 'isSuccess', formatter: $.jgrid.formatter.TRUEFALSE,
                formatoptions: {on: '已支付', off:'未支付'}},
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
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");

    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");

        if (ids.length > 1) {
            $("#delayBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            var hasReport = (rowData.hasReport == "true");
            var canReport = (rowData.canReport == "true");
            $("#delayBtn").prop("disabled", hasReport||canReport);
        }
    }
</script>