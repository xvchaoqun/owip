<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_STAY_TYPE_MAP" value="<%=MemberConstants.MEMBER_STAY_TYPE_MAP%>"/>
<div class="widget-box" style="width: 950px">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> ${MEMBER_STAY_TYPE_MAP.get(type)}组织关系暂留申请</h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table class="table table-bordered table-striped">
                <jsp:include page="memberStay_table.jsp"/>
            </table>
        </div>
    </div>
</div>