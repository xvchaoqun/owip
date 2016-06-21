<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="widget-box" style="width: 750px">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 党员出国（境）组织关系暂留申请</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table class="table table-bordered table-striped">
                <jsp:include page="graduateAbroad_table.jsp"/>
            </table>
        </div>
    </div>
</div>