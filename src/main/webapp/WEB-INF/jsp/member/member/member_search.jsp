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
      <label class="col-xs-3 control-label" style="text-align: right; line-height: 34px">选择账号</label>
      <div class="col-xs-6">
        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects" data-width="350"
                name="userId" data-placeholder="请输入账号或姓名或学工号">
          <option></option>
        </select>
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
        <small id="unit" style="display: none">
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
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
  <input type="button" id="search" class="btn btn-primary" value="查询"/>
</div>
<style>
  small{
    padding-top: 10px;
  }
</style>
<script>
  register_user_select($('#modal select[name=userId]'));
  $("#modal #search").click(function(){
    var userId = $("#modal select[name=userId]").val();
    if(userId=='') return;
    $.post("${ctx}/member/search",{userId:userId},function(ret){
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
          if($.trim(ret.unit)!='') {
            $("#modal #unit").show().find("span").html(ret.unit);
          }
          if($.trim(ret.status)!='') {
            $("#modal #status").show().find("span").html(ret.status);
          }
        }
    });
  })
</script>