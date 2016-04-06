<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!-- PAGE CONTENT BEGINS -->
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
        <!-- #section:pages/inbox -->
        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab4">
                <li class="<c:if test="${status==0}">active</c:if>">
                    <a href="?status=0">&nbsp;&nbsp;<i
                            class="green ace-icon fa fa-clock-o bigger-120"></i>&nbsp;未完成审批&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </li>

                <li class="<c:if test="${status==1}">active</c:if>">
                    <a href="?status=1">&nbsp;&nbsp;<i
                            class="orange ace-icon fa fa-check-square-o bigger-120"></i>&nbsp;已完成审批&nbsp;&nbsp;</a>
                </li>
            </ul>
            <div class="tab-content" style="padding: 0px;">
                <div class="tab-pane in active">
                    <div class="message-list-container">
                        <div class="message-list">
                            <c:if test="${fn:length(applySelfs)==0}">
                                <div class="none">目前没有${status==0?'待审批':'已审批'}记录</div>
                            </c:if>
                            <c:forEach items="${applySelfs}" var="applySelf">
                                <c:set var="cadre" value="${cadreMap.get(applySelf.cadreId)}"/>
                                <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                                <div class="openView message-item" data-openby="page"
                                     data-url="${ctx}/m/applySelf_view?id=${applySelf.id}&status=${status}&type=admin">
                                    <i class="message-star ace-icon fa ${status==1?'fa-star orange2':'fa-star-o light-green'}"></i>
                                    <span class="sender">${sysUser.realname}-${cadre.title}</span>
                                    <span class="time">${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</span>
                                <span class="summary">
                                    <span class="text">
                                        <c:set var="reasons" value="${fn:split(applySelf.reason, '+++')}"/>
                                        ${reasons[0]}${fn:length(reasons)>1?'等':''}，
                                        ${cm:formatDate(applySelf.startDate,'MM-dd')}~${cm:formatDate(applySelf.endDate,'MM-dd')}，
                                         <c:set var="toCountrys" value="${fn:split(applySelf.toCountry, ',')}"/>
                                        ${toCountrys[0]}${fn:length(toCountrys)>1?'等':''}
                                    </span>
                                    <c:if test="${status==0}">
                                        <c:if test="${applySelf.flowNode<=0}">
                                            <a class="btn btn-info btn-xs pull-right">审批</a>
                                        </c:if>
                                        <c:if test="${applySelf.flowNode>0}">
                                            <c:set var="approverType" value="${approverTypeMap.get(applySelf.flowNode)}"/>
                                            <span disabled class="label label-default label-xs pull-right">${approverType.name}审批</span>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${status==1}">
                                        <a class="btn btn-warning btn-xs pull-right">详情</a>
                                    </c:if>
                                </span>
                                </div>
                            <%--<div class="openView message-item" data-openby="page"
                                 data-url="${ctx}/m/applySelf_view?id=${applySelf.id}&status=${status}&type=admin">
                                <i class="message-star ace-icon fa ${status==1?'fa-star orange2':'fa-star-o light-green'}"></i>
                                <span class="sender">【S${applySelf.id}】${fn:replace(applySelf.reason, '+++', ',')}</span>
                                <span class="time">${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</span>
                                <span class="summary">
                                    <span class="text">
                                        ${sysUser.realname}-${cadre.title}
                                    </span>
                                    <c:if test="${status==0}">
                                        <c:if test="${applySelf.flowNode<=0}">
                                        <a class="btn btn-info btn-xs pull-right">审批</a>
                                        </c:if>
                                        <c:if test="${applySelf.flowNode>0}">
                                            <c:set var="approverType" value="${approverTypeMap.get(applySelf.flowNode)}"/>
                                            <span disabled class="label label-default label-xs pull-right">${approverType.name}审批</span>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${status==1}">
                                        <a class="btn btn-warning btn-xs pull-right">详情</a>
                                    </c:if>
                                </span>
                                <span class="time">

                                </span>
                            </div>--%>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="message-footer clearfix">
                        <wo:page commonList="${commonList}" uri="${ctx}/m/applySelf_page" target="#page-content" model="4"/>
                    </div>
                </div>
            </div>
        </div>
        <!-- /section:pages/inbox -->
        </div>
        <div id="item-content">
        </div>
    </div>
    <!-- /.col -->
</div>
<!-- /.row -->
<!-- PAGE CONTENT ENDS -->
