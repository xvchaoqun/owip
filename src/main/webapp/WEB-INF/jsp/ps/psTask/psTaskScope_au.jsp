<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${oaTaskAdmin!=null?'编辑':'添加'}年度任务发布范围</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/ps/psTaskScope_au"
		  autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${psTask.id}">
			<div class="form-group">
				<div class="col-xs-12">
                    <div id="tree"></div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty oaTaskAdmin?'确定':'添加'}</button>
</div>
<link href="${ctx}/extend/js/fancytree/skin-win8/ui.fancytree.css" rel="stylesheet"/>
<script src="${ctx}/extend/js/fancytree/jquery.fancytree-all.min.js"></script>
<script>

	$("#tree").fancytree({selectMode:3, checkbox:true, source:[${cm:toJSONObject(treeData)}]})
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');

            var _tree = $("#tree").fancytree("getTree");
            //console.log(_tree.getSelectedNodes())
            var psIds = $.map(_tree.getSelectedNodes(), function (node) {
            	if(!node.folder)
                	return node.key;
            });
            //console.log(psIds);
            $(form).ajaxSubmit({
				data:{psIds: psIds.join(",")},
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>