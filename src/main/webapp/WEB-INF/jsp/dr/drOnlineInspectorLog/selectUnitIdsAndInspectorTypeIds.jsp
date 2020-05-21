<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="onlineId" value="${onlineId}"></c:set>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>列表生成第一步：选择单位和参评人身份类型</h3>
</div>
<div class="modal-body">
  <form class="form-horizontal" action="${ctx}/dr/selectUnitIdsAndInspectorTypeIds" id="modalFrom" method="post">
    <div class="col-xs-6">
      <div id="unitsTree" style="height: 400px;">
        <div class="block-loading"/>
      </div>
    </div>
    <div class="col-xs-6">
      <div id="inspectorTypesTree" style="height: 400px;">
        <div class="block-loading"/>
      </div>
    </div>
  </form>
</div>
<div class="modal-footer">
  <div class="pull-left">
    <input type="button" id="unitObjsSelectAll" class="btn btn-success btn-xs" value="单位全选"/>
    <input type="button" id="unitObjsDeselectAll" class="btn btn-danger btn-xs" value="单位全不选"/>
  </div>
  <div class="pull-left" style="padding-left: 20px;">
    <input type="button" id="inspectorTypesSelectAll" class="btn btn-success btn-xs" value="身份全选"/>
    <input type="button" id="inspectorTypesDeselectAll" class="btn btn-danger btn-xs" value="身份全不选"/>
  </div>
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="确认"/></div>
<script>

  $.getJSON("${ctx}/dr/selectUnits_tree",{},function(data){
    var treeData = data.tree;

    $("#unitsTree").dynatree({
      checkbox: true,
      selectMode: 3,
      children: treeData,
      onSelect: function(select, node) {

        node.expand(node.data.isFolder && node.isSelected());
      },
      cookieId: "dynatree-Cb3",
      idPrefix: "dynatree-Cb3-"
    });
  });

  $("#unitObjsSelectAll").click(function(){
    $("#unitsTree").dynatree("getRoot").visit(function(node){
      node.select(true);
    });
    return false;
  });
  $("#unitObjsDeselectAll").click(function(){
    $("#unitsTree").dynatree("getRoot").visit(function(node){
      node.select(false);
    });
    return false;
  });


  $.getJSON("${ctx}/dr/selectedInspectorTypes_tree",{},function(data){
    var treeData = data.tree;

    $("#inspectorTypesTree").dynatree({
      checkbox: true,
      selectMode: 3,
      children: treeData,
      onSelect: function(select, node) {

        node.expand(node.data.isFolder && node.isSelected());
      },
      cookieId: "dynatree-Cb3",
      idPrefix: "dynatree-Cb3-"
    });
  });

  $("#inspectorTypesSelectAll").click(function(){
    $("#inspectorTypesTree").dynatree("getRoot").visit(function(node){
      node.select(true);
    });
    return false;
  });
  $("#inspectorTypesDeselectAll").click(function(){
    $("#inspectorTypesTree").dynatree("getRoot").visit(function(node){
      node.select(false);
    });
    return false;
  });

  $("#modal input[type=submit]").click(function(){$("#modal form").submit();return false;});
  $("#modal form").validate({
    submitHandler: function (form) {
      var onlineId = ${onlineId};
      var unitIds = $.map($("#unitsTree").dynatree("getSelectedNodes"), function(node){
        if(!node.data.isFolder)
          return node.data.key;
      });
      var inspectorTypeIds = $.map($("#inspectorTypesTree").dynatree("getSelectedNodes"), function(node){
        if(!node.data.isFolder)
          return node.data.key;
      });
      if(inspectorTypeIds.length==0){
        toastr.warning("请选择参评人身份类型");
        return;
      }
      open_list_gen(onlineId, unitIds, inspectorTypeIds);
      $("#modal").modal('hide');
    }
  });
</script>