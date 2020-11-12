<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>组织生活记录</h3>
</div>
<div class="modal-body" style="overflow:auto">
           <table id="jqGrid_popup" class="table-striped"> </table>
           <div id="jqGridPager_popup"> </div>
</div>
<script>
    var url = "";
    if (${param.cls==2}) {
        url = "${ctx}/pm/pm3Meeting_data?callback=?&year=${param.year}&partyId=${param.partyId}&branchId=${param.branchId}&type=${param.type}&isSum=${param.isSum}&cls=3";
    }
    if (${param.cls==1}) {
        url = "${ctx}/pm/pm3Meeting_data?callback=?&year=${param.year}&month=${param.month}&partyId=${param.partyId}&branchId=${param.branchId}&type=${param.type}&isSum=${param.isSum}&cls=3";
    }
    $("#jqGrid_popup").jqGrid({
        multiselect:false,
        height:390,
        width:965,
        rowNum:10,
        ondblClickRow:function(){},
        pager:"jqGridPager_popup",
        url: url,
        colModel: [
            { label: '年份', name: 'year', width:80},
            /*{ label: '季度', name: 'quarter',width:80, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined)
                        return '--'
                    return '第'+cellvalue+'季度'
                }
            },*/
            { label: '月份', name: 'month', width:80, frozen:true, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined)
                        return '--'
                    return cellvalue+'月'
                }
            },
            { label: '所属${_p_partyName}',  name: 'party.name',align:'left', width: 252},
            { label: '所属党支部',  name: 'branch.name',align:'left', width: 252},
            {label: '主题', name: 'name', width:252,align:'left',frozen:true},

            {label: '开始时间', name: 'startTime', width:150,
                formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y-m-d H:i'}
            },
            {label: '结束时间', name: 'endTime', width:150,
                formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y-m-d H:i'}
            },
            { label: '地点',name: 'address',width:100},
            { label: '主要内容',name: 'content',width:200},
        ],
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");

    function _pop_reload(){
        pop_reload();
        $(window).triggerHandler('resize.jqGrid');
    }
</script>