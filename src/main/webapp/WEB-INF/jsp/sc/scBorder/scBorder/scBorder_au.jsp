<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${scBorder!=null?'编辑':'添加'}出入境备案</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scBorder_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scBorder.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>年度</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" placeholder="请选择年份" name="year"
                               type="text"
                               data-date-format="yyyy" data-date-min-view-mode="2" value="${scBorder.year}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>报备日期</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="recordDate"
                               type="text"
                               data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(scBorder.recordDate,'yyyy-MM-dd')}"/>
                        <span class="input-group-addon"> <i
                                class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">报备表(新增)</label>
				<div class="col-xs-6">
                        <input class="form-control" type="file" name="_addFile">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">报备表(变更)</label>
				<div class="col-xs-6">
                        <input class="form-control" type="file" name="_changeFile">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">报备表(撤销)</label>
				<div class="col-xs-6">
                        <input class="form-control" type="file" name="_deleteFile">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">报备表</label>
				<div class="col-xs-6">
                        <input class="form-control" type="file" name="_recordFile">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited"
                              name="remark">${scBorder.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scBorder!=null}">确定</c:if><c:if test="${scBorder==null}">添加</c:if></button>
</div>
<script>
    $.fileInput($("input[name=_addFile], input[name=_changeFile], input[name=_deleteFile]", "#modalForm"), {
        no_file: '请上传PDF文件 ...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
    $.fileInput($('#modalForm input[name=_recordFile]'));

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
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>