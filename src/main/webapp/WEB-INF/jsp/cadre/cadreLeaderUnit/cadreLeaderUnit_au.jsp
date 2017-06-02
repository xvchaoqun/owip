<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreLeaderUnit!=null}">编辑</c:if><c:if test="${cadreLeaderUnit==null}">添加</c:if>校领导单位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreLeaderUnit_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreLeaderUnit.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">校领导</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="leaderId" value="${cadreLeaderUnit.leaderId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">所属单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unitId" value="${cadreLeaderUnit.unitId}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="typeId" value="${cadreLeaderUnit.typeId}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreLeaderUnit!=null}">确定</c:if><c:if test="${cadreLeaderUnit==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>