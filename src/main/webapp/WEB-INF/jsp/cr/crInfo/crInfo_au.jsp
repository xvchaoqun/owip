<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cr/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${crInfo!=null?'编辑':'添加'}招聘信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crInfo_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${crInfo.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 年度</label>
				<div class="col-xs-6">
                        <div class="input-group date"
                             data-date-format="yyyy" data-date-min-view-mode="2">
                            <input required class="form-control" placeholder="请选择" name="year"
                                   type="text" value="${empty crInfo?_thisYear:crInfo.year}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 表头名称</label>
				<div class="col-xs-8">
					<input required class="form-control" type="text" name="name" value="${crInfo.name}"/>
					<span class="help-block">导出的报名表命名：${_sysConfig.schoolName}{表头名称}报名表</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 添加日期</label>
				<div class="col-xs-6">
					<div class="input-group date" data-date-format="yyyy.mm.dd">
						<input required class="form-control" autocomplete="off" name="addDate" type="text"
							   value="${empty crInfo.addDate?_today_dot:(cm:formatDate(crInfo.addDate,'yyyy.MM.dd'))}"/>
						<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 招聘通知</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_notice"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 报名截止时间</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input class="form-control datetime-picker" required type="text" name="endTime"
							   value="${cm:formatDate(crInfo.endTime, "yyyy-MM-dd HH:mm")}">
						<span class="input-group-addon">
							<i class="fa fa-calendar bigger-110"></i>
						</span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 状态</label>
				<div class="col-xs-6">
					<select name="status" data-rel="select2"
							data-width="273"
							data-placeholder="请选择">
						<option></option>
						<c:forEach items="${CR_INFO_STATUS_MAP}" var="entity">
							<option value="${entity.key}">${entity.value}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=status]").val('${crInfo.status}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" name="remark">${crInfo.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty crInfo?'确定':'添加'}</button>
</div>
<script>
	$.fileInput($("#modalForm input[name=_notice]"),{
		no_file: '请上传pdf文件...',
		allowExt: ['pdf'],
		allowMime: ['application/pdf']
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
    $.register.date($('.input-group.date'));
    $.register.datetime($('.datetime-picker'));
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>