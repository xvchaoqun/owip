<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cetExpert!=null}">编辑</c:if><c:if test="${cetExpert==null}">添加</c:if>专家信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetExpert_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cetExpert.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>专家类别</label>
            <div class="col-xs-8">
                <div class="input-group">
                    <c:forEach items="<%=CetConstants.CET_EXPERT_TYPE_MAP%>" var="entity">
                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                            <input required type="radio" name="type"
                                   id="type${entity.key}" value="${entity.key}">
                            <label for="type${entity.key}">
                                    ${entity.value}
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="form-group" id="inDiv" style="display: none">
            <label class="col-xs-3 control-label">选择专家</label>
            <div class="col-xs-6">
                <c:set var="sysUser" value="${cm:getUserById(cetExpert.userId)}"/>
                <select name="userId" data-rel="select2-ajax" data-width="272"
                        data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                        data-placeholder="请输入账号或姓名或教工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
            </div>
        </div>
        <div id="outDiv" style="display: none">
            <div class="form-group">
                <label class="col-xs-3 control-label">专家编号</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="code" value="${cetExpert.code}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">姓名</label>
                <div class="col-xs-6">
                    <input class="form-control" type="text" name="realname" value="${cetExpert.realname}">
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>所在单位</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="unit" value="${cetExpert.unit}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>职务和职称</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="post" value="${cetExpert.post}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>联系方式</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="contact" value="${cetExpert.contact}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
					<textarea class="form-control limited"
                              name="remark">${cetExpert.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        <i class="fa fa-check"></i> ${cetExpert==null?"添加":"确定"}</button>
</div>

<script>
    $("#modalForm input[name=type]").click(function () {
        if ($(this).val() == '<%=CetConstants.CET_EXPERT_TYPE_IN%>') {
            $("#inDiv").show();
            $("#outDiv").hide();
            $("#modalForm select[name=userId]").prop("disabled", false).attr("required", "required");
            $("#modalForm input[name=code]")
                .val('').removeAttr("required");
        } else {
            $("#inDiv").hide();
            $("#outDiv").show();
            $("#modalForm select[name=userId]").val(null).trigger("change")
                .prop("disabled", true).removeAttr("required");
              $("#modalForm input[name=code]")
                  .attr("required", "required");
        }
    });
    <c:if test="${not empty cetExpert.id}">
	$("#modalForm input[name=type][value=${cetExpert.type}]").click();
	</c:if>
    var $select = $.register.user_select($("#modalForm select[name=userId]"))
    $select.on("change",function(){
        if($(this).val()>0) {
            //console.log($(this).select2("data")[0])
            var unit = $(this).select2("data")[0]['unit'] || '';
            $('#modalForm input[name=unit]').val(unit);
        }
    });

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>