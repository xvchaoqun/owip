<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${oaTaskAdmin!=null?'编辑':'添加'}协同办公任务管理员</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/oa/oaTaskAdmin_au"
		  autocomplete="off" disableautocomplete id="modalForm" method="post">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 用户</label>
				<c:if test="${empty oaTaskAdmin}">
				<div class="col-xs-6">
					<select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                            data-width="275"
                            name="userId" data-placeholder="请输入账号或姓名或工号">
                        <option></option>
                    </select>
				</div>
				</c:if>
				<c:if test="${not empty oaTaskAdmin}">
				<div class="col-xs-6 label-text">
					<input type="hidden" name="userId" value="${oaTaskAdmin.userId}">
					${oaTaskAdmin.user.realname}(${oaTaskAdmin.user.code})
				</div>
				</c:if>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span> 工作类型</label>
				<div class="col-xs-6">
                    <div id="tree"></div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
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
            var types = $.map(_tree.getSelectedNodes(), function (node) {
            	if(!node.folder)
                	return node.key;
            });
            //console.log(types)
            $(form).ajaxSubmit({
				data:{types: types.join(",")},
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
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>