<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
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
                        <c:if test="${pcsPoll.stage==PcsConstants.PCS_POLL_SECOND_STAGE}">
                            ${cm:htmlUnescape(_2_pc.content)}
                        </c:if>
                        <c:if test="${pcsPoll.stage==PcsConstants.PCS_POLL_THIRD_STAGE}">
                            ${cm:htmlUnescape(_3_pc.content)}
                        </c:if>
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
                <table class="table table-bordered table-unhover2 table-center" style="width:800px;margin: 0 auto;">
                    <tbody>
                        <tr>
                            <td colspan="2"><span class="star">*</span> 投票人身份</td>
                            <td>
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
                            <td colspan="2"><span class="star">*</span> 推荐人类型</td>
                            <td>
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="type"
                                           id="type_2" value="${PCS_USER_TYPE_DW}" ${type==PCS_USER_TYPE_DW?"checked":""}>
                                    <label for="type_2">党委委员</label>
                                </div>
                                <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                    <input required type="radio" name="type"
                                           id="type_3" value="${PCS_USER_TYPE_JW}" ${type==PCS_USER_TYPE_JW?"checked":""}>
                                    <label for="type_3">纪委委员</label>
                                </div>
                                <c:if test="${pcsPoll.stage!=PCS_POLL_THIRD_STAGE}">
                                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                        <input required type="radio" name="type"
                                               id="type_1" value="${PCS_USER_TYPE_PR}" ${type==PCS_USER_TYPE_PR?"checked":""}>
                                        <label for="type_1">代表</label>
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td>序号</td>
                            <td>姓名</td>
                            <td>推荐意见</td>
                        </tr>
                        <c:forEach items="${candidateUserIds}" var="candidateUserId" varStatus="vs">
                            <c:set var="key" value="${type}_${candidateUserId}"/>
                            <c:set var="status" value="${tempResult.secondResultMap.get(key)}"/>
                            <c:set var="otherKey" value="${key}_4"/>
                            <c:set var="userId" value="${tempResult.otherResultMap.get(otherKey)}"/>
                            <c:set var="otherUser" value="${cm:getUserById(userId)}"/>
                            <tr class="candidate">
                                <td>${vs.index+1}</td>
                                <td class="realname">${cm:getUserById(candidateUserId).realname}</td>
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
                                <td class="realname" colspan="2">另选其他推荐人</td>
                                <td>
                                    <select data-rel="select2-ajax" data-width="272"
                                            name="${key}_4" data-placeholder="请输入推荐人姓名或学工号">
                                        <option value="${otherUser.id}">${otherUser.realname}-${otherUser.code}</option>
                                    </select>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="3" style="text-align: center">
                                <button id="saveBtn" class="btn btn-primary" type="button" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 保存中"
                                        onclick="_save(0)"><i class="fa fa-save"></i> 暂存
                                </button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button id="checkSubmitBtn" class="btn btn-success" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 请稍后"
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
        $("table").mask()
        _save($(this).val())
    })

    //候选人radio初始化赋值
    /*defineWaschecked();
    function defineWaschecked() {
        $.each($(".candidate input[type=radio]"), function () {
            if ($(this).is(":checked")){
                $(this).data('waschecked', true);
            } else {
                $(this).data('waschecked', false);
            }
        })
    }*/

    $(".candidate input[type=radio]").click(function (e) {
        var $radio = $(this);
        var $otherTr = $("tr[data-candidate='" + $(this).attr("name") + "']");
        if ($radio.val() == ${RESULT_STATUS_AGREE} || $radio.val() == ${RESULT_STATUS_ABSTAIN}) {
            $otherTr.hide();
        } else {
            $otherTr.show();
        }

        if($radio.val()!=${RESULT_STATUS_DISAGREE}){
            //console.log("-----"+$radio.attr('name'))
            $("select[name="+$radio.attr('name')+"_4]").val(null).trigger("change");

            otherUserIds = $.map($('select[data-rel=select2-ajax]'), function (sel) {
                return parseInt($(sel).val());
            });
        }

        /*if ($radio.data('waschecked') == true){
            $radio.attr('checked', false);
            $radio.data('waschecked', false);
        } else {
            $radio.attr('checked', true);
            $radio.data('waschecked', true);
        }
        //console.log($radio.parent().siblings("div").find('input[type="radio"]'))
        //将未选中的“waschecked”都设置为false
        $radio.parent().siblings("div").find('input[type="radio"]').data('waschecked', false);
        if ($(this).val() == ${RESULT_STATUS_DISAGREE}&&!$(this).is(":checked")){
                $otherTr.hide();
        }*/
    })

    var $select = $.register.user_select($('select[data-rel=select2-ajax]'),
        {url:"${ctx}/user/pcs/member_selects?noAuth=1&partyId=${type==PCS_USER_TYPE_PR?inspector.partyId:''}&status=${MEMBER_STATUS_NORMAL}",
            theme:'default',language:"zh-CN"});

    var selectedUserIds=${empty selectUserIdList?'[]':selectUserIdList};
    var otherUserIds = [];
    var $tip;
    $select.on("select2:select",function(e){

        var $this = $(this);
        //console.log(selectedUserIds);
        //console.log(otherUserIds);
        if($.inArray(parseInt($this.val()), selectedUserIds)>=0 || $.inArray(parseInt($this.val()), otherUserIds)>=0) {
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
        }
        otherUserIds = $.map($('select[data-rel=select2-ajax]'), function (sel) {
            return parseInt($(sel).val());
        });
        //console.log(otherUserIds);
    });
    $select.on("select2:unselect", function (evt) {
        var userId = $(this).val();
        selectUsers = $.map($('select[data-rel=select2-ajax]'), function (sel) {
            if(userId!=$(sel).val())
                return parseInt($(sel).val());
        });
        //console.log(selectUsers)
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

                    $("#saveBtn").button('reset');
                    $("#checkSubmitBtn").button('reset');
                }
            });
        }
    });

    //保存
    function _save(flag) {

        $("#saveBtn").button('loading');
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

        $("#checkSubmitBtn").button('loading');
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
