<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <jsp:include page="/WEB-INF/jsp/common/m_head.jsp"></jsp:include>
    <title>线上民主推荐</title>
    <link rel="stylesheet" href="${ctx}/css/main.css" />
    <link rel="stylesheet" href="${ctx}/extend/css/navbar.css?t=20180411" />

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
        body #navbar{
            text-align: center;
        }

        .navbar-header .nav{
            top: 40px;
            position: relative;
            margin-left: 45px;
        }
        .navbar-header .nav a{
            color: white;
        }
        .navbar-header .nav>li>a:hover{
            background-color: inherit;
        }
    </style>
</head>
<body>
<div id="navbar" class="navbar navbar-default navbar-fixed-top" style="">
    <div class="container" id="navbar-container">
        <div class="navbar-header">
            <div class="logo" style="cursor: pointer;" onclick="location.href='#'">
                <t:img src="/img/logo_white.png"/></div>
            <div class="separator"></div>
            <div class="txt" style="cursor: pointer;">线上民主推荐系统</div>

            <ul class="nav nav-pills pull-right">
            <li class="">
                <a href="javascript:void(0)" onclick="drOnline_eva()"><i class="ace-icon fa fa-home"></i> 首页</a>
            </li>
            <li>
                <a href="javascript:void(0)" onclick="drOnline_notice()"><i class="ace-icon fa fa-question-circle"></i> 推荐说明</a>
            </li>
            <li>

                <a href="javascript:void(0)" onclick="drOnline_changePasswd()"><i class="ace-icon fa fa-key"></i> 修改密码</a>
            </li>
            <li>
                <a href="javascript:;" onclick="_logout()"><i class="ace-icon fa fa-power-off"></i> 退出</a>
            </li>
        </ul>
        </div>
    </div>
</div>
<div class="main-container" id="main-container" style="padding-top: 100px;margin-left: 15%;margin-right: 15%">
    <div class="row " id="content" style="padding-left: 7%;padding-right: 7%"><br>
        <c:if test="${not empty inspector}">
            <div class="span12 header" style="padding-left: 20px;">
                <h4 style="padding-left: 20px" align="center">
                    <strong>当前账号：</strong><a href="#" class="tag">${inspector.username}</a>&nbsp;
                    <strong>类别：</strong><a href="#" class="tag">${inspector.inspectorType.type}</a>&nbsp;
                </h4>
            </div>
        </c:if>
    </div>
    <br>
    <div class="main-content eva">
        <div class="col-xs-11" style="padding-left: 12%">
            <div class="col-sm-offset-1 col-sm-10">
                <div style="text-align: center">
                <form id="evaluateForm"  method="post">
                    <c:if test="${!tempResult.agree}">
                        <div style="width:70%; margin:0 auto;">

                            <div class="modal-body" style="text-align: left;word-wrap:break-word">
                                    ${drOnline.notice}
                            </div>
                        </div>
                        <div class="span12" style="margin-top: 30px;font:bold 25px Verdana, Arial, Helvetica, sans-serif;">
                            <center>
                                <input type="checkbox" id="agree" name="agree" style="width: 25px; height: 25px; margin: 0px;vertical-align: text-bottom;"> 我确认已阅读推荐说明
                            </center>
                        </div>
                        <div class="span12" style="margin-top: 30px;font:bold 20px Verdana, Arial, Helvetica, sans-serif;">
                            <center>
                                <button class="btn btn-large" id="enterBtn" onclick="_confirm()" type="button">进入推荐页面</button>
                            </center>
                        </div>
                    </c:if>
                </form>
                <form id="survey" method="post" display>
                    <c:if test="${tempResult.agree}">
                    <table class="table table-center table-bordered" style="width:800px;" >
                        <thead>
                        <tr style="height: 70px">
                            <th colspan="3" style="font-size: 30px!important;">
                                <font size="2">${drOnline.code}</font>
                            </th>
                        </tr>
                        <tr>
                            <th style="font-size: 15px!important;">推荐职务（最大推荐人数）</th>
                            <th style="font-size: 15px!important;">推荐人选</th>
                            <th style="font-size: 15px!important;">推荐意见</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${postViews}" var="postView">
                            <c:if test="${postView.competitiveNum == 1}">
                                <c:if test="${!postView.hasCandidate}">
                                    <tr>
                                        <td>${postView.name}（${postView.competitiveNum}人）</td>
                                        <td>
                                            另选他人
                                        </td>
                                        <td>
                                            <input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>"
                                                   postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="请输入姓名后按回车键" />
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${postView.hasCandidate}">
                                    <tr>
                                    <c:forEach items="${candidateMap}" var="candidateMap">
                                        <c:if test="${candidateMap.key == postView.id}">
                                            <td rowspan="2">${postView.name}（${postView.competitiveNum}人）</td>
                                            <c:forEach items="${candidateMap.value}" var="candidates">
                                                <td>${candidates.candidate}</td>
                                                <td>
                                                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                        <input postId="${postView.id}" type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_1" value="1">
                                                        <label for="${postView.id}_${candidates.userId}_1">同&nbsp;&nbsp;&nbsp;意</label>
                                                    </div>
                                                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                        <input postId="${postView.id}" type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_0" value="0">
                                                        <label for="${postView.id}_${candidates.userId}_0">不同意</label>
                                                    </div>
                                                </td>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                    </tr>
                                    <tr>
                                        <td>
                                           另选他人
                                        </td>
                                        <td>
                                             <input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>"
                                                   postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="请输入姓名后按回车键" />
                                        </td>
                                    </tr>
                                </c:if>
                            </c:if>
                            <c:if test="${postView.competitiveNum > 1}">
                                <c:if test="${postView.hasCandidate}">
                                    <c:forEach items="${candidateMap}" var="candidateMap">
                                        <c:forEach items="${candidateMap.value}" var="candidates" begin="0" end="0">
                                            <c:if test="${candidateMap.key == postView.id}"><%--有候选人--%>
                                                <tr>
                                                    <td rowspan="${postView.existNum+1}">${postView.name}（${postView.competitiveNum}人）</td><%--existNum不变，候选人列表要增加--%>
                                                    <td>${candidates.candidate}</td>
                                                    <td>
                                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                            <input postId="${postView.id}" type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_1" value="1">
                                                            <label for="${postView.id}_${candidates.userId}_1">同意</label>
                                                        </div>
                                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                            <input postId="${postView.id}" type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_0" value="0">
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
                                                    <td>${candidates.candidate}</td>
                                                    <td>
                                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                            <input postId="${postView.id}" type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_1" value="1">
                                                            <label for="${postView.id}_${candidates.userId}_1">同意</label>
                                                        </div>
                                                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                                            <input postId="${postView.id}" type="radio" name="${postView.id}_${candidates.userId}" id="${postView.id}_${candidates.userId}_0" value="0">
                                                            <label for="${postView.id}_${candidates.userId}_0">不同意</label>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                    <tr>
                                        <td>
                                            另选他人
                                        </td>
                                        <td>
                                            <input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>"
                                                   postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="请输入姓名后按回车键" />
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!postView.hasCandidate}">
                                    <tr>
                                        <td>${postView.name}（${postView.competitiveNum}人）</td>
                                        <td>
                                            另选他人
                                        </td>
                                        <td>
                                            <input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>"
                                                   postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="请输入姓名后按回车键" />
                                        </td>
                                    </tr>
                                </c:if>
                            </c:if>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="3">
                                <button class="btn btn-sm btn-success" type="button"
                                        onclick="doTempSave()"><i class="fa fa-save"></i> 保存</button>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <button class="btn btn-sm btn-primary" id="tempSubmit"
                                        type="button"
                                        onclick="doTempSubmit()"><i class="fa fa-check"></i> 提交</button>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                    </c:if>
                </form>
                </div>
            </div>
        </div>
    </div>
    <div class="main-content notice" disabled hidden>
        <div class="col-xs-11" style="padding-left: 12%">
            <div class="col-sm-offset-1 col-sm-10">

                    <div class="modal-body">
                        ${drOnline.notice}
                    </div>
            </div>
        </div>
    </div>
    <div class="main-content changePasswd" disabled hidden>
        <div class="col-xs-11" style="padding-left: 12%">
                <div class="col-sm-offset-1 col-sm-10">
                    <div class="space"></div>
                        <form class="form-horizontal" id="form" action="${ctx}/user/dr/changePasswd"  method="post">
                            <div class="tabbable">

                            <div class="tab-content profile-edit-tab-content">
                                <div>
                                    <div class="modal-header" style="text-align: center">
                                        <h2>修改密码</h2>
                                    </div>
                                    <div class="form-group"></div><div class="form-group"></div>
                                    <div style="padding-left:20%">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label no-padding-right"><span class="star">*</span>原密码</label>
                                            <div class="col-sm-3">
                                                <input required type="password" name="oldPasswd">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label no-padding-right"><span class="star">*</span>新密码</label>
                                            <div class="col-sm-3">
                                                <input required type="password"  name="passwd" id="passwd">
                                            </div>
                                        </div>

                                        <div class="space-4"></div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label no-padding-right"><span class="star">*</span>新密码确认</label>

                                            <div class="col-sm-3">
                                                <input required type="password" name="repasswd">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="clearfix" style="background-color: white;margin-top: 20px;">
                            <div class="col-md-offset-3 col-md-9" style="padding-left:15%;">
                                <button class="btn btn-info" type="submit">
                                    <i class="fa fa-save"></i>
                                    保存
                                </button>

                                &nbsp; &nbsp;
                                <button class="btn" type="reset">
                                    <i class="fa fa-undo"></i>
                                    重置
                                </button>
                            </div>
                        </div>
                    </form>
                    </div>
                </div><!-- /.span -->
            </div>
</div>
<script src="${ctx}/assets/js/bootstrap-tag.js"></script>
<script src="${ctx}/assets/js/ace/elements.typeahead.js"></script>
<script src="${ctx}/js/dr.js"></script>
<script>

    function _confirm() {
        if ($('#agree').is(':checked') == false){
            $('#agree').qtip({content: '请您确认您已阅读说明。', show: true});
            return false;
        }
        $("#evaluateForm").ajaxSubmit({
            url: "${ctx}/user/dr/agree",
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
                placeholder:tag_input.attr('placeholder')
            }
        )
    } catch(e) {
        tag_input.after('<textarea id="'+tag_input.attr('id')+'" style="border: 0px white;margin: 0px;width: 100%;height: 95%;" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
    }

    //保存临时数据
    var isSubmit = 0;
    var postViews = ${cm:toJSONObject(postViews)};

    $("#survey input[name=candidateCode]").parent().css({"height": "95%", "width": "95%"})
    $("#survey input[name=candidateCode]").next().css({"font-size": "15px"})

    //保存
    function doTempSave(){
        var onlineId = ${drOnline.id};
        var datas = new Array();
        var others = new Array();
        var flag = 1;   //是否提交数据
        var totalCount = 0;//已投
        var _totalCount = 0;//应投
        var postNames = new Array();

        $.each(postViews, function (i, item) {
            var count = 0;//统计各个推荐职务人数
            var postId = item.id;
            //管理员添加的候选人
            $("table input[postId="+postId+"]:checked").each(function () {
                if($(this).val() == 1) {
                    count++;
                }
                datas.push($(this).attr("id"));
            })
            //参评人添加的候选人
            var userIds = ($("input[name=candidateCode][postId="+postId+"]").val()).split(",");
            //console.log($.trim(userIds))

            //检查是否名字有重复
            if ($.trim(userIds).length != 0){
                for(var i = 0; i < userIds.length; i++){
                    //console.log($.inArray(userIds[i], item.cans))
                    //var index = $.inArray(b, array);
                    //返回 -1 表示没有包含
                    //返回大于 0 表示包含
                    if ($.inArray(userIds[i], item.cans) >= 0){
                        $.tip({
                            $target: $("#survey input[name=candidateCode][postId=" + postId + "]").parent(),
                            at: 'center right', my: 'center left', type: 'info',
                            msg: '候选人姓名重复'
                        });
                        flag = 0;
                        return false;
                    }
                }
                count += userIds.length;
            }
            //console.log(count)
            if (count > item.competitiveNum){
                //console.log($("input[name=candidateCode][postId="+postId+"]"))
                $.tip({
                    $target: $("#survey input[name=candidateCode][postId=" + postId + "]").parent(),
                    at: 'center right', my: 'center left', type: 'info',
                    msg: '该职务只允许推荐' + item.competitiveNum + '人'
                });
                flag = 0;//放在提示信息中，falg赋不上值
                return false;
            }else if (count < item.competitiveNum) {
                postNames.push(item.name);
            }

            var user = "";
            if ($.trim(userIds).length == 0) {
                user = "";
            }else {
                user = postId + "-" + userIds.join("-");
                //console.log($("input[name=candidateCode][postId=" + postId + "]").val())
            }
            others.push(user);
            totalCount += count;
            _totalCount += item.competitiveNum;
            if (flag == 0)return false;
        })

        //console.log(others.length)
        if (flag == 0)return;
        if(isSubmit == 1){
            if (_totalCount > totalCount) {

                SysMsg.warning("还有未完成推荐的职务（" + postNames.join("，") + "）")

                return;
            }else {
                bootbox.confirm('<div style="font-size: 16pt;font-weight: bolder;color:red;margin:10px;">\
					<div style="text-indent:2em;margin:50px 10px 10px 10px;">提交之前，请您确认推荐结果无需再做修改。</div>\
					<div style="text-indent:2em;padding:10px;">为保证您评价信息的安全，在点击确定提交后您的对应账号、密码即失效。<div></div>', function (result) {
                    if (result) {
                        $.post("${ctx}/user/dr/doTempSave?&isSubmit=1",{"datas[]": datas, "others[]": others, "onlineId": onlineId},function(ret) {
                            if (ret.success) {
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
                        })
                    }else {
                        $(this).modal("hide");
                    }
                })
            }
        }else{
            $.post("${ctx}/user/dr/doTempSave?&isSubmit=0",{"datas[]": datas, "others[]": others, "onlineId": onlineId},function(ret) {
                if (ret.success) {
                    SysMsg.success('保存成功。', '成功', function () {
                        location.reload();
                    })
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
    if (tempResult.rawOptionMap != undefined){
        $.each(tempResult.rawOptionMap, function (key, value) {
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
                            _logout();
                        });
                    }
                }
            });
        }
    });

    function _logout(){
        location.href = "${ctx}/user/dr/logout";
    }

</script>
</body>
</html>
