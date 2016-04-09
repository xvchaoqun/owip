<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="closeView btn btn-mini btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">审核记录</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8">
                <div class="space-4"></div>
                <table id="jqGrid2" class="jqGrid2 table-striped"> </table>
                <div id="jqGridPager2"> </div>
            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
<script>
    $("#jqGrid2").jqGrid({
        multiselect:false,
        pager:"jqGridPager2",
        url: "${ctx}/applyApprovalLog_data?callback=?&id=${memberOutflow.id}&type=${APPLY_APPROVAL_LOG_TYPE_MEMBER_OUTFLOW}",
        colModel: [
            { label: '阶段',  name: 'stage', width: 200 },
            { label: '审核时间',  name: 'createTime', width: 200 },
            { label: '审核人', name: 'user.realname',resizable:false, width: 150 },
            { label:'审核结果',  name: 'status', width: 100, formatter:function(cellvalue, options, rowObject){
                return cellvalue==0?"未通过":"通过";
            } },
            { label:'IP',  name: 'ip', width: 150 }
        ],
        gridComplete:function(){
            $(window).triggerHandler('resize.jqGrid2');
        }
    });
</script>