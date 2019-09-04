<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
	<c:set value="<%=CgConstants.CG_RULE_TYPE_MAP%>" var="CG_RULE_TYPE_MAP"/>
	<button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
	<h3>${cgRule.content!=null?'编辑':'添加'}${CG_RULE_TYPE_MAP.get(cgRule.type)}</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" action="${ctx}/cg/cgRule_content" autocomplete="off" disableautocomplete id="funForm" method="post">
		<input name="id" type="hidden" value="${cgRule.id}">
		<div class="form-group">
			<div class="col-xs-12">
				<textarea id="content">${cgRule.content}</textarea>
			</div>
		</div>
	</form>
</div>
<div class="modal-footer">
	<a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
	<button id="submitBtn"
			data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
			class="btn btn-primary"><i class="fa fa-check"></i> ${not empty cgRule?'保存':'添加'}</button>
</div>
<script>

	var ke = KindEditor.create('#content', {
		filterMode:true,
		htmlTags:{
			p : ['style']
		},
		cssPath:"${ctx}/css/ke.css",
		items: ["source", "|", "fullscreen", "|", 'preview'],
		height: '400px',
		width: '100%'
	});

    $("#submitBtn").click(function(){$("#funForm").submit();return false;});
    $("#funForm").validate({

        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({

				data: {content: ke.html()},
                success:function(ret){
                    if(ret.success){

                        $("#modal").modal('hide');
                        $("#jqGrid_current").trigger("reloadGrid");
                        $("#jqGrid_history").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });

</script>