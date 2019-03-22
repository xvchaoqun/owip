<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>添加到培训班</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cet/cetTrainCourse_selectCourses" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="courseIds[]" value="${param['ids[]']}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所选课程数量</label>
            <div class="col-xs-6 label-text">
                <div>${fn:length(fn:split(param['ids[]'],","))} 门</div>
                <div class="label-inline"> （提示：培训班已有的课程将忽略）</div>
            </div>

        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">培训班</label>
            <div class="col-xs-8">
                <div class="input-group">
                    <select data-width="350"
                            data-ajax-url="${ctx}/cet/cetTrain_selects"
                            name="trainId" data-placeholder="请输入培训班名称">
                        <option></option>
                    </select>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    function _templateResult(state) {

        var $state = state.text;
        if ($.trim(state.sn) != '')
            $state += ($state != '' ? '-' : '') + state.sn;

        // 反转义
        return $('<div/>').html($state).text();
    }
    $.register.ajax_select($("#modalForm select[name=trainId]"),
            {templateResult:_templateResult, templateSelection:_templateResult})
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        SysMsg.success("课程添加成功。");
                    }
                }
            });
        }
    });
</script>