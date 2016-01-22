<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dispatchType!=null}">编辑</c:if><c:if test="${dispatchType==null}">添加</c:if>发文类型</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchType_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${dispatchType.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label">年份</label>
                <div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                               data-date-format="yyyy" data-date-min-view-mode="2" value="${dispatchType.year}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${dispatchType.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">发文属性</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="attr" value="${dispatchType.attr}">
				</div>
			</div>

    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${dispatchType!=null}">确定</c:if><c:if test="${dispatchType==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
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
    register_date($('.date-picker'));
</script>