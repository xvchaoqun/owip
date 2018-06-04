<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH%>" var="CADRE_INFO_TYPE_RESEARCH"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY%>" var="CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY%>" var="CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_RESEARCH_REWARD%>" var="CADRE_INFO_TYPE_RESEARCH_REWARD"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_PAPER_SUMMARY%>" var="CADRE_INFO_TYPE_PAPER_SUMMARY"/>
<c:set value="<%=CadreConstants.CADRE_INFO_TYPE_BOOK_SUMMARY%>" var="CADRE_INFO_TYPE_BOOK_SUMMARY"/>

<c:set var="CADRE_REWARD_TYPE_OTHER" value="<%=CadreConstants.CADRE_REWARD_TYPE_OTHER%>"/>
<c:set var="CADRE_REWARD_TYPE_RESEARCH" value="<%=CadreConstants.CADRE_REWARD_TYPE_RESEARCH%>"/>
<c:set var="CADRE_REWARD_TYPE_TEACH" value="<%=CadreConstants.CADRE_REWARD_TYPE_TEACH%>"/>
<c:set var="CADRE_RESEARCH_TYPE_DIRECT" value="<%=CadreConstants.CADRE_RESEARCH_TYPE_DIRECT%>"/>
<c:set var="CADRE_RESEARCH_TYPE_IN" value="<%=CadreConstants.CADRE_RESEARCH_TYPE_IN%>"/>

<c:set value="<%=CadreConstants.CADRE_INFO_CHECK_RESULT_NOT_EXIST%>" var="CADRE_INFO_CHECK_RESULT_NOT_EXIST"/>
<c:set value="<%=CadreConstants.CADRE_INFO_CHECK_RESULT_EXIST%>" var="CADRE_INFO_CHECK_RESULT_EXIST"/>
<c:set value="<%=CadreConstants.CADRE_INFO_CHECK_RESULT_MAP%>" var="CADRE_INFO_CHECK_RESULT_MAP"/>

<c:set var="toEdit" value="${hasDirectModifyCadreAuth || cm:isPermitted(PERMISSION_CADREADMIN)}"/>
<c:set var="moudleBase" value="${param._auth=='self'?100:0}"/>
<div class="tabbable" style="margin: 0px 20px; width: 900px">
    <div class="space-4"></div>
    <table class="checkTable table table-bordered table-unhover2" data-offset-top="101">
    <thead>
    <tr>
        <th width="150">类别</th>
        <th width="300">具体内容</th>
        <th>完整性校验结果</th>
        <th width="260">操作</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <c:set var="baseRowspan" value="10"/>
        <c:if test="${cm:hasRole(ROLE_CADRERECRUIT)}">
        <c:set var="baseRowspan" value="11"/>
        </c:if>
        <td rowspan="${baseRowspan}">
            基本信息
        </td>
        <td>照片</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'avatar', 1)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
            <c:if test="${not empty sysUser.avatar && result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}">
                <input type="checkbox" data-name="avatar" name="check" class="cadre-info-check"> 已确认为近期标准证件照
            </c:if>
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
<c:if test="${cm:hasRole(ROLE_CADRERECRUIT)}">
    <tr>
        <td>所在单位及职务</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, 'title', 7)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_base_edit notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}" toEdit="${toEdit}"/>
        </td>
    </tr>
    </c:if>
    <tr>
        <td rowspan="3">
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
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_EDU}"
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
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_EDU}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>硕士和博士导师信息</td>
        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, null, 9)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreEdu_page"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_EDU}"
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
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_WORK}"
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
            <c:set var="updateName" value="post_pro"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, updateName, 3)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadrePostInfo_page&type=1"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_POSTPRO}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}"
                    updateName="${updateName}"/>
        </td>
    </tr>
    <tr>
        <td>管理岗位过程信息</td>
        <td>
            <c:set var="updateName" value="post_admin"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, updateName, 3)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadrePostInfo_page&type=2"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_POSTADMIN}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}"
                    updateName="${updateName}"/>
        </td>
    </tr>
    <tr>
        <td>工勤岗位过程信息</td>
        <td>
            <c:set var="updateName" value="post_work"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, updateName, 3)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadrePostInfo_page&type=3"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_POSTWORK}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}"
                    updateName="${updateName}"/>
        </td>
    </tr>


    <tr>
        <td>
            社会或学术兼职
        </td>
        <td>社会或学术兼职</td>
        <td>
            <c:set var="updateName" value="parttime"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, updateName, 3)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreParttime_page"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_PARTTIME}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>
    <tr>
        <td>
            培训情况
        </td>
        <td>培训情况</td>
        <td>
            <c:set var="updateName" value="train"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, updateName, 3)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreTrain_page"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_TRAIN}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>

    <tr>
        <td rowspan="2">
            教学情况
        </td>
        <td>承担本、硕、博课程情况</td>

        <td>
            <c:set var="updateName" value="course"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, updateName, 3)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreCourse_page&type=1"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_COURSE}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>
    <tr>
        <td>教学成果及获奖情况</td>

        <td>
            <c:set var="updateName" value="course_reward"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreRewardCheck(param.cadreId, CADRE_REWARD_TYPE_TEACH)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreCourse_page&type=2"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_TEACH}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>
    <tr>
        <td rowspan="5">
            科研情况
        </td>
        <td>主持科研项目</td>

        <td>
            <c:set var="updateName" value="research_direct"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreResearchCheck(param.cadreId, CADRE_RESEARCH_TYPE_DIRECT)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreResearch_page&type=${CADRE_INFO_TYPE_RESEARCH_DIRECT_SUMMARY}"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_DIRECT}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>
    <tr>
        <td>参与科研项目</td>

        <td>
            <c:set var="updateName" value="research_in"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreResearchCheck(param.cadreId, CADRE_RESEARCH_TYPE_IN)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreResearch_page&type=${CADRE_INFO_TYPE_RESEARCH_IN_SUMMARY}"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_RESEARCH_IN}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>
    <tr>
        <td>出版著作</td>

        <td>
            <c:set var="updateName" value="book"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, updateName, 3)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreResearch_page&type=${CADRE_INFO_TYPE_BOOK_SUMMARY}"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_BOOK}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>
    <tr>
        <td>发表论文</td>

        <td>
            <c:set var="updateName" value="paper"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, updateName, 3)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreResearch_page&type=${CADRE_INFO_TYPE_PAPER_SUMMARY}"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_PAPER}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>
    <tr>
        <td>科研成果及获奖</td>

        <td>
            <c:set var="updateName" value="research_reward"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreRewardCheck(param.cadreId, CADRE_REWARD_TYPE_RESEARCH)}"/>
           </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreResearch_page&type=${CADRE_INFO_TYPE_RESEARCH_REWARD}"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_RESEARCH}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>
    <tr>
        <td>
            其他奖励情况
        </td>
        <td>其他奖励情况</td>

        <td>
            <c:set var="updateName" value="reward"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreRewardCheck(param.cadreId, CADRE_REWARD_TYPE_OTHER)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreReward_page&rewardType=${CADRE_REWARD_TYPE_OTHER}"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_REWARD_OTHER}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>

    <tr>
        <td rowspan="2">
            家庭成员信息
        </td>
        <td>家庭成员信息</td>

        <td>
            <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, null, 8)}"/>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreFamily_page"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILY}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}"/>
        </td>
    </tr>
    <tr>
        <td>家庭成员移居国（境）外的情况</td>
        <td>
            <c:set var="updateName" value="family_abroad"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, updateName, 4)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td>
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreFamily_page"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_FAMILYABROAD}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>
    <tr>
        <td>
            企业、社团兼职
        </td>
        <td>企业、社团兼职</td>

        <td>
            <c:set var="updateName" value="company"/>
            <c:set var="result" value="${CADRE_INFO_CHECK_RESULT_EXIST}"/>
            <c:if test="${cm:canUpdate(param.cadreId, updateName)}">
                <c:set var="result" value="${cm:cadreInfoCheck(param.cadreId, updateName, 4)}"/>
            </c:if>
            ${CADRE_INFO_CHECK_RESULT_MAP.get(result)}
        </td>
        <td class="bg-left">
            <t:cadre_info_edit
                    editUrl="?cadreId=${param.cadreId}&to=cadreCompany"
                    applyUrl="${ctx}/modifyTableApply?module=${moudleBase+moudleBase+MODIFY_TABLE_APPLY_MODULE_CADRE_COMPANY}"
                    notExist="${result==CADRE_INFO_CHECK_RESULT_NOT_EXIST}"
                    toEdit="${toEdit}" updateName="${updateName}"/>
        </td>
    </tr>

    </tbody>
</table>
</div>
<style>
    .table thead th {
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