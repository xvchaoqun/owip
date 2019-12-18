<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cla/constants.jsp" %>
    <h3 class="header">干部请假申请变更</h3>
    <form class="form-horizontal" action="${ctx}/cla/claApply_change" autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${claApply.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>干部</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
							name="cadreId" data-placeholder="请选择干部">
						<option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>申请日期</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 200px">
						<input required class="form-control date-picker" name="_applyDate" type="text"
							   data-date-format="yyyy-mm-dd" value="${cm:formatDate(claApply.applyDate,'yyyy-MM-dd')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>类别</label>
				<div class="col-xs-6">
					<select required name="type" data-rel="select2" data-placeholder="请选择">
						<option></option>
						<c:forEach items="${CLA_APPLY_TYPE_MAP}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=type]").val('${claApply.type}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>出发时间</label>
				<div class="col-xs-6">
					<div class="input-group"  style="width: 200px">
						<input required class="form-control datetime-picker" name="startTime" type="text"
							   value="${cm:formatDate(claApply.startTime,'yyyy-MM-dd HH:mm')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>返校时间</label>
				<div class="col-xs-6">
					<div class="input-group"  style="width: 200px">
						<input required class="form-control datetime-picker" name="endTime" type="text"
							   value="${cm:formatDate(claApply.endTime,'yyyy-MM-dd HH:mm')}" />
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>目的地</label>
				<div class="col-xs-6" >
					<input required style="width: 200px" type="text" name="destination" value="${claApply.destination}"/>
				</div>
			</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>请假事由</label>
			<div class="col-xs-9 choice label-text">
				<textarea required class="form-control limited" name="reason" maxlength="100" style="width: 255px">${claApply.reason}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">同行人员</label>
			<div class="col-xs-9">
				<input type="text" name="peerStaff" value="${claApply.peerStaff}"  style="width: 255px" />
			</div>
		</div>

		<div class="form-group">
			<label class="col-xs-3 control-label">备注</label>
			<div class="col-xs-2">
				<textarea  class="form-control limited" name="remark" maxlength="100" style="width: 255px">${claApply.remark}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">本人说明材料</label>
			<div class="col-xs-2">
			<input  class="form-control" type="file" name="_modifyProof" style="width: 220px"/>
				</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>变更原因</label>
			<div class="col-xs-2">
				<textarea required class="form-control" name="modifyRemark" style="width: 255px"></textarea>
			</div>
		</div>
    </form>
<div class="clearfix form-actions">
	<div class="col-md-offset-3 col-md-9">
		<button class="btn btn-info" type="submit">
			<i class="ace-icon fa fa-check bigger-110"></i>
			提交
		</button>
		&nbsp; &nbsp; &nbsp;
		<button class="hideView btn btn-default" type="button">
			<i class="ace-icon fa fa-undo bigger-110"></i>
			取消
		</button>
	</div>
</div>

<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>

	$.fileInput($("input[name=_modifyProof]"))

	$("button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

			$(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						//SysMsg.success('修改成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
							$.hideView();
						//});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'))
	$.register.datetime($('.datetime-picker'));
	$.register.user_select($('[data-rel="select2-ajax"]'));
	$('textarea.limited').inputlimiter();
</script>