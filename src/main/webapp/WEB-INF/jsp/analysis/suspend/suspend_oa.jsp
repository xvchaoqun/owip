<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="row">
    <div class="col-sm-12">
        <div class="widget-box transparent">
            <div class="widget-header widget-header-flat">
                <h4 class="widget-title lighter">
                    <i class="ace-icon fa fa-hourglass-end orange"></i>
                    协同办公待处理事项
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
                        <shiro:hasPermission name="oaTaskUser:check">
                        <div class="infobox infobox-blue"
                             <c:if test="${approvalCount>0}">data-url="${ctx}/oa/oaTask"</c:if>>
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-sign-out"></i>
                            </div>
                            <div class="infobox-data">
                                <span class="infobox-data-number"><span class="count">${approvalCount}</span> <span
                                        style="font-size: 10pt;">待审</span></span>
                                <div class="infobox-content">协同办公审批</div>
                            </div>
                        </div>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="userOaTask:report">
                        <div class="infobox infobox-green"
                             <c:if test="${taskCount>0}">data-url="${ctx}/user/oa/oaTask"</c:if>>
                            <div class="infobox-icon">
                                <i class="ace-icon fa fa-sign-in"></i>
                            </div>

                            <div class="infobox-data">
                                <span class="infobox-data-number"><span class="count">${taskCount}</span> <span
                                        style="font-size: 10pt;">未处理</span> </span>

                                <div class="infobox-content">
                                    协同办公任务
                                </div>
                            </div>
                        </div>
                            </shiro:hasPermission>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
    </div>
    <!-- /.col -->
</div>
