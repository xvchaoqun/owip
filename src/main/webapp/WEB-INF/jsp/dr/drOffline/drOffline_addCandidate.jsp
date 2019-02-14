<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<form class="form-horizontal" action="${ctx}/drOffline_addCandidate"
      id="voteForm" method="post">
    <input type="hidden" name="id" value="${candidate.id}">
    <input type="hidden" name="offlineId" value="${drOffline.id}">
    <input type="hidden" name="tplId" value="${drOffline.voterTypeTplId}">
    <table class="table table-striped table-bordered
                    table-condensed table-center table-unhover2">
        <thead>
        <tr>
            <th>推荐人选</th>
            <th>工号</th>
            <th style="width: 95px">得票总数</th>
            <c:if test="${drOffline.needVoterType}">
                <c:forEach items="${typeMap}" var="entity">
                    <th style="width: 70px">${entity.value.name}</th>
                </c:forEach>
            </c:if>
            <th style="width: 100px;">
                <c:if test="${fn:length(candidates)>1}">
                    <button class="confirm btn btn-danger btn-xs"
                            data-title="清空" type="button"
                            data-msg="确定清空全部候选人？"
                            data-callback="_reloadCandidates"
                            data-url="${ctx}/drOffline_delCandidate?offlineId=${drOffline.id}">
                        <i class="fa fa-trash"></i> 清空
                    </button>
                </c:if>
            </th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="2" class="bg-center">
                <select required data-rel="select2-ajax"
                        data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                        data-width="220"
                        name="userId" data-placeholder="请输入账号或姓名或工号">
                    <option value="${candidate.user.id}">${candidate.user.realname}-${candidate.user.code}</option>
                </select>
            </td>

            <c:if test="${drOffline.needVoterType}">
                <td class="bg-center">${candidate.vote}</td>
                <c:forEach items="${typeMap}" var="entity">
                    <td class="bg-center">
                        <input required class="form-control digits"
                               data-at="bottom center" data-my="top center" data-rule-max="${voterMap.get(entity.key)}"
                               style="width: 50px;margin: 0 auto;" maxlength="4"
                               type="text" name="type_${entity.key}" value="${candidateVoterMap.get(entity.key)}">
                    </td>
                </c:forEach>
            </c:if>
            <c:if test="${!drOffline.needVoterType}">
                <td>
                    <input required class="form-control digits"
                           data-at="bottom center" data-my="top center"
                           data-rule-max="${drOffline.ballot-drOffline.invalid}"
                           style="width: 50px;margin: 0 auto;" maxlength="4"
                           type="text" name="vote" value="${candidate.vote}">
                </td>
            </c:if>
            <td class="bg-center">
                <c:if test="${empty candidate}">
                    <button id="voteBtn" type="button" data-loading-text="<i class='fa fa-spinner fa-spin '></i>"
                            class="btn btn-success btn-sm">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                </c:if>
                <c:if test="${not empty candidate}">
                    <button id="voteBtn" type="button" data-loading-text="<i class='fa fa-spinner fa-spin '></i>"
                            class="btn btn-success btn-xs">
                        <i class="fa fa-check-square-o"></i>
                    </button>
                    <button type="button"
                            data-target="#candidatesDiv"
                            data-url="${ctx}/drOffline_addCandidate?offlineId=${drOffline.id}"
                            class="reloadBtn btn btn-primary btn-xs">
                        <i class="fa fa-reply"></i>
                    </button>
                </c:if>
            </td>
        </tr>
        <c:forEach items="${candidates}" var="candidate">
            <tr>
                <td style="width: 100px">${candidate.user.realname}</td>
                <td>${candidate.user.code}</td>
                <td>${candidate.vote}</td>
                <c:if test="${drOffline.needVoterType}">
                    <c:forEach items="${typeMap}" var="entity">
                        <td>
                                ${candidate.voterMap.get(entity.key)}
                        </td>
                    </c:forEach>
                </c:if>

                <td>
                    <button class="reloadBtn btn btn-primary btn-xs"
                            data-target="#candidatesDiv" type="button"
                            data-url="${ctx}/drOffline_addCandidate?offlineId=${drOffline.id}&candidateId=${candidate.id}">
                        <i class="fa fa-edit"></i>
                    </button>
                    <button class="confirm btn btn-danger btn-xs"
                            data-title="删除" type="button"
                            data-msg="确定删除【${candidate.user.realname}】？"
                            data-callback="_reloadCandidates"
                            data-url="${ctx}/drOffline_delCandidate?id=${candidate.id}">
                        <i class="fa fa-minus"></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${!empty commonList && commonList.pageNum>1 }">
        <wo:page commonList="${commonList}" uri="${ctx}/drOffline_addCandidate?offlineId=${drOffline.id}"
        target="#candidatesDiv" pageNum="5" model="3"/>
    </c:if>
</form>
<script>
    $.register.user_select($('#voteForm select[name=userId]'));

    function _reloadCandidates() {

        $("#candidatesDiv").load("${ctx}/drOffline_addCandidate?offlineId=${drOffline.id}")
    }

    <c:if test="${drOffline.needVoterType}">
    $("input", "#voteForm table tbody tr:first").on("keyup", function () {
        var total = 0;
        $.each($("input", "#voteForm table tbody tr:first"), function (input, i) {
            total += parseInt(Math.trimToZero($(this).val()));
        });
        $("td:eq(1)", "#voteForm table tbody tr:first").html(total)
    })
    </c:if>

    $("#voteBtn").click(function () {
        <c:if test="${drOffline.needVoterType}">
        if ($("#voteForm input[name=tplId]").val() == '') {
            SysMsg.warning("请先填写并保存民主推荐会基本信息。")
            return;
        }
        </c:if>
        $("#voteForm").submit();
        return false;
    });
    $("#voteForm").validate({
        submitHandler: function (form) {
            var $btn = $("#voteBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        _reloadCandidates()
                    }
                    $btn.button('reset');
                }
            });
        }
    });
</script>
