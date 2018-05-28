<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<c:set var="isOpen" value="${crsPost.switchStatus==CRS_POST_ENROLL_STATUS_OPEN}"/>
<c:set var="isClosed" value="${crsPost.switchStatus==CRS_POST_ENROLL_STATUS_CLOSED}"/>
<c:set var="isPause" value="${crsPost.switchStatus==CRS_POST_ENROLL_STATUS_PAUSE}"/>
<div class="row">
    <div style="width: 575px;float: left;margin-right: 10px;">
    <div class="alert alert-warning" style="padding-right: 0">
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
    <div style="width: 400px;float: left;margin-right: 25px">
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
                                <label class="col-xs-4 control-label">报名开始时间</label>

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
                                <label class="col-xs-4 control-label">报名结束时间</label>

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
                                <label class="col-xs-4 control-label">报名开关</label>
                                <div class="col-xs-8 label-text">
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
    <div style="width: 150px; float:left">
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
                            data-msg="确定强制开启报名？" data-callback="_stepReload"
                            data-url="${ctx}/crsPost_detail/step2_enrollStatus?id=${crsPost.id}
                            &enrollStatus=${CRS_POST_ENROLL_STATUS_OPEN}">
                        <i class="fa fa-check"></i> 开启报名</button>
                    <br/>
                    <button class="confirm btn btn-danger btn-block"
                    ${!crsPost.autoSwitch && isClosed?'disabled':''}
                            data-msg="确定强制关闭报名？" data-callback="_stepReload"
                            data-url="${ctx}/crsPost_detail/step2_enrollStatus?id=${crsPost.id}
                            &enrollStatus=${CRS_POST_ENROLL_STATUS_CLOSED}">
                        <i class="fa fa-times"></i> 关闭报名</button>
                    <br/>
                    <button class="confirm btn btn-warning btn-block"
                    ${!crsPost.autoSwitch && isPause?'disabled':''}
                            data-msg="确定强制暂停报名？" data-callback="_stepReload"
                            data-url="${ctx}/crsPost_detail/step2_enrollStatus?id=${crsPost.id}
                            &enrollStatus=${CRS_POST_ENROLL_STATUS_PAUSE}">
                        <i class="fa fa-exclamation-triangle"></i> 暂停报名</button>
                    <br/>
                    <button class="confirm btn btn-primary btn-block"
                    ${crsPost.autoSwitch?'disabled':''}
                            data-msg="确定由系统自动判断报名开关？" data-callback="_stepReload"
                            data-url="${ctx}/crsPost_detail/step2_enrollStatus?id=${crsPost.id}
                            &enrollStatus=${CRS_POST_ENROLL_STATUS_DEFAULT}">
                        <i class="fa fa-reply"></i> 恢复自动</button>
                </div>
            </div>
        </div>
    </div>
    </div>
    <div style="width: 725px;float: left">
    <div class="panel panel-default" style="margin-bottom: 10px">
        <div class="panel-heading">
            <h3 class="panel-title"><span class="text-success bolder">
                <i class="fa fa-list"></i>   补报名</span>
                <span style="padding-left: 250px;">
                <button data-url="${ctx}/crsApplyUser_au?postId=${param.id}"
                    class="popupBtn btn btn-info btn-sm">
                <i class="fa fa-plus-circle"></i> 添加</button>
                <button data-url="${ctx}/crsApplyUser_au"
                     data-grid-id="#jqGrid2"
                    class="jqOpenViewBtn btn btn-primary btn-sm">
                <i class="fa fa-edit"></i> 修改</button>
                </span>
                <button data-url="${ctx}/crsApplyUser_status?status=${CRS_APPLY_USER_STATUS_OPEN}"
                        data-title="启动报名"
                        data-msg="确定启动这{0}个人的报名？（针对未结束且未完成补报的记录）"
                        data-grid-id="#jqGrid2"
                        class="jqBatchBtn btn btn-success btn-sm">
                    <i class="fa fa-play-circle-o"></i> 启动报名</button>
                <button data-url="${ctx}/crsApplyUser_status?status=${CRS_APPLY_USER_STATUS_CLOSED}"
                        data-title="关闭报名"
                        data-msg="确定关闭这{0}个人的报名？（针对未结束且未完成补报的记录）"
                     data-grid-id="#jqGrid2"
                    class="jqBatchBtn btn btn-primary btn-sm">
                <i class="fa fa-stop-circle-o"></i> 关闭报名</button>
                <button data-url="${ctx}/crsApplyUser_batchDel"
                        data-title="删除"
                        data-msg="确定删除这{0}条数据？（针对未完成补报的记录）"
                        data-grid-id="#jqGrid2"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-trash"></i> 删除
                </button>
                </span>

                </span>
            </h3>
        </div>
        <div class="collapse in">
            <div class="panel-body rownumbers">
                <table id="jqGrid2" data-width-reduce="30"
                       class="table-striped"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        width:695,
        height:328,
        url: '${ctx}/crsApplyUser_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'user.code', width:110, frozen:true},
            {label: '姓名', name: 'user.realname', frozen:true},
            {label: '补报开始时间', name: 'startTime', width:140, formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}},
            {label: '补报结束时间', name: 'endTime', width:140, formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}},
            {label: '状态', name: 'status', formatter: function (cellvalue, options, rowObject){
                var nowTime = $.date(new Date(), 'yyyy-MM-dd hh:mm');
                var endTime = $.date(rowObject.endTime, 'yyyy-MM-dd hh:mm');
                if (endTime < nowTime) {
                    return '关闭补报窗口'
                }
                return _cMap.CRS_APPLY_USER_STATUS_AMP[cellvalue];
            }},
            {label: '备注', name: 'remark', width:180},
            {label: '添加时间', name: 'createTime', width:180}
        ]
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid2", "jqGridPager2");

    $.register.datetime($('.datetime-picker'));
    $("#timeForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //$("#modal").modal('hide');
                        _stepReload()
                    }
                }
            });
        }
    });
    $("#timeForm :checkbox").bootstrapSwitch();
    $('#timeForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>