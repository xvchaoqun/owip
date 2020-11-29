<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>确定入党介绍人</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/apply_candidate_sponsor" autocomplete="off" disableautocomplete id="applyForm" method="post">
        <input type="hidden" name="ids" value="${param.ids}">
        <c:set var="count" value="${fn:length(fn:split(param.ids,\",\"))}"/>
        <c:if test="${count>1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">处理记录</label>
                <div class="col-xs-6 label-text">
                        ${count} 条
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>入党介绍人类型</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required ${inSchool==1?'checked':''} type="radio" name="inSchool" id="inSchool1" value="1">
                        <label for="inSchool1">
                            校内
                        </label>
                    </div>
                    &nbsp;&nbsp;
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required ${inSchool==0?'checked':''} type="radio"  name="inSchool" id="inSchool0" value="0">
                        <label for="inSchool0">
                            校外
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group inSchool">
            <label class="col-xs-4 control-label">入党介绍人1</label>
            <div class="col-xs-6 required">
                <c:set var="sysUser" value="${cm:getUserById(userIds[0])}"/>
                <select name="userId1" data-rel="select2-ajax" data-width="270"
                        data-ajax-url="${ctx}/member_selects?noAuth=1&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}&status=${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_TRANSFER}"
                        data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group inSchool">
            <label class="col-xs-4 control-label">入党介绍人2</label>
            <div class="col-xs-6 ${_p_sponsorUsers_count==2?'required':''}">
                <c:set var="sysUser" value="${cm:getUserById(userIds[1])}"/>
                <select name="userId2" data-rel="select2-ajax" data-width="270"
                        data-ajax-url="${ctx}/member_selects?noAuth=1&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}&status=${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_TRANSFER}"
                        data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group outSchool">
            <label class="col-xs-4 control-label">入党介绍人1</label>
            <div class="col-xs-6 required">
                <input style="width: 100px" class="form-control" type="text" name="user1" value="${users[0]}">
                <span class="help-block">注：请输入一位入党介绍人的姓名</span>
            </div>
        </div>
        <div class="form-group outSchool">
            <label class="col-xs-4 control-label">入党介绍人2</label>
            <div class="col-xs-6 ${_p_sponsorUsers_count==2?'required':''}">
                <input style="width: 100px" class="form-control" type="text" name="user2" value="${users[1]}">
                <span class="help-block">注：请输入一位入党介绍人的姓名</span>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <button id="applySubmitBtn" type="button" class="btn btn-primary"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"><i class="fa fa-check"></i> 确定</button>
</div>

<script>
    function _typeChange(){
        var inSchool = $('#applyForm input[name=inSchool]:checked').val();
        if(inSchool==undefined || inSchool==1){
            $("select", "#applyForm div.required").requireField(true);
            $("input", "#applyForm").requireField(false, false, true);
            $("#applyForm div.inSchool").show();
            $("#applyForm div.outSchool").hide();
        }else{
            $("select", "#applyForm").requireField(false).val(null).trigger("change");
            $("input", "#applyForm div.required").requireField(true, false, false);
            $("#applyForm div.inSchool").hide();
            $("#applyForm div.outSchool").show();
        }
    }
    $('#applyForm input[name=inSchool]').click(function(){
        _typeChange();
    });
    _typeChange();

    $.register.user_select($('#applyForm select[data-rel="select2-ajax"]'));

    $.register.date($('.date-picker'), {endDate:"${_today}"});
    $("#applySubmitBtn").click(function(){$("#applyForm").submit();return false;});
    $("#applyForm").validate({
        submitHandler: function (form) {
            var $btn = $("#applySubmitBtn").button('loading');
            var userIds = [];
            userIds.push($("#applyForm select[name=userId1]").val());
            userIds.push($("#applyForm select[name=userId2]").val());
            var users = [];
            users.push($("#applyForm input[name=user1]").val())
            var user2 = $("#applyForm input[name=user2]").val();
            if($.trim(user2)!='') {
                users.push(user2)
            }

            $(form).ajaxSubmit({
                data:{userIds:userIds, users:users},
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        //goto_next("${param.gotoNext}", function(){
                            $("label.sponsorUsers").html(ret.sponsorUsers)
                            $("#modalForm input[name=sponsorUsers]").val(ret.sponsorUsers)
                            $("#modalForm input[name=sponsorUserIds]").val(ret.sponsorUserIds)
                        //});
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>