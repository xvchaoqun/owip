<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div style="width: 500px;float: left;margin-right: 25px">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="smaller">
                设置
            </h4>
        </div>
        <div class="widget-body">
            <div class="widget-main" id="qualification-content">
                <form class="form-horizontal" action="${ctx}/cet/cetProject_detail_setting" id="timeForm" method="post">
                    <input type="hidden" name="projectId" value="${param.projectId}">
                    <div class="form-group">
                        <label class="col-xs-5 control-label">达到结业要求的学时数</label>
                        <div class="col-xs-5">
                            <input required class="form-control period" type="text" name="requirePeriod" value="${cetProject.requirePeriod}">
                        </div>
                    </div>

                    <div class="modal-footer center" style="margin-top: 22px;">
                        <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
                        <input type="submit" class="btn btn-primary" value="确定"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div style="clear: both"></div>
<script>
    $("#timeForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        SysMsg.success("设置成功。",function(){
                            _detailReload()
                        })
                    }
                }
            });
        }
    });
</script>