<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${branch!=null}">编辑</c:if><c:if test="${branch==null}">添加</c:if>党支部</h3>
</div>
<form class="form-horizontal" action="${ctx}/branch_au" id="modalForm" method="post">
<div class="modal-body">

        	<input type="hidden" name="id" value="${branch.id}">
		<div class="row">
			<div class="col-xs-8">
			<%--<div class="form-group">
				<label class="col-xs-5 control-label">编号</label>
				<div class="col-xs-7">
                        <input required class="form-control" type="text" name="code" value="${branch.code}">
				</div>
			</div>--%>
			<div class="form-group">
				<label class="col-xs-4 control-label">名称</label>
				<div class="col-xs-8">
                        <input required class="form-control" type="text" name="name" value="${branch.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">简称</label>
				<div class="col-xs-8">
                        <input class="form-control" type="text" name="shortName" value="${branch.shortName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">所属分党委</label>
				<div class="col-xs-8">
					<select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?auth=1&notDirect=1&notBranchAdmin=1"
							name="partyId" data-placeholder="请选择">
						<option value="${party.id}">${party.name}</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">类别</label>
				<div class="col-xs-8">
					<select required class="form-control" name="typeId" data-rel="select2" data-placeholder="请选择类别">
						<option></option>
						<c:forEach items="${typeMap}" var="type">
							<option value="${type.key}">${type.value.name}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=typeId]").val('${branch.typeId}');
					</script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">是否是教工党支部</label>
				<div class="col-xs-8">
					<label>
						<input name="isStaff" ${branch.isStaff?"checked":""}  type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">是否是专业教师党支部</label>
					<div class="col-xs-8">
						<label>
							<input name="isPrefessional" ${branch.isPrefessional?"checked":""}  type="checkbox" />
							<span class="lbl"></span>
						</label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">是否建立在团队</label>
					<div class="col-xs-8">
						<label>
							<input name="isBaseTeam" ${branch.isBaseTeam?"checked":""}  type="checkbox" />
							<span class="lbl"></span>
						</label>
					</div>
				</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">单位属性</label>
				<div class="col-xs-8">
					<select required class="form-control" name="unitTypeId" data-rel="select2" data-placeholder="请选择单位属性">
						<option></option>
						<c:forEach items="${branchUnitTypeMap}" var="unitType">
							<option value="${unitType.key}">${unitType.value.name}</option>
						</c:forEach>
					</select>
					<script>
						$("#modalForm select[name=unitTypeId]").val('${branch.unitTypeId}');
					</script>
				</div>
			</div>
				</div>
			<div class="col-xs-4">
				<div class="form-group">
					<label class="col-xs-5 control-label">联系电话</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="phone" value="${branch.phone}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">传真</label>
					<div class="col-xs-6">
						<input class="form-control" type="text" name="fax" value="${branch.fax}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">邮箱</label>
					<div class="col-xs-6">
						<input class="form-control email" type="text" name="email" value="${branch.email}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-5 control-label">成立时间</label>
					<div class="col-xs-6">
						<div class="input-group">
							<input required class="form-control date-picker" name="_foundTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(branch.foundTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>

			<div class="form-group enterprise">
				<label class="col-xs-5 control-label">是否为大中型</label>
				<div class="col-xs-6">
					<label>
						<input name="isEnterpriseBig" ${branch.isEnterpriseBig?"checked":""}  type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group enterprise">
				<label class="col-xs-5 control-label">是否国有独资</label>
				<div class="col-xs-6">
					<label>
						<input name="isEnterpriseNationalized" ${branch.isEnterpriseNationalized?"checked":""}  type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>
			<div class="form-group enterprise">
				<label class="col-xs-5 control-label">是否联合党支部</label>
				<div class="col-xs-6">
					<label>
						<input name="isUnion" ${branch.isUnion?"checked":""}  type="checkbox" />
						<span class="lbl"></span>
					</label>
				</div>
			</div>

				</div>
			</div>

</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
	<input type="reset" class="btn btn-default"  value="重置">
    <input type="submit" class="btn btn-primary" value="<c:if test="${branch!=null}">确定</c:if><c:if test="${branch==null}">添加</c:if>"/>
</div>
</form>
<style>
	.enterprise{
		display: none;
	}
</style>
<script>
	$("#modal :checkbox").bootstrapSwitch();
	register_date($('.date-picker'), {endDate:'${today}'});
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						$("#modal").modal("hide")
						//SysMsg.success('提交成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
							$.hashchange();
						//});
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2({width:300});

	$('#modalForm select[name=unitTypeId]').change(function(){

		if($(this).val()=='${cm:getMetaTypeByCode('mt_branch_unit_type_enterprise').id}')
			$(".enterprise").show();
		else {
			$(".enterprise :checkbox").prop("checked",false);
			$(".enterprise").hide();
		}
	}).change();

    $('[data-rel="tooltip"]').tooltip();
	register_party_select($('#modalForm select[name=partyId]'));
</script>