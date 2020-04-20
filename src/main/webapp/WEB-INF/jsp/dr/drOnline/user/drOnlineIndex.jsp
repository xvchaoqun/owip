<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <jsp:include page="/WEB-INF/jsp/common/m_head.jsp"></jsp:include>
    <title>线上民主推荐投票</title>
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
    </style>
</head>
<body>
<div id="navbar" class="navbar navbar-default navbar-fixed-top" style="width: 70%;border-radius:10px;margin-top: 10px;margin-left: 15%;margin-right: 15%;padding-left: 10%;">
    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left hidden-xs hidden-sm">
            <div class="logo" style="cursor: pointer;" onclick="location.href='#'">
                <t:img src="/img/logo_white.png"/></div>
            <div class="separator"></div>
            <div class="txt" style="cursor: pointer;">线上民主推荐投票</div>
        </div>
        <div class="navbar-header pull-left hidden-md hidden-lg ">
            <a href="${ctx}/" class="navbar-brand">
                <small style="cursor: pointer;">
                    线上民主推荐投票
                </small>
            </a>
        </div>
    </div>
    <div class="navbar-buttons navbar-header pull-right" role="navigation">
        <ul class="nav nav-pills hidden-xs hidden-sm hidden-md" style="margin-left: 0px">
            <li class="">
                <a href="javascript:void(0)" onclick="drOnline_eva()"><i class="ace-icon fa fa-check"></i>测评</a>
            </li>
            <li>
                <a href="javascript:void(0)" onclick="drOnline_notice()"><i class="ace-icon fa fa-file-powerpoint-o"></i>测评说明</a>
            </li>
            <li>

                <a href="javascript:void(0)" onclick="drOnline_changePasswd()"><i class="ace-icon fa fa-question-circle"></i> 修改密码</a>
            </li>
            <li>
                <a href="${ctx}/dr/drOnline/logout"><i class="ace-icon fa fa-power-off"></i> 退出</a>
            </li>
        </ul>
        <ul class="nav nav-pills hidden-lg" style="margin-left: 0px">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span> 功能操作</span>
                    <i class="fa fa-caret-down"> </i></a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="javascript:void(0)" onclick="drOnline_eva()"><i class="ace-icon fa fa-check"></i>测评</a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" onclick="drOnline_notice()"><i class="ace-icon fa fa-file-powerpoint-o"></i>测评说明</a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" onclick="drOnline_changePasswd()"><i class="ace-icon fa fa-question-circle"></i> 修改密码</a>
                    </li>
                    <li>
                        <a href="${ctx}/dr/drOnline/logout"><i class="ace-icon fa fa-power-off"></i> 退出</a>
                    </li>
                </ul>
            </li>
        </ul>
        <%--<ul class="nav nav-pills" hidden>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span> 功能操作</span>
                    <i class="fa fa-caret-down"> </i></a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="javascript:void(0)" onclick="drOnline_eva()"><i class="ace-icon fa fa-check"></i>测评</a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" onclick="drOnline_notice()"><i class="ace-icon fa fa-file-powerpoint-o"></i>测评说明</a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" onclick="drOnline_changePasswd()"><i class="ace-icon fa fa-question-circle"></i> 修改密码</a>
                    </li>
                    <li>
                        <a href="${ctx}/dr/drOnline/logout"><i class="ace-icon fa fa-power-off"></i> 退出</a>
                    </li>
                </ul>
            </li>
        </ul>--%>
    </div>
</div>
<div class="main-container" id="main-container" style="padding-top: 100px;margin-left: 15%;margin-right: 15%">
    <div class="row " id="content" style="padding-left: 7%;padding-right: 7%"><br>
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
    <div class="main-content eva">
        <div class="col-xs-11" style="padding-left: 12%">
            <div class="col-sm-offset-1 col-sm-10">
                <div style="text-align: center">
                <form id="evaluateForm"  method="post">
                    <c:if test="${!tempResult.agree}">
                        <div style="width:70%; margin:0 auto;">
                            <div class="modal-header" style="padding-top: 20px!important;" align="center">
                                <h2>测评说明</h2>
                            </div>
                            <div class="modal-body" style="text-align: left;word-wrap:break-word">
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
                <form id="survey" method="post" display>
                    <c:if test="${tempResult.agree}">
                    <table class="table-bordered" style="width: 80%;margin: 0 auto;" >
                        <thead>
                        <tr style="height: 70px">
                            <th colspan="3" style="font-size: 30px!important;">
                                干部民主推荐表&nbsp;<a href="#"><font size="2">${drOnline.code}</font></a>
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
                                            <input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>"
                                                   postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="输入后请按回车键" />
                                        </td>
                                        <td>
                                            <div>
                                                <label>--</label>
                                            </div>
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
                                            <input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>"
                                                   postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="输入后请按回车键" />
                                        </td>
                                        <td>
                                            <div>
                                                <label>--</label>
                                            </div>
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
                                            <input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>"
                                                   postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="输入后请按回车键" />
                                        </td>
                                        <td>
                                            <div>
                                                <label>--</label>
                                            </div>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!postView.hasCandidate}">
                                    <tr>
                                        <td>${postView.name}（${postView.competitiveNum}人）</td>
                                        <td>
                                            <input type="text" value="<c:forEach items="${tempResult.otherResultMap}" var="to"><c:if test="${to.key==postView.id}">${to.value}</c:if></c:forEach>"
                                                   postId="${postView.id}" style="width: 100%;height: 100%;" name="candidateCode" class="form-field-tags"  placeholder="输入后请按回车键" />
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
    </div>
    <div class="main-content notice" disabled hidden>
        <div class="col-xs-11" style="padding-left: 12%">
            <div class="col-sm-offset-1 col-sm-10">
                    <div class="modal-header" style="text-align: center">
                        <h2>干部民主推荐说明</h2>
                    </div>
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
                        <form class="form-horizontal" id="form" action="${ctx}/dr/drOnline/user/changePasswd"  method="post">
                            <div class="tabbable"><%--
            <jsp:include page="../sys/profile/menu.jsp"/>--%>

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
                            <div class="col-md-offset-3 col-md-9" style="padding-left:15%">
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
                placeholder:tag_input.attr('placeholder')
            }
        )
    } catch(e) {
        tag_input.after('<textarea id="'+tag_input.attr('id')+'" style="border: 0px white;margin: 0px;width: 100%;height: 95%;" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
    }

    //保存临时数据
    var isSubmit = 0;
    var postViews = ${cm:toJSONObject(postViews)};
    function doTempSave(){
        var onlineId = ${drOnline.id};
        var datas = new Array();
        var others = new Array();
        var flag = 1;   //是否提交数据
        var totalCount = 0;//已投
        var _totalCount = 0;//应投

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
                        SysMsg.info('候选人姓名重复，请加以区别！', '提示',function () {
                            return;
                        })
                        flag = 0;
                    }
                }
                count += userIds.length;
            }
            //console.log(count)
            if (count > item.competitiveNum){
                SysMsg.info(item.name + '中投同意票的总数，超过了最大推荐人数' + item.competitiveNum + ',请重选！', '提示',function () {
                    return;
                })
                flag = 0;//放在提示信息中，falg赋不上值
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
                SysMsg.info('请完成推荐表后，再进行提交。', '提示',function () {
                    return;
                })
            }else {
                SysMsg.confirm('提交成功将直接退出系统，然后该账号不能登录。请谨慎提交！', '确认提交',function () {
                    $.post("${ctx}/dr/drOnline/doTempSave?&isSubmit=1",{"datas[]": datas, "others[]": others, "onlineId": onlineId},function(ret) {
                        if (ret.success) {
                            SysMsg.success('提交成功。', '提交', function(){
                                location.href ="${ctx}/dr/drOnline/logout"
                            });
                        }
                    })
                })
            }
        }else{
            SysMsg.confirm("确认保存投票信息。", "保存", function () {
                $.post("${ctx}/dr/drOnline/doTempSave?&isSubmit=0",{"datas[]": datas, "others[]": others, "onlineId": onlineId},function(ret) {
                    if (ret.success) {
                        SysMsg.success('保存成功。', '成功', function () {
                            location.reload();
                        })
                    }
                })
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
