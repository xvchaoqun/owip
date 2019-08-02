<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script>
        var colModel= [
            { label: '年份', name: 'dispatch.year', width: 75,frozen:true },
            { label:'发文号',  name: 'dispatch.dispatchCode', width: 190,formatter:function(cellvalue, options, rowObject){

                return $.pdfPreview(rowObject.dispatch.file, rowObject.dispatch.fileName,
                    cellvalue, cellvalue, '${param.type eq 'all'?'modal':'url'}');
            },frozen:true },
            { label: '任免日期',  name: 'dispatch.workTime',frozen:true , formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'} },
            { label:'类别', name: 'type', width: 50, formatter:function(cellvalue, options, rowObject){
                return _cMap.DISPATCH_CADRE_TYPE_MAP[cellvalue];
            },frozen:true },
            { label:'任免方式', name: 'wayId', width: 80, formatter: $.jgrid.formatter.MetaType},
            { label:'任免程序', name: 'procedureId', width: 80, formatter: $.jgrid.formatter.MetaType},
            { label:'干部类型', name: 'cadreTypeId', width: 80, formatter: $.jgrid.formatter.MetaType},
            { label:'工作证号', name: 'user.code'},
            { label:'姓名', name: 'user.realname', width: 90, formatter: function (cellvalue, options, rowObject) {
                if(rowObject.cadre==undefined) return '--'
                return $.cadre(rowObject.cadre.id, cellvalue, '${param.type eq 'all'?'':'_blank'}');
            }},
            { label:'任免职务', name: 'post', width: 150, cellattr: function (rowId, val, rowObject, cm, rdata) {
                    if(rowObject.unitPostId==undefined)
                        return "class='warning'";
                }, align:'left'},
            { label:'职务属性', name: 'postType', width: 120, align:'left', formatter: $.jgrid.formatter.MetaType},
            { label:'行政级别', name: 'adminLevel', formatter: $.jgrid.formatter.MetaType},
            { label:'所属单位', name: 'unit.name', width: 150, align:'left' },
            { label:'单位类型', name: 'unit.typeId', width: 120, formatter: $.jgrid.formatter.MetaType},
            { label:'发文类型', name: 'dispatch.dispatchType.name', width: 80},
            <c:if test="${param.type eq 'all' || param.type1 eq 'team'}">
            {
                label: '党委常委会', name: 'dispatch.scDispatch.scCommittees', width:210, formatter: function (cellvalue, options, rowObject) {

                if(cellvalue==undefined || cellvalue.length==0) return '--'

                var scCommittee = cellvalue[0];
                var str = scCommittee.code
                if(cellvalue.length>1){
                    str += "，..."
                }else{

                    return ('<a href="javascript:;" class="linkBtn"'
                    +'data-url="${ctx}#/sc/scCommittee?year={0}&holdDate={1}"'
                    +'data-target="_blank">{2}</a>')
                            .format(scCommittee.year, $.date(scCommittee.holdDate,'yyyyMMdd'),str)
                }

                return ('<a href="javascript:;" class="popupBtn" ' +
                'data-url="${ctx}/sc/scDispatchCommittee?dispatchId={0}">{1}</a>')
                        .format(rowObject.dispatch.scDispatch.id, str);
            }
            },
            { label:'党委常委会日期', name: 'dispatch.meetingTime', width: 130, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            { label:'发文日期', name: 'dispatch.pubTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
           /* { label:'任免文件', name: 'fileName', formatter:function(cellvalue, options, rowObject){

                return $.pdfPreview(rowObject.dispatch.file, rowObject.dispatch.fileName, '查看');
            }},
            { label:'上会ppt', name: 'pptName', formatter:function(cellvalue, options, rowObject){

                return $.pdfPreview(rowObject.dispatch.ppt, rowObject.dispatch.pptName, '查看');
            }},*/
           <c:if test="${param.type eq 'all'}">
            { label: '是否复核', name: 'hasChecked', width: 80, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '--';
                return cellvalue?"已复核":"否";
            }},{hidden:true, name:'_hasChecked', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.hasChecked==undefined) return 0;
                return rowObject.hasChecked?1:0;
            }},
            { label:'备注', width: 250, name: 'remark'}
            </c:if>
            <c:if test="${param.type1 eq 'team'}">
            { label: '所属班子', name: 'postTeam', width: 80, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '--';
                return cellvalue=='dw'?"党委班子":"行政班子";
            }}
            </c:if>
            </c:if>
        ]
</script>