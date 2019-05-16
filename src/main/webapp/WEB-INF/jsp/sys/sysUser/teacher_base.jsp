<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-paw blue "></i>
            基本信息
        </h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main no-padding">

            <table class="table table-unhover table-bordered table-striped">
                <tbody>
                <tr>
                    <td rowspan="6" style="text-align: center;vertical-align: middle;
				 width: 50px;background-color: #fff;">
                        <img src="${ctx}/avatar?path=${cm:encodeURI(uv.avatar)}&t=<%=new Date().getTime()%>"
                             class="avatar">
                    </td>

                    <td class="bg-right">
                        姓名
                    </td>
                    <td class="bg-left" style="min-width: 150px;">
                        ${uv.realname}
                    </td>

                    <td class="bg-right">
                        工作证号
                    </td>
                    <td class="bg-left" style="min-width: 150px;">
                        ${uv.code}
                    </td>
                    <td class="bg-right">性别</td>
                    <td class="bg-left" style="min-width: 150px;">
                        ${GENDER_MAP.get(uv.gender)}
                    </td>

                </tr>
                <tr>
                    <td>
                        民族
                    </td>
                    <td>
                        ${uv.nation}
                    </td>
                    <td>出生日期</td>
                    <td>
                        ${cm:formatDate(uv.birth,'yyyy-MM-dd')}
                    </td>
                    <td>
                        年龄
                    </td>
                    <td>
                        ${uv.birth==null?'':cm:intervalYearsUntilNow(uv.birth)}
                    </td>
                </tr>
                <tr>
                    <td>政治面貌</td>
                    <td>
                        <c:if test="${not empty cadre}">
                        ${cm:cadreParty(cadre.isOw, cadre.owGrowTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('partyName')}
                        </c:if>
                        <c:if test="${empty cadre && not empty member}">
                            中共党员
                        </c:if>
                    </td>
                    <td>
                        党派加入时间
                    </td>
                    <td>
                        <c:if test="${not empty cadre}">
                        ${cm:cadreParty(cadre.isOw, cadre.owGrowTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('growTime')}
                        </c:if>
                        <c:if test="${empty cadre && not empty member}">
                            ${cm:formatDate(member.growTime,'yyyy-MM-dd')}
                        </c:if>
                    </td>

                    <td>国家/地区</td>
                    <td>
                        ${uv.country}
                    </td>
                </tr>
                <tr>
                    <c:if test="${_p_hasPartyModule}">
                        <td>
                            所在党组织
                        </td>
                        <td>
                                ${cm:displayParty(member.partyId, member.branchId)}
                        </td>
                    </c:if>
                    <td>证件类型</td>
                    <td>
                        ${uv.idcardType}
                    </td>
                    <td>
                        证件号码
                    </td>
                    <td colspan="${_p_hasPartyModule?'':3}">
                        ${uv.idcard}
                    </td>
                </tr>
                <tr>
                    <td>
                        籍贯
                    </td>
                    <td style="min-width: 100px">
                        ${uv.nativePlace}
                    </td>
                    <td>出生地</td>
                    <td>
                        ${uv.homeplace}
                    </td>
                    <td>
                        户籍地
                    </td>
                    <td>
                        ${uv.household}
                    </td>
                </tr>
                <tr>
                    <td>
                        健康状况
                    </td>
                    <td>
                        ${cm:getMetaType(uv.health).name}
                    </td>
                    <td>熟悉专业有何专长</td>
                    <td colspan="3">
                        ${uv.specialty}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-phone-square blue"></i>
            联系方式
        </h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main no-padding">
            <table class="table table-unhover table-bordered table-striped">
                <tbody>
                <tr>
                    <td>
                        手机号
                    </td>
                    <td style="min-width: 80px">
                        ${uv.mobile}
                    </td>
                    <td>
                        办公电话
                    </td>
                    <td style="min-width: 80px">
                        ${uv.phone}
                    </td>

                    <td>
                        电子邮箱
                    </td>
                    <td style="min-width: 80px">
                        ${uv.email}
                    </td>
                </tr>

                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="widget-box transparent">
    <div class="widget-header widget-header-flat">
        <h4 class="widget-title lighter">
            <i class="ace-icon fa fa-info-circle blue"></i>
            人事信息
        </h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main no-padding">
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
                        人员分类
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
                        ${cm:formatDate(teacherInfo.workStartTime, "yyyy-MM-dd")}
                    </td>
                    <td>间断工龄</td>
                    <td>
                        ${teacherInfo.workBreak}
                    </td>
                    <td>
                        到校时间
                    </td>
                    <td>
                        ${cm:formatDate(teacherInfo.arriveTime, "yyyy-MM-dd")}
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
                        ${cm:formatDate(teacherInfo.regularTime, "yyyy-MM-dd")}
                    </td>

                </tr>
                <tr>
                    <td class="bg-right">
                        人才/荣誉称号
                    </td>
                    <td class="bg-left" colspan="5">
                        ${teacherInfo.talentTitle}
                    </td>

                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>