<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scPassport!=null}">编辑</c:if><c:if test="${scPassport==null}">添加</c:if>上交证件信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scPassport_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${scPassport.id}">
        <input type="hidden" name="handId" value="${handId}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>证件名称</label>

			<div class="col-xs-6">
				<select required data-rel="select2" data-width="273"
						name="classId" data-placeholder="请选择证件名称">
					<option></option>
					<c:import url="/metaTypes?__code=mc_passport_type"/>
				</select>
				<script type="text/javascript">
					$("#modal form select[name=classId]").val(${scPassport.classId});
				</script>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">是否持有证件</label>
			<div class="col-xs-6">
				<input type="checkbox" class="big" name="isExist" ${(scPassport==null ||scPassport.isExist)?"checked":""}/>
			</div>
		</div>
		<div id="infoDiv">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>证件号码</label>

			<div class="col-xs-6">
				<input required class="form-control" type="text" name="code" value="${scPassport.code}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">证件首页</label>
			<div class="col-xs-6">
				<input class="form-control" type="file" name="_pic"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">发证机关</label>

			<div class="col-xs-6">
				<input class="form-control" type="text" name="authority"
					   value="${empty scPassport.id?"公安部出入境管理局":scPassport.authority}">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">发证日期</label>

			<div class="col-xs-6">
				<div class="input-group">
					<input class="form-control date-picker" name="issueDate" type="text"
						   data-date-format="yyyy-mm-dd" value="${cm:formatDate(scPassport.issueDate,'yyyy-MM-dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">有效期</label>

			<div class="col-xs-6">
				<div class="input-group">
					<input class="form-control date-picker" name="expiryDate" type="text"
						   data-date-format="yyyy-mm-dd" value="${cm:formatDate(scPassport.expiryDate,'yyyy-MM-dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">集中保管日期</label>

			<div class="col-xs-6">
				<div class="input-group">
					<input class="form-control date-picker" name="keepDate" type="text"
						   data-date-format="yyyy-mm-dd"
						   value="${param.op=='back'?_today:cm:formatDate(scPassport.keepDate,'yyyy-MM-dd')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label">存放保险柜</label>

			<div class="col-xs-6">
				<select data-rel="select2" name="safeBoxId" data-width="273" data-placeholder="保险柜">
					<option></option>
					<c:forEach items="${safeBoxMap}" var="safeBox">
						<option value="${safeBox.key}">${safeBox.value.code}</option>
					</c:forEach>
				</select>
				<script type="text/javascript">
					$("#modalForm select[name=safeBoxId]").val(${scPassport.safeBoxId});
				</script>
			</div>
		</div>
		</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
					<textarea class="form-control limited" name="remark">${scPassport.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scPassport!=null}">确定</c:if><c:if test="${scPassport==null}">添加</c:if></button>
</div>
<script>

	function isExistChange(){
		if(!$("input[name=isExist]").bootstrapSwitch("state")) {
			$("#modalForm input[name=code]").requireField(false);
			$("#infoDiv").hide();
		}else {
			$("#modalForm input[name=code]").requireField(true);
			$("#infoDiv").show();
		}
	}
	$('input[name=isExist]').on('switchChange.bootstrapSwitch', function(event, state) {
		isExistChange();
	});
	isExistChange();

	$.fileInput($('#modalForm input[name=_pic]'),{
		no_file: '请选择证件首页图片...',
		allowExt: ['jpg', 'jpeg', 'png', 'gif'],
		allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
	})

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                        //$("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
	$("#modalForm :checkbox").bootstrapSwitch();
	$('#modalForm [data-rel="select2"]').select2();
	$('[data-rel="tooltip"]').tooltip();
	$.register.date($('.date-picker'))
	$.register.user_select($('[data-rel="select2-ajax"]'));
</script>