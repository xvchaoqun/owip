<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>

<div class="widget-box" style="width: 750px">
    <div class="widget-header">
        <h4 class="smaller">
            <div class="pull-right" style="margin-right: 10px">
                <button type="button"
                        data-width="750" data-url="${ctx}/crsPost_requirement?id=${param.id}"
                        class="popupBtn btn btn-xs btn-success">
                    <i class="ace-icon fa fa-edit"></i>
                    编辑
                </button>
            </div>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            ${crsPost.requirement}
        </div>
    </div>
</div>