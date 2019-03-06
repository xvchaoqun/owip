<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>上交</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scMatterItem_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scMatterItem.id}">
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>实交回日期</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="realHandTime" type="text"
                           data-date-format="yyyy-mm-dd" value="${empty scMatterItem.realHandTime?_today:cm:formatDate(scMatterItem.realHandTime,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>封面填表日期</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="fillTime" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(scMatterItem.fillTime,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" name="remark">${scMatterItem.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> <c:if
            test="${scMatterItem!=null}">确定</c:if><c:if test="${scMatterItem==null}">添加</c:if></button>
</div>

<script>
    $.register.date($('.date-picker'));
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
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>