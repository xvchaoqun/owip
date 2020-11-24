<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="constants.jsp" %>

<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>退回申请</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberApply_back" autocomplete="off" disableautocomplete id="modalForm" method="post">

        <input type="hidden" name="ids" value="${param.ids}">
        <div class="form-group">
            <label class="col-xs-3 control-label">退回申请记录</label>
            <div class="col-xs-8 label-text">
                ${fn:length(fn:split(param.ids,","))} 条
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">退回至状态</label>
            <div class="col-xs-8">
                <select name="stage" data-rel="select2">
                    <c:forEach var="_stage" items="${cm:inverseMap(OW_APPLY_STAGE_MAP)}">
                        <c:if test="${_stage.key<OW_APPLY_STAGE_POSITIVE
                        && _stage.key>=OW_APPLY_STAGE_DENY && _stage.key<=param.stage}">
                        <option value="${_stage.key}">${_stage.value}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <span class="help-block">注：将退回至所选阶段的初始状态</span>
            </div>
        </div>
        <c:if test="${param.stage>=OW_APPLY_STAGE_DRAW}">
            <shiro:hasPermission name="applySnRange:*">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>编码处理方式</label>
            <div class="col-xs-8">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input type="radio" checked name="applySnReuse" id="type1" value="1">
                        <label for="type1">
                            重新使用
                        </label>
                    </div>
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input type="radio" name="applySnReuse" id="type2" value="0">
                        <label for="type2">
                            作废
                        </label>
                    </div>
                </div>
                <span class="help-block">注：此选项仅对退回至“预备党员”之前的阶段，且已分配志愿书编码的申请有效</span>
            </div>
        </div>
            </shiro:hasPermission>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>退回原因</label>
            <div class="col-xs-6">
                <textarea required class="form-control limited" type="text" name="reason" rows="5"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <c:if test="${param.stage>OW_APPLY_STAGE_DRAW}">
        <div class="note">注：如果退回至“预备党员”之前的阶段，系统将删除该党员的相关信息，无法恢复，请谨慎操作！</div>
    </c:if>
 <button id="submitBtn" type="button" class="btn btn-primary"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定</button>
</div>

<script>
    $('[data-rel="select2"]').select2({allowClear:false});

    $.register.date($('.date-picker'));
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
             var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        //SysMsg.success('操作成功。', '成功', function () {
                            page_reload();
                        //});
                    }
                     $btn.button('reset');
                }
            });
        }
    });
</script>