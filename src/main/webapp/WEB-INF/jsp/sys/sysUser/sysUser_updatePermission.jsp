<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">账号权限</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main" style="padding: 0px 5px">
            <div class="tab-content" style="padding-bottom: 0px">
                <div class="row">
                    <div class="col-xs-12">
                        <form class="form-horizontal" id="submitForm" action="${ctx}/sysUser_updatePermission" method="post">
                            <input type="hidden" name="userId" value="${sysUserInfo.userId}">

                            <div class="col-xs-6" style="width: 480px;float: left;margin-left:15px;border: 1px dotted">
                                    <div class="title" style="height: 40px;line-height: 50px;font-weight: bolder;">账号加权限：</div>
                                    <div class="form-group">
                                        <div class="col-xs-11" style="margin:10px;">
                                            <div id="addTree" style="height: 238px;"></div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-11" style="margin:10px;">
                                            <div id="m_addTree" style="height: 238px;"></div>
                                        </div>
                                    </div>
                            </div>
                            <div class="col-xs-6" style="width: 480px;float: left;margin-left:15px;border: 1px dotted">
                                <div class="title"  style="height: 40px;line-height: 50px;font-weight: bolder;">账号减权限：</div>
                                <div class="form-group">
                                    <div class="col-xs-11" style="margin:10px;">
                                        <div id="minusTree" style="height: 238px;"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-11" style="margin:10px;">
                                        <div id="m_minusTree" style="height: 238px;"></div>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <shiro:hasPermission name="menu:preview">
                            <div class="col-xs-6 sidebar-review" style="margin-left:15px">
                                <div id="sidebar-review" style="float: left;margin-right: 20px;">
                                    <div class="title">网页菜单预览：
                                        <div style="position: absolute;left: 120px;top: 0px;">
                                            <a href="javascript:;" class="popupBtn btn btn-xs btn-success" data-width="800" data-url="${ctx}/sysUser_permissions?userId=${sysUserInfo.userId}&isMobile=0">
                                                <i class="ace-icon fa fa-search"></i>
                                                查看权限</a>
                                        </div>
                                    </div>
                                    <div class="sidebar">
                                        <c:import url="/menu_preview_byUser?userId=${sysUserInfo.userId}&isMobile=0"/>
                                    </div>
                                </div>
                                <div id="m-sidebar-review">
                                    <div class="title">手机菜单预览：
                                        <div style="position: absolute;left: 340px;top: 0px;">
                                            <a href="javascript:;" class="popupBtn btn btn-xs btn-success" data-width="800" data-url="${ctx}/sysUser_permissions?userId=${sysUserInfo.userId}&isMobile=1">
                                                <i class="ace-icon fa fa-search"></i>
                                                查看权限</a>
                                        </div>
                                    </div>
                                    <div class="sidebar">
                                        <c:import url="/menu_preview_byUser?userId=${sysUserInfo.userId}&isMobile=1"/>
                                    </div>
                                </div>
                            </div>
                        </shiro:hasPermission>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class=" form-actions center" style="margin: 0px;">
        <button id="submitBtn" type="button" class="btn btn-primary"
                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
            <i class="ace-icon fa fa-check bigger-110"></i> 提交</button>
    </div>
</div>
<div class="footer-margin lower"/>
<script>
    $('#sidebar-review .sidebar').ace_sidebar();
    $('#m-sidebar-review .sidebar').ace_sidebar();

    $("input[type=checkbox]").click(function(){
        if($(this).prop("checked")){
            $("input[type=checkbox]").not(this).prop("checked", false);
        }
    });
    function _initMenu() {

        $("#sidebar-review a").removeClass("hashchange").removeAttr("href")
        $("#sidebar-review #sidebar-collapse").removeClass("sidebar-collapse")
        $("#sidebar-review ul.submenu").each(function () {
            if ($("li", this).length == 0) {
                $(this).closest("li").find(".menu-text").css("color", "red");
                $(this).remove();
            }
        })
        //console.log("-----------------" + $("#sidebar-review #sidebar-collapse").attr("class"))
    }
    _initMenu();
    function _m_initMenu() {

        $("#m-sidebar-review a").removeClass("hashchange").removeAttr("href")
        $("#m-sidebar-review #sidebar-collapse").removeClass("sidebar-collapse")
        $("#m-sidebar-review ul.submenu").each(function () {
            if ($("li", this).length == 0) {
                $(this).closest("li").find(".menu-text").css("color", "red");
                $(this).remove();
            }
        })
        //console.log("-----------------" + $("#sidebar-review #sidebar-collapse").attr("class"))
    }
    _m_initMenu()


    var addTreeData = ${addTree};
    addTreeData.title = "网页端资源";
    $("#addTree").dynatree({
        checkbox: true,
        selectMode: 2,
        children: addTreeData,
        onSelect: function (select, node) {
            //node.expand(node.data.isFolder && node.isSelected());
            <shiro:hasPermission name="menu:preview">
            var resIds = $.map($("#addTree").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            $('#sidebar-review .sidebar').load("${ctx}/menu_preview?isMobile=0",{resIds:resIds},function(){
                _initMenu();
            });
            </shiro:hasPermission>
        },
        onCustomRender: function (node) {
            if (node.data.tooltip != null)
                return "<a href='#' class='dynatree-title' title='{0}'>{1}[{0}]</a>"
                    .format(node.data.tooltip, node.data.title)
        },
        cookieId: "dynatree-Cb3",
        idPrefix: "dynatree-Cb3-"
    });

    var m_addTreeData = ${m_addTree};
    m_addTreeData.title = "手机端资源";
    $("#m_addTree").dynatree({
        checkbox: true,
        selectMode: 2,
        children: m_addTreeData,
        onSelect: function (select, node) {
            //node.expand(node.data.isFolder && node.isSelected());
            <shiro:hasPermission name="menu:preview">
            var resIds = $.map($("#m_addTree").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            $('#m-sidebar-review .sidebar').load("${ctx}/menu_preview?isMobile=1",{resIds:resIds},function(){
                _m_initMenu();
            });
            </shiro:hasPermission>
        },
        onCustomRender: function (node) {
            if (node.data.tooltip != null)
                return "<a href='#' class='dynatree-title' title='{0}'>{1}[{0}]</a>"
                    .format(node.data.tooltip, node.data.title)
        },
        cookieId: "dynatree-Cb3",
        idPrefix: "dynatree-Cb3-"
    });

    var minusTreeData = ${minusTree};
    minusTreeData.title = "网页端资源";
    $("#minusTree").dynatree({
        checkbox: true,
        selectMode: 2,
        children: minusTreeData,
        onSelect: function (select, node) {
            //node.expand(node.data.isFolder && node.isSelected());
            <shiro:hasPermission name="menu:preview">
            var resIds = $.map($("#minusTree").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            $('#sidebar-review .sidebar').load("${ctx}/menu_preview?isMobile=0",{resIds:resIds},function(){
                _initMenu();
            });
            </shiro:hasPermission>
        },
        onCustomRender: function (node) {
            if (node.data.tooltip != null)
                return "<a href='#' class='dynatree-title' title='{0}'>{1}[{0}]</a>"
                    .format(node.data.tooltip, node.data.title)
        },
        cookieId: "dynatree-Cb3",
        idPrefix: "dynatree-Cb3-"
    });

    var m_minusTreeData = ${m_minusTree};
    m_minusTreeData.title = "手机端资源";
    $("#m_minusTree").dynatree({
        checkbox: true,
        selectMode: 2,
        children: m_minusTreeData,
        onSelect: function (select, node) {
            //node.expand(node.data.isFolder && node.isSelected());
            <shiro:hasPermission name="menu:preview">
            var resIds = $.map($("#m_minusTree").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            $('#m-sidebar-review .sidebar').load("${ctx}/menu_preview?isMobile=1",{resIds:resIds},function(){
                _m_initMenu();
            });
            </shiro:hasPermission>
        },
        onCustomRender: function (node) {
            if (node.data.tooltip != null)
                return "<a href='#' class='dynatree-title' title='{0}'>{1}[{0}]</a>"
                    .format(node.data.tooltip, node.data.title)
        },
        cookieId: "dynatree-Cb3",
        idPrefix: "dynatree-Cb3-"
    });
    $("#submitBtn").click(function () {
        $("#submitForm").submit();
        return false;
    });
    $("#submitForm").validate({
        submitHandler: function (form) {

            var addIds = $.map($("#addTree").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            var m_addIds = $.map($("#m_addTree").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            var minusIds = $.map($("#minusTree").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            var m_minusIds = $.map($("#m_minusTree").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                data: {addIds: addIds, m_addIds:m_addIds,minusIds: minusIds, m_minusIds:m_minusIds},
                success: function (data) {
                    if (data.success) {
                        $.reloadMetaData(function(){
                            $.hideView();
                        });
                    }else {
                        $btn.button('reset');
                    }
                }
            });
        }
    });
</script>