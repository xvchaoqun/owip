<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<form class="form-horizontal" action="${ctx}/memberTeacher_au" id="modalForm" method="post">

	<table class="table table-bordered table-striped">
		<tbody>
		<tr>
			<td>
				姓名
			</td>
			<td>
				${memberTeacher.realname}
			</td>
			<td>
				性别
			</td>
			<td>
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
				${memberTeacher.isRetire}
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
				${memberTeacher.isHonorRetire}
			</td>
		</tr>
		</tbody>
	</table>
</form>

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
<script>
	$("#modal form").validate({
		submitHandler: function (form) {
			$(form).ajaxSubmit({
				success:function(ret){
					if(ret.success){
						page_reload();
						toastr.success('操作成功。', '成功');
					}
				}
			});
		}
	});
	$('#modalForm [data-rel="select2"]').select2();
	$('[data-rel="tooltip"]').tooltip();
	$('#modalForm [data-rel="select2-ajax"]').select2({
		ajax: {
			dataType: 'json',
			delay: 200,
			data: function (params) {
				return {
					searchStr: params.term,
					pageSize: 10,
					pageNo: params.page
				};
			},
			processResults: function (data, params) {
				params.page = params.page || 1;
				return {results: data.options,  pagination: {
					more: (params.page * 10) < data.totalCount
				}};
			},
			cache: true
		}
	});
</script>