<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改报送信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/oa/oaGrid_au" autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${oaGrid.id}">
            <div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span> 所属年度</label>
				<div class="col-xs-6">
					<div class="input-group">
						<input required class="form-control date-picker" placeholder="请选择年份"
							   name="year"
							   type="text"
							   style="width: 100px;"
							   data-date-format="yyyy" data-date-min-view-mode="2"
							   value="${empty oaGridParty.year?_thisYear:oaGridParty.year}"/>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span> 表格名称</label>
				<div class="col-xs-6">
					<input required class="form-control" type="text" name="name" value="${oaGrid.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"><span class="star">*</span>  应完成时间</label>
				<div class="col-xs-6">
					<input required class="form-control datetime-picker"
						   name="deadline"
						   type="text"
						   value="${(cm:formatDate(oaGrid.deadline,'yyyy-MM-dd HH:mm'))}"/>
                    <span class="help-block">格式：yyyy-MM-dd HH:mm</span>
                </div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 填报说明</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" name="content" rows="3">${oaGrid.content}</textarea>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-4 control-label"> 联系方式</label>
				<div class="col-xs-6">
                        <input class="form-control" type="text" name="contact" value="${oaGrid.contact}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" name="remark" rows="3">${oaGrid.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty oaGrid?'确定':'添加'}</button>
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
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
	$.register.date($('.date-picker'));
    $.register.datetime($('.datetime-picker'));
</script>