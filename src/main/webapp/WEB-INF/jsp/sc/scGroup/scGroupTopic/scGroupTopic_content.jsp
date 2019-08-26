<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h4 class="widget-title lighter smaller">
	<a href="javascript:;" class="hideView btn btn-xs btn-success">
		<i class="ace-icon fa fa-backward"></i>
		返回</a>
</h4>
<div class="row dispatch_au">
	<div class="preview" style="width: 620px">
		<div class="widget-box" style="height: 650px;">
			<div class="widget-header">
				<h4 class="widget-title">
					议题内容
				</h4>
			</div>
			<div class="widget-body">
				<div class="widget-main" style="height:${not empty scGroupTopic.filePath?550:600}px;overflow-y: scroll">

					<c:set var="_code" value="${cm:getMetaType(scGroupTopic.type).code}"/>
					<c:choose>
						<c:when test="${_code=='mt_sgt_motion'}">
							<div class="col"><h4 class="header"><i class="fa fa-hand-o-right"></i> 拟调整的岗位：</h4><p>${unitPost.code}-${unitPost.name}</p></div>
							<div class="col"><h4 class="header"><i class="fa fa-hand-o-right"></i> 确定选任方式：</h4><p>${cm:getMetaType(scGroupTopic.scType).name}</p></div>
							<div class="col"><h4 class="header"><i class="fa fa-hand-o-right"></i> 确定工作方案：</h4><p></div>
						</c:when>
						<c:when test="${_code=='mt_sgt_candidate'}">
							<div class="col"><h4 class="header"><i class="fa fa-hand-o-right"></i> 对应的干部选任纪实：</h4><p>${scRecord.code}-${scRecord.postName}-${scRecord.job}</p></div>
							<div class="col"><h4 class="header"><i class="fa fa-hand-o-right"></i> 确定选任方式：</h4><p>${cm:getMetaType(scGroupTopic.scType).name}</p></div>
							<div class="col"><h4 class="header"><i class="fa fa-hand-o-right"></i> 确定考察对象：</h4><p>
							<table class="table table-bordered table-condensed table-striped table-center table-unhover2">
								<thead>
								<tr>
									<td>工作证号</td>
									<td>姓名</td>
									<td>所在单位及职务</td>
								</tr>
								</thead>
								<tbody>
								<c:forEach items="${selectUsers}" var="u">
								<tr>
									<td>${u.code}</td>
									<td>${u.realname}</td>
									<td class="align-left">${u.title}</td>
								</tr>
								</c:forEach>
								</tbody>
							</table>
								</p>
							</div>
						</c:when>
						<c:when test="${_code=='mt_sgt_recommend'}">
							<div class="col"><h4 class="header"><i class="fa fa-hand-o-right"></i> 对应的干部选任纪实：</h4><p>${scRecord.code}-${scRecord.postName}-${scRecord.job}</p></div>
							<div class="col"><h4 class="header"><i class="fa fa-hand-o-right"></i> 拟调整的岗位：</h4><p>${unitPost.code}-${unitPost.name}</p></div>
							<div class="col"><h4 class="header"><i class="fa fa-hand-o-right"></i> 推荐拟任人选：</h4><p>${candidateUser.realname}</p></div>
						</c:when>
					</c:choose>
					${scGroupTopic.content}
				</div>
				<c:if test="${not empty scGroupTopic.filePath}">
				<div class="well" style="margin-bottom: 0px;">
						附件：
						<c:forEach var="file" items="${fn:split(scGroupTopic.filePath,',')}" varStatus="vs">
							<a href="${ctx}/attach_download?path=${cm:encodeURI(file)}&filename=附件${vs.count}">附件${vs.count}</a>
							${vs.last?"":"、"}
						</c:forEach>
				</div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="au" style="width: 620px">
		<div class="widget-box" style="height: 650px;">
			<div class="widget-header">
				<h4 class="widget-title">
					议题讨论备忘
				</h4>
			</div>
			<div class="widget-body">
				<div class="widget-main" style="height:600px;overflow-y: scroll">
					${scGroupTopic.memo}
				</div>
			</div>
		</div>
	</div>
</div>
<style>
	.col p{
		text-indent: 2em;
	}
	.col .header{
		 margin: 0 0 5px;
	}
</style>