<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <jsp:include page="/WEB-INF/jsp/common/m_head.jsp"></jsp:include>
    <title>${_plantform_name}</title>
    <link rel="stylesheet" href="${ctx}/css/main.css" />
    <link rel="stylesheet" href="${ctx}/extend/css/navbar.css?t=20180411" />
</head>
<body>
<div id="navbar" class="navbar navbar-default" id="top">
    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left hidden-xs hidden-sm">
            <div class="logo"  style="cursor: pointer;"><t:img src="/img/logo_white.png"/></div>
            <div class="txt" style="cursor: pointer;">线上民主推荐投票</div>
        </div>
        <div class="navbar-header pull-left hidden-md hidden-lg ">
            <a href="index" class="navbar-brand">
                <small  style="cursor: pointer;" onclick="location.href='../../../../..'">
                    ${_plantform_short_name}
                </small>
            </a>
        </div>
    </div>
    <div class="navbar-buttons navbar-header pull-right hidden-xs hidden-sm hidden-md" role="navigation">
        <ul class="nav nav-pills">
            <li>
                <a href="javascript:void(0)" onclick="drOnline_eva()"><i class="batch write"></i>测评</a>
            </li>
            <li>
                <a href="javascript:void(0)" onclick="drOnline_notice()"><i class="batch forms"></i>测评说明</a>
            </li>
            <li>
                <a href="javascript:void(0)" onclick="drOnline_changePasswd()"><i class="ace-icon fa fa-question-circle"></i> 修改密码</a>
            </li>
            <li>
                <a href="${ctx}/dr/drOnline/logout"><i class="ace-icon fa fa-power-off"></i> 退出</a>
            </li>
        </ul>
    </div>
</div>
<div class="container bs-docs-container" style="padding-top: 91px;">
    <div class="row " id="content"><br>
        <c:if test="${not empty inspector}">
            <div class="span12 header" style="padding-left: 20px;">
                <h4 style="padding-left: 20px" align="center">
                    <strong>当前账号：</strong><a href="#" class="tag">${inspector.username}</a>
                    <strong>所属单位：</strong><a href="#" class="tag">${unitMap.get(inspector.unitId).name}</a>
                    <strong>参评人身份类型：</strong><a href="#" class="tag">${inspector.inspectorType.type}</a>
                </h4>
            </div>
        </c:if>
    </div>
    <br>
    <div class="row eva" style="padding-left: 100px;" >
        <div class="col-xs-11">
            <div class="col-sm-offset-1 col-sm-10">
                <form id="evaluateForm"  method="post">
                    <c:if test="${!tempResult.agree}">
                        <div style="width:70%; margin:0 auto;">
                            <div class="modal-header" style="padding-top: 30px!important;" align="center">
                                <h2>测评说明</h2>
                            </div>
                            <div class="modal-body">
                                    ${drOnline.notice}
                            </div>
                        </div>
                        <div class="span12" style="margin-top: 30px;font:bold 25px Verdana, Arial, Helvetica, sans-serif;">
                            <center>
                                <input type="checkbox" id="agree" name="agree" style="width: 30px; height: 30px; margin: 0;"> 我确认已阅读测评说明
                            </center>
                        </div>
                        <div class="span12" style="margin-top: 30px;font:bold 20px Verdana, Arial, Helvetica, sans-serif;">
                            <center>
                                <button class="btn btn-large btn-success" onclick="_confirm()" type="button">进入测评页面</button></center>
                        </div>
                    </c:if>
                </form>
                <form id="survey" method="post">
                    <c:if test="${tempResult.agree}">
                    <table class="table-bordered" style="width: 80%;margin: 0 auto;" >
                        <thead>
                        <tr style="height: 70px">
                            <th colspan="3" style="font-size: 30px!important;padding-left: 180px">
                                干部民主推荐表<a href="#" class="tag"><font size="2">${drOnline.code}</font></a>
                            </th>

                        </tr>
                        <tr>
                            <th style="font-size: 15px!important;">推荐职务</th>
                            <th style="font-size: 15px!important;">推荐人选</th>
                            <th style="font-size: 15px!important;">推荐意见</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${postViews}" var="postView">
                            <c:if test="${postView.competitiveNum == 1}">
                                    <tr>
                                        <td>${postView.name}</td>
                                        <c:if test="${!postView.hasCandidate}">
                                            <td>
                                                <input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>" postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="输入后选择候选人或按回车 ..." />
                                            </td>
                                            <td>
                                                <div>
                                                    <label>--</label>
                                                </div>
                                            </td>
                                        </c:if>
                                        <c:if test="${postView.hasCandidate}">
                                            <c:forEach items="${candidateMap}" var="candidateMap">
                                                <c:if test="${candidateMap.key == postView.id}">
                                                    <td>${candidateMap.value.user.realname}</td>
                                                    <td>
                                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                            <input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_1" value="1">
                                                            <label for="${postView.id}_${candidates.userId}_1">同&nbsp;&nbsp;&nbsp;意</label>
                                                        </div>
                                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                            <input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_0" value="0">
                                                            <label for="${postView.id}_${candidates.userId}_0">不同意</label>
                                                        </div>
                                                    </td>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </tr>
                            </c:if>
                            <c:if test="${postView.competitiveNum > 1}">
                                <c:if test="${postView.hasCandidate}">
                                    <c:forEach items="${candidateMap}" var="candidateMap">
                                        <c:forEach items="${candidateMap.value}" var="candidates" begin="0" end="0">
                                            <c:if test="${candidateMap.key == postView.id}">
                                                <tr>
                                                    <td rowspan="${postView.existNum+1}">${postView.name}</td>
                                                    <td>${candidates.user.realname}</td>
                                                    <td>
                                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                            <input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_1" value="1">
                                                            <label for="${postView.id}_${candidates.userId}_1">同意</label>
                                                        </div>
                                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                            <input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_0" value="0">
                                                            <label for="${postView.id}_${candidates.userId}_0">不同意</label>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                    <c:forEach items="${candidateMap}" var="candidateMap">
                                        <c:forEach items="${candidateMap.value}" var="candidates" begin="1" end="${postView.existNum}">
                                            <c:if test="${candidateMap.key == postView.id}">
                                                <tr>
                                                    <td>${candidates.user.realname}</td>
                                                    <td>
                                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                            <input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_1" value="1">
                                                            <label for="${postView.id}_${candidates.userId}_1">同意</label>
                                                        </div>
                                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                            <input type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_0" value="0">
                                                            <label for="${postView.id}_${candidates.userId}_0">不同意</label>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                    <c:if test="${postView.competitiveNum > postView.existNum}">
                                                <tr>
                                                    <td>
                                                        <input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>" postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="输入后选择候选人或按回车 ..." />
                                                    </td>
                                                    <td>
                                                        <div>
                                                            <label>--</label>
                                                        </div>
                                                    </td>
                                                </tr>
                                    </c:if>
                                </c:if>
                                <c:if test="${!postView.hasCandidate}">
                                    <tr>
                                        <td>${postView.name}</td>
                                        <td>
                                            <input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>" postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="输入后选择候选人或按回车 ..." />
                                        </td>
                                        <td>
                                            <div>
                                                <label>--</label>
                                            </div>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:if>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="3">
                                <button class="btn btn-sm btn-primary"
                                        style="font-weight: bolder; font-size: medium; color: white" type="button" onclick="doTempSave()">保存</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button class="btn btn-sm btn-primary"
                                        style="font-weight: bolder; font-size: medium; color: white" type="button"
                                        onclick="doTempSubmit()">提交</button>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
    <div class="row notice" disabled hidden>
        <div class="col-xs-11" style="padding-left: 180px">
            <div class="col-sm-offset-1 col-sm-10">
                    <div class="modal-header" style="padding-top: 30px!important;" align="center">
                        <h2>干部民主推荐说明</h2>
                    </div>
                    <div class="modal-body">
                        ${drOnline.notice}
                    </div>
            </div>
        </div>
    </div>
    <div class="row changePasswd" disabled hidden>
        <div class="col-xs-11" style="padding-left: 180px">
                <div class="col-sm-offset-1 col-sm-10">
                    <div class="space"></div>
                        <form class="form-horizontal" id="form" action="${ctx}/dr/drOnline/user/changePasswd"  method="post">
                            <div class="tabbable"><%--
            <jsp:include page="../sys/profile/menu.jsp"/>--%>

                            <div class="tab-content profile-edit-tab-content">
                                <div>
                                    <div class="modal-header" align="center" >
                                        <h2>修改密码</h2>
                                    </div>
                                    <div class="form-group"></div><div class="form-group"></div>
                                    <div style="padding-left: 90px">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label no-padding-right"><span class="star">*</span>原密码</label>
                                            <div class="col-sm-9">
                                                <input required type="password" name="oldPasswd">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label no-padding-right"><span class="star">*</span>新密码</label>
                                            <div class="col-sm-9">
                                                <input required type="password"  name="passwd" id="passwd">
                                            </div>
                                        </div>

                                        <div class="space-4"></div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label no-padding-right"><span class="star">*</span>新密码确认</label>

                                            <div class="col-sm-9">
                                                <input required type="password" name="repasswd">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="clearfix form-actions" style="background-color: white;">
                            <div class="col-md-offset-3 col-md-9" style="padding-left: 50px">
                                <button class="btn btn-info" type="submit">
                                    <i class="ace-icon fa fa-check bigger-110"></i>
                                    保存
                                </button>

                                &nbsp; &nbsp;
                                <button class="btn" type="reset">
                                    <i class="ace-icon fa fa-undo bigger-110"></i>
                                    重置
                                </button>
                            </div>
                        </div>
                    </form>
                    </div>
                </div><!-- /.span -->
            </div>
</div>
<style>
    h1[id],h2[id]{
        padding-top:95px;
        margin-top:-95px;
    }
    #survey th,td{
        margin: 0px;
        padding: 0px;
        text-align: center;
        width: 200px;
        height: 50px;
    }
    #survey table{
        background-color: white;
        border: 1px black;
    }
</style>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script>

    function drOnline_eva() {
        $('.changePasswd').attr("disabled", "disabled");
        $('.changePasswd').attr("hidden", "hidden");

        $('.notice').attr("disabled", "disabled");
        $('.notice').attr("hidden", "hidden");

        $('.eva').removeAttr("disabled");
        $('.eva').removeAttr("hidden");
    }


    function drOnline_notice() {
        $('.changePasswd').attr("disabled", "disabled");
        $('.changePasswd').attr("hidden", "hidden");

        $('.notice').removeAttr("disabled");
        $('.notice').removeAttr("hidden");

        $('.eva').attr("disabled", "disabled");
        $('.eva').attr("hidden", "hidden");
    }

    function drOnline_changePasswd() {
        $('.changePasswd').removeAttr("disabled");
        $('.changePasswd').removeAttr("hidden");

        $('.notice').attr("disabled", "disabled");
        $('.notice').attr("hidden", "hidden");

        $('.eva').attr("disabled", "disabled");
        $('.eva').attr("hidden", "hidden");
    }

    function _confirm() {
        if ($('#agree').is(':checked') == false){
            $('#agree').qtip({content: '请您确认您已阅读测评说明！', show: true});
            return false;
        }
        $("#evaluateForm").ajaxSubmit({
            url: "${ctx}/dr/drOnline/agree",
            success: function (data) {
                if (data.success) {
                    //console.log(data)
                    location.reload();
                }
            }
        });

    }

    var tag_input = $('.form-field-tags');
    try{
        tag_input.tag(
            {
                placeholder:tag_input.attr('placeholder'),
                source: ${sysUser}
            }
        )
    } catch(e) {
        tag_input.after('<textarea id="'+tag_input.attr('id')+'" style="border: 0px white;margin: 0px;width: 100%;height: 95%;" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
    }

    //保存临时数据
    var isSubmit = 0;
    function doTempSave(){
        var onlineId = ${drOnline.id};
        var datas = new Array();
        $("table input:checked").each(function () {
            var radioName = $(this).attr("name");
            datas.push($(this).attr("id"));
            //console.log(datas);
        })

        var count = 0;
        var others = new Array();
        $("input[name=candidateCode]").each(function(){
            var postId = $(this).attr("postId");
            var user;
            var userIds = ($(this).val()).split(",");
            if ($.trim(userIds).length == 0)
                user = userIds;
            else {
                user = postId + "-" + userIds;
                count++;
            }
            //console.log(user)
            others.push(user)
        })
        if(isSubmit == 1){
            if ($("#survey tr").length - 3 > datas.length + count.length) {
                SysMsg.info('请完成推荐表后，再进行提交。', '提示')
                return;
            }else {
                $.post("${ctx}/dr/drOnline/doTempSave?&isSubmit=" + isSubmit,{"datas[]": datas, "others[]": others, "onlineId": onlineId},function(ret) {
                    if (ret.success) {
                        SysMsg.success('提交成功。退出后账号将不能登录。', '成功', function(){
                            location.href ="${ctx}/dr/drOnline/logout"
                        });
                    }
                })
            }
        }else{
            $.post("${ctx}/dr/drOnline/doTempSave?&isSubmit=" + isSubmit,{"datas[]": datas, "others[]": others, "onlineId": onlineId},function(ret) {
               if (ret.success) {
                   SysMsg.success('保存成功。', '成功')
                   //location.reload();
               }
           })
        }
    }

    //提交推荐数据
    function doTempSubmit(){
        isSubmit = 1;
        doTempSave();
        isSubmit = 0;
    }

    //添加候选人，动态改变name和id属性
    $(function () {
        $("#survey select").on("change", function () {
            //alert($(this).val().length)
            //console.log($(this).val().length)
            var arr = $(this).parent().parent().next().children().children();
            //console.log($(this).parent().parent().next().children().children())
            var postViewId = ((arr[0]).name.split("_"))[0];
            if ($(this).val().length != 0) {
                $(arr[0]).attr("name", postViewId + "_" + this.value).attr("id", postViewId + "_" + this.value + "_1");
                $(arr[1]).attr("for", postViewId + "_" + this.value + "_1");
                $(arr[2]).attr("name", postViewId + "_" + this.value).attr("id", postViewId + "_" + this.value + "_0");
                $(arr[3]).attr("for", postViewId + "_" + this.value + "_0");
            }else  {
                $(arr[0]).removeAttr("id");
                $(arr[2]).removeAttr("id");
            }
        })
    })

    //接收临时数据(管理员设置的候选人)，并在页面显示
    var tempResult=${cm:toJSONObject(tempResult)};
    //console.log(tempResult)
    if (tempResult.tempInspectorResultMap != undefined){
        $.each(tempResult.tempInspectorResultMap, function (onlineId, val) {
            $.each(val.optionIdMap, function (key, value) {
                var radioName, radioValue, userId, postId;
                radioName = key;
                radioValue = value;
                var keys = key.split("_");
                if (keys.length == 2) {
                    postId = keys[0];
                    userId = keys[1];
                }else {
                    return true; //数据有误
                }
                $("[name=" + radioName + "][value=" + radioValue + "]").click();
                //console.log($("[name=" + radioName + "][value=" + radioValue + "]"))
            })
        })
    }

    $("#form button[type=submit]").click(function(){$("#form").submit();return false;});
    $("#form").validate({
        rules: {
            repasswd:{
                equalTo:'#passwd'
            }
        },
        submitHandler: function (form) {

            $(form).ajaxSubmit({
                success:function(data){
                    //console.log(data)
                    if(data.success){
                        SysMsg.success('修改密码成功，请重新登录。', '成功', function(){
                            location.href ="${ctx}/dr/drOnline/logout"
                        });
                    }
                }
            });
        }
    });

</script>
</body>
</html>
