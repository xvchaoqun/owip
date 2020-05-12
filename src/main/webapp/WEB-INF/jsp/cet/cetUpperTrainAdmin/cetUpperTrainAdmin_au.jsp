<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="CET_UPPER_TRAIN_UPPER" value="<%=CetConstants.CET_UPPER_TRAIN_UPPER%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetUpperTrainAdmin!=null}">编辑</c:if><c:if test="${cetUpperTrainAdmin==null}">添加</c:if>上级调训单位管理员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUpperTrainAdmin_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetUpperTrainAdmin.id}">
        <input type="hidden" name="upperType" value="${upperType}">

            <c:set var="isUnitAdmin" value="${not empty cetUpperTrainAdmin && !cetUpperTrainAdmin.type}"/>
            <c:set var="isLeaderAdmin" value="${cetUpperTrainAdmin.type}"/>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 所属单位</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax"
                                                                data-width="273" data-ajax-url="${ctx}/unit_selects"
                                                                name="unitId" data-placeholder="请选择${upperType==CET_UPPER_TRAIN_UPPER?'派出':'组织'}单位">
                        <option value="${cetUpperTrainAdmin.unit.id}">${cetUpperTrainAdmin.unit.name}</option>
                    </select>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 选择管理员</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax"
                            data-width="273" data-ajax-url="${ctx}/sysUser_selects?types=<%=SystemConstants.USER_TYPE_JZG%>"
                            name="userId" data-placeholder="请选择">
                        <option value="${cetUpperTrainAdmin.user.id}">${cetUpperTrainAdmin.user.realname}</option>
                    </select>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetUpperTrainAdmin!=null}">确定</c:if><c:if test="${cetUpperTrainAdmin==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.ajax_select($('#modalForm select[name=unitId]'));
    $.register.user_select($('#modalForm select[name=leaderUserId]'));
    $.register.user_select($('#modalForm select[name=userId]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>