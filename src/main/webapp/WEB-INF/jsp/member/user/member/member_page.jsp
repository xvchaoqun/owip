<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
	<div class="modal-body">

		<div class="widget-box transparent" id="view-box">
			<div class="widget-header">
				<h4 class="widget-title lighter smaller">
					&nbsp;
				</h4>

				<div class="widget-toolbar no-border">
					<ul class="nav nav-tabs">
						<li class="active">
							<a href="javascript:;" data-url="${ctx}/user/member">基本信息</a>
						</li>
						<li>
							<a href="javascript:;" data-url="${ctx}/user/memberOutflow">党员流出</a>
						</li>
						<li>
							<a href="javascript:;" data-url="${ctx}/user/memberOut">组织关系转出</a>
						</li>
						<li>
							<a href="javascript:;" data-url="${ctx}/user/memberTransfer">校内组织关系转接</a>
						</li>
						<li>
							<a href="javascript:;" data-url="${ctx}/user/memberStay">党员出国（境）申请组织关系暂留</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main padding-4">
					<div class="tab-content padding-8">
						<c:import url="/user/member"/>
					</div>
				</div><!-- /.widget-main -->
			</div><!-- /.widget-body -->
		</div><!-- /.widget-box -->
	</div>
<script>
	function _reload(){
		$("#view-box .nav-tabs li.active a").click();
	}
</script>
