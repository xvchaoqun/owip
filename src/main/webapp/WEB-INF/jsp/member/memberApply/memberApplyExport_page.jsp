<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/member/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="widget-box transparent">
                <div class="widget-header">
                        <jsp:include page="/WEB-INF/jsp/member/memberApply/menu.jsp"/>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-12 no-padding-left no-padding-right" style="padding-top: 5px;">
                        <div class="tab-content padding-4">
                            <div class="tab-pane in active">
                                <div class="widget-box">
                                    <div class="widget-header">
                                        <h4 class="widget-title"><i class="ace-icon fa fa-download red "></i> 申请入党人员信息导出</h4>
                                        <div class="widget-toolbar">
                                            <a href="javascript:;" data-action="collapse">
                                                <i class="ace-icon fa fa-chevron-down"></i>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <form class="form-inline search-form" id="exportForm1">
                                                <div class="form-group">
                                                    <label>提交书面申请书时间</label>
                                                    <div class="input-group tooltip-success" data-rel="tooltip" title="时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                        <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                               type="text" name="_applyTime" value="${param._applyTime}"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>确定为入党积极分子时间</label>
                                                    <div class="input-group tooltip-success" data-rel="tooltip" title="时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                        <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                               type="text" name="_activeTime" value="${param._activeTime}"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>确定为发展对象时间</label>
                                                    <div class="input-group tooltip-success" data-rel="tooltip" title="时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                        <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                               type="text" name="_candidateTime" value="${param._candidateTime}"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>${_p_partyName}</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/party_selects?auth=1" data-width="350"
                                                            name="partyId" data-placeholder="请选择">
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
                                                    $.register.party_branch_select($("#exportForm1"), "branchDiv1",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                </script>
                                                <div class="text-danger bolder">
                                                    注：
                                                    <ul>
                                                        <li>不选${_p_partyName}、党支部则导出全部</li>
                                                        <li>
                                                            <c:if test="${_ignore_plan_and_draw}">
                                                                导出结果中包含申请（支部审核通过）至发展对象这三个阶段的数据
                                                            </c:if>
                                                            <c:if test="${!_ignore_plan_and_draw}">
                                                                导出结果中包含申请（支部审核通过）至领取志愿书这五个阶段的数据
                                                            </c:if>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </form>
                                                <div class="space-4"></div>
                                                <div class="clearfix form-actions center" style="margin-top: 0;margin-bottom: 0;">
                                                    <button class="btn btn-primary btn-sm" onclick="_exportApply1(this, '${MEMBER_TYPE_STUDENT}')">
                                                        <i class="fa fa-download"></i> 导出学生</button>

                                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                                        <button type="button" class="btn btn-warning btn-sm" onclick="_exportApply1(this, '${MEMBER_TYPE_TEACHER}')">
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
                                            <a href="javascript:;" data-action="collapse">
                                                <i class="ace-icon fa fa-chevron-down"></i>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <form class="form-inline search-form" id="exportForm2">
                                                <div class="form-group">
                                                    <label>发展时间</label>
                                                    <div class="input-group tooltip-success" data-rel="tooltip" title="时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                        <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                               type="text" name="_growTime" value="${param._growTime}"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>${_p_partyName}</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/party_selects?auth=1" data-width="350"
                                                            name="partyId" data-placeholder="请选择">
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
                                                    $.register.party_branch_select($("#exportForm2"), "branchDiv2",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                </script>
                                                <div class="text-danger bolder">
                                                    注：
                                                    <ul>
                                                        <li>不选${_p_partyName}、党支部则导出全部</li>
                                                        <li>导出结果中包含预备党员这个阶段的数据</li>
                                                    </ul>
                                                </div>

                                            </form>
                                                <div class="clearfix form-actions center" style="margin-top: 0;margin-bottom: 0;">
                                                    <button class="btn btn-primary btn-sm"  onclick="_exportApply2(this,'${MEMBER_TYPE_STUDENT}')">
                                                        <i class="fa fa-download"></i> 导出学生</button>

                                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                                    <button type="button" class="btn btn-warning btn-sm"  onclick="_exportApply2(this,'${MEMBER_TYPE_TEACHER}')">
                                                        <i class="fa fa-download"></i> 导出教职工
                                                    </button>

                                                </div>

                                        </div>
                                    </div>
                                </div>
                                 <c:if test="${!_ignore_plan_and_draw}">
                                <div class="widget-box">
                                    <div class="widget-header">
                                        <h4 class="widget-title"><i class="ace-icon fa fa-download red "></i>
                                                领取志愿书信息导出
                                        </h4>
                                        <div class="widget-toolbar">
                                            <a href="javascript:;" data-action="collapse">
                                                <i class="ace-icon fa fa-chevron-down"></i>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <form class="form-inline search-form" id="exportForm3">
                                                <c:if test="${_ignore_plan_and_draw}">
                                                    <div class="form-group">
                                                        <label>发展为预备党员时间</label>
                                                        <div class="input-group tooltip-success" data-rel="tooltip" title="时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                            <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                                   type="text" name="_growTime" value="${param._growTime}"/>
                                                        </div>
                                                    </div>
                                                </c:if>

                                                <div class="form-group">
                                                    <label>领取志愿书时间</label>
                                                    <div class="input-group tooltip-success" data-rel="tooltip" title="时间范围">
                                                        <span class="input-group-addon">
                                                            <i class="fa fa-calendar bigger-110"></i>
                                                        </span>
                                                        <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                               type="text" name="_drawTime" value="${param._drawTime}"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>${_p_partyName}</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/party_selects?auth=1" data-width="350"
                                                            name="partyId" data-placeholder="请选择">
                                                        <option value="${party.id}">${party.name}</option>
                                                    </select>

                                                </div>

                                                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv3">
                                                    <label >党支部</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/branch_selects?auth=1"
                                                            name="branchId" data-placeholder="请选择党支部">
                                                        <option value="${branch.id}">${branch.name}</option>
                                                    </select>
                                                </div>
                                                <script>
                                                    $.register.party_branch_select($("#exportForm3"), "branchDiv3",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                </script>
                                                <div class="text-danger bolder">
                                                    注：
                                                    <ul>
                                                        <li>不选${_p_partyName}、党支部则导出全部</li>
                                                        <li>
                                                            <c:if test="${_ignore_plan_and_draw}">
                                                                导出结果中包含预备党员、正式党员这两个阶段的数据
                                                            </c:if>
                                                            <c:if test="${!_ignore_plan_and_draw}">
                                                                导出结果中包含领取志愿书、预备党员、正式党员这三个阶段的数据
                                                            </c:if>

                                                        </li>
                                                    </ul>
                                                </div>

                                            </form>
                                                <div class="clearfix form-actions center" style="margin-top: 0;margin-bottom: 0;">
                                                    <button class="btn btn-primary btn-sm"  onclick="_exportApply3(this,'${MEMBER_TYPE_STUDENT}')">
                                                        <i class="fa fa-download"></i> 导出学生</button>

                                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                                    <button type="button" class="btn btn-warning btn-sm"  onclick="_exportApply3(this,'${MEMBER_TYPE_TEACHER}')">
                                                        <i class="fa fa-download"></i> 导出教职工
                                                    </button>

                                                </div>

                                        </div>
                                    </div>
                                </div>
                                 </c:if>

                                <div class="widget-box">
                                    <div class="widget-header">
                                        <h4 class="widget-title"><i class="ace-icon fa fa-download red "></i> 预备党员转正信息导出</h4>
                                        <div class="widget-toolbar">
                                            <a href="javascript:;" data-action="collapse">
                                                <i class="ace-icon fa fa-chevron-down"></i>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-main">
                                            <form class="form-inline search-form" id="exportForm4">
                                                <div class="form-group">
                                                    <label>转正时间</label>
                                                    <div class="input-group tooltip-success" data-rel="tooltip" title="时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                        <input placeholder="请选择时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                                               type="text" name="_positiveTime" value="${param._positiveTime}"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>${_p_partyName}</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/party_selects?auth=1" data-width="350"
                                                            name="partyId" data-placeholder="请选择">
                                                        <option value="${party.id}">${party.name}</option>
                                                    </select>

                                                </div>

                                                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv4">
                                                    <label >党支部</label>
                                                    <select class="form-control" data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/branch_selects?auth=1"
                                                            name="branchId" data-placeholder="请选择党支部">
                                                        <option value="${branch.id}">${branch.name}</option>
                                                    </select>
                                                </div>
                                                <script>
                                                    $.register.party_branch_select($("#exportForm4"), "branchDiv4",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                </script>
                                                <div class="text-danger bolder">
                                                    注：
                                                    <ul>
                                                        <li>不选${_p_partyName}、党支部则导出全部</li>
                                                    </ul>
                                                </div>
                                            </form>
                                                <div class="clearfix form-actions center" style="margin-top: 0;margin-bottom: 0;">
                                                    <button class="btn btn-primary btn-sm"  onclick="_exportApply4(this,'${MEMBER_TYPE_STUDENT}')">
                                                        <i class="fa fa-download"></i> 导出学生</button>

                                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                                    <button type="button" class="btn btn-warning btn-sm"  onclick="_exportApply4(this,'${MEMBER_TYPE_TEACHER}')">
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
        <div id="body-content-view">

        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    function _exportApply1(btn, type){

        var _applyTime = $("input[name=_applyTime]", "#exportForm1").val();
        var _activeTime = $("input[name=_activeTime]", "#exportForm1").val();
        var _candidateTime = $("input[name=_candidateTime]", "#exportForm1").val();
        var partyId = $("select[name=partyId]", "#exportForm1").val();
        var branchId = $("select[name=branchId]", "#exportForm1").val();
        var url="${ctx}/memberApplyExport?exportType=1&type={0}&partyId={1}&branchId={2}&_applyTime={3}&_activeTime={4}&_candidateTime={5}&t={6}"
                .format(type, partyId, branchId, _applyTime, _activeTime, _candidateTime, new Date().getTime());
        $(btn).download(url);
    }

    function _exportApply2(btn, type){

        var _growTime = $("input[name=_growTime]", "#exportForm2").val();
        var partyId = $("select[name=partyId]", "#exportForm2").val();
        var branchId = $("select[name=branchId]", "#exportForm2").val();
        var url="${ctx}/memberApplyExport?exportType=2&type={0}&partyId={1}&branchId={2}&_growTime={3}&t={4}"
                .format(type, partyId, branchId, _growTime, new Date().getTime());
        $(btn).download(url);
    }

    function _exportApply3(btn, type){

        var _growTime = $("input[name=_growTime]", "#exportForm3").val();
        var _drawTime = $("input[name=_drawTime]", "#exportForm3").val();
        var partyId = $("select[name=partyId]", "#exportForm3").val();
        var branchId = $("select[name=branchId]", "#exportForm3").val();
        var url="";
        if (${_ignore_plan_and_draw}){
            url="${ctx}/memberApplyExport?exportType=3&type={0}&partyId={1}&branchId={2}&_growTime={3}&t={4}"
                .format(type, partyId, branchId, _growTime, new Date().getTime());
        }else {
            url="${ctx}/memberApplyExport?exportType=3&type={0}&partyId={1}&branchId={2}&_drawTime={3}&t={4}"
                .format(type, partyId, branchId, _drawTime, new Date().getTime());
        }
        $(btn).download(url);
    }

    function _exportApply4(btn, type){

        var _positiveTime = $("input[name=_positiveTime]", "#exportForm4").val();
        var partyId = $("select[name=partyId]", "#exportForm4").val();
        var branchId = $("select[name=branchId]", "#exportForm4").val();
        var url="${ctx}/memberApplyExport?exportType=4&type={0}&partyId={1}&branchId={2}&_positiveTime={3}&t={4}"
                .format(type, partyId, branchId, _positiveTime, new Date().getTime());
        $(btn).download(url);
    }
</script>