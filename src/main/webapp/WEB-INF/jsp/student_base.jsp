<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
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
					<img src="${ctx}/avatar/${sysUser.username}" class="avatar">
				</td>

				<td class="bg-right">
					学号
				</td>
				<td class="bg-left" style="min-width: 80px">
					${sysUser.code}
				</td>
				<td class="bg-right">
					性别
				</td>
				<td class="bg-left" style="min-width: 80px">
					${GENDER_MAP.get(sysUser.gender)}
				</td>

				<td class="bg-right">
					出生年月
				</td>
				<td class="bg-left" style="min-width: 80px">
					${cm:formatDate(sysUser.birth,'yyyy-MM-dd')}
				</td>
				<td class="bg-right">
					身份证号
				</td>
				<td class="bg-left" style="min-width: 120px">
					${sysUser.idcard}
				</td>
			</tr>
			<tr>
				<td class="bg-right">手机</td>
				<td  class="bg-left">
					${sysUser.mobile}
				</td>
				<td class="bg-right">
					邮箱
				</td>
				<td class="bg-left">
					${sysUser.email}
				</td>
				<td  class="bg-right">
					来源
				</td>
				<td  class="bg-left">
					${USER_SOURCE_MAP.get(sysUser.source)}
				</td>
				<td class="bg-right">组织关系状态</td>
				<td class="bg-left">
					${memberStudent.status==null?"-":MEMBER_STATUS_MAP.get(memberStudent.status)}
				</td>
			</tr>
			<tr>
				<td class="bg-right">
					年级
				</td>
				<td class="bg-left">
					${student.grade}
				</td>
				<td class="bg-right">培养类型</td>
				<td  class="bg-left">
					${student.eduType}
				</td>
				<td class="bg-right">
					培养层次
				</td>
				<td class="bg-left">
					${student.eduLevel}
				</td>
				<td  class="bg-right">
					培养方式
				</td>
				<td class="bg-left">
					${student.eduWay}
				</td>
			</tr>
			<tr>

				<td class="bg-right">招生年度</td>
				<td  class="bg-left">
					${student.enrolYear}
				</td>
				<%--<td class="bg-right">
					是否全日制
				</td>
				<td class="bg-left">
					<c:if test="${empty student.isFullTime}">-</c:if>
					<c:if test="${not empty student.isFullTime}">${student.isFullTime?"是":"否"}</c:if>
				</td>--%>
				<td class="bg-right" >
					学生类别
				</td>
				<td class="bg-left">
					${student.type}
				</td>
				<td class="bg-right">教育类别</td>
				<td  class="bg-left" colspan="3">
					${student.eduCategory}
				</td>
			</tr>
			<tr>

				<td class="bg-right">实际入学年月</td>
				<td  class="bg-left">
					${cm:formatDate(student.actualEnrolTime,'yyyy-MM-dd')}
				</td>
				<td class="bg-right">
					预计毕业年月
				</td>
				<td class="bg-left">
					${cm:formatDate(student.expectGraduateTime,'yyyy-MM-dd')}
				</td>
				<td  class="bg-right">
					实际毕业年月
				</td>
				<td class="bg-left">
					${cm:formatDate(student.actualGraduateTime,'yyyy-MM-dd')}
				</td>
				<td class="bg-right">
					延期毕业年限
				</td>
				<td class="bg-left">
					${student.delayYear}
				</td>
			</tr>
			</tbody>
		</table>
			</div></div></div>