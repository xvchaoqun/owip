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
      <label class="col-xs-3 control-label" style="text-align: right; line-height: 34px">选择干部</label>
      <div class="col-xs-6">
        <select data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects" data-width="350"
                name="cadreId" data-placeholder="请输入账号或姓名或学工号">
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
        <small id="msg" style="display: none">
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
  register_user_select($('#modal select[name=cadreId]'));

  $("#modal #search").click(function(){
    var cadreId = $("#modal select[name=cadreId]").val();
    if(cadreId=='') return;
    $.post("${ctx}/cadre/search",{cadreId:cadreId},function(ret){
        if(ret.success){
          $("#modal #result").show();
          //$("#modal #msg").hide();
          //$("#modal #status").hide();

          var msg = ret.msg;
          var status = $.trim(ret.status);
          var url = ''
          if(status!=''){

            if(status=='${CADRE_STATUS_MIDDLE}' || status=='${CADRE_STATUS_MIDDLE_LEAVE}')
              url='${ctx}/cadre?status='+status;
            if(status=='${CADRE_STATUS_LEADER}' || status=='${CADRE_STATUS_LEADER_LEAVE}')
              url='${ctx}/cadreLeaderInfo?status='+status;

            msg = _cMap.CADRE_STATUS_MAP[ret.status];
            if(url!='') msg += '&nbsp;&nbsp;<a class="renderBtn btn btn-success btn-xs" href="javascript:;" data-url="'+url+'&cadreId='+ ret.cadreId +'"><i class="fa fa-search"></i> 前往查看</a>';
          }
          $("#modal #msg").show().find("span").html(msg);

          if($.trim(ret.realname)!='') {
            $("#modal #realname").show().find("span").html(ret.realname);
          }
        }
    });
  })
</script>