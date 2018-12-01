<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${unitTeam!=null}">编辑</c:if><c:if test="${unitTeam==null}">添加</c:if>行政班子</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitTeam_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitTeam.id}">
        <input type="hidden" name="unitId" value="${unit.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属单位</label>
            <div class="col-xs-6 label-text">
                ${unit.name}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">行政班子名称</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${empty unitTeam?unit.name:unitTeam.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">届数</label>
            <div class="col-xs-6">
                <input required class="form-control digits" type="text" name="seq" value="${unitTeam.seq}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                    <textarea class="form-control limited" name="remark">${unitTeam.remark}</textarea>
            </div>
        </div>
        <%--<div class="form-group">
            <label class="col-xs-3 control-label">应换届时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="expectDeposeDate" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(unitTeam.expectDeposeDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">实际换届时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input class="form-control date-picker" name="deposeDate" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(unitTeam.deposeDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">任命时间</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control date-picker" name="appointDate" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(unitTeam.appointDate,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>--%>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        ${unitTeam!=null?'确定':'添加'}</button>
</div>

<script>
    $("#modalForm :checkbox").bootstrapSwitch();
    $.register.date($('.date-picker'));
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
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>