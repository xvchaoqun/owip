<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${party!=null}">编辑</c:if><c:if test="${party==null}">添加</c:if>基层党组织</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/party_au" id="modalForm" method="post">
		<div class="row">
			<div class="col-xs-6">
		<input type="hidden" name="id" value="${party.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">编号</label>
				<div class="col-xs-8">
                        <input required class="form-control" type="text" name="code" value="${party.code}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">名称</label>
				<div class="col-xs-8">
                        <input required class="form-control" type="text" name="name" value="${party.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">简称</label>
				<div class="col-xs-8">
                        <input class="form-control" type="text" name="shortName" value="${party.shortName}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">网址</label>
				<div class="col-xs-8">
                        <input class="form-control url" type="text" name="url" value="${party.url}">
				</div>
			</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">联系电话</label>
					<div class="col-xs-8">
						<input class="form-control" type="text" name="phone" value="${party.phone}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">传真</label>
					<div class="col-xs-8">
						<input class="form-control" type="text" name="fax" value="${party.fax}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">邮箱</label>
					<div class="col-xs-8">
						<input  class="form-control email" type="text" name="email" value="${party.email}">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">成立时间</label>
					<div class="col-xs-8">
						<div class="input-group">
							<input required class="form-control date-picker" name="_foundTime" type="text"
								   data-date-format="yyyy-mm-dd" value="${cm:formatDate(party.foundTime,'yyyy-MM-dd')}" />
							<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="col-xs-3 control-label">所属单位</label>
					<div class="col-xs-8">
						<select required class="form-control" name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
							<option></option>
							<c:forEach items="${unitMap}" var="unit">
								<option value="${unit.key}">${unit.value.name}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=unitId]").val('${party.unitId}');
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">党总支类别</label>
					<div class="col-xs-8">
						<select required class="form-control" name="classId" data-rel="select2" data-placeholder="请选择分类">
							<option></option>
							<c:forEach items="${partyClassMap}" var="cls">
								<option value="${cls.key}">${cls.value.name}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=classId]").val('${party.classId}');
						</script>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">组织类型</label>
					<div class="col-xs-8">
						<select required data-rel="select2" name="typeId" data-placeholder="请选择组织类型">
							<option></option>
							<c:import url="/metaTypes?__code=mc_party_type"/>
						</select>
						<script>
							$("#modalForm select[name=typeId]").val('${party.typeId}');
						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">所在单位属性</label>
					<div class="col-xs-8">
						<select required class="form-control" name="unitTypeId" data-rel="select2" data-placeholder="请选择">
							<option></option>
							<c:forEach items="${partyUnitTypeMap}" var="unitType">
								<option value="${unitType.key}">${unitType.value.name}</option>
							</c:forEach>
						</select>
						<script>
							$("#modalForm select[name=unitTypeId]").val('${party.unitTypeId}');
						</script>
					</div>
					</div>

				<div class="form-group">
					<label class="col-xs-3 control-label">所在单位是否独立法人</label>
					<div class="col-xs-8">
							<input name="isSeparate" ${party.isSeparate?"checked":""} type="checkbox" />
					</div>
				</div>
			<div class="form-group enterprise">
				<label class="col-xs-3 control-label">是否大中型</label>
				<div class="col-xs-8">
						<input name="isEnterpriseBig" ${party.isEnterpriseBig?"checked":""}  type="checkbox" />
				</div>
			</div>
			<div class="form-group enterprise">
				<label class="col-xs-3 control-label">是否国有独资</label>
				<div class="col-xs-8">
						<input name="isEnterpriseNationalized" ${party.isEnterpriseNationalized?"checked":""} type="checkbox" />
				</div>
			</div>


				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${party!=null}">确定</c:if><c:if test="${party==null}">添加</c:if>"/>
</div>
<style>
	.enterprise{
		display: none;
	}
</style>
<script>

	jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
	jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();

	$("#modal :checkbox").bootstrapSwitch();
	register_date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
						$("#modal").modal("hide")
						//SysMsg.success('提交成功。', '成功',function(){
							$("#jqGrid").trigger("reloadGrid");
							$(".closeView").click();
						//});
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();

	$('#modalForm select[name=unitTypeId]').change(function(){
		if($(this).val()=='${cm:getMetaTypeByCode('mt_party_unit_type_enterprise').id}') {
			$(".enterprise").show();
		} else {
			$(".enterprise :checkbox").prop("checked",false);
			$(".enterprise").hide();
		}
	}).change();

    $('[data-rel="tooltip"]').tooltip();
</script>