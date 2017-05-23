<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>选择单位</h3>
</div>
<div class="modal-body">
  <form class="form-horizontal"  id="modalFrom" method="post">
    <div id="unitsTree"></div>

  </form>
</div>
<div class="modal-footer">
  <div class="pull-left">
    <input type="button" id="unitObjsSelectAll" class="btn btn-success btn-xs" value="单位全选"/>
    <input type="button" id="unitObjsDeselectAll" class="btn btn-danger btn-xs" value="单位全不选"/>
  </div>
  <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
  <input type="submit" class="btn btn-primary" value="确认"/></div>
<script>

  $.getJSON("${ctx}/selectUnits_tree",{},function(data){
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


  $("#modal input[type=submit]").click(function(){$("#modal form").submit();return false;});
  $("#modal form").validate({
    submitHandler: function (form) {

      var unitIds = $.map($("#unitsTree").dynatree("getSelectedNodes"), function(node){
        if(!node.data.isFolder)
          return node.data.key;
      });

      if(unitIds.length==0){
        toastr.warning("请选择单位");
        return;
      }
      $("#modal").modal("hide");
      _openView("${ctx}/cpcAllocationSetting?unitIds={0}".format(unitIds));
    }
  });
</script>