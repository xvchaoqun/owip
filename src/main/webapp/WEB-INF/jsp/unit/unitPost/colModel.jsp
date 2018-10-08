<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
    { label: '岗位编号',name: 'code', frozen:true},
    { label: '岗位名称',name: 'name', align:'left', width: 300, frozen:true},
    <c:if test="${cls==2}">
    {label: '撤销日期', name: 'abolishDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}, frozen:true},
    </c:if>
    <c:if test="${param.list==1 && !_query}">
    { label:'排序',align:'center', formatter: $.jgrid.formatter.sortOrder,
      formatoptions:{grid:'#jqGrid2',url:'${ctx}/unitPost_changeOrder'},frozen:true },
    </c:if>
    <c:if test="${param.list==0}">
    { label: '单位编号', name: 'unitCode', width: 80},
    /*{ label: '单位名称', name: 'unitName', width: 250, align:'left', formatter:function(cellvalue, options, rowObject){
      return '<a href="javascript:;" class="openView" data-url="${ctx}/unit_view?id={0}">{1}</a>'
              .format(rowObject.unitId, cellvalue);
    }},*/
    {label: '单位名称', name: 'unitId', width: 250, align:'left', formatter: $.jgrid.formatter.unit},
    { label: '单位类型', name: 'unitTypeId', width: 180,frozen:true, formatter: $.jgrid.formatter.MetaType },
    </c:if>
    { label: '分管工作', align:'left', name: 'job', width: 450 },
    { label: '是否正职',name: 'isPrincipalPost', width: 80, formatter: $.jgrid.formatter.TRUEFALSE},
    { label: '行政级别',name: 'adminLevel', formatter:$.jgrid.formatter.MetaType},
    { label: '职务属性',name: 'postType', width: 150, formatter:$.jgrid.formatter.MetaType},
    { label: '职务类别',name: 'postClass', formatter:$.jgrid.formatter.MetaType},
    { label: '是否占干部职数',name: 'isCpc', width: 120, formatter: $.jgrid.formatter.TRUEFALSE},
    { label: '备注',name: 'remark', align:'left', width: 250}
  ]
</script>