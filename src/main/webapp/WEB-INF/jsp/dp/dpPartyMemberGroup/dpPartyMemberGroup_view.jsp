<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped"></table>
<div id="jqGridPager2"></div>
<script>
    $("#jqGrid2").jqGrid({
        //multiselect:false,
        pager: "jqGridPager2",
        url: '${ctx}/dp/dpPartyMemberGroup_data?callback=?&partyId=${param.partyId}',
        colModel: [
            {
                label: '名称',
                name: 'name',
                align: 'left',
                width: 400,
                formatter: function (cellvalue, options, rowObject) {
                    var str = '<span class="label label-sm label-primary arrowed-in arrowed-in-right" style="display: inline!important;"> 现任委员会</span>&nbsp;';
                    return (rowObject.isPresent) ? str + cellvalue : cellvalue;
                },
                frozen: true
            },
            {label: '所属民主党派', name: 'dpParty.name', width: 280, formatter: function (cellvalue, options, rowObject) {
                    var _dpPartyView = null;
                    if ($.inArray("dpParty:list", _permissions) >= 0 || $.inArray("dpParty:*", _permissions) >= 0)
                        _dpPartyView = '<a href="javascript:;" class="openView" data-url="{2}/dp/dpParty_view?id={0}">{1}</a>'
                            .format(rowObject.partyId, cellvalue, ctx);
                    if (cellvalue != ''){
                        return '<span class="{0}">{1}</span>'.format(rowObject.isDeleted ? "delete" : "", _dpPartyView);
                    }
                    return "--";
                }},
            {label: '委员会届数', name: 'groupSession', width: 100},
            {label: '应换届时间', name: 'tranTime', width: 130, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {
                label: '撤销时间',
                name: 'actualTranTime',
                width: 130,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {label: '成立时间', name: 'appointTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {
                hidden: true, name: 'isPresent', formatter: function (cellvalue, options, rowObject) {
                    return (rowObject.isPresent) ? 1 : 0;
                }
            },
            {label: '备注', name: 'remark', width: 180}

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
        $("#dp-content").loadPage("${ctx}/dp/dpPartyMemberGroup_view?${cm:encodeQueryString(pageContext.request.queryString)}");
    }

</script>