<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.unitId ||not empty param.postId || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <shiro:hasPermission name="unitPostAllocation:module2">
                    <jsp:include page="unitPostAllocation_menu.jsp"/>
                </shiro:hasPermission>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="space-4"></div>
                        <c:if test="${module==1}">
                        <div class="buttons" style="position: absolute; top:35px;">
                            <shiro:hasPermission name="cadre:export">
                            <a href="javascript:;" class="downloadBtn btn btn-success btn-sm"
                               data-url="${ctx}/unitPostAllocation?module=1&export=1&cadreType=${cadreType}"><i class="fa fa-download"></i> 导出</a>
                            </shiro:hasPermission>
                        </div>
                        <jsp:include page="unitPostAllocation_table.jsp"/>
                            <script>
                                $('[data-tooltip="tooltip"]').tooltip({html: true});
                            </script>
                        </c:if>
                        <c:if test="${module==2}">
                            <div class="buttons" style="position: absolute; top:35px;">
                                <shiro:hasPermission name="cadre:export">
                                <a href="javascript:;" class="downloadBtn btn btn-success btn-sm"
                                   data-url="${ctx}/unitPostAllocation?module=2&export=1&cadreType=${cadreType}"><i class="fa fa-download"></i> 导出</a>
                                </shiro:hasPermission>
                            </div>
                            <jsp:include page="unitPostAllocation_stat_table.jsp"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>