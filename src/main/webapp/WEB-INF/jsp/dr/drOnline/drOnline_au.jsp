<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=DrConstants.DR_MEMBER_STATUS_NOW%>" var="DR_MEMBER_STATUS_NOW"/>
<div class="modal-header">
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>按批次${drOnline!=null?'编辑':'添加'}线上民主推荐</h3>
</div>
<div class="modal-body overflow-visible">
    <form class="form-horizontal" action="${ctx}/dr/drOnline_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${drOnline.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>年份</label>
				<div class="col-xs-6">
					<div class="input-group">
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						<input required style="width: 80px;" autocomplete="off" class="form-control date-picker"
							   placeholder="请选择年份"
							   name="year" type="text"
							   data-date-format="yyyy" data-date-min-view-mode="2"
							   value="${empty drOnline.year?_thisYear:drOnline.year}"/>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>推荐日期</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker"
							   name="_recommendDate"
							   type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${empty drOnline.recommendDate?_today:(cm:formatDate(drOnline.recommendDate,'yyyy-MM-dd'))}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>推荐类型</label>
				<div class="col-xs-6">
					<select required data-rel="select2" data-width="273"
							name="type" data-placeholder="请选择">
						<option></option>
						<c:import url="/metaTypes?__code=mc_dr_type"/>
					</select>
					<script type="text/javascript">
						$("#modalForm select[name=type]").val(${drOnline.type});
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 说明模板</label>
				<div class="col-xs-6">
					<select data-rel="select2" data-width="273"
							name="noticeId" data-placeholder="请选择说明模板">
						<option></option>
						<c:forEach items="${drOnlineNotices}" var="drOnlineNotice">
							<option value="${drOnlineNotice.value.id}">${drOnlineNotice.value.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<%--<div class="form-group">
				<label class="col-xs-3 control-label">推荐职务</label>
				<div class="col-xs-6">
					<input type="hidden" name="recordId" value="${scRecord.id}">
					<input id="postName" readonly class="form-control" type="text" value="${scRecord.postName}">
				</div>
				<button type="button" class="btn btn-sm btn-success" id="selectPostBtn"><i class="fa fa-plus"></i> 选择岗位
				</button>
			</div>--%>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>推荐组负责人</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/drMember_selects"
							name="chiefMemberId" data-placeholder="请输入账号或姓名或学工号" data-width="270">
						<option value="${chiefMember.id}" delete="${chiefMember.status!=DR_MEMBER_STATUS_NOW}">${chiefMember.user.realname}-${chiefMember.user.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">推荐组成员</label>
				<div class="col-xs-8">
					<select class="multiselect" multiple="" name="memberIds">
						<c:forEach var="ms" items="<%=DrConstants.DR_MEMBER_STATUS_MAP%>">
							<optgroup label="${ms.value}" value="-1">
								<c:forEach items="${drMemberListMap.get(ms.key)}" var="drMember">
									<option value="${drMember.id}">${drMember.user.realname}(${drMember.user.code})</option>
								</c:forEach>
							</optgroup>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 推荐开始时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker"
							   name="_startTime"
							   type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${(cm:formatDate(drOnline.startTime,'yyyy-MM-dd'))}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 推荐截止时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker"
							   name="_endTime"
							   type="text"
							   data-date-format="yyyy-mm-dd"
							   value="${(cm:formatDate(drOnline.endTime,'yyyy-MM-dd'))}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 备注</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="remark" value="${drOnline.remark}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty drOnline?'确定':'添加'}</button>
</div>
<script>

	$.register.multiselect($('#modalForm select[name=memberIds]'), ${cm:toJSONArray(selectMemberIds)}, {
		enableClickableOptGroups: true,
		enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false
	});

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
	$.register.del_select($('select[name=chiefMemberId]'));
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>