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
			<td>
				姓名
			</td>
			<td style="min-width: 80px">
				${memberTeacher.realname}
			</td>
			<td>
				性别
			</td>
			<td style="min-width: 80px">
				${GENDER_MALE_MAP.get(memberTeacher.gender)}
			</td>

			<td>
				民族
			</td>
			<td  style="min-width: 120px">
				${memberTeacher.nation}
			</td>
			<td >
				身份证号
			</td>
			<td style="min-width: 120px">
				${memberTeacher.idcard}
			</td>
		</tr>
		<tr>
			<td>工作证号</td>
			<td >
				${memberTeacher.code}
			</td>
			<td>
				籍贯
			</td>
			<td>
				${memberTeacher.nativePlace}
			</td>
			<td >
				最高学历
			</td>
			<td>
				${memberTeacher.education}
			</td>
			<td>
				最高学位
			</td>
			<td>
				${memberTeacher.degree}
			</td>
		</tr>
		<tr>
			<td>
				学位授予日期
			</td>
			<td>
				${memberTeacher.degreeTime}
			</td>
			<td>所学专业</td>
			<td >
				${memberTeacher.major}
			</td>
			<td>
				毕业学校
			</td>
			<td>
				${memberTeacher.school}
			</td>
			<td >
				毕业学校类型
			</td>
			<td>
				${memberTeacher.schoolType}
			</td>
		</tr>
		<tr>

			<td>到校日期</td>
			<td >
				${memberTeacher.arriveTime}
			</td>
			<td>
				编制类别
			</td>
			<td>
				${memberTeacher.authorizedType}
			</td>
			<td >
				人员分类
			</td>
			<td>
				${memberTeacher.staffType}
			</td>
			<td>人员状态</td>
			<td >
				${memberTeacher.staffStatus}
			</td>
		</tr>
		<tr>

			<td>岗位类别</td>
			<td >
				${memberTeacher.postClass}
			</td>
			<td>
				岗位子类别
			</td>
			<td>
				${memberTeacher.postType}
			</td>
			<td >
				在岗情况
			</td>
			<td>
				${memberTeacher.onJob}
			</td>
			<td>
				专业技术职务
			</td>
			<td>
				${memberTeacher.proPost}
			</td>
		</tr>
		<tr>

			<td>专技岗位等级</td>
			<td >
				${memberTeacher.proPostLevel}
			</td>
			<td>
				职称级别
			</td>
			<td>
				${memberTeacher.titleLevel}
			</td>
			<td >
				管理岗位等级
			</td>
			<td>
				${memberTeacher.manageLevel}
			</td>
			<td>
				工勤岗位等级
			</td>
			<td>
				${memberTeacher.officeLevel}
			</td>
		</tr>
		<tr>

			<td>行政职务</td>
			<td >
				${memberTeacher.post}
			</td>
			<td>
				任职级别
			</td>
			<td>
				${memberTeacher.postLevel}
			</td>
			<td >
				人才/荣誉称号
			</td>
			<td>
				${memberTeacher.talentTitle}
			</td>
			<td>
				居住地址
			</td>
			<td>
				${memberTeacher.address}
			</td>
		</tr>
		<tr>

			<td>婚姻状况</td>
			<td >
				${memberTeacher.maritalStatus}
			</td>
			<td>
				联系邮箱
			</td>
			<td>
				${memberTeacher.email}
			</td>
			<td >
				联系手机
			</td>
			<td>
				${memberTeacher.mobile}
			</td>
			<td>
				家庭电话
			</td>
			<td>
				${memberTeacher.phone}
			</td>
		</tr>
		<tr>

			<td>是否退休</td>
			<td >
				${memberTeacher.isRetire?"是":"否"}
			</td>
			<td>
				退休时间
			</td>
			<td>
				${memberTeacher.retireTime}
			</td>
			<td >
				是否离休
			</td>
			<td colspan="3">
				${memberTeacher.isHonorRetire?"是":"否"}
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
			${partyMap.get(memberTeacher.partyId).name}
			<c:if test="${not empty memberTeacher.branchId}">
				-${branchMap.get(memberTeacher.branchId).name}
			</c:if>
		</td>
	</tr>
	<tr>
		<td>政治面貌</td>
		<td colspan="2">
			${MEMBER_POLITICAL_STATUS_MAP.get(memberTeacher.politicalStatus)}
		</td>
		<td>
			党内职务
		</td>
		<td colspan="2">
			${memberTeacher.partyPost}
		</td>
	</tr>
	<tr>
		<td>
			入党时间
		</td>
		<td>
			${cm:formatDate(memberTeacher.growTime,'yyyy-MM-dd')}
		</td>
		<td>
			转正时间
		</td>
		<td >
			${cm:formatDate(memberTeacher.positiveTime,'yyyy-MM-dd')}
		</td>
		<td>
			进入系统方式
		</td>
		<td>
			${MEMBER_SOURCE_MAP.get(memberTeacher.source)}
		</td>
	</tr>
	<tr>
		<td>提交书面申请书时间</td>
		<td >
			${cm:formatDate(memberTeacher.applyTime,'yyyy-MM-dd')}
		</td>
		<td>
			确定为入党积极分子时间
		</td>

		<td>
			${cm:formatDate(memberTeacher.activeTime,'yyyy-MM-dd')}
		</td>
		<td>
			确定为发展对象时间
		</td>
		<td>
			${cm:formatDate(memberTeacher.candidateTime,'yyyy-MM-dd')}
		</td>
	</tr>
	<tr>
		<td>
			党内奖励
		</td>
		<td colspan="5">
			${memberTeacher.partyReward}
		</td>
	</tr>
	<tr>
		<td>
			其他奖励
		</td>
		<td colspan="5">
			${memberTeacher.otherReward}
		</td>
	</tr>
	</tbody>
</table>
			</div></div></div>
<style>
	.table-striped > tbody > tr:nth-of-type(odd) {
		background-color:inherit;
	}
	.table tbody tr:hover td, .table tbody tr:hover th {
		background-color:transparent;
	}
	.table-striped > tbody > tr > td:nth-of-type(odd) {
		background-color: #f9f9f9;
		text-align: right;
	}
</style>
