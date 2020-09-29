<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="UNIT_POST_STATUS_NORMAL" value="<%=SystemConstants.UNIT_POST_STATUS_NORMAL%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${scShift!=null?'编辑':'添加'}交流轮岗</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scShift_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="id" value="${scShift.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 干部</label>
            <div class="col-xs-6">
                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/cadre_selects?key=1"
                        data-width="273" name="userId" data-placeholder="请选择干部">
                    <option value="${scShift.userId}">${cm:getUserById(scShift.userId).realname}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 交流类型</label>
            <div class="col-xs-6">
                <select required class="col-xs-6" required name="type" data-width="270"
                        data-rel="select2" data-placeholder="请选择">
                    <option></option>
                    <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_sc_shift').id}"/>
                </select>
                <script type="text/javascript">
                    $("#modalForm select[name=type]").val(${scShift.type});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"> 拟调整岗位</label>
            <div class="col-xs-6 postIdSelect">
                <input type="hidden" name="recordPostId" value=""/>
                <c:set value="${scShift.unitPost}" var="unitPost" />
                <select name="postId" data-rel="select2-ajax" data-ajax-url="${ctx}/unitPost_selects"
                        data-width="273" data-placeholder="请选择">
                    <option value="${unitPost.id}" delete="${unitPost.status!=UNIT_POST_STATUS_NORMAL}">${unitPost.code}-${unitPost.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span> 拟任职岗位</label>
            <div class="col-xs-6">
                <c:set var="assignPost" value="${scShift.assignPost}" />
                <select required name="assignPostId" data-rel="select2-ajax" data-ajax-url="${ctx}/unitPost_selects"
                        data-width="273" data-placeholder="请选择">
                    <option value="${assignPost.id}" delete="${assignPost.status!=UNIT_POST_STATUS_NORMAL}">${assignPost.code}-${assignPost.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"> 对应的选任纪实</label>
            <div class="col-xs-6">
                <input type="hidden" name="recordId" value="${scRecord.id}">
                <input readonly class="form-control" type="text" name="recordCode" value="${scRecord.code}">
            </div>
            <button id="selectRecordBtn" type="button" class="btn btn-success btn-sm"><i
                    class="fa fa-plus"></i> 选择
            </button>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"> 备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" type="text" name="remark">${scShift.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
            class="btn btn-primary"><i class="fa fa-check"></i> ${not empty scShift?'确定':'添加'}</button>
</div>
<script>

    $("#modalForm select[name=assignPostId]").change(function () {

        var recordPostId = $("#modalForm input[name=recordPostId]").val();
        if ($(this).val() != recordPostId) {
            $("#modalForm input[name=recordId]").val('');
            $("#modalForm input[name=recordCode]").val('');
        }
    });

    function _selectItem(id, rowData) {
        if (id > 0) {
            var code = rowData.code;
            $("#modalForm input[name=recordId]").val(id);
            $("#modalForm input[name=recordCode]").val(code);
            $("#modalForm input[name=recordPostId]").val(rowData.unitPostId);

            $("#modalForm select[name=assignPostId]").html('<option value="{0}">{1}</option>'
                .format(rowData.unitPostId, rowData.postCode + "-" + rowData.postName))
                .trigger("change");
        } else {
            $("#modalForm input[name=recordId]").val('');
            $("#modalForm input[name=recordCode]").val('');
        }
        WebuiPopovers.hideAll();
    }

    $('#selectRecordBtn').webuiPopover({
        padding: true,
        backdrop: false,
        autoHide: false,
        trigger: 'click',
        content: function (data) {
            return '<div id="popup-content">' + data + '</div>';
        },
        type: 'async',
        cache: false,
        url: '/sc/scRecord?cls=10&year=${_thisYear}&scType=${cm:getMetaTypeByCode("mt_sctype_crs").id}'
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
    //$('textarea.limited').inputlimiter();
    //$.register.date($('.date-picker'));
</script>