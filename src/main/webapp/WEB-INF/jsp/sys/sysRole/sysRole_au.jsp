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
        <div class="widget-main">
            <div class="tab-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="col-xs-6" style="width: 600px;float: left">
                            <form class="form-horizontal" id="submitForm" action="${ctx}/sysRole_au" method="post">
                                <input type="hidden" name="id" value="${sysRole.id}">

                                <div class="form-group">
                                    <label class="col-xs-2 control-label">代码</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control" ${sysRole.role eq 'admin'?'disabled':''}
                                        type="text" name="role" value="${sysRole.role}">
                                        <span class="help-block small danger">如需修改，请联系系统开发人员</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label required class="col-xs-2 control-label">名称</label>

                                    <div class="col-xs-6">
                                        <input class="form-control" type="text" name="description"
                                               value="${sysRole.description}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 control-label">备注</label>

                                    <div class="col-xs-6">
                                        <textarea class="form-control" name="remark">${sysRole.remark}</textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-2 control-label">拥有的资源</label>

                                    <div class="col-xs-9">
                                        <div id="tree3" style="height: 450px;"></div>
                                    </div>
                                </div>

                            </form>
                        </div>
                        <div class="col-xs-6" id="sidebar-review">
                            <div class="title">菜单预览：</div>
                            <div class="sidebar">
                                <c:import url="/menu_preview_byRoleId?roleId=${sysRole.id}"/>
                            </div>
                            <script type="text/javascript">
                                //$("#sidebar-review a").attr("href", "#")
                                $("#sidebar-review a").removeClass("hashchange").removeAttr("href")
                                $("#sidebar-review ul.submenu").each(function(){
                                    if($("li", this).length==0){
                                        $(this).closest("li").find(".menu-text").css("color", "red");
                                        $(this).remove();
                                    }
                                })
                            </script>

                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class=" form-actions center">
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
            $('#sidebar-review .sidebar').load("${ctx}/menu_preview",{resIds:resIds},function(){
                //$('#sidebar-review .sidebar').ace_sidebar();
                //$("#sidebar-review a").attr("href", "#")
                $("#sidebar-review a").removeClass("hashchange").removeAttr("href")
                $("#sidebar-review ul.submenu").each(function(){
                    if($("li", this).length==0){
                        $(this).closest("li").find(".menu-text").css("color", "red");
                        $(this).remove();
                    }
                })
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

            $(form).ajaxSubmit({
                data: {resIds: resIds},
                success: function (data) {
                    if (data.success) {
                        $.hideView();
                    }
                }
            });
        }
    });
</script>