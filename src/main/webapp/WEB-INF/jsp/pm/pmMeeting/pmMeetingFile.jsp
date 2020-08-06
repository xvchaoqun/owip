<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pmMeeting.name}-附件</h3>
</div>
<div class="modal-body">

        <form class="form-horizontal" action="${ctx}/pmMeetingFile_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
            <input type="hidden" name="id" value="${pmMeeting.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>上传附件(批量)</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="file" name="files" multiple="multiple"/>
                </div>
            </div>
            <div class="modal-footer">
                <button id="submitBtn" class="btn btn-primary"><i class="fa fa-upload"></i> 上传</button>
            </div>

        </form>
        <div class="space-10"></div>

    <div class="popTableDiv"
         data-url-page="${ctx}/pmMeetingFile?id=${pmMeeting.id}">
        <table class="table table-actived table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>附件</th>
                <th width="100">
                    <%--<shiro:hasPermission name="oaTask:delFile">
                        <button class="delBtn btn btn-danger btn-xs" data-id="${oaTaskFile.id}">
                            <i class="fa fa-trash"></i> 全部清空
                        </button>
                    </shiro:hasPermission>--%>
                </th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${pmMeetingFiles}" var="pmMeetingFile" varStatus="st">
                <tr>
                    <td nowrap >${pmMeetingFile.fileName}</td>
                    <td nowrap>
                        <div class="hidden-sm hidden-xs action-buttons">
                                <button class="confirm btn btn-danger btn-xs"
                                        data-title="删除"
                                        data-msg="确定删除？"
                                        data-callback="_pop_reload"
                                        data-url="${ctx}/pmMeetingFile_del?id=${pmMeetingFile.id}">
                                    <i class="fa fa-times"></i> 删除
                                </button>
                            <button class="downloadBtn btn btn-xs btn-info" data-type="download"
                               data-url="${ctx}/attach_download?path=${cm:sign(pmMeetingFile.filePath)}&filename=${pmMeetingFile.fileName}">
                                <i class="fa fa-download"></i> 下载</button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${empty pmMeetingFiles}">    无</c:if>
    </div>
</div>
<script>
    $.fileInput($('#modalForm input[type=file]'));
    function _pop_reload(){
        pop_reload();
        $(window).triggerHandler('resize.jqGrid');
    }
    $("#submitBtn", "#modalForm").click(function () {
        $("#modalForm").submit();
        return false;
    })
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        _pop_reload();
                    }
                }
            });
        }
    });
</script>