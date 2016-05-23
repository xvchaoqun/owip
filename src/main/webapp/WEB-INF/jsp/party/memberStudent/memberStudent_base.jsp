<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="GENDER_MAP" value="<%=SystemConstants.GENDER_MAP%>"/>
<c:set var="MEMBER_SOURCE_MAP" value="<%=SystemConstants.MEMBER_SOURCE_MAP%>"/>
<c:set var="USER_SOURCE_MAP" value="<%=SystemConstants.USER_SOURCE_MAP%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<c:set var="MEMBER_STATUS_MAP" value="<%=SystemConstants.MEMBER_STATUS_MAP%>"/>
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
					<img src="${ctx}/avatar/${cm:getUserById(param.userId).code}"  class="avatar">
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
					${GENDER_MAP.get(memberStudent.gender)}
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
				<td class="bg-right">学生证号</td>
				<td class="bg-left">
					${memberStudent.code}
				</td>
				<td class="bg-right">
					籍贯
				</td>
				<td class="bg-left">
					${memberStudent.nativePlace}
				</td>
				<td  class="bg-right">
					来源
				</td>
				<td class="bg-left">
					${MEMBER_SOURCE_MAP.get(memberStudent.source)}
				</td>
				<td class="bg-right">
					同步来源
				</td>
				<td class="bg-left">
					${USER_SOURCE_MAP.get(memberStudent.syncSource)}
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
					${memberStudent.isFullTime}
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
				<td  class="bg-left">${cm:formatDate(memberStudent.actualEnrolTime,'yyyy-MM')}
				</td>
				<td class="bg-right">
					预计毕业年月
				</td>
				<td class="bg-left">${cm:formatDate(memberStudent.expectGraduateTime,'yyyy-MM')}
				</td>
				<td  class="bg-right">
					实际毕业年月
				</td>
				<td class="bg-left">${cm:formatDate(memberStudent.actualGraduateTime,'yyyy-MM')}
				</td>
				<td class="bg-right">
					延期毕业年限
				</td>
				<td class="bg-left">
					${memberStudent.delayYear}
				</td>
			</tr>
			<tr>
				<td class="bg-right" colspan="2">学籍状态</td>
				<td  class="bg-left" colspan="7">${memberStudent.xjStatus}
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
		<td class="bg-right">
			所属组织机构
		</td>

		<td class="bg-left" colspan="5">
			${partyMap.get(memberStudent.partyId).name}
			<c:if test="${not empty memberStudent.branchId}">
				-${branchMap.get(memberStudent.branchId).name}
			</c:if>
		</td>

	</tr>
	<tr>
		<td class="bg-right">政治面貌</td>
		<td class="bg-left">
			${MEMBER_POLITICAL_STATUS_MAP.get(memberStudent.politicalStatus)}
		</td>
		<td class="bg-right">状态</td>
		<td class="bg-left">
			${MEMBER_STATUS_MAP.get(memberStudent.status)}
		</td>
		<td class="bg-right">
			党内职务
		</td>
		<td class="bg-left">
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
			${MEMBER_SOURCE_MAP.get(memberStudent.source)}
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
</table></div></div></div>

<div class="clearfix form-actions center">
		<button class="btn btn-info  btn-pink" onclick="member_sync(${param.userId})" type="button">
			<i class="ace-icon fa fa-refresh "></i>
			同步数据
		</button>

		&nbsp; &nbsp; &nbsp;
		<button class="closeView btn" type="button">
			<i class="ace-icon fa fa-undo"></i>
			返回
		</button>
</div>

<script>
	function _reload(){
		$("#item-content #view-box .nav-tabs li.active a").click();
	}

	function member_sync(userId){
		var $container = $("#view-box");
		$container.showLoading({'afterShow':
				function() {
					setTimeout( function(){
						$container.hideLoading();
					}, 2000 );
				}})
		$.post("${ctx}/member_sync",{userId:userId},function(ret){

			if(ret.success){
				$container.hideLoading();
				_reload();
			}
		});
	}
</script>