<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row" style="width: 700px">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-check blue"></i> 出生时间认定（${verifyAge.cadre.realname} - ${verifyAge.cadre.title}）</h4>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <form class="form-horizontal" action="${ctx}/verifyAge_verify" autocomplete="off" disableautocomplete id="modalFormVerify" method="post">
                    <input type="hidden" name="id" value="${verifyAge.id}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label"><span class="star">*</span>认定类别</label>
                        <div class="col-xs-6">
                            <select required name="type" data-rel="select2" data-placeholder="请选择" data-width="270">
                                <option></option>
                                <c:forEach items="<%=VerifyConstants.VERIFY_AGE_TYPE_MAP%>" var="entity">
                                    <option value="${entity.key}">${entity.value}</option>
                                </c:forEach>
                            </select>
                            <script>
                                $("#modalFormVerify select[name=type]").val('${verifyAge.type}');
                            </script>
                        </div>
                    </div>
                        <fieldset>
                            <legend>档案中最早的材料</legend>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">材料名称</label>
                                <div class="col-xs-6">
                                    <input class="form-control" type="text" name="materialName" value="${verifyAge.materialName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">形成时间</label>
                                <div class="col-xs-6">
                                    <div class="input-group date" data-date-format="yyyy.mm.dd">
                                        <input class="form-control" name="materialTime" type="text"
                                                value="${cm:formatDate(verifyAge.materialTime,'yyyy.MM.dd')}"/>
                                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">记载的出生时间</label>
                                <div class="col-xs-6">
                                    <div class="input-group date" data-date-format="yyyy.mm.dd" >
                                        <input class="form-control" name="materialBirth" type="text"
                                               value="${cm:formatDate(verifyAge.materialBirth,'yyyy.MM.dd')}"/>
                                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    <fieldset>
                        <legend>档案中最新干部任免审批表</legend>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">形成时间</label>

                            <div class="col-xs-6">
                                <div class="input-group date" data-date-format="yyyy.mm.dd" >
                                    <input class="form-control" name="adTime" type="text"
                                           value="${cm:formatDate(verifyAge.adTime,'yyyy.MM.dd')}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">记载的出生时间</label>

                            <div class="col-xs-6">
                                    <div class="input-group date" data-date-format="yyyy.mm.dd" >
                                        <input class="form-control" name="adBirth" type="text"
                                               value="${cm:formatDate(verifyAge.adBirth,'yyyy.MM.dd')}"/>
                                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                        </div>
                    </fieldset>
                    <div class="form-group">
                        <label class="col-xs-4 control-label">身份证出生时间</label>

                        <div class="col-xs-6">
                                    <div class="input-group date" data-date-format="yyyy.mm.dd" >
                                        <input class="form-control" name="oldBirth" type="text"
                                               value="${cm:formatDate(verifyAge.oldBirth,'yyyy.MM.dd')}"/>
                                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                    </div>
                    <div class="form-group well" style="margin: 10px;">
                        <label class="col-xs-4 control-label" style="font-weight: bolder; font-size: large"><span class="star">*</span>组织认定出生时间</label>
                        <div class="col-xs-6">
                                    <div class="input-group date" data-date-format="yyyy.mm.dd" >
                                        <input class="form-control" name="verifyBirth" type="text"
                                               value="${cm:formatDate(verifyAge.verifyBirth,'yyyy.MM.dd')}"/>
                                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-4 control-label">备注</label>

                        <div class="col-xs-6">
                            <textarea class="form-control limited" type="text"
                                      name="remark" rows="5">${verifyAge.remark}</textarea>
                        </div>
                    </div>
                </form>
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button id="submitBtn" class="btn btn-info"
                                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
                                type="button">
                            <i class="ace-icon fa fa-check bigger-110"></i>
                            认定
                        </button>

                        &nbsp; &nbsp; &nbsp;
                        <button class="hideView btn btn-default" type="button">
                            <i class="ace-icon fa fa-undo bigger-110"></i>
                            返回
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    fieldset{
        border: 1px solid #c0c0c0 !important;
        margin-bottom: 20px;
    }
    legend{

        text-align: left;
        margin-left: 50px;
        width:auto!important;
        border-bottom:none!important;
    }
</style>

<script>
    $.register.date($('.input-group.date'))
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('textarea.limited').inputlimiter();
    $("#body-content-view #submitBtn").click(function(){$("#modalFormVerify").submit(); return false;});
    $("#modalFormVerify").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.hideView();
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalFormVerify :checkbox").bootstrapSwitch();
    $('#modalFormVerify [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>