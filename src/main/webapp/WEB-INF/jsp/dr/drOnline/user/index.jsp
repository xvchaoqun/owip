<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="RESULT_STATUS_AGREE" value="<%=DrConstants.RESULT_STATUS_AGREE%>"/>
<c:set var="RESULT_STATUS_DISAGREE" value="<%=DrConstants.RESULT_STATUS_DISAGREE%>"/>
<c:set var="RESULT_STATUS_ABSTAIN" value="<%=DrConstants.RESULT_STATUS_ABSTAIN%>"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>线上民主推荐系统</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>

    <style>
        h1[id], h2[id] {
            padding-top: 95px;
            margin-top: -95px;
        }
        body{
            background-color: inherit;
        }

        .navbar-header .nav {
            top: 35px;
            position: relative;
        }

        .navbar-header .nav a {
            color: white;
        }

        .navbar-header .nav > li > a:hover {
            color: #0d43fa;
        }

        #candidateForm .table td, #candidateForm .table th {
            vertical-align: middle;
        }

        #candidateForm td.post-name {
            text-align: left !important;
            font-size: x-large;
            background-color: #d9edf7;
        }

        #candidateForm td.realname {
            text-align: center;
            font-weight: bolder;
            font-size: larger;
            width: 250px;
        }

        #candidateForm tr.other .realname {
            font-weight: inherit;

        }
    </style>
</head>
<body>
<div id="navbar" class="navbar navbar-default navbar-fixed-top">
    <div class="container" id="navbar-container">
        <div class="navbar-header" style="width: 100%">
            <div class="logo" style="cursor: pointer;" onclick="location.href='#'">
                <t:img src="/img/logo_white.png"/></div>
            <div class="separator"></div>
            <div class="txt" style="cursor: pointer;">线上民主推荐系统</div>

            <ul class="nav nav-pills pull-right">
                 <c:if test="${tempResult.agree}">
                <li>
                    <a href="${ctx}/user/dr/index?notice=1"><i
                            class="ace-icon fa fa-question-circle"></i> 推荐说明</a>
                </li>
                 </c:if>
                <li>

                    <a class="popupBtn" type="button" href="javascript:;" data-width="250" data-url="${ctx}/user/dr/inspector_changePasswd"><i class="ace-icon fa fa-key"></i>
                        修改密码</a>
                </li>
                <li>
                    <a href="javascript:;" onclick="_logout()"><i class="ace-icon fa fa-power-off"></i> 退出</a>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="container" style="margin-top: 100px">
    <div class="row">
        <c:if test="${not empty inspector}">
            <div class="header">
                <h4 align="center">
                    <strong>当前账号：</strong><a href="#" class="tag">${inspector.username}</a>&nbsp;
                    <strong>类别：</strong><a href="#" class="tag">${inspector.inspectorType.type}</a>&nbsp;
                </h4>
            </div>
        </c:if>
    </div>
    <div class="main-content eva">
        <c:if test="${param.notice==1 || !tempResult.agree}">
            <form id="agreeForm" method="post">
                <div style="width:70%; margin:0 auto;">

                    <div class="modal-body" style="text-align: left;word-wrap:break-word">
                            ${drOnline.notice}
                    </div>
                </div>

                <div class="span12" style="margin-top: 30px;font:bold 20px Verdana, Arial, Helvetica, sans-serif;">
                    <center>
                         <c:if test="${param.notice==1}">
                        <a class="btn btn-primary btn-lg" id="enterBtn" href="${ctx}/user/dr/index"
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
            <div class="alert alert-block alert-success center bolder"
                 style="width:800px;margin: 0 auto 5px;font-size: larger;">
                ${drOnline.name}
            </div>
            <form id="candidateForm" method="post" action="${ctx}/user/dr/doTempSave">
                <input type="hidden" name="isSubmit" value="0">
                <table class="table table-bordered table-unhover2" style="width:800px;margin: 0 auto;">
                    <tbody>
                    <c:forEach items="${postViews}" var="post">
                        <c:set var="realnameSet" value="${tempResult.realnameSetMap.get(post.id)}"/>
                        <tr>
                            <td colspan="2" class="post-name">${post.name}</td>
                        </tr>
                        <c:set var="candidates" value="${candidateMap.get(post.id)}"/>
                        <c:set var="candidateNum" value="${fn:length(candidates)}"/>
                        <c:forEach items="${candidates}" var="candidate">
                            <c:set var="key" value="${post.id}_${candidate.userId}"/>
                            <c:set var="status" value="${tempResult.candidateMap.get(key)}"/>
                            <c:set var="realname" value="${tempResult.otherMap.get(key)}"/>
                            <tr class="candidate">
                                <td class="realname"><span class="star">*</span> ${candidate.realname}</td>
                                <td>
                                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                        <input postId="${post.id}" type="radio" name="${key}"
                                               id="${key}_1" value="${RESULT_STATUS_AGREE}" ${status==RESULT_STATUS_AGREE?"checked":""}>
                                        <label for="${key}_1">同意</label>
                                    </div>
                                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                        <input postId="${post.id}" type="radio" name="${key}"
                                               id="${key}_2" value="${RESULT_STATUS_DISAGREE}" ${status==RESULT_STATUS_DISAGREE?"checked":""}>
                                        <label for="${key}_2">不同意</label>
                                    </div>
                                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                        <input postId="${post.id}" type="radio" name="${key}"
                                               id="${key}_3" value="${RESULT_STATUS_ABSTAIN}" ${status==RESULT_STATUS_ABSTAIN?"checked":""}>
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
                        <c:forEach begin="${candidateNum+1}" end="${post.competitiveNum}" var="idx">
                            <tr>
                                <td class="realname"><span class="star">*</span> 推荐人${idx}</td>
                                <td>
                                    <input name="${post.id}_realname_${idx}" type="text" maxlength="10"
                                           value="${cm:getSetValue(realnameSet, idx-candidateNum-1)}">
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <button class="btn btn-primary" type="button"
                                    onclick="doTempSave()"><i class="fa fa-save"></i> 暂存
                            </button>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <button class="btn btn-success" id="tempSubmit"
                                    type="button"
                                    onclick="doTempSubmit()"><i class="fa fa-check"></i> 提交
                            </button>
                        </td>
                    </tr>
                     </tbody>
                </table>
            </form>
        </c:if>
    </div>
</div>
<div id="modal" class="modal fade">
    <div class="modal-dialog" role="document" <%--style="min-width: 650px;"--%>>
        <div class="modal-content">
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/scripts.jsp"></jsp:include>
<script>

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
            $('#agree').qtip({content: '请您确认您已阅读说明。', show: true});
            return false;
        }
        $("#agreeForm").ajaxSubmit({
            url: "${ctx}/user/dr/agree",
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
    function doTempSave() {
        $("input[name=isSubmit]").val(0);
        $("#candidateForm").submit();
        return false;
    }

    //提交推荐数据
    function doTempSubmit() {

        bootbox.confirm('<div style="font-size: 16pt;font-weight: bolder;color:red;margin:10px;">\
            <div style="text-indent:2em;margin:50px 10px 10px 10px;">提交之前，请您确认推荐结果无需再做修改。</div>\
            <div style="text-indent:2em;padding:10px;">为保证信息的安全，在点击确定提交成功后您的账号、密码立即失效。<div></div>', function (result) {
            if (result) {
                $("input[name=isSubmit]").val(1);
                $("#candidateForm").submit();
                $(this).modal("hide");
                return false;
            }else {
                $(this).modal("hide");
            }
        })
    }

    function _logout() {
        location.href = "${ctx}/user/dr/logout";
    }

</script>
</body>
</html>
