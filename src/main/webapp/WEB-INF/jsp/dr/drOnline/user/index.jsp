<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="RESULT_STATUS_AGREE" value="<%=DrConstants.RESULT_STATUS_AGREE%>"/>
<c:set var="RESULT_STATUS_DISAGREE" value="<%=DrConstants.RESULT_STATUS_DISAGREE%>"/>
<c:set var="RESULT_STATUS_ABSTAIN" value="<%=DrConstants.RESULT_STATUS_ABSTAIN%>"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <jsp:include page="/WEB-INF/jsp/common/m_head.jsp"></jsp:include>
    <title>线上民主推荐</title>
    <link rel="stylesheet" href="${ctx}/css/main.css"/>
    <link rel="stylesheet" href="${ctx}/extend/css/navbar.css?t=20180411"/>

    <style>
        h1[id], h2[id] {
            padding-top: 95px;
            margin-top: -95px;
        }

        body #navbar {
            text-align: center;
        }

        .navbar-header .nav {
            top: 40px;
            position: relative;
            margin-left: 45px;
        }

        .navbar-header .nav a {
            color: white;
        }

        .navbar-header .nav > li > a:hover {
            background-color: inherit;
        }

        #candidateForm .table td, #candidateForm .table th {
            vertical-align: middle;
        }

        #candidateForm td.post-name {
            text-align: left !important;
            font-size: x-large;
            background-color: #ddd;
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
        <div class="navbar-header">
            <div class="logo" style="cursor: pointer;" onclick="location.href='#'">
                <t:img src="/img/logo_white.png"/></div>
            <div class="separator"></div>
            <div class="txt" style="cursor: pointer;">线上民主推荐系统</div>

            <ul class="nav nav-pills pull-right">
                <li>
                    <a href="javascript:void(0)" onclick="drOnline_eva()"><i class="ace-icon fa fa-home"></i> 首页</a>
                </li>
                <li>
                    <a href="javascript:void(0)" onclick="drOnline_notice()"><i
                            class="ace-icon fa fa-question-circle"></i> 推荐说明</a>
                </li>
                <li>

                    <a href="javascript:void(0)" onclick="drOnline_changePasswd()"><i class="ace-icon fa fa-key"></i>
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
        <c:if test="${!tempResult.agree}">
            <form id="agreeForm" method="post">
                <div style="width:70%; margin:0 auto;">

                    <div class="modal-body" style="text-align: left;word-wrap:break-word">
                            ${drOnline.notice}
                    </div>
                </div>
                <div class="span12" style="margin-top: 30px;font:bold 25px Verdana, Arial, Helvetica, sans-serif;">
                    <center>
                        <input type="checkbox" id="agree" name="agree"
                               style="width: 25px; height: 25px; margin: 0px;vertical-align: text-bottom;"> 我确认已阅读推荐说明
                    </center>
                </div>
                <div class="span12" style="margin-top: 30px;font:bold 20px Verdana, Arial, Helvetica, sans-serif;">
                    <center>
                        <button class="btn btn-large" id="enterBtn" onclick="_confirm()" type="button">进入推荐页面</button>
                    </center>
                </div>
            </form>
        </c:if>
        <c:if test="${tempResult.agree}">
            <form id="candidateForm" method="post" action="${ctx}/user/dr/doTempSave">
                <input type="hidden" name="isSubmit" value="0">
                <table class="table table-bordered" style="width:800px;margin: 0 auto;">
                    <thead>
                    <tr>
                        <th colspan="2" class="center">
                                ${drOnline.code}
                        </th>
                    </tr>
                    </thead>
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
                                <td class="realname">${candidate.realname}</td>
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
                                    <input name="${key}_realname" type="text" value="${realname}">
                                </td>
                            </tr>
                        </c:forEach>
                        <c:forEach begin="${candidateNum+1}" end="${post.competitiveNum}" var="idx">
                            <tr>
                                <td class="realname">推荐人${idx}</td>
                                <td>
                                    <input name="${post.id}_realname_${idx}" type="text"
                                           value="${cm:getSetValue(realnameSet, idx-candidateNum-1)}">
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                    </tbody>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <button class="btn btn-sm btn-success" type="button"
                                    onclick="doTempSave()"><i class="fa fa-save"></i> 保存
                            </button>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <button class="btn btn-sm btn-primary" id="tempSubmit"
                                    type="button"
                                    onclick="doTempSubmit()"><i class="fa fa-check"></i> 提交
                            </button>
                        </td>
                    </tr>
                </table>
            </form>
        </c:if>
    </div>
</div>
<script>

    $("input[type=radio]").click(function () {
        var $otherTr = $("tr[data-candidate='" + $(this).attr("name") + "']");
        if ($(this).val() == ${RESULT_STATUS_AGREE}) {
            $otherTr.hide();
            $("input", $otherTr).removeAttr("required");
        } else {
            $otherTr.show();
            $("input", $otherTr).attr("required", "required");
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
                            SysMsg.success('保存成功。', '成功', function () {
                                location.reload();
                            })
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
