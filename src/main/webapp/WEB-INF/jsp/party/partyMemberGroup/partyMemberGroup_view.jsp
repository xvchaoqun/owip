<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="partyMemberGroup:edit">
        <button class="popupBtn btn btn-primary btn-xs"
                data-url="${ctx}/partyMemberGroup_au?type=view&partyId=${param.partyId}">
            <i class="fa fa-users"></i> 添加${_p_partyName}班子
        </button>
        <a href="javascript:;"
           data-url="${ctx}/partyMemberGroup_au"
           data-grid-id="#jqGrid2"
           data-querystr="&type=view&partyId=${param.partyId}"
           class="jqOpenViewBtn btn btn-primary btn-xs">
            <i class="fa fa-edit"></i> 修改信息</a>
    </shiro:hasPermission>

    <%--<button onclick="_editMember()" class="btn btn-warning btn-xs">
        <i class="fa fa-user"></i> 编辑委员
    </button>--%>

    <shiro:hasPermission name="partyMemberGroup:del">
        <button data-url="${ctx}/partyMemberGroup_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-xs">
            <i class="fa fa-trash"></i> 删除
        </button>
    </shiro:hasPermission>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped"></table>
<div id="jqGridPager2"></div>
<script>
    $("#jqGrid2").jqGrid({
        //multiselect:false,
        pager: "jqGridPager2",
        url: '${ctx}/partyMemberGroup_data?callback=?&partyId=${param.partyId}',
        colModel: [
            {
                label: '名称',
                name: 'name',
                align: 'left',
                width: 400,
                formatter: function (cellvalue, options, rowObject) {
                    var str = '<span class="label label-sm label-primary arrowed-in arrowed-in-right" style="display: inline!important;"> 现任班子</span>&nbsp;';
                    return (rowObject.isPresent) ? str + cellvalue : cellvalue;
                },
                frozen: true
            },
            {label: '所属${_p_partyName}', name: 'partyId', width: 280, formatter:function(cellvalue, options, rowObject){
                return $.party(cellvalue);
            }},
            {label: '应换届时间', name: 'tranTime', width: 130, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {
                label: '实际换届时间',
                name: 'actualTranTime',
                width: 130,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {label: '任命时间', name: 'appointTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {
                hidden: true, name: 'isPresent', formatter: function (cellvalue, options, rowObject) {
                    return (rowObject.isPresent) ? 1 : 0;
                }
            }
        ],
        rowattr: function (rowData, currentObj, rowId) {
            if (rowData.isPresent) {
                //console.log(rowData)
                return {'class': 'success'}
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    function _reload() {
        $("#modal").modal('hide');
        $("#view-box .tab-content").loadPage("${ctx}/partyMemberGroup_view?${cm:encodeQueryString(pageContext.request.queryString)}");
    }

</script>