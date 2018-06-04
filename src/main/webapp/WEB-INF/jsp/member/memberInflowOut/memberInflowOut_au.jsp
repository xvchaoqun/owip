<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_TYPE_MAP" value="<%=MemberConstants.MEMBER_TYPE_MAP%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberInflow!=null}">编辑</c:if><c:if test="${memberInflow==null}">添加</c:if>流入党员转出</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberInflowOut_au" id="modalForm" method="post">
			<div class="form-group">
				<label class="col-xs-3 control-label">用户</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax"
							data-ajax-url="${ctx}/memberInflow_selects?hasOutApply=0&status=<%=MemberConstants.MEMBER_INFLOW_STATUS_PARTY_VERIFY%>"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">转出单位</label>
					<div class="col-xs-6" >
						<textarea required class="form-control"
								  type="text" name="outUnit">${memberInflow.outUnit}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">转出地</label>
					<div class="col-xs-6">
						<select required class="loc_province" name="outLocation" style="width:120px;" data-placeholder="请选择">
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">转出时间</label>
					<div class="col-xs-6">
						<div class="input-group" style="width: 200px">
							<input required class="form-control date-picker" name="_outTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberInflow.outTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberInflow!=null}">确定</c:if><c:if test="${memberInflow==null}">添加</c:if>"/>
</div>
<script type="text/javascript" src="${ctx}/extend/js/location.js"></script>
<script>
	jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
	jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();

	showLocation("${memberInflow.province}");
	$('textarea.limited').inputlimiter();
	$.register.date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						$("#modal").modal("hide");
						//SysMsg.success('操作成功。', '成功', function () {
							page_reload();
						//});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$.register.user_select($('#modalForm select[name=userId]'));
</script>