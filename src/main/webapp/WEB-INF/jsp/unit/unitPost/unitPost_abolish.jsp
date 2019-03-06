<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>撤销岗位</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitPost_abolish" id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitPost.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>撤销日期</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 150px;">
                        <input  class="form-control date-picker required" name="abolishDate"
                               type="text" data-date-format="yyyy-mm-dd"
                               value="${cm:formatDate(empty unitPost.abolishDate?now:unitPost.abolishDate, "yyyy-MM-dd")}"/>
                        <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                    </div>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>
<script>
    $("#modal form").validate({
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
    $.register.date($('.date-picker'));
</script>