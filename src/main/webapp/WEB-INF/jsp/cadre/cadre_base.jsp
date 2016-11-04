<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-paw blue "></i>
			基本信息
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
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
					<img src="${ctx}/avatar/${sysUser.username}" class="avatar">
				</td>

				<td class="bg-right">
					姓名
				</td>
				<td class="bg-left"  style="min-width: 150px;">
					${extJzg.xm}
				</td>

				<td class="bg-right">
					工作证号
				</td>
				<td class="bg-left" style="min-width: 150px;" >
					${extJzg.zgh}
				</td>
				<td class="bg-right">性别</td>
				<td class="bg-left"  style="min-width: 150px;">
					${extJzg.xb}
				</td>

			</tr>
			<tr>
				<td>
					民族
				</td>
				<td>
					${extJzg.mz}
				</td>
				<td>出生日期</td>
				<td >
					${cm:formatDate(extJzg.csrq,'yyyy-MM-dd')}
				</td>
				<td>
					年龄
				</td>
				<td>
					${cm:intervalYearsUntilNow(extJzg.csrq)}
				</td>
			</tr>
			<tr>
				<td>政治面貌</td>
				<td >
					${cadre.isDp?democraticPartyMap.get(cadre.dpTypeId).name:
						MEMBER_POLITICAL_STATUS_MAP.get(member.politicalStatus)}
				</td>
				<td>
					党派加入时间
				</td>
				<td>
					${cadre.isDp?(cm:formatDate(cadre.dpAddTime,'yyyy-MM-dd')):(cm:formatDate(member.growTime,'yyyy-MM-dd'))}
				</td>

				<td>国家/地区</td>
				<td >
					${extJzg.gj}
				</td>
			</tr>
			<tr>
				<td >
					所在党组织
				</td>
				<td>
					${partyMap.get(member.partyId).name}
					<c:if test="${not empty member.branchId}">
					- ${branchMap.get(member.branchId).name}
					</c:if>
				</td>

				<td>证件类型</td>
				<td >
					${extJzg.name}
				</td>
				<td>
					证件号码
				</td>
				<td>
					${extJzg.sfzh}
				</td>
			</tr>
			<tr>
				<td>
					籍贯
				</td>
				<td style="min-width: 100px">
					${extJzg.jg}
				</td>
				<td>出生地</td>
				<td>
					--
				</td>
				<td>
					户籍地
				</td>
				<td >
					--
				</td>
			</tr>
			<tr>
				<td>熟悉专业有何专长</td>
				<td >
					--
				</td>
				<td>
					健康状况
				</td>
				<td colspan="3">
					--
				</td>
			</tr>
			</tbody>
		</table>
</div></div></div>
<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-info-circle blue"></i>
			人事信息
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
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
						${extJzg.dwmc}
					</td>
					<td>
						所在子单位1
					</td>
					<td style="min-width: 80px">
						${extJzg.jzdw1}
					</td>

					<td>
						所在子单位2
					</td>
					<td  style="min-width: 80px">
						${extJzg.jzdw2}
					</td>
				</tr>
				<tr>
					<td>编制类别</td>
					<td >
						${extJzg.bzlx}
					</td>
					<td>
						人员分类
					</td>
					<td>
						${extJzg.rylx}
					</td>
					<td >
						人员身份
					</td>
					<td>
						--
					</td>
				</tr>
				<tr>
					<td>
						人事转否
					</td>
					<td>
						${extJzg.rszf}
					</td>
					<td>人事转入时间</td>
					<td >
						--
					</td>
					<td>
						人员状态
					</td>
					<td>
						${extJzg.ryzt}
					</td>
				</tr>
				<tr>

					<td>岗位类别</td>
					<td >
						--
					</td>
					<td>
						岗位子类别
					</td>
					<td>
						${extJzg.gwlb}
					</td>
					<td >
						在岗情况
					</td>
					<td>
						${extJzg.sfzg}
					</td>
				</tr>
				<tr>

					<td>参加工作时间</td>
					<td >
						--
					</td>
					<td>
						到校时间
					</td>
					<td>${cm:formatDate(extJzg.lxrq,'yyyy-MM-dd')}
					</td>
					<td >
						转正定级时间
					</td>
					<td>
						--
					</td>
				</tr>
				</tbody>
			</table>
		</div></div></div>

<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-pencil-square-o blue"></i>
			任职信息
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
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
						任职单位
					</td>
					<td style="min-width: 80px">
						${unitMap.get(mainCadrePost.unitId).name}
					</td>
					<td>
						单位属性
					</td>
					<td style="min-width: 80px">
						${unitTypeMap.get(unitMap.get(mainCadrePost.unitId).typeId).name}
					</td>

					<td>
						是否双肩挑
					</td>
					<td  style="min-width: 80px">
						${mainCadrePost.isDouble?"是":"否"}
					</td>

				</tr>
				<tr>
					<td >
						现任职务
					</td>
					<td style="min-width: 120px">
						${mainCadrePost.post}
					</td>
					<td >
						任现职时间
					</td>
					<td>${cm:formatDate(mainCadrePost.dispatchCadreRelateBean.last.workTime,'yyyy-MM-dd')}
					</td>
					<td>
						现职务始任时间
					</td>
					<td>${cm:formatDate(mainCadrePost.dispatchCadreRelateBean.first.workTime,'yyyy-MM-dd')}
					</td>
				</tr>
				<tr>
					<td>
						行政级别
					</td>
					<td>
						${adminLevelMap.get(cadreAdminLevel.adminLevelId).name}
					</td>
					<td>任现职级时间</td>
					<td >
						${cm:formatDate(cadreAdminLevel.startDispatch.workTime,'yyyy-MM-dd')}
					</td>
					<td>
						任现职级年限
					</td>
					<td>
						<c:if test="${not empty cadreAdminLevel}">
						<c:set value="${cm:intervalYearsUntilNow(cadreAdminLevel.startDispatch.workTime)}" var="workYear"/>
						${workYear==0?"未满一年":workYear}
						</c:if>
					</td>
				</tr>
				<tr>

					<td>兼职单位1</td>
					<td >
						${unitMap.get(subCadrePost1.unitId).name}
					</td>
					<td>
						兼任职务1
					</td>
					<td>
						${subCadrePost1.post}
					</td>
					<td >
						任兼职时间1
					</td>
					<td>${cm:formatDate(subCadrePost1.dispatchCadreRelateBean.last.workTime,'yyyy-MM-dd')}
					</td>
				</tr>
				<tr>

					<td>兼职单位2</td>
					<td >
						${unitMap.get(subCadrePost2.unitId).name}
					</td>
					<td>
						兼任职务2
					</td>
					<td>
						${subCadrePost2.post}
					</td>
					<td >
						任兼职时间2
					</td>
					<td>${cm:formatDate(subCadrePost2.dispatchCadreRelateBean.last.workTime,'yyyy-MM-dd')}
					</td>
				</tr>
				<tr>

					<td>党委委员</td>
					<td >
						${cadreView.actualEnrolTime}
					</td>
					<td>
						纪委委员
					</td>
					<td colspan="3">
						${cadreView.expectGraduateTime}
					</td>
				</tr>
				<tr>

					<td>担任其他职务</td>
					<td colspan="5">
						${cadreView.actualEnrolTime}
					</td>
				</tr>
				<tr>

					<td>基层工作经历</td>
					<td colspan="5">
						${cadreView.actualEnrolTime}
					</td>
				</tr>
				<tr>

					<td>挂职借调经历</td>
					<td colspan="5">
						${cadreView.actualEnrolTime}
					</td>
				</tr>
				</tbody>
			</table>
		</div></div></div>

<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-star blue"></i>
			职称信息
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">
			<table class="table table-unhover table-bordered">
				<tbody>
				<tr>
					<td rowspan="2"  class="bg-right" style="text-align: center;vertical-align: middle">
						专技岗位
					</td>
					<td class="bg-right">
						专业技术职务
					</td>
					<td style="min-width: 80px" class="bg-left">
						--
					</td>

					<td class="bg-right">
						专业技术职务评定时间
					</td>
					<td  style="min-width: 80px"  class="bg-left">
						--
					</td>
				</tr>
				<tr>
					<td  class="bg-right">专业技术职务等级</td>
					<td >
						--
					</td>
					<td  class="bg-right">
						专业技术职务分级时间
					</td>
					<td>
						--
					</td>

				</tr>
				<tr>
					<td colspan="5" class="bg-left">
						专技岗位备注：
					</td>

				</tr>
				<tr>

					<td  class="bg-right" style="text-align: center">管理岗位</td>

					<td class="bg-right">
						管理岗位等级
					</td>
					<td  class="bg-left">
						${extJzg.glgwdj}
					</td>
					<td class="bg-right">
						管理岗位分级
					</td>
					<td  class="bg-left">
						--
					</td>
				</tr>
				<tr>


					<td colspan="4" class="bg-right">
						管理岗位分级时间
					</td>
					<td>
						--
					</td>
				</tr>
				<tr>
					<td colspan="5" class="bg-left">
						管理岗位备注：--
					</td>

				</tr>
				<tr>

					<td  class="bg-right" style="text-align: center">工勤岗位</td>

					<td class="bg-right">
						工勤岗位等级
					</td>
					<td  class="bg-left">
						--
					</td>
					<td  class="bg-right">
						工勤岗位分级时间
					</td>
					<td  class="bg-left">
						--
					</td>
				</tr>
				<tr>
					<td colspan="5" class="bg-left">
						工勤岗位备注：--
					</td>

				</tr>
				</tbody>
			</table>
		</div></div></div>

<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-graduation-cap blue"></i>
			学历学位信息
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
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
						最高学历
					</td>
					<td style="min-width: 80px">
						${eduTypeMap.get(highEdu.eduId).name}
					</td>
					<td>
						学习方式
					</td>
					<td style="min-width: 80px">
						${learnStyleMap.get(highEdu.learnStyle).name}
					</td>

					<td>
						毕业学校
					</td>
					<td style="min-width: 80px">
						${highEdu.school}
					</td>
				</tr>
				<tr>
					<td>毕业学院</td>
					<td >
						${highEdu.dep}
					</td>
					<td>
						所学专业
					</td>
					<td>
						${highEdu.major}
					</td>
					<td >
						学校类型
					</td>
					<td>${CADRE_SCHOOL_TYPE_MAP.get(highEdu.schoolType)}
					</td>
				</tr>
				<tr>
					<td>
						最高学位
					</td>
					<td>
						${highDegree.degree}
					</td>
					<td>学习方式</td>
					<td >
						${learnStyleMap.get(highDegree.learnStyle).name}
					</td>
					<td>
						毕业学校
					</td>
					<td>
						${highDegree.school}
					</td>
				</tr>
				<tr>

					<td>毕业学院</td>
					<td >
						${highDegree.dep}
					</td>
					<td>
						所学专业
					</td>
					<td>
						${highDegree.major}
					</td>
					<td >
						学校类型
					</td>
					<td>
						${CADRE_SCHOOL_TYPE_MAP.get(highDegree.schoolType)}
					</td>
				</tr>

				</tbody>
			</table>
		</div></div></div>


<div class="clearfix form-actions center">
	<button class="closeView btn btn-default" type="button">
		<i class="ace-icon fa fa-undo"></i>
		返回
	</button>
</div>