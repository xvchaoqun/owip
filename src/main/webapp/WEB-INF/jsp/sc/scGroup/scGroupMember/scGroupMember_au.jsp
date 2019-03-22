<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scGroupMember!=null}">编辑</c:if><c:if test="${scGroupMember==null}">添加</c:if>干部工作小组会组成名单</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scGroupMember_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${scGroupMember.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">成员</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax"
                            data-ajax-url="${ctx}/cadre_selects?key=1&types=${CADRE_STATUS_MIDDLE},${CADRE_STATUS_LEADER},${CADRE_STATUS_LEADER_LEAVE}"
                            name="userId" data-placeholder="请输入账号或姓名或教工号"  data-width="270">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否现有成员</label>
				<div class="col-xs-6">
                    <input type="checkbox" class="big" name="isCurrent" ${(empty scGroupMember || scGroupMember.isCurrent)?"checked":""}/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否组长</label>
				<div class="col-xs-6">
                    <input type="checkbox" class="big" name="isLeader" ${scGroupMember.isLeader?"checked":""}/>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scGroupMember!=null}">确定</c:if><c:if test="${scGroupMember==null}">添加</c:if></button>
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

    $.register.user_select($('[data-rel="select2-ajax"]'));
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>