<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>党员退休-审核</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/retireApply_verify" id="modalForm" method="post">
        <input type="hidden" name="userId" value="${retireApply.userId}">
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"> 退休后所在分党委</label>
            <div class="col-sm-7">
                <input class="form-control" type="text" disabled value="${party.name}">
            </div>
        </div>
        <c:if test="${branch!=null}">
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"> 退休后所在党支部</label>
            <div class="col-sm-7">
                <input class="form-control" type="text" disabled value="${branch.name}">
            </div>
        </div>
        </c:if>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"> 填报时间</label>
            <div class="col-sm-4">
                <input  class="form-control"type="text" disabled value="${cm:formatDate(retireApply.createTime,'yyyy-MM-dd HH:mm:ss')}">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="审核"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('审核成功。', '提示');
                    }
                }
            });
        }
    });
</script>