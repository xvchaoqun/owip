<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="toEdit" value="${hasDirectModifyCadreAuth || cm:isPermitted(PERMISSION_CADREADMIN)}"/>
<div class="tabbable" style="margin: 0px 20px; width: 900px">
    <div class="space-4"></div>
    <table class="checkTable table table-bordered table-unhover2" data-offset-top="101">
    <thead>
    <tr>
        <th width="150">类别</th>
        <th width="300">具体内容</th>
        <th>完整性校验结果</th>
        <th width="250">操作</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td rowspan="10">
            基本信息
        </td>
        <td>照片</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'avatar', 1)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>籍贯</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'native_place', 1)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>出生地</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'homeplace', 1)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>户籍地</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'household', 1)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>健康状况</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'health', 1)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>熟悉专业有何专长</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'specialty', 1)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>手机号</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'mobile', 1)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>办公电话</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'phone', 1)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>电子邮箱</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'email', 1)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>参加工作时间</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'work_time', 2)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td rowspan="2">
            学习经历
        </td>
        <td>最高学历</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, null, 5)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreEdu_page"
                    applyUrl="${ctx}/modifyCadreEdu"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                               toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>最高学位</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, null, 6)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreEdu_page"
                    applyUrl="${ctx}/modifyCadreEdu"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}"/>
        </td>
    </tr>

    <tr>
        <td>
            工作经历
        </td>
        <td>工作经历</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'work', 3)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreWork_page"
                    applyUrl="${ctx}/modifyCadreWork"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}"/>
        </td>
    </tr>

    <tr>
        <td rowspan="3">
            岗位过程信息
        </td>
        <td>专技岗位过程信息</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'post_pro', 4)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadrePostInfo_page&type=1"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}"
                    updateName="post_pro"/>
        </td>
    </tr>
    <tr>
        <td>管理岗位过程信息</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'post_admin', 4)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadrePostInfo_page&type=2"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}"
                    updateName="post_admin"/>
        </td>
    </tr>
    <tr>
        <td>工勤岗位过程信息</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'post_work', 4)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadrePostInfo_page&type=3"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}"
                    updateName="post_work"/>
        </td>
    </tr>


    <tr>
        <td>
            社会或学术兼职
        </td>
        <td>社会或学术兼职</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'parttime', 3)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreParttime_page"
                    applyUrl="${ctx}/modifyCadreParttime"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="parttime"/>
        </td>
    </tr>
    <tr>
        <td>
            培训情况
        </td>
        <td>培训情况</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'train', 3)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreTrain_page"
                    applyUrl="${ctx}/modifyCadreTrain"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="train"/>
        </td>
    </tr>

    <tr>
        <td rowspan="2">
            教学情况
        </td>
        <td>承担本、硕、博课程情况</td>

        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'course', 3)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreCourse_page&type=1"
                    applyUrl="${ctx}/modifyCadreCourse"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="course"/>
        </td>
    </tr>
    <tr>
        <td>教学成果及获奖情况</td>

        <td>
            <c:set var="result" value="${cm:cadreRewardCheck(param.cadreId, CADRE_REWARD_TYPE_TEACH)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreCourse_page&type=2"
                    applyUrl="${ctx}/modifyCadreReward?rewardType=1"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="course_reward"/>
        </td>
    </tr>
    <tr>
        <td rowspan="5">
            科研情况
        </td>
        <td>主持科研项目</td>

        <td>
            <c:set var="result" value="${cm:cadreResearchCheck(param.cadreId, CADRE_RESEARCH_TYPE_DIRECT)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreResearch_page&type=${CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY}"
                    applyUrl="${ctx}/modifyCadreResearch?researchType=1"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="research_direct"/>
        </td>
    </tr>
    <tr>
        <td>参与科研项目</td>

        <td>
            <c:set var="result" value="${cm:cadreResearchCheck(param.cadreId, CADRE_RESEARCH_TYPE_IN)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreResearch_page&type=${CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY}"
                    applyUrl="${ctx}/modifyCadreResearch?researchType=2"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="research_in"/>
        </td>
    </tr>
    <tr>
        <td>出版著作</td>

        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'book', 3)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreResearch_page&type=${CADRE_INFO_TYPE_BOOK_SUMMARY}"
                    applyUrl="${ctx}/modifyCadreBook"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="book"/>
        </td>
    </tr>
    <tr>
        <td>发表论文</td>

        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'paper', 3)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreResearch_page&type=${CADRE_INFO_TYPE_PAPER_SUMMARY}"
                    applyUrl="${ctx}/modifyCadrePaper"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="paper"/>
        </td>
    </tr>
    <tr>
        <td>科研成果及获奖</td>

        <td>
            <c:set var="result" value="${cm:cadreRewardCheck(param.cadreId, CADRE_REWARD_TYPE_RESEARCH)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreResearch_page&type=${CADRE_INFO_TYPE_RESEARCH_REWARD}"
                    applyUrl="${ctx}/modifyCadreReward?rewardType=2"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="research_reward"/>
        </td>
    </tr>
    <tr>
        <td>
            其他奖励情况
        </td>
        <td>其他奖励情况</td>

        <td>
            <c:set var="result" value="${cm:cadreRewardCheck(param.cadreId, CADRE_REWARD_TYPE_OTHER)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreReward_page&rewardType=${CADRE_REWARD_TYPE_OTHER}"
                    applyUrl="${ctx}/modifyCadreReward?rewardType=3"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="reward"/>
        </td>
    </tr>

    <tr>
        <td rowspan="2">
            家庭成员信息
        </td>
        <td>家庭成员信息</td>

        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'famliy', 4)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreFamliy_page"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>家庭成员移居国（境）外的情况</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'famliy_abroad', 4)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreFamliy_page"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="famliy_abroad"/>
        </td>
    </tr>
    <tr>
        <td>
            企业、社团兼职
        </td>
        <td>企业、社团兼职</td>

        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'train', 3)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td class="bg-left">
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreCompany"
                    applyUrl="${ctx}/modifyCadreCompany"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="company"/>
        </td>
    </tr>

    </tbody>
</table>
</div>
<style>
    .checkTable thead th {
        text-align: center!important;
        vertical-align: middle!important;
    }
   .checkTable tbody td {
        background-color: #f9f9f9!important;
        text-align: center!important;
        vertical-align: middle!important;
    }
   .checkTable tbody td:nth-last-child(1),.checkTable tbody td:nth-last-child(2) {
        background-color: #fff!important;
    }

    .checkTable tbody td.notExist{
        background-color: #f2dede!important;
    }
</style>
<script>
    stickheader();
    //console.log($("td:contains('否')").length)
    $("td:contains('否')").prev().addClass("notExist");
</script>