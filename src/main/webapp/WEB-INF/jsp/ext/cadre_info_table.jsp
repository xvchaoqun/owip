<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table class="table table-unhover table-bordered table-striped">
    <tbody>
    <tr>
        <td>
            所在单位
        </td>
        <td style="min-width: 80px">
            ${uv.unit}
        </td>
        <td>编制类别</td>
        <td>
            ${teacherInfo.authorizedType}
        </td>
        <td>
            人员类别
        </td>
        <td>
            ${teacherInfo.staffType}
        </td>
    </tr>
    <tr>
        <td>
            人员状态
        </td>
        <td>
            ${teacherInfo.staffStatus}
        </td>
        <td>
            在岗情况
        </td>
        <td>
            ${teacherInfo.onJob}
        </td>
        <td>
            人事转否
        </td>
        <td>
            ${teacherInfo.personnelStatus}
        </td>
    </tr>
    <tr>

        <td>岗位类别</td>
        <td>
            ${teacherInfo.postClass}
        </td>
        <td>
            岗位子类别
        </td>
        <td>
            ${teacherInfo.subPostClass}
        </td>
        <td>
            主岗等级
        </td>
        <td>
            ${teacherInfo.mainPostLevel}
        </td>
    </tr>
    <tr>
        <td>
            工龄起算日期
        </td>
        <td>
            ${cm:formatDate(teacherInfo.workStartTime, "yyyy.MM.dd")}
        </td>
        <td>间断工龄</td>
        <td>
            ${teacherInfo.workBreak}
        </td>
        <td>
            到校日期
        </td>
        <td>
            ${cm:formatDate(teacherInfo.arriveTime, "yyyy.MM.dd")}
        </td>
    </tr>
    <tr>

        <td>参加工作时间</td>
        <td>
            ${cm:formatDate(teacherInfo.workTime, "yyyy.MM")}
        </td>
        <td>
            转正定级时间
        </td>
        <td colspan="3">
            ${cm:formatDate(teacherInfo.regularTime, "yyyy.MM.dd")}
        </td>

    </tr>
    <tr>
        <td>
            人才/荣誉称号
        </td>
        <td class="bg-left" colspan="5">
            ${teacherInfo.talentTitle}
        </td>

    </tr>
    </tbody>
</table>