<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row" style="width: 700px">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-check blue"></i> 入党时间认定（${verifyTime.cadre.realname} - ${verifyTime.cadre.title}）</h4>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <form class="form-horizontal" action="${ctx}/verify/verifyGrowTime_verify" autocomplete="off" disableautocomplete id="modalFormVerify" method="post">
                    <input type="hidden" name="id" value="${verifyTime.id}">
                        <fieldset>
                            <legend>《入党志愿书》</legend>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">形成时间</label>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                        <input class="form-control date-picker" name="materialTime" type="text"
                                               data-date-format="yyyy.mm.dd" value="${cm:formatDate(verifyTime.materialTime,'yyyy.MM.dd')}"/>
                                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">记载的入党时间</label>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                        <input class="form-control date-picker" name="materialGrowTime" type="text"
                                               data-date-format="yyyy.mm.dd" value="${cm:formatDate(verifyTime.materialGrowTime,'yyyy.MM.dd')}"/>
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
                                <div class="input-group">
                                    <input class="form-control date-picker" name="adTime" type="text"
                                           data-date-format="yyyy.mm.dd" value="${cm:formatDate(verifyTime.adTime,'yyyy.MM.dd')}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">记载的入党时间</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input class="form-control date-picker" name="adGrowTime" type="text"
                                           data-date-format="yyyy.mm.dd" value="${cm:formatDate(verifyTime.adGrowTime,'yyyy.MM.dd')}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <div class="form-group">
                        <label class="col-xs-4 control-label">认定前的入党时间</label>

                        <div class="col-xs-6">
                            <div class="input-group">
                                <input class="form-control date-picker" name="oldGrowTime" type="text"
                                       data-date-format="yyyy.mm.dd" value="${cm:formatDate(verifyTime.oldGrowTime,'yyyy.MM.dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group well" style="margin: 10px;">
                        <label class="col-xs-4 control-label" style="font-weight: bolder; font-size: large"><span class="star">*</span>组织认定入党时间</label>

                        <div class="col-xs-6">
                            <div class="input-group">
                                <input required class="form-control date-picker" name="verifyGrowTime" type="text"
                                       data-date-format="yyyy.mm.dd" value="${cm:formatDate(verifyTime.verifyGrowTime,'yyyy.MM.dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-4 control-label">备注</label>

                        <div class="col-xs-6">
                            <textarea class="form-control limited" type="text"
                                      name="remark" rows="5">${verifyTime.remark}</textarea>
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
    $.register.date($('.date-picker'))
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('textarea.limited').inputlimiter();

    $("#body-content-view #submitBtn").click(function(){$("#modalFormVerify").submit(); return false;});
    $("#modalFormVerify").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalFormVerify :checkbox").bootstrapSwitch();
    $('#modalFormVerify [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
</script>