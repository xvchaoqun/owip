<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cesResult!=null?'修改':'添加'}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cesResult_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="id" value="${cesResult.id}">
        <input type="hidden" name="type" value="${param.type}">
        <c:if test="${param.type==CES_RESULT_TYPE_CADRE}">
            <input type="hidden" name="cadreId" value="${cadre.id}">
        </c:if>
        <c:if test="${param.type==CES_RESULT_TYPE_UNIT && not empty unit}">
            <input type="hidden" name="unitId" value="${unit.id}">
        </c:if>

        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 年份</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 100px">
                    <input required class="form-control date-picker" placeholder="请选择" name="year" type="text"
                           data-date-format="yyyy" data-date-min-view-mode="2"
                           value="${empty cesResult.year?_thisYear:cesResult.year}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <c:if test="${param._auth!='1' && param.type==CES_RESULT_TYPE_CADRE}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span> 姓名</label>
                <c:if test="${empty cadre}">
                    <div class="col-xs-6">
                        <select data-rel="select2-ajax"
                                data-ajax-url="${ctx}/cadre_selects?status=${CADRE_STATUS_CJ},${CADRE_STATUS_KJ},${CADRE_STATUS_LEADER}"
                                data-width="273"
                                name="cadreId" data-placeholder="请选择" required>
                            <option></option>
                        </select>
                    </div>
                </c:if>
                <c:if test="${not empty cadre}">
                    <div class="col-xs-6 label-text">
                            ${cadre.realname}
                    </div>
                </c:if>
            </div>
        </c:if>
        <c:if test="${param.type==CES_RESULT_TYPE_UNIT && not empty unit}">
            <div class="form-group">
                <label class="col-xs-3 control-label">所属单位</label>
                <div class="col-xs-6 label-text">
                    <span delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</span>
                </div>
            </div>
        </c:if>
        <c:if test="${param.type!=CES_RESULT_TYPE_UNIT || empty unit}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span
                        class="star">*</span> ${param.type==CES_RESULT_TYPE_CADRE?'时任单位':'所属单位'}</label>
                <div class="col-xs-6">
                    <select data-rel="select2-ajax"
                            data-width="273" data-ajax-url="${ctx}/unit_selects"
                            name="unitId" data-placeholder="请选择">
                        <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                    </select>
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">${param.type==CES_RESULT_TYPE_CADRE?'时任职务':'班子名称'}</label>
            <div class="col-xs-6">
                <textarea class="form-control noEnter" name="title">${cesResult.title}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 测评类别</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${cesResult.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 排名</label>
            <div class="col-xs-6">
                <input required class="form-control" type="number" style="width: 80px;" min="1" name="rank"
                       value="${cesResult.rank}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span
                    class="star">*</span> ${param.type==CES_RESULT_TYPE_CADRE?'总人数':'班子总人数'}</label>
            <div class="col-xs-6">
                <input required class="form-control" type="number" style="width: 80px;" min="1" name="num"
                       value="${cesResult.num}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"> 备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text" name="remark">${cesResult.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty cesResult?'确定':'添加'}</button>
</div>
<script>
    $.register.ajax_select($('#modalForm select[name=unitId]'));
    $.register.user_select($('#modalForm select[name=cadreId]'));
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
                        _reloadGrid();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>