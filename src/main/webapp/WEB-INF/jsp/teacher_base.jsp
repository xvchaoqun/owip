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
					<td rowspan="6" style="text-align: center;vertical-align: middle;
				 width: 50px;background-color: #fff;">
						<img src="${ctx}/avatar?path=${sysUser.avatar}" class="avatar">
					</td>
					<td class="bg-right">
						姓名
					</td>
					<td class="bg-left" style="min-width: 80px">
						${sysUser.realname}
					</td>
					<td class="bg-right">
						性别
					</td>
					<td class="bg-left" style="min-width: 80px">
						${GENDER_MAP.get(sysUser.gender)}
					</td>

					<td class="bg-right">
						民族
					</td>
					<td class="bg-left" style="min-width: 120px">
						${sysUser.nation}
					</td>
					<td  class="bg-right">
						身份证号
					</td>
					<td class="bg-left" style="min-width: 120px">
						${sysUser.idcard}
					</td>
				</tr>
				<tr>
					<td class="bg-right">工作证号</td>
					<td  class="bg-left">
						${sysUser.code}
					</td>
					<td class="bg-right">
						籍贯
					</td>
					<td class="bg-left">
						${sysUser.nativePlace}
					</td>
					<td  class="bg-right">
						最高学历
					</td>
					<td class="bg-left">
						${teacher.education}
					</td>
					<td class="bg-right">
						最高学位
					</td>
					<td class="bg-left">
						${teacher.degree}
					</td>
				</tr>
				<tr>
					<td class="bg-right">
						学位授予日期
					</td>
					<td class="bg-left">
						${cm:formatDate(teacher.degreeTime, "yyyy-MM-dd")}
					</td>
					<td class="bg-right">所学专业</td>
					<td  class="bg-left">
						${teacher.major}
					</td>
					<td class="bg-right">
						毕业学校
					</td>
					<td class="bg-left">
						${teacher.school}
					</td>
					<td  class="bg-right">
						毕业学校类型
					</td>
					<td class="bg-left">
						${teacher.schoolType}
					</td>
				</tr>
				<tr>

					<td class="bg-right">到校日期</td>
					<td class="bg-left" >
						${cm:formatDate(teacher.arriveTime, "yyyy-MM-dd")}
					</td>
					<td class="bg-right">
						编制类别
					</td>
					<td class="bg-left">
						${teacher.authorizedType}
					</td>
					<td  class="bg-right">
						人员分类
					</td>
					<td class="bg-left">
						${teacher.staffType}
					</td>
					<td class="bg-right">人员状态</td>
					<td  class="bg-left">
						${teacher.staffStatus}
					</td>
				</tr>
				<tr>

					<td class="bg-right">岗位类别</td>
					<td  class="bg-left">
						${teacher.postClass}
					</td>
					<td class="bg-right">
						主岗等级
					</td>
					<td class="bg-left">
						${teacher.mainPostLevel}
					</td>
					<td  class="bg-right">
						在岗情况
					</td>
					<td class="bg-left">
						${teacher.onJob}
					</td>
					<td class="bg-right">
						专业技术职务
					</td>
					<td class="bg-left">
						${teacher.proPost}
					</td>
				</tr>
				<tr>

					<td  class="bg-right">专技岗位等级</td>
					<td  class="bg-left">
						${teacher.proPostLevel}
					</td>
					<td class="bg-right">
						职称级别
					</td>
					<td class="bg-left">
						${teacher.titleLevel}
					</td>
					<td  class="bg-right">
						管理岗位等级
					</td>
					<td class="bg-left">
						${teacher.manageLevel}
					</td>
					<td class="bg-right">
						工勤岗位等级
					</td>
					<td class="bg-left">
						${teacher.officeLevel}
					</td>
				</tr>
				<tr>

					<td class="bg-right" colspan="2">行政职务</td>
					<td  class="bg-left">
						${teacher.post}
					</td>
					<td class="bg-right">
						任职级别
					</td>
					<td class="bg-left">
						${teacher.postLevel}
					</td>
					<td class="bg-right">
						居住地址
					</td>
					<td class="bg-left" colspan="3">
						${teacher.address}
					</td>
				</tr>
				<tr>

					<td class="bg-right" colspan="2">婚姻状况</td>
					<td  class="bg-left">
						${teacher.maritalStatus}
					</td>
					<td class="bg-right">
						联系邮箱
					</td>
					<td class="bg-left">
						${sysUser.email}
					</td>
					<td  class="bg-right">
						联系手机
					</td>
					<td class="bg-left">
						${sysUser.mobile}
					</td>
					<td class="bg-right">
						家庭电话
					</td>
					<td class="bg-left">
						${sysUser.homePhone}
					</td>
				</tr>
				<tr>

					<td  class="bg-right" colspan="2">是否退休</td>
					<td class="bg-left" >
						${teacher.isRetire?"是":"否"}
					</td>
					<td class="bg-right">
						退休时间
					</td>
					<td class="bg-left">${cm:formatDate(teacher.retireTime,'yyyy-MM-dd')}
					</td>
					<td  class="bg-right">
						是否离休
					</td>
					<td  class="bg-left">
						${teacher.isHonorRetire?"是":"否"}
					</td>
					<td class="bg-right">组织关系状态</td>
					<td class="bg-left">
						${memberTeacher.status==null?"-":MEMBER_STATUS_MAP.get(memberTeacher.status)}
					</td>
				</tr>
				<tr>
					<td  class="bg-right" colspan="2">
						人才/荣誉称号
					</td>
					<td class="bg-left" colspan="7">
						${teacher.talentTitle}
					</td>

				</tr>
				</tbody>
			</table>
			</div></div></div>