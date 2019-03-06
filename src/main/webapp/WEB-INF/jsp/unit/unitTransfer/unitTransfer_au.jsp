<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${unitTransfer!=null}">编辑</c:if><c:if test="${unitTransfer==null}">添加</c:if>单位变更</h3>
</div>
<div class="modal-body">
    <div class="bootbox-body" >
    <form class="form-horizontal" action="${ctx}/unitTransfer_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitTransfer.id}">
        <input type="hidden" name="unitId" value="${unitId}">
			<div class="form-group">
				<label class="col-xs-3 control-label">所属单位</label>
				<div class="col-xs-6">
                   <input type="text" disabled value="${unitMap.get(unitId).name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>文件主题</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="subject" value="${unitTransfer.subject}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">文件具体内容</label>
				<div class="col-xs-6">
                        <textarea class="form-control" name="content" rows="10">${unitTransfer.content}</textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>日期</label>
				<div class="col-xs-6">
                    <div  class="input-group">
                        <input required class="form-control date-picker" name="_pubTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(unitTransfer.pubTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
    </form>
    </div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${unitTransfer!=null}">确定</c:if><c:if test="${unitTransfer==null}">添加</c:if>"/>
</div>

<script>
    $.register.date($('.date-picker'));

    $("#modal form").validate({
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
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>