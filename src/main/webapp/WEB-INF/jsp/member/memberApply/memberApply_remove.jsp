<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="OW_APPLY_STAGE_DRAW" value="<%=OwConstants.OW_APPLY_STAGE_DRAW%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${param.isRemove==0?'撤销':''}移除申请</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberApply_remove" autocomplete="off" disableautocomplete id="modalForm" method="post">

        <input type="hidden" name="ids" value="${param.ids}">
        <input type="hidden" name="isRemove" value="${param.isRemove}">
        <div class="form-group">
            <label class="col-xs-3 control-label">${param.isRemove==0?'撤销':''}移除申请记录</label>
            <div class="col-xs-6 label-text">
                ${fn:length(fn:split(param.ids,","))} 条
            </div>
        </div>
        <c:if test="${param.stage>=OW_APPLY_STAGE_DRAW}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>编码处理方式</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input type="radio" checked name="applySnReuse" id="type1" value="1">
                        <label for="type1">
                            重新使用
                        </label>
                    </div>
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input type="radio" name="applySnReuse" id="type2" value="2">
                        <label for="type2">
                            作废
                        </label>
                    </div>
                </div>
                <span class="help-block">注：仅对已分配志愿书编码的申请有效</span>
            </div>
        </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> ${param.isRemove==0?'撤销':''}移除原因</label>
            <div class="col-xs-6">
                <textarea required class="form-control limited" type="text" name="reason" rows="5"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <div style="float: left;text-align: left;color: red;font-weight: bolder">
        <ul>
        <li>移除操作会保留用户的发展身份；</li>
        <li>移除后，将转移到【已移除的申请】列表中；</li>
        <li>如果需要撤销用户的发展身份，请使用退回功能（退回至申请阶段 → 不通过审批）。</li>
            </ul>
    </div>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $('[data-rel="select2"]').select2({allowClear:false});

    $.register.date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        //SysMsg.success('操作成功。', '成功', function () {
                            page_reload();
                        //});
                    }
                }
            });
        }
    });
</script>