<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>切换账号</h3>
</div>
<div class="modal-body">
    <form  class="form-horizontal">
    <div class="form-group">
        <label class="col-xs-3 control-label">登录账号</label>
        <div class="col-xs-6">
            <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects" data-width="350"
                    name="userId" data-placeholder="请选择">
                <option></option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-3 control-label">当前登录状态</label>
        <div class="col-xs-6 label-text" id="loginStatus">

        </div>
    </div>
    </form>
</div>
<div class="modal-footer">

    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="submitBtn" type="button" class="btn btn-primary" value="确定"/>

    <div style="text-align: left;margin-bottom: 10px;padding-top: 10px;">注：
        <br/>
        <div style="color: red">1、如果账号已经登录状态，会踢出该账号。</div>
        <div>2、此操作不产生登录日志。</div>
        3、切换后的账号，如有业务操作，会产生操作日志
    </div>
</div>
<script>
    var user;
    var $select = $.register.user_select($('#modal select[name=userId]'));
    $select.on("change",function(){
        user = $(this).select2("data")[0];
        $("#loginStatus").html("");
        $.getJSON("${ctx}/sysLogin_status",{username: user.username},function(ret){
            if(ret.success){
                if(user.locked){
                    $("#loginStatus").html("禁用账号");
                }else {
                    $("#loginStatus").html(ret.isOnline ? '<span class="text text-danger bolder larger">在线</span>'
                            : '<span class="text text-success">离线</span>');
                }
            }
        })
    });
    $("#submitBtn").click(function(){
        user = user || {};
        if(!user.locked && $.trim(user.username)!=''){
            location.href="${ctx}/cas_test?username="+ user.username;
        }
    })
</script>