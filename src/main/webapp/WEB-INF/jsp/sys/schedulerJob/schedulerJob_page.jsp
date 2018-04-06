<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv">
            <div class="jqgrid-vertical-offset buttons">
                <a class="popupBtn btn btn-info btn-sm"
                   data-url="${ctx}/schedulerJob_au"><i class="fa fa-plus"></i> 添加定时任务</a>

                <button class="jqEditBtn btn btn-primary btn-sm" data-url="${ctx}/schedulerJob_au">
                    <i class="fa fa-edit"></i> 更新
                </button>
                <button id="startBtn" class="jqItemBtn btn btn-success btn-sm"
                   data-url="${ctx}/schedulerJob_start" data-title="启动定时任务"
                   data-msg="确定启动这个定时任务吗？" data-callback="_reload"><i class="fa fa-clock-o"></i> 启动任务</button>
                <button id="stopBtn" class="jqItemBtn btn btn-warning btn-sm"
                   data-url="${ctx}/schedulerJob_stop" data-title="暂停定时任务"
                   data-msg="确定暂停这个定时任务吗？" data-callback="_reload"><i class="fa fa-dot-circle-o"></i> 暂停任务</button>
                <button class="jqItemBtn btn btn-danger btn-sm"
                   data-url="${ctx}/schedulerJob_del" data-title="删除定时任务"
                   data-msg="确定删除这个定时任务吗？" data-callback="_reload"><i class="fa fa-trash"></i> 删除</button>
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
    var allJobsReg;
    var runJobsReg;
    $("#jqGrid").jqGrid({
        url: '${ctx}/schedulerJob_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '任务名称', name: 'name', width:300, align:'left'},
            { label: '执行类', name: 'clazz', width: 300, align:'left'},
            { label: 'cron表达式', name: 'cron', width: 120, align:'left'},
            { label: '启动状态', name: '_status', formatter:function(cellvalue, options, rowObject){
                return (allJobsReg.test(rowObject.name))?'<span class="text-success bolder">已启动</span>':'未启动'
            }},
            { label: '执行状态', name: '_runStatus', formatter:function(cellvalue, options, rowObject){
                return (runJobsReg.test(rowObject.name))?'<span class="text-success bolder">执行中</span>':'未执行'
            }},
            { label: '任务描述', name: 'summary', width: 550, align:'left', formatter: $.jgrid.formatter.NoMultiSpace}
        ],
        beforeProcessing:function(data, status, xhr){
          //console.log(data)
            allJobsReg = new RegExp(data.allJobs||null);
            runJobsReg = new RegExp(data.runJobs||null);
        },
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
            var isStart = (allJobsReg.test(rowData.name));
            var isRun = (runJobsReg.test(rowData.name));

            $("#startBtn").prop("disabled", isStart);
            $("#stopBtn").prop("disabled", !isStart);
        }
    }
</script>