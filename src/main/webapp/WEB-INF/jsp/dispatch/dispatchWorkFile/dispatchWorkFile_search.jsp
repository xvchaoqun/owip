<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>查询文件</h3>
</div>
<div class="modal-body">
  <form>
  <div class="form-group">
      <label class="col-xs-3 control-label" style="text-align: right; line-height: 34px">文件名</label>
      <div class="col-xs-6">
        <input class="form-control" type="text" name="fileName">
      </div>
    </div>
  </form>
  <br/>  <br/>
  <div class="row" id="result">

  </div>

</div>
<div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
  <input type="button" id="search" class="btn btn-primary" value="查询"/>
</div>
<style>
  small{
    padding-top: 10px;
  }
</style>
<script type="text/template" id="result_tpl">
  <div class="col-xs-12">
    <table class="table table-bordered table-condensed table-unhover2 table-center">
      <thead>
      <tr>
        <th>文件名</th>
        <th>类型</th>
        <th style="width: 60px;">状态</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      {{_.each(files, function(f, idx){ }}
      <tr>
        <td style="text-align: left">
          {{if(f.pdfFilePath.length>0){}}
          <a href="javascript:void(0)" data-url="${ctx}/swf/preview?type=url&path={{=f.pdfFilePath}}&filename={{=f.fileName}}"
                  title="PDF文件预览" class="openUrl">{{=f.fileName}}</a>
          {{}else{}}
          {{=f.fileName}}
          {{}}}
        </td>
        <td nowrap>{{=_cMap.DISPATCH_WORK_FILE_TYPE_MAP[f.type]}}</td>
        <td>{{=f.status?'有效':'失效'}}</td>
        <td><a href="javascript:;" class="btn btn-success btn-xs hashchange"
               data-url="${ctx}/dispatchWorkFile?type={{=f.type}}&status={{=f.status}}&fileName={{=f.fileName}}">
          <i class="fa fa-search"></i> 前往查看</a></td>
      </tr>
      {{});}}
      </tbody>
    </table>
  </div>
</script>
<script>

  $("#modal #search").click(function(){
    var fileName = $("#modal input[name=fileName]").val();
    if($.trim(fileName)=='') return;
    $.post("${ctx}/dispatchWorkFile_search",{fileName:fileName},function(ret){
        if(ret.success){

          $("#result").html(_.template($("#result_tpl").html())({files: ret.dispatchWorkFiles}));
        }
    });
  })
</script>