<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${trainEvaNorm!=null}">编辑</c:if><c:if test="${trainEvaNorm==null}">添加</c:if>评估指标</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/trainEvaNorm_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${trainEvaNorm.id}">
        <input type="hidden" name="evaTableId" value="${trainEvaTable.id}">
        <input type="hidden" name="fid" value="${fid}">

        <div class="form-group">
				<label class="col-xs-3 control-label">评估表</label>
				<div class="col-xs-6 label-text">
                    ${trainEvaTable.name}
				</div>
			</div>
            <c:if test="${not empty topTrainEvaNorm}">
                <div class="form-group">
                    <label class="col-xs-3 control-label">评估内容</label>
                    <div class="col-xs-6 label-text">
                            ${topTrainEvaNorm.name}
                    </div>
                </div>
            </c:if>
			<div class="form-group">
				<label class="col-xs-3 control-label">${empty fid?"评估内容":"评估指标"}</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${trainEvaNorm.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                    <textarea class="form-control limited" type="text" name="remark" >${trainEvaNorm.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${trainEvaNorm!=null}">确定</c:if><c:if test="${trainEvaNorm==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        currentExpandRows = [];
                        $("#modal").modal("hide");
                        <c:if test="${empty param.fid}">
                        currentExpandRows = [];
                        </c:if>
                        <c:if test="${not empty param.fid}">
                        currentExpandRows.push(${param.fid});
                        </c:if>

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