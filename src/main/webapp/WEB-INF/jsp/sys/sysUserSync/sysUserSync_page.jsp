<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/sysLog_au"
             data-url-page="${ctx}/sysLog"
             data-url-export="${ctx}/sysLog_data"
             data-url-co="${ctx}/sysLog_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="jqgrid-vertical-offset buttons">
                <a data-type="${SYNC_TYPE_JZG}" class="syncBtn btn btn-info btn-sm btn-purple" data-loading-text="<i class='fa fa-refresh fa-spin'></i> 人事库同步中..." autocomplete="off"><i class="fa fa-refresh"></i> 同步人事库</a>
                <a  data-type="${SYNC_TYPE_BKS}" class="syncBtn btn btn-info btn-sm btn-grey" data-loading-text="<i class='fa fa-refresh fa-spin'></i> 本科生库同步中..." autocomplete="off"><i class="fa fa-refresh"></i> 同步本科生库</a>
                <a data-type="${SYNC_TYPE_YJS}" class="syncBtn btn btn-info btn-sm btn-pink" data-loading-text="<i class='fa fa-refresh fa-spin'></i> 研究生库同步中..." autocomplete="off"><i class="fa fa-refresh"></i> 同步研究生库</a>
                <a data-type="${SYNC_TYPE_ABROAD}" class="syncBtn btn btn-info btn-sm btn-pink" data-loading-text="<i class='fa fa-refresh fa-spin'></i> 教职工党员出国境信息同步中..." autocomplete="off"><i class="fa fa-refresh"></i> 同步教职工党员出国境信息</a>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"> </table>
            <div id="jqGridPager"> </div>
        </div><div id="item-content"></div>
    </div>
</div>

<script>
    $("#jqGrid").jqGrid({
        multiselect:false,
        url: '${ctx}/sysUserSync_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '类型',align:'center', name: 'type', width: 200, formatter:function(cellvalue, options, rowObject){
                return _cMap.SYNC_TYPE_MAP[cellvalue];
            },frozen:true},
            { label: '触发方式', name: 'autoStart', width: 80 , formatter:function(cellvalue, options, rowObject){
                return rowObject.autoStart?"系统自动":"功能按钮";
            },frozen:true},
            { label: '是否结束', name: 'isStop', width: 80 , formatter:$.jgrid.formatter.TRUEFALSE,frozen:true},
            { label: '是否自动结束', name: 'autoStop', width: 120, formatter:$.jgrid.formatter.TRUEFALSE},
            { label: '当前记录数', name: 'currentCount', width: 100},
            { label: '总记录数', name: 'totalCount', width: 100},
            { label: '开始时间', name: 'startTime', width: 150},
            { label: '结束时间', name: 'endTime', width: 150},
            { label: '更新时间', name: 'updateTime', width: 150},
            { label: '操作', name: 'stopBtn', width: 150, formatter:function(cellvalue, options, rowObject){

                return rowObject.isStop?'':'<button class="btn btn-danger btn-xs" onclick="_stop({0})">'.format(rowObject.id)
                        +'<i class="fa fa-stop-circle-o"></i> 强制结束</button>';
            }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    function _stop(id){

        $.post("${ctx}/sync_stop",{id:id},function(ret){
            if (ret.success) {
                //SysMsg.success('操作成功。', '成功');
                $("#jqGrid").trigger("reloadGrid");
            }
        })
    }
    $(".syncBtn").click(function(){
        var $this = $(this);
        bootbox.confirm("确定同步（将耗费很长时间）？", function (result) {
            if (result) {
                var $btn = $this.button('loading')
                $.post("${ctx}/sync_user_batch",{type:$this.data("type")},function(ret){
                    if(ret.success){
                        $btn.button('reset');
                        SysMsg.success('同步成功。', '成功');
                        //clearTimeout(t);
                    }
                });
                setTimeout(function(){
                    $("#jqGrid").trigger("reloadGrid");
                }, 2000);
            }
        });
    });
</script>