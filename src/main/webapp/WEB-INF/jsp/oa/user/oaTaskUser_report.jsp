<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=OaConstants.OA_TASK_USER_STATUS_PASS%>" var="OA_TASK_USER_STATUS_PASS"/>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main" style="width: 900px">
            <form class="form-horizontal" action="${ctx}/user/oa/oaTaskUser_report" autocomplete="off" disableautocomplete id="modalForm" method="post">
                <input type="hidden" name="taskId" value="${oaTaskUser.taskId}">
                <table class="table table-bordered table-unhover">
                    <c:if test="${param.type!='report'}">
                    <tr>
                        <td width="100">标题</td>
                        <td colspan="3">${oaTask.name}</td>
                    </tr>
                    <tr>
                        <td>具体事项</td>
                        <td colspan="3" class="unpre">${cm:htmlUnescape(oaTask.content)}</td>
                    </tr>
                    <tr>
                        <td>附件</td>
                        <td colspan="3">
                            <c:forEach var="file" items="${oaTaskFiles}">
                                <div>
                                    <a href="${ctx}/attach/download?path=${cm:encodeURI(file.filePath)}&filename=${cm:encodeURI(file.fileName)}">${file.fileName}</a>
                                </div>
                            </c:forEach>
                        </td>

                    </tr>
                    <tr>
                        <td>应完成时间</td>
                        <td width="250">${cm:formatDate(oaTask.deadline, "yyyy-MM-dd HH:mm")}</td>
                        <td width="100">联系方式</td>
                        <td>${oaTask.contact}</td>
                    </tr>
                    </c:if>
                    <c:if test="${param.type=='report' || oaTaskUser.status==OA_TASK_USER_STATUS_PASS}">
                    <tr>
                        <td style="vertical-align: middle">报送内容</td>
                        <td colspan="3">
                            <c:if test="${oaTaskUser.status==OA_TASK_USER_STATUS_PASS}">
                                <div class="unpre">
                                        ${cm:htmlUnescape(oaTaskUser.content)}
                                </div>
                            </c:if>
                            <c:if test="${oaTaskUser.status!=OA_TASK_USER_STATUS_PASS}">
                                <textarea id="content" class="form-control">${oaTaskUser.content}</textarea>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: middle">附件</td>
                        <td colspan="3">
                            <c:forEach var="file" items="${oaTaskUserFiles}">
                                <div class="file">
                                    <a href="${ctx}/attach/download?path=${cm:encodeURI(file.filePath)}&filename=${cm:encodeURI(file.fileName)}"
                                       title="${file.fileName}">${cm:cnsubstr(file.fileName, 40, '...')}</a>
                                    <c:if test="${oaTaskUser.status!=OA_TASK_USER_STATUS_PASS}">
                                        <a href="javascript:;" class="confirm"
                                           data-url="${ctx}/user/oa/oaTaskUser_batchDelFiles?ids[]=${file.id}"
                                           data-msg="确认删除该附件？"
                                           data-callback="_delFileCallback">删除</a>
                                    </c:if>
                                </div>
                            </c:forEach>
                            <c:if test="${oaTaskUser.status!=OA_TASK_USER_STATUS_PASS}">
                                <div class="files">
                                    <input class="form-control" type="file" name="_files[]"/>
                                </div>
                                <button type="button" onclick="addFile()"
                                        class="addFileBtn btn btn-default btn-xs"><i class="fa fa-plus"></i></button>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td style="vertical-align: middle">备注</td>
                        <td colspan="3">
                            <c:if test="${oaTaskUser.status!=OA_TASK_USER_STATUS_PASS}">
                            <textarea name="remark" maxlength="100" rows="3" }
                                      class="form-control limited">${oaTaskUser.remark}</textarea>
                            </c:if>
                            <c:if test="${oaTaskUser.status==OA_TASK_USER_STATUS_PASS}">
                                ${oaTaskUser.remark}
                            </c:if>
                        </td>
                    </tr>
                        </c:if>
                    <c:if test="${param.type=='report'}">
                        <tr>
                            <td colspan="4">
                                <div class="modal-footer center">
                                    <button type="button" id="submitBtn" data-loading-text="提交中..."
                                            class="btn btn-success btn-lg"><i class="fa fa-check"></i> 确定报送
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                </table>
            </form>
        </div>
    </div>
</div>
<style>
    .files {
        width: 300px;
        float: left;
        padding-top: 10px;
    }

    .addFileBtn {
        position: absolute;
        margin: 12px 20px;
    }

    .file {
        height: 25px;
        line-height: 25px;
        background: #FFFFFF url(/img/dot_25.gif);
        width: 345px;
        /*border-bottom: dotted 1px;*/
    }

    .file a.confirm {
        float: right;
    }

    .file:hover {
        background-color: #eadda9;
    }
</style>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>
    var ke = KindEditor.create('#content', {
        allowFileManager: true,
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '350px',
        width: '770px'
    });

    $.fileInput($('input[type=file]'), {
        no_file: '请上传附件...'
    });

    function addFile() {
        var _file = $('<input class="form-control" type="file" name="_files[]" />');
        $(".files").append(_file);
        $.fileInput(_file, {
            no_file: '请上传附件...'
        });
        return false;
    }

    function _delFileCallback(btn) {

        $(btn).closest(".file").remove();
    }

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({

        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                data: {content: ke.html()},
                success: function (ret) {
                    if (ret.success) {

                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('textarea.limited').inputlimiter();
</script>