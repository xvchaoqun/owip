<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="backBtn btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">${currentYear}年度请假记录</a>
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

    $(".backBtn").click(function(){
        $("#body-content-view").hide().load("${ctx}/${param.type=='user'?'user/':''}cla/claApply_view?id=${claApply.id}&type=${param.type}&approvalTypeId=${param.approvalTypeId}").fadeIn();
    });

    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager:"jqGridPager2",
        url: "${ctx}/cla/claApply_yearLogs_data?callback=?&cadreId=${claApply.cadreId}&year=${currentYear}",
        colModel: [
            { label: '序号', name: 'id', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return "L{0}".format(rowObject.id);
            },frozen:true},
            { label: '申请日期', name: 'applyDate',frozen:true, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'} },
            { label: '出发时间', name: 'startTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d H:i'} },
            { label: '返回时间', name: 'endTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d H:i'} },
            { label: '出行天数', name: 'day', width: 80,formatter:function(cellvalue, options, rowObject){
                return $.dayDiff(rowObject.startTime, rowObject.endTime);
            }},
            { label:'目的地',name: 'destination', width: 180},
            { label:'请假事由', name: 'reason', width: 200},
            { label: '审批情况', name: 'status' , formatter:function(cellvalue, options, rowObject){
                var tdBean = rowObject.approvalTdBeanMap[0];
                return (function(tdBean){
                    var type = tdBean.tdType;
                    //console.log(type);
                    var html = "";
                    switch (type){
                        //not_approval
                        case 2:
                        case 3:
                        case 4: html = "待审批"; break;
                        case 5: html = "未通过审批"; break;
                        case 6: html = "通过审批"; break;
                    }

                    return html;
                })(tdBean);
            }},
            { label:'备注', name: 'reason', width: 200, formatter:function(cellvalue, options, rowObject){
                if(rowObject.usePassport==undefined) return "--";
                if(rowObject.usePassport==0)
                    return "取消行程，未持证件出国（境）";
                return "--"
            }}
        ]
    });
    $(window).triggerHandler('resize.jqGrid2');
</script>