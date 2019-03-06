<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>添加报备干部</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scBorderItem_add" id="modalForm" method="post">
        <input type="hidden" name="borderId" value="${param.borderId}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>报备类别</label>
            <div class="col-xs-6">
                <c:forEach var="_type" items="<%=ScConstants.SC_BORDER_ITEM_TYPE_MAP%>">
                    <label class="label-text" style="padding-right: 15px;">
                        <input required name="type" type="radio" class="ace"
                               value="${_type.key}"/>
                        <span class="lbl"> ${_type.value}</span>
                    </label>
                </c:forEach>

            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">选择离任干部</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax"
                            data-ajax-url="${ctx}/cadre_selects?types=${CADRE_STATUS_MIDDLE_LEAVE},${CADRE_STATUS_LEADER_LEAVE}"
                            name="cadreId" data-placeholder="请输入账号或姓名或教工号"  data-width="270">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scBorderItem!=null}">确定</c:if><c:if test="${scBorderItem==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>