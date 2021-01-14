<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_STAY_TYPE_ABROAD" value="<%=MemberConstants.MEMBER_STAY_TYPE_ABROAD%>"/>
<c:set var="MEMBER_STAY_TYPE_INTERNAL" value="<%=MemberConstants.MEMBER_STAY_TYPE_INTERNAL%>"/>
<c:set var="MEMBER_STAY_TYPE_MAP" value="<%=MemberConstants.MEMBER_STAY_TYPE_MAP%>"/>
<c:set var="OW_APPLY_TYPE_TEACHER" value="<%=OwConstants.OW_APPLY_TYPE_TEACHER%>"/>
<c:set var="OW_APPLY_TYPE_STU" value="<%=OwConstants.OW_APPLY_TYPE_STU%>"/>
<c:set var="OW_APPLY_STAGE_DRAW" value="<%=OwConstants.OW_APPLY_STAGE_DRAW%>"/>
<c:set var="OW_APPLY_STAGE_GROW" value="<%=OwConstants.OW_APPLY_STAGE_GROW%>"/>
<c:set value="${_pMap['ignore_plan_and_draw']=='true'}" var="_ignore_plan_and_draw"/>
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

                        <c:if test="${!_ignore_plan_and_draw}">
                            <div class="infobox infobox-pink">
                                <div class="infobox-icon">
                                    <i class="ace-icon fa fa-hand-lizard-o"></i>
                                </div>
                                <div class="infobox-data">
                                    <span class="infobox-data-number">
                                        <span data-url="${ctx}/memberApply_layout?stage=${OW_APPLY_STAGE_DRAW}&type=${OW_APPLY_TYPE_STU}&growStatus=-1">
                                            <span class="count">${studentGrowOdCheckCount}</span>
                                            <span style="font-size: 10pt;">学生</span>
                                        </span>
                                        <span data-url="${ctx}/memberApply_layout?stage=${OW_APPLY_STAGE_DRAW}&type=${OW_APPLY_TYPE_TEACHER}&growStatus=-1">
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
                                <span data-url="${ctx}/memberApply_layout?stage=${OW_APPLY_STAGE_GROW}&type=${OW_APPLY_TYPE_STU}&positiveStatus=1">
                                <span class="count">${studentPositiveOdCheckCount}</span><span
                                        style="font-size: 10pt;">学生</span></span>
                                 <span data-url="${ctx}/memberApply_layout?stage=${OW_APPLY_STAGE_GROW}&type=${OW_APPLY_TYPE_TEACHER}&positiveStatus=1">
                                <span class="count">${teacherPositiveOdCheckCount}</span><span
                                         style="font-size: 10pt;">教职工</span></span>
                                </span>
                                <div class="infobox-content">预备党员转正审批</div>
                            </div>
                        </div>
                        <div class="infobox infobox-blue"
                             <c:if test="${memberOutCount>0}">data-url="${ctx}/memberOut?cls=6"</c:if>>
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-sign-out"></i>
                            </div>
                            <div class="infobox-data">
                                <span class="infobox-data-number"><span class="count">${memberOutCount}</span> <span
                                        style="font-size: 10pt;">待审</span></span>

                                <div class="infobox-content">组织关系转出审批</div>
                            </div>
                        </div>
                        <div class="infobox infobox-green"
                             <c:if test="${memberInCount>0}">data-url="${ctx}/memberIn?cls=4"</c:if>>
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-sign-in"></i>
                            </div>

                            <div class="infobox-data">
                                <span class="infobox-data-number"><span class="count">${memberInCount}</span> <span
                                        style="font-size: 10pt;">未处理</span> </span>

                                <div class="infobox-content">
                                    组织关系转入审批
                                </div>
                            </div>
                        </div>
                        <div class="infobox infobox-blue2"
                             <c:if test="${memberStayCount_abroad>0}">data-url="${ctx}/memberStay?type=${MEMBER_STAY_TYPE_ABROAD}&cls=3"</c:if>>
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
                             <c:if test="${memberStayCount_internal>0}">data-url="${ctx}/memberStay?type=${MEMBER_STAY_TYPE_INTERNAL}&cls=3"</c:if>>
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
