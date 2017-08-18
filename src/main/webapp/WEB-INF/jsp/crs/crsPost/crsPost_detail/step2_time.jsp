<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<c:set var="isOpen" value="${crsPost.switchStatus==CRS_POST_ENROLL_STATUS_OPEN}"/>
<c:set var="isClosed" value="${crsPost.switchStatus==CRS_POST_ENROLL_STATUS_CLOSED}"/>
<c:set var="isPause" value="${crsPost.switchStatus==CRS_POST_ENROLL_STATUS_PAUSE}"/>
<div class="row" style="width: 850px">
    <div class="alert alert-warning" style="">
        说明：<br/>
        1）自动开关设定好之后，如果手动开关没有任何操作，那么严格按照设定的实际来自动开启和关闭；<br/>
        2）自动开关设定好之后，如果手动开关有操作，那么以手动开关为准。<br/>
        3）自动开关不设定，那么以手动开关为准。<br/>
        当前状态：
        <div style="margin-left: 30px">
        报名时间：${cm:formatDate(crsPost.startTime, "yyyy-MM-dd HH:mm")}${cm:formatDate(crsPost.endTime, " ~ yyyy-MM-dd HH:mm")}<br/>
        报名开关：
            <c:if test="${isOpen}">
            <span class="label label-success"><i class="fa fa-check"></i> 开启（${crsPost.autoSwitch?'自动':'手动'}）</span>
            </c:if>
            <c:if test="${isClosed}">
            <span class="label label-danger"><i class="fa fa-times"></i> 关闭（${crsPost.autoSwitch?'自动':'手动'}）</span>
            </c:if>
            <c:if test="${isPause}">
            <span class="label label-warning"><i class="fa fa-exclamation-triangle"></i> 暂停（${crsPost.autoSwitch?'自动':'手动'}）</span>
            </c:if>
        </div>
    </div>
    <div style="width: 500px;float: left;margin-right: 25px">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    报名自动开关
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" id="qualification-content">
                        <form class="form-horizontal" action="${ctx}/crsPost_detail/step2_time" id="timeForm" method="post">
                            <input type="hidden" name="id" value="${crsPost.id}">

                            <div class="form-group">
                                <label class="col-xs-3 control-label">报名开始时间</label>

                                <div class="col-xs-6">
                                    <div class="input-group">
                                        <input class="form-control datetime-picker" required type="text" name="startTime"
                                               value="${cm:formatDate(crsPost.startTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">报名结束时间</label>

                                <div class="col-xs-6">
                                    <div class="input-group">
                                        <input class="form-control datetime-picker" required type="text" name="endTime"
                                               value="${cm:formatDate(crsPost.endTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">报名开关</label>
                                <div class="col-xs-6 label-text">
                                    根据报名时间自动开启和关闭
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
    <div style="width: 325px; float:left">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    报名手动开关
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <button class="confirm btn btn-success btn-block"
                            ${!crsPost.autoSwitch && isOpen?'disabled':''}
                            data-msg="确定强制开启报名？" data-callback="_stepContentReload"
                            data-url="${ctx}/crsPost_detail/step2_enrollStatus?id=${crsPost.id}
                            &enrollStatus=${CRS_POST_ENROLL_STATUS_OPEN}">
                        <i class="fa fa-check"></i> 开启报名</button>
                    <br/>
                    <button class="confirm btn btn-danger btn-block"
                    ${!crsPost.autoSwitch && isClosed?'disabled':''}
                            data-msg="确定强制关闭报名？" data-callback="_stepContentReload"
                            data-url="${ctx}/crsPost_detail/step2_enrollStatus?id=${crsPost.id}
                            &enrollStatus=${CRS_POST_ENROLL_STATUS_CLOSED}">
                        <i class="fa fa-times"></i> 关闭报名</button>
                    <br/>
                    <button class="confirm btn btn-warning btn-block"
                    ${!crsPost.autoSwitch && isPause?'disabled':''}
                            data-msg="确定强制暂停报名？" data-callback="_stepContentReload"
                            data-url="${ctx}/crsPost_detail/step2_enrollStatus?id=${crsPost.id}
                            &enrollStatus=${CRS_POST_ENROLL_STATUS_PAUSE}">
                        <i class="fa fa-exclamation-triangle"></i> 暂停报名</button>
                    <br/>
                    <button class="confirm btn btn-primary btn-block"
                    ${crsPost.autoSwitch?'disabled':''}
                            data-msg="确定由系统自动判断报名开关？" data-callback="_stepContentReload"
                            data-url="${ctx}/crsPost_detail/step2_enrollStatus?id=${crsPost.id}
                            &enrollStatus=${CRS_POST_ENROLL_STATUS_DEFAULT}">
                        <i class="fa fa-reply"></i> 恢复自动</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    register_datetime($('.datetime-picker'));

    $("#timeForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //$("#modal").modal('hide');
                        _stepContentReload()
                    }
                }
            });
        }
    });
    $("#timeForm :checkbox").bootstrapSwitch();
    $('#timeForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>