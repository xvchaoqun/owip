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
      <label class="col-xs-3 control-label" style="text-align: right; line-height: 34px"><span class="star">*</span>选择干部</label>
      <div class="col-xs-6">
        <select required data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects" data-width="350"
                name="cadreId" data-placeholder="请输入账号或姓名或学工号">
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
        姓名：<span >{{=ret.realname}}</span>
      </small>
      <small>
        所在干部库：<span >{{=ret.msg}}
        {{if(ret.url){}}
      &nbsp;&nbsp;<a class="btn btn-success btn-xs" href="{{=ret.url}}"><i class="fa fa-search"></i> 前往查看</a>
         {{}}}
      </span>
      </small>
    </blockquote>
  </div>
</script>
<script>
  $.register.user_select($('#modal select[name=cadreId]'));

  $("#modal #search").click(function(){
    var cadreId = $("#modal select[name=cadreId]").val();
    if(cadreId=='') return;
    $.post("${ctx}/cadre_search",{cadreId:cadreId},function(ret){
        if(ret.success){

          var status = $.trim(ret.status);
          if(status!=''){
            ret.msg = _cMap.CADRE_STATUS_MAP[ret.status]
            if(status=='${CADRE_STATUS_CJ}' || status=='${CADRE_STATUS_CJ_LEAVE}'
            ||status=='${CADRE_STATUS_KJ}' || status=='${CADRE_STATUS_KJ_LEAVE}')
              ret.url='#${ctx}/cadre?status='+status;
            if(status=='${CADRE_STATUS_LEADER}' || status=='${CADRE_STATUS_LEADER_LEAVE}')
              ret.url='#${ctx}/leaderInfo?status='+status;

            ret.url+='&cadreId='+ ret.cadreId;
          }

          $("#result").html(_.template($("#result_tpl").html())({ret: ret}));
        }
    });
  })
</script>