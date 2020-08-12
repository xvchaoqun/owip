<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>修改往月应交金额</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmd/pmdMember_changeDuePay" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="ids" value="${param.ids}">
        <c:set var="num" value='${fn:length(fn:split(param.ids,","))}'/>
        <c:if test="${num==1}">
        <div class="form-group">
            <label class="col-xs-4 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${pmdMember.user.realname}
            </div>
        </div>
        </c:if>
        <c:if test="${num>1}">
        <div class="form-group">
            <label class="col-xs-4 control-label">已选择党员数量</label>
            <div class="col-xs-6 label-text">
                 ${num}人
            </div>
        </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">额度（单位：人民币）</label>
            <div class="col-xs-6">
                <input type="text" name="amount" class="form-control float" data-rule-min="0.01" maxlength="10"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>
            <div class="col-xs-6">
            <textarea class="form-control limited" type="text"
                      name="remark" maxlength="100"></textarea>
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
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('textarea.limited').inputlimiter();
</script>