<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>候选人学工号查询</h3>
</div>
<div class="modal-body">
  <form>
  <div class="form-group">
      <label class="col-xs-3 control-label" style="text-align: right; line-height: 34px">候选人姓名</label>
      <div class="col-xs-6">
        <input class="form-control search-query" name="reportName" type="text"
               placeholder="请输入投票名称">
      </div>
    <script> $("input[name=reportName]").val(${param.reportName})</script>
  </div>
  <input type="button" id="search" class="btn btn-primary" value="查询"/>
  </form>
  <br/>  <br/>
  <div class="row" id="result">

  </div>

</div>
<div class="modal-footer">
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
        <th>学工号</th>
        <th>姓名</th>
        <th>所在单位</th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      {{_.each(records, function(record, idx){ }}
      <tr>
        <td>{{=record.code}}</td>
        <td nowrap>{{=record.realname}}</td>
        <td>{{=record.unit}}</td>
        <td>
          <input type="button" class="btn btn-primary btn-xs" id="copy" data-clipboard-action="copy" name="{{=record.code}}" value="复制学工号"/>
        </td>
      </tr>
      {{});}}
      </tbody>
    </table>
  </div>
</script>
<script src="${ctx}/extend/js/clipboard.min.js"></script>
<script>



  $("#modal ").on('click','#copy', function(){
    var content = $(this).attr("name");
    var clipboard = new ClipboardJS('#copy', {
      text: function() {
        return content;
      }
    });
  })

  $("#modal #search").click(function(){
    var reportName = $("#modal input[name=reportName]").val();
    if($.trim(reportName)=='') return;
    $.post("${ctx}/pcs/user_search",{reportName:reportName},function(ret){
        if(ret.success){
          console.log(ret.count);
          $("#result").html(_.template($("#result_tpl").html())({records: ret.records}));
        }
    });
  })
</script>