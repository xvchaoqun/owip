<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>归还</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scMatterAccess_back" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scMatterAccess.id}">

        <div class="form-group">
            <label class="col-xs-4 control-label">归还时间</label>
            <div class="col-xs-6">
                <div class="input-group" style="width:150px">
                    <input required class="form-control date-picker" name="returnDate"
                           type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${empty scMatterAccess.returnDate?today:(cm:formatDate(scMatterAccess.returnDate,'yyyy-MM-dd'))}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">归还接收人</label>

            <div class="col-xs-6 label-text">
                ${empty scMatterAccess.returnUser.realname?_user.realname:scMatterAccess.returnUser.realname}
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary"><i class="fa fa-check"></i> 确定</button>
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
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>