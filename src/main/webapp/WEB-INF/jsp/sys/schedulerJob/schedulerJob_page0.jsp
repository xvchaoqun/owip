<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div id="body-content">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
                <a class="popupBtn btn btn-primary btn-sm"
                   data-url="${ctx}/schedulerJob_au"><i class="fa fa-plus"></i> 添加定时任务</a>

                <a class="btn btn-success btn-sm" href="javascript:;" onclick="_reload()"><i class="fa fa-refresh"></i> 立即刷新</a>

                <div class="space-4"></div>
                <table class="table table-actived table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>任务名称</th>
                        <th>执行类</th>
                        <th>cron表达式</th>
                        <th class="hidden-xs hidden-sm hidden-md">任务描述</th>
                        <th>状态</th>
                        <%--<th width="180" class="hidden-xs hidden-sm hidden-md">创建时间</th>--%>
                        <th></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${schedulerJobs}" var="schedulerJob" varStatus="st">
                        <tr>
                            <td>${schedulerJob.name}</td>
                            <td>${schedulerJob.clazz}</td>
                            <td nowrap>${schedulerJob.cron}</td>
                            <td title="${schedulerJob.summary}" class="hidden-xs hidden-sm hidden-md">
                                    ${cm:substr(schedulerJob.summary, 0, 40, "...")}
                            </td>
                            <td nowrap>
                                ${allJobsMap.get(schedulerJob.name)!=null?'已启动':'已关闭'}
                                （${runJobsMap.get(schedulerJob.name)!=null?'执行中':'未执行'}）
                            </td>
                            <%--<td class="hidden-xs hidden-sm hidden-md">
                                    ${cm:formatDate(schedulerJob.createTime, "yyyy-MM-dd HH:mm:ss")}
                            </td>--%>
                            <td nowrap>
                                <div class="buttons">
                                    <button class="popupBtn btn btn-primary btn-sm" data-url="${ctx}/schedulerJob_au?id=${schedulerJob.id}">
                                        <i class="fa fa-edit"></i> 更新
                                    </button>
                                    <c:if test="${!schedulerJob.isStarted}">
                                    <a class="confirm btn btn-success btn-sm"
                                       data-url="${ctx}/schedulerJob_start?id=${schedulerJob.id}" data-title="启动定时任务"
                                       data-msg="确定启动这个定时任务吗？" data-callback="_reload"><i class="fa fa-clock-o"></i> 启动任务</a>
                                    </c:if>
                                    <c:if test="${schedulerJob.isStarted}">
                                    <a class="confirm btn btn-warning btn-sm"
                                       data-url="${ctx}/schedulerJob_stop?id=${schedulerJob.id}" data-title="关闭定时任务"
                                       data-msg="确定关闭这个定时任务吗？" data-callback="_reload"><i class="fa fa-times"></i> 关闭任务</a>
                                    </c:if>
                                    <a class="confirm btn btn-danger btn-sm"
                                       data-url="${ctx}/schedulerJob_del?id=${schedulerJob.id}" data-title="删除定时任务"
                                       data-msg="确定删除这个定时任务吗？" data-callback="_reload"><i class="fa fa-trash"></i> 删除</a>
                                </div>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:if test="${!empty commonList && commonList.pageNum>1 }">
                    <wo:page commonList="${commonList}" uri="${ctx}/schedulerJob" target="#page-content" pageNum="5"
                             model="3"/>
                </c:if>
        </div>
    </div>
    <div id="item-content"></div>
</div>

<script>
    function _reload() {

        $("#modal").modal('hide');
        $("#page-content").load("${ctx}/schedulerJob?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>
