<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="OW_PARTY_REPU_MAP" value="<%=OwConstants.OW_PARTY_REPU_MAP%>"/>
<c:set var="OW_PARTY_REPU_PARTY" value="<%=OwConstants.OW_PARTY_REPU_PARTY%>"/>
<c:set var="OW_PARTY_REPU_BRANCH" value="<%=OwConstants.OW_PARTY_REPU_BRANCH%>"/>
<c:set var="OW_PARTY_REPU_MEMBER" value="<%=OwConstants.OW_PARTY_REPU_MEMBER%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3><c:if test="${partyReward!=null}">修改</c:if><c:if test="${partyReward==null}">添加</c:if>党内奖励信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/party/partyReward_au" autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${partyReward.id}">
		<input type="hidden" name="partyId" value="${party.id}">
        <input type="hidden" name="branchId" value="${branch.id}">
        <input type="hidden" name="userId" value="${user.id}">
		<c:if test="${cls==OW_PARTY_REPU_PARTY}">
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6 label-text">
					<c:forEach items="${OW_PARTY_REPU_MAP}" var="_type">
						<c:if test="${_type.key==cls}">
							奖励<span>${_type.value}</span>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">分党委名称</label>
				<div class="col-xs-6 label-text">
					${party.name}
				</div>
			</div>
		</c:if>
		<c:if test="${cls==OW_PARTY_REPU_BRANCH}">
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6 label-text">
					<c:forEach items="${OW_PARTY_REPU_MAP}" var="_type">
						<c:if test="${_type.key==cls}">
							奖励<span>${_type.value}</span>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">党支部名称</label>
				<div class="col-xs-6 label-text">
                    ${branch.name}
				</div>
			</div>
		</c:if>
		<c:if test="${cls==OW_PARTY_REPU_MEMBER}">
			<div class="form-group">
				<label class="col-xs-3 control-label">类型</label>
				<div class="col-xs-6 label-text">
					<c:forEach items="${OW_PARTY_REPU_MAP}" var="_type">
						<c:if test="${_type.key==cls}">
							奖励<span>${_type.value}</span>
						</c:if>
					</c:forEach>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">党员姓名</label>
				<div class="col-xs-6 label-text">
                     ${user.realname}
				</div>
			</div>
		</c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 获奖日期</label>
				<div class="col-xs-6">
					<div class="input-group date" data-date-format="yyyy.mm.dd">
						<input required class="form-control date-picker" name="rewardTime" type="text" data-width="270"
							   placeholder="格式：yyyy.mm.dd"
							   value="${cm:formatDate(partyReward.rewardTime,'yyyy.MM.dd')}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group ${cls==OW_PARTY_REPU_MEMBER?'hidden':''}">
				<label class="col-xs-3 control-label"><span class="star">*</span> 获奖类型</label>
				<div class="col-xs-6">
					<select ${cls==OW_PARTY_REPU_MEMBER?'':'required'} data-rel="select2" name="rewardType" data-width="270"
							data-placehoder="请选择">
						<option></option>
						<c:if test="${cls==OW_PARTY_REPU_PARTY}">
						<c:import url="/metaTypes?__code=mt_party_reward"/>
						</c:if>
						<c:if test="${cls==OW_PARTY_REPU_BRANCH}">
						<c:import url="/metaTypes?__code=mt_branch_reward"/>
						</c:if>
					</select>
					<script type="text/javascript">
						$("#modalForm select[name=rewardType]").val(${partyReward.rewardType});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 获得奖项</label>
				<div class="col-xs-6">
					<textarea required class="form-control" name="name">${partyReward.name}</textarea>
					<span class="help-block">注：不要加书名号。</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">颁奖单位</label>
				<div class="col-xs-6">
						<textarea class="form-control" name="unit">${partyReward.unit}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 获奖证书</label>
				<div class="col-xs-6">
					<input ${ empty partyReward.proof?'required':''} class="form-control" type="file" name="_proof"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 获奖证书文件名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="proofFilename" value="${partyReward.proofFilename}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="remark" value="${partyReward.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty partyReward?'确定':'添加'}</button>
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
                        $("#jqGrid2").trigger("reloadGrid");
                    }2
                    $btn.button('reset');
                }
            });
        }
    });
	$.fileInput($('#modalForm input[type=file]'));
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
	$.register.date($('.input-group.date'));
</script>