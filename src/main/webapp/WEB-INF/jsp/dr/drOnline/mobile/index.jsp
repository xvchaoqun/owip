<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['dr_site_name']}" var="_p_drSiteName"/>
<c:set var="RESULT_STATUS_AGREE" value="<%=DrConstants.RESULT_STATUS_AGREE%>"/>
<c:set var="RESULT_STATUS_DISAGREE" value="<%=DrConstants.RESULT_STATUS_DISAGREE%>"/>
<c:set var="RESULT_STATUS_ABSTAIN" value="<%=DrConstants.RESULT_STATUS_ABSTAIN%>"/>
<!DOCTYPE html>
<html>
<head>
    <title>${_p_drSiteName}</title>
    <jsp:include page="/WEB-INF/jsp/common/m_head.jsp"></jsp:include>
    <style>
        #candidateForm .table td, #candidateForm .table th {
            vertical-align: middle;
        }
        #candidateForm td.post-name {
            text-align: left !important;
            font-weight: bolder;
            font-size: larger;
            background-color: #d9edf7;
        }
        #candidateForm td.realname {
            text-align: center;
            font-weight: bolder;
        }

        #candidateForm tr.other .realname {
            font-weight: inherit;
        }
    </style>
</head>
<body class="no-skin">
<!-- #section:basics/navbar.layout -->
<div id="navbar" class="navbar navbar-default">
    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="javascript:;" class="navbar-brand">
                <span style="font-size: 16px; font-weight: bold"><i class="ace-icon fa fa-signal"></i> ${_p_drSiteName}</span>
            </a>
        </div>
        <div class="navbar-buttons navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue">
                    <a data-toggle="dropdown" href="javascript:;" class="dropdown-toggle">
                        <img class="nav-user-photo" src="${ctx}/extend/img/default.png" width="90" alt="头像"/>
                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>
                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <c:if test="${tempResult.agree}">
                        <li style="margin-bottom: 5px">
                            <a href="${ctx}/user/dr/index?isMobile=1&notice=1"><i
                                    class="ace-icon fa fa-question-circle"></i> 推荐说明</a>
                        </li>
                        </c:if>
                        <li style="margin-bottom: 5px">
                            <a href="javascript:;" onclick="_logout()">
                                <i class="ace-icon fa fa-power-off"></i> 安全退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div><!-- /.navbar-container -->
</div>
<div class="main-container" id="main-container">
    <div class="main-content">
        <div class="main-content-inner">

            <div class="page-content" id="page-content">
                <c:if test="${param.notice==1 || !tempResult.agree}">
                    <form id="agreeForm" method="post">
                        <div class="modal-body" style="align: left;word-wrap:break-word">
                                ${cm:htmlUnescape(drOnline.mobileNotice)}
                        </div>
                        <div class="span12"
                             style="margin-top: 10px;font:bold 20px Verdana, Arial, Helvetica, sans-serif;">
                            <center>
                                <c:if test="${param.notice==1}">
                                <a class="btn btn-primary btn-lg" id="enterBtn" href="${ctx}/user/dr/index?isMobile=1"
                                        type="button"><i class="fa fa-hand-o-right"></i> 返回推荐页面
                                </a>
                                </c:if>
                                <c:if test="${param.notice!=1}">
                                    <div style="margin-bottom: 15px">
                                <input type="checkbox" id="agree" name="agree"
                                       style="width: 17px; height: 17px;vertical-align: text-after-edge;">
                                我确认已阅读推荐说明
                                        </div>
                                <button class="btn btn-success btn-lg" id="enterBtn" onclick="_confirm()"
                                        type="button"><i class="fa fa-hand-o-right"></i> 进入推荐页面
                                </button>
                                </c:if>
                            </center>
                        </div>
                    </form>
                </c:if>
                <c:if test="${param.notice!=1 && tempResult.agree}">
                    <div class="alert alert-block alert-success bolder" style="margin-bottom: 5px;">
                        ${drOnline.name}
                    </div>
                    <form id="candidateForm" method="post" action="${ctx}/user/dr/submit">
                        <input type="hidden" name="isMobile" value="1">
                        <input type="hidden" name="isSubmit" value="0">
                        <table class="table table-bordered">
                            <tbody>
                            <c:forEach items="${posts}" var="post">
                                <c:set var="realnameSet" value="${tempResult.realnameSetMap.get(post.id)}"/>
                                <tr>
                                    <td colspan="2" class="post-name">
                                            ${post.name}<c:if test="${post.minCount>0}"><span style="font-size: smaller">（最少推荐人数：${post.minCount}人）</span></c:if>
                                    </td>
                                </tr>
                                <c:set var="candidates" value="${candidateMap.get(post.id)}"/>
                                <c:set var="candidateNum" value="${fn:length(candidates)}"/>
                                <c:forEach items="${candidates}" var="candidate">
                                    <c:set var="key" value="${post.id}_${candidate.userId}"/>
                                    <c:set var="status" value="${tempResult.candidateMap.get(key)}"/>
                                    <c:set var="realname" value="${tempResult.otherMap.get(key)}"/>
                                    <tr class="candidate">
                                        <td class="realname">${candidate.realname}</td>
                                        <td>
                                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                <input postId="${post.id}" type="radio" name="${key}"
                                                       id="${key}_1"
                                                       value="${RESULT_STATUS_AGREE}" ${status==RESULT_STATUS_AGREE?"checked":""}>
                                                <label for="${key}_1">同意</label>
                                            </div>
                                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                <input postId="${post.id}" type="radio" name="${key}"
                                                       id="${key}_2"
                                                       value="${RESULT_STATUS_DISAGREE}" ${status==RESULT_STATUS_DISAGREE?"checked":""}>
                                                <label for="${key}_2">不同意</label>
                                            </div>
                                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                <input postId="${post.id}" type="radio" name="${key}"
                                                       id="${key}_3"
                                                       value="${RESULT_STATUS_ABSTAIN}" ${status==RESULT_STATUS_ABSTAIN?"checked":""}>
                                                <label for="${key}_3">弃权</label>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr class="other" style="display: ${status>1?'':'none'}" data-candidate="${key}">
                                        <td class="realname">另选推荐人</td>
                                        <td>
                                            <input name="${key}_realname" type="text" value="${realname}" maxlength="10">
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:forEach begin="${candidateNum+1}" end="${post.headCount}" var="idx">
                                    <tr>
                                        <td class="realname">推荐人${idx}</td>
                                        <td>
                                            <input name="${post.id}_realname_${idx}" type="text"  maxlength="10"
                                                   value="${cm:getSetValue(realnameSet, idx-candidateNum-1)}">
                                            <%--<div style="font-size: smaller">注：请填写一人，多填无效，下同。</div>--%>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                            </tbody>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="btn btn-primary" type="button"
                                            onclick="_save()"><i class="fa fa-save"></i> 暂存
                                    </button>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button class="btn btn-success" id="tempSubmit"
                                            type="button"
                                            onclick="_submit()"><i class="fa fa-check"></i> 提交
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</div>

<a href="javascript:;" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
    <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
</a>

<div id="modal" class="modal fade">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

        </div>
    </div>
</div>
<script type="text/javascript">

     $("input[type=radio]").click(function () {
        var $otherTr = $("tr[data-candidate='" + $(this).attr("name") + "']");
        if ($(this).val() == ${RESULT_STATUS_AGREE}) {
            $otherTr.hide();
        } else {
            $otherTr.show();
        }
    })

    function _confirm() {
        if ($('#agree').is(':checked') == false) {
            $('#agree').qtip({content: '请您确认您已阅读推荐说明！', show: true});
            return false;
        }
        $("#agreeForm").ajaxSubmit({
            url: "${ctx}/user/dr/agree?isMobile=1",
            success: function (data) {
                if (data.success) {
                    //console.log(data)
                    location.reload();
                }
            }
        });
    }

    $("#candidateForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        if ($("input[name=isSubmit]").val() == 0) {
                            SysMsg.success('保存成功。', '成功')
                        } else {
                            bootbox.alert({
                                closeButton: false,
                                buttons: {
                                    ok: {
                                        label: '确定',
                                        className: 'btn-success'
                                    }
                                },
                                message: '<span style="font-size: 16pt;font-weight: bolder;padding:10px">您已完成此次民主推荐，感谢您对工作的大力支持！<span>',
                                callback: function () {
                                    _logout();
                                }
                            });
                        }
                    }
                }
            });
        }
    });

    //保存
    function _save() {
        $("input[name=isSubmit]").val(0);
        $("#candidateForm").submit();
        return false;
    }

    //提交推荐数据
    function _submit() {

        bootbox.confirm('<div style="font-size: 16pt;font-weight: bolder;color:red;margin:10px;">\
            <div style="text-indent:2em;margin:50px 10px 10px 10px;">提交之前，请您确认推荐结果无需再做修改。</div>\
            <div style="text-indent:2em;padding:10px;">为保证信息的安全，在点击确定提交成功后您的账号、密码立即失效。<div></div>', function (result) {
            if (result) {
                $("input[name=isSubmit]").val(1);
                $("#candidateForm").submit();
                $(this).modal("hide");
                return false;
            } else {
                $(this).modal("hide");
            }
        })
    }

    function _logout() {
        location.href = "${ctx}/user/dr/logout?isMobile=1";
    }
</script>
</body>
</html>
