<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <!-- PAGE CONTENT BEGINS -->
            <div class="widget-box transparent">
                <div class="widget-header">
                    <div class="widget-toolbar no-border">
                        <jsp:include page="/WEB-INF/jsp/party/memberApply/menu.jsp"/>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-12 no-padding-left no-padding-right">
                        <div class="tab-content padding-4">
                            <div class="tab-pane in active">
                                <div class="widget-box">
                                    <div class="widget-header">
                                        <h4 class="widget-title"><i class="ace-icon fa fa-download red "></i> 申请入党人员信息导出</h4>
                                        <div class="widget-toolbar">
                                            <a href="#" data-action="collapse">
                                                <i class="ace-icon fa fa-chevron-down"></i>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <form class="form-inline search-form" id="exportForm1">

                                                <div class="form-group">
                                                    <label>分党委</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/party_selects?auth=1" data-width="350"
                                                            name="partyId" data-placeholder="请选择分党委">
                                                        <option value="${party.id}">${party.name}</option>
                                                    </select>

                                                </div>

                                                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv1">
                                                    <label >党支部</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/branch_selects?auth=1"
                                                            name="branchId" data-placeholder="请选择党支部">
                                                        <option value="${branch.id}">${branch.name}</option>
                                                    </select>
                                                </div>
                                                <script>
                                                    register_party_branch_select($("#exportForm1"), "branchDiv1",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                </script>
                                                <div class="text-danger bolder">
                                                    注：不选分党委、党支部则导出全部
                                                </div>
                                            </form>
                                                <div class="space-4"></div>
                                                <div class="clearfix form-actions center">
                                                    <button class="btn btn-primary btn-sm" onclick="_exportApply1('${APPLY_TYPE_STU}')">
                                                        <i class="fa fa-download"></i> 导出学生</button>

                                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                                        <button type="button" class="btn btn-warning btn-sm" onclick="_exportApply1('${APPLY_TYPE_TEACHER}')">
                                                            <i class="fa fa-download"></i> 导出教职工
                                                        </button>

                                                </div>

                                        </div>
                                    </div>
                                </div>
                                <div class="widget-box">
                                    <div class="widget-header">
                                        <h4 class="widget-title"><i class="ace-icon fa fa-download red "></i> 发展党员信息导出</h4>
                                        <div class="widget-toolbar">
                                            <a href="#" data-action="collapse">
                                                <i class="ace-icon fa fa-chevron-down"></i>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <form class="form-inline search-form" id="exportForm2">

                                                <div class="form-group">
                                                    <label>分党委</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/party_selects?auth=1" data-width="350"
                                                            name="partyId" data-placeholder="请选择分党委">
                                                        <option value="${party.id}">${party.name}</option>
                                                    </select>

                                                </div>

                                                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv2">
                                                    <label >党支部</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/branch_selects?auth=1"
                                                            name="branchId" data-placeholder="请选择党支部">
                                                        <option value="${branch.id}">${branch.name}</option>
                                                    </select>
                                                </div>
                                                <script>
                                                    register_party_branch_select($("#exportForm2"), "branchDiv2",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                </script>
                                                <div class="text-danger bolder">
                                                    注：不选分党委、党支部则导出全部
                                                </div>
                                            </form>
                                                <div class="clearfix form-actions center">
                                                    <button class="btn btn-primary btn-sm"  onclick="_exportApply2('${APPLY_TYPE_STU}')">
                                                        <i class="fa fa-download"></i> 导出学生</button>

                                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                                    <button type="button" class="btn btn-warning btn-sm"  onclick="_exportApply2('${APPLY_TYPE_TEACHER}')">
                                                        <i class="fa fa-download"></i> 导出教职工
                                                    </button>

                                                </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<script>
    function _exportApply1(type){
        var partyId = $("select[name=partyId]", "#exportForm1").val();
        var branchId = $("select[name=branchId]", "#exportForm1").val();
        location.href="${ctx}/memberApplyExport?exportType=1&type={0}&partyId={1}&branchId={2}&t={3}"
                .format(type, partyId, branchId, new Date().getTime);
    }

    function _exportApply2(type){

        var partyId = $("select[name=partyId]", "#exportForm2").val();
        var branchId = $("select[name=branchId]", "#exportForm2").val();
        location.href="${ctx}/memberApplyExport?exportType=2&type={0}&partyId={1}&branchId={2}&t={3}"
                .format(type, partyId, branchId, new Date().getTime);
    }
</script>