<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetTrainEvaRank!=null}">编辑</c:if><c:if test="${cetTrainEvaRank==null}">添加</c:if>评估等级</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainEvaRank_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetTrainEvaRank.id}">
        <input type="hidden" name="evaTableId" value="${cetTrainEvaTable.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属评估表</label>
                <div class="col-xs-6 label-text">
                    ${cetTrainEvaTable.name}
                </div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cetTrainEvaRank.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>得分</label>
				<div class="col-xs-6">
                        <input required class="form-control digits" type="text" name="score" value="${cetTrainEvaRank.score}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>得分显示内容</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="scoreShow" value="${cetTrainEvaRank.scoreShow}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" type="text" name="remark" >${cetTrainEvaRank.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cetTrainEvaRank!=null}">确定</c:if><c:if test="${cetTrainEvaRank==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>