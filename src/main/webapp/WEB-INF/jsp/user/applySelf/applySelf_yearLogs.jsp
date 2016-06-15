<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!-- PAGE CONTENT BEGINS -->
<div class="widget-box transparent" id="useLogs">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="backBtn btn btn-mini btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">${currentYear}年度所有的因私出国（境）申请记录</a>
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
        $("#item-content").hide().load("${ctx}/${param.type=='user'?'user/':''}applySelf_view?id=${applySelf.id}&type=${param.type}&approvalTypeId=${param.approvalTypeId}").fadeIn();
    });

    $("#jqGrid2").jqGrid({
        //forceFit:true,
        pager:"jqGridPager2",
        url: "${ctx}/applySelf_yearLogs_data?callback=?&cadreId=${applySelf.cadreId}&year=${currentYear}",
        colModel: [
            { label: '序号', align:'center', name: 'id', width: 80 ,formatter:function(cellvalue, options, rowObject){
                return "S{0}".format(rowObject.id);
            },frozen:true},
            { label: '申请日期', align:'center', name: 'applyDate', width: 100,frozen:true },
            { label: '出发时间', align:'center', name: 'startDate', width: 100 },
            { label: '回国时间', align:'center', name: 'endDate', width: 100 },
            { label: '出行天数', align:'center', name: 'day', width: 80,formatter:function(cellvalue, options, rowObject){
                return DateDiff(rowObject.startDate, rowObject.endDate);
            }},
            { label:'前往国家或地区', align:'center',name: 'toCountry', width: 180},
            { label:'因私出国（境）事由', align:'center', name: 'reason', width: 200, formatter:function(cellvalue, options, rowObject){
                return cellvalue.replace(/\+\+\+/g, ',');
            }},
            { label: '审批情况', align:'center', name: 'status', width: 100 , formatter:function(cellvalue, options, rowObject){
                var tdBean = rowObject.approvalTdBeanMap[0];
                return processTdBean(tdBean)
            }}
        ],
        gridComplete:function(){
            $(window).triggerHandler('resize.jqGrid2');
        }
    });
    function processTdBean(tdBean){

        var type = tdBean.tdType;
        console.log(type)
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
    }

</script>