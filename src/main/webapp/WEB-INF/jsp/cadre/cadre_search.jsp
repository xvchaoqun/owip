<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>查询账号所属的干部库</h3>
</div>
<div class="modal-body">
  <form>
  <div class="form-group">
      <label class="col-xs-4 control-label" style="text-align: right; line-height: 34px">工号</label>
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
    $.post("${ctx}/cadre/search",{code:code},function(ret){
        if(ret.success){
          $("#modal #result").show();
          //$("#modal #msg").hide();
          //$("#modal #status").hide();

          var msg = ret.msg;
          if($.trim(ret.status)!=''){
            msg = _cMap.CADRE_STATUS_MAP[ret.status]
                    + '&nbsp;&nbsp;<a class="btn btn-success btn-xs" href="${ctx}/cadre?status='+ret.status+'&cadreId='+ ret.cadreId +'"><i class="fa fa-search"></i> 前往查看</a>';
          }
          $("#modal #msg").show().find("span").html(msg);

          if($.trim(ret.realname)!='') {
            $("#modal #realname").show().find("span").html(ret.realname);
          }
        }
    });
  })
</script>