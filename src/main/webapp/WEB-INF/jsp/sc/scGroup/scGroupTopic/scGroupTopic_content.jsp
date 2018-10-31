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
				<h4 class="smaller">
					议题内容
				</h4>
			</div>
			<div class="widget-body">
				<div class="widget-main" style="height:${not empty scGroupTopic.filePath?550:600}px;overflow-y: scroll">
					${scGroupTopic.content}
				</div>
				<c:if test="${not empty scGroupTopic.filePath}">
				<div class="well" style="margin-bottom: 0px;">
						附件：
						<c:forEach var="file" items="${fn:split(scGroupTopic.filePath,',')}" varStatus="vs">
							<a href="${ctx}/attach/download?path=${cm:encodeURI(file)}&filename=附件${vs.count}">附件${vs.count}</a>
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
				<h4 class="smaller">
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