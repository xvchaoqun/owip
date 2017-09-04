<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box transparent" id="view-box">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>

                    <span style="margin-left: 20px">${party.name}</span>
                </h4>

                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;"
                               data-url="${ctx}/pcsPrOw_party_candidate_page?stage=${param.stage}&partyId=${param.partyId}">党代表候选人${param.stage==PCS_STAGE_FIRST?'初步':'预备'}人选名单</a>
                        </li>
                        <li>
                            <a href="javascript:;"
                               data-url="${ctx}/pcsPrOw_party_table_page?stage=${param.stage}&partyId=${param.partyId}">党代表候选人${param.stage==PCS_STAGE_FIRST?'初步':'预备'}人选数据统计</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8">
                     <c:import url="/pcsPrOw_party_candidate_page"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>