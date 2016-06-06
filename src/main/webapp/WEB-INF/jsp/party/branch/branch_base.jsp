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
				<table class="table table-bordered table-striped">
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
							${branch.phone}
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
							${branch.email}
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

<div class="widget-box transparent">

	<div class="widget-header widget-header-flat">
		<h4 class="widget-title lighter">
			<i class="ace-icon fa fa-group"></i>
			支部委员会
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">

			<table class="table table-striped table-bordered table-hover">
				<thead>
				<tr>
					<th style="width: 200px">姓名</th>
					<th>类别</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${branchMembers}" var="branchMember" varStatus="st">
					<tr>
						<td ><c:if test="${branchMember.isAdmin}">
							<span class="label label-success arrowed-in arrowed-in-right">管理员</span>
						</c:if>${cm:getUserById(branchMember.userId).realname}</td>
						<td >
								${typeMap.get(branchMember.typeId).name}
						</td>
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
			支部管理员
		</h4>

		<div class="widget-toolbar">
			<a href="#" data-action="collapse">
				<i class="ace-icon fa fa-chevron-up"></i>
			</a>
		</div>
	</div>

	<div class="widget-body">
		<div class="widget-main no-padding">

			<table class="table table-striped table-bordered">
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
						<td >${user.realname}</td>
						<td >
							<a class="confirm btn btn-danger btn-xs"
							   data-url="${ctx}/branchAdmin_del?userId=${adminId}&branchId=${branch.id}"
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

		SysMsg.success('删除成功。', '成功',function(){
			$("#view-box .nav-tabs li.active a").click();
		});
	}
</script>