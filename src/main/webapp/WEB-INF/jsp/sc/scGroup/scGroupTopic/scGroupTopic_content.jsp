<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h4 class="widget-title lighter smaller">
	<a href="javascript:;" class="hideView btn btn-xs btn-success">
		<i class="ace-icon fa fa-backward"></i>
		返回</a>
</h4>
<div class="row dispatch_au">
	<div class="preview">
		<div class="widget-box">
			<div class="widget-header">
				<h4 class="smaller">
					议题内容
				</h4>
			</div>
			<div class="widget-body">
				<div class="widget-main">
					${scGroupTopic.content}
				</div>
			</div>
		</div>
	</div>
	<div class="au">
		<div class="widget-box">
			<div class="widget-header">
				<h4 class="smaller">
					议题讨论备忘
				</h4>
			</div>
			<div class="widget-body">
				<div class="widget-main">
					${scGroupTopic.memo}
				</div>
			</div>
		</div>
	</div>
</div>