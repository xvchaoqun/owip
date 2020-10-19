<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>添加每月参与线上收费的党支部</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdBranch_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <%--<input type="hidden" name="id" value="${pmdBranch.id}">--%>
			<input type="hidden" name="partyId" value="${param.partyId}"/>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>所属党委</label>
				<div class="col-xs-6 label-text">
                        ${party.name}
				</div>
			</div>
			<div class="form-group" id="branchDiv">
				<label class="col-xs-3 control-label"><span class="star">*</span>党支部</label>
				<div class="col-xs-6">
					<select required class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1&partyId=${param.partyId}&del=0"
							name="branchId" data-placeholder="请选择" data-width="270">
					</select>
				</div>
			</div>
			<%--<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>所属党委</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="partyName" value="${pmdBranch.partyName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>支部名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="branchName" value="${pmdBranch.branchName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党委的顺序</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="sortOrder" value="${pmdBranch.sortOrder}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>往月延迟缴费党员数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="historyDelayMemberCount" value="${pmdBranch.historyDelayMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>应补缴往月党费数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="historyDelayPay" value="${pmdBranch.historyDelayPay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>是否报送</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="hasReport" value="${pmdBranch.hasReport}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>党员总数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="memberCount" value="${pmdBranch.memberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>本月应交党费数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="duePay" value="${pmdBranch.duePay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>本月按时缴费党员数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="finishMemberCount" value="${pmdBranch.finishMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>本月实缴党费数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realPay" value="${pmdBranch.realPay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>本月线上缴费数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="onlineRealPay" value="${pmdBranch.onlineRealPay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>本月现金缴费数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cashRealPay" value="${pmdBranch.cashRealPay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>本月延迟缴费数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="delayPay" value="${pmdBranch.delayPay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>本月延迟缴费党员数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="delayMemberCount" value="${pmdBranch.delayMemberCount}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>实补缴往月党费数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="realDelayPay" value="${pmdBranch.realDelayPay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>线上补缴往月党费数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="onlineRealDelayPay" value="${pmdBranch.onlineRealDelayPay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>现金补缴往月党费数</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="cashRealDelayPay" value="${pmdBranch.cashRealDelayPay}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>report_user_id</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="reportUserId" value="${pmdBranch.reportUserId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>report_time</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="reportTime" value="${pmdBranch.reportTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>report_ip</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="reportIp" value="${pmdBranch.reportIp}">
				</div>
			</div>--%>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="submitBtn" type="button" class="btn btn-primary" value="<c:if test="${pmdBranch!=null}">确定</c:if><c:if test="${pmdBranch==null}">添加</c:if>"/>
</div>

<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
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
    $.register.ajax_select($('#modalForm select[name=branchId]'));
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>