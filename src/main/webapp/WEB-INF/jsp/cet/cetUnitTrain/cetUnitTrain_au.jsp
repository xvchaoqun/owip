<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cetUnitTrain!=null?'编辑':'添加'}二级党委培训记录</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetUnitTrain_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetUnitTrain.id}">
        <input type="hidden" name="projectId" value="${cetUnitProject.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>参训人</label>
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
							name="userId" data-placeholder="请输入账号或姓名或学工号"  data-width="270">
						<option value="${cetUnitTrain.user.id}">${cetUnitTrain.user.realname}-${cetUnitTrain.user.code}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 参训人员类型</label>
				<div class="col-xs-6">
					<select required data-rel="select2" name="traineeTypeId" data-placeholder="请选择" data-width="272">
                        <option></option>
                        <c:forEach items="${traineeTypeMap}" var="entity">
							<c:if test="${entity.value.code!='t_reserve' && entity.value.code!='t_candidate'}">
							<option value="${entity.value.id}">${entity.value.name}</option>
							</c:if>
						</c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=traineeTypeId]").val(${cetUnitTrain.traineeTypeId});
                    </script>
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">时任单位及职务</label>
				<div class="col-xs-6">
					<textarea class="form-control noEnter" rows="3"
							   name="title">${cetUnitTrain.title}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职务属性</label>
				<div class="col-xs-6">
					<select  data-rel="select2" name="postType" data-placeholder="请选择职务属性" data-width="272">
                        <option></option>
                        <jsp:include page="/metaTypes?__code=mc_post"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=postType]").val(${cetUnitTrain.postType});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>完成培训学时</label>
				<div class="col-xs-6">
                        <input required class="form-control period" type="text"
							   name="period" value="${empty cetUnitTrain?cetUnitProject.period:cetUnitTrain.period}">
				</div>
			</div>

			<div class="form-group">
					<label class="col-xs-3 control-label">培训总结</label>
					<div class="col-xs-6">
						<input class="form-control" type="file" name="_word"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-offset-3 col-xs-6">
						<input class="form-control" type="file" name="_pdf"/>
					</div>
				</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" rows="2"
							   name="remark">${cetUnitTrain.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${cetUnitTrain!=null}">确定</c:if><c:if test="${cetUnitTrain==null}">添加</c:if></button>
</div>
<script>
	$.fileInput($("#modalForm input[name=_word]"),{
		no_file: '请上传word文件...',
		allowExt: ['doc', 'docx'],
		allowMime: ['application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
	});
	$.fileInput($("#modalForm input[name=_pdf]"),{
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
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    var $select = $.register.user_select($('#modalForm select[name=userId]'));
    $select.on("change",function(){
        //console.log($(this).select2("data")[0])
        var title = $(this).select2("data")[0]['title']||'';
        var postType = $(this).select2("data")[0]['postType']||'';
        $('#modalForm textarea[name=title]').val(title);

        $("#modalForm select[name=postType]").val(postType).trigger("change");
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>