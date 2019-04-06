<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<jsp:include page="menu.jsp"/>
<div style="width: 900px">
<div class="modal-header">
    <h3>${unitFunction!=null?'编辑':'添加'}单位职能</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitFunction_au" autocomplete="off" disableautocomplete id="funForm" method="post">
        <input type="hidden" name="id" value="${unitFunction.id}">
		<input type="hidden" name="unitId" value="${unitId}">

			<div class="form-group">
				<label class="col-xs-3 control-label">是否当前职能</label>
				<div class="col-xs-6">
					<input type="checkbox" name="isCurrent" ${(unitFunction.isCurrent)?"checked":""}/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职能确定时间</label>
				<div class="col-xs-6">
					<div class="input-group" style="width: 150px">
						<input required class="form-control date-picker"
							   name="confirmTime"
							   type="text"
							   data-date-format="yyyy.mm.dd"
							   value="${cm:formatDate(unitFunction.confirmTime,'yyyy.MM.dd')}"/>
							<span class="input-group-addon"> <i
									class="fa fa-calendar bigger-110"></i></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职能内容</label>
				<div class="col-xs-6">
					<textarea class="form-control" name="content" rows="8">${unitFunction.content}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">相关文件</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_file"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control noEnter" name="remark">${unitFunction.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer center">
    <button id="submitFunFormBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty unitFunction?'确定':'添加'}</button>
	<c:if test="${not empty unitFunction}">
    <a href="javascript:;" onclick="_reloadUnitFunction()" class="btn btn-default"><i class="fa fa-reply"></i> 返回</a>
	</c:if>
</div>
	</div>
<script>
    $("#submitFunFormBtn").click(function(){$("#funForm").submit();return false;});
    $("#funForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitFunFormBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        //$("#jqGrid").trigger("reloadGrid");
						_reloadUnitFunction()
                    }
                    $btn.button('reset');
                }
            });
        }
    });

    function _reloadUnitFunction(){
        $("#unitfunctionDiv").load("${ctx}/unitFunction?unitId=${unitId}&funId=${unitFunction.id}")
    }

    $.fileInput($('#funForm input[name=_file]'),{
        no_file:'请上传pdf文件',
        allowExt: ['pdf']
    })
    $("#funForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>