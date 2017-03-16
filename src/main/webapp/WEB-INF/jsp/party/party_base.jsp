<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-info-circle"></i>
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

						<td>
							名称
						</td>
						<td style="min-width: 80px">
							${party.name}
						</td>
						<td>
							简称
						</td>
						<td  style="min-width: 80px">
							${party.shortName}
						</td>
						<td>编号</td>
						<td >
							${party.code}
						</td>
						<td>
							网址
						</td>
						<td style="min-width: 80px">
							<c:if test="${not empty party.url}">
								<a href="${party.url}" target="_blank">${party.url}</a>
							</c:if>
						</td>
					</tr>
					<tr>
						
						<td>
							联系电话
						</td>
						<td>
							${party.phone}
						</td>
						<td >
							传真
						</td>
						<td>
							${party.fax}
						</td>
						<td >
							邮箱
						</td>
						<td>
							${party.email}
						</td>
						<td >
							成立时间
						</td>
						<td>
							${cm:formatDate(party.foundTime,'yyyy-MM-dd')}
						</td>
					</tr>
					</tbody>
				</table>


</div></div></div>

<div class="widget-box transparent">

	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-group"></i>
			领导班子
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">

			<table class="table table-unhover table-striped table-bordered">
				<thead>
				<tr>
					<th style="width: 200px">姓名</th>
					<th width="100">职务</th>
					<th width="100">分工</th>
					<th width="100">任职时间</th>
					<th width="100">办公电话</th>
					<th>手机号</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${partyMembers}" var="partyMember" varStatus="st">
					<tr>
						<td ><c:if test="${partyMember.isAdmin}">
							<span class="label label-success arrowed-in arrowed-in-right">管理员</span>
						</c:if>${cm:getUserById(partyMember.userId).realname}</td>
						<td class="bg-left">
								${partyMemberPostMap.get(partyMember.postId).name}
						</td>
						<td class="bg-left">
							<c:forEach items="${fn:split(type_ids, ',')}" var="typeId">
								${partyMemberTypeMap.get(typeId).name}
							</c:forEach>
						</td>
						<td class="bg-left">${cm:formatDate(partyMember.assignDate, "yyyy.MM")}</td>
						<td class="bg-left">${partyMember.officePhone}</td>
						<td class="bg-left">${partyMember.mobile}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>
<div class="widget-box transparent">

	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-key"></i>
			分党委管理员
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">

			<table class="table table-unhover table-striped table-bordered">
				<thead>
				<tr>
					<th style="width: 200px">姓名</th>
					<th></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${adminIds}" var="adminId" varStatus="st">
					<c:set var="user" value="${cm:getUserById(adminId)}"/>
					<tr>
						<td >${user.realname}（${user.code}）</td>
						<td >
							<a class="confirm btn btn-danger btn-xs"
							   data-url="${ctx}/partyAdmin_del?userId=${adminId}&partyId=${party.id}"
							   data-msg="确定删除该管理员？"
							   data-callback="_delAdminCallback">删除</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script>
	function _delAdminCallback(target){

		//SysMsg.success('删除成功。', '成功',function(){
			$("#view-box .nav-tabs li.active a").click();
		//});
	}
</script>