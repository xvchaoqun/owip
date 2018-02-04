<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_cadres">
	<div class="dispatch">
		<div class="widget-box">
			<div class="widget-header">
				<h4 class="smaller">
					常委会文件预览
					<div class="buttons pull-right ">
						<a href="javascript:;" class="hideView btn btn-xs btn-success"
						   style="margin-right: 10px; top: -5px;">
							<i class="ace-icon fa fa-backward"></i>
							返回</a>
					</div>
				</h4>
			</div>
			<div class="widget-body">
				<div class="widget-main">
					<div id="dispatch-file-view">
						<c:import url="${ctx}/swf/preview?type=html&path=${scCommittee.filePath}"/>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="cadres">
		<div id="dispatch-cadres-view">
			<c:import url="${ctx}/sc/scCommitteeVote_au_form?topicId=${scCommitteeTopic.id}"/>
		</div>
	</div>
</div>
<script>

</script>