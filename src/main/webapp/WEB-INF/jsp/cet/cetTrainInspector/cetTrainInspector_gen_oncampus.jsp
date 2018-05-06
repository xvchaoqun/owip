<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>生成评课账号</h3>
</div>
<div class="modal-body">
  <form class="form-horizontal" action="${ctx}/cet/cetTrainInspector_gen_oncampus" id="modalForm" method="post">
    <input type="hidden" name="trainId" value="${cetTrain.id}">
    <div class="form-group">
      <label class="col-xs-3 control-label">培训班次</label>
      <div class="col-xs-6 label-text">
        ${cetTrain.name}
      </div>
    </div>
    <div class="form-group">
      <label class="col-xs-3 control-label">账号数量</label>
      <div class="col-xs-6 label-text">
        ${cetTrain.traineeCount}
      </div>
    </div>
    <div class="form-group">
      <label class="col-xs-3 control-label">是否匿名评课</label>
      <div class="col-xs-6">
        <c:choose>
        <c:when test="${cetTrain.evaCount>0}">
          <input name="evaAnonymous" type="hidden" value="${cetTrain.evaAnonymous}"/>
          ${cetTrain.evaAnonymous?"是":"否"}
        </c:when>
      <c:otherwise>
        <label>
          <input name="evaAnonymous" type="checkbox" checked/>
          <span class="lbl"></span>
        </label>
  </c:otherwise>
        </c:choose>
      </div>
    </div>
  </form>
</div>
<div class="modal-footer">
  <div style="text-align: left">
    注：1.匿名评课，将生成随机账号密码 <br/> 2.实名评课，使用工号登录即可<br/>
     3.如果有新增选课账号，重复此操作生成新账号即可<br/>
  </div>
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>

  $("#modalForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success:function(ret){
          if(ret.success){
            $("#modal").modal('hide');
            //$("#jqGrid4").trigger("reloadGrid");
            _detailContentReload2();
          }
        }
      });
    }
  });
  $("#modalForm :checkbox").bootstrapSwitch();
</script>