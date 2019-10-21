<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=QyConstants.QY_REWARD_MAP%>" var="QY_REWARD_MAP"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${qyReward!=null?'编辑':'添加'}七一表彰奖项</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/qyReward_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${qyReward.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 奖项名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${qyReward.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 奖励对象</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="type" data-placeholder="请选择" data-width="272">
						<option></option>
						<c:forEach var="_type" items="${QY_REWARD_MAP}">
							<option value="${_type.key}">${_type.value}</option>
						</c:forEach>
					</select>
					<script type="text/javascript">
						$("#modalForm select[name=type]").val(${qyReward.type});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 备注</label>
				<div class="col-xs-6">
					<textarea class="form-control" type="text" name="remark" value="${qyReward.remark}">${qyReward.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty qyReward?'确定':'添加'}</button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
   $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
   $('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>