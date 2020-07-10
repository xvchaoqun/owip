<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_TRANSFER_STATUS_BACK" value="<%=MemberConstants.MEMBER_TRANSFER_STATUS_BACK%>"/>
<div class="well">注：本校读取研究生或博士生或留校，<span style="color: #ff0000; ">需通过现有学工号提交完成“组织关系转出”审批，再用新分配学工号提交完成“组织关系转入”审批。</span>
</div>
<c:if test="${memberTransfer.status==MEMBER_TRANSFER_STATUS_BACK}">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">
            <i class="ace-icon fa fa-times"></i>
        </button>
        <strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if
            test="${not empty memberTransfer.reason}">: ${memberTransfer.reason}</c:if>
        <br>
    </div>
</c:if>
<form class="form-horizontal" action="${ctx}/m/memberTransfer_au" autocomplete="off" disableautocomplete id="modalForm"
      method="post">
    <input type="hidden" name="id" value="${memberTransfer.id}">
    <div class="form-group">
        <label class="control-label">转出组织机构 </label>
            <div>
                ${fromParty.name}
            <c:if test="${not empty fromBranch}">-${fromBranch.name}</c:if>
            </div>
    </div>

    <div class="form-group">
        <label class="control-label"><span class="star">*</span>转入党组织</label>
        <div>
                <select required class="form-control" data-rel="select2-ajax"
                        data-ajax-url="${ctx}/m/party_selects?del=0" data-width="100%"
                        name="toPartyId" data-placeholder="请选择转入${_p_partyName}">
                    <option value="${toParty.id}">${toParty.name}</option>
                </select>
        </div>
        <div style="padding-top: 10px;${(empty toBranch)?'display: none':''}" id="toBranchDiv">
            <select class="form-control" data-rel="select2-ajax" data-width="100%"
                    data-ajax-url="${ctx}/m/branch_selects?del=0"
                    name="toBranchId" data-placeholder="请选择">
                <option value="${toBranch.id}">${toBranch.name}</option>
            </select>
    </div>
    </div>

    <script>
        $.register.party_branch_select($("#modalForm"), "toBranchDiv",
            '${cm:getMetaTypeByCode("mt_direct_branch").id}',
            "${toParty.id}", "${toParty.classId}", "toPartyId", "toBranchId", true);
    </script>

    <div class="form-group">
        <label class="col-xs-6 control-label"><span class="star">*</span>转出单位联系电话</label>
        <div class="col-xs-6" >
            <input required class="form-control" type="text" name="fromPhone"
                   value="${memberTransfer.fromPhone}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-6 control-label">转出单位传真</label>
        <div class="col-xs-6" >
            <input class="form-control" type="text" name="fromFax" value="${memberTransfer.fromFax}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-6 control-label"><span class="star">*</span>党费缴纳至年月</label>
        <div class="col-xs-6" >
            <div class="input-group">
                <input required class="form-control date-picker" name="_payTime" type="text"
                       data-date-format="yyyy-mm"
                       data-date-min-view-mode="1"
                       value="${cm:formatDate(memberTransfer.payTime,'yyyy-MM')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-6 control-label"><span class="star">*</span>介绍信有效期天数</label>
        <div class="col-xs-6" >
            <input required class="form-control digits" type="text" name="validDays"
                   value="${memberTransfer.validDays}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-6 control-label"><span class="star">*</span>转出办理时间</label>
        <div class="col-xs-6" >
            <div class="input-group">
                <input required class="form-control date-picker" name="_fromHandleTime" type="text"
                       data-date-format="yyyy-mm-dd"
                       data-date-end-date="${_today}"
                       value="${cm:formatDate(memberTransfer.fromHandleTime,'yyyy-MM-dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <div class="clearfix form-actions center">
        <button class="btn btn-info" id="submitBtn" type="button" data-loading-text="提交中..." autocomplete="off">
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
        </button>
    </div>
</form>

<script>

    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
    $("#submitBtn").click(function () {
        var $btn = $(this).button('loading');
        $("#modalForm").submit();
        setTimeout(function () {
            $btn.button('reset');
        }, 1000);
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {

            if (!$("#branchDiv").is(":hidden")) {
                if ($('#modalForm select[name=branchId]').val() == '') {
                    SysMsg.success('请选择支部。');
                    return;
                }
            }
            $(form).ajaxSubmit({
                success: function (ret) {
                    $("#submitBtn").button("reset");
                    if (ret.success) {
                        SysMsg.success('提交成功。', function () {
                            location.reload();
                        });
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>
