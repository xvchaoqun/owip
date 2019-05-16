<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 基本信息</h4>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
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
                    <td class="bg-left" colspan="3">
                        ${studentInfo.type}
                    </td>
                    <td class="bg-right">教育类别</td>
                    <td class="bg-left">
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
                    <td class="bg-left">${cm:formatDate(studentInfo.actualGraduateTime,'yyyy-MM')}
                    </td>
                    <td class="bg-right">
                        延期毕业年限
                    </td>
                    <td class="bg-left">
                        ${studentInfo.delayYear}
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
</div>