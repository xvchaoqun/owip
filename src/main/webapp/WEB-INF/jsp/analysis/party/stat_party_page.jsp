<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row" id="cartogram">
    <shiro:hasPermission name="suspend:party">
        <c:import url="/suspend_party?partyId=${partyId}"/>
    </shiro:hasPermission>
    <div class="row">
        <div class="col-xs-12">
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-star green"></i>
                        党员基本信息统计
                    </h4>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-4">
                        <div class="row">
                            <c:import url="/stat_party_member_count?partyId=${partyId}"/>
                            <c:import url="/stat_party_member_age?partyId=${partyId}"/>
                            <c:import url="/stat_party_branch_type?partyId=${partyId}"/>
                            <c:import url="/stat_party_member_apply?partyId=${partyId}"/>
                        </div>
                    </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
            </div><!-- /.widget-box -->
        </div><!-- /.col -->
    </div>
    <div class="row">
        <div class="col-xs-12">
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-bar-chart"></i>
                        党员人数分布
                    </h4>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-4">
                        <c:import url="/stat_party_member_party?partyId=${partyId}"/>
                    </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
            </div><!-- /.widget-box -->
        </div><!-- /.col -->
    </div>
</div>
<script>
    $('[data-rel="select2"]').change(function () {
        $.post("${ctx}/stat_party_page", {partyId: this.value}, function (html) {
            $("#cartogram").replaceWith(html);
        });
    });
    $('[data-rel="select2"]').select2();
</script>