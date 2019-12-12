<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>更换党组织</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberApply_changeParty" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="userId" value="${memberApply.userId}">
        <div class="form-group">
			<label class="col-xs-4 control-label">姓名</label>
			<div class="col-xs-6 label-text">
				${memberApply.user.realname}
			</div>
		</div>
        <div class="form-group">
			<label class="col-xs-4 control-label">当前党组织</label>
			<div class="col-xs-6 label-text">
				${cm:displayParty(memberApply.partyId, memberApply.branchId)}
			</div>
		</div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>转入${_p_partyName}</label>
            <div class="col-xs-6">
                <select required class="form-control"  data-rel="select2-ajax"
                        data-width="360"
                        data-ajax-url="${ctx}/party_selects"
                        name="partyId" data-placeholder="请选择" >
                    <option value=""></option>
                </select>
            </div>
        </div>
        <div class="form-group" style="display: none" id="branchDiv">
            <label class="col-xs-4 control-label"><span class="star">*</span>转入党支部</label>
            <div class="col-xs-6">
                <select required class="form-control"  data-rel="select2-ajax"
                        data-width="360"
                        data-ajax-url="${ctx}/branch_selects"
                        name="branchId" data-placeholder="请选择">
                    <option value=""></option>
                </select>
            </div>
        </div>
        <div class="form-group">
			<label class="col-xs-4 control-label"><span class="star">*</span>变更原因</label>
			<div class="col-xs-6">
				<textarea required class="form-control limited" name="remark"></textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <div class="note">
		注：此功能仅适用于该人员已转院系的情况，请谨慎操作。
	</div>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $.register.party_branch_select($("#modalForm"), "branchDiv",
            '${cm:getMetaTypeByCode("mt_direct_branch").id}',
            "${party.id}", "${party.classId}" , "partyId", "branchId");
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        //SysMsg.success('操作成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                }
            });
        }
    });
</script>