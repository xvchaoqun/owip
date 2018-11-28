<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=DispatchConstants.DISPATCH_CATEGORY_UNIT%>" var="DISPATCH_CATEGORY_UNIT" />
<c:set value="<%=DispatchConstants.DISPATCH_CATEGORY_PARTY%>" var="DISPATCH_CATEGORY_PARTY"/>
<c:set var="unit" value="${isUnit?unitMap.get(dispatchUnit.unitId):partyMap.get(dispatchUnit.unitId)}"/>
<c:set var="oldUnit" value="${isUnit?unitMap.get(dispatchUnit.oldUnitId):partyMap.get(dispatchUnit.oldUnitId)}"/>
<div class="widget-box">
  <div class="widget-header">
    <h4 class="widget-title">
      修改机构调整信息
    </h4>
  </div>
  <div class="widget-body">
    <div class="widget-main">
  <form class="form-horizontal" action="${ctx}/dispatchUnit_au" id="cadreForm" method="post">

        <input type="hidden" name="id" value="${dispatchUnit.id}">
        <input type="hidden" name="dispatchId" value="${dispatch.id}">
        <div class="form-group">
          <label class="col-xs-3 control-label" id="typeNameTd">机构类型</label>
          <div class="col-xs-6 label-text">
            ${empty isUnit?'<span class="text-danger bolder">请先选择发文</span>':(isUnit?'内设机构':'组织机构')}
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">调整方式</label>
          <div class="col-xs-6">
            <select required data-rel="select2" name="type" data-width="150" data-placeholder="请选择">
              <option></option>
              <c:import url="/metaTypes?__code=mc_dispatch_unit_type"/>
            </select>
            <script type="text/javascript">
              $("#cadreForm select[name=type]").val('${dispatchUnit.type}');
            </script>
          </div>
        </div>
    <c:set var="unitIsDelete" value="${isUnit?unit.status==UNIT_STATUS_HISTORY:unit.isDeleted}"/>
<c:set var="oldUnitIsDelete" value="${isUnit?oldUnit.status==UNIT_STATUS_HISTORY:oldUnit.isDeleted}"/>
        <div class="form-group">
          <label class="col-xs-3 control-label">新成立机构名称</label>
          <div class="col-xs-6">
            <select required data-rel="select2-ajax" data-ajax-url="${ctx}/${isUnit?'unit_selects':'party_selects'}"
                    name="unitId" data-width="340" data-placeholder="请选择">
              <option value="${unit.id}" title="${unitIsDelete}">${unit.name}</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-xs-3 control-label">撤销机构名称</label>
          <div class="col-xs-6">
            <select required data-rel="select2-ajax" data-ajax-url="${ctx}/${isUnit?'unit_selects':'party_selects'}"
                    name="oldUnitId" data-width="340" data-placeholder="请选择">
              <option value="${oldUnit.id}" title="${oldUnitIsDelete}">${oldUnit.name}</option>
            </select>
          </div>
        </div>

        <div class="form-group">
          <label class="col-xs-3 control-label">备注</label>
          <div class="col-xs-6">
            <textarea class="form-control limited" name="remark" rows="2">${dispatchUnit.remark}</textarea>
          </div>
        </div>

    <div class="clearfix form-actions center">
        <button class="btn ${empty dispatchUnit?'btn-success':'btn-info'} btn-sm" type="submit">
          <i class="ace-icon fa ${empty dispatchUnit?"fa-plus":"fa-edit"} "></i>
          ${empty dispatchUnit?"添加":"修改"}
        </button>
        <c:if test="${not empty dispatchUnit}">
        &nbsp; &nbsp; &nbsp;
        <button type="button" class="btn btn-default btn-sm" onclick="_reload2()">
          <i class="ace-icon fa fa-undo"></i>
          返回添加
        </button>
        </c:if>
    </div>

  </form>
    </div>
  </div>
</div>

<div style="padding-top: 20px">
  <table class="table table-actived table-striped table-bordered table-center table-hover">
    <thead>
    <tr>
      <th style="width: 80px">机构类型</th>
      <th style="width: 80px">调整方式</th>
      <th>新成立机构名称</th>
      <th>撤销机构名称</th>
      <th style="width: 80px"></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${dispatchUnits}" var="dispatchUnit" varStatus="st">
      <c:set var="unit" value="${isUnit?unitMap.get(dispatchUnit.unitId):partyMap.get(dispatchUnit.unitId)}"/>
      <c:set var="oldUnit" value="${isUnit?unitMap.get(dispatchUnit.oldUnitId):partyMap.get(dispatchUnit.oldUnitId)}"/>
      <c:set var="unitIsDelete" value="${isUnit?unit.status==UNIT_STATUS_HISTORY:unit.isDeleted}"/>
<c:set var="oldUnitIsDelete" value="${isUnit?oldUnit.status==UNIT_STATUS_HISTORY:oldUnit.isDeleted}"/>
      <tr>
        <td nowrap>${isUnit?'内设机构':'组织机构'}</td>
        <td nowrap>${cm:getMetaType(dispatchUnit.type).name}</td>
        <td style="text-align: left"><span class="${unitIsDelete?'delete':''}">${unit.name}</span></td>
        <td style="text-align: left"><span class="${oldUnitIsDelete?'delete':''}">${oldUnit.name}</span></td>
        <td nowrap>
          <button type="button" class="btn btn-xs btn-primary" onclick="_update(${dispatchUnit.id})">
          <i class="fa fa-edit"></i> 修改</button>

            <button type="button" class="confirm btn btn-xs btn-danger"
                    data-callback="_reload2"
                    data-title="删除调整信息"
                    data-msg="确定删除该条调整信息"
                    data-url="${ctx}/dispatchUnit_del?id=${dispatchUnit.id}">
              <i class="fa fa-times"></i>
                删除</button>

        </td>
      </tr>
    </c:forEach>
    </tbody>
    </tbody>
  </table>

</div>
<script>

    function typeChange(){

        $.register.del_select($('[data-rel="select2-ajax"]'));

        var $option = $('#cadreForm select[name=type] option:selected');
        var $unit = $("#cadreForm select[name=unitId]");
        var $oldUnit = $("#cadreForm select[name=oldUnitId]");
           console.log($option.data('extra-attr'))
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
	$("#cadreForm select[name=type]").change(function(){
		typeChange();
	});
	typeChange();

  <c:if test="${empty dispatch}">
  $("select, input, button, textarea", "#cadreForm").prop("disabled", true);
  </c:if>
  $('textarea.limited').inputlimiter();
  $("#cadreForm button[type=submit]").click(function(){$("#cadreForm").submit();return false;});
  $("#cadreForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success:function(ret){
          if(ret.success){
            $("#dispatch-cadres-view").load("${ctx}/dispatch_units_admin?dispatchId=${param.dispatchId}");
            //SysMsg.success('操作成功。', '成功');
          }
        }
      });
    }
  });

  function _reload2(){
    $("#dispatch-cadres-view").load("${ctx}/dispatch_units_admin?dispatchId=${param.dispatchId}");
  }
  function _update(id){
    $("#dispatch-cadres-view").load("${ctx}/dispatch_units_admin?dispatchId=${param.dispatchId}&id="+ $.trim(id));
  }

  $('[data-rel="select2"]').select2();
  $('[data-rel="tooltip"]').tooltip();

</script>