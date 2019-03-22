<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>补选课通知</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainCourse_applyMsg" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="trainCourseIds[]" value="${param["trainCourseIds[]"]}">
        <div class="form-group">
            <label class="col-xs-3 control-label">实践教学名称</label>
            <div class="col-xs-8 label-text">
                <c:forEach var="cetTrainCourse" items="${cetTrainCourses}" varStatus="vs">
                    ${cetTrainCourse.cetCourse.name}
                    <c:if test="${!vs.last}">
                        <br/>
                    </c:if>
                </c:forEach>
                <span class="help-block red">（注：* 选择多门课程时，发送范围为没有选其中任何一门课程的学员）</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">发送人数</label>
            <div class="col-xs-8 label-text">
                <c:set value="${fn:length(userIds)}" var="num"/>
                ${num} 人
                (
                <c:forEach items="${userIds}" var="userId" varStatus="vs" end="2">
                    ${cm:getUserById(userId).realname}
                    <c:if test="${!vs.last}">、</c:if>
                </c:forEach>
                <c:if test="${num>3}">
                    等
                </c:if>
                )
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">手机号码</label>
            <div class="col-xs-8">
                <input class="form-control mobile" type="text" name="mobile">
                <span class="help-block"><span class="star">*</span> 发送给指定手机号码，留空则发给全部未选课的培训对象</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>短信内容</label>
            <div class="col-xs-8">
                <textarea required class="form-control" type="text" name="msg" rows="8"></textarea>
                <span class="help-block"><span class="star">*</span> 短信内容可修改</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">是否添加后缀</label>
            <div class="col-xs-8">
                <input type="checkbox" class="big" name="addSuffix" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">后缀</label>
            <div class="col-xs-8">
                <input class="form-control" type="text" name="suffix" value="（系统短信，请勿直接回复）">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定发送"/>
</div>

<script>
    $("#modalForm :checkbox").bootstrapSwitch();
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        //$("#jqGrid").trigger("reloadGrid");
                        SysMsg.info("共发送{0}条短信，其中发送成功{1}条".format(ret.totalCount, ret.successCount))
                    }
                }
            });
        }
    });
    //$('textarea.limited').inputlimiter();
</script>