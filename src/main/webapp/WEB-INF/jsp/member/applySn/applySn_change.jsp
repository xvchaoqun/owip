<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>换领志愿书</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/applySn_change" autocomplete="off"
          disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${param.id}">
        <div class="form-group">
            <label class="col-xs-4 control-label">原志愿书编码</label>
            <div class="col-xs-7 label-text">
                ${applySn.displaySn}
                <span class="help-block">注：原志愿书编码将作废，不可再使用</span>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-4 control-label">选择分配方式</label>
            <div class="col-xs-7">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio" name="assignType" id="type1" value="1">
                        <label for="type1">
                            系统自动分配
                        </label>
                    </div>
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio" name="assignType" id="type2" value="2">
                        <label for="type2">
                            指定编码
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="manualAssign form-group" style="display: none">
            <label class="col-xs-4 control-label">选择志愿书编码</label>
            <div class="col-xs-7 label-text">
                <select data-rel="select2-ajax" data-ajax--url="${ctx}/applySn_selects"
                        id="newSnId" data-placeholder="请选择">
                    <option></option>
                </select>
            </div>
        </div>
        <div class="autoAssign form-group" style="display: none">
            <label class="col-xs-4 control-label">新志愿书编码</label>
            <div class="col-xs-7 label-text">
                ${empty newApplySn?"无可用编码":newApplySn.displaySn}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">原编码处理方式</label>
            <div class="col-xs-7">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio" checked name="opType" id="opType1" value="1">
                        <label for="opType1">
                            作废
                        </label>
                    </div>
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio" name="opType" id="opType2" value="2">
                        <label for="opType2">
                            重新使用
                        </label>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <button id="submitBtn" type="button" ${empty newApplySn?'disabled':''}
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