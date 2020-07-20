<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="OW_ORG_ADMIN_PARTY" value="<%=OwConstants.OW_ORG_ADMIN_PARTY%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>切换账号</h3>
</div>
<div class="modal-body">
    <form  class="form-horizontal">
    <div class="form-group">
        <label class="col-xs-4 control-label">登录账号</label>
        <div class="col-xs-7">
            <c:choose>
                <c:when test="${cm:isPermitted('sysLogin:switch')}">
                    <c:set var="select_url" value="sysUser_selects"/>
                </c:when>
                <c:when test="${cm:isPermitted('sysLogin:switchParty')}">
                    <c:set var="select_url" value="orgAdmin_selects?type=${OW_ORG_ADMIN_PARTY}"/>
                </c:when>
                <c:otherwise>
                    <c:set var="select_url" value=""/>
                </c:otherwise>
            </c:choose>
            <select data-rel="select2-ajax"
                    data-ajax-url="${ctx}${param.mobile==1?'/m':''}/${select_url}"
                    data-width="100%"
                    name="userId" data-placeholder="请选择">
                <option></option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-xs-4 control-label">当前登录状态</label>
        <div class="col-xs-7 label-text" id="loginStatus">

        </div>
    </div>
    </form>
</div>
<div class="modal-footer">

    <button id="_submitBtn" data-loading-text='<i class="fa fa-spinner fa-spin"></i> 切换中'
            type="button" disabled class="linkBtn btn btn-primary">确定</button>

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
        if($.trim(user.username)==''){
            $("#loginStatus").html("");
            $("#_submitBtn").prop("disabled", true);
            return;
        }
        $("#loginStatus").html("");
        $.getJSON("${ctx}${param.mobile==1?'/m':''}/sysLogin_switch_status",{username: user.username},function(ret){
            if(ret.success){
                if(!ret.canSwitch){
                    $("#loginStatus").html("该账号无法切换");
                }else if(user.locked){
                    $("#loginStatus").html("禁用账号");
                }else {
                    var isOnline = (ret.onlineSession!=undefined);

                    $("#loginStatus").html(isOnline ? ('<table><tr><td><span class="text text-danger bolder larger">在线</span></td></tr>' +
                    '<tr><td>登录时间：{0}</td></tr><tr><td>最新操作时间：{1}</td></tr></table>')
                            .format($.date(ret.onlineSession.startTimestamp, 'yyyy-MM-dd HH:mm:ss'),
                            $.date(ret.onlineSession.lastAccessTime, 'yyyy-MM-dd HH:mm:ss'))
                            : '<span class="text text-success">离线</span>');
                }

                if(ret.canSwitch && !user.locked && $.trim(user.username)!='' && $.trim(user.username)!='${_user.username}'){
                    $("#_submitBtn").data("url", "${ctx}${param.mobile==1?'/m':''}/login_switch?username="+ user.username)
                            .prop("disabled", false);
                }else{
                    $("#_submitBtn").prop("disabled", true);
                }
            }
        })
    });
</script>