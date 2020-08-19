<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['pcs_poll_site_name']}" var="_p_pcsSiteName"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_PR%>" var="PCS_POLL_CANDIDATE_PR"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_DW%>" var="PCS_POLL_CANDIDATE_DW"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_JW%>" var="PCS_POLL_CANDIDATE_JW"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_TYPE%>" var="PCS_POLL_CANDIDATE_TYPE"/>
<c:set value="<%=PcsConstants.RESULT_STATUS_AGREE%>" var="RESULT_STATUS_AGREE"/>
<c:set value="<%=PcsConstants.RESULT_STATUS_DISAGREE%>" var="RESULT_STATUS_DISAGREE"/>
<c:set value="<%=PcsConstants.RESULT_STATUS_ABSTAIN%>" var="RESULT_STATUS_ABSTAIN"/>

<c:set value="<%=PcsConstants.PCS_POLL_THIRD_STAGE%>" var="PCS_POLL_THIRD_STAGE"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <title>${_p_pcsSiteName}</title>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
    <t:link href="/css/dr.css"/>
</head>
<body style="background-color: inherit;">
<div id="navbar" class="navbar navbar-default navbar-fixed-top">
    <div class="container" id="navbar-container">
        <div class="navbar-header" style="width: 100%">
            <div class="logo" style="cursor: pointer;" onclick="location.href='#'">
                <t:img src="/img/logo_white.png"/></div>
            <div class="separator"></div>
            <div class="txt" style="cursor: pointer;">${_p_pcsSiteName}</div>

            <ul class="nav nav-pills pull-right">
                <c:if test="${not empty inspector}">
                    <li>
                        <a href="javascript:;"><i
                                class="ace-icon fa fa-user"></i> 当前账号：${inspector.username}</a>
                    </li>
                </c:if>
                 <c:if test="${tempResult.agree}">
                <li>
                    <a href="${ctx}/user/pcs/index?notice=1"><i
                            class="ace-icon fa fa-question-circle"></i> 推荐说明</a>
                </li>
                 </c:if>
                <li>
                    <a href="javascript:;" onclick="_logout()"><i class="ace-icon fa fa-power-off"></i> 退出</a>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="container" style="padding-top: 100px">
    <div class="main-content eva">
        <c:if test="${param.notice==1 || !tempResult.agree}">
            <form id="agreeForm" method="post">
                <div style="width:70%; margin:0 auto;">

                    <div class="modal-body" style="text-align: left;word-wrap:break-word">
                            ${cm:htmlUnescape(pcsPoll.notice)}
                    </div>
                </div>

                <div class="span12" style="margin-top: 30px;font:bold 20px Verdana, Arial, Helvetica, sans-serif;">
                    <center>
                         <c:if test="${param.notice==1}">
                        <a class="btn btn-primary btn-lg" id="enterBtn" href="${ctx}/user/pcs/index"
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
            <div class="alert alert-block alert-success left bolder"
                 style="width:800px;margin: 0 auto 10px;font-size: larger;">
                ${pcsPoll.name}
            </div>
            <form id="candidateForm" method="post" action="${ctx}/user/pcs/submit">
                <input type="hidden" name="flag" value="0">
                <input type="hidden" name="isSubmit" value="0">
                <input type="hidden" name="type" value="${type}">
                <table class="table table-bordered table-unhover2" style="width:800px;margin: 0 auto;">
                    <tbody>
                        <tr>
                            <td align="right"><span class="star">*</span> 投票人身份</td>
                            <td align="left">
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input type="radio" name="isPositive"
                                           id="isPositive_1" value="1" ${inspector.isPositive?"checked":""}>
                                    <label for="isPositive_1">正式党员</label>
                                </div>
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input type="radio" name="isPositive"
                                           id="isPositive_0" value="0" ${empty inspector.isPositive?"":(inspector.isPositive?"":"checked")}>
                                    <label for="isPositive_0">预备党员</label>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><span class="star">*</span> 推荐人类型</td>
                            <td align="left">
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="type"
                                           id="type_2" value="${PCS_POLL_CANDIDATE_DW}" ${type==PCS_POLL_CANDIDATE_DW?"checked":""}>
                                    <label for="type_2">党委委员</label>
                                </div>
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="type"
                                           id="type_3" value="${PCS_POLL_CANDIDATE_JW}" ${type==PCS_POLL_CANDIDATE_JW?"checked":""}>
                                    <label for="type_3">纪委委员</label>
                                </div>
                                <c:if test="${pcsPoll.stage!=PCS_POLL_THIRD_STAGE}">
                                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                        <input required type="radio" name="type"
                                               id="type_1" value="${PCS_POLL_CANDIDATE_PR}" ${type==PCS_POLL_CANDIDATE_PR?"checked":""}>
                                        <label for="type_1">代表</label>
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                        <c:forEach items="${cans}" var="can">
                            <c:set var="key" value="${type}_${can.userId}"/>
                            <c:set var="status" value="${tempResult.secondResultMap.get(key)}"/>
                            <c:set var="otherKey" value="${key}_4"/>
                            <c:set var="userId" value="${tempResult.otherResultMap.get(otherKey)}"/>
                            <c:set var="otherUser" value="${cm:getUserById(userId)}"/>
                            <tr class="candidate">
                                <td class="realname" align="right">${can.user.realname}（${can.user.code}）</td>
                                <td>
                                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                        <input type="radio" name="${key}"
                                               id="${key}_1" value="${RESULT_STATUS_AGREE}" ${status==RESULT_STATUS_AGREE?"checked":""}>
                                        <label for="${key}_1">同意</label>
                                    </div>
                                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                        <input type="radio" name="${key}"
                                               id="${key}_2" value="${RESULT_STATUS_DISAGREE}" ${status==RESULT_STATUS_DISAGREE?"checked":""}>
                                        <label for="${key}_2">不同意</label>
                                    </div>
                                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                        <input type="radio" name="${key}"
                                               id="${key}_3" value="${RESULT_STATUS_ABSTAIN}" ${status==RESULT_STATUS_ABSTAIN?"checked":""}>
                                        <label for="${key}_3">弃权</label>
                                    </div>
                                </td>
                            </tr>
                            <tr class="other" style="display: ${status==2?'':'none'}" data-candidate="${key}">
                                <td class="realname" align="right">另选其他推荐人</td>
                                <td>
                                    <select data-rel="select2-ajax" data-width="272"
                                            name="${key}_4" data-placeholder="请输入推荐人姓名或学工号">
                                        <option value="${otherUser.id}">${otherUser.realname}-${otherUser.code}</option>
                                    </select>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="2" style="text-align: center">
                                <button class="btn btn-primary" type="button"
                                        onclick="_save(0)"><i class="fa fa-save"></i> 暂存
                                </button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button class="btn btn-success" id="tempSubmit"
                                        type="button"
                                        onclick="_submit(4)"><i class="fa fa-check"></i> 提交
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
    $('#candidateForm input[name=type]').change(function () {
        _save($(this).val())
    })

    $(".candidate input[type=radio]").click(function (e) {
        var $otherTr = $("tr[data-candidate='" + $(this).attr("name") + "']");
        if ($(this).val() == ${RESULT_STATUS_AGREE} || $(this).val() == ${RESULT_STATUS_ABSTAIN}) {
            $otherTr.hide();
        } else {
            $otherTr.show();
        }

        /*if($(this).data("check")){
            $(this).data("check", false);
            $(this).prop("checked", false)
        }else if($(this).is(":checked")){
            $(this).data("check", true);
        }*/
    })

    var $select = $.register.user_select($('select[data-rel=select2-ajax]'),
        {url:"${ctx}/user/pcs/member_selects?noAuth=1&partyId=${type==PCS_POLL_CANDIDATE_PR?inspector.partyId:''}&status=${MEMBER_STATUS_NORMAL}",
            theme:'default',language:"zh-CN"});

    var selectedUserIds=${empty selectUserIdList?'[]':selectUserIdList};
    //console.log(selectedUserIds)
    var $tip;
    $select.on("select2:select",function(e){

        var $this = $(this);
        if($.inArray(parseInt($this.val()), selectedUserIds)>=0) {
            $tip = $.tip({
                $target: $this.closest("td").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "您已经选择了该推荐人。"
            });
            $this.val(null).trigger("change");
        }else {
            if($tip!=undefined) {
                $tip.qtip('destroy', true);
            }
            selectedUserIds.push(parseInt($(this).val()));
        }
        //console.log(selectedUserIds)
    });

    function _confirm() {
        if ($('#agree').is(':checked') == false) {
            $('#agree').qtip({content: '请您确认您已阅读说明。', show: true});
            return false;
        }
        $("#agreeForm").ajaxSubmit({
            url: "${ctx}/user/pcs/agree",
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
                        if ($("input[name=flag]").val() == 4) {
                            $.loadModal("${ctx}/user/pcs/submit_info");
                        }else if ($("input[name=flag]").val() != 0) {//切换人员类型时，保存数据
                            var type = $('#candidateForm input[name=type]:checked').val();
                            location.href="${ctx}/user/pcs/index?type="+type;
                        }else if ($("input[name=isSubmit]").val() == 0) {
                            SysMsg.success('保存成功（数据还未提交，请填写完成后提交全部结果）。', '暂存')
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
    function _submit(flag) {

        var isPositive = $("input[name=isPositive]:checked").val();
        if(isPositive!=0 && isPositive!=1){
            SysMsg.error("请选择投票人身份");
            return;
        }

        $("input[name=flag]").val(flag);//0保存按钮保存 4是先保存，然后弹出提示框,进行提交
        $("input[name=isSubmit]").val(0);
        $("#candidateForm").submit();
        return false;
    }

    function _logout() {
        location.href = "${ctx}/user/pcs/logout";
    }

</script>
</body>
</html>
