<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${partyMemberGroup!=null}">编辑</c:if><c:if test="${partyMemberGroup==null}">添加</c:if>领导班子</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/partyMemberGroup_au" autocomplete="off" disableautocomplete
          id="modalForm" method="post">
        <input type="hidden" name="id" value="${partyMemberGroup.id}">
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>所属${_p_partyName}</label>
            <c:if test="${empty partyMemberGroup}">
                <div class="col-xs-8">
                    <select required data-rel="select2-ajax" data-width="292"
                            data-ajax-url="${ctx}/party_selects?auth=1"
                            name="partyId" data-placeholder="请选择">
                        <option></option>
                    </select>
                </div>
            </c:if>
            <c:if test="${not empty partyMemberGroup}">
                <div class="col-xs-8 label-text">
                    <input type="hidden" name="partyId" value="${party.id}">
                        ${party.name}
                </div>
            </c:if>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">上一届班子</label>
            <div class="col-xs-8">
                <div class="help-block">
                    <select class="form-control" name="fid" data-width="292"
                            data-rel="select2-ajax" data-ajax-url="${ctx}/partyMemberGroup_selects?partyId=${party.id}"
                            data-placeholder="请选择班子">
                        <option value="${fPartyMemberGroup.id}">${fPartyMemberGroup.name}</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>名称</label>
            <div class="col-xs-8" style="width: 312px">
                <textarea required class="form-control" name="name">${partyMemberGroup.name}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">是否现任班子</label>
            <div class="col-xs-8">
                <label>
                    <input name="isPresent" ${partyMemberGroup.isPresent?"checked":""} type="checkbox"/>
                    <span class="lbl"></span>
                </label>
                <span class="help-block">注：每个${_p_partyName}必须设定一个“现任班子”</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>任命时间</label>
            <div class="col-xs-8">
                <div class="input-group" style="width: 150px">
                    <input required class="form-control date-picker" name="_appointTime" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(partyMemberGroup.appointTime,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>应换届时间</label>
            <div class="col-xs-8">
                <div class="input-group" style="width: 150px">
                    <input required class="form-control date-picker" name="_tranTime" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(partyMemberGroup.tranTime,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">实际换届时间</label>
            <div class="col-xs-8">
                <div class="input-group" style="width: 150px">
                    <input class="form-control date-picker" name="_actualTranTime" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(partyMemberGroup.actualTranTime,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
                <span class="help-block">注：实际换届时填写，完成修改后请撤销该班子</span>
            </div>
        </div>
        <%--<div class="form-group">
            <label class="col-xs-4 control-label">发文</label>
            <div class="col-xs-8">
                <select data-rel="select2-ajax" data-width="272" data-ajax-url="${ctx}/dispatchUnit_selects?unitId=${party.unitId}"
                        name="dispatchUnitId" data-placeholder="请选择单位发文">
                    <option value="${partyMemberGroup.dispatchUnitId}">${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year )}</option>
                </select>
            </div>
        </div>--%>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        ${partyMemberGroup!=null?'确定':'添加'}</button>
</div>

<script>
    var $party = $.register.ajax_select("#modalForm select[name=partyId]");
    $party.on("change", function () {

        var partyId = $.trim($(this).val());

        $("#modalForm select[name=fid]").data("ajax-url",
            "${ctx}/partyMemberGroup_selects?partyId=" + partyId);
        $.register.ajax_select("#modalForm select[name=fid]");

        if (partyId == '') {
            $("#modalForm select[name=fid]").val(null).trigger("change");
        }
    })
    $.register.ajax_select("#modalForm select[name=fid]");

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
                        if ("${param.type}" == "view") {
                            _reload();
                            //SysMsg.success('操作成功。', '成功');
                        } else {
                            $("#modal").modal("hide")
                            // SysMsg.success('提交成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                            $.hashchange();
                            //  });
                        }
                    }else{
                        $btn.button('reset');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>