<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="back-btn">
    <a href="javascript:;" class="closeView"><i class="fa fa-reply"></i> 返回</a>
</div>
<div class="alert alert-block alert-success" style="margin-bottom: 5px; padding: 5px 15px;">
    <i class="ace-icon fa fa-info-circle green"></i> ${crsPost.name}
    <c:if test="${not empty crsPost.job}">(${crsPost.job})</c:if>
</div>
<div class="tabbable">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab4">
        <li class="<c:if test="${cls==1}">active</c:if>">
            <a href="javascript:;" class="${cls==1?'':'openView'}" data-open-by="page" data-url="${ctx}/m/crs/crsApplicant_page?cls=1&postId=${param.postId}">
                应聘名单(${count[1]})</a>
        </li>

        <li class="<c:if test="${cls==2}">active</c:if>">
            <a href="javascript:;" class="${cls==2?'':'openView'}" data-open-by="page" data-url="${ctx}/m/crs/crsApplicant_page?cls=2&postId=${param.postId}">
                通过(${count[2]})</a>
        </li>

        <li class="<c:if test="${cls==3}">active</c:if>">
            <a href="javascript:;" class="${cls==3?'':'openView'}" data-open-by="page" data-url="${ctx}/m/crs/crsApplicant_page?cls=3&postId=${param.postId}">
                未通过(${count[3]})</a>
        </li>

        <li class="<c:if test="${cls==4}">active</c:if>">
            <a href="javascript:;" class="${cls==4?'':'openView'}" data-open-by="page" data-url="${ctx}/m/crs/crsApplicant_page?cls=4&postId=${param.postId}">退出(${count[4]})</a>
        </li>
    </ul>
    <div class="tab-content" style="padding: 0px;">
        <div class="tab-pane in active">
            <div style="overflow:auto;width:100%">
                <table id="fixedTable" class="table table-bordered table-center" width="auto"
                       style="white-space:nowrap">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>姓名</th>
                        <th>工作证号</th>
                        <th>报名时间</th>
                        <th>所在单位及职务</th>
                        <th>性别</th>
                        <th>出生时间</th>
                        <th>年龄</th>
                        <th>民族</th>
                        <th>政治面貌</th>
                        <th>党派加入时间</th>
                        <th>最高学历</th>
                        <th>毕业学校</th>
                        <th>所学专业</th>
                        <th>参加工作时间</th>
                        <th>到校时间</th>
                        <th>专业技术职务</th>
                        <th>专技职务评定时间</th>
                        <th>专技岗位等级</th>
                        <th>专技岗位分级时间</th>
                        <th>管理岗位等级</th>
                        <th>管理岗位分级时间</th>
                        <th>推荐/自荐</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="backTo" value="${ctx}/m/crs/crsApplicant_page?cls=${param.cls}&postId=${param.postId}&pageNo=${param.pageNo}"/>
                    <c:forEach var="crsApplicant" items="${crsApplicants}" varStatus="vs">
                        <c:set var="cadre" value="${crsApplicant.cadre}"/>
                        <tr>
                            <td>${vs.count}</td>
                            <td>
                                <a href="javascript:;" class="openView" data-open-by="page"
                                    data-url="${ctx}/m/cadre_info?cadreId=${cadre.id}&backTo=${cm:encodeURI(backTo)}">
                            ${cadre.realname}</a></td>
                            <td>${cadre.code}</td>
                            <td>${cm:formatDate(crsApplicant.enrollTime,'yyyy-MM-dd HH:mm')}</td>
                            <td style="text-align: left">${cadre.title}</td>
                            <td>${GENDER_MAP.get(cadre.gender)}</td>
                            <td>${cm:formatDate(cadre.birth,'yyyy-MM-dd')}</td>
                            <td>
                                <c:if test="${not empty cadre.birth}">
                                    ${cm:intervalYearsUntilNow (cadre.birth)}岁
                                </c:if>
                            </td>
                            <td>${cadre.nation}</td>
                            <td>${cadre.cadreDpType>0?democraticPartyMap.get(cadre.dpTypeId).name:(cadre.cadreDpType==0)?'中共党员':''}</td>
                            <td>${cm:formatDate(cadre.cadreGrowTime,'yyyy-MM-dd')}</td>
                            <td>${eduTypeMap.get(cadre.eduId).name}</td>
                            <td>${cadre.school}</td>
                            <td>${cadre.major}</td>
                            <td>${cm:formatDate(cadre.workTime, "yyyy-MM-dd")}</td>
                            <td>${cm:formatDate(cadre.arriveTime, "yyyy-MM-dd")}</td>
                            <td>${cadre.proPost}</td>
                            <td>${cm:formatDate(cadre.proPostTime, "yyyy-MM-dd")}</td>
                            <td>${cadre.proPostLevel}</td>
                            <td>${cm:formatDate(cadre.proPostLevelTime, "yyyy-MM-dd")}</td>
                            <td>${cadre.manageLevel}</td>
                            <td>${cm:formatDate(cadre.manageLevelTime, "yyyy-MM-dd")}</td>
                            <td>${crsApplicant.isRecommend?'推荐':'个人报名'}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="message-footer clearfix">
                <wo:page commonList="${commonList}" uri="${ctx}/m/crs/crsApplicant_page" target="#body-content-view"
                         model="4"/>
            </div>
        </div>
    </div>
</div>
<style>
    .table > thead > tr > th:last-child {
        border-right-color: inherit;
    }
</style>
<script>
    $("#fixedTable").fixedTable({
        fixedCell: 2,
        fixedType: "left",
    });

    window.addEventListener("orientationchange", function () {
        window.setTimeout(function () {
            var w = $("#body-content-view").width() - $("#fixedTable_fixed").width();
            $("#fixedTable").closest("div").width(w);
        }, 200);
    });
</script>
