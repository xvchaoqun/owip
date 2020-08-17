<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${pcsConfig!=null}">编辑</c:if><c:if test="${pcsConfig==null}">添加</c:if>党代会</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcs/pcsConfig_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${pcsConfig.id}">
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>届数</label>
				<div class="col-xs-6">
                    <input required class="form-control" type="text" name="name" value="${pcsConfig.name}">
				</div>
			</div>
        <shiro:hasPermission name="pcsAdmin:*">
        <div class="form-group">
            <label class="col-xs-4 control-label">是否当前党代会</label>
            <div class="col-xs-6">
                <input type="checkbox" class="big" name="isCurrent" ${pcsConfig.isCurrent?"checked":""}/>
            </div>
        </div>
        </shiro:hasPermission>
        <shiro:hasPermission name="pcsProposal:menu">
        <div class="form-group">
            <label class="col-xs-4 control-label">提交提案时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control datetime-picker" type="text"  name="proposalSubmitTime"
                           value="${cm:formatDate(pcsConfig.proposalSubmitTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">征集附议人时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control datetime-picker" type="text"  name="proposalSupportTime"
                           value="${cm:formatDate(pcsConfig.proposalSupportTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">立案附议人数</label>
            <div class="col-xs-6">
                <input class="form-control num" type="text" maxlength="4"
                       name="proposalSupportCount" value="${pcsConfig.proposalSupportCount}">
            </div>
        </div>
        </shiro:hasPermission>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text"
                          name="remark" maxlength="200">${pcsConfig.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${pcsConfig!=null}">确定</c:if><c:if test="${pcsConfig==null}">添加</c:if>"/>
</div>

<script>
    $.register.datetime($('.datetime-picker'));
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $('textarea.limited').inputlimiter();
</script>