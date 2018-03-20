<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="courseNum" value="${fn:length(cetTrainCourses)}"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>[${cetTrain.name}] 评课账号</h3>
</div>
<div class="modal-body">
    <table class="table table-actived  table-striped table-bordered table-center">
        <thead>
        <tr>
            <th width="50">序号</th>
            <c:if test="${cetTrain.evaAnonymous}">
            <th width="80">账号</th>
                <th width="50">密码</th>
            </c:if>
            <c:if test="${!cetTrain.evaAnonymous}">
                <th width="100">手机号</th>
                <th width="80">姓名</th>
            </c:if>
                <%--<th width="50">生成方式</th>--%>

            <th width="100">测评状态</th>
            <th width="200">已完成/暂存/课程总数</th>

                <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${inspectors}" var="inspector" varStatus="vs">
            <tr>
                <td>${vs.index+1+commonList.pageSize*(commonList.pageNo-1)}</td>
                <c:if test="${cetTrain.evaAnonymous}">
                <td>${inspector.username}</td>
                    <td class="${inspector.passwdChangeType==CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET?'text-danger':''}"
                        title="${inspector.passwdChangeType==CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET?'管理员重置了密码':''}
                        ${inspector.passwdChangeType==CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF?'本人修改了密码':''}">
                        <c:if test="${inspector.passwdChangeType!=CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF}">
                            ${inspector.passwd}
                        </c:if>
                        <c:if test="${inspector.passwdChangeType==CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF}">
                            ******
                        </c:if>
                    </td>
                </c:if>
                <c:if test="${!cetTrain.evaAnonymous}">
                    <td>${inspector.mobile}</td>
                    <td>${inspector.realname}</td>
                </c:if>

                <%--<td>${inspector.type==1?"列表生成":(inspector.type==2?"个别生成":"")}</td>--%>

                <td>
                    <c:set var="labelTxt" value="${CET_TRAIN_INSPECTOR_STATUS_MAP.get(inspector.status)}"/>
                        <c:choose>
                            <c:when test="${inspector.status==CET_TRAIN_INSPECTOR_STATUS_INIT}">
                                <c:set var="labelCss" value="label-default"/>
                                <c:if test="${inspector.finishCourseNum==courseNum}">
                                    <c:set var="labelCss" value="label-success"/>
                                    <c:set var="labelTxt" value="已完成"/>
                                </c:if>
                                <c:if test="${inspector.finishCourseNum>0 && inspector.finishCourseNum < courseNum}">
                                    <c:set var="labelCss" value="label-info"/>
                                    <c:set var="labelTxt" value="部分完成"/>
                                </c:if>
                            </c:when>
                            <c:when test="${inspector.status==CET_TRAIN_INSPECTOR_STATUS_ABOLISH}">
                                <c:set var="labelCss" value="label-danger"/>
                            </c:when>
                        </c:choose>
                        <span class="label ${labelCss}">${labelTxt}</span>
                </td>
                    <td>${inspector.finishCourseNum}/${inspector.saveCourseNum}/${courseNum}</td>
                    <td nowrap="">
                        <c:if test="${cetTrain.evaAnonymous && inspector.passwdChangeType==CET_TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF &&
                        inspector.status!=CET_TRAIN_INSPECTOR_STATUS_ABOLISH}">
                            <button onclick="passwdReset(${inspector.id}, this)" class="btn btn-xs btn-primary"><i
                                    class="icon-white fa fa-refresh"></i> 重置密码
                            </button>
                        </c:if>
                        <c:if test="${inspector.status!=CET_TRAIN_INSPECTOR_STATUS_ABOLISH}">
                            <button onclick="abolish(${inspector.id}, this)" class="btn btn-xs btn-warning"><i
                                    class="icon-white fa fa-remove"></i> 作废
                            </button>
                        </c:if>
                        <c:if test="${inspector.status==CET_TRAIN_INSPECTOR_STATUS_ABOLISH}">
                            <button onclick="_delAbolished(${inspector.id}, this)" class="btn btn-xs btn-danger"><i
                                    class="icon-white fa fa-remove"></i> 删除
                            </button>
                        </c:if>

                    </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <wo:page commonList="${commonList}" uri="${ctx}/cet/cetTrainInspector_list" target="#modal .modal-content" pageNum="5"
             model="3"/>
</div>

<script>

    function _delAbolished(inspectorId, btn) {
        $(btn).closest("td").click();
        if (confirm("确定删除该账号？")) {
            $.post("${ctx}/cet/cetTrainInspector_delAbolished", {id: inspectorId}, function () {

                $("#modal .modal-content").load("${ctx}/cet/cetTrainInspector_list?${pageContext.request.queryString}")
                //toastr.success('操作成功。', '成功');
                $("#jqGrid").trigger("reloadGrid");
            });
        }
    }
    function abolish(inspectorId, btn) {
        $(btn).closest("td").click();
        if (confirm("确定将该账号作废？（该账号下的所有数据均删除！）")) {
            $.post("${ctx}/cet/cetTrainInspector_abolish", {id: inspectorId}, function () {

                $("#modal .modal-content").load("${ctx}/cet/cetTrainInspector_list?${pageContext.request.queryString}")
                //toastr.success('操作成功。', '成功');
            });
        }
    }

    function passwdReset(inspectorId, btn) {
        $(btn).closest("td").click();
        if (confirm("确定重置账号密码？（重置之后，单位管理员不可看到该密码）")) {
            $.post("${ctx}/cet/cetTrainInspector_password_reset", {id: inspectorId}, function () {

                $("#modal .modal-content").load("${ctx}/cet/cetTrainInspector_list?${pageContext.request.queryString}")
                //toastr.success('操作成功。', '成功');
            });
        }
    }
</script>