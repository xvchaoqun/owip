<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

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
					<img src="${ctx}/avatar/${memberStudent.code}">
				</td>

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
				<td class="bg-left" style="min-width: 80px">
					${memberStudent.nation}
				</td>
				<td class="bg-right">
					身份证号
				</td>
				<td class="bg-left" style="min-width: 120px">
					${memberStudent.idcard}
				</td>
			</tr>
			<tr>
				<td>学生证号</td>
				<td >
					${memberStudent.code}
				</td>
				<td>
					籍贯
				</td>
				<td>
					${memberStudent.nativePlace}
				</td>
				<td >
					来源
				</td>
				<td>
					${MEMBER_SOURCE_MAP.get(memberStudent.source)}
				</td>
				<td>
					同步来源
				</td>
				<td>
					${memberStudent.syncSource}
				</td>
			</tr>
			<tr>
				<td>
					年级
				</td>
				<td>
					${memberStudent.grade}
				</td>
				<td>培养类型</td>
				<td >
					${memberStudent.eduType}
				</td>
				<td>
					培养层次
				</td>
				<td>
					${memberStudent.eduLevel}
				</td>
				<td >
					培养方式
				</td>
				<td>
					${memberStudent.eduWay}
				</td>
			</tr>
			<tr>

				<td>招生年度</td>
				<td >
					${memberStudent.enrolYear}
				</td>
				<td>
					是否全日制
				</td>
				<td>
					${memberStudent.isFullTime}
				</td>
				<td >
					学生类别
				</td>
				<td>
					${memberStudent.type}
				</td>
				<td>教育类别</td>
				<td >
					${memberStudent.eduCategory}
				</td>
			</tr>
			<tr>

				<td>实际入学年月</td>
				<td >
					${memberStudent.actualEnrolTime}
				</td>
				<td>
					预计毕业年月
				</td>
				<td>
					${memberStudent.expectGraduateTime}
				</td>
				<td >
					实际毕业年月
				</td>
				<td>
					${memberStudent.actualGraduateTime}
				</td>
				<td>
					延期毕业年限
				</td>
				<td>
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
		<td>
			所属组织机构
		</td>

		<td colspan="5">
			${partyMap.get(memberStudent.partyId).name}
			<c:if test="${not empty memberStudent.branchId}">
				-${branchMap.get(memberStudent.branchId).name}
			</c:if>
		</td>

	</tr>
	<tr>
		<td>政治面貌</td>
		<td colspan="2">
			${MEMBER_POLITICAL_STATUS_MAP.get(memberStudent.politicalStatus)}
		</td>

		<td>
			党内职务
		</td>
		<td colspan="2">
			${memberStudent.partyPost}
		</td>
	</tr>
	<tr>
		<td>
			入党时间
		</td>
		<td>
			${cm:formatDate(memberStudent.growTime,'yyyy-MM-dd')}
		</td>
		<td>
			转正时间
		</td>
		<td>
			${cm:formatDate(memberStudent.positiveTime,'yyyy-MM-dd')}
		</td>
		<td>
			进入系统方式
		</td>
		<td>
			${MEMBER_SOURCE_MAP.get(memberStudent.source)}
		</td>
	</tr>
	<tr>
		<td>提交书面申请书时间</td>
		<td >
			${cm:formatDate(memberStudent.applyTime,'yyyy-MM-dd')}
		</td>
		<td>
			确定为入党积极分子时间
		</td>

		<td>
			${cm:formatDate(memberStudent.activeTime,'yyyy-MM-dd')}
		</td>
		<td>
			确定为发展对象时间
		</td>
		<td >
			${cm:formatDate(memberStudent.candidateTime,'yyyy-MM-dd')}
		</td>
	</tr>
	<tr>
		<td>
			党内奖励
		</td>
		<td colspan="5">
			${memberStudent.partyReward}
		</td>
	</tr>
	<tr>
		<td>
			其他奖励
		</td>
		<td colspan="5">
			${memberStudent.otherReward}
		</td>
	</tr>

	</tbody>
</table>
			</div></div></div>

<style>
	#item-content .table-striped > tbody > tr:nth-of-type(odd) {
		background-color:inherit;
	}
	#item-content .table tbody tr:hover td, .table tbody tr:hover th {
		background-color:transparent;
	}
	#item-content .table-striped > tbody > tr > td:nth-of-type(odd) {
		background-color: #f9f9f9;
		text-align: right;
	}
</style>