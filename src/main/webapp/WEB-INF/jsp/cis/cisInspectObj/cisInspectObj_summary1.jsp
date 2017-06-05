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
                    <a href="javascript:;">编辑考察材料</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <div class="tab-content">
                <form action="${ctx}/cisInspectObj_summary" id="modalFrom" method="post">
                    <div class="row dispatch_cadres" style="width: 1250px">
                        <div class="dispatch" style="width: 450px;">
                            <div class="widget-box" style="width: 400px;">
                                <div class="widget-header">
                                    <h4 class="smaller">
                                        请选择单位
                                    </h4>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main" style="margin-bottom: 10px">
                                        <div id="tree3" style="height: 550px;">
                                            <div class="block-loading"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cadres">
                            <div class="widget-box" style="width: 750px;">
                                <div class="widget-header">
                                    <h4 class="smaller">
                                        考察报告
                                    </h4>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main" style="margin-bottom: 10px">
                                        <textarea id="content">${cisInspectObj.summary}</textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="clearfix form-actions center">
        <button class="btn btn-info" type="submit">
            <i class="ace-icon fa fa-save bigger-110"></i>
            保存
        </button>
    </div>
</div>

<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>

    var ke = KindEditor.create('#content', {
        cssPath: "${ctx}/css/ke.css",
        items: ["source", "|", "fullscreen"],
        height: '550px',
        width: '700px'
    });

    $(function () {
        $.getJSON("${ctx}/cisObjUnits_tree", {objId: "${param.objId}"}, function (data) {
            var treeData = data.tree.children;
            $("#tree3").dynatree({
                checkbox: true,
                selectMode: 3,
                children: treeData,
                onSelect: function (select, node) {

                    node.expand(node.data.isFolder && node.isSelected());
                },
                cookieId: "dynatree-Cb3",
                idPrefix: "dynatree-Cb3-"
            });
        });

        $("#item-content button[type=submit]").click(function () {
            $("#modalFrom").submit();
            return false;
        });
        $("#modalFrom").validate({
            submitHandler: function (form) {
                var unitIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
                    if (!node.data.isFolder && !node.data.hideCheckbox)
                        return node.data.key;
                });

                $(form).ajaxSubmit({
                    data: {unitIds: unitIds, objId: "${param.objId}", summary: ke.html()},
                    success: function (data) {
                        if (data.success) {
                            $.hideView()
                        }
                    }
                });
            }
        });
    })
</script>