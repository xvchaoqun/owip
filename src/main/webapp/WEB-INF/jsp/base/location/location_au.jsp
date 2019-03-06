<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${location!=null}">编辑</c:if><c:if test="${location==null}">添加</c:if>省、市、地区</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/location_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${location.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>上级节点编码</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="text" disabled value="${location.parentCode}">
                    <input type="hidden" name="parentCode" value="${location.parentCode}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>编码</label>
                <div class="col-xs-6">
                    <input required class="form-control digits" type="text" name="code" value="${location.code}">
                </div>
            </div>

			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${location.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea  class="form-control"  name="remark" rows="5">${location.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${location!=null}">确定</c:if><c:if test="${location==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>