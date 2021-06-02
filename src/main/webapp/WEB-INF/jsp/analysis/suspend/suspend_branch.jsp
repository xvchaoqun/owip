<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="row">
    <div class="col-sm-12">
        <div class="widget-box transparent">
            <div class="widget-header widget-header-flat">
                <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-hourglass-end orange"></i>
                    党建待处理事项
                </h4>
                <div class="widget-toolbar">
                    <a href="javascript:;" data-action="collapse">
                        <i class="ace-icon fa fa-chevron-up"></i>
                    </a>
                </div>
            </div>

            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="col-sm-12">

                        <div class="infobox infobox-blue"
                             <c:if test="${branchMemberGroupCount>0}">data-url="${ctx}/branchMemberGroup?branchId=${param.branchId}&isTranTime=1"</c:if>>
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-sign-out"></i>
                            </div>
                            <div class="infobox-data">
                                <span class="infobox-data-number"><span class="count">${branchMemberGroupCount}</span> <span
                                        style="font-size: 10pt;">应换届</span></span>

                                <div class="infobox-content">应换届支部委员会</div>
                            </div>
                        </div>

                        <div class="infobox infobox-blue">
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-send"></i>
                            </div>
                            <div class="infobox-data">
                                <span class="infobox-data-number">
                                <span data-url="${ctx}/memberApply_layout?branchId=${param.branchId}&stage=${OW_APPLY_STAGE_INIT}&type=${MEMBER_TYPE_STUDENT}&applyStatus=0">
                                    <span class="count">${studentApplyCount}</span><span
                                        style="font-size: 10pt;">学生</span></span>
                                 <span data-url="${ctx}/memberApply_layout?branchId=${param.branchId}&stage=${OW_APPLY_STAGE_INIT}&type=${MEMBER_TYPE_TEACHER}&applyStatus=0">
                                    <span class="count">${teacherApplyCount}</span><span
                                         style="font-size: 10pt;">教职工</span></span>
                                </span>
                                <div class="infobox-content">入党申请审批</div>
                            </div>
                        </div>

                        <div class="infobox infobox-green2">
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-users"></i>
                            </div>
                            <div class="infobox-data">
                                <span class="infobox-data-number">
                                <span data-url="${ctx}/memberApply_layout?branchId=${param.branchId}&stage=${OW_APPLY_STAGE_ACTIVE}&type=${MEMBER_TYPE_STUDENT}&activityStatus=-1">
                                    <span class="count">${studentActivityCount}</span><span
                                        style="font-size: 10pt;">学生</span></span>
                                 <span data-url="${ctx}/memberApply_layout?branchId=${param.branchId}&stage=${OW_APPLY_STAGE_ACTIVE}&type=${MEMBER_TYPE_TEACHER}&activityStatus=-1">
                                    <span class="count">${teacherActivityCount}</span><span
                                         style="font-size: 10pt;">教职工</span></span>
                                </span>
                                <div class="infobox-content">入党积极分子审批</div>
                            </div>
                        </div>

                        <div class="infobox infobox-orange2">
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-id-card"></i>
                            </div>
                            <div class="infobox-data">
                                <span class="infobox-data-number">
                                <span data-url="${ctx}/memberApply_layout?branchId=${param.branchId}&stage=${OW_APPLY_STAGE_CANDIDATE}&type=${MEMBER_TYPE_STUDENT}&planStatus=-1">
                                    <span class="count">${studentObjectCount}</span><span
                                        style="font-size: 10pt;">学生</span></span>
                                 <span data-url="${ctx}/memberApply_layout?branchId=${param.branchId}&stage=${OW_APPLY_STAGE_CANDIDATE}&type=${MEMBER_TYPE_TEACHER}&planStatus=-1">
                                    <span class="count">${teacherObjectCount}</span><span
                                         style="font-size: 10pt;">教职工</span></span>
                                </span>
                                <div class="infobox-content">发展对象审批</div>
                            </div>
                        </div>

                        <c:if test="${!_ignore_plan_and_draw}">
                            <div class="infobox infobox-pink">
                                <div class="infobox-icon">
                                    <i class="ace-icon fa fa-hand-lizard-o"></i>
                                </div>
                                <div class="infobox-data">
                                    <span class="infobox-data-number">
                                        <span data-url="${ctx}/memberApply_layout?partyId=${param.partyId}&stage=${OW_APPLY_STAGE_DRAW}&type=${MEMBER_TYPE_STUDENT}&growStatus=2">
                                            <span class="count">${studentGrowOdCheckCount}</span>
                                            <span style="font-size: 10pt;">学生</span>
                                        </span>
                                        <span data-url="${ctx}/memberApply_layout?partyId=${param.partyId}&stage=${OW_APPLY_STAGE_DRAW}&type=${MEMBER_TYPE_TEACHER}&growStatus=2">
                                            <span class="count">${teacherGrowOdCheckCount}</span>
                                            <span style="font-size: 10pt;">教职工</span>
                                        </span>
                                    </span>
                                    <div class="infobox-content">领取志愿书审批</div>
                                </div>
                            </div>
                        </c:if>


                        <div class="infobox infobox-red">
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-star"></i>
                            </div>
                            <div class="infobox-data">
                                <span class="infobox-data-number">
                                   <%-- <c:if test="${!_ignore_plan_and_draw}">--%>
                                        <span data-url="${ctx}/memberApply_layout?branchId=${param.branchId}&stage=${OW_APPLY_STAGE_GROW}&type=${MEMBER_TYPE_STUDENT}&positiveStatus=-1">
                                            <span class="count">${studentPositiveOdCheckCount}</span><span
                                                style="font-size: 10pt;">学生</span></span>
                                         <span data-url="${ctx}/memberApply_layout?branchId=${param.branchId}&stage=${OW_APPLY_STAGE_GROW}&type=${MEMBER_TYPE_TEACHER}&positiveStatus=-1">
                                            <span class="count">${teacherPositiveOdCheckCount}</span><span
                                                 style="font-size: 10pt;">教职工</span></span>
                                    <%--</c:if>

                                    <c:if test="${_ignore_plan_and_draw}">
                                        <span data-url="${ctx}/memberApply_layout?branchId=${param.branchId}&stage=${OW_APPLY_STAGE_CANDIDATE}&type=${MEMBER_TYPE_STUDENT}&candidateStatus=0">
                                            <span class="count">${studentPositiveOdCheckCount}</span><span
                                                style="font-size: 10pt;">学生</span></span>
                                         <span data-url="${ctx}/memberApply_layout?branchId=${param.branchId}&stage=${OW_APPLY_STAGE_CANDIDATE}&type=${MEMBER_TYPE_TEACHER}&candidateStatus=0">
                                            <span class="count">${teacherPositiveOdCheckCount}</span><span
                                                 style="font-size: 10pt;">教职工</span></span>
                                    </c:if>--%>
                                </span>
                                <div class="infobox-content">预备党员转正审批</div>
                            </div>
                        </div>

                        <div class="infobox infobox-blue2"
                                 <c:if test="${memberStayCount_abroad>0}">data-url="${ctx}/memberStay?type=${MEMBER_STAY_TYPE_ABROAD}&cls=1&branchId=${param.branchId}"</c:if>>
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-plane"></i>
                            </div>
                            <div class="infobox-data">
                                <span class="infobox-data-number"><span
                                        class="count">${memberStayCount_abroad}</span> <span style="font-size: 10pt;">未处理</span></span>
                                <div class="infobox-content">${MEMBER_STAY_TYPE_MAP.get(MEMBER_STAY_TYPE_ABROAD)}暂留</div>
                            </div>
                        </div>
                        <div class="infobox infobox-blue2"
                             <c:if test="${memberStayCount_internal>0}">data-url="${ctx}/memberStay?type=${MEMBER_STAY_TYPE_INTERNAL}&cls=1&branchId=${param.branchId}"</c:if>>
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-plane"></i>
                            </div>
                            <div class="infobox-data">
                                <span class="infobox-data-number"><span class="count">${memberStayCount_internal}</span> <span
                                        style="font-size: 10pt;">未处理</span></span>
                                <div class="infobox-content">${MEMBER_STAY_TYPE_MAP.get(MEMBER_STAY_TYPE_INTERNAL)}暂留</div>
                            </div>
                        </div>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
    </div>
    <!-- /.col -->
</div>