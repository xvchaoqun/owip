<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${param.check==true?'审核':'退回待审核'}</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/pmMeeting2_check?check=${param.check}" autocomplete="off" disableautocomplete id="checkForm" method="post">
        <input type="hidden" name="ids" value="${param.ids}">

        <c:if test="${param.check}">
            <div class="form-group">
                <label class="col-xs-4 control-label"style="padding: 10px">是否通过</label>
                <div class="col-xs-8" style="padding: 10px">
                    <input type="checkbox" class="big" name="hasPass" checked/>
                </div>
            </div>
            <div class="form-group" id="reason" style="display: none">
                <label class="col-xs-4 control-label"style="padding: 10px">未通过原因</label>
                <div class="col-xs-6">
                    <textarea class="form-control limited" maxlength="200" name="reason">${pmMeeting2.reason}</textarea>
                </div>
            </div>
        </c:if>
        <c:if test="${!param.check}">
            <div class="form-group">
                <label class="col-xs-4 control-label">退回原因</label>
                <div class="col-xs-6">
                    <textarea class="form-control limited" type="text"
                              name="reason" maxlength="200"></textarea>
                </div>
            </div>
        </c:if>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="submitBtn" type="button" class="btn btn-primary" value="确定"/>
</div>

<script>
    $("#submitBtn").click(function(){$("#checkForm").submit();return false;});
    $("#checkForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#checkForm :checkbox").bootstrapSwitch();
    $('textarea.limited').inputlimiter();
    function hasPass(){
        if($("input[name=hasPass]").bootstrapSwitch("state")) {

            $("#reason").hide();
            //$("textarea[name=backReason]").val('').removeAttr("required");
            $("input[type=number]").removeAttr("disabled");
        }else {
            $("#reason").show();
            // $("textarea[name=backReason]").attr("required", "required");
            $("input[type=number]").attr("disabled", "disabled");
        }
    }
    $('input[name=hasPass]').on('switchChange.bootstrapSwitch', function(event, state) {
        hasPass();
    });
    hasPass();
</script>
