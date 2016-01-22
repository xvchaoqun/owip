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
			<i class="ace-icon fa fa-circle-o-notch fa-spin"></i>
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

			<table class="table table-striped table-bordered table-hover table-condensed">
				<thead>
				<tr>
					<th style="width: 200px">姓名</th>
					<th>类别</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${partyMembers}" var="partyMember" varStatus="st">
					<tr>
						<td ><c:if test="${partyMember.isAdmin}">
							<span class="label label-success arrowed-in arrowed-in-right">管理员</span>
						</c:if>${cm:getUserById(partyMember.userId).realname}</td>
						<td >
								${typeMap.get(partyMember.typeId).name}
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>

<style>
	#view-box .table-striped > tbody > tr:nth-of-type(odd) {
		background-color:inherit;
	}
	#view-box .table tbody tr:hover td, .table tbody tr:hover th {
		background-color:transparent;
	}
	#view-box .table-striped > tbody > tr > td:nth-of-type(odd) {
		background-color: #f9f9f9;
		text-align: right;
	}
	#view-box  .widget-main.no-padding .table{

		border: 1px solid #E5E5E5
	}
</style>