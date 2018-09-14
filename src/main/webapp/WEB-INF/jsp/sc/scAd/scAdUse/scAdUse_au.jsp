<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scAdUse!=null}">编辑</c:if><c:if test="${scAdUse==null}">添加</c:if>其他用途</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scAdUse_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scAdUse.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">年份</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" placeholder="请选择年份"
                           name="year"
                           type="text"
                           data-date-format="yyyy" data-date-min-view-mode="2"
                           value="${empty scAdUse?_thisYear:scAdUse.year}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">日期</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="useDate"
                           type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${empty scAdUse?_today:(cm:formatDate(scAdUse.useDate,'yyyy-MM-dd'))}"/>
                                                    <span class="input-group-addon"> <i
                                                            class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">使用单位类型</label>

            <div class="col-xs-6">
                <label class="label-text">
                    <input required name="isOnCampus" type="radio" class="ace"
                           value="0"
                           <c:if test="${not empty scAdUse && !scAdUse.isOnCampus}">checked</c:if>/>
                    <span class="lbl"> 校外单位</span>
                </label>
                <label class="label-text">
                    <input required name="isOnCampus" type="radio" class="ace"
                           value="1"
                           <c:if test="${empty scAdUse || scAdUse.isOnCampus}">checked</c:if>/>
                    <span class="lbl"> 校内单位</span>
                </label>
            </div>
        </div>
        <div class="form-group" id="onCampusUnit">
            <label class="col-xs-3 control-label">使用单位</label>
            <div class="col-xs-6">
                <select data-rel="select2-ajax" data-width="270" data-ajax-url="${ctx}/unit_selects?status=1"
                        name="unitId" data-placeholder="请选择单位">
                    <option value="${scAdUse.unit.id}">${scAdUse.unit.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group" id="notOnCampusUnit" style="display: none">
            <label class="col-xs-3 control-label">使用单位</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="outUnit" value="${scAdUse.outUnit}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">用途</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="useage" value="${scAdUse.useage}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">干部</label>

            <div class="col-xs-6">
                <select required data-rel="select2-ajax"
                        data-ajax-url="${ctx}/cadre_selects?types=${CADRE_STATUS_MIDDLE},${CADRE_STATUS_LEADER}"
                        name="cadreId" data-placeholder="请输入账号或姓名或教工号" data-width="270">
                    <option value="${scAdUse.cadre.id}">${scAdUse.cadre.realname}-${scAdUse.cadre.code}</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited"
                          name="remark">${scAdUse.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if
            test="${scAdUse!=null}">确定</c:if><c:if test="${scAdUse==null}">添加</c:if></button>
</div>

<script>
    $("#modalForm input[name=isOnCampus]").change(function () {

        if ($(this).val() == '1') {
            $("#modalForm select[name=unitId]").prop("required", true);
            $("#modalForm input[name=outUnit]").removeAttr("required");
            $("#modalForm #onCampusUnit").show();
            $("#modalForm #notOnCampusUnit").hide();

        } else {
            $("#modalForm select[name=unitId]").removeAttr("required");
            $("#modalForm input[name=outUnit]").prop("required", true);
            $("#modalForm #onCampusUnit").hide();
            $("#modalForm #notOnCampusUnit").show();
        }
    }).change();
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });

    $.register.date($('.date-picker'));
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
</script>