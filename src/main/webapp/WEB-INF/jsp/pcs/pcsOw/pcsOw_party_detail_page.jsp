<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box transparent">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                    <span style="padding-left: 20px;">${party.name}</span>
                </h4>

                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" class="loadPage"
                               data-load-el="#step-item-content" data-callback="_menuSelected"
                               data-url='${ctx}/pcsPrOw_party_branch_page?partyId=${param.partyId}&stage=${param.stage}'><i
                                    class="fa fa-calendar-o"></i> 各支部推荐情况
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;" class="loadPage"
                               data-load-el="#step-item-content" data-callback="_menuSelected"
                               data-url='${ctx}/pcsOw_party_candidate_page?partyId=${param.partyId}&stage=${param.stage}'><i
                                    class="fa fa-calendar-o"></i> 分党委推荐情况汇总
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4" id="step-item-content">
                    <c:import url="${ctx}/pcsPrOw_party_branch_page?partyId=${param.partyId}stage=${param.stage}"/>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function _menuSelected($aHref){

        var $nav = $aHref.closest(".nav");
        $("li", $nav).removeClass("active");
        $aHref.closest("li").addClass("active");
    }
</script>