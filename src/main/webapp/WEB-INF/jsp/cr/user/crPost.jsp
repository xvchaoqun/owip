<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="row" style="width: 1050px">
    <div class="widget-box transparent">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="hideView btn btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i> 返回</a>

            </h4>
            <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    ${cm:formatDate(crInfo.addDate, "yyyy.MM.dd")} 招聘信息
                </span>
        </div>
        <div class="tabbable">
            <div class="tab-content">
                <table class="table table-bordered table-condensed table-striped table-unhover2 table-center">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>岗位名称</th>
                        <th width="50">招聘人数</th>
                        <th>主要职责</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${crPosts}" var="post" varStatus="vs">
                        <tr>
                            <td>${vs.count}</td>
                            <td class="align-left">${post.name}</td>
                            <td>${post.num}</td>
                            <td class="align-left">${post.duty}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>