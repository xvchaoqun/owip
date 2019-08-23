<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>成员党派关系查询</h3>
</div>
<div class="modal-body">
  <form>
  <div class="form-group">
      <label class="col-xs-3 control-label" style="text-align: right; line-height: 34px">选择账号</label>
      <div class="col-xs-6">
        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects" data-width="250"
                name="userId" data-placeholder="请输入账号或姓名或学工号">
          <option></option>
        </select>
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
    <blockquote>
      <small>
        {{=(ret.userType==${USER_TYPE_JZG})?"教工号":"学号"}}：<span>{{=ret.code}}</span>
      </small>
      <small>
        姓名：<span>{{=ret.realname}}</span>
      </small>
      <small>
        所在单位：<span >{{=ret.unit}}</span>
      </small>
      <small>
        查询结果：<span>{{=ret.msg}}</span>
      </small>
      {{if(ret.status){}}
      <small>
        状态：<span>{{=ret.status}}</span>
      </small>
      {{}}}
    </blockquote>
  </div>
</script>
<script>
  $.register.user_select($('#modal select[name=userId]'));
  $("#modal #search").click(function(){
    var userId = $("#modal select[name=userId]").val();
    if(userId=='') return;
    $.post("${ctx}/dp/dpMember/search",{userId:userId},function(ret){
        if(ret.success){
          $("#result").html(_.template($("#result_tpl").html())({ret: ret}));
        }
    });
  })
</script>