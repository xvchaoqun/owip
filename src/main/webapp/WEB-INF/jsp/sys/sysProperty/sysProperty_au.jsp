<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="SYS_PROPERTY_TYPE_BOOL" value="${SystemConstants.SYS_PROPERTY_TYPE_BOOL}"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${sysProperty!=null?'编辑':'添加'}系统属性</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sysProperty_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${sysProperty.id}">

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${sysProperty.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 类型</label>
				<div class="col-xs-6">
					<select required class="form-control" data-rel="select2" data-width="180"
                                                                name="type" data-placeholder="请选择">
							<option></option>
						 <c:forEach items="<%=SystemConstants.SYS_PROPERTY_TYPE_MAP%>" var="_type">
							<option value="${_type.key}">${_type.value}</option>
						</c:forEach>
					 </select>
					<script>
						$("#modalForm select[name=type]").val('${sysProperty.type}')
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 代码</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${sysProperty.code}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">取值</label>
				<div class="col-xs-6" id="contentDiv">
					<textarea class="form-control limited noEnter" name="content">${sysProperty.content}</textarea>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">说明</label>
				<div class="col-xs-6">
					 <textarea class="form-control limited noEnter" name="remark">${sysProperty.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${sysProperty!=null}">确定</c:if><c:if test="${sysProperty==null}">添加</c:if></button>
</div>
<script type="text/template" id="content_tpl">
	{{if(type==<%=SystemConstants.SYS_PROPERTY_TYPE_STRING%>){}}
	<textarea class="form-control limited noEnter" name="content"></textarea>
	{{}}}
	{{if(type==<%=SystemConstants.SYS_PROPERTY_TYPE_INT%>){}}
	<input class="form-control num" type="text" name="content"/>
	{{}}}
	{{if(type==<%=SystemConstants.SYS_PROPERTY_TYPE_BOOL%>){}}
	<input class="form-control" type="checkbox" name="content">
	{{}}}
	{{if(type==<%=SystemConstants.SYS_PROPERTY_TYPE_DATE%>){}}
	<div class="input-group date" data-date-format="yyyy-mm-dd">
		<input required class="form-control date-picker" name="content" type="text"
				placeholder="yyyy-mm-dd"/>
		<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
	</div>
	{{}}}
	{{if(type==<%=SystemConstants.SYS_PROPERTY_TYPE_PIC%>){}}
	<input type="file" name="_file"/>
	{{}}}
</script>

<script>

	$("#modalForm select[name=type]").change(function () {
        var type = $(this).val();
        $("#contentDiv").html(_.template($("#content_tpl").html())({type: type}));

        if(type==<%=SystemConstants.SYS_PROPERTY_TYPE_STRING%>){

		}else if(type==<%=SystemConstants.SYS_PROPERTY_TYPE_INT%>){

		}else if(type==<%=SystemConstants.SYS_PROPERTY_TYPE_BOOL%>){

        	$('#modalForm input[name=content]').bootstrapSwitch();
		}else if(type==<%=SystemConstants.SYS_PROPERTY_TYPE_DATE%>){

        	$.register.date($('.input-group.date'));
		}else if(type==<%=SystemConstants.SYS_PROPERTY_TYPE_PIC%>){

        	$.fileInput($('#modalForm input[name=_file]'),{
				no_file: '请选择图片...',
				allowExt: ['jpg', 'jpeg', 'png', 'gif'],
				allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
			})
		}
    }).change();

	<c:if test="${sysProperty.type == SYS_PROPERTY_TYPE_BOOL}">
		$('#modalForm input[name=content]').bootstrapSwitch('state', ${sysProperty.content})
	</c:if>
	<c:if test="${sysProperty.type != SYS_PROPERTY_TYPE_BOOL}">
		$('#modalForm [name=content]').val('${sysProperty.content}')
	</c:if>

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
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>