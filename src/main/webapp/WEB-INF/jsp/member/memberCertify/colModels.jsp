<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_GROW" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_GROW%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>

<c:set var="MEMBER_CERTIFY_STATUS_MAP" value="<%=MemberConstants.MEMBER_CERTIFY_STATUS_MAP%>"/>
<c:set var="MEMBER_CERTIFY_STATUS_BACK" value="<%=MemberConstants.MEMBER_CERTIFY_STATUS_BACK%>"/>
<c:set var="MEMBER_CERTIFY_STATUS_APPLY" value="<%=MemberConstants.MEMBER_CERTIFY_STATUS_APPLY%>"/>
<c:set var="MEMBER_CERTIFY_STATUS_PARTY_VERIFY" value="<%=MemberConstants.MEMBER_CERTIFY_STATUS_PARTY_VERIFY%>"/>
<c:set var="MEMBER_CERTIFY_STATUS_OW_VERIFY" value="<%=MemberConstants.MEMBER_CERTIFY_STATUS_OW_VERIFY%>"/>

<c:set var="JASPER_PRINT_TYPE_MEMBER_CERTIFY" value="<%=SystemConstants.JASPER_PRINT_TYPE_MEMBER_CERTIFY%>"/>
<script>
    var colModels = function () {
    };
    colModels.colModel = [
        { label: '年份',name: 'year', width:70},
        { label: '学工号',name: 'user.code', width:120},
        { label: '姓名',name: 'user.realname'},
        { label: '介绍信编号',name: 'sn'},
        <c:if test="${cls==7}">
        { label: '介绍信打印', width: 110, formatter:function(cellvalue, options, rowObject){

                var html = '<button class="openView btn btn-primary btn-xs"'
                    +' data-url="${ctx}/report/printPreview?type=${JASPER_PRINT_TYPE_MEMBER_CERTIFY}&ids[]={0}"><i class="fa fa-print"></i> 打印</button>'
                        .format(rowObject.id);
                return html;
            }},
        </c:if>
        <c:if test="${cls==0}">
        { label: '申请状态',name: 'status', width: 140, formatter: function (cellvalue, options, rowbjects) {
                if (cellvalue == ${MEMBER_CERTIFY_STATUS_BACK}) {
                    return '<font color="orange">${MEMBER_CERTIFY_STATUS_MAP.get(MEMBER_CERTIFY_STATUS_BACK)}</font>';
                }else if (cellvalue == ${MEMBER_CERTIFY_STATUS_APPLY}) {
                    return '<font color="green">${MEMBER_CERTIFY_STATUS_MAP.get(MEMBER_CERTIFY_STATUS_APPLY)}</font>';
                }else if (cellvalue == ${MEMBER_CERTIFY_STATUS_PARTY_VERIFY}) {
                    return '<font color="green">${MEMBER_CERTIFY_STATUS_MAP.get(MEMBER_CERTIFY_STATUS_PARTY_VERIFY)}</font>';
                }else if (cellvalue == ${MEMBER_CERTIFY_STATUS_OW_VERIFY}) {
                    return '<font color="green">${MEMBER_CERTIFY_STATUS_MAP.get(MEMBER_CERTIFY_STATUS_OW_VERIFY)}</font>';
                }
            }},
        </c:if>
        {
            label: '所在党组织', name: 'party', width: 500, formatter: function (cellvalue, options, rowObject) {
                return $.party(rowObject.partyId, rowObject.branchId);
            }, sortable: true, align: 'left'
        },
        { label: '政治面貌',name: 'politicalStatus', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue==null || cellvalue=='') return '--';
                if (cellvalue==${MEMBER_POLITICAL_STATUS_POSITIVE}) {
                    return '${MEMBER_POLITICAL_STATUS_MAP.get(MEMBER_POLITICAL_STATUS_POSITIVE)}';
                }else {
                    return '${MEMBER_POLITICAL_STATUS_MAP.get(MEMBER_POLITICAL_STATUS_GROW)}';
                }
            }},
        { label: '原单位',name: 'fromUnit', width:200, align:'left'},
        { label: '介绍信抬头',name: 'toTitle', width:200, align:'left'},
        { label: '拟去往的工作学习单位',name: 'toUnit', width:200, align:'left'},
        <c:if test="${cls==2||cls==5}">
        { label: '返回修改原因',name: 'reason', width:200, align: 'left'},
        </c:if>
        { label: '介绍信日期',name: 'certifyDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
        { label: '申请时间',name: 'applyTime', width: 150}
    ];
</script>