<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_TYPE_MAP" value="<%=MemberConstants.MEMBER_TYPE_MAP%>"/>
<c:set var="OR_STATUS_MAP" value="<%=SystemConstants.OR_STATUS_MAP%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberOutflow!=null}">编辑</c:if><c:if test="${memberOutflow==null}">添加</c:if>流出党员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberOutflow_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberOutflow.id}">
		<div class="row">
			<div class="col-xs-5">
			<div class="form-group">
				<label class="col-xs-3 control-label">用户</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">原职业</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="originalJob" data-placeholder="请选择">
							<option></option>
							<c:import url="/metaTypes?__code=mc_job"/>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=originalJob]").val(${memberOutflow.originalJob});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">外出流向</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="direction" data-placeholder="请选择">
							<option></option>
							<c:import url="/metaTypes?__code=mc_flow_direction"/>
						</select>
						<script type="text/javascript">
							$("#modal form select[name=direction]").val(${memberOutflow.direction});
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">流出时间</label>
					<div class="col-xs-6">
						<div class="input-group" style="width: 200px">
							<input required class="form-control date-picker" name="_flowTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberOutflow.flowTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">流出省份</label>
					<div class="col-xs-6">
						<select required class="loc_province" name="province" style="width:120px;" data-placeholder="请选择">
						</select>
					</div>
				</div>
				</div>
				<div class="col-xs-7">
			<div class="form-group">
				<label class="col-xs-5 control-label">流出原因</label>
				<div class="col-xs-6">
                        <textarea required class="form-control limited" type="text" name="reason" rows="5">${memberOutflow.reason}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">是否持有《中国共产党流动党员活动证》</label>
				<div class="col-xs-6">
					<label>
						<input name="hasPapers" ${memberOutflow.hasPapers?"checked":""}  type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label">组织关系状态</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="orStatus" data-placeholder="请选择">
						<option></option>
						<c:forEach items="${OR_STATUS_MAP}" var="_status">
							<option value="${_status.key}">${_status.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=orStatus]").val(${memberOutflow.orStatus});
					</script>
				</div>
			</div>
					</div></div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberOutflow!=null}">确定</c:if><c:if test="${memberOutflow==null}">添加</c:if>"/>
</div>
<script type="text/javascript" src="${ctx}/extend/js/location.js"></script>
<script>
	jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
	jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();

	$("#modalForm :checkbox").bootstrapSwitch();
	showLocation("${memberOutflow.province}");

	$('textarea.limited').inputlimiter();
	register_date($('.date-picker'));
    $("#modalForm").validate({
        submitHandler: function (form) {

			if(!$("#branchDiv").is(":hidden")){
				if($('#modalForm select[name=branchId]').val()=='') {
					SysMsg.warning("请选择支部。", "提示");
					return;
				}
			}
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						$("#modal").modal('hide');
						//SysMsg.success('提交成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
						//});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	register_user_select($('#modalForm select[name=userId]'));
</script>