<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <a href="javascript:;">讨论议题</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8 row">
                <form class="form-horizontal" action="${ctx}/sc/scGroupTopic_au" id="modalForm" method="post"
                      enctype="multipart/form-data">
                    <div style="width: 500px;float: left;margin-right: 25px">
                        <div class="widget-box" style="height: 500px;">
                            <div class="widget-header">
                                <h4 class="smaller">
                                    ${scGroupTopic!=null?"修改":"添加"}讨论议题
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <div class="row">
                                        <input type="hidden" name="id" value="${scGroupTopic.id}">
                                        <input type="hidden" name="filePath" value="${scGroupTopic.filePath}">

                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">所属干部小组会</label>

                                            <div class="col-xs-6">
                                                <select required name="groupId" data-rel="select2"
                                                        data-width="240"
                                                        data-placeholder="请选择">
                                                    <option></option>
                                                    <c:forEach var="scGroup" items="${scGroups}">
                                                        <option value="${scGroup.id}">
                                                            干部小组会[${cm:formatDate(scGroup.holdDate, "yyyyMMdd")}]号
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                                <script>
                                                    $("#modalForm select[name=groupId]").val("${groupId}");
                                                </script>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">议题名称</label>

                                            <div class="col-xs-6">
                                                <textarea class="form-control noEnter" rows="3"
                                                          name="name">${scGroupTopic.name}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">涉及单位</label>

                                            <div class="col-xs-6">
                                                <div class="input-group">
                                                    <select class="multiselect" multiple="" name="unitIds">
                                                        <c:forEach var="unitType"
                                                                   items="${cm:getMetaTypes('mc_unit_type')}">
                                                            <c:set var="unitList"
                                                                   value="${unitListMap.get(unitType.value.id)}"/>
                                                            <c:if test="${fn:length(unitList)>0}">
                                                                <optgroup label="${unitType.value.name}">
                                                                    <c:forEach
                                                                            items="${unitList}"
                                                                            var="unitId">
                                                                        <c:set var="unit"
                                                                               value="${unitMap.get(unitId)}"></c:set>
                                                                        <option value="${unit.id}">${unit.name}</option>
                                                                    </c:forEach>
                                                                </optgroup>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                    <span class="help-block">（正在运转单位）</span>

                                                    <div class="space-4"></div>
                                                    <select class="multiselect" multiple="" name="historyUnitIds">
                                                        <c:forEach var="unitType"
                                                                   items="${cm:getMetaTypes('mc_unit_type')}">
                                                            <c:set var="unitList"
                                                                   value="${historyUnitListMap.get(unitType.value.id)}"/>
                                                            <c:if test="${fn:length(unitList)>0}">
                                                                <optgroup label="${unitType.value.name}">
                                                                    <c:forEach
                                                                            items="${unitList}"
                                                                            var="unitId">
                                                                        <c:set var="unit"
                                                                               value="${unitMap.get(unitId)}"></c:set>
                                                                        <option value="${unit.id}">${unit.name}</option>
                                                                    </c:forEach>
                                                                </optgroup>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                    <span class="help-block">（历史单位）</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">附件（批量）</label>

                                            <div class="col-xs-6">
                                                <input class="form-control" type="file" name="files"
                                                       multiple="multiple"/>
                                                <c:if test="${not empty scGroupTopic.filePath}">
                                                    已上传附件：
                                                <c:forEach var="file" items="${fn:split(scGroupTopic.filePath,',')}" varStatus="vs">
                                                    <a href="${ctx}/attach/download?path=${cm:encodeURI(file)}&filename=附件${vs.count}">附件${vs.count}</a>
                                                    ${vs.last?"":"、"}
                                                </c:forEach>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">备注</label>

                                            <div class="col-xs-6">
                                        <textarea class="form-control limited" rows="4"
                                                  name="remark">${scGroupTopic.remark}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="width: 500px;float: left;margin-right: 25px">
                        <div class="widget-box">
                            <div class="widget-header">
                                <h4 class="smaller">
                                    议题内容
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <textarea id="contentId">${scGroupTopic.content}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="width: 500px;float: left;margin-right: 25px">
                        <div class="widget-box">
                            <div class="widget-header">
                                <h4 class="smaller">
                                    议题讨论备忘
                                </h4>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main">
                                    <textarea id="memoId">${scGroupTopic.memo}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div style="clear: both"></div>
                    <div class="clearfix form-actions center">
                        <button class="btn btn-info btn-sm" type="button" id="submitBtn">
                            <i class="ace-icon fa fa-check "></i>
                            ${scGroupTopic!=null?"修改":"添加"}
                        </button>
                    </div>
                </form>
            </div>
            <!-- /.widget-main -->
        </div>
        <!-- /.widget-body -->
    </div>
    <!-- /.widget-box -->
</div>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script src="${ctx}/assets/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-multiselect.css"/>
<script>
    var contentKe = KindEditor.create('#contentId', {
        //cssPath:"${ctx}/css/ke.css",
        items: ["wordpaste", "source", "|", "fullscreen"],
        height: '450px',
        width: '480px',
        minWidth: 480,
        filterMode:false,
        pasteType:0
    });

    var memoKe = KindEditor.create('#memoId', {
        //cssPath:"${ctx}/css/ke.css",
        items: ["wordpaste", "source", "|", "fullscreen"],
        height: '450px',
        width: '480px',
        minWidth: 480,
        filterMode:false,
        pasteType:0
    });

    $.fileInput($('#modalForm input[type=file]'));

    var doubleUnitIds = ${selectUnitIds};
    $.register.multiselect($('#modalForm select[name=unitIds]'), doubleUnitIds, {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false, buttonWidth: '240px'
    });

    $.register.multiselect($('#modalForm select[name=historyUnitIds]'), doubleUnitIds, {
        enableClickableOptGroups: true,
        enableCollapsibleOptGroups: true, collapsed: true, selectAllJustVisible: false, buttonWidth: '240px'
    });

    $.register.date($('.date-picker'));
    $('#modalForm [data-rel="select2"]').select2();
    $("#upload-file").change(function () {
        if ($("#upload-file").val() != "") {
            var $this = $(this);
            var $form = $this.closest("form");
            var $btn = $("button", $form).button('loading');
            var viewHtml = $("#dispatch-file-view").html()
            $("#dispatch-file-view").html('<img src="${ctx}/img/loading.gif"/>')
            $form.ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //console.log(ret)
                        $("#dispatch-file-view").load("${ctx}/swf/preview?type=html&path=" + encodeURI(ret.filePath));

                        $("#modalForm input[name=filePath]").val(ret.filePath);
                    } else {
                        $("#dispatch-file-view").html(viewHtml)
                    }
                    $btn.button('reset');
                    $this.removeAttr("disabled");
                }
            });
            $this.attr("disabled", "disabled");
        }
    });
    var selectedUnitIds = [];
    $("#submitBtn").click(function () {
        selectedUnitIds = $.map($('#modalForm select[name=unitIds] option:selected, ' +
                '#modalForm select[name=historyUnitIds] option:selected'), function (option) {
            return $(option).val();
        });
        /*if (selectedUnitIds.length == 0) {
            $.tip({
                $target: $("#modalForm select[name=unitIds]").closest(".input-group"),
                at: 'right center', my: 'left center', type: 'info',
                msg: "请选择单位。"
            });
        }*/
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            /*if (selectedUnitIds.length == 0) {
                return;
            }*/
            $(form).ajaxSubmit({
                data: {unitIds: selectedUnitIds, content: contentKe.html(), memo: memoKe.html()},
                success: function (ret) {
                    if (ret.success) {
                        $.hideView();
                    }
                }
            });
        }
    });
</script>