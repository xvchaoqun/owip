<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
  <div class="col-xs-12">
    <jsp:include page="menu.jsp"/>
    <div class="space-4"></div>
    <button data-url="${ctx}/pcs/pcsVoteStat_clear"
            data-title="清空数据"
            data-msg="确定清空两委选举相关的所有数据？<div class='text-danger'>（不可恢复，请谨慎操作！）</div>"
            data-callback="_reload"
            class="confirm btn btn-danger btn-sm">
      <i class="fa fa-trash"></i> 清空数据
    </button>
    <div class="space-4"></div>
    <div class="widget-box" style="width: 500px">
      <div class="widget-header">
        <h4 class="widget-title">
          基本设置
        </h4>
      </div>
      <div class="widget-body">
        <div class="widget-main">
          <form class="form-horizontal" action="${ctx}/pcs/pcsVoteStat_au" id="statForm"
                method="post">

            <div class="form-group">
              <label class="col-xs-5 control-label">两委选举实到会的代表人数</label>
              <div class="col-xs-6">
                <input class="form-control num" type="text" maxlength="4"
                       name="committeeJoinCount" value="${pcsConfig.committeeJoinCount}">
              </div>
            </div>
            <div class="form-group">
              <label class="col-xs-5 control-label">发出党委委员选票数</label>
              <div class="col-xs-6">
                <input class="form-control num" type="text" maxlength="4"
                       name="dwSendVote" value="${pcsConfig.dwSendVote}">
              </div>
            </div>
            <div class="form-group">
              <label class="col-xs-5 control-label">发出纪委委员选票数</label>
              <div class="col-xs-6">
                <input class="form-control num" type="text" maxlength="4"
                       name="jwSendVote" value="${pcsConfig.jwSendVote}">
              </div>
            </div>
            <div class="form-group">
              <label class="col-xs-5 control-label">收回党委委员选票数</label>
              <div class="col-xs-6">
                <input class="form-control num" type="text" maxlength="4"
                       name="dwBackVote" value="${pcsConfig.dwBackVote}">
              </div>
            </div>
            <div class="form-group">
              <label class="col-xs-5 control-label">各小组领回选票总数</label>
              <div class="col-xs-6 label-text">
                ${dwPcsVoteGroup.vote}
              </div>
            </div>
            <div class="form-group">
              <label class="col-xs-5 control-label">收回纪委委员选票数</label>
              <div class="col-xs-6">
                <input class="form-control num" type="text" maxlength="4"
                       name="jwBackVote" value="${pcsConfig.jwBackVote}">
              </div>
            </div>
            <div class="form-group">
              <label class="col-xs-5 control-label">各小组领回选票总数</label>
              <div class="col-xs-6 label-text">
                ${jwPcsVoteGroup.vote}
              </div>
            </div>
            <div class="form-group">
              <label class="col-xs-5 control-label">计票时是否可以选择预备人选</label>
              <div class="col-xs-6 label-text">
                <input type="checkbox" class="big" name="committeeCanSelect" ${pcsConfig.committeeCanSelect?"checked":""}/>
              </div>
            </div>
            <div class="modal-footer center">
              <%--<a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>--%>
              <input type="submit" class="btn btn-primary" value="更新"/>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

</div>
<script>
  function _reload(){
    $.hashchange();
  }
  $("#statForm :checkbox").bootstrapSwitch();
  $("#statForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success: function (ret) {
          if (ret.success) {
            SysMsg.info("更新成功。")
          }
        }
      });
    }
  });

</script>