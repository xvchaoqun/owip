<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="tabbable">
    <div class="tab-content">
        <table class="table table-bordered table-unhover2">
            <tr>
                <td class="bg-right" width="200">招聘会时间</td>
                <td width="150">${empty crsPost.meetingTime?'待定':(cm:formatDate(crsPost.meetingTime, "yyyy-MM-dd HH:mm"))}</td>
                <td class="bg-right" width="100">招聘会地点</td>
                <td colspan="3">${empty crsPost.meetingAddress?'待定':crsPost.meetingAddress}</td>
            </tr>
            <tr>
                <td class="bg-right" width="200">报名材料修改截止时间</td>
                <td width="150">${empty crsPost.reportDeadline?'待定':(cm:formatDate(crsPost.reportDeadline, "yyyy-MM-dd HH:mm"))}</td>
                <td class="bg-right" width="140">退出竞聘截止时间</td>
                <td width="150">${empty crsPost.quitDeadline?'待定':(cm:formatDate(crsPost.quitDeadline, "yyyy-MM-dd HH:mm"))}</td>
                <td class="bg-right" width="160">上传应聘PPT截止时间</td>
                <td width="150">${empty crsPost.pptDeadline?'待定':(cm:formatDate(crsPost.pptDeadline, "yyyy-MM-dd HH:mm"))}</td>
            </tr>
            <tr>
                <td class="bg-right">上传应聘PPT</td>
                <td colspan="5">
                    <c:if test="${not empty crsApplicant.ppt}">
                        <div style="float: left;margin-right: 15px;">
                            <a href="${ctx}/attach/download?path=${crsApplicant.ppt}&filename=${crsApplicant.pptName}">下载已上传PPT</a>
                        </div>
                    </c:if>

                    <form class="form-inline" action="${ctx}/user/crsPost_apply_ppt" id="modalForm" method="post">
                        <input type="hidden" name="postId" value="${crsPost.id}">
                        <div style="width: 200px;float: left" class="input-group" data-my="bottom center" data-at="top center">
                            <input required class="form-control" type="file" name="ppt" />
                        </div>
                        <div style="float: left; margin-left: 15px;">
                            <button class="btn btn-primary btn-sm" ${crsPost.pptUploadClosed?"disabled":""}>
                                <i class="fa fa-upload"></i>
                                <c:if test="${empty crsApplicant.ppt}">上传</c:if><c:if test="${not empty crsApplicant.ppt}">修改</c:if>
                            </button>
                        </div>
                    </form>

                </td>
            </tr>
            <tr class="bg-right" style="text-align: center!important;font-weight: bolder;font-size: larger">
                <td colspan="6">招聘会公告</td>
            </tr>
            <tr>
                <td colspan="6">
                    ${crsPost.meetingNotice}
                </td>
            </tr>

        </table>
    </div>
</div>
<script>
    $.fileInput($('#modalForm input[type=file]')/*,{allowExt: ['ppt', 'pptx']}*/);
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#detail-ul li.active .loadPage").click()
                    }
                }
            });
        }
    });
</script>