<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=OwConstants.OW_PARTY_EVA_MAP%>" var="OW_PARTY_EVA_MAP"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${partyReport!=null?'编辑':'添加'}党支部考核</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/partyReport_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="id" value="${partyReport.id}">
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 年度</label>
            <div class="col-xs-6">
                <input required class="date-picker"
                       name="year" type="text"
                       data-date-start-view="2"
                       data-date-min-view-mode="2"
                       data-date-max-view-mode="2"
                       data-date-format="yyyy"
                       style="width: 100px"
                       value="${partyReport.year}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 所属${_p_partyName}</label>
            <div class="col-xs-6">
                <select required class="form-control" data-rel="select2-ajax"
                        data-ajax-url="${ctx}/party_selects?auth=1"
                        name="partyId" data-placeholder="请选择${_p_partyName}" data-width="272">
                    <option value="${partyReport.partyId}">${partyReport.party.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
            <label class="col-xs-4 control-label"><span class="star">*</span> 党支部</label>
            <div class="col-xs-6">
                <select class="form-control"  data-rel="select2-ajax"
                        data-ajax-url="${ctx}/branch_selects?auth=1"
                        name="branchId" data-placeholder="请选择党支部" data-width="272">
                    <option value="${partyReport.branch.id}" delete="${partyReport.branch.isDeleted}">${partyReport.branch.name}</option>
                </select>
            </div>
        </div>
        <script>
            $.register.party_branch_select($("#modalForm"), "branchDiv",
                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${partyReport.party.id}",  "${partyReport.party.classId}" );
        </script>
       <%-- <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span> 工作总结</label>
            <div class="col-xs-6">
                <input ${partyReport.reportFile==null?'required':''} class="form-control" type="file"
                                                                     name="_reportFile">
            </div>
        </div>--%>
        <div class="form-group">
            <label class="col-xs-4 control-label"> 考核结果</label>
            <div class="col-xs-6">
                <select data-rel="select2" name="evaResult" data-placeholder="请选择" data-width="272">
                    <option></option>
                    <c:forEach var="_type" items="${OW_PARTY_EVA_MAP}">
                        <option value="${_type.key}">${_type.value}</option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=evaResult]").val(${partyReport.evaResult});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"> 考核结果文件</label>
            <div class="col-xs-6">
                <input class="form-control" type="file" name="_evaFile">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"> 备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" type="text" name="remark">${partyReport.remark}</textarea>
            </div>
        </div>

    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty partyReport?'确定':'添加'}</button>
</div>
<script>

    $.fileInput($("#modalForm input[name=_reportFile]"), {
        no_file: '请上传word文件...',
        allowExt: ['doc', 'docx'],
        allowMime: ['application/msword', 'application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    });
    $.fileInput($('#modalForm input[name=_evaFile]'), {
        no_file: '请上传Pdf文件...',
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
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#modalForm [name="evaResult"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
</script>