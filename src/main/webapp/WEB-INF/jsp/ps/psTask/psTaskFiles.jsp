<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${psTask.name}-附件</h3>
</div>
<div class="modal-body">
    <shiro:hasPermission name="oaTask:edit">
        <form class="form-horizontal" action="${ctx}/ps/psTaskFile_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
            <input type="hidden" name="taskId" value="${psTask.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>上传附件(批量)</label>
                <div class="col-xs-6">
                    <input required class="form-control" type="file" name="files" multiple="multiple"/>
                </div>
            </div>
            <div class="modal-footer">
                 <button id="submitBtn" type="button" class="btn btn-primary"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中，请不要关闭此窗口"> 确定</button>
            </div>

        </form>
        <div class="space-10"></div>
    </shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/ps/psTaskFiles?taskId=${psTask.id}">
        <table class="table table-actived table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>附件</th>
                <th width="100"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${psTaskFiles}" var="psTaskFile" varStatus="st">
                <tr>
                    <td nowrap>${psTaskFile.fileName}</td>
                    <td nowrap>
                        <div class="hidden-sm hidden-xs action-buttons">
                            <shiro:hasPermission name="psTask:edit">
                                <button class="confirm btn btn-danger btn-xs"
                                        data-title="删除"
                                        data-msg="确定删除？"
                                        data-callback="_pop_reload"
                                        data-url="${ctx}/ps/psTaskFile_del?id=${psTaskFile.id}">
                                    <i class="fa fa-times"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <button class="downloadBtn btn btn-xs btn-info" data-type="download"
                               data-url="${ctx}/attach_download?path=${cm:encodeURI(psTaskFile.filePath)}&filename=${psTaskFile.fileName}">
                                <i class="fa fa-download"></i> 下载</button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script>
    //$.fileInput($('#modalForm input[type=file]'));
    $.fileInput($("#modalForm input[type=file]"),{
        no_file:'请选择pdf或word文件',
        allowExt: ['pdf', 'doc', 'docx'],
        allowMime: ['application/pdf','application/msword',
            'application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    });

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
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        _pop_reload();
                    }
                     $btn.button('reset');
                }
            });
        }
    });
</script>