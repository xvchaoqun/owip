<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberAbroad!=null}">编辑</c:if><c:if test="${memberAbroad==null}">添加</c:if>党员出国境信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberAbroad_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberAbroad.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">党员</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="userId" value="${memberAbroad.userId}">
				</div>
			</div>

			<div class="form-group">
				<label class="col-xs-3 control-label">出国时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="abroadTime" value="${memberAbroad.abroadTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">出国缘由</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="reason" value="${memberAbroad.reason}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">预计归国时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="expectReturnTime" value="${memberAbroad.expectReturnTime}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">实际归国时间</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="actualReturnTime" value="${memberAbroad.actualReturnTime}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberAbroad!=null}">确定</c:if><c:if test="${memberAbroad==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>