<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/pmd/pmdMonth/menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        开发中...
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view">
        </div>
    </div>
</div>