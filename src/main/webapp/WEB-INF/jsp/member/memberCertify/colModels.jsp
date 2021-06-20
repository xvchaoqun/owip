<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_CERTIFY_STATUS_BACK" value="<%=MemberConstants.MEMBER_CERTIFY_STATUS_BACK%>"/>

<c:set var="JASPER_PRINT_TYPE_MEMBER_CERTIFY" value="<%=SystemConstants.JASPER_PRINT_TYPE_MEMBER_CERTIFY%>"/>
<script>
    var colModels = function () {
    };
    colModels.colModel = [
        { label: '年份',name: 'year', width:70, frozen: true},
        { label: '学工号',name: 'user.code', width:120, frozen: true},
        { label: '姓名',name: 'user.realname', frozen: true},
        { label: '介绍信编号',name: 'sn'},
        <c:if test="${cls==7}">
        { label: '介绍信打印', width: 110, formatter:function(cellvalue, options, rowObject){

                var html = '<button class="openView btn btn-primary btn-xs"'
                    +' data-url="${ctx}/report/printPreview?type=${JASPER_PRINT_TYPE_MEMBER_CERTIFY}&ids={0}"><i class="fa fa-print"></i> 打印</button>'
                        .format(rowObject.id);
                return html;
            }},
        </c:if>
        <c:if test="${cls==0}">
        { label: '申请状态',name: 'status', width: 140, formatter: function (cellvalue, options, rowObject) {

                if(rowObject.isBack) return '返回修改'
                return ('<font color="{0}">{1}</font>').format(cellvalue == ${MEMBER_CERTIFY_STATUS_BACK}?"orange":"green",
                    _cMap.MEMBER_CERTIFY_STATUS_MAP[rowObject.status])
            }},
        </c:if>
        {
            label: '所在党组织', name: 'party', width: 500, formatter: function (cellvalue, options, rowObject) {
                return $.party(rowObject.partyId, rowObject.branchId);
            }, sortable: true, align: 'left'
        },
        { label: '政治面貌',name: 'politicalStatus', formatter: function (cellvalue, options, rowObject) {
                return _cMap.MEMBER_POLITICAL_STATUS_MAP[cellvalue];
            }},
        { label: '原单位',name: 'fromUnit', width:200, align:'left'},
        { label: '介绍信抬头',name: 'toTitle', width:200, align:'left'},
        { label: '拟去往的工作学习单位',name: 'toUnit', width:200, align:'left'},
        <c:if test="${cls==1}">
        { label: '返回修改原因',name: 'reason', width:200, align: 'left'},
        </c:if>
        { label: '介绍信日期',name: 'certifyDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
        { label: '申请时间',name: 'applyTime', width: 150},{ name: 'status', hidden:true}
    ];
</script>