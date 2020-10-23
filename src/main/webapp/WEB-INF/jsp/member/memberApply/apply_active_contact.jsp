<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>确定培养联系人</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/apply_active_contact" autocomplete="off" disableautocomplete id="applyForm" method="post">
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
            <label class="col-xs-4 control-label"><span class="star">*</span>培养联系人类型</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio" name="inSchool" id="inSchool1" value="1">
                        <label for="inSchool1">
                            校内
                        </label>
                    </div>
                    &nbsp;&nbsp;
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input required type="radio"  name="inSchool" id="inSchool0" value="0">
                        <label for="inSchool0">
                            校外
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group inSchool">
            <label class="col-xs-4 control-label"><span class="star">*</span>培养联系人1</label>
            <div class="col-xs-6">
                <select required name="contactUserIds" data-rel="select2-ajax" data-width="270"
                        data-ajax-url="${ctx}/member_selects?noAuth=1&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}&status=${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_TRANSFER}"
                        data-placeholder="请输入账号或姓名或学工号">
                    <option></option>
                </select>
            </div>
        </div>
        <div class="form-group inSchool">
            <label class="col-xs-4 control-label"><span class="star">*</span>培养联系人2</label>
            <div class="col-xs-6">
                <select required name="contactUserIds" data-rel="select2-ajax" data-width="270"
                        data-ajax-url="${ctx}/member_selects?noAuth=1&politicalStatus=${MEMBER_POLITICAL_STATUS_POSITIVE}&status=${MEMBER_STATUS_NORMAL},${MEMBER_STATUS_TRANSFER}"
                        data-placeholder="请输入账号或姓名或学工号">
                    <option></option>
                </select>
            </div>
        </div>
        <div class="form-group outSchool">
            <label class="col-xs-4 control-label"><span class="star">*</span>培养联系人1</label>
            <div class="col-xs-6">
                <input required style="width: 100px" class="form-control" type="text" name="contactUsers" value="${contactUsers[0]}">
                <span class="help-block">注：请输入一个联系人的姓名</span>
            </div>
        </div>
        <div class="form-group outSchool">
            <label class="col-xs-4 control-label"><span class="star">*</span>培养联系人2</label>
            <div class="col-xs-6">
                <input required style="width: 100px" class="form-control" type="text" name="contactUsers" value="${contactUsers[1]}">
                <span class="help-block">注：请输入一个联系人的姓名</span>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <button id="applySubmitBtn" type="button" class="btn btn-primary"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"><i class="fa fa-check"></i> 确定</button>
</div>

<script>
    $('#applyForm input[name=inSchool]').click(function(){

        var inSchool = $('#applyForm input[name=inSchool]:checked').val();
        if(inSchool==undefined || inSchool==1){
            $("#applyForm select[name=contactUserIds]").requireField(true);
            $("#applyForm input[name=contactUsers]").requireField(false);
            $("#applyForm div.inSchool").show();
            $("#applyForm div.outSchool").hide();
        }else{
            $("#applyForm select[name=contactUserIds]").requireField(false);
            $("#applyForm input[name=contactUsers]").requireField(true);
            $("#applyForm div.inSchool").hide();
            $("#applyForm div.outSchool").show();
        }
    });
    $('#applyForm input[name=inSchool][value=${inSchool?1:0}]').click();

    $.register.user_select($('select[name=contactUserIds]'));

    $.register.date($('.date-picker'), {endDate:"${_today}"});
    $("#applySubmitBtn").click(function(){$("#applyForm").submit();return false;});
    $("#applyForm").validate({
        submitHandler: function (form) {
            var $btn = $("#applySubmitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        goto_next("${param.gotoNext}");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>