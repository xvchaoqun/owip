<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="widget-box" style="width: 750px">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 党员出国（境）组织关系暂留申请</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table class="table table-bordered table-striped">
                <tbody>
                <tr>
                    <td class="bg-right"  style="width: 100px">
                        姓名
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${userBean.realname}
                    </td>
                    <td class="bg-right"  style="width: 80px">
                        性别
                    </td>
                    <td class="bg-left" style="width: 50px">
                        ${GENDER_MAP.get(userBean.gender)}
                    </td>
                    <td class="bg-right" style="width: 100px">
                        名族
                    </td>
                    <td class="bg-left"  style="width: 50px">
                        ${userBean.nation}
                    </td>
                    <td  rowspan="3" style="text-align: center;vertical-align: middle;
				 background-color: #fff;">
                        <img src="${ctx}/avatar/${userBean.username}" alt="免冠照片"  class="avatar">
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        出生年月
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${cm:formatDate(userBean.birth,'yyyy-MM-dd')}
                    </td>
                    <td class="bg-right">
                        入党时间
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${cm:formatDate(userBean.growTime,'yyyy-MM-dd')}
                    </td>

                    <td class="bg-right">
                        籍贯
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${student.nativePlace}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        人员类别
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${abroadUserTypeMap.get(graduateAbroad.userType).name}
                    </td>
                    <td class="bg-right">
                        身份证号
                    </td>
                    <td class="bg-left" style="min-width: 80px" colspan="3">
                        ${userBean.idcard}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        毕业班级
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${student.grade}
                    </td>
                    <td class="bg-right">
                        学号
                    </td>
                    <td class="bg-left" style="min-width: 80px">
                        ${userBean.code}
                    </td>
                    <td class="bg-right">
                        党籍状况
                    </td>
                    <td class="bg-left" style="min-width: 120px" colspan="3">
                        ${MEMBER_POLITICAL_STATUS_MAP.get(userBean.politicalStatus)}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        手机
                    </td>
                    <td class="bg-left">
                        ${graduateAbroad.mobile}
                    </td>

                    <td class="bg-right">
                        家庭电话
                    </td>
                    <td class="bg-left">
                        ${graduateAbroad.phone}
                    </td>
                    <td class="bg-right">
                        微信号
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.weixin}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        电子邮箱
                    </td>
                    <td class="bg-left" colspan="3">
                        ${graduateAbroad.email}
                    </td>

                    <td class="bg-right">
                        QQ号
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.qq}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right" rowspan="2">
                        通讯地址
                    </td>
                    <td class="bg-right"  style="width: 100px">
                        国（境）内
                    </td>
                    <td class="bg-left" colspan="5">
                        ${graduateAbroad.inAddress}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        国（境）外
                    </td>
                    <td class="bg-left" colspan="5">
                        ${graduateAbroad.outAddress}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right" rowspan="3">
                        国（境）内第一联系人
                    </td>
                    <td class="bg-right">
                        姓名（与本人关系）
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.name1} - ${graduateAbroad.relate1}
                    </td>
                    <td class="bg-right">
                        单位
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.unit1}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        职务
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.post1}
                    </td>
                    <td class="bg-right">
                        办公电话
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.phone1}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        手机号
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.mobile1}
                    </td>
                    <td class="bg-right">
                        电子邮箱
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.email1}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right" rowspan="3">
                        国（境）内第二联系人
                    </td>
                    <td class="bg-right">
                        姓名（与本人关系）
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.name2} - ${graduateAbroad.relate2}
                    </td>
                    <td class="bg-right">
                        单位
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.unit2}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        职务
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.post2}
                    </td>
                    <td class="bg-right">
                        办公电话
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.phone2}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right">
                        手机号
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.mobile2}
                    </td>
                    <td class="bg-right">
                        电子邮箱
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.email2}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right" colspan="2">
                        出国原因
                    </td>
                    <td class="bg-left" colspan="5">
                        ${fn:replace(graduateAbroad.abroadReason, "+++", ",")}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right" colspan="2">
                        留学国家
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.country}
                    </td>
                    <td class="bg-right">
                        留学学校（院系）
                    </td>
                    <td class="bg-left" colspan="2">
                        ${graduateAbroad.school}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right" colspan="2">
                        留学起止时间
                    </td>
                    <td class="bg-left" colspan="2">
                        ${cm:formatDate(graduateAbroad.startTime,'yyyy/MM')} 至 ${cm:formatDate(graduateAbroad.endTime,'yyyy/MM')}
                    </td>
                    <td class="bg-right">
                        留学方式
                    </td>
                    <td class="bg-left" colspan="2">
                        ${GRADUATE_ABROAD_TYPE_MAP.get(graduateAbroad.type)}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right" colspan="2">
                        原组织关系所在党支部名称
                    </td>
                    <td class="bg-left" colspan="2">
                        ${party.name}<c:if test="${not empty branch}">-${branch.name}</c:if>
                    </td>
                    <td class="bg-right">
                        原组织关系所在党支部负责人姓名、电话
                    </td>
                    <td class="bg-left" colspan="2">
                        ${not empty branch?branch.phone:party.phone}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right" colspan="2">
                        申请保留组织关系起止时间
                    </td>
                    <td class="bg-left" colspan="2">
                        ${cm:formatDate(graduateAbroad.saveStartTime,'yyyy/MM')} 至 ${cm:formatDate(graduateAbroad.saveEndTime,'yyyy/MM')}
                    </td>
                    <td class="bg-right">
                        党费交纳截止时间
                    </td>
                    <td class="bg-left" colspan="2">
                        ${cm:formatDate(graduateAbroad.payTime,'yyyy-MM')}
                    </td>
                </tr>
                <tr>
                    <td class="bg-right" colspan="2">
                        暂留所在党支部名称
                    </td>
                    <td class="bg-left" colspan="2">
                        ${toBranch.name}
                    </td>
                    <td class="bg-right">
                        状态
                    </td>
                    <td class="bg-left" style="min-width: 80px" colspan="2">
                        ${GRADUATE_ABROAD_STATUS_MAP.get(graduateAbroad.status)}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>