<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>

<div class="row" style="width: 1050px">

    <div style="width: 500px;float: left;margin-right: 25px">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    招聘会时间和地点
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
                                    <input class="form-control datetime-picker" required type="text" name="meetingTime"
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
                                <input required class="form-control" type="text" name="meetingAddress"
                                       value="${crsPost.meetingAddress}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-4 control-label">退出报名的最后期限</label>

                            <div class="col-xs-6">
                                <div class="input-group">
                                    <input class="form-control datetime-picker" required type="text" name="quitDeadline"
                                           value="${cm:formatDate(crsPost.quitDeadline, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer center">
                            <%--<a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>--%>
                            <input type="submit" class="btn btn-primary" value="更新"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div style="width: 500px; float:left">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    短信通知
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">

                    <div <%--id="accordion"--%> class="accordion-style1 panel-group">
                        <c:forEach items="${tplList}" var="tpl" varStatus="vs">

                            <div class="panel panel-default">
                                <div class="buttons">
                                    <button type="button"
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
                                            class="popupBtn btn btn-xs btn-info">
                                        <i class="ace-icon fa fa-history"></i>
                                        记录
                                    </button>
                                </div>
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a class="accordion-toggle ${vs.first?'':'collapsed'}" data-toggle="collapse" <%--data-parent="#accordion"--%>
                                           href="#collapse${tpl.id}">
                                            <i class="ace-icon fa fa-angle-${vs.first?'down':'right'} bigger-110"
                                               data-icon-hide="ace-icon fa fa-angle-down"
                                               data-icon-show="ace-icon fa fa-angle-right"></i>
                                                ${CRS_SHORT_MSG_TPL_MAP.get(tpl.code)}
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
    register_datetime($('.datetime-picker'));

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