<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>确定为发展对象</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/apply_candidate" id="modalForm" method="post">
        <input type="hidden" name="userId" value="${param.userId}">
			<div class="form-group">
				<label class="col-xs-5 control-label">确定为发展对象时间</label>
				<div class="col-xs-4">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_candidateTime" type="text"
                               data-date-format="yyyy-mm-dd"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-5 control-label">参加培训时间</label>
            <div class="col-xs-4">
                <div class="input-group">
                    <input required class="form-control date-picker" name="_trainTime" type="text"
                           data-date-format="yyyy-mm-dd"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    register_date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        SysMsg.success('操作成功。', '成功');
                        goto_next(parseInt("${param.type}"));
                    }
                }
            });
        }
    });
</script>