<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_TYPE_MAP" value="<%=SystemConstants.MEMBER_TYPE_MAP%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
    <h3><c:if test="${memberTransfer!=null}">编辑</c:if><c:if test="${memberTransfer==null}">添加</c:if>校内组织关系互转</h3>
<hr/>
    <form class="form-horizontal" action="${ctx}/memberTransfer_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberTransfer.id}">
		<div class="row">
			<div class="col-xs-4">
			<div class="form-group">
				<label class="col-xs-5 control-label">用户</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">学工号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${memberTransfer.code}">
				</div>
			</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">转入单位</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="toUnit" value="${memberTransfer.toUnit}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">转入分党委</label>
					<div class="col-xs-6">
						<select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
								name="toPartyId" data-placeholder="请选择" >
							<option value="${toParty.id}">${toParty.name}</option>
						</select>
					</div>
				</div>
				<div class="form-group" style="${(empty toBranch)?'display: none':''}" id="toBranchDiv">
					<label class="col-xs-5 control-label">转入党支部</label>
					<div class="col-xs-6">
						<select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
								name="toBranchId" data-placeholder="请选择">
							<option value="${toBranch.id}">${toBranch.name}</option>
						</select>
					</div>
				</div>
				<script>
					register_party_branch_select($("#modalForm"), "toBranchDiv",
							'${cm:getMetaTypeByCode("mt_direct_branch").id}',
							"${toParty.id}", "${toParty.classId}" , "toPartyId", "toBranchId");
				</script>
				<div class="form-group">
					<label class="col-xs-5 control-label">转出单位</label>
					<div class="col-xs-6">
						<input required class="form-control" type="text" name="fromUnit" value="${memberTransfer.fromUnit}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">转出分党委</label>
					<div class="col-xs-6">
						<select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
								name="fromPartyId" data-placeholder="请选择">
							<option value="${fromParty.id}">${fromParty.name}</option>
						</select>
					</div>
				</div>
				<div class="form-group" style="${(empty fromBranch)?'display: none':''}" id="fromBranchDiv">
					<label class="col-xs-5 control-label">转出党支部</label>
					<div class="col-xs-6">
						<select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
								name="fromBranchId" data-placeholder="请选择">
							<option value="${fromBranch.id}">${fromBranch.name}</option>
						</select>
					</div>
				</div>
				<script>
					register_party_branch_select($("#modalForm"), "fromBranchDiv",
							'${cm:getMetaTypeByCode("mt_direct_branch").id}',
							"${fromParty.id}", "${fromParty.classId}", "fromPartyId", "fromBranchId");
				</script>

				</div>
					<div class="col-xs-4">
						<div class="form-group">
							<label class="col-xs-3 control-label">姓名</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="realname" value="${memberTransfer.realname}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">类别</label>
							<div class="col-xs-6">
								<select required data-rel="select2" name="type" data-placeholder="请选择">
									<option></option>
									<c:forEach items="${MEMBER_TYPE_MAP}" var="_type">
										<option value="${_type.key}">${_type.value}</option>
									</c:forEach>
								</select>
								<script>
									$("#modalForm select[name=type]").val(${memberTransfer.type});
								</script>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">性别</label>
							<div class="col-xs-6">
								<select required data-rel="select2" name="gender" data-placeholder="请选择">
									<option></option>
									<c:forEach items="${GENDER_MAP}" var="_gender">
										<option value="${_gender.key}">${_gender.value}</option>
									</c:forEach>
								</select>
								<script>
									$("#modalForm select[name=gender]").val(${memberTransfer.gender});
								</script>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">年龄</label>
							<div class="col-xs-6">
								<input required class="form-control digits" type="text" name="age" value="${memberTransfer.age}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">民族</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="nation" value="${memberTransfer.nation}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">政治面貌</label>
							<div class="col-xs-6">
								<select required data-rel="select2" name="politicalStatus" data-placeholder="请选择">
									<option></option>
									<c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
										<option value="${_status.key}">${_status.value}</option>
									</c:forEach>
								</select>
								<script>
									$("#modalForm select[name=politicalStatus]").val(${memberTransfer.politicalStatus});
								</script>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">身份证号</label>
							<div class="col-xs-6">
								<input required class="form-control" type="text" name="idcard" value="${memberTransfer.idcard}">
							</div>
						</div>
					</div>
			<div class="col-xs-4">
			<div class="form-group">
				<label class="col-xs-5 control-label">转出单位联系电话</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fromPhone" value="${memberTransfer.fromPhone}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">转出单位传真</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="fromFax" value="${memberTransfer.fromFax}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">党费缴纳至年月</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_payTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberTransfer.payTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">介绍信有效期天数</label>
				<div class="col-xs-6">
                        <input required class="form-control digits" type="text" name="validDays" value="${memberTransfer.validDays}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">转出办理时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_fromHandleTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberTransfer.fromHandleTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">返回修改原因</label>
				<div class="col-xs-6">
					<textarea required class="form-control limited" type="text" name="reason" rows="5">${memberTransfer.reason}</textarea>
				</div>
			</div>
				</div></div>
    </form>
<div class="modal-footer">
    <a href="#"  class="closeView btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberTransfer!=null}">确定</c:if><c:if test="${memberTransfer==null}">添加</c:if>"/>
</div>

<script>

	$('textarea.limited').inputlimiter();
	register_date($('.date-picker'));
	$("#item-content input[type=submit]").click(function(){$("#modalForm").submit(); return false;});
	$("#modalForm").validate({
        submitHandler: function (form) {

			if(!$("#toBranchDiv").is(":hidden")){
				if($('#modalForm select[name=toBranchId]').val()=='') {
					toastr.warning("请选择转入支部。", "提示");
					return;
				}
			}

			if(!$("#fromBranchDiv").is(":hidden")){
				if($('#modalForm select[name=fromBranchId]').val()=='') {
					toastr.warning("请选择转出支部。", "提示");
					return;
				}
			}
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	register_user_select($('#modalForm select[name=userId]'));
</script>