<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>党支部活动记录</h3>
</div>
<div class="modal-body" style="overflow:auto">
           <table id="jqGrid_popup" class="table-striped"> </table>
           <div id="jqGridPager_popup"> </div>
</div>
<script>

    $("#jqGrid_popup").jqGrid({
        multiselect:false,
        height:390,
        width:965,
        rowNum:10,
        ondblClickRow:function(){},
        pager:"jqGridPager_popup",
        url: "${ctx}/pmMeeting2_data?callback=?&year=${param.year}&quarter=${param.quarter}&month=${param.month}&partyId=${param.partyId}&branchId=${param.branchId}&cls=1&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: [
            { label: '年份', name: 'year', width:80},
            { label: '季度', name: 'quarter',width:80, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined)
                        return '--'
                    return '第'+cellvalue+'季度'
                }
            },
            { label: '所属党支部',  name: 'branch.name',align:'left', width: 250},
            { label: '活动名称',name: 'type', width:180,align:'left', formatter:function(cellvalue, options, rowObject){
                    var type1=rowObject.type1;
                    var type2=rowObject.type2;
                    if(type1==undefined) return '--';
                    return '<a href="javascript:;" class="openView" data-url="${ctx}/pmMeeting2_au?edit=false&id={0}">{1}</a>'.format( rowObject.id,type2==null?_cMap.PARTY_MEETING_MAP[type1]:_cMap.PARTY_MEETING_MAP[type1]+","+_cMap.PARTY_MEETING_MAP[type2]);
                }
            },
            { label: '次数',name: 'number', formatter: function (cellvalue, options, rowObject) {
                    var number1=rowObject.number1;
                    var number2=rowObject.number2;
                    if(number1==undefined)
                        return '--'
                    return number2==undefined?'第'+number1+'次':'第'+number1+'次'+','+'第'+number2+'次';
                }},
            {label: '实际时间', name: 'date', width:150,
                formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y.m.d H:i', newformat: 'Y-m-d H:i'}
            },
            { label: '地点',name: 'address'},
            { label: '主要内容',name: 'shortContent',width:200},
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