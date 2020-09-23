<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${oaGrid!=null?'编辑':'添加'}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/oa/oaGrid_au" autocomplete="off" disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${oaGrid.id}">
		<div class="row">
		<div class="col-xs-6">
			<div class="form-group">
				<label class="col-xs-5 control-label"><span class="star">*</span> 名称</label>
				<div class="col-xs-6">
					<input required class="form-control" type="text" name="name" value="${oaGrid.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label"><span class="star">*</span> 年度</label>
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
				<label class="col-xs-5 control-label"><span class="star">*</span> 类别</label>
				<div class="col-xs-6">
					<select required class="form-control" data-rel="select2" name="type" data-placeholder="请选择" data-width="224">
							<option></option>
						<option value="1">党统</option>
					</select>
					<script> $('select[name=type]').val(${oaGrid.type}) </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label"> 上传Excel文件</label>
				<div class="col-xs-6">
					<input class="form-control" type="file" name="_templateFilePath"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label"><span class="star">*</span> 表格行数</label>
				<div class="col-xs-6">
					<input required style="width: 78px;" class="digits form-control" type="text" name="row" value="${oaGrid.row}">
					<span style="color: red">格式：请输入阿拉伯数字</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label"><span class="star">*</span> 表格列数</label>
				<div class="col-xs-6">
					<input required style="width: 78px;" class="form-control" type="text" name="col" value="${oaGrid.col}">
					<span style="color: red">格式：请输入最后一列的大写字母</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label"><span class="star">*</span> 数据填报的左上角<br>单元格坐标</label>
				<div class="col-xs-6">
					<input required class="form-control" type="text" name="startPos" value="${oaGrid.startPos}">
					<span style="color: red">格式：D8：表示第D列第8行</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-5 control-label"><span class="star">*</span> 数据填报的右下角<br>单元格坐标</label>
				<div class="col-xs-6">
					<input required class="form-control" type="text" name="endPos" value="${oaGrid.endPos}">
				</div>
			</div>
		</div>
		<div class="col-xs-6">
			<div class="form-group">
				<label class="col-xs-4 control-label"> 只读单元格坐标</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" name="readonlyPos" rows="3">${oaGrid.readonlyPos}</textarea>
					<span style="color: red">即不需要填写数据的单元格坐标。格式：D9-D13;K8-K18;V9-V13;F8-F18;O8-O18;</span>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-4 control-label"> 具体事项</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" name="content" rows="3">${oaGrid.content}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label"> 截止时间</label>
				<div class="col-xs-6">
					<input class="form-control datetime-picker"
						   name="deadline"
						   type="text"
						   value="${(cm:formatDate(oaGrid.deadline,'yyyy-MM-dd HH:mm'))}"/>
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

	$.fileInput($('#modalForm input[type=file]'), {
		no_file: '请上传xlsx,xls文件...',
		allowExt: ['xlsx','xls']
	})

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