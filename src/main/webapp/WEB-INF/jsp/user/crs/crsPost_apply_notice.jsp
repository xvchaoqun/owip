<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="tabbable">
    <div class="tab-content">
    <table class="table table-bordered table-unhover2">
        <tr>
            <td class="bg-right" width="100">招聘会时间</td>
            <td width="150">${empty crsPost.meetingTime?'待定':(cm:formatDate(crsPost.meetingTime, "yyyy-MM-dd HH:mm"))}</td>
            <td class="bg-right" width="100">招聘会地点</td>
            <td>${empty crsPost.meetingAddress?'待定':crsPost.meetingAddress}</td>
        </tr>
        <tr class="bg-right" style="text-align: center!important;font-weight: bolder;font-size: larger">
            <td colspan="4">招聘会公告</td>
        </tr>
        <tr>
            <td colspan="4">
                ${crsPost.meetingNotice}
            </td>
        </tr>
    </table>
</div>
</div>