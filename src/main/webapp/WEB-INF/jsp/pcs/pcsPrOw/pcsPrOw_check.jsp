<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${_p_partyName}推荐情况审核</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pcs/pcsPrOw_check" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="partyIds" value="${param['partyIds']}">
        <input type="hidden" name="stage" value="${param.stage}">
        <c:set var="num" value='${fn:length(fn:split(param["partyIds"],","))}'/>
        <c:if test="${num==1}">
        <div class="form-group">
            <label class="col-xs-4 control-label">${_p_partyName}名称</label>
            <div class="col-xs-6 label-text">
                ${party.name}
            </div>
        </div>
        </c:if>
    <c:if test="${num>1}">
        <div class="form-group">
            <label class="col-xs-4 control-label">已选择${_p_partyName}数量</label>
            <div class="col-xs-6 label-text">
                 ${num}个
                     <span class="help-inline">（注：本操作只对已上报、未审核的${_p_partyName}进行审核，否则忽略）</span>
            </div>
        </div>
    </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">是否审核通过</label>
            <div class="col-xs-6">
                <input type="checkbox" class="big" name="status"  data-off-text="不通过" data-on-text="通过" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text"
                          name="remark" maxlength="200"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('textarea.limited').inputlimiter();
</script>