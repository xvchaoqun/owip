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
							<t:mask src="${party.phone}" type="fixedPhone"/>
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
							<t:mask src="${party.email}" type="email"/>
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
			<c:if test="${empty presentGroup}">
				<span style="color: red;font-size: 12px">【注：该党委还未设置现任班子，只有设置了现任班子之后，添加的委员和管理员才会生效】</span>
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

			<table class="table table-unhover2 table-center table-striped table-bordered">
				<thead>
				<tr>
					<th width="150">姓名</th>
					<th width="150">工作证号</th>
					<th width="150">职务</th>
					<th width="150">分工</th>
					<th width="100">任职时间</th>
					<th width="200">办公电话</th>
					<th width="100">手机号</th>
					<th></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${partyMembers}" var="partyMember" varStatus="st">
					<c:set var="user" value="${cm:getUserById(partyMember.userId)}"/>
					<tr>
						<td ><c:if test="${partyMember.isAdmin}">
							<span class="label label-success arrowed-in arrowed-in-right">管理员</span>
						</c:if>${user.realname}</td>
						<td>${user.code}</td>
						<td>
								${cm:getMetaType(partyMember.postId).name}
						</td>
						<td>
							<c:forEach items="${fn:split(partyMember.typeIds, ',')}" var="typeId" varStatus="vs">
								${cm:getMetaType(typeId).name}
								${vs.last?"":"、"}
							</c:forEach>
						</td>
						<td>${cm:formatDate(partyMember.assignDate, "yyyy.MM")}</td>
						<td><t:mask src="${partyMember.officePhone}" type="fixedPhone"/></td>
						<td><t:mask src="${partyMember.mobile}" type="mobile"/></td>
						<td></td>
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
			${_p_partyName}管理员
		</h4>

		<div class="widget-toolbar">
			<a href="javascript:;" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">
			<table class="table table-unhover2 table-center table-striped table-bordered">
				<thead>
				<tr>
					<th width="100">姓名</th>
					<th width="150">学工号</th>
					<th width="150">联系方式</th>
					<th></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${adminIds}" var="adminId" varStatus="st">
					<c:set var="user" value="${cm:getUserById(adminId)}"/>
					<tr>
						<td>${user.realname}</td>
						<td>${user.code}</td>
						<td><t:mask src="${user.mobile}" type="mobile"/></td>
						<td class="bg-left">
							<shiro:hasPermission name="partyMember:del">
							<a class="confirm btn btn-danger btn-xs"
							   data-url="${ctx}/partyAdmin_del?userId=${adminId}&partyId=${party.id}"
							   data-msg="确定删除该管理员？"
							   data-callback="_delAdminCallback">删除</a>
							</shiro:hasPermission>
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

		$("ul[data-target=\"#party-content\"] li.active a").click();
	}
</script>