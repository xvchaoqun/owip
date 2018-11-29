<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
    var colModel =  [
            { label: '年份', name: 'dispatch.year', width: 75,frozen:true },
            { label:'发文号',  name: 'dispatch.dispatchCode', width: 140, align:'left',formatter:function(cellvalue, options, rowObject){

                return $.swfPreview(rowObject.dispatch.file, rowObject.dispatch.fileName,
                    cellvalue, cellvalue, '${param.type eq 'all'?'modal':'url'}');
            },frozen:true },
            { label: '发文日期',  name: 'dispatch.pubTime',frozen:true , formatter: 'date', formatoptions: {newformat: 'Y-m-d'} },

            { label:'机构类型', name: 'type', width: 90, formatter:function(cellvalue, options, rowObject){

                //console.log((','+ rowObject.dispatch.category +','))
                return (','+ rowObject.dispatch.category +',')
                    .indexOf(',<%=DispatchConstants.DISPATCH_CATEGORY_UNIT%>,')>=0?'内设机构':'组织机构';
            },frozen:true },
            { label:'调整方式', name: 'type', width: 80, formatter: $.jgrid.formatter.MetaType},
            { label:'新成立机构名称', name: 'unitId', width: 280, align:'left' ,formatter:function(cellvalue, options, rowObject){

                 if(cellvalue==undefined) return '--'
                //console.log((','+ rowObject.dispatch.category +',') .indexOf(',<%=DispatchConstants.DISPATCH_CATEGORY_UNIT%>,'))
                return (','+ rowObject.dispatch.category +',')
                    .indexOf(',<%=DispatchConstants.DISPATCH_CATEGORY_UNIT%>,')>=0?$.jgrid.formatter.unit(cellvalue)
                    :_cMap.partyMap[cellvalue].name;
            }},
            { label:'撤销机构名称', name: 'oldUnitId', width: 250, align:'left' ,formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '--'
                return (','+ rowObject.dispatch.category +',')
                    .indexOf(',<%=DispatchConstants.DISPATCH_CATEGORY_UNIT%>,')>=0?$.jgrid.formatter.unit(cellvalue)
                    :_cMap.partyMap[cellvalue].name;
            }},
            <c:if test="${param.type eq 'all'}">
            { label:'备注', name: 'remark', width: 250}
            </c:if>
        ];
</script>
