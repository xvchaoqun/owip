<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
</head>
<body class="no-skin">
<div id="navbar" class="navbar navbar-default navbar-fixed-top">
    <%--<script type="text/javascript">
      try{ace.settings.check('navbar' , 'fixed')}catch(e){}
    </script>--%>
    <div class="navbar-container" id="navbar-container">
        <!-- #section:basics/sidebar.mobile.toggle -->
        <shiro:lacksRole name="reg">
            <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
                <span class="sr-only">Toggle sidebar</span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>
            </button>
        </shiro:lacksRole>
        <div class="navbar-header pull-left hidden-xs hidden-sm">
            <div class="logo" style="cursor: pointer;" onclick="location.href='#'">
                <t:img src="/img/logo_white.png"/></div>
            <div class="separator"></div>
            <div class="txt" style="cursor: pointer;" onclick="location.href='#'">${_plantform_name}</div>
        </div>
        <div class="navbar-header pull-left hidden-md hidden-lg ">
            <a href="${ctx}/" class="navbar-brand">
                <small style="cursor: pointer;">
                    ${cm:getSysConfig().siteShortName}
                </small>
            </a>
        </div>
    </div>
    <div class="navbar-buttons navbar-header pull-right" role="navigation">
        <div style="position: absolute;top:10px;right: 35px;float: right;font-size: 14px">
            <c:if test="${cm:isPermitted('sysLogin:switch') || cm:isPermitted('sysLogin:switchParty')}">
                <c:if test="${empty sessionScope._switchUser}">
                    <a href="javascript:;"
                       data-url="${ctx}/sysLogin_switch"
                       class="popupBtn">
                        <i class="fa fa-refresh"></i> 切换账号
                    </a>
                </c:if>
            </c:if>
            <c:if test="${not empty sessionScope._switchUser}">
                <a href="${ctx}/sysLogin_switch_back">
                    <i class="fa fa-reply"></i> 返回主账号
                </a>
            </c:if>
        </div>
        <c:set var="isSuperAccount" value="${cm:isSuperAccount(_user.username)}"/>
        <ul class="nav nav-pills hidden-xs hidden-sm hidden-md" style="margin-left: 0px">
            <li class="">
                <a href="javascript:;" class="hashchange" data-url="${ctx}/profile"><i
                        class="fa fa-user ${isSuperAccount?'light-green':''}"></i>
                    <span class="${isSuperAccount?'light-green':''}">${_user.realname}<c:if
                            test="${_user.code!=_user.realname}">(${_user.code})</c:if></span></a>
            </li>
            <c:if test="${!_p_hideHelp}">
                <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_PARTYADMIN},${ROLE_BRANCHADMIN}">
                    <li class="<c:if test="${_path=='/help'}">active</c:if>">
                        <a href="${ctx}/help"><i class="ace-icon fa fa-question-circle"></i> 帮助文档</a>
                    </li>
                </shiro:hasAnyRoles>
            </c:if>
            <shiro:hasPermission name="shortMsgTpl:*">
                <li>
                    <a href="javascript:;" class="hashchange" data-url="${ctx}/shortMsgTpl"><i class="fa fa-send"></i>
                        定向消息</a>
                </li>
            </shiro:hasPermission>
            <li>
                <a href="${ctx}/logout"><i class="ace-icon fa fa-power-off"></i> 退出</a>
            </li>
        </ul>
        <ul class="nav nav-pills hidden-lg" style="margin-left: 0px">
            <li class="dropdown">

                <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span> ${_user.realname}<c:if
                        test="${_user.code!=_user.realname}">(${_user.code})</c:if></span>
                    <i class="fa fa-caret-down"> </i></a>
                <ul class="dropdown-menu">
                    <li class="">
                        <a href="javascript:;" class="hashchange" data-url="${ctx}/profile"><i class="fa fa-user"></i>
                            个人信息
                        </a>
                    </li>
                    <c:if test="${!_p_hideHelp}">
                        <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_ODADMIN},${ROLE_PARTYADMIN},${ROLE_BRANCHADMIN}">
                            <li class="<c:if test="${_path=='/help'}">active</c:if>">
                                <a href="${ctx}/help"><i class="ace-icon fa fa-question-circle"></i> 帮助文档</a>
                            </li>
                        </shiro:hasAnyRoles>
                    </c:if>
                    <shiro:hasPermission name="shortMsgTpl:*">
                        <li>
                            <a href="javascript:;" class="hashchange" data-url="${ctx}/shortMsgTpl"><i
                                    class="fa fa-send"></i> 定向消息</a>
                        </li>
                    </shiro:hasPermission>
                    <li>
                        <a href="${ctx}/logout"><i class="ace-icon fa fa-power-off"></i> 退出</a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
    <!-- /.navbar-container -->
</div>
<div class="main-container" id="main-container">
    <shiro:lacksRole name="reg">
        <div id="sidebar" class="sidebar responsive sidebar-fixed">
            <c:import url="/menu?isMobile=0"/>
        </div>
    </shiro:lacksRole>
    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs" id="breadcrumbs" style="display: none;">

            </div>
            <div class="page-content">
                <shiro:hasPermission name="cadre:archive">
                    <div class="ace-settings-container" id="ace-settings-container">
                        <div class="btn btn-app btn-xs btn-info ace-settings-btn" id="ace-settings-btn">
                            <i class="ace-icon fa fa-cog bigger-130"></i>
                        </div>
                        <div class="ace-settings-box clearfix" id="ace-settings-box">
                            <div style="width: 600px; height: 400px;">
                                <h4 class="header">干部电子档案查询</h4>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                                        name="userId" data-placeholder="请输入账号或姓名或工号">
                                    <option></option>
                                </select>
                                <!-- 不能使用button，干部档案页可能会disabled -->
                                <a href="javascript:;" class="searchBtn btn btn-primary btn-sm"
                                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 查询中">
                                    <i class="fa fa-search"></i> 查询
                                </a>
                                <div class="resultDiv row">

                                </div>
                            </div>
                        </div>
                    </div>
                    <script type="text/template" id="archive_search_tpl">
                        <div class="space-4"></div>
                        <div class="col-xs-12">
                            <blockquote>
                                <small>
                                    姓名：<span>{{=ret.realname}}</span>
                                </small>
                                <small>
                                    查询结果：<span>{{=ret.msg}}</span>
                                    <div class="space-4"></div>
                                    <div>
                                    {{if(ret.cadreId>0){}}
                                        &nbsp;&nbsp;<a href="javascript:;" onclick="_loadArchive(this)" class="btn btn-success btn-xs"
                                                       data-url="{{=ret.url}}"><i
                                            class="fa fa-hand-o-right"></i> 前往查看</a>
                                    {{}else{}}
                                        <shiro:hasRole name="${ROLE_CADREADMIN}">
                                        &nbsp;&nbsp;<a href="javascript:;" onclick="_loadArchive(this)" class="btn btn-warning btn-xs"
                                                       data-url="{{=ret.url}}"><i
                                            class="fa fa-hand-o-right"></i> 建立临时干部档案</a>
                                        </shiro:hasRole>
                                    {{}}}
                                    </div>
                                </small>
                            </blockquote>
                        </div>
                    </script>
                </shiro:hasPermission>
                <div id="page-content"></div>
            </div>
            <!-- /.page-content -->
        </div>
    </div>
    <!-- /.main-content -->

    <div class="footer">
        <div class="footer-inner">
            <div class="footer-content">
            <span class="bigger-120">
                ${_sysConfig.siteCopyright}
            </span>
            </div>
        </div>
    </div>

    <a href="javascript:;" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div>
<!-- /.main-container -->
<div id="modal" class="modal fade" data-backdrop="static" tabindex="-1" data-keyboard="false">
    <div class="modal-dialog" role="document" <%--style="min-width: 650px;"--%>>
        <div class="modal-content">
        </div>
    </div>
</div>
<script src="${ctx}/js/extra.js"></script>
<jsp:include page="/WEB-INF/jsp/common/scripts.jsp"></jsp:include>
<script type="text/javascript">
    try {
        ace.settings.check('sidebar', 'collapsed')
    } catch (e) {
    }
    window.onload = function () {
        $(".sidebar ul.submenu").each(function () {
            if ($("li", this).length == 0) $(this).remove();
        })
        $(window).trigger('hashchange');
    }

    <shiro:hasPermission name="cadre:archive">
    $('#ace-settings-btn').on(ace.click_event, function(e){
        e.preventDefault();
        $(this).toggleClass('open');
        $('#ace-settings-box').toggleClass('open');
     })

    $.register.user_select($('#ace-settings-box select[name=userId]'));
    $('#ace-settings-box select[name=userId]').on("change", function(){
        $("#ace-settings-box .resultDiv").html('')
    })
    function _loadArchive(btn){
        var breadcrumbs = _.template($("#breadcrumbs_tpl").html())({data: {cur:{'name':'干部电子档案'}, parents:[{}]}});
        $("#breadcrumbs").html(breadcrumbs)
        $.loadPage({url:$(btn).data("url"), callback:function(){
            $("#sidebar .nav-list li").removeClass("active open");
            $("#sidebar .nav-list li .submenu").hide();
            $('#ace-settings-btn').click();}});
    }
    $('#ace-settings-box .searchBtn').click(function () {
        var $select = $('#ace-settings-box select[name=userId]');
        var userId = $select.val();
        if (userId == '') {
            $.tip({
                $target: $select.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请选择账号"
            });
            return;
        }
        var $btn = $(this).button('loading');
        $.post("${ctx}/cadre_archive_search", {userId: userId}, function (ret) {
            if (ret.success) {

                ret.url = '${ctx}/cadre_archive?userId=' + ret.userId;
                $("#ace-settings-box .resultDiv").html(_.template($("#archive_search_tpl").html())({ret: ret}));
            }
            $btn.button('reset');
        })
    })
    </shiro:hasPermission>
     <shiro:lacksRole name="reg">

        $.getJSON('${ctx}/info', function (ret) {
            var menuCountMap = ret.menuCountMap;
            //console.log(menuCountMap)
            $.each(menuCountMap, function(key, val){
                //console.log(key+"="+ val)
                $("#sidebar ul li[data-permission='"+ key +"'] .badge").html(val);
            })
        })
    </shiro:lacksRole>
</script>
<!-- inline scripts related to this page -->
<script src="${ctx}/assets/js/flot/jquery.flot.js"></script>
<script src="${ctx}/assets/js/flot/jquery.flot.pie.js"></script>
<script src="${ctx}/assets/js/flot/jquery.flot.resize.js"></script>
<script src="${ctx}/assets/js/ace/elements.scroller.js"></script>
<script src="${ctx}/extend/js/highcharts.js"></script>
<script type="text/template" id="common_sort_tpl">
    <a href="javascript:;" class="jqOrderBtn" data-grid-id="{{=grid}}" data-url="{{=url}}" data-id="{{=id}}"
       data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
    <input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
           title="修改操作步长">
    <a href="javascript:;" class="jqOrderBtn" data-grid-id="{{=grid}}" data-url="{{=url}}" data-id="{{=id}}"
       data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>
</script>
<script type="text/template" id="breadcrumbs_tpl">
    <ul class="breadcrumb">
        {{_.each(data.parents, function(p, idx){ }}
        {{if(idx==0){}}
        <li>
            <i class="ace-icon fa fa-home home-icon"></i>
            <a href="${ctx}/">回到首页</a>
        </li>
        {{}}}
        {{if(idx>=1){}}
        <li>
            {{=p.name}}
        </li>
        {{}}}
        {{});}}
        {{if(data.parents.length>=1){}}
        <li class="active">{{=data.cur.name}}</li>
        {{}}}
    </ul>
</script>
</body>
</html>
