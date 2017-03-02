<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>[${train.name}] 评课账号</h3>
</div>
<div class="modal-body">
    <table class="table table-actived  table-striped table-bordered ">
        <thead>
        <tr>
            <th width="20">序号</th>
            <c:if test="${train.isAnonymous}">
            <th width="50">账号</th>
                <th width="50">密码</th>
            </c:if>
            <c:if test="${!train.isAnonymous}">
                <th width="50">手机号</th>
                <th width="50">姓名</th>
            </c:if>
                <%--<th width="50">生成方式</th>--%>

            <th width="30">测评状态</th>

                <th style="width: 100px"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${inspectors}" var="inspector" varStatus="vs">
            <tr>
                <td>${vs.index+1+commonList.pageSize*(commonList.pageNo-1)}</td>
                <c:if test="${train.isAnonymous}">
                <td>${inspector.username}</td>
                    <td class="${inspector.passwdChangeType==TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET?'text-danger':''}"
                        title="${inspector.passwdChangeType==TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_ADMN_RESET?'管理员重置了密码':''}
                        ${inspector.passwdChangeType==TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF?'本人修改了密码':''}">
                        <c:if test="${inspector.passwdChangeType!=TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF}">
                            ${inspector.passwd}
                        </c:if>
                        <c:if test="${inspector.passwdChangeType==TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF}">
                            ******
                        </c:if>
                    </td>
                </c:if>
                <c:if test="${!train.isAnonymous}">
                    <td>${inspector.mobile}</td>
                    <td>${inspector.realname}</td>
                </c:if>

                <%--<td>${inspector.type==1?"列表生成":(inspector.type==2?"个别生成":"")}</td>--%>

                <td>
                        <c:choose>
                            <c:when test="${inspector.status==TRAIN_INSPECTOR_STATUS_INIT}">
                                <c:set var="labelCss" value="label-default"/>
                            </c:when>
                            <c:when test="${inspector.status==TRAIN_INSPECTOR_STATUS_ABOLISH}">
                                <c:set var="labelCss" value="label-danger"/>
                            </c:when>
                            <c:when test="${inspector.status==TRAIN_INSPECTOR_STATUS_ALL_FINISH}">
                                <c:set var="labelCss" value="label-success"/>
                            </c:when>
                            <c:when test="${inspector.status==TRAIN_INSPECTOR_STATUS_PART_FINISH}">
                                <c:set var="labelCss" value="label-info"/>
                            </c:when>
                        </c:choose>
                        <span class="label ${labelCss}">${TRAIN_INSPECTOR_STATUS_MAP.get(inspector.status)}
                            <c:if test="${inspector.status==TRAIN_INSPECTOR_STATUS_PART_FINISH
                            || inspector.status==TRAIN_INSPECTOR_STATUS_ALL_FINISH}">
                                （${inspector.finishCourseNum}/${fn:length(trainCourses)}）
                                 </c:if>

                        </span>
                </td>

                    <td nowrap="">
                        <c:if test="${train.isAnonymous && inspector.passwdChangeType==TRAIN_INSPECTOR_PASSWD_CHANGE_TYPE_SELF &&
                        inspector.status!=TRAIN_INSPECTOR_STATUS_ABOLISH}">
                            <button onclick="passwdReset(${inspector.id}, this)" class="btn btn-xs btn-primary"><i
                                    class="icon-white fa fa-refresh"></i> 重置密码
                            </button>
                        </c:if>
                        <c:if test="${inspector.status!=TRAIN_INSPECTOR_STATUS_ABOLISH}">
                            <button onclick="abolish(${inspector.id}, this)" class="btn btn-xs btn-warning"><i
                                    class="icon-white fa fa-remove"></i> 作废
                            </button>
                        </c:if>
                        <c:if test="${inspector.status==TRAIN_INSPECTOR_STATUS_ABOLISH}">
                            <button onclick="_delAbolished(${inspector.id}, this)" class="btn btn-xs btn-danger"><i
                                    class="icon-white fa fa-remove"></i> 删除
                            </button>
                        </c:if>

                    </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <wo:page commonList="${commonList}" uri="${ctx}/trainInspector_list" target="#modal .modal-content" pageNum="5"
             model="3"/>
</div>

<script>

    function _delAbolished(inspectorId, btn) {
        $(btn).closest("td").click();
        if (confirm("确定删除该账号？")) {
            $.post("${ctx}/trainInspector_delAbolished", {id: inspectorId}, function () {

                $("#modal .modal-content").load("${ctx}/trainInspector_list?${pageContext.request.queryString}")
                //toastr.success('操作成功。', '成功');
            });
        }
    }
    function abolish(inspectorId, btn) {
        $(btn).closest("td").click();
        if (confirm("确定将该账号作废？（该账号下的所有数据均删除！）")) {
            $.post("${ctx}/trainInspector_abolish", {id: inspectorId}, function () {

                $("#modal .modal-content").load("${ctx}/trainInspector_list?${pageContext.request.queryString}")
                //toastr.success('操作成功。', '成功');
            });
        }
    }

    function passwdReset(inspectorId, btn) {
        $(btn).closest("td").click();
        if (confirm("确定重置账号密码？（重置之后，单位管理员不可看到该密码）")) {
            $.post("${ctx}/trainInspector_password_reset", {id: inspectorId}, function () {

                $("#modal .modal-content").load("${ctx}/trainInspector_list?${pageContext.request.queryString}")
                //toastr.success('操作成功。', '成功');
            });
        }
    }
</script>