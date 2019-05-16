<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>调换志愿书编码</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/applySn_exchange" autocomplete="off"
          disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <div class="form-group">
            <label class="col-xs-4 control-label">志愿书编码</label>
            <div class="col-xs-7 label-text">
                ${applySn.displaySn}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">调换志愿书编码</label>
            <div class="col-xs-7 label-text">
                <select data-rel="select2-ajax" data-ajax--url="${ctx}/applySn_selects?isUsed=1"
                        name="exchangeSnId" data-placeholder="请选择">
                    <option></option>
                </select>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <button id="submitBtn" type="button"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> 确定
    </button>
</div>

<script>
    $("#modalForm input[name=assignType]").click(function () {
        if ($(this).val() == 2) {
            $(".manualAssign").show();
            $(".autoAssign").hide();
            $("#modalForm .manualAssign select").prop("disabled", false).attr("required", "required");
        } else {
            $(".manualAssign").hide();
            $(".autoAssign").show();
            $("#modalForm .manualAssign select").val(null).trigger("change").prop("disabled", true).removeAttr("required");
        }
    });
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'), {endDate: "${_today}"});
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modal form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            var newSnId;
            var assignType = $("#modalForm input[name=assignType]:checked").val();
            if (assignType == 2) {
                newSnId = $("#newSnId").val();
            }else if(assignType==1){
                newSnId = '${newApplySn.id}';
            }

            $(form).ajaxSubmit({
                data:{newSnId: newSnId},
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal("hide");
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>