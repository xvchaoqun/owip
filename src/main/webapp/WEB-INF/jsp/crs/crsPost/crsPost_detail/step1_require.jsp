<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<div class="widget-box" style="width: 750px">
    <div class="widget-header">
        <h4 class="smaller">
            任职资格
            <div class="pull-right" style="margin-right: 10px">
                <button type="button"
                        data-width="750" data-url="${ctx}/crsPost_qualification?id=${param.id}"
                        class="popupBtn btn btn-xs btn-success">
                    <i class="ace-icon fa fa-edit"></i>
                    编辑
                </button>
            </div>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            ${crsPost.qualification}
        </div>
    </div>
</div>

<div class="widget-box" style="width: 750px">
    <div class="widget-header">
        <h4 class="smaller">
            岗位要求
            <div class="pull-right" style="margin-right: 10px">
                <button type="button"
                        data-width="750" data-url="${ctx}/crsPost_detail/step1_require_au?id=${param.id}"
                        class="popupBtn btn btn-xs btn-success">
                    <i class="ace-icon fa fa-edit"></i>
                    编辑
                </button>
            </div>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <div style="font-size: 16px; font-weight: bolder">${crsPostRequire.name}：</div>
            <jsp:include page="/WEB-INF/jsp/crs/crsPostRequire/postRequire.jsp"/>
        </div>
    </div>
</div>
