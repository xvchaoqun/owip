<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['pcs_poll_site_name']}" var="_p_pcsSiteName"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_PR%>" var="PCS_POLL_CANDIDATE_PR"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_DW%>" var="PCS_POLL_CANDIDATE_DW"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_JW%>" var="PCS_POLL_CANDIDATE_JW"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_TYPE%>" var="PCS_POLL_CANDIDATE_TYPE"/>
<c:set var="RESULT_STATUS_AGREE" value="<%=PcsConstants.RESULT_STATUS_AGREE%>"/>
<c:set var="RESULT_STATUS_DISAGREE" value="<%=PcsConstants.RESULT_STATUS_DISAGREE%>"/>
<c:set var="RESULT_STATUS_ABSTAIN" value="<%=PcsConstants.RESULT_STATUS_ABSTAIN%>"/>
<!DOCTYPE html>
<html>
<head>
    <title>${_p_pcsSiteName}</title>
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
                <span style="font-size: 16px; font-weight: bold"><i class="ace-icon fa fa-signal"></i> ${_p_pcsSiteName}</span>
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
                                <a href="${ctx}/user/pcs/index?isMobile=1&notice=1"><i
                                        class="ace-icon fa fa-question-circle"></i> 推荐说明</a>
                            </li>
                            <li>
                                <a href="javascript:;<%--${ctx}/user/pcs/index?type=1--%>" onclick="_save(1)"><i
                                        class="ace-icon fa fa-user"></i> 推荐代表</a>
                            </li>
                            <li>
                                <a href="javascript:;<%--${ctx}/user/pcs/index?type=2--%>" onclick="_save(2)"><i
                                        class="ace-icon fa fa fa-user"></i> 推荐党委委员</a>
                            </li>
                            <li>
                                <a href="javascript:;<%--${ctx}/user/pcs/index?type=3--%>" onclick="_save(3)"><i
                                        class="ace-icon fa fa fa-user"></i> 推荐纪委委员</a>
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
                                ${cm:htmlUnescape(pcsPoll.mobileNotice)}
                        </div>
                        <div class="span12"
                             style="margin-top: 10px;font:bold 20px Verdana, Arial, Helvetica, sans-serif;">
                            <center>
                                <c:if test="${param.notice==1}">
                                <a class="btn btn-primary btn-lg" id="enterBtn" href="${ctx}/user/pcs/index?isMobile=1"
                                        type="button"><i class="fa fa-hand-o-right"></i> 返回投票页面
                                </a>
                                </c:if>
                                <c:if test="${param.notice!=1}">
                                    <div style="margin-bottom: 15px">
                                <input type="checkbox" id="agree" name="agree"
                                       style="width: 17px; height: 17px;vertical-align: text-after-edge;">
                                我确认已阅读推荐说明
                                        </div>
                                <button class="btn btn-success btn-lg" id="enterBtn" onclick="_confirm()"
                                        type="button"><i class="fa fa-hand-o-right"></i> 进入投票页面
                                </button>
                                </c:if>
                            </center>
                        </div>
                    </form>
                </c:if>
                <c:if test="${param.notice!=1 && tempResult.agree}">
                    <div class="alert alert-block alert-success bolder" style="margin-bottom: 5px;">
                        ${pcsPoll.name}（${PCS_POLL_CANDIDATE_TYPE.get(type)}）
                    </div>
                    <form id="candidateForm" method="post" action="${ctx}/user/pcs/submit">
                        <input type="hidden" name="flag" value="0">
                        <input type="hidden" name="isMobile" value="1">
                        <input type="hidden" name="isSubmit" value="0">
                        <input type="hidden" name="type" value="${type}">
                        <table class="table table-bordered">
                            <tbody>
                                <tr>
                                    <td class="realname">投票人身份</td>
                                </tr>
                                <tr>
                                    <td align="center">
                                            <input type="radio" name="isPositive" id="isPositive_1" value="1" ${inspector.isPositive?"checked":""}>
                                            <label for="isPositive_1">正式党员</label>
                                            &nbsp;&nbsp;
                                            <input type="radio" name="isPositive" id="isPositive_0" value="0" ${empty inspector.isPositive?"":(inspector.isPositive?"":"checked")}>
                                            <label for="isPositive_0">预备党员</label>
                                    </td>
                                </tr>
                                <c:forEach items="${cans}" var="can">
                                    <c:set var="key" value="${type}_${can.userId}"/>
                                    <c:set var="status" value="${tempResult.secondResultMap.get(key)}"/>
                                    <c:set var="otherKey" value="${key}_4"/>
                                    <c:set var="otherUser" value="${tempResult.otherResultMap.get(otherKey)}"/>
                                    <tr>
                                        <td class="realname">${can.user.realname}（${can.user.code}）</td>
                                    </tr>
                                    <tr class="candidate">
                                        <td align="center">
                                            <input postId="${post.id}" type="radio" name="${key}"
                                                   id="${key}_1"
                                                   value="${RESULT_STATUS_AGREE}" ${status==RESULT_STATUS_AGREE?"checked":""}>
                                            <label for="${key}_1">同意</label>&nbsp;&nbsp;
                                            <input postId="${post.id}" type="radio" name="${key}"
                                                   id="${key}_2"
                                                   value="${RESULT_STATUS_DISAGREE}" ${status==RESULT_STATUS_DISAGREE?"checked":""}>
                                            <label for="${key}_2">不同意</label>&nbsp;&nbsp;
                                            <input postId="${post.id}" type="radio" name="${key}"
                                                   id="${key}_3"
                                                   value="${RESULT_STATUS_ABSTAIN}" ${status==RESULT_STATUS_ABSTAIN?"checked":""}>
                                            <label for="${key}_3">弃权</label>&nbsp;&nbsp;
                                        </td>
                                    </tr>
                                    <tr class="other" style="display: ${status==2?'':'none'}" data-candidate="${key}">
                                        <td class="realname">另选其他推荐人</td>
                                    </tr>
                                    <tr class="other" style="display: ${status==2?'':'none'}" data-candidate="${key}">
                                        <td>
                                            <select data-rel="select2-ajax" data-width="272" data-ajax-url="${ctx}/user/pcs/candidate_selects?pollId=${pcsPoll.id}&type=${type}&isSecond=1"
                                                    name="${key}_4" data-placeholder="请输入账号或姓名或学工号">
                                                <option value="${otherUser.id}">${otherUser.realname}-${otherUser.code}</option>
                                            </select>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tr>
                                <td colspan="2" style="text-align: center">
                                    <button class="btn btn-primary" type="button"
                                            onclick="_save(0)"><i class="fa fa-save"></i> 暂存
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

     $(".candidate input[type=radio]").click(function () {
        var $otherTr = $("tr[data-candidate='" + $(this).attr("name") + "']");
        if ($(this).val() == ${RESULT_STATUS_AGREE} || $(this).val() == ${RESULT_STATUS_ABSTAIN}) {
            $otherTr.hide();
        } else {
            $otherTr.show();
        }
    })
     $.register.user_select($('[data-rel="select2-ajax"]'));

    function _confirm() {
        if ($('#agree').is(':checked') == false) {
            $('#agree').qtip({content: '请您确认您已阅读推荐说明！', show: true});
            return false;
        }
        $("#agreeForm").ajaxSubmit({
            url: "${ctx}/user/pcs/agree?isMobile=1",
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
                        var type = $("input[name=flag]").val();
                        if ($("input[name=flag]").val() != 0) {
                            location.href="${ctx}/user/pcs/index?isMobile=1&type="+type;
                        }else if ($("input[name=isSubmit]").val() == 0) {
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
                                message: '<span style="font-size: 16pt;font-weight: bolder;padding:10px">您已完成此次党代会投票，感谢您对工作的大力支持！<span>',
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
    function _save(flag) {
        $("input[name=flag]").val(flag);//0保存按钮保存
        $("input[name=isSubmit]").val(0);
        $("#candidateForm").submit();
        return false;
    }

    //提交推荐数据
    function _submit() {

        bootbox.confirm('<div style="font-size: 16pt;font-weight: bolder;color:red;margin:10px;">\
            <div style="text-indent:2em;margin:50px 10px 10px 10px;">提交之前，请您确认投票结果无需再做修改。</div>\
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
        location.href = "${ctx}/user/pcs/logout?isMobile=1";
    }
</script>
</body>
</html>
