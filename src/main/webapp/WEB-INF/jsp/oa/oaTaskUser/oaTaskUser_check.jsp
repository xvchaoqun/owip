<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>审核</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/oa/oaTaskUser_check" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="taskId" value="${param.taskId}">
        <input type="hidden" name="taskUserIds" value="${param['taskUserIds']}">
        <c:set var="num" value='${fn:length(fn:split(param["taskUserIds"],","))}'/>
        <div class="form-group">
            <label class="col-xs-4 control-label">任务</label>
            <div class="col-xs-6 label-text">
                ${oaTask.name}
            </div>
        </div>
        <c:if test="${num==1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">任务对象</label>
                <div class="col-xs-6 label-text">
                        ${sysUser.realname}
                </div>
            </div>
        </c:if>
    <c:if test="${num>1}">
        <div class="form-group">
            <label class="col-xs-4 control-label">已选择报送数量</label>
            <div class="col-xs-6 label-text">
                 ${num}个
                     <span class="help-inline">（注：本操作只对已上报、未审核的报送进行审核，否则忽略）</span>
            </div>
        </div>
    </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">是否审核通过</label>
            <div class="col-xs-6">
                <input type="checkbox" class="big" name="pass"  data-off-text="不通过" data-on-text="通过" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control limited" type="text"
                          name="remark" maxlength="200"></textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <div class="note">该操作仅对已报送的任务对象有效</div>
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="submitBtn" type="button" class="btn btn-primary" value="确定"/>
</div>

<script>
    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        $("#jqGrid2").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('textarea.limited').inputlimiter();
</script>