<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box transparent">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:;" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                </h4>
                <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    招聘岗位：${crsPost.name}
                </span>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
                    <div class="tab-content padding-4" id="step-content">
                        <div style="width: 450px; float: left;margin-right: 25px">
                            <div class="widget-box">
                                <div class="widget-header">
                                    <h4 class="smaller">
                                        一、上传应聘PPT
                                    </h4>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <form class="form-horizontal" action="${ctx}/user/crsPost_apply_ppt"
                                              id="modalForm" method="post">
                                            <input type="hidden" name="postId" value="${crsPost.id}">

                                            <div class="form-group">
                                                <div class="col-xs-offset-3 col-xs-6">
                                                    <input required class="form-control" type="file"
                                                           name="ppt"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="col-xs-offset-3 col-xs-6">
                                                    <c:if test="${not empty crsApplicant.ppt}">
                                                        <a href="${ctx}/attach/download?path=${crsApplicant.ppt}&filename=${crsApplicant.pptName}">（下载已上传PPT）</a>
                                                    </c:if>
                                                </div>
                                            </div>

                                            <div class="modal-footer center">
                                                <button class="btn btn-success" ${crsPost.pptUploadClosed?"disabled":""}>
                                                    <i class="fa fa-upload"></i>
                                                    <c:if test="${empty crsApplicant.ppt}">上  传</c:if><c:if
                                                        test="${not empty crsApplicant.ppt}">重新上传</c:if>
                                                </button>

                                            </div>
                                        </form>

                                    </div>
                                </div>
                            </div>
                            <div class="widget-box">
                                <div class="widget-header">
                                    <h4 class="smaller">
                                        二、招聘会信息
                                    </h4>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main">

                                        <table class="table table-bordered table-unhover2">
                                            <tr>
                                                <td class="bg-right" style="width:100px;white-space: nowrap">招聘会时间</td>
                                                <td>${empty crsPost.meetingTime?'待定':(cm:formatDate(crsPost.meetingTime, "yyyy-MM-dd HH:mm"))}</td>
                                            </tr>
                                            <tr>
                                                <td class="bg-right" style="white-space: nowrap">招聘会地点</td>
                                                <td>${empty crsPost.meetingAddress?'待定':crsPost.meetingAddress}</td>
                                            </tr>
                                            <tr>
                                                <td class="bg-right" style="white-space: nowrap">报名材料修改截止时间</td>
                                                <td width="150">${empty crsPost.reportDeadline?'待定':(cm:formatDate(crsPost.reportDeadline, "yyyy-MM-dd HH:mm"))}</td>
                                            </tr>
                                            <tr>
                                                <td class="bg-right" style="white-space: nowrap">退出竞聘截止时间</td>
                                                <td width="150">${empty crsPost.quitDeadline?'待定':(cm:formatDate(crsPost.quitDeadline, "yyyy-MM-dd HH:mm"))}</td>
                                            </tr>
                                            <tr>
                                                <td class="bg-right" style="white-space: nowrap">上传应聘PPT截止时间</td>
                                                <td width="150">${empty crsPost.pptDeadline?'待定':(cm:formatDate(crsPost.pptDeadline, "yyyy-MM-dd HH:mm"))}</td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div style="width: 800px; float: left;margin-right: 25px">
                            <div class="widget-box">
                                <div class="widget-header">
                                    <h4 class="smaller">
                                        三、招聘会公告
                                    </h4>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main">
                                        ${crsPost.meetingNotice}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    .widget-header h4{
        font-size: 16pt;
        font-weight: bolder;
    }
</style>
<script>
    $.fileInput($('#modalForm input[type=file]')/*,{allowExt: ['ppt', 'pptx']}*/);
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        SysMsg.info("上传成功。", function(){
                            $.loadView("${ctx}/user/crsPost_apply_notice?postId=${crsPost.id}");
                        })
                    }
                }
            });
        }
    });
</script>