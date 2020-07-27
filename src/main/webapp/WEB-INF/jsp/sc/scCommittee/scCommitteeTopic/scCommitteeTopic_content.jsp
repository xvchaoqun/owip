<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
           ${scCommitteeTopic.name}（${scCommittee.code}）
        </span>
    </div>
    <div class="widget-body">
        <div class="row dispatch_au">
            <div class="preview" style="width: 620px">
                <div class="widget-box" style="height: 650px;">
                    <div class="widget-header">
                        <h4 class="widget-title">
                            议题内容
                        </h4>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main" style="height:600px;overflow-y: scroll">
                            ${cm:htmlUnescape(scCommitteeTopic.content)}
                        </div>

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
                            ${cm:htmlUnescape(scCommitteeTopic.memo)}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>