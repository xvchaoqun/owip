<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${train!=null}">编辑</c:if><c:if test="${train==null}">添加</c:if>培训班次</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/train_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${train.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">名称</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${train.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">简介</label>

            <div class="col-xs-6">
                <textarea class="form-control" name="summary">${train.summary}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">开始日期</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input  class="form-control date-picker required" name="_startDate"
                            type="text" data-date-format="yyyy-mm-dd"
                            value="${cm:formatDate(train.startDate, "yyyy-MM-dd")}"/>
                    <span class="input-group-addon">
                        <i class="fa fa-calendar bigger-110"></i>
                    </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">结束日期</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control date-picker required" name="_endDate"
                           type="text" data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(train.endDate, "yyyy-MM-dd")}"/>
                        <span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>

            <div class="col-xs-6">
                <textarea class="form-control" name="remark">${train.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${train!=null}">确定</c:if><c:if test="${train==null}">添加</c:if>"/>
</div>

<script>
    register_date($('.date-picker'));

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

    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>