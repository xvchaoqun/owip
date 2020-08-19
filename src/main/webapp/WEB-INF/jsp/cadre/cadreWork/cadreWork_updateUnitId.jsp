<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改所属单位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreWork_updateUnitId?cadreId=${cadre.id}" autocomplete="off" disableautocomplete id="modalForm" method="post">
            <input type="hidden" name="id" value="${cadreWork.id}">
			<div class="form-group">
				<label class="col-xs-4 control-label">姓名</label>
				<div class="col-xs-6 label-text">
                    ${cadre.realname}
				</div>
			</div>
            <div class="form-group">
                <label class="col-xs-4 control-label">工作单位及担任职务<br/>（或专技职务）</label>
                <div class="col-xs-6 label-text">
                    ${cadreWork.detail}
                </div>
            </div>
			<div class="form-group">
                <div class="col-xs-6">
                    <div id="unitsTree"></div>
                </div>
                <div class="col-xs-6">
                    <div id="historyUnitsTree"></div>
                    <div class="space-4"/>
                </div>
			</div>
        <span class="help-block"><span class="star">*</span> 留空则删除对应工作单位</span>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    var selectedUnitIds = '${cadreWork.unitIds}';

    $.getJSON("${ctx}/selectUnits_tree",{status:'${UNIT_STATUS_RUN}'},function(data){
        var treeData = data.tree;
        treeData.title="正在运转单位"
        $("#unitsTree").dynatree({
            checkbox: true,
            selectMode: 3,
            children: treeData,
            onSelect: function(select, node) {

                node.expand(node.data.isFolder && node.isSelected());
            },
            onPostInit:function(isReloading, isError){
                var $this = this;
                return ($.map(selectedUnitIds.split(","), function(unitId){
                    return $this.selectKey(unitId,true);
                })).join(",")
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });
    });
    $.getJSON("${ctx}/selectUnits_tree",{status:'${UNIT_STATUS_HISTORY}'},function(data){
        var treeData = data.tree;
        treeData.title="历史单位"
        $("#historyUnitsTree").dynatree({
            checkbox: true,
            selectMode: 3,
            children: treeData,
            onSelect: function(select, node) {

                node.expand(node.data.isFolder && node.isSelected());
            },
            onPostInit:function(isReloading, isError){
                var $this = this;
                return ($.map(selectedUnitIds.split(","), function(unitId){
                    return $this.selectKey(unitId,true);
                })).join(",")
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });
    });
    $("#modal form").validate({
        submitHandler: function (form) {

            var unitIds = $.map($("#unitsTree").dynatree("getSelectedNodes"), function (node) {
                if(!node.data.isFolder)
                    return node.data.key;
            });
            var historyUnitIds = $.map($("#historyUnitsTree").dynatree("getSelectedNodes"), function (node) {
                if(!node.data.isFolder)
                    return node.data.key;
            });
            console.log("unitIds="+unitIds)

            $(form).ajaxSubmit({
                data: {unitIds: unitIds.concat(historyUnitIds)},
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid_cadreWork").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $.register.ajax_select('#modalForm [data-rel="select2-ajax"]')
</script>