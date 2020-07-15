<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>三会一课记录</h3>
</div>
<div class="modal-body" style="overflow:auto">
           <table id="jqGrid_popup" class="table-striped"> </table>
           <div id="jqGridPager_popup"> </div>
</div>
<script>
    var url = "${ctx}/pmMeeting_data?callback=?&year=${param.year}&partyId=${param.partyId}&branchId=${param.branchId}&type=${param.type}&cls=3";
    if (${param.cls==2}) {
        url = "${ctx}/pmMeeting_data?callback=?&year=${param.year}&quarter=${param.quarter}&partyId=${param.partyId}&branchId=${param.branchId}&type=${param.type}&cls=3";
    }
    if (${param.cls==3}) {
        url = "${ctx}/pmMeeting_data?callback=?&year=${param.year}&quarter=${param.quarter}&month=${param.month}&partyId=${param.partyId}&branchId=${param.branchId}&type=${param.type}&cls=3";
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
            { label: '季度', name: 'quarter',width:80, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined)
                        return '--'
                    return '第'+cellvalue+'季度'
                }
            },
            { label: '所属党支部',  name: 'branch.name',align:'left', width: 250},
            {label: '${param.type!=5?"会议":"主题党日活动"}名称', name: 'name', width:350,align:'left',frozen:true},
            {label: '${param.type!=5?"会议议题":"活动主题"}', name: 'issue', width:350},

            {label: '实际时间', name: 'date', width:150,
                formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y-m-d H:i'}
            },
            { label: '地点',name: 'address'},
            { label: '主要内容',name: 'content',width:200},
        ],
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");

    function _pop_reload(){
        pop_reload();
        $(window).triggerHandler('resize.jqGrid');
    }
    $("#submitBtn", "#modalForm").click(function () {
        $("#modalForm").submit();
        return false;
    })
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        _pop_reload();
                    }
                }
            });
        }
    });
</script>