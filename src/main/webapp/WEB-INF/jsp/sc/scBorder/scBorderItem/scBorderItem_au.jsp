<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${scBorderItem!=null?'编辑':'添加'}报备干部</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scBorderItem_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${scBorderItem.id}">
        <div class="form-group">
            <label class="col-xs-4 control-label">所属干部</label>
            <div class="col-xs-6 label-text">
                ${cm:getCadreById(scBorderItem.cadreId).realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label" style="padding-top: 25px">报备时所在单位及职务</label>
            <div class="col-xs-6">
                <textarea class="form-control noEnter" name="title" rows="3">${scBorderItem.title}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">行政级别</label>
            <div class="col-xs-6">
                <select  data-rel="select2" name="adminLevel" data-width="272" data-placeholder="请选择行政级别">
                    <option></option>
                    <jsp:include page="/metaTypes?__code=mc_admin_level"/>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=adminLevel]").val(${scBorderItem.adminLevel});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control noEnter limited" maxlength="200"
                          name="remark" rows="3">${scBorderItem.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> <c:if test="${scBorderItem!=null}">确定</c:if><c:if test="${scBorderItem==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>