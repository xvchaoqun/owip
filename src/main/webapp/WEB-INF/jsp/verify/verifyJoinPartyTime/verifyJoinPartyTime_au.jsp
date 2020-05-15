<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${verifyJoinPartyTime!=null?'编辑':'添加'}入党时间认定</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/verify/verifyJoinPartyTime_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
		<input type="hidden" name="id" value="${verifyJoinPartyTime.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 所属干部</label>
			<div class="col-xs-6">
				<select required data-rel="select2-ajax"
						data-ajax-url="${ctx}/cadre_selects?types=${CADRE_STATUS_INSPECT},${CADRE_STATUS_CJ},${CADRE_STATUS_CJ_LEAVE},${CADRE_STATUS_LEADER},${CADRE_STATUS_LEADER_LEAVE}"
						name="cadreId" data-placeholder="请输入账号或姓名或学工号"  data-width="270">
					<option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
				</select>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty verifyJoinPartyTime?'确定':'添加'}</button>
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
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>