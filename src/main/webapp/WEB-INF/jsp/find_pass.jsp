<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <jsp:include page="/WEB-INF/jsp/common/meta.jsp"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
  <meta charset="utf-8"/>
  <title>修改密码-${_plantform_name}</title>
  <link href="${ctx}/assets/css/bootstrap.css" rel="stylesheet" type="text/css" />
<%--  <link rel="stylesheet" href="${ctx}/extend/css/bootstrap-theme-3.3.5.css" />--%>
    <link rel="stylesheet" href="${ctx}/assets/css/ace-nobtn.css" class="ace-main-stylesheet" id="main-ace-style" />
  <link href="${ctx}/extend/css/faq.css" rel="stylesheet" type="text/css" />
  <link href="${ctx}/assets/css/font-awesome.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="top">
  <div class="w1000">
    <div class="logo"><img src="${ctx}/public/logo" /></div>
    <div class="txt">${_plantform_name}</div>
  </div>
</div>
<div class="container">
<div class="row">
    <div class="vspace-16" style="display: block;">&nbsp;</div>
    <div class="widget-box">
        <div class="widget-header widget-header-flat">
            <h4 class="widget-title">修改密码</h4>
            <%--<div class="pull-right" style="margin-top: 2px;margin-right: 5px;">
            <a href="${ctx}/login" class="btn btn-primary ">
                <i class="fa fa-reply"></i>
                返回登录</a>
            </div>--%>
        </div>

        <div class="widget-body">
            <div class="widget-main">
                <ul class="list-unstyled spaced2">
                    <li class="text-warning bigger-110 orange">
                        <i class="ace-icon fa fa-exclamation-triangle"></i>
                        如果您的账号是<b>师大门户账号</b>，请访问信息门户：
                        <a href="http://one.bnu.edu.cn" target="_blank">http://one.bnu.edu.cn</a>，通过【忘记密码】功能修改密码；
                    </li>
                    <li class="green">
                        <i class="ace-icon fa fa-circle green"></i>
                        如果您的账号是在本系统注册的账号，请按如下操作：
                    </li>
                </ul>
                <hr>
                <form class="form-horizontal">
                    <div class="row">
                    <div  class="">
                        <div class="form-group">
                            <label class="col-xs-5 control-label">请输入账号</label>
                            <div class="col-xs-6">
                                <input type="text" name="username"/>
                                <button type="button" class="btn btn-success btn-xs" id="msgBtn"><i class="fa fa-mobile"></i> 发送短信</button>
                                <div id="result" style="margin-top: 5px;display:none">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">短信验证码</label>
                            <div class="col-xs-6">
                                <input type="text" name="code" maxlength="4" style="width: 80px"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">新密码</label>
                            <div class="col-xs-6">
                                <input type="text" onfocus="this.type='password'" name="password" autocomplete="off"/>
                            </div>
                        </div>

                    </div>
                    </div>
                    <div class="clearfix form-actions center">
                        <button class="btn btn-info" type="button" id="submitBtn">
                            <i class="ace-icon fa fa-check bigger-110"></i>
                            确认
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="${ctx}/login" class="btn btn-default">
                            <i class="fa fa-reply"></i>
                            返回登录</a>
                    </div>
                </form>

                <hr>
                <div class="row">
                    <div class="col-xs-12">
                        <div style="font-weight: bolder">注：</div>
                        <ol class="spaced" style="padding-left: 20px;">
                            <li>短信验证码将发到您注册账号时填写的手机上，请注意查收；</li>
                            <li>每天只有5次修改密码的机会，请不要频繁操作；</li>
                            <li>密码修改成功之后，请使用新密码登录系统；</li>
                            <li class="text-danger">如果密码修改失败，请联系您所在的分党委管理员；</li>
                        </ol>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='${ctx}/assets/js/jquery.js'>"+"<"+"/script>");
</script>
<!-- <![endif]-->
<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${ctx}/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script src="${ctx}/assets/js/bootstrap.js"></script>
<script src="${ctx}/extend/js/bootbox.min.js"></script>
<script src="${ctx}/js/jquery.extend.js"></script>
<script>
function _msg(msg){
    $("#result").show().html(msg);
}
$(function(){
    $("#msgBtn").click(function(){

        $("#result").hide().html("");
        var username = $.trim($("input[name=username]").val());
        if(username == ''){
            $("input[name=username]").val("").focus();
            return;
        }
        var $this =  $(this);
        $this.prop("disabled", true);
        $.post("${ctx}/find_pass", {username: username},function(ret){
            if(ret.success){
                if(ret.type==0){
                    _msg("该账号不存在，请重新输入。");
                    $this.prop("disabled", false);
                }else if (ret.type==1){
                    _msg('该账号是信息门户账号，请到<a href="http://one.bnu.edu.cn" target="_blank">http://one.bnu.edu.cn</a>修改密码。');
                    $this.prop("disabled", false);
                }else if (ret.type==2){
                    if(ret.send==0){
                        _msg("该账号没有关联手机号码，请联系党支部或分党委管理员进行修改密码。");
                        $this.prop("disabled", false);
                    }else if(ret.send==1){
                        var msg = "短信验证码已发送至手机号"+ret.mobile+"，短信序号["+ret.seq+"]。";
                        _msg(msg);
                        var limit = 100;
                        var ts = setInterval(function(){
                            //console.log(limit);
                            $this.html('<i class="fa fa-mobile"></i> 重新发送('+(limit--)+')');
                            if(limit<-1){
                                clearInterval(ts);
                                $this.html('<i class="fa fa-mobile"></i> 重新发送').prop("disabled", false);
                            }
                        }, 1000);

                    }else if(ret.send==2){
                        _msg("密码重置失败，请稍后重试。");
                        $this.prop("disabled", false);
                    }
                }

            }else{
                _msg(ret.msg);
                $this.prop("disabled", false);
            }
        });
    });
    //$("input[name=code]").val(' ');
    $("#submitBtn").click(function(){

        var username = $.trim($("input[name=username]").val());
        if(username == ''){
            $("input[name=username]").val("").focus();
            return;
        }
        var code = $.trim($("input[name=code]").val());
        if(code == ''){
            $("input[name=code]").val("").focus();
            return;
        }
        var password = $.trim($("input[name=password]").val());
        if(password == ''){
            $("input[name=password]").val("").focus();
            return;
        }

        $.post("${ctx}/find_pass/changepw",{username:username, code:code, password:password},function(ret){
            if(ret.success){
                SysMsg.info("密码修改成功，请使用新密码登录。", "成功",function(){
                    location.href="${ctx}/login";
                })
            }else{
                SysMsg.info(ret.msg)
            }
        });

    })

})
</script>
</body>
</html>
