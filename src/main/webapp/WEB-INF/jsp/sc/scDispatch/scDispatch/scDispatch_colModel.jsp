<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
      /*{label: '年度', name: 'year'},*/
      /*{
          label: '发文类型', name: 'dispatchType', formatter: function (cellvalue, options, rowObject) {
          if(cellvalue==undefined) return '-'
          return cellvalue.name;
      }, frozen: true
      },*/
      {label: '发文号', name: 'dispatchCode', width: 150},
      {label: '标题', name: 'title', width: 350, align:'left'},
      {label: '党委常委会日期', name: 'meetingTime', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
      {label: '起草日期', name: 'pubTime', width: 120, formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
      {label: '任命人数', name: 'appointCount', width: 80},
      {label: '免职人数', name: 'dismissCount', width: 80},
      {
          label: '文件签发稿', width: 180, align:'left', formatter: function (cellvalue, options, rowObject) {

          var ret = "";
          var pdfFilePath = rowObject.filePath;
          var fileName = rowObject.dispatchCode +"-文件签发稿";
          if ($.trim(pdfFilePath) != '') {
              //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
              ret = '<button href="javascript:void(0)" data-url="${ctx}/swf/preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                              .format(encodeURI(pdfFilePath), encodeURI(fileName))
                      + '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}" title="下载PDF文件" class="linkBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                              .format(encodeURI(pdfFilePath), encodeURI(fileName));
          }
          var wordFilePath = rowObject.wordFilePath;
          if ($.trim(wordFilePath) != '') {
              ret += '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}"  title="下载WORD文件" class="linkBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                      .format(encodeURI(wordFilePath), encodeURI(fileName));
          }
          return ret;
      }
      },
      {label: '签发单', name: '_sign', width: 200, align:'left', formatter: function (cellvalue, options, rowObject) {
          var ret = '<button href="javascript:void(0)" data-url="${ctx}/sc/scDispatch_exportSign?dispatchId={0}" title="系统自动生成签发单" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-gears"></i> 生成</button>'
                  .format(rowObject.id)
          + '&nbsp;<button data-url="${ctx}/sc/scDispatch_uploadSign?id={0}" title="签批文件上传" class="popupBtn btn btn-xs btn-warning"><i class="fa fa-upload"></i> 正式签发</button>'
                  .format(rowObject.id);

          var signFilePath = rowObject.signFilePath;
          if ($.trim(signFilePath) != '') {
              ret += '&nbsp;<button href="javascript:void(0)" data-url="${ctx}/swf/preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                      .format(encodeURI(signFilePath), encodeURI(rowObject.dispatchCode +"-签批文件"))
          }
          return ret;
      }},
      /*{label: '签发单', name: 'signFilePath', formatter: function (cellvalue, options, rowObject) {
          if(rowObject.signFilePath==undefined) return '-'
          return $.swfPreview(rowObject.signFilePath, "签发单", "查看", null, "${param.type=='admin'?'':'url'}");
      }},*/
      {label: '备注', name: 'remark', width: 380}
  ]
</script>
