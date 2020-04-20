<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>参评人过滤</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="onlineId" value="onlineId"/>
        <div class="col-xs-6">
			<div id="inspectorTypesTree"></div>
		</div>
    </form>
</div>
<div class="modal-footer">
    <div class="pull-left" style="padding-left: 20px;">
        <input type="button" id="inspectorTypesSelectAll" class="btn btn-success btn-xs" value="身份全选"/>
        <input type="button" id="inspectorTypesDeselectAll" class="btn btn-danger btn-xs" value="身份全不选"/>
    </div>
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submit"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
</div>
<script>

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

    $("#submit").click(function () {

        //tree中取得节点的方法
        var inspectorTypeIds = $.map($("#inspectorTypesTree").dynatree("getSelectedNodes"), function(node){
            if(!node.data.isFolder)
                return node.data.key;
        });

        var typeIds = new Array();
        $.each(inspectorTypeIds,function (key, value) {
            typeIds.push(value)

        })
        var queryData= {
            "typeIds[]": typeIds
        }
        changeUrl(queryData);
        $("#modal").modal('hide');
        $("#jqGrid2").jqGrid("setGridParam", { postData: queryData}).trigger("reloadGrid");
        //console.log(typeIds)
        /*$.post("${ctx}/dr/drOnline/drOnlineResult",{"onlineId": ${onlineId}, "typeIds[]": typeIds},function(ret) {
            $("#modal").modal('hide');
            $("#jqGrid2").jqGrid("setGridParam", { postData: queryData }).trigger("reloadGrid");

        })*/
    })
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>