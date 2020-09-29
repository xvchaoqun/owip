<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${dpEva!=null?'编辑':'添加'}统战人员年度考核记录</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dp/dpEva_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${dpEva.id}">
		<input type="hidden" name="userId" value="${userId}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>年份</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input required autocomplete="off" disableautocomplete
						   class="form-control date-picker" placeholder="请选择年份" name="year"
						   type="text"
						   data-date-format="yyyy" data-date-min-view-mode="2" value="${dpEva.year}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>

			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>考核情况</label>
			<div class="col-xs-6">
				<select required data-rel="select2" data-width="273"
						name="type" data-placeholder="请选择">
					<option></option>
					<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_cadre_eva').id}"/>
				</select>
				<script type="text/javascript">
					$("#modal form select[name=type]").val(${dpEva.type});
				</script>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">时任职务</label>
			<div class="col-xs-6">
				<textarea class="form-control noEnter" name="title" rows="3">${empty dpEva?cadre.title:dpEva.title}</textarea>
			</div>
			<a href="javascript:;" onclick="$('#modalForm textarea[name=title]').val('').focus()">清空</a>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">备注</label>
			<div class="col-xs-6">
				<textarea class="form-control limited" name="remark">${dpEva.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty dpEva?'确定':'添加'}</button>
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
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>