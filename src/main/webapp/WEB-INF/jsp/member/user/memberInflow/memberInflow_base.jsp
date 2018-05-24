<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
	<div class="modal-body">

		<div class="widget-box transparent">
			<div class="widget-header">
				<h4 class="widget-title lighter smaller">
					&nbsp;
				</h4>

				<div class="widget-toolbar no-border">
					<ul class="nav nav-tabs">
						<li class="active">
							<a href="javascript:;">流入党员信息</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main padding-4">
					<div class="tab-content padding-8" id="body-content-view">
						<table class="table table-bordered table-striped">
							<tbody>
							<tr>
								<td class="bg-right">
									姓名
								</td>
								<td class="bg-left" style="min-width: 80px">
									${_user.realname}
								</td>
								<td class="bg-right">
									所属组织机构
								</td>
								<td  class="bg-left" style="min-width: 80px">
										${cm:displayParty(memberInflow.partyId, memberInflow.branchId)}
								</td>
								<td class="bg-right">
									原职业
								</td>
								<td class="bg-left" style="min-width: 80px">
									${cm:getMetaType(memberInflow.originalJob).name}
								</td>
								<td class="bg-right">
									流入前所在省份
								</td>
								<td class="bg-left" style="min-width: 120px">
									${locationMap.get(memberInflow.province).name}
								</td>
							</tr>
							<tr>
								<td class="bg-right">流入原因</td>
								<td class="bg-left">
									${memberInflow.reason}
								</td>
								<td class="bg-right">
									流入时间
								</td>
								<td class="bg-left">${cm:formatDate(memberInflow.flowTime,'yyyy-MM-dd')}
								</td>
								<td class="bg-right">
									入党时间
								</td>
								<td class="bg-left">
									${cm:formatDate(memberInflow.growTime,'yyyy-MM-dd')}
								</td>
								<td class="bg-right">
									组织关系所在地
								</td>
								<td class="bg-left">
									${memberInflow.orLocation}
								</td>
							</tr>
							<tr>
								<td class="bg-right" colspan="3">
									是否持有《中国共产党流动党员活动证》
								</td>
								<td class="bg-left" colspan="5">
									${memberInflow.hasPapers?"是":"否"}
								</td>
							</tr>
							</tbody>
						</table>
					</div>
				</div><!-- /.widget-main -->
			</div><!-- /.widget-body -->
		</div><!-- /.widget-box -->
	</div>
