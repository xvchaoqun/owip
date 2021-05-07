<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/member/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>确定培养联系人</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/apply_grow_contact" autocomplete="off" disableautocomplete id="applyForm" method="post">
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
            <label class="col-xs-4 control-label"><span class="star">*</span>培养联系人1类型</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required ${empty bean.inSchool1 || bean.inSchool1==1?'checked':''} type="radio" name="inSchool1" id="inSchool1_1" value="1">
                        <label for="inSchool1_1">
                            校内
                        </label>
                    </div>
                    &nbsp;&nbsp;
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required ${bean.inSchool1==0?'checked':''} type="radio"  name="inSchool1" id="inSchool1_0" value="0">
                        <label for="inSchool1_0">
                            校外
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group inSchool1">
            <label class="col-xs-4 control-label">培养联系人1</label>
            <div class="col-xs-6 required">
                <c:set var="sysUser" value="${cm:getUserById(bean.userId1)}"/>
                <select name="userId1" data-rel="select2-ajax" data-width="270"
                        data-ajax-url="${ctx}/member_selects?noAuth=1&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}&status=${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_OUT}"
                        data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
            </div>
        </div>
        <div class="form-group outSchool1">
            <label class="col-xs-4 control-label">培养联系人1</label>
            <div class="col-xs-6 required">
                <input style="width: 100px" class="form-control" type="text" name="user1" value="${bean.user1}">
                <span class="help-block">注：请输入一位培养联系人的姓名</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>培养联系人2类型</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required ${empty bean.inSchool2 || bean.inSchool2==1?'checked':''} type="radio" name="inSchool2" id="inSchool2_1" value="1">
                        <label for="inSchool2_1">
                            校内
                        </label>
                    </div>
                    &nbsp;&nbsp;
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required ${bean.inSchool2==0?'checked':''} type="radio"  name="inSchool2" id="inSchool2_0" value="0">
                        <label for="inSchool2_0">
                            校外
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group inSchool2">
            <label class="col-xs-4 control-label">培养联系人2</label>
            <div class="col-xs-6 ${_p_growContactUsers_count==2?'required':''}">
                <c:set var="sysUser" value="${cm:getUserById(bean.userId2)}"/>
                <select name="userId2" data-rel="select2-ajax" data-width="270"
                        data-ajax-url="${ctx}/member_selects?noAuth=1&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}&status=${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_OUT}"
                        data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                </select>
            </div>
        </div>

        <div class="form-group outSchool2">
            <label class="col-xs-4 control-label">培养联系人2</label>
            <div class="col-xs-6 ${_p_growContactUsers_count==2?'required':''}">
                <input style="width: 100px" class="form-control" type="text" name="user2" value="${bean.user2}">
                <span class="help-block">注：请输入一位培养联系人的姓名</span>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <button id="applySubmitBtn" type="button" class="btn btn-primary"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"><i class="fa fa-check"></i> 确定</button>
</div>

<script>
    function _type1Change(){
        var inSchool1 = $('#applyForm input[name=inSchool1]:checked').val();
        if(inSchool1==undefined || inSchool1==1){
            $("select[name=userId1]", "#applyForm div.required").requireField(true);
            $("input[name=user1]", "#applyForm").requireField(false, false, true);
            $("#applyForm div.inSchool1").show();
            $("#applyForm div.outSchool1").hide();
        }else{
            $("select[name=userId1]", "#applyForm").requireField(false).val(null).trigger("change");
            $("input[name=user1]", "#applyForm div.required").requireField(true, false, false);
            $("#applyForm div.inSchool1").hide();
            $("#applyForm div.outSchool1").show();
        }
    }
    $('#applyForm input[name=inSchool1]').click(function(){
        _type1Change();
    });
    _type1Change();
    function _type2Change(){
        var inSchool2 = $('#applyForm input[name=inSchool2]:checked').val();
        if(inSchool2==undefined || inSchool2==1){
            $("select[name=userId2]", "#applyForm div.required").requireField(true);
            $("input[name=user2]", "#applyForm").requireField(false, false, true);
            $("#applyForm div.inSchool2").show();
            $("#applyForm div.outSchool2").hide();
        }else{
            $("select[name=userId2]", "#applyForm").requireField(false).val(null).trigger("change");
            $("input[name=user2]", "#applyForm div.required").requireField(true, false, false);
            $("#applyForm div.inSchool2").hide();
            $("#applyForm div.outSchool2").show();
        }
    }
    $('#applyForm input[name=inSchool2]').click(function(){
        _type2Change();
    });
    _type2Change();

    $.register.user_select($('#applyForm select[data-rel="select2-ajax"]'));

    $.register.date($('.date-picker'), {endDate:"${_today}"});
    $("#applySubmitBtn").click(function(){$("#applyForm").submit();return false;});
    $("#applyForm").validate({
        submitHandler: function (form) {

            var $btn = $("#applySubmitBtn").button('loading');

            var growContactUserIds = "";
            var inSchool1 = $('#applyForm input[name=inSchool1]:checked').val();
            var inSchool2 = $('#applyForm input[name=inSchool2]:checked').val();
            if(inSchool1==1){
                growContactUserIds = "1##"+$("#applyForm select[name=userId1]").val();
            }else{
                growContactUserIds = "0##"+$("#applyForm input[name=user1]").val();
            }
            if(inSchool2==1){
                growContactUserIds += ",1##"+$("#applyForm select[name=userId2]").val();
            }else{
                growContactUserIds += ",0##"+$("#applyForm input[name=user2]").val();
            }

            $(form).ajaxSubmit({
                data:{growContactUserIds:growContactUserIds},
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        //goto_next("${param.gotoNext}", function(){
                            $("label.growContactUsers").html(ret.growContactUsers);
                            $("#applyAuForm input[name=growContactUsers]").val(ret.growContactUsers);
                            $("#applyAuForm input[name=growContactUserIds]").val(ret.growContactUserIds);
                        //});
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>