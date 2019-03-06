<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${oaTask.name}-附件</h3>
</div>
<div class="modal-body">
    <shiro:hasPermission name="oaTask:edit">
        <form class="form-horizontal" action="${ctx}/oa/oaTaskFile_au" id="modalForm" method="post">
            <input type="hidden" name="taskId" value="${oaTask.id}">
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
    </shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/oa/oaTaskFiles?taskId=${oaTask.id}">
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
            <c:forEach items="${oaTaskFiles}" var="oaTaskFile" varStatus="st">
                <tr>
                    <td nowrap>${oaTaskFile.fileName}</td>
                    <td nowrap>
                        <div class="hidden-sm hidden-xs action-buttons">
                            <shiro:hasPermission name="oaTask:delFile">
                                <button class="confirm btn btn-danger btn-xs"
                                        data-title="删除"
                                        data-msg="确定删除？"
                                        data-callback="_pop_reload"
                                        data-url="${ctx}/oa/oaTaskFile_del?id=${oaTaskFile.id}">
                                    <i class="fa fa-times"></i> 删除
                                </button>
                            </shiro:hasPermission>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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