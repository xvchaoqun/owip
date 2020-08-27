<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
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
            text-align: left;
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
                            <c:if test="${pcsPoll.stage==PcsConstants.PCS_POLL_SECOND_STAGE}">
                                ${cm:htmlUnescape(_2_m.content)}
                            </c:if>
                            <c:if test="${pcsPoll.stage==PcsConstants.PCS_POLL_THIRD_STAGE}">
                                ${cm:htmlUnescape(_3_m.content)}
                            </c:if>
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
                        ${pcsPoll.name}（${PCS_USER_TYPE_MAP.get(type)}）
                    </div>
                    <form id="candidateForm" method="post" action="${ctx}/user/pcs/submit">
                        <input type="hidden" name="flag" value="0">
                        <input type="hidden" name="isMobile" value="1">
                        <input type="hidden" name="isSubmit" value="0">
                        <input type="hidden" name="type" value="${type}">
                        <table class="table table-bordered">
                            <tbody>
                                <tr>
                                    <td class="realname"><span class="star">*</span>投票人身份</td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                            <input type="radio" name="isPositive" id="isPositive_1" value="1" ${inspector.isPositive?"checked":""}>
                                            <label for="isPositive_1">正式党员</label>
                                        </div>
                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                            <input type="radio" name="isPositive" id="isPositive_0" value="0" ${empty inspector.isPositive?"":(inspector.isPositive?"":"checked")}>
                                            <label for="isPositive_0">预备党员</label>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="realname"><span class="star">*</span>推荐人类型</td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                            <input type="radio" name="type"
                                                   id="type_2" value="${PCS_USER_TYPE_DW}" ${type==PCS_USER_TYPE_DW?"checked":""}>
                                            <label for="type_2">党委委员</label>
                                        </div>
                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                            <input type="radio" name="type"
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
                                <c:forEach items="${candidateUserIds}" var="candidateUserId" varStatus="vs">
                                    <c:set var="key" value="${type}_${candidateUserId}"/>
                                    <c:set var="status" value="${tempResult.secondResultMap.get(key)}"/>
                                    <c:set var="otherKey" value="${key}_4"/>
                                    <c:set var="userId" value="${tempResult.otherResultMap.get(otherKey)}"/>
                                    <c:set var="otherUser" value="${cm:getUserById(userId)}"/>
                                    <tr>
                                        <td class="realname">推荐人${vs.index+1}：${cm:getUserById(candidateUserId).realname}</td>
                                    </tr>
                                    <tr class="candidate">
                                        <td align="center">
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
                                    <tr class="other" style="display: ${status==2?'':'none'}" data-candidate="${key}">
                                        <td class="realname">另选其他推荐人</td>
                                    </tr>
                                    <tr class="other" style="display: ${status==2?'':'none'}" data-candidate="${key}">
                                        <td>
                                            <select data-rel="select2-ajax" data-width="272"
                                                    name="${key}_4" data-placeholder="请输入推荐人姓名或学工号">
                                                <option value="${otherUser.id}">${otherUser.realname}-${otherUser.code}</option>
                                            </select>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tr>
                                <td colspan="2" style="text-align: center">
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

     $(".candidate input[type=radio]").click(function () {
        var $otherTr = $("tr[data-candidate='" + $(this).attr("name") + "']");
        if ($(this).val() == ${RESULT_STATUS_AGREE} || $(this).val() == ${RESULT_STATUS_ABSTAIN}) {
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

        /* var $radio = $(this);
         if ($radio.data('waschecked') == true){
             $radio.attr('checked', false);
             $radio.data('waschecked', false);
         } else {
             $radio.attr('checked', true);
             $radio.data('waschecked', true);
         }
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
    //console.log(selectedUserIds)
    var $tip;
    $select.on("select2:select",function(e){

        var $this = $(this);
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
    });
    $select.on("select2:unselect", function (evt) {
        var userId = $(this).val();
        otherUserIds = $.map($('select[data-rel=select2-ajax]'), function (sel) {
            if(userId!=$(sel).val())
                return parseInt($(sel).val());
        });
    });

    function _confirm() {
        if ($('#agree').is(':checked') == false) {
            $('#agree').qtip({content: '请您确认您已阅读推荐说明', show: true});
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
                        if ($("input[name=flag]").val() == 4) {
                            $.loadModal("${ctx}/user/pcs/submit_info");
                        }else if ($("input[name=flag]").val() != 0) {//切换人员类型时，保存数据
                            var type = $('#candidateForm input[name=type]:checked').val();
                            location.href="${ctx}/user/pcs/index?isMobile=1&type="+type;
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

    function _logout(isFinished) {
        location.href = "${ctx}/user/pcs/logout?isMobile=1&isFinished="+$.trim(isFinished);
    }
</script>
</body>
</html>
