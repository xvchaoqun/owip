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
                        <li>
                            <a href="javascript:;" class="loadPage"
                               data-load-el="#step-item-content" data-callback="$.menu.liSelected"
                               data-url='${ctx}/pcsPrFile?partyId=${param.partyId}'><i
                                    class="fa fa-th-list"></i> 材料准备情况
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;" class="loadPage"
                               data-load-el="#step-item-content" data-callback="$.menu.liSelected"
                               data-url='${ctx}/pcsPrVote?partyId=${param.partyId}'><i
                                    class="fa fa-hand-paper-o"></i> 选举情况
                            </a>
                        </li>
                        <li class="active">
                            <a href="javascript:;" class="loadPage"
                               data-load-el="#step-item-content" data-callback="$.menu.liSelected"
                               data-url='${ctx}/pcsPrList_page?partyId=${param.partyId}'><i
                                    class="fa fa-envelope-open"></i> 党代表名单
                            </a>
                        </li>
                        <li>
                            <a href="javascript:;" class="loadPage"
                               data-load-el="#step-item-content" data-callback="$.menu.liSelected"
                               data-url='${ctx}/pcsPrList_table_page?partyId=${param.partyId}'><i
                                    class="fa fa-line-chart"></i> 党代表数据统计
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4" id="step-item-content">
                    <c:import url="${ctx}/pcsPrList_page"/>
                </div>
            </div>
        </div>
    </div>
</div>