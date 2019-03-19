<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_TYPE_MAP" value="<%=MemberConstants.MEMBER_TYPE_MAP%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberInflow!=null}">编辑</c:if><c:if test="${memberInflow==null}">添加</c:if>流入党员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberInflow_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberInflow.id}">
		<div class="row">
			<div class="col-xs-6">
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>用户</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/notMember_selects"
							name="userId" data-placeholder="请输入账号或姓名或学工号">
						<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>类别</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="type" data-placeholder="请选择类别">
						<option></option>
						<c:forEach items="${MEMBER_TYPE_MAP}" var="_type">
							<option value="${_type.key}">${_type.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=type]").val(${memberInflow.type});
					</script>
				</div>
			</div>

				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>${_p_partyName}</label>
					<div class="col-xs-6">
						<select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?auth=1"
								name="partyId" data-placeholder="请选择">
							<option value="${party.id}">${party.name}</option>
						</select>
					</div>
				</div>
				<div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
					<label class="col-xs-4 control-label">党支部</label>
					<div class="col-xs-6">
						<select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?auth=1"
								name="branchId" data-placeholder="请选择">
							<option value="${branch.id}">${branch.name}</option>
						</select>
					</div>
				</div>
				<script>
					$.register.party_branch_select($("#modalForm"), "branchDiv",
							'${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
				</script>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>原职业</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="originalJob" data-placeholder="请选择">
						<option></option>
						<c:import url="/metaTypes?__code=mc_job"/>
					</select>
					<script type="text/javascript">
						$("#modalForm select[name=originalJob]").val("${memberInflow.originalJob}");
					</script>
				</div>
			</div>
				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>流入前所在省份</label>
					<div class="col-xs-6" id="loc_province_container1">
						<select required class="loc_province" name="province" style="width:120px;" data-placeholder="请选择">
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-4 control-label"><span class="star">*</span>流入原因</label>
					<div class="col-xs-6">
						<textarea required class="form-control limited" type="text" name="flowReason" rows="5">${memberInflow.flowReason}</textarea>
					</div>
				</div>
				</div>
				<div class="col-xs-6">

					<div class="form-group">
						<label class="col-xs-4 control-label"><span class="star">*</span>流入时间</label>
						<div class="col-xs-6">
							<div class="input-group">
								<input required class="form-control date-picker" name="_flowTime" type="text"
									   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberInflow.flowTime,'yyyy-MM-dd')}" />
								<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
							</div>
						</div>
					</div>

			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>入党时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_growTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberInflow.growTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>组织关系所在地</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="orLocation" value="${memberInflow.orLocation}">
				</div>
			</div>
			<%--<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>转出单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="outflowUnit" value="${memberInflow.outUnit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>转出地</label>
				<div class="col-xs-6" id="loc_province_container2">
					<select required class="loc_province" name="outflowLocation" style="width:120px;" data-placeholder="请选择">
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>转出时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" name="_outTime" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberInflow.outTime,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>--%>
					<div class="form-group">
						<label class="col-xs-4 control-label">是否持有《中国共产党流动党员活动证》</label>
						<div class="col-xs-6">
							<label>
								<input name="hasPapers" ${memberInflow.hasPapers?"checked":""}  type="checkbox" />
								<span class="lbl"></span>
							</label>
						</div>
					</div>
					</div></div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberInflow!=null}">确定</c:if><c:if test="${memberInflow==null}">添加</c:if>"/>
</div>
<script type="text/javascript" src="${ctx}/extend/js/location.js"></script>
<script>
	jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
	jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();

	$("#modal :checkbox").bootstrapSwitch();
	//alert(JSON.stringify(Location.items[0]))
	showLocation("${memberInflow.province}",null, null, $("#loc_province_container1"));
	showLocation("${memberInflow.outLocation}",null, null, $("#loc_province_container2"));
	$('textarea.limited').inputlimiter();
	$.register.date($('.date-picker'));
    $("#modal form").validate({
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
	$.register.user_select($('#modalForm select[name=userId]'));
</script>