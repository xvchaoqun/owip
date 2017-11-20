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
                                   data-load-el="#step-item-content" data-callback="_menuSelected"
                                   data-url='${ctx}/pcsVoteStat_group_stat_page?type=${type}'><i
                                        class="fa fa-bullhorn"></i> 小组计票
                                </a>
                            </li>
                            <li>
                                <a href="javascript:;" class="loadPage"
                                   data-load-el="#step-item-content" data-callback="_menuSelected"
                                   data-url='${ctx}/pcsVoteCandidate?type=${type}'><i
                                        class="fa fa-calendar-o"></i> 统计汇总
                                </a>
                            </li>
                            <li>
                                <a id="chooseAhref" href="javascript:;" class="loadPage"
                                   data-load-el="#step-item-content" data-callback="_menuSelected"
                                   data-url='${ctx}/pcsVoteMember?type=${type}'><i
                                        class="fa fa-tasks"></i>  当选${PCS_USER_TYPE_MAP.get(type)}
                                </a>
                            </li>
                        </ul>
                        <div class="col-xs-12" id="step-item-content">
                            <c:import url="${ctx}/pcsVoteStat_group_stat_page"/>
                        </div>
                        <div style="clear: both"></div>
                    </div>
                </div>
            </div>
        </div>
        <div id="item-content"></div>
    </div>
</div>
<script>
    function _menuSelected($aHref){

        var $nav = $aHref.closest(".nav");
        $("li", $nav).removeClass("active");
        $aHref.closest("li").addClass("active");
    }
</script>