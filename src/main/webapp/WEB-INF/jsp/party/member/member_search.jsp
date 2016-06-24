<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>全校组织关系查询</h3>
</div>
<div class="modal-body">
  <form>
  <div class="form-group">
      <label class="col-xs-4 control-label" style="text-align: right; line-height: 34px">学工号</label>
      <div class="col-xs-6">
        <input type="text" name="code">
      </div>
    </div>
  </form>
  <br/>  <br/>
  <div class="row" id="result" style="display: none">
    <div class="col-xs-12">
      <blockquote>
        <small id="realname" style="display: none">
          <span ></span>
        </small>
        <small id="msg" style="display: none">
          <span ></span>
        </small>
        <small id="status" style="display: none">
          <span ></span>
        </small>
      </blockquote>
    </div>
  </div>

</div>
<div class="modal-footer">
  <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
  <input type="button" id="search" class="btn btn-primary" value="查询"/>
</div>
<style>
  small{
    padding-top: 10px;
  }
</style>
<script>
  $("#modal #search").click(function(){
    var code = $("#modal input[name=code]").val();
    $.post("${ctx}/member/search",{code:code},function(ret){
        if(ret.success){
          $("#modal #result").show();
          $("#modal #msg").hide();
          $("#modal #status").hide();
          if($.trim(ret.msg)!='') {
            $("#modal #msg").show().find("span").html(ret.msg);
          }
          if($.trim(ret.realname)!='') {
            $("#modal #realname").show().find("span").html(ret.realname);
          }
          if($.trim(ret.status)!='') {
            $("#modal #status").show().find("span").html(ret.status);
          }
        }
    });
  })
</script>