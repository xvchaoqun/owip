<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_POLITICAL_STATUS_GROW" value="<%=MemberConstants.MEMBER_POLITICAL_STATUS_GROW%>"/>

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
					<c:set var="sysUser" value="${cm:getUserById(param.userId)}"/>
					<td rowspan="6" style="text-align: center;vertical-align: middle;
				 width: 50px;background-color: #fff;">
						<img src="${ctx}/avatar?path=${cm:encodeURI(sysUser.avatar)}"  class="avatar">
					</td>
					<td class="bg-right">
						姓名
					</td>
					<td class="bg-left" style="min-width: 80px">
						${member.realname}
					</td>
					<td class="bg-right">
						性别
					</td>
					<td class="bg-left" style="min-width: 80px">
						${GENDER_MAP.get(member.gender)}
					</td>

					<td class="bg-right">
						民族
					</td>
					<td class="bg-left" style="min-width: 120px">
						${member.nation}
					</td>
					<td  class="bg-right">
						身份证号
					</td>
					<td class="bg-left" style="min-width: 120px">
						${member.idcard}
					</td>
				</tr>
				<tr>
					<td class="bg-right">工作证号</td>
					<td  class="bg-left">
						${member.code}
					</td>
					<td class="bg-right">
						籍贯
					</td>
					<td class="bg-left">
						${member.nativePlace}
					</td>
					<td  class="bg-right">
						最高学历
					</td>
					<td class="bg-left">
						${member.education}
					</td>
					<td class="bg-right">
						最高学位
					</td>
					<td class="bg-left">
						${member.degree}
					</td>
				</tr>
				<tr>
					<td class="bg-right">
						学位授予日期
					</td>
					<td class="bg-left">
						${cm:formatDate(member.degreeTime, "yyyy-MM-dd")}
					</td>
					<td class="bg-right">所学专业</td>
					<td  class="bg-left">
						${member.major}
					</td>
					<td class="bg-right">
						毕业学校
					</td>
					<td class="bg-left">
						${member.school}
					</td>
					<td  class="bg-right">
						毕业学校类型
					</td>
					<td class="bg-left">
						${member.schoolType}
					</td>
				</tr>
				<tr>

					<td class="bg-right">到校日期</td>
					<td class="bg-left" >
							${cm:formatDate(member.arriveTime, "yyyy-MM-dd")}
					</td>
					<td class="bg-right">
						编制类别
					</td>
					<td class="bg-left">
						${member.authorizedType}
					</td>
					<td  class="bg-right">
						人员分类
					</td>
					<td class="bg-left" colspan="3">
						${member.staffType}
					</td>
				</tr>
				<tr>

					<td class="bg-right">岗位类别</td>
					<td  class="bg-left">
						${member.postClass}
					</td>
					<td class="bg-right">
						主岗等级
					</td>
					<td class="bg-left">
						${member.mainPostLevel}
					</td>
					<td class="bg-right">
						专业技术职务
					</td>
					<td class="bg-left" colspan="3">
						${member.proPost}
					</td>
				</tr>
				<tr>

					<td  class="bg-right">专技岗位等级</td>
					<td  class="bg-left">
						${member.proPostLevel}
					</td>
					<td class="bg-right">
						职称级别
					</td>
					<td class="bg-left">
						${member.titleLevel}
					</td>
					<td  class="bg-right">
						管理岗位等级
					</td>
					<td class="bg-left">
						${member.manageLevel}
					</td>
					<td class="bg-right">
						工勤岗位等级
					</td>
					<td class="bg-left">
						${member.officeLevel}
					</td>
				</tr>
				<tr>

					<td class="bg-right" colspan="2">行政职务</td>
					<td  class="bg-left">
						${member.post}
					</td>
					<td class="bg-right">
						任职级别
					</td>
					<td class="bg-left">
						${member.postLevel}
					</td>
					<td  class="bg-right">
						人才类型
					</td>
					<td class="bg-left" colspan="3">
						${member.talentType}
					</td>

				</tr>
				<tr>

					<td class="bg-right" colspan="2">婚姻状况</td>
					<td  class="bg-left">
						${member.maritalStatus}
					</td>
					<td class="bg-right">
						邮箱
					</td>
					<td class="bg-left">
						${member.email}
					</td>

					<td class="bg-right" >
						居住地址
					</td>
					<td class="bg-left">
						${member.address}
					</td>
				</tr>
				<tr>
					<td  class="bg-right" colspan="2">
						手机号码
					</td>
					<td class="bg-left">
						${member.mobile}
					</td>
					<td  class="bg-right" >是否退休</td>
					<td class="bg-left" >
						${member.isRetire?"是":"否"}
					</td>
					<td class="bg-right">
						退休时间
					</td>
					<td class="bg-left">${cm:formatDate(member.retireTime,'yyyy-MM-dd')}
					</td>
					<td  class="bg-right">
						是否离休
					</td>
					<td  class="bg-left">
						${member.isHonorRetire?"是":"否"}
					</td>
				</tr>
				<tr>
					<td  class="bg-right" colspan="2">人员状态</td>
					<td class="bg-left" colspan="3">
						${member.staffStatus}
					</td>
					<td  class="bg-right">在岗情况</td>
					<td class="bg-left" colspan="3">
						${member.onJob}
					</td>
				</tr>
				<tr>
					<td  class="bg-right" colspan="2">
						人才/荣誉称号
					</td>
					<td class="bg-left" colspan="7">
						${member.talentTitle}
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
		<td class="bg-left" colspan="5">
				${cm:displayParty(member.partyId, member.branchId)}
		</td>
	</tr>
	<tr>
		<td class="bg-right">党籍状态</td>
		<td class="bg-left" width="300">
			${MEMBER_POLITICAL_STATUS_MAP.get(member.politicalStatus)}
<shiro:hasPermission name="member:edit">
				<c:if test="${member.politicalStatus==MEMBER_POLITICAL_STATUS_GROW}">
					&nbsp;
					<button class="confirm btn btn-xs btn-primary"
							data-title="同步预备党员"
							data-msg="确定将此预备党员信息导入<span style='color:red;font-weight:bolder;'>[入党申请管理-预备党员]支部审核阶段</span>？"
							data-url="${ctx}/snyc_memberApply?userId=${param.userId}">
						<i class="fa fa-random "></i> 同步至入党申请列表</button>
				</c:if>
	</shiro:hasPermission>
		</td>
		<td class="bg-right">状态</td>
		<td class="bg-left">
			${MEMBER_STATUS_MAP.get(member.status)}
		</td>
		<td class="bg-right">
			党内职务
		</td>
		<td class="bg-left">
			${member.partyPost}
		</td>
	</tr>
	<tr>
		<td class="bg-right">
			入党时间
		</td>
		<td class="bg-left" width="150">
			${cm:formatDate(member.growTime,'yyyy-MM-dd')}
		</td>
		<td class="bg-right">
			入党介绍人
		</td>
		<td class="bg-left" >
			${member.sponsor}
		</td>
		<td class="bg-right">
			入党时所在党支部
		</td>
		<td class="bg-left">
			${member.growBranch}
		</td>
	</tr>
	<tr>
		<td class="bg-right">
			转正时间
		</td>
		<td class="bg-left" >
			${cm:formatDate(member.positiveTime,'yyyy-MM-dd')}
		</td>
		<td class="bg-right">
			转正时所在党支部
		</td>
		<td class="bg-left" width="150">
			${member.positiveBranch}
		</td>
		<td class="bg-right">
			进入系统方式
		</td>
		<td class="bg-left">
			${MEMBER_SOURCE_MAP.get(member.source)}
		</td>
	</tr>
	<tr>
		<td class="bg-right">提交书面申请书时间</td>
		<td  class="bg-left">
			${cm:formatDate(member.applyTime,'yyyy-MM-dd')}
		</td>
		<td class="bg-right">
			确定为入党积极分子时间
		</td>

		<td class="bg-left">
			${cm:formatDate(member.activeTime,'yyyy-MM-dd')}
		</td>
		<td class="bg-right">
			确定为发展对象时间
		</td>
		<td class="bg-left">
			${cm:formatDate(member.candidateTime,'yyyy-MM-dd')}
		</td>
	</tr>
	<tr>
		<td class="bg-right">
			党内奖励
		</td>
		<td class="bg-left" colspan="5">
			${member.partyReward}
		</td>
	</tr>
	<tr>
		<td class="bg-right">
			其他奖励
		</td>
		<td class="bg-left" colspan="5">
			${member.otherReward}
		</td>
	</tr>
	</tbody>
</table>
			</div></div></div>
<div class="clearfix form-actions center">

	<c:if test="${sysUser.source==USER_SOURCE_JZG}">
	<button class="btn btn-info  btn-pink" onclick="_sync(${param.userId}, this)" type="button"
			data-loading-text="<i class='fa fa-refresh fa-spin'></i> 同步中..." autocomplete="off">
		<i class="ace-icon fa fa-random "></i>
		同步学校信息
	</button>
	</c:if>

	&nbsp; &nbsp; &nbsp;
	<button class="hideView btn btn-default" type="button">
		<i class="ace-icon fa fa-undo"></i>
		返回
	</button>
</div>

<script>
	function _reload(){
		$("#body-content-view #view-box .nav-tabs li.active a").click();
	}

	function _sync(userId, btn){

		var $btn = $(btn).button('loading')
		var $container = $("#view-box");
		$container.showLoading({'afterShow':
				function() {
					setTimeout( function(){
						$container.hideLoading();
						$btn.button('reset');
					}, 10000 );
				}});
		$.post("${ctx}/sync_user",{userId:userId},function(ret){

			if(ret.success){
				$container.hideLoading();
				_reload();
				$btn.button('reset');
			}
		});
	}
</script>