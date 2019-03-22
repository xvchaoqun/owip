<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cetUnitProject!=null?'编辑':'添加'}二级单位培训班</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUnitProject_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetUnitProject.id}">
        <input type="hidden" name="addType" value="${addType}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>年度</label>
				<div class="col-xs-6">
					<div class="input-group">
                                <input required class="form-control date-picker" placeholder="请选择年份" name="year"
                                       type="text"
                                       data-date-format="yyyy" data-date-min-view-mode="2" value="${empty cetUnitProject?_thisYear:cetUnitProject.year}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>培训班主办方</label>
				<div class="col-xs-6">
					<select required
								data-rel="select2" data-width="272" name="unitId" data-placeholder="请选择单位">
							<option></option>
							<c:forEach var="unit" items="${upperUnits}">
								<option value="${unit.id}">${unit.name}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=unitId]").val('${cetUnitProject.unitId}')
						</script>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>培训开始时间</label>
				<div class="col-xs-6">
                        <div class="input-group">
							<input required class="form-control date-picker" name="startDate"
								   type="text" autocomplete="off" disableautocomplete
								   data-date-format="yyyy-mm-dd"
								   value="${cm:formatDate(cetUnitProject.startDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
						</div>
				</div>
			</div>
		<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>培训结束时间</label>
				<div class="col-xs-6">
                        <div class="input-group">
							<input required class="form-control date-picker" name="endDate"
								   type="text" autocomplete="off" disableautocomplete
								   data-date-format="yyyy-mm-dd"
								   value="${cm:formatDate(cetUnitProject.endDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
													class="fa fa-calendar bigger-110"></i></span>
						</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>培训班名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="projectName" value="${cetUnitProject.projectName}">
				</div>
			</div>
		<div class="form-group">
					<label class="col-xs-3 control-label"><span class="star">*</span>培训班类型</label>
					<div class="col-xs-6">
						<select required data-rel="select2" name="projectType" data-placeholder="请选择" data-width="272">
							<option></option>
							<c:import url="/metaTypes?__code=mc_cet_upper_train_type2"/>
						</select>
						<script type="text/javascript">
							$("#modalForm select[name=projectType]").val(${cetUnitProject.projectType});
						</script>
					</div>
				</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>所属专项培训</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="specialType" data-placeholder="请选择" data-width="272">
							<option></option>
							<c:import url="/metaTypes?__code=mc_cet_upper_train_special2"/>
							<option value="0">无</option>
						</select>
						<script type="text/javascript">
							$("#modalForm select[name=specialType]").val(${cetUnitProject.specialType});
						</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>培训学时</label>
				<div class="col-xs-6">
                        <input required class="form-control period" type="text" name="period" value="${cetUnitProject.period}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>培训地点</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="address" value="${cetUnitProject.address}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否计入年度学习任务</label>
				<div class="col-xs-6">
					<input type="checkbox" class="big" name="isValid" ${cetUnitProject.isValid?"checked":""}/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" rows="2"
							   name="remark">${cetUnitProject.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetUnitProject!=null}">确定</c:if><c:if test="${cetUnitProject==null}">添加</c:if></button>
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
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>