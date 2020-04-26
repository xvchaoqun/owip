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
                                            <div class="treeDiv" data-add="1" data-mobile="0" style="height: 238px;"></div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-11" style="margin:10px;">
                                            <div class="treeDiv" data-add="1" data-mobile="1" style="height: 238px;"></div>
                                        </div>
                                    </div>
                            </div>
                            <div class="col-xs-6" style="width: 480px;float: left;margin-left:15px;border: 1px dotted">
                                <div class="title"  style="height: 40px;line-height: 50px;font-weight: bolder;">账号减权限：</div>
                                <div class="form-group">
                                    <div class="col-xs-11" style="margin:10px;">
                                        <div class="treeDiv" data-add="0" data-mobile="0"  style="height: 238px;"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-11" style="margin:10px;">
                                        <div class="treeDiv" data-add="0" data-mobile="1"  style="height: 238px;"></div>
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
    function _initMenu(isMobile) {
        var $sidebar = $(isMobile==1?"#m-sidebar-review":"#sidebar-review");
        $("a", $sidebar).removeClass("hashchange").removeAttr("href")
        $("#sidebar-collapse", $sidebar).removeClass("sidebar-collapse")
        $("ul.submenu", $sidebar).each(function () {
            if ($("li", this).length == 0) {
                $(this).closest("li").find(".menu-text").css("color", "red");
                $(this).remove();
            }
        })
        //console.log("-----------------" + $("#sidebar-review #sidebar-collapse").attr("class"))
    }
    _initMenu(0);
    _initMenu(1);

    var addTreeData = ${addTree};
    addTreeData.title = "网页端资源";
    initTree(1, 0, addTreeData)
    var mAddTreeData = ${mAddTree};
    mAddTreeData.title = "手机端资源";
    initTree(1, 1, mAddTreeData)

    var minusTreeData = ${minusTree};
    minusTreeData.title = "网页端资源";
    initTree(0, 0, minusTreeData)

    var mMinusTreeData = ${mMinusTree};
    mMinusTreeData.title = "手机端资源";
    initTree(0, 1, mMinusTreeData)

    function initTree(add, mobile, initData){

        $("div.treeDiv[data-add="+add+"][data-mobile="+mobile+"]").dynatree({
            checkbox: true,
            selectMode: 2,
            children: initData,
            onSelect: function (select, node) {
                //node.expand(node.data.isFolder && node.isSelected());
                <shiro:hasPermission name="menu:preview">
                var resIds = $.map($("div.treeDiv[data-add=1][data-mobile="+mobile+"]").dynatree("getSelectedNodes"), function (node) {
                    //if(!node.data.isFolder)
                    return node.data.key;
                });
                var minusResIds = $.map($("div.treeDiv[data-add=0][data-mobile="+mobile+"]").dynatree("getSelectedNodes"), function (node) {
                    //if(!node.data.isFolder)
                    return node.data.key;
                });
                var $sidebar = $(mobile==1?"#m-sidebar-review":"#sidebar-review");
                $('.sidebar',$sidebar).load("${ctx}/menu_preview",{isMobile:mobile, resIds:resIds, minusResIds:minusResIds},function(){
                    _initMenu(mobile);
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
    }

    $("#submitBtn").click(function () {
        $("#submitForm").submit();
        return false;
    });
    $("#submitForm").validate({
        submitHandler: function (form) {

            var addIds = $.map($("div.treeDiv[data-add=1][data-mobile=0]").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            var mAddIds = $.map($("div.treeDiv[data-add=1][data-mobile=1]").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            var minusIds = $.map($("div.treeDiv[data-add=0][data-mobile=0]").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            var mMinusIds = $.map($("div.treeDiv[data-add=0][data-mobile=1]").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                data: {addIds: addIds, mAddIds:mAddIds,minusIds: minusIds, mMinusIds:mMinusIds},
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