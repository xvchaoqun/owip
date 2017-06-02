<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row">
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
				<td class="bg-right">
					姓名
				</td>
				<td class="bg-left" style="min-width: 80px">
					${memberStudent.realname}
				</td>
				<td class="bg-right">
					性别
				</td>
				<td class="bg-left" style="min-width: 80px">
					${GENDER_MALE_MAP.get(memberStudent.gender)}
				</td>

				<td class="bg-right">
					民族
				</td>
				<td class="bg-left"  style="min-width: 80px">
					${memberStudent.nation}
				</td>
				<td  class="bg-right">
					身份证号
				</td>
				<td class="bg-left" style="min-width: 120px">
					${memberStudent.idcard}
				</td>
			</tr>
			<tr>
				<td class="bg-right">学生证号</td>
				<td  class="bg-left">
					${memberStudent.code}
				</td>
				<td class="bg-right">
					籍贯
				</td>
				<td class="bg-left">
					${memberStudent.nativePlace}
				</td>
				<td  class="bg-right">
					学籍状态
				</td>
				<td class="bg-left">
					${memberStudent.xjStatus}
				</td>
				<td class="bg-right">
					同步来源
				</td>
				<td class="bg-left" colspan="3">
					${USER_SOURCE_MAP.get(_user.source)}
				</td>
			</tr>
			<tr>
				<td class="bg-right">
					年级
				</td>
				<td class="bg-left">
					${memberStudent.grade}
				</td>
				<td class="bg-right">培养类型</td>
				<td  class="bg-left">
					${memberStudent.eduType}
				</td>
				<td class="bg-right">
					培养层次
				</td>
				<td class="bg-left">
					${memberStudent.eduLevel}
				</td>
				<td  class="bg-right">
					培养方式
				</td>
				<td class="bg-left">
					${memberStudent.eduWay}
				</td>
			</tr>
			<tr>

				<td class="bg-right">招生年度</td>
				<td  class="bg-left">
					${memberStudent.enrolYear}
				</td>
				<td class="bg-right">
					是否全日制
				</td>
				<td class="bg-left">
					<c:if test="${empty memberStudent.isFullTime}">-</c:if>
					<c:if test="${not empty memberStudent.isFullTime}">${memberStudent.isFullTime?"是":"否"}</c:if>
				</td>
				<td  class="bg-right">
					学生类别
				</td>
				<td class="bg-left">
					${memberStudent.type}
				</td>
				<td class="bg-right">教育类别</td>
				<td  class="bg-left">
					${memberStudent.eduCategory}
				</td>
			</tr>
			<tr>

				<td class="bg-right">实际入学年月</td>
				<td  class="bg-left">
					${cm:formatDate(memberStudent.actualEnrolTime,'yyyy-MM-dd')}
				</td>
				<td class="bg-right">
					预计毕业年月
				</td>
				<td class="bg-left">
					${cm:formatDate(memberStudent.expectGraduateTime,'yyyy-MM-dd')}
				</td>
				<td  class="bg-right">
					实际毕业年月
				</td>
				<td class="bg-left">
					${cm:formatDate(memberStudent.actualGraduateTime,'yyyy-MM-dd')}
				</td>
				<td class="bg-right">
					延期毕业年限
				</td>
				<td class="bg-left">
					${memberStudent.delayYear}
				</td>
			</tr>
			</tbody>
		</table>
			</div></div></div>
<div class="widget-box">
	<div class="widget-header">
		<h4 class="widget-title"><i class="fa fa-star blue"></i> 党籍信息</h4>

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
		<td class="bg-right">
			所属组织机构
		</td>

		<td  class="bg-left" colspan="5">
				${cm:displayParty(memberStudent.partyId, memberStudent.branchId)}
		</td>

	</tr>
	<tr class="bg-right">
		<td class="bg-right">党籍状态</td>
		<td  class="bg-left" colspan="2">
			${MEMBER_POLITICAL_STATUS_MAP.get(memberStudent.politicalStatus)}
		</td>

		<td class="bg-right">
			党内职务
		</td>
		<td class="bg-left" colspan="2">
			${memberStudent.partyPost}
		</td>
	</tr>
	<tr>
		<td class="bg-right">
			入党时间
		</td>
		<td class="bg-left">
			${cm:formatDate(memberStudent.growTime,'yyyy-MM-dd')}
		</td>
		<td class="bg-right">
			转正时间
		</td>
		<td class="bg-left">
			${cm:formatDate(memberStudent.positiveTime,'yyyy-MM-dd')}
		</td>
		<td class="bg-right">
			进入系统方式
		</td>
		<td class="bg-left">
			${MEMBER_SOURCE_MAP.get(memberStudent.memberSource)}
		</td>
	</tr>
	<tr>
		<td class="bg-right">提交书面申请书时间</td>
		<td class="bg-left" >
			${cm:formatDate(memberStudent.applyTime,'yyyy-MM-dd')}
		</td>
		<td class="bg-right">
			确定为入党积极分子时间
		</td>

		<td class="bg-left">
			${cm:formatDate(memberStudent.activeTime,'yyyy-MM-dd')}
		</td>
		<td class="bg-right">
			确定为发展对象时间
		</td>
		<td class="bg-left" >
			${cm:formatDate(memberStudent.candidateTime,'yyyy-MM-dd')}
		</td>
	</tr>
	<tr>
		<td class="bg-right">
			党内奖励
		</td>
		<td class="bg-left" colspan="5">
			${memberStudent.partyReward}
		</td>
	</tr>
	<tr>
		<td class="bg-right">
			其他奖励
		</td>
		<td class="bg-left" colspan="5">
			${memberStudent.otherReward}
		</td>
	</tr>

	</tbody>
</table>
			</div></div></div>
	</div>