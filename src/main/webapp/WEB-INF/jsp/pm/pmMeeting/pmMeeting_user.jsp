<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${param.type==1?'参会':'请假'}人员</h3>
</div>
<div class="modal-body"style="overflow:auto">
        <table class="table table-actived table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th width="30%">工作证号</th>
                <th>党员姓名</th>
                <th>党内职务</th>
                <th>手机号</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${param.type==1?pmMeeting.attendList:pmMeeting.absentList}" var="user" varStatus="st">
                <tr>
                    <td nowrap >${user.code}</td>
                    <td nowrap >${user.realname}</td>
                    <td nowrap >${user.partyPost}</td>
                    <td nowrap >${user.mobile}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
</div>
<script>
    function _pop_reload(){
        pop_reload();
        $(window).triggerHandler('resize.jqGrid');
    }
    $("#submitBtn", "#modalForm").click(function () {
        $("#modalForm").submit();
        return false;
    })
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        _pop_reload();
                    }
                }
            });
        }
    });
</script>