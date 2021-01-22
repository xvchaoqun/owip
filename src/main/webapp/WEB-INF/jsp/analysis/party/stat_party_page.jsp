<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row" id="cartogram">
    <div style="display: inline-block;position: relative;" id="selectParty">
        <c:if test="${(not empty parties && fn:length(parties)>1)||cm:isPermitted(PERMISSION_PARTYVIEWALL)}">
            ${_p_partyName}：
            <select data-rel="select2" name="party" data-width="350">
                <c:forEach items="${parties}" var="party">
                    <option value="${party.id}">${party.name}</option>
                </c:forEach>
            </select>
            <c:if test="${not empty checkParty}">
                <script type="text/javascript">
                    $("select[name=party]").val(${checkParty.id});
                </script>
            </c:if>
        </c:if>
    </div>
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
    <div class="row">
        <div class="col-xs-12">
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-line-chart"></i>
                        党员每月转入转出统计（近两年）
                    </h4>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>

                <div class="widget-body">
                    <div class="widget-main padding-4">
                        <c:import url="/stat_party_member_inout?partyId=${partyId}"/>
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