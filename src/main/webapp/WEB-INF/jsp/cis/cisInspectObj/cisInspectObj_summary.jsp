<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CisConstants.CIS_INSPECTOR_TYPE_OW%>" var="CIS_INSPECTOR_TYPE_OW"/>
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

                    <div class="row dispatch_cadres" style="width: 1250px">
                        <div class="dispatch" style="width: 450px;margin-right: 0">
                            <div class="widget-box" style="width: 430px;">
                                <div class="widget-header">
                                    <h4 class="smaller">
                                        考察基本情况
                                    </h4>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main" style="margin-bottom: 10px; height: 425px;">
                                        <form class="form-horizontal" action="${ctx}/cisInspectObj_summary"
                                              id="cisForm" method="post">
                                            <input type="hidden" name="id" value="${param.objId}">
                                            <div class="form-group">
                                                <label class="col-xs-4 control-label">谈话人数</label>
                                                <div class="col-xs-6">
                                                    <input class="form-control digits" type="text" name="talkUserCount"
                                                           value="${cisInspectObj.talkUserCount}">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-4 control-label">考察原始记录</label>
                                                <div class="col-xs-6">
                                                    <input class="form-control" type="file" name="_logFile"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-xs-4 control-label">备注</label>

                                                <div class="col-xs-6">
                                                    <textarea class="form-control limited" rows="8" name="remark">${cisInspectObj.remark}</textarea>
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
        <button id="cisBtn" class="btn btn-info" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
                type="button">
            <i class="ace-icon fa fa-save bigger-110"></i>
            保存
        </button>
    </div>
</div>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>

    $.fileInput($("#cisForm input[type=file]"), {
        no_file: '请上传PDF文件 ...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });

    var ke = KindEditor.create('#content', {
        cssPath: "${ctx}/css/ke.css",
        items: ["source", "|", "fullscreen"],
        height: '400px',
        width: '720px'
    });

    $(function () {
        $("#cisBtn").click(function () {
            $("#cisForm").submit();
            return false;
        });
        $("#cisForm").validate({
            submitHandler: function (form) {
                var $btn = $("#cisBtn").button('loading');
                $(form).ajaxSubmit({
                    data: { summary: ke.html()},
                    success: function (data) {
                        if (data.success) {
                            $.hideView()
                        }
                        $btn.button('reset');
                    }
                });
            }
        });
    })
</script>