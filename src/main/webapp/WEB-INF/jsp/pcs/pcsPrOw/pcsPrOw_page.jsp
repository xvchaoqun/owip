<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/pcsPrOw"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="candidate-table tab-content">
                    <div class="tab-pane in active rownumbers">
                        <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                            <li class="active">
                                <a href="javascript:;" class="loadPage"
                                   data-load-el="#step-item-content" data-callback="$.menu.liSelected"
                                   data-url='${ctx}/pcsPrOw_party_candidate_page?stage=${param.stage}'><i
                                        class="fa fa-bullhorn"></i> 全校党代表候选人${param.stage==PCS_STAGE_FIRST?'初步':'预备'}人选名单
                                </a>
                            </li>
                            <li>
                                <a href="javascript:;" class="loadPage"
                                   data-load-el="#step-item-content" data-callback="$.menu.liSelected"
                                   data-url='${ctx}/pcsPrOw_party_table_page?stage=${param.stage}'><i
                                        class="fa fa-calendar-o"></i> 全校党代表候选人${param.stage==PCS_STAGE_FIRST?'初步':'预备'}人选数据统计
                                </a>
                            </li>
                            <li>
                                <a href="javascript:;" class="loadPage"
                                   data-load-el="#step-item-content" data-callback="$.menu.liSelected"
                                   data-url='${ctx}/pcsPrOw_allocate_table_page?stage=${param.stage}'><i
                                        class="fa fa-tasks"></i> 全校党员参与推荐情况
                                </a>
                            </li>
                        </ul>
                        <div class="col-xs-12" id="step-item-content">
                        <c:import url="${ctx}/pcsPrOw_party_candidate_page"/>
                        </div>
                        <div style="clear: both"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="item-content"></div>
    </div>
</div>