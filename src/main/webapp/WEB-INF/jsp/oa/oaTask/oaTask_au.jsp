<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=OaConstants.OA_TASK_TYPE_MAP%>" var="OA_TASK_TYPE_MAP"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${oaTask!=null}">编辑</c:if><c:if test="${oaTask==null}">添加</c:if>协同办公任务</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/oa/oaTask_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${oaTask.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label">工作类型</label>

            <div class="col-xs-6">
                <c:if test="${fn:length(adminTypes)==1}">
                    ${OA_TASK_TYPE_MAP.get(adminTypes[0])}
                    <input type="hidden" name="type" value="${adminTypes[0]}">
                </c:if>
                <c:if test="${fn:length(adminTypes)>1}">
                    <select required class="form-control" name="type"
                            data-rel="select2"
                            data-width="350"
                            data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${adminTypes}" var="type">
                            <option value="${type}">${OA_TASK_TYPE_MAP.get(type)}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=type]").val('${oaTask.type}');
                    </script>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">标题</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${oaTask.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">应完成时间</label>

            <div class="col-xs-6">
                <div class="input-group">
                    <input required class="form-control datetime-picker" type="text" name="deadline"
                           autocomplete="off" disableautocomplete
                           value="${cm:formatDate(oaTask.deadline, "yyyy-MM-dd HH:mm")}">
								<span class="input-group-addon">
								<i class="fa fa-calendar bigger-110"></i>
							</span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">联系方式</label>

            <div class="col-xs-6">
                <input required class="form-control" type="text" name="contact" value="${oaTask.contact}">
            </div>
        </div>
        <div class="form-group">
            <div class="col-xs-10">
                <div style="text-align: center;width:715px;font-weight: bolder;font-size: 16pt;padding-bottom: 10px;">
                    具体事项
                </div>
                <textarea id="content" class="form-control">${oaTask.content}</textarea>
            </div>
        </div>

    </form>
</div>
<div class="modal-footer">
    <input id="submitBtn" type="button" class="btn btn-primary" value="保存"/>
</div>

<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>
    var ke = KindEditor.create('#content', {
        allowFileManager: true,
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '350px',
        width: '715px'
    });
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                data: {content: ke.html()},
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $.register.datetime($('.datetime-picker'));
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>