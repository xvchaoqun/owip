<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

        <div class="widget-box transparent">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:;" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                </h4>
                <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">${dpParty.name}</span>
                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs" data-target="#dp-party-content" id="flush-dpParty-base">
                        <li class="active">
                            <a href="javascript:;" data-url="${ctx}/dp/dpParty_base?id=${param.id}">基本信息</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/dp/dpPartyMemberGroup_view?partyId=${param.id}">党派委员会</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8" id="dp-party-content">
                        <c:import url="/dp/dpParty_base?${cm:encodeQueryString(pageContext.request.queryString)}"/>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->

<script>
    $("#jqGrid").setSelection('${param.id}', true);
</script>