<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="space-4"></div>
<div class="row" style="width: 1580px">

    <div style="width: 500px;float: left;margin-right: 25px">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    招聘会相关时间节点
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal" action="${ctx}/crsPost_detail/step3_meeting" id="modalForm"
                          method="post">
                        <input type="hidden" name="id" value="${crsPost.id}">

                        <div class="form-group">
                            <label class="col-xs-4 control-label">招聘会时间</label>

                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input class="form-control datetime-picker" type="text" name="meetingTime"
                                           value="${cm:formatDate(crsPost.meetingTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">地点</label>

                            <div class="col-xs-6">
                                <input class="form-control" type="text" name="meetingAddress"
                                       value="${crsPost.meetingAddress}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-4 control-label">报名材料修改截止时间</label>

                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input class="form-control datetime-picker" type="text" name="reportDeadline"
                                           value="${cm:formatDate(crsPost.reportDeadline, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">退出竞聘截止时间</label>

                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input class="form-control datetime-picker" type="text" name="quitDeadline"
                                           value="${cm:formatDate(crsPost.quitDeadline, "yyyy-MM-dd HH:mm")}">
							    <span class="input-group-addon">
                                <i class="fa fa-calendar bigger-110"></i>
                                </span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">上传应聘PPT截止时间</label>
                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input class="form-control datetime-picker" type="text" name="pptDeadline"
                                           value="${cm:formatDate(crsPost.pptDeadline, "yyyy-MM-dd HH:mm")}">
							    <span class="input-group-addon">
                                <i class="fa fa-calendar bigger-110"></i>
                                </span>
                                </div>
                            </div>

                        </div>
                        <div class="modal-footer center">

                            <%--<a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>--%>
                            <input type="submit" class="btn btn-primary" value="更新"/>

                            <div style="text-align: left; padding-top: 10px">
                                注：短信提醒上传应聘PPT的规则如下：<br/>如果设置了招聘会时间、地点和上传应聘PPT截止时间，并且启动了[上传应聘ppt提醒]定时任务时，会发送如下短信：<br/>
                                <div style="color: red">
                                    1.截止时间前1天（24小时），给还没有上传的应聘人发送一次<br/>
                                    2.截止时间前1个小时，给还没有上传的应聘人发送一次<br/>
                                    3.截止时间刚到，给还没有上传的应聘人发送一次，后面的4小时内每过2个小时再催一次(共两次)<br/>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div style="width: 500px; float:left;margin-right: 25px">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    短信通知
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">

                    <div id="accordion" class="accordion-style1 panel-group">
                        <c:forEach items="${tplList}" var="tpl" varStatus="vs">

                            <div class="panel panel-default">
                                <div class="buttons">
                                    <button type="button"
                                            data-width="650"
                                            data-url="${ctx}/crsPost_detail/step3_shortMsg?tplKey=${tpl.code}"
                                            class="popupBtn btn btn-xs btn-primary">
                                        <i class="ace-icon fa fa-edit"></i>
                                            编辑
                                    </button>
                                    <button type="button"
                                            data-url="${ctx}/crsPost_detail/step3_shortMsg_send?postId=${crsPost.id}&tplKey=${tpl.code}"
                                            class="popupBtn btn btn-xs btn-warning">
                                        <i class="ace-icon fa fa-send"></i>
                                        发送
                                    </button>
                                    <button type="button"
                                            data-url="${ctx}/crsPost_detail/step3_shortMsg_list?postId=${crsPost.id}&tplKey=${tpl.code}"
                                            data-width="800"
                                            class="popupBtn btn btn-xs btn-info">
                                        <i class="ace-icon fa fa-history"></i>
                                        记录
                                    </button>
                                </div>
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a class="accordion-toggle ${vs.first?'':'collapsed'}" data-toggle="collapse" data-parent="#accordion"
                                           href="#collapse${tpl.id}">
                                            <i class="ace-icon fa fa-angle-${vs.first?'down':'right'} bigger-110"
                                               data-icon-hide="ace-icon fa fa-angle-down"
                                               data-icon-show="ace-icon fa fa-angle-right"></i>
                                                <c:set value="<%=ContentTplConstants.CONTENT_TPL_CRS_MSG_MAP%>" var="CONTENT_TPL_CRS_MSG_MAP"/>
                                                ${CONTENT_TPL_CRS_MSG_MAP.get(tpl.code)}
                                        </a>
                                    </h4>
                                </div>
                                <div class="panel-collapse collapse ${vs.first?'in':''}" id="collapse${tpl.id}">
                                    <div class="panel-body">
                                            ${tpl.content}
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div style="width: 500px;float: left;margin-right: 25px">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    招聘会公告
                    <c:if test="${not empty crsPost.meetingNotice}">
                        <div class="pull-right" style="margin-right: 10px">
                            <button type="button"
                                    data-load-el="#meetingNotice-content"
                                    data-url="${ctx}/crsPost_templateContent?id=${param.id}&type=${CRS_TEMPLATE_TYPE_MEETINGNOTICE}"
                                    class="loadPage btn btn-xs btn-success">
                                <i class="ace-icon fa fa-edit"></i>
                                编辑
                            </button>
                        </div>
                    </c:if>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" id="meetingNotice-content" style="min-height: 570px">
                    ${crsPost.meetingNotice}
                    <c:if test="${empty crsPost.meetingNotice}">
                        <c:import url="${ctx}/crsPost_templateContent?id=${param.id}&type=${CRS_TEMPLATE_TYPE_MEETINGNOTICE}"/>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    .panel .buttons{
        top: 6px;
        right: 10px;
        position: relative;
        overflow: hidden;
        z-index: 999;
        float: right!important;
    }
</style>
<script>
    $.register.datetime($('.datetime-picker'));

    $("#modalForm").validate({
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>