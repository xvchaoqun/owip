<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=DpConstants.DP_PR_CM_MAP%>" var="DP_PR_CM_MAP"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${dpPrCm!=null?'编辑':'添加'}人大代表、政协委员信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dp/dpPrCm_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${dpPrCm.id}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 账号</label>
			<div class="col-xs-6">
				<c:if test="${not empty dpPrCm}">
					<input type="hidden" value="${dpPrCm.userId}" name="userId">
				</c:if>
				<select ${not empty dpPrCm?"disabled data-theme='default'":""} required data-rel="select2-ajax"
																			 data-ajax-url="${ctx}/sysUser_selects"
																			 name="userId" data-width="270"
																			 data-placeholder="请输入账号或姓名或学工号">
					<option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 所属类别</label>
			<div class="col-xs-6">
				<select required data-rel="select2" name="type" data-width="270" data-placeholder="请选择"  data-width="120">
					<option></option>
					<c:forEach items="${DP_PR_CM_MAP}" var="_type">
						<option value="${_type.key}">${_type.value}</option>
					</c:forEach>
				</select>
				<script>
					$("#modalForm select[name=type]").val(${dpPrCm.type});
				</script>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 参加工作时间</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 270px">
					<input class="form-control date-picker" type="text" name="workTime" data-date-format="yyyy.mm.dd" value="${cm:formatDate(dpPrCm.workTime,'yyyy.MM.dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 当选时间</label>
			<div class="col-xs-6">
				<div class="input-group" style="width: 270px">
					<input required class="form-control date-picker" type="text" name="electTime" data-date-format="yyyy.mm.dd" value="${cm:formatDate(dpPrCm.electTime,'yyyy.MM.dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 所属单位及职位</label>
			<div class="col-xs-6">
				<textarea class="form-control" rows="3" name="unitPost">${dpPrCm.unitPost}</textarea>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 行政级别</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="executiveLevel" value="${dpPrCm.executiveLevel}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 当选时职务</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="electPost" value="${dpPrCm.electPost}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 当选届次</label>
			<div class="col-xs-6">
				<input required class="form-control" type="text" name="electSession" value="${dpPrCm.electSession}" placeholder="请填写阿拉伯数字1、2、3">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 最高学历</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="education" value="${dpPrCm.education}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 最高学位</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="degree" value="${dpPrCm.degree}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 毕业学校</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="school" value="${dpPrCm.school}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 所学专业</label>
			<div class="col-xs-6">
				<input class="form-control" type="text" name="major" value="${dpPrCm.major}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 备注</label>
			<div class="col-xs-6">
				<textarea class="form-control" rows="3" name="remark">${dpPrCm.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty dpPrCm?'确定':'添加'}</button>
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
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>