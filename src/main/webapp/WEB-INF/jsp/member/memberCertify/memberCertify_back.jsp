<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_CERTIFY_STATUS_MAP" value="<%=MemberConstants.MEMBER_CERTIFY_STATUS_MAP%>"/>
<c:set var="MEMBER_CERTIFY_STATUS_BACK" value="<%=MemberConstants.MEMBER_CERTIFY_STATUS_BACK%>"/>

<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>介绍信退回</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/member/memberCertify_back" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="ids" value="${param.ids}">
        <div class="form-group">
            <label class="col-xs-3 control-label">退回记录</label>
            <div class="col-xs-6 label-text">
                ${fn:length(fn:split(param.ids,","))} 条
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">退回至状态</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <select required name="status" data-rel="select2">
                            <c:forEach var="_status" items="${cm:inverseMap(MEMBER_CERTIFY_STATUS_MAP)}">
                                <c:if test="${_status.key>=MEMBER_CERTIFY_STATUS_BACK && _status.key<status}">
                                    <option value="${_status.key}">${_status.value}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>退回原因</label>
            <div class="col-xs-6">
                <textarea required class="form-control limited" type="text" name="reason" rows="5"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">

    <input id="submitBtn" type="button" class="btn btn-primary" value="确定"/>
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
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>