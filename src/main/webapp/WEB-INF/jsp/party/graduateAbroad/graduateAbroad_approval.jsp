<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-body">
    <!-- PAGE CONTENT BEGINS -->
    <div class="widget-box transparent">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="closeView reload btn btn-mini btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回</a>
            </h4>
            <div class="widget-toolbar no-border">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="javascript:;">申请详情</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main padding-4">
                <div class="tab-content padding-8">

                    <div class="col-xs-offset-1" style="padding-top: 50px; ">

                        <div class="page-header">
                            <h1>
                                <i class="fa fa-check-square-o"></i>
                                毕业生党员出国（境）申请组织关系暂留申请信息
                                <c:if test="${count>0}">
                                （总共${count}条记录未处理）
                                </c:if>
                            </h1>
                        </div>
                        <div class="col-xs-12">
                        <table class="center-block table table-bordered table-striped" style="width: 700px">
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
                                    <img src="${ctx}/avatar/${userBean.username}" alt="免冠照片">
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
                                <td class="bg-right" colspan="2">
                                    身份证号
                                </td>
                                <td class="bg-left" style="min-width: 80px" colspan="4">
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
                                <td class="bg-right">
                                    状态
                                </td>
                                <td class="bg-left" style="min-width: 80px" colspan="6">
                                    ${GRADUATE_ABROAD_STATUS_MAP.get(graduateAbroad.status)}
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        </div>
                        <div style="padding-top: 50px">
                            <ul class="steps">
                                <li data-step="1" class="complete">
                                    <span class="step">0</span>
                                    <span class="title">申请已提交</span>
                                  <span class="subtitle">
                                      ${cm:formatDate(graduateAbroad.createTime,'yyyy-MM-dd')}
                                  </span>
                                </li>
                                <c:if test="${graduateAbroad.status==GRADUATE_ABROAD_STATUS_SELF_BACK || graduateAbroad.status==GRADUATE_ABROAD_STATUS_BACK}">
                                    <li data-step="2" class="active">
                                        <span class="step">1</span>
                                        <span class="title">未通过申请</span>
                                    </li>
                                </c:if>
                                <li data-step="1"  class="${graduateAbroad.status==GRADUATE_ABROAD_STATUS_BRANCH_VERIFY?'complete':''}">
                                    <span class="step">1</span>
                                    <span class="title">支部审核</span>
                                    <%--<span class="subtitle">
                                            通过时间
                                    </span>--%>
                                </li>
                                <li data-step="2"  class="${graduateAbroad.status==GRADUATE_ABROAD_STATUS_PARTY_VERIFY?'complete':''}">
                                    <span class="step">2</span>
                                    <span class="title">分党委审核</span>
                                    <%--<span class="subtitle">
                                            通过时间
                                    </span>--%>
                                </li>
                                <li data-step="3" class="${graduateAbroad.status==GRADUATE_ABROAD_STATUS_OW_VERIFY?'complete':''}">
                                    <span class="step">3</span>
                                    <span class="title">组织部审核</span>
                                    <%--<span class="subtitle">
                                            通过时间
                                    </span>--%>
                                </li>
                            </ul>
                        </div>
                        <div class="clearfix form-actions center">
                            <div class="pull-left">
                                <c:if test="${empty last}">
                                    <button id="last" class="btn disabled" type="button">
                                        <i class="ace-icon fa fa-angle-double-left fa-lg"></i>上一条
                                    </button>
                                </c:if>
                                <c:if test="${not empty last}">
                                    <button id="last" class="openView btn"
                                            data-url="${ctx}/graduateAbroad_approval?id=${last.id}&type=${param.type}"
                                            type="button">
                                        <i class="ace-icon fa fa-angle-double-left fa-lg"></i>上一条
                                    </button>
                                </c:if>
                            </div>
                            <div class="pull-right">
                                <c:if test="${empty next}">
                                    <button id="next" class="btn disabled" type="button">
                                        下一条 <i class="ace-icon fa fa-angle-double-right fa-lg "></i>
                                    </button>
                                </c:if>
                                <c:if test="${not empty next}">
                                <button id="next" class="openView btn"
                                        data-url="${ctx}/graduateAbroad_approval?id=${next.id}&type=${param.type}"
                                        type="button">
                                    下一条 <i class="ace-icon fa fa-angle-double-right fa-lg "></i>
                                </button>
                                    </c:if>
                            </div>
                            <button ${isAdmin?'':'disabled'}  onclick="apply_pass(${graduateAbroad.id}, ${param.type}, true)" class="btn btn-success">
                                <i class="fa fa-check"></i> 通过
                            </button>
                            &nbsp;&nbsp;
                            <button ${isAdmin?'':'disabled'}  onclick="apply_deny(${graduateAbroad.id}, ${param.type}, true)" class="btn btn-danger">
                                <i class="fa fa-times"></i> 返回修改
                            </button>
                        </div>
                    </div>


                </div>
            </div><!-- /.widget-main -->
        </div><!-- /.widget-body -->
    </div><!-- /.widget-box -->
    <c:import url="/applyApprovalLogs?id=${graduateAbroad.id}&type=${APPLY_APPROVAL_LOG_TYPE_GRADUATE_ABROAD}"/>
</div>
