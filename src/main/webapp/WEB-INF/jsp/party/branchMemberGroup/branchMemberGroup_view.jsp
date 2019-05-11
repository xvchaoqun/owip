<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="jqgrid-vertical-offset buttons">
    <button class="popupBtn btn btn-primary btn-xs"
            data-url="${ctx}/branchMemberGroup_au?type=view&branchId=${param.branchId}">
        <i class="fa fa-users"></i> 添加支部委员会
    </button>
    <a href="javascript:;"
       data-url="${ctx}/branchMemberGroup_au"
       data-grid-id="#jqGrid2"
       data-querystr="&type=view&branchId=${param.branchId}"
       class="jqOpenViewBtn btn btn-primary btn-xs" >
        <i class="fa fa-edit"></i> 修改信息</a>

    <a href="javascript:;"
       data-url="${ctx}/branch_member"
       data-grid-id="#jqGrid2"
       class="jqOpenViewBtn btn btn-warning btn-xs" >
        <i class="fa fa-user"></i> 编辑委员</a>

    <shiro:hasPermission name="branchMemberGroup:del">
        <button data-url="${ctx}/branchMemberGroup_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-xs">
            <i class="fa fa-trash"></i> 删除
        </button>
    </shiro:hasPermission>
</div>
            <div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped"> </table>
<div id="jqGridPager2"> </div>
<script>
    $("#jqGrid2").jqGrid({
        pager:"jqGridPager2",
        url: '${ctx}/branchMemberGroup_data?callback=?&branchId=${param.branchId}',
        colModel: [
            { label: '名称',  name: 'name',align:'left',width: 400,formatter:function(cellvalue, options, rowObject){
                var str = '<span class="label label-sm label-primary arrowed-in arrowed-in-right" style="display: inline!important;"> 现任委员会</span>&nbsp;';
                return (rowObject.isPresent)?str+cellvalue:cellvalue;
            }, frozen:true},
            {
                label: '所属组织机构', name: 'party',  width: 450,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }, frozen:true
            },
            { label: '应换届时间', name: 'tranTime', width: 130, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'} },
            { label: '实际换届时间', name: 'actualTranTime', width: 130, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'} },
            { label: '任命时间', name: 'appointTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'} },
            { label:'发文号',  name: 'dispatchCode', width: 180},
            {  hidden:true, name: 'isPresent',formatter:function(cellvalue, options, rowObject){
                return (rowObject.isPresent)?1:0;
            }}
        ],
        rowattr: function(rowData, currentObj, rowId)
        {
            if(rowData.isPresent) {
                //console.log(rowData)
                return {'class':'success'}
            }
        }
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid2');
    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").loadPage("${ctx}/branchMemberGroup_view?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>