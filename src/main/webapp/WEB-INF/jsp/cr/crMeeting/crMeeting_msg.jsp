<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>招聘会通知</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crMeeting_msg" id="modalForm" method="post">
        <input type="hidden" name="meetingId" value="${param.meetingId}">
        <div class="form-group">
            <label class="col-xs-3 control-label">招聘会日期</label>
            <div class="col-xs-8 label-text">
                ${cm:formatDate(crMeeting.meetingDate, "yyyy.MM.dd")}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">选择消息模板</label>
            <div class="col-xs-8">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/shortMsgTpl_selects" data-width="503"
                        name="relateId" data-placeholder="请选择">
                    <option value=""></option>
                </select>
                <span class="help-block">从定向消息模板中获取通知内容</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>通知内容</label>
            <div class="col-xs-8">
                <textarea required class="form-control" name="msg" rows="12"></textarea>
            </div>
        </div>
            <div class="col-xs-12" style="max-height: 300px">
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th colspan="5">应聘人(总共${fn:length(crApplicants)}人)</th>
                    </tr>
                    <tr>
                        <th>工号</th>
                        <th>姓名</th>
                        <th>手机号码</th>
                        <th>第一志愿</th>
                        <th>第二志愿</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${crApplicants}" var="crApplicant">
                        <c:set var="sysUser" value="${crApplicant.user}"/>
                        <tr>
                            <td>${sysUser.code}</td>
                            <td>${sysUser.realname}</td>
                            <td>${sysUser.mobile}</td>
                            <td>${postMap.get(crApplicant.firstPostId).name}</td>
                            <td>${postMap.get(crApplicant.secondPostId).name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button type="button" id="submitBtn" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 发送中，请稍后"
            data-success-text="发送成功" autocomplete="off">
        确定发送
    </button>
</div>
<script>
    $.register.ajax_select($('#modalForm select[name=relateId]'));
    $('#modalForm select[name=relateId]').change(function () {
        if ($(this).val() > 0) {
            var content = $(this).select2("data")[0]['content'] || '';
            $("#modalForm textarea[name=msg]").val(content);
        }
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
                        $btn.button("success").addClass("btn-success");

                        $("#modal").modal('hide');
                        //$("#jqGrid").trigger("reloadGrid");
                        SysMsg.info("共发送{0}条通知，其中发送成功{1}条".format(ret.totalCount, ret.successCount))
                    } else {
                        $btn.button('reset');
                    }
                }
            });
        }
    });
</script>