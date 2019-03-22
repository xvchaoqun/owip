<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scMatterTransfer!=null}">编辑</c:if><c:if test="${scMatterTransfer==null}">添加</c:if>个人有关事项-移交记录</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scMatterTransfer_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${scMatterTransfer.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>移交对象</label>
            <c:if test="${empty scMatterTransfer}">
                <div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sc/scMatterUser_selects"
                            name="userId" data-placeholder="请输入账号或姓名或学工号" data-width="270">
                        <option></option>
                    </select>
                </div>
            </c:if>
            <c:if test="${not empty scMatterTransfer}">
                <div class="col-xs-6 label-text">
                        ${scMatterTransfer.user.realname}
                </div>
            </c:if>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>移交日期</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="transferDate"
                           type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(scMatterTransfer.transferDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>移交地点</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="location" value="${scMatterTransfer.location}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">移交原因</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" name="reason">${scMatterCheck.reason}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if
            test="${scMatterTransfer!=null}">确定</c:if><c:if test="${scMatterTransfer==null}">添加</c:if></button>
</div>

<script>
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

    $.register.user_select($('#modalForm [data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'));
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>