<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dispatchUnit!=null}">编辑</c:if><c:if test="${dispatchUnit==null}">添加</c:if>单位发文</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchUnit_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${dispatchUnit.id}">
			<input type="hidden" name="dispatchId" value="${dispatch.id}">
         <div class="form-group">
          <label class="col-xs-3 control-label" id="typeNameTd">机构类型</label>
          <div class="col-xs-6 label-text">
            ${empty isUnit?'<span class="text-danger bolder">请先选择发文</span>':(isUnit?'内设机构':'组织机构')}
          </div>
        </div>

			<div class="form-group">
          <label class="col-xs-3 control-label"><span class="star">*</span>调整方式</label>
          <div class="col-xs-6">
            <select required data-rel="select2" name="type" data-width="150" data-placeholder="请选择">
              <option></option>
              <c:import url="/metaTypes?__code=mc_dispatch_unit_type"/>
            </select>
            <script type="text/javascript">
              $("#modalForm select[name=type]").val('${dispatchUnit.type}');
            </script>
          </div>
        </div>
        <c:set var="unit" value="${isUnit?unitMap.get(dispatchUnit.unitId):partyMap.get(dispatchUnit.unitId)}"/>
<c:set var="oldUnit" value="${isUnit?unitMap.get(dispatchUnit.oldUnitId):partyMap.get(dispatchUnit.oldUnitId)}"/>
<c:set var="unitIsDelete" value="${isUnit?unit.status==UNIT_STATUS_HISTORY:unit.isDeleted}"/>
<c:set var="oldUnitIsDelete" value="${isUnit?oldUnit.status==UNIT_STATUS_HISTORY:oldUnit.isDeleted}"/>
        <div class="form-group">
          <label class="col-xs-3 control-label"><span class="star">*</span>新成立机构名称</label>
          <div class="col-xs-6">
            <select required data-rel="select2-ajax" data-ajax-url="${ctx}/${isUnit?'unit_selects':'party_selects'}"
                    name="unitId" data-width="340" data-placeholder="请选择">
              <option value="${unit.id}" title="${unitIsDelete}">${unit.name}</option>
            </select>
               <script type="text/javascript">
              $("#modalForm select[name=unitId]").val('${dispatchUnit.unitId}');
            </script>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label"><span class="star">*</span>撤销机构名称</label>
          <div class="col-xs-6">
            <select required data-rel="select2-ajax" data-ajax-url="${ctx}/${isUnit?'unit_selects':'party_selects'}"
                    name="oldUnitId" data-width="340" data-placeholder="请选择">
              <option value="${oldUnit.id}" title="${oldUnitIsDelete}">${oldUnit.name}</option>
            </select>
              <script type="text/javascript">
              $("#modalForm select[name=oldUnitId]").val('${dispatchUnit.oldUnitId}');
            </script>
          </div>
        </div>

        <div class="form-group">
          <label class="col-xs-3 control-label">备注</label>
          <div class="col-xs-6">
            <textarea class="form-control limited" name="remark" rows="2">${dispatchUnit.remark}</textarea>
          </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${dispatchUnit!=null}">确定</c:if><c:if test="${dispatchUnit==null}">添加</c:if>"/>
</div>

<script>

    function typeChange(){

        $.register.del_select($('[data-rel="select2-ajax"]'));

        var $option = $('#modalForm select[name=type] option:selected');
        var $unit = $("#modalForm select[name=unitId]");
        var $oldUnit = $("#modalForm select[name=oldUnitId]");

        if($option.data('extra-attr')=='add'){
            $unit.prop("disabled", false).attr("required", "required");
            $.register.del_select($oldUnit,{theme: "default"});
            $oldUnit.val(null).trigger("change").prop("disabled", true).removeAttr("required");
        }else if($option.data('extra-attr')=='change'){
            $.register.del_select($('[data-rel="select2-ajax"]'));
            $unit.prop("disabled", false).attr("required", "required");
            $oldUnit.prop("disabled", false).attr("required", "required");
        }else if($option.data('extra-attr')=='delete'){
            $oldUnit.prop("disabled", false).attr("required", "required");
            $.register.del_select($unit,{theme: "default"});
            $unit.val(null).trigger("change").prop("disabled", true).removeAttr("required");
        }
	}
	$("#modalForm select[name=type]").change(function(){
		typeChange();
	});
	typeChange();

    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                         $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

</script>