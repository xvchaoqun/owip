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
                  id="taskForm" method="post">
                <input type="hidden" name="id" value="${oaTask.id}">

                <div class="form-group">
                    <label class="col-xs-2 control-label"><c:if test="${fn:length(oaTaskTypes)>1}"><span
                            class="star">*</span></c:if>工作类型</label>
                    <div class="col-xs-6">
                    <select required name="type" data-width="270"
                            data-rel="select2" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_oa_task_type"/>
                    </select>
                    </div>
                </div>
                <script>
                    $("#taskForm select[name=type]").val('${oaTask.type}');
                </script>
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
                <div class="form-group">
                    <label class="col-xs-2 control-label"><span class="star">*</span>报送文件数量</label>
                    <div class="col-xs-6">
                        <input required class="form-control digits" type="text"
                               name="userFileCount" value="${empty oaTask.userFileCount?0:oaTask.userFileCount}">
                        <span class="help-block">注：此处填写任务对象报送时必须上传的文件数量，如果不要求上传文件请填0</span>
                    </div>
                </div>
            </form>
            <div class="modal-footer center">
                <button id="taskSubmitBtn" type="button" class="btn btn-success btn-lg"><i class="fa fa-check"></i> 保存
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
    $("#taskSubmitBtn").click(function () {
        $("#taskForm").submit();
        return false;
    });
    $("#taskForm").validate({
        submitHandler: function (form) {
            var $btn = $("#taskSubmitBtn").button('loading');
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
    $("#taskForm :checkbox").bootstrapSwitch();
    $('#taskForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>