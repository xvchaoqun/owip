<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreCompany!=null}">编辑</c:if><c:if test="${cadreCompany==null}">添加</c:if>干部企业兼职情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreCompany_au?toApply=${param.toApply}" autocomplete="off"
          disableautocomplete id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="id" value="${cadreCompany.id}">
        <input type="hidden" name="isFinished" value="${isFinished}">
        <div class="col-xs-12">
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>姓名</label>
                    <c:if test="${not empty cadre}">
                        <div class="col-xs-6 label-text">
                                ${cadre.realname}
                            <input type="hidden" name="cadreId" value="${cadre.id}">
                        </div>
                    </c:if>
                    <c:if test="${empty cadre}">
                        <div class="col-xs-6">
                            <select required data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects"
                                    name="cadreId" data-placeholder="请选择" data-width="225">
                                <option></option>
                            </select>
                        </div>
                    </c:if>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>兼职类型</label>
                    <div class="col-xs-6">
                        <select required data-rel="select2" name="type"
                                data-placeholder="请选择" data-width="225">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_cadre_company_type"/>
                        </select>
                        <script type="text/javascript">
                            $("#modal form select[name=type]").val(${cadreCompany.type});
                        </script>
                    </div>
                </div>
                <div class="form-group" id="typeOtherDiv">
                    <label class="col-xs-3 control-label">其他兼职类型</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="text" name="typeOther" placeholder="请输入其他兼职类型"
                               value="${cadreCompany.typeOther}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>兼职单位</label>
                    <div class="col-xs-8">
                        <textarea required class="form-control" name="unit">${cadreCompany.unit}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>兼任职务</label>
                    <div class="col-xs-8">
                        <textarea required class="form-control" name="post">${cadreCompany.post}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">兼职起始时间</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 130px">
                            <input class="form-control date-picker" name="startTime" type="text"
                                   data-date-min-view-mode="1" placeholder="yyyy.mm"
                                   data-date-format="yyyy.mm"
                                   value="${cm:formatDate(cadreCompany.startTime,'yyyy.MM')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <c:if test="${isFinished}">
                    <div class="form-group">
                        <label class="col-xs-3 control-label">兼职结束时间</label>
                        <div class="col-xs-6">
                            <div class="input-group" style="width: 130px">
                                <input class="form-control date-picker" name="finishTime" type="text"
                                       data-date-min-view-mode="1" placeholder="yyyy.mm"
                                       data-date-format="yyyy.mm"
                                       value="${cm:formatDate(cadreCompany.finishTime,'yyyy.MM')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label class="col-xs-3 control-label">审批单位</label>
                    <div class="col-xs-8">
                        <textarea class="form-control" name="approvalUnit">${cadreCompany.approvalUnit}</textarea>
                    </div>
                </div>
            </div>
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-4 control-label">批复日期</label>
                    <div class="col-xs-6">
                        <div class="input-group" style="width: 150px">
                            <input class="form-control date-picker" name="approvalDate" type="text"
                                   placeholder="yyyy-mm-dd"
                                   data-date-format="yyyy-mm-dd"
                                   value="${cm:formatDate(cadreCompany.approvalDate,'yyyy-MM-dd')}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-4 control-label">批复文件</label>
                    <div class="col-xs-7">
                        <input class="form-control" type="file" name="_approvalFile"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-4 control-label">是否取酬</label>
                    <div class="col-xs-6">
                        <label>
                            <input name="hasPay" ${cadreCompany.hasPay?"checked":""} type="checkbox"/>
                            <span class="lbl"></span>
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-4 control-label">所取酬劳是否全额上交学校</label>
                    <div class="col-xs-6">
                        <label>
                            <input name="hasHand" ${cadreCompany.hasHand?"checked":""} type="checkbox"/>
                            <span class="lbl"></span>
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-xs-4 control-label">备注</label>
                    <div class="col-xs-7">
                        <textarea class="form-control" name="remark">${cadreCompany.remark}</textarea>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button type="button" class="btn btn-primary" id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"><c:if
            test="${cadreCompany!=null}">确定</c:if><c:if test="${cadreCompany==null}">添加</c:if></button>
</div>

<script>
    $("#modal :checkbox").bootstrapSwitch();

    function hasPayChange() {
        if (!$("input[name=hasPay]").bootstrapSwitch("state")) {
            $("input[name=hasHand]").bootstrapSwitch("state", false).bootstrapSwitch('disabled', true);
        } else {
            $("input[name=hasHand]").bootstrapSwitch('disabled', false);
        }
    }

    $('input[name=hasPay]').on('switchChange.bootstrapSwitch', function (event, state) {
        hasPayChange();
    });
    hasPayChange();

    $.register.date($('.date-picker'));
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modal form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal("hide");
                        <c:if test="${param.toApply!=1}">
                        $('#${not empty param.grid?param.grid:"jqGrid"}').trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#body-content-view").load("${ctx}/modifyTableApply_detail?module=${param.module}&opType=${param.opType}&applyId=${param.applyId}&_=" + new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        $.hashchange('cls=1&module=${param.module}');
                        </c:if>
                        </c:if>
                    }
                    $btn.button('reset');
                }
            });
        }
    });

    function typeChange() {

        if ($("#modalForm select[name=type]").val() == '${cm:getMetaTypeByCode("mt_cadre_company_other").id}') {
            $("#typeOtherDiv").show();
            $("#modalForm input[name=typeOther]").attr("required", "required");
        } else {
            $("#typeOtherDiv").hide();
            $("#modalForm input[name=typeOther]").removeAttr("required");
        }
    }

    $("#modalForm select[name=type]").change(function () {
        typeChange();
    });
    typeChange();

    $.fileInput($('#modalForm input[type=file]'), {
        no_file: '请上传pdf文件',
        allowExt: ['pdf']
    })

    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>