<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script>
  var colModel = [
      {label: '发文号', name: 'dispatchCode', align:'left', width: 190, frozen: true},
      {label: '标题', name: 'title', width: 350, align:'left', frozen: true},
      {
          label: '党委常委会', name: 'scCommittees', align:'left', width:210, formatter: function (cellvalue, options, rowObject) {

          if($.isBlank(cellvalue) || cellvalue.length==0) return '--'

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
                  .format(rowObject.id, str);
      }
      },
      {label: '党委常委会日期', name: 'meetingTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
      {label: '起草日期', name: 'pubTime', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
      {label: '任命人数', name: 'appointCount', width: 80},
      {label: '免职人数', name: 'dismissCount', width: 80},
          <c:if test="${param.type=='admin'}">
      {
          label: '文件签发稿', width: 180, align:'left', formatter: function (cellvalue, options, rowObject) {

          var ret = "";
          var pdfFilePath = rowObject.filePath;
          var fileName = rowObject.dispatchCode +"-文件签发稿";
          if ($.trim(pdfFilePath) != '') {
              //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
              ret = '<button href="javascript:void(0)" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                              .format(encodeURI(pdfFilePath), encodeURI(fileName))
                      + '&nbsp;<button data-url="${ctx}/sc/scDispatch_download?id={0}&fileType=1" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                              .format(rowObject.id/*encodeURI(pdfFilePath), encodeURI(fileName)*/);
          }
          var wordFilePath = rowObject.wordFilePath;
          if ($.trim(wordFilePath) != '') {
              ret += '&nbsp;<button data-url="${ctx}/sc/scDispatch_download?id={0}&fileType=2"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                      .format(rowObject.id/*encodeURI(wordFilePath), encodeURI(fileName)*/);
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
              ret += '&nbsp;<button href="javascript:void(0)" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                      .format(encodeURI(signFilePath), encodeURI(rowObject.dispatchCode +"-签批文件"))
          }
          return ret;
      }},
      {
          label: '红头文件', name: 'dispatchCode', width: 190, formatter: function (cellvalue, options, rowObject) {

              if(rowObject.dispatchId==undefined){

                  return ('<button class="openView btn btn-xs btn-primary" data-url="${ctx}/dispatch_au_page?scDispatchId={0}">'
                      +'<i class="fa fa-edit"></i> 编辑</button>').format(rowObject.id)
              }else if($.trim(rowObject.dispatchFile)==''){

                  return ('<button class="openView btn btn-xs btn-success" data-url="${ctx}/dispatch_au_page?id={0}">'
                      +'<i class="fa fa-edit"></i> 编辑</button>').format(rowObject.dispatchId)
              }

              return $.pdfPreview(rowObject.dispatchFile, rowObject.dispatchFileName, cellvalue, cellvalue);
          }, frozen: true
      },
      {label: '纪实人员', name: 'recordUser.realname'},
      </c:if>
      {label: '备注', name: 'remark', width: 380}
  ]
</script>
