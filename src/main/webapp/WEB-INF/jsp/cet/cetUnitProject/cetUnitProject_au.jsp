<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cetUnitProject!=null?'编辑':'添加'}二级党委培训班</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUnitProject_au" autocomplete="off" disableautocomplete
          id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetUnitProject.id}">
        <input type="hidden" name="addType" value="${addType}">
        <div class="col-xs-12">
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>年度</label>
                    <div class="col-xs-7">
                        <div class="input-group">
                            <input required class="form-control date-picker" placeholder="请选择年份" name="year"
                                   type="text"
                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                   value="${empty cetUnitProject?_thisYear:cetUnitProject.year}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训项目名称</label>
                    <div class="col-xs-7">
					<textarea required class="form-control noEnter" rows="2"
                              name="projectName">${cetUnitProject.projectName}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训班类型</label>
                    <div class="col-xs-7">
                        <select required data-rel="select2" name="projectType" data-placeholder="请选择" data-width="223">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_cet_upper_train_type2"/>
                        </select>
                        <script type="text/javascript">
                            $("#modalForm select[name=projectType]").val(${cetUnitProject.projectType});
                        </script>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">报告名称</label>
                    <div class="col-xs-7">
                        <input class="form-control" name="reportName" value="${cetUnitProject.reportName}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">主讲人</label>
                    <div class="col-xs-7">
                        <input class="form-control" name="reporter" value="${cetUnitProject.reporter}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训班主办方</label>
                    <div class="col-xs-7">
                        <select required data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                 data-width="223" name="partyId" data-placeholder="请选择二级党委">
                            <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                        </select>
                        <script>
                            $.register.del_select($("#modalForm select[name=partyId]"))
                        </script>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>主办单位</label>
                    <div class="col-xs-7">
                        <select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                 data-width="223" data-placeholder="请选择单位">
                            <option value="${unit.id}"
                                    delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                        </select>
                        <script>
                            $.register.del_select($("#modalForm select[name=unitId]"))
                        </script>
                    </div>
                </div>
            </div>
            <div class="col-xs-6">
                <div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>培训开始时间</label>
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
				<label class="col-xs-4 control-label"><span class="star">*</span>培训结束时间</label>
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
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训学时</label>
                    <div class="col-xs-6">
                        <input required class="form-control period" type="text" name="period"
                               value="${cetUnitProject.period}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>培训地点</label>
                    <div class="col-xs-6">
                        <input required class="form-control" type="text" name="address"
                               value="${cetUnitProject.address}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-5 control-label">是否计入年度学习任务</label>
                    <div class="col-xs-3">
                        <input type="checkbox" class="big" name="isValid" ${cetUnitProject.isValid?"checked":""}/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">备注</label>
                    <div class="col-xs-6">
                        <textarea class="form-control limited" rows="2"
                                  name="remark">${cetUnitProject.remark}</textarea>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetUnitProject!=null}">确定</c:if><c:if
            test="${cetUnitProject==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
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