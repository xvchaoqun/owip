<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_RETURN_STATUS_DENY" value="<%=MemberConstants.MEMBER_RETURN_STATUS_DENY%>"/>
<c:if test="${memberReturn.status==MEMBER_RETURN_STATUS_DENY}">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">
            <i class="ace-icon fa fa-times"></i>
        </button>
        <strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if
            test="${not empty memberReturn.remark}">: ${memberReturn.remark}</c:if>
        <br>
    </div>
</c:if>
<div class="page-header">
    <h1>
        留学归国申请恢复组织生活
    </h1>
</div>
<form class="form-horizontal" autocomplete="off" disableautocomplete id="modalForm" method="post"
      action="${ctx}/m/memberReturn">
    <input type="hidden" name="id" value="${memberReturn.id}">
    <div class="form-group">
        <label class="col-xs-5 control-label no-padding-right"> ${(_user.type==USER_TYPE_JZG)?"工作证号":"学号"}</label>
        <div class="col-xs-7 label-text">
            ${_user.code}
        </div>
    </div>
    <div class="form-group">
            <label class="col-xs-5 control-label no-padding-right"><span class="star">*</span>联系基层党组织</label>
            <div class="col-xs-12 ">
                <select required class="form-control" data-width="100%" data-rel="select2-ajax" data-ajax-url="${ctx}/m/party_selects?del=0"
                        name="partyId" data-placeholder="请选择">
                    <option value="${party.id}">${party.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
            <label class="col-xs-5 control-label"><span class="star">*</span>联系党支部</label>
            <div class="col-xs-12">
                <select class="form-control" data-width="100%" data-rel="select2-ajax" data-ajax-url="${ctx}/m/branch_selects?del=0"
                        name="branchId" data-placeholder="请选择党支部">
                    <option value="${branch.id}">${branch.name}</option>
                </select>
            </div>
        </div>
        <script>
            $.register.party_branch_select($("#modalForm"), "branchDiv",
                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}", "partyId", "branchId", true);
        </script>

    <div class="form-group">
        <label class="col-xs-5 control-label"><span class="star">*</span>党籍状态</label>
        <div class="col-xs-7">
            <select required data-rel="select2" name="politicalStatus" data-placeholder="请选择" data-width="100%">
                <option></option>
                <c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
                    <option value="${_status.key}">${_status.value}</option>
                </c:forEach>
            </select>
            <script>
                $("#modalForm select[name=politicalStatus]").val(${memberReturn.politicalStatus});
            </script>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-6 control-label"><span class="star">*</span>提交恢复组织生活申请时间</label>
        <div class="col-xs-6">
            <div class="input-group">
                <input required class="form-control date-picker" name="_returnApplyTime" type="text"
                       data-date-format="yyyy-mm-dd"
                       value="${cm:formatDate(memberReturn.returnApplyTime,'yyyy-MM-dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-6 control-label">提交书面申请书时间</label>
        <div class="col-xs-6">
            <div class="input-group">
                <input class="form-control date-picker" name="_applyTime" type="text"
                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.applyTime,'yyyy-MM-dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-6 control-label">确定为入党积极分子时间</label>
        <div class="col-xs-6">
            <div class="input-group">
                <input class="form-control date-picker" name="_activeTime" type="text"
                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.activeTime,'yyyy-MM-dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-6 control-label">确定为发展对象时间</label>
        <div class="col-xs-6">
            <div class="input-group">
                <input class="form-control date-picker" name="_candidateTime" type="text"
                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.candidateTime,'yyyy-MM-dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-6 control-label">入党时间</label>
        <div class="col-xs-6">
            <div class="input-group">
                <input class="form-control date-picker" name="_growTime" type="text"
                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.growTime,'yyyy-MM-dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-6 control-label">转正时间</label>
        <div class="col-xs-6">
            <div class="input-group">
                <input class="form-control date-picker" name="_positiveTime" type="text"
                       data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.positiveTime,'yyyy-MM-dd')}"/>
                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
            </div>
        </div>
    </div>
<div class="form-group">
        <label class="col-sm-5 control-label">备注</label>
        <div class="col-sm-7">
            <textarea class="form-control limited" name="remark" maxlength="100"
                      rows="2">${memberReturn.remark}</textarea>
        </div>
    </div>

    <div class="clearfix form-actions center">
        <button class="btn btn-info" type="submit" id="submitBtn" data-loading-text="提交中..."
                data-success-text="您的申请已提交成功" autocomplete="off">
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
        </button>

        &nbsp; &nbsp; &nbsp;
        <button class="hideView btn btn-default" type="button">
            <i class="ace-icon fa fa-undo bigger-110"></i>
            返回
        </button>
    </div>
</form>
<script>
    $('textarea.limited').inputlimiter();

    $.register.date($('.date-picker'), {endDate: '${_today}'});
    $('#modalForm [data-rel="select2"]').select2();
    var jqValid = $("form").validate({
        submitHandler: function (form) {
            if (!$("#party").is(":hidden")) {
                if ($('select[name=partyId]').val() == '') {
                    SysMsg.success("请选择${_p_partyName}。");
                    return;
                }
            }
            if (!$("#branch").is(":hidden")) {
                if ($('select[name=branchId]').val() == '') {
                    SysMsg.success("请选择支部。");
                    return;
                }
            }
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        SysMsg.success("提交成功。", function () {
                            $btn.button("success").addClass("btn-success");
                            location.reload();
                        });
                    } else {
                        $btn.button('reset');
                    }
                }
            });
        }
    });

    $('#modalForm select[name=politicalStatus]').change(function () {
        var $input = $("#modalForm  input[name=_positiveTime]");
        if ($(this).val() == '${MEMBER_POLITICAL_STATUS_POSITIVE}') {
            //$input.closest(".form-group").addClass("has-error");
            $input.attr("required", "required");
        } else {
            //$input.closest(".form-group").removeClass("has-error");
            $input.removeAttr("required");
        }
        jqValid.element($input);
    }).change();
</script>
