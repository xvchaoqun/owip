<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div style="width: 500px;float: left;margin-right: 25px">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="smaller">
                设置
            </h4>
        </div>
        <div class="widget-body">
            <div class="widget-main" id="qualification-content">
                <form class="form-horizontal" action="${ctx}/cet/cetProject_detail_begin" id="beginForm" method="post">
                    <input type="hidden" name="projectId" value="${param.projectId}">
                    <div class="form-group" id="_startTime">
                        <label class="col-xs-3 control-label"><span class="star">*</span>开班仪式时间</label>
                        <div class="col-xs-6">
                            <div class="input-group">
                                <input class="form-control datetime-picker" required type="text"  name="openTime"
                                       value="${cm:formatDate(cetProject.openTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label"><span class="star">*</span>开班仪式地点</label>
                        <div class="col-xs-6">
                            <input required class="form-control" type="text" name="openAddress" value="${cetProject.openAddress}">
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
                                            data-url="${ctx}/cet/cetTrain_detail/msg_au?tplKey=${tpl.code}"
                                            class="popupBtn btn btn-xs btn-primary">
                                        <i class="ace-icon fa fa-edit"></i>
                                        编辑
                                    </button>
                                    <button type="button"
                                            data-url="${ctx}/cet/cetTrain_detail/msg_send?projectId=${param.projectId}&tplKey=${tpl.code}"
                                            class="popupBtn btn btn-xs btn-warning">
                                        <i class="ace-icon fa fa-send"></i>
                                        发送
                                    </button>
                                    <button type="button"
                                            data-url="${ctx}/cet/cetTrain_detail/msg_list?recordId=${param.projectId}&tplKey=${tpl.code}"
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
                                            <c:set value="<%=ContentTplConstants.CONTENT_TPL_CET_MSG_MAP%>" var="CONTENT_TPL_CET_MSG_MAP"/>
                                                ${CONTENT_TPL_CET_MSG_MAP.get(tpl.code)}
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
<div style="clear: both"></div>
<script>
    $.register.datetime($('.datetime-picker'));
    $("#beginForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        SysMsg.success("设置成功。",function(){
                            _detailReload()
                        })
                        //$("#modal").modal('hide');

                    }
                }
            });
        }
    });
</script>