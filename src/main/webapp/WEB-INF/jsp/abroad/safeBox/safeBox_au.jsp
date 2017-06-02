<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${safeBox!=null}">编辑</c:if><c:if test="${safeBox==null}">添加</c:if>保险柜</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/safeBox_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${safeBox.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">保险柜编号</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="code" value="${safeBox.code}">
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text" name="remark" rows="2" value="${safeBox.remark}"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${safeBox!=null}">确定</c:if><c:if test="${safeBox==null}">添加</c:if>"/>
</div>

<script>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        //SysMsg.success('操作成功。', '成功', function(){
                            $("#jqGrid").trigger("reloadGrid");
                        //});
                    }
                }
            });
        }
    });
    $('textarea.limited').inputlimiter();
</script>