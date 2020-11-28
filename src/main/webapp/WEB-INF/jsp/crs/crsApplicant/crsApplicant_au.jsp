<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${crsApplicant!=null}">编辑</c:if><c:if test="${crsApplicant==null}">添加</c:if>报名人员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsApplicant_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
            <input type="hidden" name="postId" value="${param.postId}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>用户</label>
				<div class="col-xs-8">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                            data-width="280"
                            name="userId" data-placeholder="请输入账号或姓名或工作证号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">工作设想和预期目标</label>
				<div class="col-xs-8">
                    <textarea name="report" class="form-control limited" rows="12" maxlength="1000">${crsApplicant.report}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${crsApplicant!=null}">确定</c:if><c:if test="${crsApplicant==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>