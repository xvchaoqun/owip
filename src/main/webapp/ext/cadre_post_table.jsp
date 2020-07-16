<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['proPostTimeToDay']=='true'?'yyyy.MM.dd':'yyyy.MM'}" var="_p_proPostTimeFormat"/>
<table class="table table-unhover table-bordered">
    <tbody>
    <tr>
        <td rowspan="2" class="bg-right" style="text-align: center;vertical-align: middle">
            专技岗位
        </td>
        <td class="bg-right">
            专业技术职务
        </td>
        <td style="min-width: 80px" class="bg-left">
            ${teacherInfo.proPost}
        </td>

        <td class="bg-right">
            专业技术职务评定时间
        </td>
        <td style="min-width: 80px" class="bg-left">
            ${cm:formatDate(teacherInfo.proPostTime, _p_proPostTimeFormat)}
        </td>
    </tr>
    <tr>
        <td class="bg-right">专业技术职务等级</td>
        <td>
            ${teacherInfo.proPostLevel}
        </td>
        <td class="bg-right">
            专业技术职务分级时间
        </td>
        <td>
            ${cm:formatDate(teacherInfo.proPostLevelTime, _p_proPostTimeFormat)}
        </td>

    </tr>
    <tr>

        <td class="bg-right" style="text-align: center">管理岗位</td>

        <td class="bg-right">
            管理岗位等级
        </td>
        <td class="bg-left">
            ${teacherInfo.manageLevel}
        </td>
        <td class="bg-right">
            管理岗位分级时间
        </td>
        <td class="bg-left">
            ${cm:formatDate(teacherInfo.manageLevelTime, "yyyy.MM")}
        </td>
    </tr>
    <tr>

        <td class="bg-right" style="text-align: center">工勤岗位</td>

        <td class="bg-right">
            工勤岗位等级
        </td>
        <td class="bg-left">
            ${teacherInfo.officeLevel}
        </td>
        <td class="bg-right">
            工勤岗位分级时间
        </td>
        <td class="bg-left">
            ${cm:formatDate(teacherInfo.officeLevelTime, "yyyy.MM")}
        </td>
    </tr>
    </tbody>
</table>