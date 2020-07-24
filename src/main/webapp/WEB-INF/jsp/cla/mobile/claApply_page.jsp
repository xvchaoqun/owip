<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
        <!-- #section:pages/inbox -->
        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab4">
                <li class="<c:if test="${status==0}">active</c:if> claApplyLi" data-status="0">
                    <a<%-- href="?status=0"--%>><i
                            class="green ace-icon fa fa-clock-o bigger-120"></i>未完成审批</a>
                </li>

                <li class="<c:if test="${status==1}">active</c:if> claApplyLi" data-status="1">
                    <a<%-- href="?status=1"--%>><i
                            class="orange ace-icon fa fa-check-square-o bigger-120"></i>已完成审批</a>
                </li>
            </ul>
            <div class="tab-content" style="padding: 0px;">
                <div class="tab-pane in active">
                    <div class="message-list-container">
                        <div class="message-list">
                            <c:if test="${fn:length(claApplys)==0}">
                                <div class="none">目前没有${status==0?'待审批':'已审批'}记录</div>
                            </c:if>
                            <c:forEach items="${claApplys}" var="claApply">
                                <c:set var="cadre" value="${cm:getCadreById(claApply.cadreId)}"/>
                                <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                                <div class="openView message-item" data-open-by="page"
                                     data-url="${ctx}/m/cla/claApply_view?id=${claApply.id}&status=${status}&type=admin">
                                    <i class="message-star ace-icon fa ${status==1?'fa-star orange2':'fa-star-o light-green'}"></i>
                                    <span class="sender">${sysUser.realname}</span>
                                    <span class="time">${cm:formatDate(claApply.applyDate,'yyyy-MM-dd')}</span>
                                <span class="summary">
                                    <span class="text">
                                        ${cm:formatDate(claApply.startTime,'MM.dd')}~${cm:formatDate(claApply.endTime,'MM.dd')}，
                                            ${cm:substr(claApply.reason, 0, 15, "")}，${cm:substr(claApply.destination, 0, 15, "")}
                                    </span>
                                    <c:if test="${status==0}">
                                        <c:if test="${claApply.flowNode<=0}">
                                            <a class="btn btn-info btn-xs pull-right">审批</a>
                                        </c:if>
                                        <%--<c:if test="${claApply.flowNode>0}">
                                            <c:set var="approverType" value="${approverTypeMap.get(claApply.flowNode)}"/>
                                            <span disabled class="label label-default label-xs pull-right">${approverType.name}审批</span>
                                        </c:if>--%>
                                    </c:if>
                                    <c:if test="${status==1}">
                                        <a class="btn btn-warning btn-xs pull-right">详情</a>
                                    </c:if>
                                </span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="message-footer clearfix">
                        <wo:page commonList="${commonList}" uri="${ctx}/m/cla/claApply_page" target="#page-content" model="4"/>
                    </div>
                </div>
            </div>
        </div>
        <!-- /section:pages/inbox -->
        </div>
        <div id="body-content-view">
        </div>
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->
<!-- PAGE CONTENT ENDS -->
<script>
    $("#myTab4 .claApplyLi").click(function () {

        $("#page-content").load("${ctx}/m/cla/claApply_page?status="+$(this).data("status"));
    });
</script>