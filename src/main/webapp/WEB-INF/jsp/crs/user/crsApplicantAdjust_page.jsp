<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box transparent">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:;" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>

                </h4>
                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" >调整记录</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
                    <div class="tab-content padding-4" id="step-content">
                        <table class="table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th width="150">调整时间</th>
                                <th width="300">调整前岗位</th>
                                <th>调整后岗位</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${adjusts}" var="adjust">
                                <tr>
                                    <td>${cm:formatDate(adjust.adjustTime, "yyyy-MM-dd HH:mm:ss")}</td>
                                    <td>
                                        <c:forEach items="${adjust.prePosts}" var="post">
                                            <div>${post.name}</div>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach items="${adjust.afterPosts}" var="post">
                                            <div>${post.name}</div>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>