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
       <shiro:hasPermission name="unitPost:changeOrder">
    <c:if test="${param.list==1 && !_query}">
    { label:'排序', width: 85, formatter: $.jgrid.formatter.sortOrder,
      formatoptions:{grid:'#jqGrid2',url:'${ctx}/unitPost_changeOrder'},frozen:true },
    </c:if>
      </shiro:hasPermission>
    <c:if test="${param.list==0}">
    { label: '单位编号', name: 'unitCode', width: 80},
    {label: '单位名称', name: 'unitId', width: 200, align:'left', formatter: $.jgrid.formatter.unit},
    { label: '单位类型', name: 'unitTypeId', width: 120,frozen:true, formatter: $.jgrid.formatter.MetaType },
    </c:if>
    { label: '分管工作', align:'left', name: 'job', width: 200 },
    { label: '是否<br/>正职',name: 'isPrincipalPost', width: 50, formatter: $.jgrid.formatter.TRUEFALSE},
    { label: '岗位级别',name: 'adminLevel', width: 85, formatter:$.jgrid.formatter.MetaType},
    { label: '职务属性',name: 'postType', width: 120, formatter:$.jgrid.formatter.MetaType},
    { label: '职务<br/>类别',name: 'postClass', width: 50, formatter:$.jgrid.formatter.MetaType},
    { label: '是否占<br/>干部职数',name: 'isCpc', width: 70, formatter: $.jgrid.formatter.TRUEFALSE},
      {label: '现任职干部', name: '_cadre', width: 120, formatter: function (cellvalue, options, rowObject) {
              if(rowObject.cadre==undefined) return '--'
              return $.cadre(rowObject.cadre.id, rowObject.cadre.realname, '${param.list==1?'_blank':''}');
          }},
      {label: '干部级别', name: 'cpAdminLevel' , formatter:$.jgrid.formatter.MetaType, cellattr: function (rowId, val, rowObject, cm, rdata) {
              if(rowObject.cpAdminLevel!=undefined && rowObject.adminLevel!=null
                  && rowObject.cpAdminLevel!=  rowObject.adminLevel)
                  return "class='danger'";
          }},
      { label: '任职<br/>类型',name: 'cadrePost.isMainPost', width: 50, formatter: function (cellvalue, options, rowObject) {
              if(cellvalue==undefined) return '--'
              return cellvalue?"主职":"兼职";
       }},
      {
          label: '任职日期',
          name: 'cadrePost.dispatchCadreRelateBean.last.workTime',
          formatter: 'date',
          formatoptions: {newformat: 'Y-m-d'}
      },
      {
          label: '现任职务<br/>年限',
          width: 80,
          name: 'cadrePost.dispatchCadreRelateBean.last.workTime',
          formatter: function (cellvalue, options, rowObject) {
              if (cellvalue == undefined) return '';
              var year = $.yearOffNow(cellvalue);
              return year == 0 ? "未满一年" : year;
          }
      },
      {
          label: '现职务<br/>任职文件',
          width: 150,
          name: 'cadrePost.dispatchCadreRelateBean.last',
          formatter: function (cellvalue, options, rowObject) {
              if (!cellvalue || cellvalue.id == undefined) return '';
              var dispatchCode = cellvalue.dispatchCode;

              return $.swfPreview(cellvalue.file, cellvalue.fileName, dispatchCode, dispatchCode);
          }
      },
      {
          label: '现任职务<br/>始任日期',
          name: 'cadrePost.dispatchCadreRelateBean.first.workTime',
          formatter: 'date',
          formatoptions: {newformat: 'Y-m-d'}
      },
      {
          label: '现任职务<br/>始任年限',
          width: 80,
          name: 'cadrePost.dispatchCadreRelateBean.first.workTime',
          formatter: function (cellvalue, options, rowObject) {
              if (cellvalue == undefined) return '';
              var year = $.yearOffNow(cellvalue);
              return year == 0 ? "未满一年" : year;
          }
      },
      {
          label: '现任职务<br/>始任文件',
          width: 150,
          name: 'cadrePost.dispatchCadreRelateBean.first',
          formatter: function (cellvalue, options, rowObject) {
              if (!cellvalue || cellvalue.id == undefined) return '';
              var dispatchCode = cellvalue.dispatchCode;

              return $.swfPreview(cellvalue.file, cellvalue.fileName, dispatchCode, dispatchCode);
          }
      },
      {
          label: '干部任免文件',
          name: 'cadrePost.dispatchCadreRelateBean.all',
          formatter: function (cellvalue, options, rowObject) {
              if (cellvalue == undefined) return '';
              var count = cellvalue.length;
              <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
              if(count==0) return ''
              </shiro:lacksPermission>
              return _.template($("#dispatch_select_tpl").html().NoMultiSpace())
              ({id: rowObject.cadrePost.id, cadreId: rowObject.cadrePost.cadreId, count: count});
          },
          width: 120
      },
    { label: '历史<br/>任职干部',name: '_history', width: 85, formatter: function (cellvalue, options, rowObject) {

        return ('<button class="popupBtn btn btn-xs btn-warning" data-width="950"' +
            ' data-url="${ctx}/unitPost_cadres?unitPostId={0}">' +
            '<i class="fa fa-search"></i> 查看</button>').format(rowObject.id)
    }},
    { label: '备注',name: 'remark', align:'left', width: 250}
  ]
</script>