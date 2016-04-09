<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="USER_SOURCE_MAP" value="<%=SystemConstants.USER_SOURCE_MAP%>"/>
<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title"><i class="fa fa-paw blue"></i> 基本信息</h4>

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
				<td rowspan="5" style="text-align: center;vertical-align: middle;
				 width: 50px;background-color: #fff;">
					<img src="${ctx}/avatar/${sysUser.username}">
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
				<td  class="bg-left" colspan="3">
					${USER_SOURCE_MAP.get(sysUser.source)}
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
				<td class="bg-right">
					是否全日制
				</td>
				<td class="bg-left">
					${student.isFullTime?"是":"否"}
				</td>
				<td class="bg-right" >
					学生类别
				</td>
				<td class="bg-left">
					${student.type}
				</td>
				<td class="bg-right">教育类别</td>
				<td  class="bg-left">
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