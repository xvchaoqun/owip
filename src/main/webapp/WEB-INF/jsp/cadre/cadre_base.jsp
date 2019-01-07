<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=CadreConstants.CADRE_SCHOOL_TYPE_MAP%>" var="CADRE_SCHOOL_TYPE_MAP"/>

<shiro:lacksRole name="${ROLE_ONLY_CADRE_VIEW}">
	<c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
		<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
			<li class="${empty param.type?"active":""}">
				<a href="javascript:" onclick="_innerPage()"><i class="fa fa-flag"></i> 查看基本信息</a>
			</li>
			<li class="${param.type==1?"active":""}">
				<a href="javascript:" onclick="_innerPage(1)"><i class="fa fa-flag"></i> 修改基本信息</a>
			</li>

		</ul>
	</c:if>
</shiro:lacksRole>
<c:if test="${empty param.type}">
	<div class="widget-box transparent">
		<div class="widget-header widget-header-flat">
			<h4 class="widget-title lighter">
				<i class="ace-icon fa fa-paw blue "></i>
				基本信息
			</h4>

			<div class="widget-toolbar">
				<a href="javascript:;" data-action="collapse">
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
							<img src="${ctx}/avatar?path=${cm:encodeURI(uv.avatar)}&t=<%=new Date().getTime()%>" class="avatar">
						</td>

						<td class="bg-right">
							姓名
						</td>
						<td class="bg-left"  style="min-width: 150px;">
								${uv.realname}
						</td>

						<td class="bg-right">
							工作证号
						</td>
						<td class="bg-left" style="min-width: 150px;" >
								${uv.code}
						</td>
						<td class="bg-right">性别</td>
						<td class="bg-left"  style="min-width: 150px;">
								${GENDER_MAP.get(uv.gender)}
						</td>

					</tr>
					<tr>
						<td>
							民族
						</td>
						<td>
								${uv.nation}
						</td>
						<td>出生日期</td>
						<td >
								${cm:formatDate(uv.birth,'yyyy-MM-dd')}
						</td>
						<td>
							年龄
						</td>
						<td>
								${uv.birth==null?'':cm:intervalYearsUntilNow(uv.birth)}
						</td>
					</tr>
					<tr>
						<td>政治面貌</td>
						<td>
								${cm:cadreParty(cadre.isOw, cadre.owGrowTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('partyName')}
						</td>
						<td>
							党派加入时间
						</td>
						<td>
								${cm:cadreParty(cadre.isOw, cadre.owGrowTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('growTime')}
						</td>

						<td>国家/地区</td>
						<td>
								${uv.country}
						</td>
					</tr>
					<tr>
						<c:if test="${_hasPartyModule}">
							<td >
								所在党组织
							</td>
							<td>
									${cm:displayParty(member.partyId, member.branchId)}
							</td>
						</c:if>
						<td>证件类型</td>
						<td >
								${uv.idcardType}
						</td>
						<td>
							证件号码
						</td>
						<td colspan="${_hasPartyModule?'':3}">
								${uv.idcard}
						</td>
					</tr>
					<tr>
						<td>
							籍贯
						</td>
						<td style="min-width: 100px">
								${uv.nativePlace}
						</td>
						<td>出生地</td>
						<td>
								${uv.homeplace}
						</td>
						<td>
							户籍地
						</td>
						<td >
								${uv.household}
						</td>
					</tr>
					<tr>
						<td>
							健康状况
						</td>
						<td>
								${cm:getMetaType(uv.health).name}
						</td>
						<td>熟悉专业有何专长</td>
						<td colspan="3">
								${uv.specialty}
						</td>
					</tr>
					</tbody>
				</table>
			</div></div></div>

	<div class="widget-box transparent">
		<div class="widget-header widget-header-flat">
			<h4 class="widget-title lighter">
				<i class="ace-icon fa fa-phone-square blue"></i>
				联系方式
			</h4>

			<div class="widget-toolbar">
				<a href="javascript:;" data-action="collapse">
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
							手机号
						</td>
						<td style="min-width: 80px">
								${uv.mobile}
						</td>
						<td>
							办公电话
						</td>
						<td style="min-width: 80px">
								${uv.phone}
						</td>

						<td>
							电子邮箱
						</td>
						<td  style="min-width: 80px">
								${uv.email}
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
				<a href="javascript:;" data-action="collapse">
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
								${uv.unit}
						</td>
						<td>编制类别</td>
						<td >
								${teacherInfo.authorizedType}
						</td>
						<td>
							人员分类
						</td>
						<td>
								${teacherInfo.staffType}
						</td>
					</tr>
					<tr>
						<td>
							人员状态
						</td>
						<td>
								${teacherInfo.staffStatus}
						</td>
						<td >
							在岗情况
						</td>
						<td>
								${teacherInfo.onJob}
						</td>
						<td >
							人事转否
						</td>
						<td>
								${teacherInfo.personnelStatus}
						</td>
					</tr>
					<tr>

						<td>岗位类别</td>
						<td >
								${teacherInfo.postClass}
						</td>
						<td>
							岗位子类别
						</td>
						<td>
								${teacherInfo.subPostClass}
						</td>
						<td >
							主岗等级
						</td>
						<td>
								${teacherInfo.mainPostLevel}
						</td>
					</tr>
					<tr>
						<td>
							工龄起算日期
						</td>
						<td>
								${cm:formatDate(teacherInfo.workStartTime, "yyyy-MM-dd")}
						</td>
						<td>间断工龄</td>
						<td >
								${teacherInfo.workBreak}
						</td>
						<td>
							到校时间
						</td>
						<td>
								${cm:formatDate(teacherInfo.arriveTime, "yyyy-MM-dd")}
						</td>
					</tr>
					<tr>

						<td>参加工作时间</td>
						<td >
								${cm:formatDate(teacherInfo.workTime, "yyyy.MM")}
						</td>
						<td >
							转正定级时间
						</td>
						<td colspan="3">
								${cm:formatDate(teacherInfo.regularTime, "yyyy-MM-dd")}
						</td>

					</tr>
					<tr>
						<td  class="bg-right">
							人才/荣誉称号
						</td>
						<td class="bg-left" colspan="5">
								${teacherInfo.talentTitle}
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
				<a href="javascript:;" data-action="collapse">
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
								${cm:getMetaType(unitMap.get(mainCadrePost.unitId).typeId).name}
						</td>

						<td>
							是否双肩挑
						</td>
						<td  style="min-width: 80px">
							<c:if test="${not empty mainCadrePost}">
								${mainCadrePost.isDouble?"是":"否"}
							</c:if>
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
								${cm:getMetaType(cadreAdminLevel.adminLevelId).name}
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
						<%--<tr>

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
                        </tr>--%>
						<%--<tr>

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
                        </tr>--%>
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
				<a href="javascript:;" data-action="collapse">
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
								${teacherInfo.proPost}
						</td>

						<td class="bg-right">
							专业技术职务评定时间
						</td>
						<td  style="min-width: 80px"  class="bg-left">
								${cm:formatDate(teacherInfo.proPostTime, "yyyy.MM")}
						</td>
					</tr>
					<tr>
						<td  class="bg-right">专业技术职务等级</td>
						<td >
								${teacherInfo.proPostLevel}
						</td>
						<td  class="bg-right">
							专业技术职务分级时间
						</td>
						<td>
								${cm:formatDate(teacherInfo.proPostLevelTime, "yyyy.MM")}
						</td>

					</tr>
						<%--<tr>
                            <td colspan="2" class="bg-right">
                                专技岗位备注：
                            </td>
                            <td colspan="3"  class="bg-left">
                                --
                            </td>
                        </tr>--%>
					<tr>

						<td  class="bg-right" style="text-align: center">管理岗位</td>

						<td class="bg-right">
							管理岗位等级
						</td>
						<td  class="bg-left">
								${teacherInfo.manageLevel}
						</td>
						<td class="bg-right">
							管理岗位分级时间
						</td>
						<td  class="bg-left">
								${cm:formatDate(teacherInfo.manageLevelTime, "yyyy.MM")}
						</td>
					</tr>

						<%--<tr>
                            <td colspan="2" class="bg-right">
                                管理岗位备注：
                            </td>
                            <td colspan="3"  class="bg-left">
                                --
                            </td>
                        </tr>--%>
					<tr>

						<td  class="bg-right" style="text-align: center">工勤岗位</td>

						<td class="bg-right">
							工勤岗位等级
						</td>
						<td  class="bg-left">
								${teacherInfo.officeLevel}
						</td>
						<td  class="bg-right">
							工勤岗位分级时间
						</td>
						<td  class="bg-left">
								${cm:formatDate(teacherInfo.officeLevelTime, "yyyy.MM")}
						</td>
					</tr>
						<%--<tr>
                            <td colspan="2" class="bg-right">
                                工勤岗位备注：
                            </td>
                            <td colspan="3"  class="bg-left">
                                --
                            </td>
                        </tr>--%>
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
				<a href="javascript:;" data-action="collapse">
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
								${cm:getMetaType(highEdu.eduId).name}
						</td>
						<td>
							学习方式
						</td>
						<td style="min-width: 80px">
								${cm:getMetaType(highEdu.learnStyle).name}
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
								${cm:getMetaType(highDegree.learnStyle).name}
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
	<c:if test="${param._auth!='self'}">
		<div class="clearfix form-actions center">
			<button class="hideView btn btn-default"
					data-load-el="#${param.loadEl}" data-hide-el="#${param.hideEl}"
					type="button">
				<i class="ace-icon fa fa-undo"></i>
				返回
			</button>
		</div>
	</c:if>
</c:if>
<c:if test="${cm:isPermitted(PERMISSION_CADREADMIN) || hasDirectModifyCadreAuth}">
	<c:if test="${param.type==1}">
		<form class="form-horizontal" action="${ctx}/cadreBaseInfo?cadreId=${cadre.id}" id="modalForm" method="post" enctype="multipart/form-data">
			<div class="widget-box transparent">
				<div class="widget-header widget-header-flat">
					<h4 class="widget-title lighter">
						<i class="ace-icon fa fa-paw blue "></i>
						基本信息
					</h4>
				</div>

				<div class="widget-body">
					<div class="widget-main no-padding">

						<table class="table table-unhover table-bordered table-striped" >
							<tbody>
							<tr>
								<td id="_avatarTitle" class="bg-right" style="text-align: left!important;">头像：</td>

								<td class="bg-right">
									姓名
								</td>
								<td class="bg-left" style="min-width: 150px;">
										${uv.realname}
								</td>

								<td class="bg-right">
									工作证号
								</td>
								<td class="bg-left" style="min-width: 150px;">
										${uv.code}
								</td>
								<td class="bg-right">性别</td>
								<td class="bg-left" style="min-width: 150px;">
										${GENDER_MAP.get(uv.gender)}
								</td>

							</tr>
							<tr>
								<td rowspan="5" style="text-align: center;
				                         width: 50px;background-color: #fff;">
									<div  style="width:145px">
										<input type="file" name="_avatar" id="_avatar"/>
									</div>
									<div>
										<button type="button" class="btn btn-xs btn-primary" onclick='$("#_avatar").click()'>
											<i class="fa fa-upload"></i> 重传</button>
									</div>
								</td>
								<td class="bg-right">
									民族
								</td>
								<td class="bg-left">
										${uv.nation}
								</td>
								<td class="bg-right">出生日期</td>
								<td class="bg-left">
										${cm:formatDate(uv.birth,'yyyy-MM-dd')}
								</td>
								<td class="bg-right">
									年龄
								</td>
								<td class="bg-left">
										${empty uv.birth?'':cm:intervalYearsUntilNow(uv.birth)}
								</td>
							</tr>
							<tr>
								<td>政治面貌</td>
								<td>
									<c:set var="original" value="${cm:cadreParty(cadre.isOw, cadre.owGrowTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('partyName')}"/>
									<c:if test="${member!=null}">${original}</c:if>
									<c:if test="${member==null}">
										<select data-rel="select2" name="dpTypeId" data-width="150" data-placeholder="请选择">
											<option></option>
											<option value="0">中共党员</option>
											<jsp:include page="/metaTypes?__code=mc_democratic_party"/>
										</select>
									</c:if>
								</td>
								<td>
									党派加入时间
								</td>
								<td>
									<c:set var="original" value="${cm:cadreParty(cadre.isOw, cadre.owGrowTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('growTime')}"/>
									<c:if test="${member!=null}">${original}</c:if>
									<c:if test="${member==null}">
										<div class="input-group" style="width: 130px">
											<input class="form-control date-picker" type="text" name="_dpAddTime" data-date-format="yyyy-mm-dd"/>
											<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
										</div>
									</c:if>
									<script type="text/javascript">
										<c:choose>
										<c:when test="${cadre.dpTypeId>0}">
										$("#modalForm select[name=dpTypeId]").val(${cadre.dpTypeId});
										$("#modalForm input[name=_dpAddTime]").val('${cm:formatDate(cadre.dpGrowTime, "yyyy-MM-dd")}');
										</c:when>
										<c:when test="${cadre.isOw}">
										$("#modalForm select[name=dpTypeId]").val(0);
										$("#modalForm input[name=_dpAddTime]").val('${cm:formatDate(cadre.owGrowTime, "yyyy-MM-dd")}');
										</c:when>
										</c:choose>
										$("#modalForm select[name=dpTypeId]").on("change",function(){
											var val = $.trim($(this).val());
											$("#modalForm input[name=_dpAddTime]").prop("required", val.length>0 && val >= 0);
										}).change();
									</script>
								</td>

								<td>国家/地区</td>
								<td>
										${uv.country}
								</td>
							</tr>
							<tr>
								<c:if test="${_hasPartyModule}">
									<td>
										所在党组织
									</td>
									<td>
											${cm:displayParty(member.partyId, member.branchId)}
									</td>
								</c:if>
								<td>证件类型</td>
								<td>
										${uv.idcardType}
								</td>
								<td>
									证件号码
								</td>
								<td colspan="${_hasPartyModule?'':3}">
										${uv.idcard}
								</td>
							</tr>
							<tr>
								<td>
									籍贯
								</td>
								<td style="min-width: 100px">
									<input required type="text" name="nativePlace" value="${uv.nativePlace}">
									<div class="inline-block">
										格式：“河北保定”或“北京海淀”
									</div>
								</td>
								<td>出生地</td>
								<td>
									<input required type="text" name="homeplace" value="${uv.homeplace}">
									<div class="inline-block">
										格式：“河北保定”或“北京海淀”
									</div>
								</td>
								<td>
									户籍地
								</td>
								<td>
									<input required type="text" name="household" value="${uv.household}">
									<div class="inline-block">
										格式：“河北保定”或“北京海淀”
									</div>
								</td>
							</tr>
							<tr>
								<td>健康状况</td>
								<td>
									<select required data-rel="select2" name="health"
											data-placeholder="请选择" data-width="162">
										<option></option>
										<c:import url="/metaTypes?__code=mc_health"/>
									</select>
									<script type="text/javascript">
										$("select[name=health]").val('${uv.health}');
									</script>
								</td>
								<td>
									熟悉专业有何专长
								</td>
								<td colspan="3">
									<input required type="text" name="specialty" value="${uv.specialty}" style="width: 500px">
								</td>
							</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="widget-box transparent">
				<div class="widget-header widget-header-flat">
					<h4 class="widget-title lighter">
						<i class="ace-icon fa fa-info-circle blue"></i>
						人事信息
					</h4>
				</div>

				<div class="widget-body">
					<div class="widget-main no-padding">
						<table class="table table-unhover table-bordered table-striped">
							<tbody>
							<tr>
								<td style="width: 300px;">
									参加工作时间
								</td>
								<td title="${hasVerifyWorkTime?'已根据您的档案记载对参加工作时间进行了组织认定':''}">
									<c:set var="original" value="${cm:formatDate(cadre.workTime,'yyyy.MM')}"/>
									<c:if test="${hasVerifyWorkTime}">${original}</c:if>
									<c:if test="${!hasVerifyWorkTime}">
										<div class="input-group" style="width: 130px">
											<input class="form-control date-picker" type="text" name="_workTime"
												   data-date-min-view-mode="1"
												   data-date-format="yyyy.mm" value="${original}"/>
											<span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
										</div>
									</c:if>
								</td>
							</tr>

							</tbody>
						</table>
					</div>
				</div>
			</div>
			<shiro:hasAnyRoles name="${ROLE_CADRERECRUIT},${ROLE_CADREADMIN}">
				<div class="widget-box transparent">
					<div class="widget-header widget-header-flat">
						<h4 class="widget-title lighter">
							<i class="ace-icon fa fa-info-circle blue"></i>
							干部信息
						</h4>
					</div>

					<div class="widget-body">
						<div class="widget-main no-padding">
							<table class="table table-unhover table-bordered table-striped">
								<tbody>
								<tr>
									<td style="width: 300px;">
										所在单位及职务
									</td>
									<td>
										<input required type="text" name="title" value="${cadre.title}" style="width: 500px">
									</td>
								</tr>

								</tbody>
							</table>
						</div>
					</div>
				</div>
			</shiro:hasAnyRoles>
			<div class="widget-box transparent">
				<div class="widget-header widget-header-flat">
					<h4 class="widget-title lighter">
						<i class="ace-icon fa fa-phone-square blue"></i>
						联系方式
					</h4>
				</div>

				<div class="widget-body">
					<div class="widget-main no-padding">
						<table class="table table-unhover table-bordered table-striped">
							<tbody>
							<tr>
								<td>
									手机号
								</td>
								<td style="min-width: 80px">
									<input required type="text" class="mobile" name="mobile" value="${uv.mobile}">
								</td>
								<td>
									办公电话
								</td>
								<td style="min-width: 80px">
									<input type="text" name="phone" value="${uv.phone}">
								</td>

								<td>
									电子邮箱
								</td>
								<td style="min-width: 80px">
									<input class="email" type="text" name="email" value="${uv.email}">
								</td>
							</tr>

							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="clearfix form-actions center">
				<a class="popupBtn btn btn-warning"
				   data-width="800"
				   data-url="${ctx}/hf_content?code=hf_cadre_base_info">
					<i class="fa fa-info-circle"></i> 填写说明</a>
				&nbsp; &nbsp; &nbsp;
				<button id="submitBtn" class="btn btn-info"
						data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
						type="button">
					<i class="ace-icon fa fa-save bigger-110"></i>
					保存
				</button>
					<%--
                                &nbsp; &nbsp; &nbsp;
                                <button class="hideView btn" type="button">
                                    <i class="ace-icon fa fa-undo bigger-110"></i>
                                    取消
                                </button>--%>
			</div>
		</form>
		<style>
			.ace-file-container{
				height: 200px!important;
			}
			.ace-file-multiple .ace-file-container .ace-file-name .ace-icon{
				line-height: 120px!important;
			}
		</style>
	</c:if>
	<script>
		function _innerPage(type) {
			if(type==undefined){
				$("#view-box .tab-content").loadPage("${ctx}/cadre_base?cadreId=${param.cadreId}&_auth=${param._auth}")
			}else{
				$("#view-box .tab-content").loadPage("${ctx}/cadre_base?cadreId=${param.cadreId}&_auth=${param._auth}&type="+type)
			}
		}
		<c:if test="${param.type==1}">
		$.fileInput($("#_avatar"),{
			style:'well',
			btn_choose:'更换头像',
			btn_change:null,
			no_icon:'ace-icon fa fa-picture-o',
			thumbnail:'large',
			maxSize:${_uploadMaxSize},
			droppable:true,
			previewWidth: 143,
			previewHeight: 198,
			allowExt: ['jpg', 'jpeg', 'png', 'gif'],
			allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
		})
		$('#modalForm button[type=reset]').on(ace.click_event, function(){
			//$('#user-profile input[type=file]').ace_file_input('reset_input');
			$("#_avatar").ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/avatar?path=${cm:encodeURI(uv.avatar)}&t=<%=new Date().getTime()%>'}]);
		});
		$("#_avatar").ace_file_input('show_file_list', [{type: 'image', name: '${ctx}/avatar?path=${cm:encodeURI(uv.avatar)}&t=<%=new Date().getTime()%>'}]);

		$("#submitBtn").click(function(){$("#modalForm").submit();return false;});
		$("#modalForm").validate({
			submitHandler: function (form) {
				if($("select[name=dpTypeId]").val()>0 && $("input[name=_dpAddTime]").val()==""){
					SysMsg.info("请填写党派加入时间");
					return ;
				}
				if($("select[name=dpTypeId]").val()=='' && $("input[name=_dpAddTime]").val()!=""){
					SysMsg.info("请选择政治面貌");
					return ;
				}
				var $btn = $("#submitBtn").button('loading');
				$(form).ajaxSubmit({
					success: function (ret) {
						if (ret.success) {
							_innerPage();
						}
						$btn.button('reset');
					}
				});
			}
		});
		$('[data-rel="select2"]').select2();
		$.register.date($('.date-picker'));
		</c:if>
	</script>
</c:if>
