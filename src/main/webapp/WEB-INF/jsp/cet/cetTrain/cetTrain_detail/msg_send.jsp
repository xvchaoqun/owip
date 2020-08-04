<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${name}</h3>
</div>
<div class="modal-body">
    <div class="alert alert-info">
        ${content}
    </div>
    <div>
        <form autocomplete="off" disableautocomplete id="modalForm" class="form-horizontal" action="${ctx}/cet/cetTrain_detail/msg_send" method="post">
            <input type="hidden" name="projectId" value="${param.projectId}">
            <input type="hidden" name="trainId" value="${param.trainId}">
            <input type="hidden" name="tplKey" value="${param.tplKey}">
            <div class="form-group">
                <label class="col-xs-3 control-label">指定接收手机号</label>
                <div class="col-xs-6">
                    <input type="text" class="mobile" name="mobile" id="mobile">
                    <span class="help-block">（如果填写了，则只给该手机号发送短信）</span>
                </div>

            </div>
        </form>
    </div>
</div>
<c:if test="${param.tplKey=='ct_cet_msg_1'}">
    <div class="modal-footer">
        <div class="red text-left padding4">注：
            <ul>
                <li>
                    只有已启动且已发布的培训班允许发送开班提醒短信；
                </li>
                <li>
                    此功能开班前一天点击才有效；
                </li>
                <li>
                    批量发送有一定的延迟，请勿重复点击发送。
                </li>
            </ul>
        </div>
        <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
        <c:set var="canSend" value="${cm:compareDate(cetProject.openTime, now)}"/>
        <button id="submitBtn" ${canSend?'':'disabled'}
                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 发送中，请不要关闭此窗口"
                class="btn btn-primary">确定发送
        </button>
    </div>
</c:if>
<c:if test="${param.tplKey=='ct_cet_msg_2'}">
    <div class="modal-footer">
        <div class="red text-left padding4">注：
            <ul>
                <li>
                    包含课程：<br/>
                    <c:forEach items="${todayTrainCourseList}" var="tc">
                        《${tc.name}》, ${cm:formatDate(tc.startTime, "yyyy-MM-dd HH:mm")} ~ ${cm:formatDate(tc.endTime, "yyyy-MM-dd HH:mm")}
                        <br/>
                    </c:forEach>
                </li>
                <li>
                    只会给当天还未开始上课的课程发送短信；
                </li>
                <li>
                    只有未签到的学员能收到短信
                </li>
                <li>
                    批量发送有一定的延迟，请勿重复点击发送。
                </li>
            </ul>
        </div>
        <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
        <button id="submitBtn" ${(fn:length(todayTrainCourseList)>0)?'':'disabled'}
                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 发送中，请不要关闭此窗口"
                class="btn btn-primary">确定发送
        </button>
    </div>
</c:if>
<script>
    $("#submitBtn").click(function () { $("#modalForm").submit(); return false;  })
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        SysMsg.info("成功发送{0}条短信。".format(ret.successCount));
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>