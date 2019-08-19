<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <span class="bolder" style="cursor: auto;padding-left: 20px;font-size:larger">
            <c:if test="${oaTask!=null}">编辑</c:if><c:if test="${oaTask==null}">添加</c:if>协同办公任务
        </span>
    </div>
    <div class="widget-body" style="padding-top: 20px;width: 1000px">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/oa/oaTask_au" autocomplete="off" disableautocomplete
                  id="modalForm" method="post">
                <input type="hidden" name="id" value="${oaTask.id}">

                <div class="form-group">
                    <label class="col-xs-2 control-label"><c:if test="${fn:length(oaTaskTypes)>1}"><span
                            class="star">*</span></c:if>工作类型</label>
                    <c:if test="${fn:length(oaTaskTypes)==1}">
                        <div class="col-xs-6 label-text">
                                ${cm:getMetaType(oaTaskTypes[0]).name}
                            <input type="hidden" name="type" value="${oaTaskTypes[0]}">
                        </div>
                    </c:if>
                    <c:if test="${fn:length(oaTaskTypes)>1}">
                        <c:if test="${empty oaTask}">
                            <div class="col-xs-6">
                                <select required class="form-control" name="type"
                                        data-rel="select2"
                                        data-width="150"
                                        data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="${oaTaskTypes}" var="oaTaskType">
                                        <c:set var="_type" value="${cm:getMetaType(oaTaskType)}"/>
                                        <option value="${_type.id}">${_type.name}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#modalForm select[name=type]").val('${oaTask.type}');
                                </script>
                            </div>
                        </c:if>
                        <c:if test="${not empty oaTask}">
                            <div class="col-xs-6 label-text">
                                    ${cm:getMetaType(oaTask.type).name}
                                <input type="hidden" name="type" value="${oaTask.type}">
                            </div>
                        </c:if>
                    </c:if>
                </div>
                <div class="form-group">
                    <label class="col-xs-2 control-label"><span class="star">*</span>标题</label>

                    <div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${oaTask.name}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-2 control-label"><span class="star">*</span>应完成时间</label>

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
                    <label class="col-xs-2 control-label"><span class="star">*</span>联系方式</label>

                    <div class="col-xs-6">
                        <input required class="form-control" type="text" name="contact" value="${oaTask.contact}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-2 control-label">具体事项</label>
                    <div class="col-xs-8">
                        <textarea id="content" class="form-control">${oaTask.content}</textarea>
                    </div>
                </div>

            </form>
            <div class="modal-footer center">
                <button id="submitBtn" type="button" class="btn btn-success btn-lg"><i class="fa fa-check"></i> 保存
                </button>
            </div>
            <!-- /.widget-main -->
        </div>
        <!-- /.widget-body -->
    </div>
    <!-- /.widget-box -->
</div>

<script>
    var ke = KindEditor.create('#content', {
        allowFileManager: true,
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '350px',
        width: '715px'
    });
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                data: {content: ke.html()},
                success: function (ret) {
                    if (ret.success) {
                        //$("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $.register.datetime($('.datetime-picker'));
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>