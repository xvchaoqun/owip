<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=OwConstants.OW_PARTY_EVA_MAP%>" var="OW_PARTY_EVA_MAP"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${memberReport!=null?'编辑':'添加'}党组织书记考核</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/member/memberReport_au" autocomplete="off" disableautocomplete
          id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberReport.id}">
        <input type="hidden" name="userId" value="${memberReport.userId}">
        <input type="hidden" name="year" value="${memberReport.year}">
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 考核结果</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="evaResult" data-placeholder="请选择" data-width="272">
                    <option></option>
                    <c:forEach var="_type" items="${OW_PARTY_EVA_MAP}">
                        <option value="${_type.key}">${_type.value}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=evaResult]").val(${memberReport.evaResult});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 考核结果文件</label>
            <div class="col-xs-6">
                <input ${memberReport.evaFile==null?'required':''} class="form-control" type="file" name="_evaFile">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"> 备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" type="text" name="remark">${memberReport.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty memberReport?'确定':'添加'}</button>
</div>
<script>

    $.fileInput($('#modalForm input[name=_evaFile]'), {
        no_file: '请上传pdf文件...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
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
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    //$("#modalForm :checkbox").bootstrapSwitch();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>