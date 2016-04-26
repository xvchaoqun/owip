<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>打回申请</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberApply_back" id="modalForm" method="post">

        <input type="hidden" name="ids[]" value="${param['ids[]']}">
        <div class="form-group">
            <label class="col-xs-3 control-label">打回申请记录</label>
            <div class="col-xs-6 label-text">
                ${fn:length(fn:split(param['ids[]'],","))} 条
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">打回至状态</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <select name="stage" data-rel="select2">
                            <c:forEach var="_stage" items="${cm:inverseMap(APPLY_STAGE_MAP)}">
                                <c:if test="${_stage.key>=APPLY_STAGE_INIT && _stage.key<=param.stage}">
                                <option value="${_stage.key}">${_stage.value}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">打回原因</label>
            <div class="col-xs-6">
                <textarea required class="form-control limited" type="text" name="reason" rows="5"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $('[data-rel="select2"]').select2({allowClear:false});

    register_date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        SysMsg.success('操作成功。', '成功', function () {
                            page_reload();
                        });
                    }
                }
            });
        }
    });
</script>