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
                    <a href="javascript:;">${op}角色</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main" style="padding: 0px 5px">
            <div class="tab-content" style="padding-bottom: 0px">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="col-xs-6" style="width: 600px;float: left">
                            <form class="form-horizontal" id="submitForm" action="${ctx}/sysRole_au" method="post">
                                <input type="hidden" name="id" value="${sysRole.id}">

                                <div class="form-group">
                                    <label class="col-xs-2 control-label">代码</label>

                                    <div class="col-xs-9">
                                        <input required class="form-control" ${sysRole.role eq 'admin'?'disabled':''}
                                        type="text" name="role" value="${sysRole.role}">
                                        <span class="help-block small danger">如需修改，请联系系统开发人员</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label required class="col-xs-2 control-label">名称</label>

                                    <div class="col-xs-9">
                                        <input class="form-control" type="text" name="description"
                                               value="${sysRole.description}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 control-label">备注</label>

                                    <div class="col-xs-9">
                                        <textarea class="form-control" name="remark">${sysRole.remark}</textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 control-label" style="height: 203px;line-height: 203px">网页端资源</label>
                                    <div class="col-xs-9">
                                        <div id="tree3" style="height: 203px;"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 control-label" style="height: 203px;line-height: 203px">手机端资源</label>
                                    <div class="col-xs-9">
                                        <div id="m_tree3" style="height: 203px;"></div>
                                    </div>
                                </div>

                            </form>
                        </div>
                        <div class="col-xs-6 sidebar-review">
                            <div id="sidebar-review" style="float: left;margin-right: 20px;">
                                <div class="title">网页菜单预览：</div>
                                <div class="sidebar">
                                    <c:import url="/menu_preview_byRoleId?roleId=${sysRole.id}&isMobile=0"/>
                                </div>
                            </div>
                            <div id="m-sidebar-review">
                                <div class="title">手机菜单预览：</div>
                                <div class="sidebar">
                                    <c:import url="/menu_preview_byRoleId?roleId=${sysRole.id}&isMobile=1"/>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class=" form-actions center" style="margin: 0px;">
        <button id="submitBtn" class="btn btn-info" type="submit">
            <i class="ace-icon fa fa-check bigger-110"></i>
            ${op}
        </button>
    </div>
</div>
<%--<style>
    ul.dynatree-container {
        border: none;
    }
</style>--%>
<script>
    $('#sidebar-review .sidebar').ace_sidebar();

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


    var treeData = ${tree};
    treeData.title = "选择资源";
    $("#tree3").dynatree({
        checkbox: true,
        selectMode: 2,
        children: treeData,
        onSelect: function (select, node) {
            //node.expand(node.data.isFolder && node.isSelected());
            var resIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            $('#sidebar-review .sidebar').load("${ctx}/menu_preview?isMobile=0",{resIds:resIds},function(){
                _initMenu();
            });
        },
        onCustomRender: function (node) {
            if (node.data.tooltip != null)
                return "<a href='#' class='dynatree-title' title='{0}'>{1}[{0}]</a>"
                        .format(node.data.tooltip, node.data.title)
        },
        cookieId: "dynatree-Cb3",
        idPrefix: "dynatree-Cb3-"
    });

    var m_treeData = ${mTree};
    m_treeData.title = "选择资源";
    $("#m_tree3").dynatree({
        checkbox: true,
        selectMode: 2,
        children: m_treeData,
        onSelect: function (select, node) {
            //node.expand(node.data.isFolder && node.isSelected());
            var resIds = $.map($("#m_tree3").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            $('#m-sidebar-review .sidebar').load("${ctx}/menu_preview?isMobile=1",{resIds:resIds},function(){
                _m_initMenu();
            });
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

            var resIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });
            var m_resIds = $.map($("#m_tree3").dynatree("getSelectedNodes"), function (node) {
                //if(!node.data.isFolder)
                return node.data.key;
            });

            $(form).ajaxSubmit({
                data: {resIds: resIds, m_resIds:m_resIds},
                success: function (data) {
                    if (data.success) {
                        $.hideView();
                    }
                }
            });
        }
    });
</script>