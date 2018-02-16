<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
      {label: '年度', name: 'year'},
      {
          label: '发文类型', name: 'dispatchType', formatter: function (cellvalue, options, rowObject) {
          if(cellvalue==undefined) return '-'
          return cellvalue.name;
      }, frozen: true
      },
      {label: '发文号', name: 'code'},
      {label: '党委常委会日期', name: 'meetingTime', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
      {label: '起草日期', name: 'pubTime', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
      {label: '任命人数', name: 'appointCount', width: 80},
      {label: '免职人数', name: 'dismissCount', width: 80},
      {label: '文件签发稿', name: 'filePath', formatter: function (cellvalue, options, rowObject) {
          if(rowObject.filePath==undefined) return '-'
          return '<button data-url="${ctx}/attach/download?path={0}&filename={1}" class="linkBtn btn btn-xs btn-warning"><i class="fa fa-file-word-o"></i> 下载</button>'
                  .format(encodeURI(rowObject.filePath), "文件签发稿");
      }
      },
      {label: '签发单', name: 'signFilePath', formatter: function (cellvalue, options, rowObject) {
          if(rowObject.signFilePath==undefined) return '-'
          return $.swfPreview(rowObject.signFilePath, "签发单", "查看", null, "${param.type=='admin'?'':'url'}");
      }},
      {label: '备注', name: 'remark', width: 380}
  ]
</script>
