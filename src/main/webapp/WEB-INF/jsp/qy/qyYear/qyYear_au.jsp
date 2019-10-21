<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${qyYear!=null?'编辑':'添加'}七一表彰年度</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/qyYear_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${qyYear.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 年度</label>
				<div class="col-xs-6">
					<input required class="date-picker"
						   name="year" type="text"
						   data-date-start-view="2"
						   data-date-min-view-mode="2"
						   data-date-max-view-mode="2"
						   data-date-format="yyyy"
						   style="width: 100px"
						   value="${qyYear.year}"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 备注</label>
				<div class="col-xs-6">
					<textarea class="form-control" type="text" name="remark" value="${qyYear.remark}">${qyYear.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty qyYear?'确定':'添加'}</button>
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
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>