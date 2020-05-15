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
                <form class="form-horizontal" action="${ctx}/verify/verifyJoinPartyTime_verify" autocomplete="off" disableautocomplete id="modalForm" method="post">
                    <input type="hidden" name="id" value="${verifyTime.id}">
                        <fieldset>
                            <legend>《入党志愿书》</legend>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">形成时间</label>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                        <input class="form-control date-picker" name="_materialTime" type="text"
                                               data-date-format="yyyy.mm.dd" value="${cm:formatDate(verifyTime.materialTime,'yyyy.MM.dd')}"/>
                                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">记载的入党时间</label>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                        <input class="form-control date-picker" name="_materialJoinTime" type="text"
                                               data-date-min-view-mode="1" placeholder="yyyy.mm"
                                               data-date-format="yyyy.mm" value="${cm:formatDate(verifyTime.materialJoinTime,'yyyy.MM')}"/>
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
                                    <input class="form-control date-picker" name="_adTime" type="text"
                                           data-date-format="yyyy.mm.dd" value="${cm:formatDate(verifyTime.adTime,'yyyy.MM.dd')}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">记载的入党时间</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input class="form-control date-picker" name="_adJoinTime" type="text"
                                           data-date-min-view-mode="1" placeholder="yyyy.mm"
                                           data-date-format="yyyy.mm" value="${cm:formatDate(verifyTime.adJoinTime,'yyyy.MM')}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <div class="form-group">
                        <label class="col-xs-4 control-label">认定前的入党时间</label>

                        <div class="col-xs-6">
                            <div class="input-group">
                                <input class="form-control date-picker" name="_oldJoinTime" type="text"
                                       data-date-format="yyyy.mm.dd"
                                       value="${cm:formatDate(verifyTime.oldJoinTime,'yyyy.MM.dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group well" style="margin: 10px;">
                        <label class="col-xs-4 control-label" style="font-weight: bolder; font-size: large"><span class="star">*</span>组织认定入党时间</label>

                        <div class="col-xs-6">
                            <div class="input-group">
                                <input required class="form-control date-picker" name="_verifyJoinTime" type="text"
                                       data-date-min-view-mode="1" placeholder="yyyy.mm"
                                       data-date-format="yyyy.mm" value="${cm:formatDate(verifyTime.verifyJoinTime,'yyyy.MM')}"/>
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
                        <button class="btn btn-info" type="submit">
                            <i class="ace-icon fa fa-check bigger-110"></i>
                            认定
                        </button>

                        &nbsp; &nbsp; &nbsp;
                        <button class="hideView btn btn-default" type="button">
                            <i class="ace-icon fa fa-undo bigger-110"></i>
                            取消
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
    $("#body-content-view button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.hideView()
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>