<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="closeView btn btn-xs btn-success">
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

                    <div class="row dispatch_cadres" style="width: 1250px">
                        <div class="dispatch" style="width: 450px;">
                            <div class="widget-box" style="width: 430px;">
                                <div class="widget-header">
                                    <h4 class="smaller">
                                        考察基本情况
                                    </h4>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main" style="margin-bottom: 10px">
                                        <form class="form-horizontal" action="${ctx}/cisInspectObj_summary" id="modalFrom" method="post">
                                            <input type="hidden" name="id" value="${param.objId}">
                                            <div class="form-group">
                                                <label class="col-xs-4 control-label">考察单位</label>
                                                <div class="col-xs-6">
                                                    <select class="multiselect" name="unitIds[]" multiple="" >
                                                        <c:forEach items="${runUnits}" var="unit">
                                                            <option value="${unit.id}">${unit.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-4 control-label">谈话人数</label>
                                                <div class="col-xs-6">
                                                    <input class="form-control digits" type="text" name="talkUserCount"
                                                           value="${cisInspectObj.talkUserCount}">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-4 control-label">考察对象时任职务</label>
                                                <div class="col-xs-6">
                                                    <input class="form-control" type="text" name="post"
                                                           value="${cisInspectObj.post}">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-4 control-label">考察对象拟任职务</label>
                                                <div class="col-xs-6">
                                                    <input class="form-control" type="text" name="assignPost"
                                                           value="${cisInspectObj.assignPost}">
                                                </div>
                                            </div>
                                        </form>
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
<div class="footer-margin"/>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script src="${ctx}/assets/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-multiselect.css" />
<script>

    register_multiselect($('#modalFrom select[name="unitIds[]"]'), ${selectIds});

    var ke = KindEditor.create('#content', {
        cssPath: "${ctx}/css/ke.css",
        items: ["source", "|", "fullscreen"],
        height: '550px',
        width: '700px'
    });

    $(function () {
       /* $.getJSON("${ctx}/cisObjUnits_tree", {objId: "${param.objId}"}, function (data) {
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
        });*/

        $("#item-content button[type=submit]").click(function () {
            $("#modalFrom").submit();
            return false;
        });
        $("#modalFrom").validate({
            submitHandler: function (form) {
                /*var unitIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
                    if (!node.data.isFolder && !node.data.hideCheckbox)
                        return node.data.key;
                });*/

                $(form).ajaxSubmit({
                    data: { summary: ke.html()},
                    success: function (data) {
                        if (data.success) {
                            _closeView()
                        }
                    }
                });
            }
        });
    })
</script>