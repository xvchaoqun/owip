<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${psMember!=null?'编辑':'添加'}二级党校班子成员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/ps/psMember_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${psMember.id}">
		<input type="hidden" name="psId" value="${param.psId}">
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span> 党校职务</label>
			<div class="col-xs-6">
				<select class="col-xs-6" required name="type" data-width="270"
					data-rel="select2" data-placeholder="请选择">
					<option></option>
					<c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_party_school_position').id}"/>
				</select>
			</div>
			<script>
				$("#modalForm select[name=type]").val('${psMember.type}');
			</script>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"><span class="star">*</span>人员类型</label>
			<div class="col-xs-6">
				<div class="input-group">
					<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
						<input checked required type="radio" name="personnelType" id="status1" value="1">
						<label for="status1">干部</label>
					</div>
					<div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
						<input required type="radio" name="personnelType" id="status2" value="2">
						<label for="status2">党员</label>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group" id="personnelTypeDiv">
			<label class="col-xs-3 control-label"><span class="star">*</span> 姓名</label>
			<div class="col-xs-6">
				<select required name="userId"
						data-rel="select2-ajax"
						data-toggle="userId"
						data-ajax-url="${ctx}/cadre_selects?key=1"
						data-width="270"
						data-placeholder="请输入账号或姓名或学工号">
					<option value="${psMember.userId}">${sysUser.realname}-${sysUser.code}</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 任职起始时间</label>
			<div class="col-xs-6">
				<div class="input-group">
					<input class="form-control date-picker" name="_startDate" type="text"
						   data-date-format="yyyy.mm"
						   data-date-min-view-mode="1"
						   value="${empty psMember?'':cm:formatDate(psMember.startDate,'yyyy.MM')}"/>
					<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
				</div>
			</div>
		</div>
		<c:if test="${!empty psMember}">
			<div class="form-group">
				<label class="col-xs-3 control-label"> 所在单位及职务</label>
				<div class="col-xs-6">
					<input class="form-control" type="text" name="title" value="${psMember.title}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"> 联系方式</label>
				<div class="col-xs-6">
					<input class="form-control" type="text" name="mobile" value="${psMember.mobile}">
				</div>
			</div>
		</c:if>
		<div class="form-group">
			<label class="col-xs-3 control-label"> 备注</label>
			<div class="col-xs-6">
				<textarea class="form-control" name="remark">${psMember.remark}</textarea>
			</div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
		<i class="fa fa-check"></i>
		${not empty psMember?'确定':'添加'}</button>
</div>
<script>

	$("input[name=personnelType]").click(function(){
		$("#personnelTypeDiv select[name=userId]").val(null).trigger("change");
		$("#personnelTypeDiv select[name=userId]").data("ajax-url",
				($(this).val()==1)?"${ctx}/cadre_selects?key=1":"${ctx}/member_selects?");
		$.register.user_select($('[data-rel="select2-ajax"]'));
	});

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
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    $.register.date($('.date-picker'));
</script>