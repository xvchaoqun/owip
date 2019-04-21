<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
      { label: '年度',name: 'year', width: 80},
      { label: '填报类型',name: 'type', width: 130, formatter: function (cellvalue, options, rowObject) {
          return (rowObject.type)?'个别填报':'年度集中填报';
      }},
      { label: '工作证号',name: 'code', width: 120,frozen: true},
      { label: '姓名',name: 'realname', frozen: true},
      {label: '所在单位及职务', name: 'title', align: 'left', width: 250},
      { label: '领表时间',name: 'drawTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'} },
      { label: '应交回时间',name: 'handTime',cellattr:function(rowId, val, rowObject, cm, rdata) {
          var _date = rowObject.handTime;
          if ($.trim(rowObject.realHandTime)=='' && _date <= new Date().format('yyyy-MM-dd hh:mm'))
              return "class='danger'";
      }, width: 150, formatter: $.jgrid.formatter.date, formatoptions: {srcformat:'Y-m-d H:i',newformat:'Y-m-d H:i'}},
      {label: '实交回日期', name: 'realHandTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
      {label: '封面填表日期', name: 'fillTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
      { label: '备注',name: 'remark', width: 230}
  ]
</script>
