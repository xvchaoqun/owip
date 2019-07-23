<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table class="table table-bordered table-striped">
    <tbody>
    <tr>
        <td rowspan="5" style="text-align: center;vertical-align: middle;
				 width: 50px;background-color: #fff;">
            <img src="${ctx}/avatar?path=${cm:encodeURI(uv.avatar)}" class="avatar">
        </td>
        <td class="bg-right">
            姓名
        </td>
        <td class="bg-left" style="min-width: 80px">
            ${uv.realname}
        </td>
        <td class="bg-right">
            性别
        </td>
        <td class="bg-left" style="min-width: 80px">
            ${GENDER_MAP.get(uv.gender)}
        </td>

        <td class="bg-right">
            民族
        </td>
        <td class="bg-left" style="min-width: 80px">
            ${uv.nation}
        </td>
        <td class="bg-right">
            身份证号
        </td>
        <td class="bg-left" style="min-width: 120px">
            ${uv.idcard}
        </td>
    </tr>
    <tr>
        <td class="bg-right">学生证号</td>
        <td class="bg-left">
            ${uv.code}
        </td>
        <td class="bg-right">
            籍贯
        </td>
        <td class="bg-left">
            ${uv.nativePlace}
        </td>
        <td class="bg-right">
            学籍状态
        </td>
        <td class="bg-left">
            ${studentInfo.xjStatus}
        </td>
        <td class="bg-right">
            同步来源
        </td>
        <td class="bg-left">
            <c:set var="USER_SOURCE_MAP" value="<%=SystemConstants.USER_SOURCE_MAP%>"/>
            ${USER_SOURCE_MAP.get(uv.source)}
        </td>
    </tr>
    <tr>
        <td class="bg-right">
            年级
        </td>
        <td class="bg-left">
            ${studentInfo.grade}
        </td>
        <td class="bg-right">培养类型</td>
        <td class="bg-left">
            ${studentInfo.eduType}
        </td>
        <td class="bg-right">
            培养层次
        </td>
        <td class="bg-left">
            ${studentInfo.eduLevel}
        </td>
        <td class="bg-right">
            培养方式
        </td>
        <td class="bg-left">
            ${studentInfo.eduWay}
        </td>
    </tr>
    <tr>

        <td class="bg-right">招生年度</td>
        <td class="bg-left">
            ${studentInfo.enrolYear}
        </td>
        <td class="bg-right">
            学生类别
        </td>
        <td class="bg-left">
            ${studentInfo.type}
        </td>
        <td class="bg-right">教育类别</td>
        <td class="bg-left" colspan="3">
            ${studentInfo.eduCategory}
        </td>
    </tr>
    <tr>

        <td class="bg-right">实际入学年月</td>
        <td class="bg-left">${cm:formatDate(studentInfo.actualEnrolTime,'yyyy-MM')}
        </td>
        <td class="bg-right">
            预计毕业年月
        </td>
        <td class="bg-left">${cm:formatDate(studentInfo.expectGraduateTime,'yyyy-MM')}
        </td>
        <td class="bg-right">
            实际毕业年月
        </td>
        <td class="bg-left" colspan="3">${cm:formatDate(studentInfo.actualGraduateTime,'yyyy-MM')}
        </td>
    </tr>

    </tbody>
</table>