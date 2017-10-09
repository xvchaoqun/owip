<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="title">
    <div class="first">${_school}${crsPost.name}人选</div>
    <div class="sencond">专家组推荐意见汇总表</div>
</div>
<div style="margin-bottom: 10px">
    招聘会专家组共<span class="num">${crsPost.statExpertCount}</span>人，
    发出推荐票<span class="num">${crsPost.statGiveCount}</span>张，
    收回<span class="num">${crsPost.statBackCount}</span>张，推荐结果如下：
</div>
<table class="table table-bordered table-unhover2 ">
    <thead>
    <tr>
        <th rowspan="2">招聘岗位</th>
        <th rowspan="2">应聘人员</th>
        <th colspan="2">推荐意见汇总</th>
    </tr>
    <tr>
        <th>1</th>
        <th>2</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${crsApplicants}" var="applicant" varStatus="vs">
        <tr>
            <c:if test="${vs.first}">
            <td class="center-text" rowspan="${fn:length(crsApplicants)}">${crsPost.name}</td>
            </c:if>
            <td class="center-text">${applicant.user.username}</td>
            <td>${applicant.recommendFirstCount}</td>
            <td>${applicant.recommendSecondCount}</td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="4">
            专家组推荐意见汇总表扫描件：
            <c:if test="${not empty crsPost.statFile}">
                <%--<a class="various" rel="group" title="${cm:encodeURI(crsPost.statFileName)}"
                   data-path="${cm:encodeURI(crsPost.statFile)}"
                   data-fancybox-type="image" href="${ctx}/pic?path=${cm:encodeURI(crsPost.statFile)}">
                    <img src="${ctx}/pic?path=${cm:encodeURI(crsPost.statFile)}" width="50" height="50"></a>--%>

                <a href="${ctx}/attach/download?path=${crsPost.statFile}&filename=${crsPost.statFileName}"> 下载</a>
            </c:if>
        </td>
    </tr>
    <tr>
        <td colspan="4">
            记录日期：${cm:formatDate(crsPost.statDate, "yyyy年MM月dd日")}
        </td>
    </tr>
    </tbody>
</table>
<style>
    .step-content .table th, .step-content .center-text{
        text-align: center;
        vertical-align: middle!important;
    }
    .step-content span.num{
        display:inline-block;width: 40px;border-bottom:1px solid #000; text-align: center
    }
    .step-content .title{
        margin-top:30px;margin-bottom: 40px;
    }
    .step-content .title .first{
        font-size: 18pt; font-weight: bolder; text-align: center
    }
    .step-content .title .sencond{
        font-size: 16pt; font-weight: bolder; text-align: center
    }
</style>