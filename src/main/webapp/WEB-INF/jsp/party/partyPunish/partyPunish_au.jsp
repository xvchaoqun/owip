<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="OW_PARTY_REPU_MAP" value="<%=OwConstants.OW_PARTY_REPU_MAP%>"/>
<c:set var="OW_PARTY_REPU_PARTY" value="<%=OwConstants.OW_PARTY_REPU_PARTY%>"/>
<c:set var="OW_PARTY_REPU_BRANCH" value="<%=OwConstants.OW_PARTY_REPU_BRANCH%>"/>
<c:set var="OW_PARTY_REPU_MEMBER" value="<%=OwConstants.OW_PARTY_REPU_MEMBER%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3><c:if test="${partyReward!=null}">修改</c:if><c:if test="${partyReward==null}">添加</c:if>党内惩罚信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/party/partyPunish_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${partyPunish.id}">
		<c:if test="${cls==OW_PARTY_REPU_PARTY}">
		<div class="form-group">
			<label class="col-xs-3 control-label">类型</label>
			<div class="col-xs-6 label-text">
				<c:forEach items="${OW_PARTY_REPU_MAP}" var="_type">
					<c:if test="${_type.key==cls}">
						惩罚<span>${_type.value}</span>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div class="form-group">
			<c:if test="${party!=null}">
				<label class="col-xs-3 control-label">${_p_partyName}名称</label>
				<div class="col-xs-6 label-text">
					<input type="hidden" name="partyId" value="${party.id}">
						${party.name}
				</div>
			</c:if>
			<c:if test="${party==null}">
				<label class="col-xs-3 control-label"><span class="star">*</span>${_p_partyName}名称</label>
				<div class="col-xs-6">
					<select required class="form-control" data-rel="select2-ajax"
							data-ajax-url="${ctx}/party_selects?auth=1"
							name="partyId" data-placeholder="请选择" data-width="270">
						<option value="${party.id}">${party.name}</option>
					</select>
				</div>
			</c:if>
		</div>
		</c:if>
		<c:if test="${cls==OW_PARTY_REPU_BRANCH}">
		<div class="form-group">
			<label class="col-xs-3 control-label">类型</label>
			<div class="col-xs-6 label-text">
				<c:forEach items="${OW_PARTY_REPU_MAP}" var="_type">
					<c:if test="${_type.key==cls}">
						惩罚<span>${_type.value}</span>
					</c:if>
				</c:forEach>
			</div>
		</div>
			<c:if test="${branch!=null}">
				<div class="form-group">
					<label class="col-xs-3 control-label">${_p_partyName}名称</label>
					<div class="col-xs-6 label-text">
						<input type="hidden" name="branchPartyId" value="${branchParty.id}">
							${branchParty.name}
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">党支部名称</label>
					<div class="col-xs-6 label-text">
						<input type="hidden" name="branchId" value="${branch.id}">
							${branch.name}
					</div>
				</div>
			</c:if>
			<c:if test="${branch==null}">
				<div class="form-group">
					<label class="col-xs-3 control-label"><span class="star">*</span>所属${_p_partyName}</label>
					<div class="col-xs-6">
						<select required class="form-control" data-rel="select2-ajax"
								data-ajax-url="${ctx}/party_selects?auth=1"
								name="branchPartyId" data-placeholder="请选择" data-width="270">
							<option value="${branchParty.id}">${branchParty.name}</option>
						</select>
					</div>
				</div>
				<div class="form-group" id="branchDiv">
					<label class="col-xs-3 control-label"><span class="star">*</span>党支部名称</label>
					<div class="col-xs-6">
						<select class="form-control" data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1"
								name="branchId" data-placeholder="请选择" data-width="270">
							<option value="${branch.id}">${branch.name}</option>
						</select>
					</div>
				</div>
			</c:if>
		</c:if>
		<c:if test="${cls==OW_PARTY_REPU_MEMBER}">
		<div class="form-group">
			<label class="col-xs-3 control-label">类型</label>
			<div class="col-xs-6 label-text">
				<c:forEach items="${OW_PARTY_REPU_MAP}" var="_type">
					<c:if test="${_type.key==cls}">
						惩罚<span>${_type.value}</span>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<div class="form-group">
			<c:if test="${user!=null}">
				<label class="col-xs-3 control-label">党员姓名</label>
				<div class="col-xs-6 label-text">
					<input type="hidden" name="userId" value="${user.id}">
						${user.realname}
				</div>
			</c:if>
			<c:if test="${user==null}">
				<label class="col-xs-3 control-label"><span class="star">*</span>账号</label>
				<div class="col-xs-6">
					<select required class="form-control" data-rel="select2-ajax"
							data-ajax-url="${ctx}/member_selects"
							name="userId" data-width="270"
							data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</c:if>
		</div>
		</c:if>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 处分日期</label>
			<div class="col-xs-6">
				<div class="input-group date" data-date-format="yyyy.mm.dd">
					<input required class="form-control date-picker" name="punishTime" type="text" data-width="270"
						   placeholder="格式：yyyy.mm.dd"
						   value="${cm:formatDate(partyPunish.punishTime,'yyyy.MM.dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 处分截止日期</label>
				<div class="col-xs-6">
                     <div class="input-group date" data-date-format="yyyy.mm.dd">
						 <input reqired class="form-control date-picker" name="endTime" type="text" data-width="270"
								placeholder="格式：yyyy.mm.dd"
								value="${cm:formatDate(partyPunish.punishTime, 'yyyy.MM.dd')}"/>
						 <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					 </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 受何种处分</label>
				<div class="col-xs-6">
					<textarea required class="form-control" name="name">${partyPunish.name}</textarea>
					<span class="help-block">注：不要加书名号。</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">处分单位</label>
				<div class="col-xs-6">
					<textarea class="form-control" name="unit">${partyPunish.unit}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="remark" value="${partyPunish.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty partyPunish?'确定':'添加'}</button>
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
                        $("#jqGrid_punish").trigger("reloadGrid");
						$("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
	$.register.date($('.input-group.date'));
</script>