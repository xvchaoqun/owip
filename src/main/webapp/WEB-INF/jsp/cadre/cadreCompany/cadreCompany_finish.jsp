<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>兼职结束</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreCompany_finish"
          id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${cadreCompany.id}">
        <input type="hidden" name="isFinished" value="1">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${cm:getCadreById(cadreCompany.cadreId).realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">兼职结束时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="finishTime" type="text"
                           data-date-format="yyyy.mm" value="${cm:formatDate(cadreCompany.finishTime,'yyyy.MM')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button type="button" class="btn btn-primary" id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定</button>
</div>

<script>
    $.register.date($('.date-picker'));

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modal form").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        $("#jqGrid_cadreCompany").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>