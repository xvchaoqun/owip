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
							名称
						</td>
						<td style="min-width: 80px">
							${branch.name}
						</td>
						<td>
							简称
						</td>
						<td  style="min-width: 80px">
							${branch.shortName}
						</td>
						<td>编号</td>
						<td >
							${branch.code}
						</td>
						<td>
							网址
						</td>
						<td style="min-width: 80px">

						</td>
					</tr>
					<tr>
						
						<td>
							联系电话
						</td>
						<td>
							<t:mask src="${branch.phone}" type="fixedPhone"/>
						</td>
						<td >
							传真
						</td>
						<td>
							${branch.fax}
						</td>
						<td >
							邮箱
						</td>
						<td>
							<t:mask src="${branch.email}" type="email"/>
						</td>
						<td >
							成立时间
						</td>
						<td>
							${cm:formatDate(branch.foundTime,'yyyy-MM-dd')}
						</td>
					</tr>
					</tbody>
				</table>


</div></div></div>

<c:if test="${cm:isPresentBranchAdmin(_user.id, branch.partyId, branch.id)}">
<div class="widget-box transparent">

	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-group"></i>
			支部委员会
			<c:if test="${empty presentGroup}">
				<span style="color: red;font-size: 12px">【注：该支部还未设置现任支部委员会，只有设置了现任支委会之后，添加的委员和管理员才会生效】</span>
            </c:if>
		</h4>

		<div class="widget-toolbar">
			<a href="javascript:;" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">

			<table class="table table-unhover table-striped table-bordered table-center">
				<thead>
				<tr>
					<th style="width: 200px">姓名</th>
					<th width="150">学工号</th>
					<th style="text-align: left">类别</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${branchMembers}" var="branchMember" varStatus="st">
					<c:set var="user" value="${cm:getUserById(branchMember.userId)}"/>
					<tr>
						<td class="bg-right"><c:if test="${branchMember.isAdmin}">
							<span class="label label-success arrowed-in arrowed-in-right">管理员</span>
						</c:if>${user.realname}</td>
						<td class="bg-center">${user.code}</td>
						<td class="bg-left">
								${typeMap.get(branchMember.typeId).name}
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>
	<c:set var="isPartyAdmin" value="${cm:hasPartyAuth(_user.id, branch.partyId)}"/>
<div class="widget-box transparent">
	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-key"></i>
			支部管理员
		</h4>

		<div class="widget-toolbar">
			<a href="javascript:;" data-action="collapse">
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
						<td>
							<c:if test="${isPartyAdmin}">
							<a class="confirm btn btn-danger btn-xs"
							   data-url="${ctx}/branchAdmin_del?userId=${adminId}&branchId=${branch.id}"
							   data-msg="确定删除该管理员？"
							   data-callback="_delAdminCallback">删除</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
</c:if>
<script>
	function _delAdminCallback(target){

		$("ul[data-target='#branch-content'] li.active a").click();
	}
</script>