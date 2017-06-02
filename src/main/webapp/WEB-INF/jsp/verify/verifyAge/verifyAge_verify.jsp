<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row" style="width: 700px">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="widget-title"><i class="fa fa-check blue"></i> 出生时间认定（${verifyAge.cadre.user.realname} - ${verifyAge.cadre.title}）</h4>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <form class="form-horizontal" action="${ctx}/verifyAge_verify" id="modalForm" method="post">
                    <input type="hidden" name="id" value="${verifyAge.id}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label">认定类别</label>
                        <div class="col-xs-6">
                            <select required name="type" data-rel="select2" data-placeholder="请选择" data-width="270">
                                <option></option>
                                <c:forEach items="${VERIFY_AGE_TYPE_MAP}" var="entity">
                                    <option value="${entity.key}">${entity.value}</option>
                                </c:forEach>
                            </select>
                            <script>
                                $("#modalForm select[name=type]").val('${verifyAge.type}');
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
                                    <div class="input-group">
                                        <input class="form-control date-picker" name="_materialTime" type="text"
                                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(verifyAge.materialTime,'yyyy-MM-dd')}"/>
                                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">记载的出生时间</label>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                        <input class="form-control date-picker" name="_materialBirth" type="text"
                                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(verifyAge.materialBirth,'yyyy-MM-dd')}"/>
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
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(verifyAge.adTime,'yyyy-MM-dd')}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">记载的出生时间</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input class="form-control date-picker" name="_adBirth" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(verifyAge.adBirth,'yyyy-MM-dd')}"/>
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <div class="form-group">
                        <label class="col-xs-4 control-label">身份证出生时间</label>
                        <div class="col-xs-6">
                            <div class="input-group">
                                <input class="form-control date-picker" name="_oldBirth" type="text"
                                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(verifyAge.oldBirth,'yyyy-MM-dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group well" style="margin: 10px;">
                        <label class="col-xs-4 control-label" style="font-weight: bolder; font-size: large">组织认定出生时间</label>

                        <div class="col-xs-6">
                            <div class="input-group">
                                <input required class="form-control date-picker" name="_verifyBirth" type="text"
                                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(verifyAge.verifyBirth,'yyyy-MM-dd')}"/>
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
                        <button class="btn btn-info" type="submit">
                            <i class="ace-icon fa fa-check bigger-110"></i>
                            认定
                        </button>

                        &nbsp; &nbsp; &nbsp;
                        <button class="closeView btn" type="button">
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
    register_date($('.date-picker'))
    register_user_select($('[data-rel="select2-ajax"]'));
    $('textarea.limited').inputlimiter();
    $("#item-content button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.hideView(1)
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>