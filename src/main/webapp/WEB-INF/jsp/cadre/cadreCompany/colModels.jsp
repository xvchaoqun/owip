<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
    var colModels_cadreCompany = [
        <c:if test="${param.type=='list'}">
        {label: '工作证号', name: 'cadre.code', width: 110, frozen: true},
        {
            label: '姓名', name: 'cadre.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadre.id, cellvalue);
            }, frozen: true
        },
        {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 350},
        <c:if test="${module==1}">
        {label: '是否<br/>双肩挑', width: 60, name: 'cadre.isDouble', formatter: $.jgrid.formatter.TRUEFALSE},
        {label: '干部类型', name: 'cadreStatus', formatter: function (cellvalue, options, rowObject) {
            if(cellvalue=='<%=CadreConstants.CADRE_STATUS_LEADER%>'){
                return '现任校领导'
            }else if(cellvalue=='<%=CadreConstants.CADRE_STATUS_LEADER_LEAVE%>'){
                return '离任校领导'
            }
        }},
        </c:if>
        </c:if>
        {
            label: '兼职类型', name: 'type', width: 180, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '';
                var ret = $.jgrid.formatter.MetaType(cellvalue);
                if (cellvalue == '${cm:getMetaTypeByCode("mt_cadre_company_other").id}') {
                    if (rowObject.typeOther != '') {
                        ret = ret + ":" + rowObject.typeOther;
                    }
                }
                return ret;
            }, frozen: true
        },
        {label: '兼职单位', name: 'unit', width: 250,align:'left', frozen: true},
        {label: '兼任职务', name: 'post', width: 150,align:'left', frozen: true},
        {
            label: '兼职<br/>起始时间',
            name: 'startTime',
            width: 80,
            formatter: 'date',
            formatoptions: {newformat: 'Y.m'}
        },
        <c:if test="${cls==2}">
        {
            label: '兼职<br/>结束时间',
            name: 'finishTime',
            width: 80,
            formatter: 'date',
            formatoptions: {newformat: 'Y.m'}
        },
        </c:if>
        {label: '审批单位', name: 'approvalUnit',align:'left', width: 280},
        {label: '批复日期', name: 'approvalDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen: true},
        {
            label: '批复文件', name: 'approvalFile', width: 80,
            formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined) return '-'
                return $.swfPreview(rowObject.approvalFile, rowObject.approvalFilename, "预览");
            }
        },
        {label: '是否取酬', name: 'hasPay', formatter: $.jgrid.formatter.TRUEFALSE, width: 80},
        {label: '所取酬劳是否<br/>全额上交学校', name: 'hasHand', formatter: function (cellvalue, options, rowObject) {
                if(!rowObject.hasPay) return '-'
                return  $.jgrid.formatter.TRUEFALSE(cellvalue);
            }, width: 120},
        {label: '备注', name: 'remark', width: 350}, {hidden: true, key: true, name: 'id'}
    ];
</script>