<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dpNpm!=null}">编辑</c:if><c:if test="${dpNpm==null}">添加</c:if>无党派人士</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dp/dpNpm_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${dpNpm.id}">
		<input type="hidden" name="status" value="${dpNpm.status}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
			<div class="col-xs-6">
				<c:if test="${not empty dpNpm}">
					<input type="hidden" value="${dpNpm.userId}" name="userId">
				</c:if>
				<select ${not empty dpNpm?"disabled data-theme='default'":""} required data-rel="select2-ajax"
																				 data-ajax-url="${ctx}/dp/notDpMember_selects"
																				 name="userId" data-width="270"
																				 data-placeholder="请输入账号或姓名或工作证号">
					<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
				</select>
			</div>
		</div>
        <div class="form-group">
            <label class="col-xs-3 control-label"> 认定时间</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 270px">
                    <input class="form-control date-picker" name="addTime" type="text"
                                                                  data-date-format="yyyy.mm.dd" value="${cm:formatDate(dpNpm.addTime,'yyyy.MM.dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
		<div class="form-group">
			<label class="col-xs-3 control-label">现任职务</label>
			<div class="col-xs-6">
				<textarea class="form-control" rows="2" name="post">${dpNpm.post}</textarea>
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 备注</label>
				<div class="col-xs-6">
					<textarea class="form-control" rows="3" name="remark">${dpNpm.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty dpNpm?'确定':'添加'}</button>
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
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>