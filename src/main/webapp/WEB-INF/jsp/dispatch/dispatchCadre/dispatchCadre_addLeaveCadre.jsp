<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>添加离任干部（<span style="color: red;">非现任干部</span>）</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchCadre_addLeaveCadre"
          autocomplete="off" disableautocomplete id="modalForm" method="post">
        <div class="form-group selectNotCadre">
            <label class="col-xs-4 control-label"><span class="star">*</span>请选择系统账号</label>
            <div class="col-xs-6">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/notCadre_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option></option>
                </select>
                <span class="help-block">注：此处是从非干部库中选择</span>
            </div>
        </div>
        <div class="form-group needCreate">
            <label class="col-xs-4 control-label"><span class="star">*</span>是否需要生成系统账号</label>
            <div class="col-xs-6">
                <input type="checkbox" name="needCreate"/>
                <span class="help-block">注：系统账号不存在时请选“是”；如已选择系统账号则此选项将忽略。</span>
            </div>
        </div>
        <div class="form-group realname">
            <label class="col-xs-4 control-label"><span class="star">*</span>姓名</label>
            <div class="col-xs-6">
                <input type="text" name="realname"/>
            </div>
        </div>
        <c:if test="${status==CADRE_STATUS_MIDDLE||status==CADRE_STATUS_MIDDLE_LEAVE}">
            <c:if test="${_p_hasKjCadre}">
                <div class="form-group">
                    <label class="col-xs-4 control-label"><span class="star">*</span>干部类型</label>
                    <div class="col-xs-6">
                        <div class="input-group">
                            <c:forEach items="${CADRE_TYPE_MAP}" var="entity">
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="type" id="type${entity.key}"
                                        ${cadre.type==entity.key?"checked":""} value="${entity.key}">
                                    <label for="type${entity.key}">
                                            ${entity.value}
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">离任后所在单位及职务</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="title">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <div class="note">
        <ul>
            <li>此功能用于添加历史任免信息时，找不到干部的情况。</li>
            <li>如需添加现任干部，请勿在此添加，应该在“现任干部库”中添加。</li>
        </ul>
    </div>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中"> ${not empty cadre?"确定":"添加"}
    </button>
</div>

<script>
    var $selectCadre = $.register.user_select($('#modalForm select[name=userId]'));
    $selectCadre.on("change", function () {
        var userId = $.trim($(this).val());
        if (userId > 0) {
            $("#modalForm .needCreate").hide();
            $("#modalForm .realname").hide();
        } else {
            $("#modalForm .needCreate").show();
            $("#modalForm .realname").show();
        }
    });

    $("#modalForm input[name=needCreate]").bootstrapSwitch();
    $('#modalForm input[name=needCreate]').on('switchChange.bootstrapSwitch', function(event, state) {

        if($("#modalForm input[name=needCreate]").bootstrapSwitch("state")){

            $("#modalForm input[name=realname]").attr("required", "required");
            $("#modalForm .selectNotCadre").hide();
            $("#modalForm .realname").show();
        }else{
            $("#modalForm input[name=realname]").removeAttr("required");
            $("#modalForm .selectNotCadre").show();
            $("#modalForm .realname").hide();
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
                        var $cadreSelect = $("#cadreForm select[name=cadreId]");
                        $cadreSelect.html('<option value="{0}">{1}</option>'.format(ret.cadreId, ret.code));
                        $cadreSelect.trigger("change");
                        $('#cadreForm input[name=_name]').val(ret.realname);
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $("#modal :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
</script>