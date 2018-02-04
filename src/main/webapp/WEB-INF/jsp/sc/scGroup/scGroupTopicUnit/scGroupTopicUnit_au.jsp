<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scGroupTopicUnit!=null}">编辑</c:if><c:if test="${scGroupTopicUnit==null}">添加</c:if>干部小组会议题涉及单位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scGroupTopicUnit_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scGroupTopicUnit.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">议题</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="topicId" value="${scGroupTopicUnit.topicId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">涉及单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unitId" value="${scGroupTopicUnit.unitId}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scGroupTopicUnit!=null}">确定</c:if><c:if test="${scGroupTopicUnit==null}">添加</c:if></button>
</div>

<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>