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
        <td class="bg-left" style="min-width: 120px">
            ${uv.nation}
        </td>
        <td class="bg-right">
            身份证号
        </td>
        <td class="bg-left" style="min-width: 120px">
            <t:mask src="${uv.idcard}" type="idCard"/>
        </td>
    </tr>
    <tr>
        <td class="bg-right">工作证号</td>
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
            最高学历
        </td>
        <td class="bg-left">
            ${teacherInfo.education}
        </td>
        <td class="bg-right">
            最高学位
        </td>
        <td class="bg-left">
            ${teacherInfo.degree}
        </td>
    </tr>

    <tr>

        <td class="bg-right">到校日期</td>
        <td class="bg-left">
            ${cm:formatDate(teacherInfo.arriveTime, "yyyy.MM.dd")}
        </td>
        <td class="bg-right">
            编制类别
        </td>
        <td class="bg-left">
            ${teacherInfo.authorizedType}
        </td>
        <td class="bg-right">
            人员类别
        </td>
        <td class="bg-left">
            ${teacherInfo.staffType}
        </td>

        <td class="bg-right">
            专业技术职务
        </td>
        <td class="bg-left">
            ${teacherInfo.proPost}
        </td>
    </tr>
    <tr>
        <td class="bg-right">人员状态</td>
        <td class="bg-left" colspan="3">
            ${teacherInfo.staffStatus}
        </td>
        <td class="bg-right">是否退休</td>
        <td class="bg-left" colspan="3">
            ${teacherInfo.isRetire?"是":"否"}
        </td>
    </tr>
    <tr>
        <td class="bg-right">
            手机号码
        </td>
        <td class="bg-left" colspan="3">
            <t:mask src="${uv.mobile}" type="mobile"/>
        </td>
        <td class="bg-right">
            邮箱
        </td>
        <td class="bg-left" colspan="3">
            <t:mask src="${uv.email}" type="email"/>
        </td>
    </tr>
    </tbody>
</table>
