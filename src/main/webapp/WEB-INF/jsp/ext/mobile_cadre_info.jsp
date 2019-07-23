<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="pi" class="tab-pane">
    <div class="profile-user-info profile-user-info-striped">
        <div class="profile-info-row">
            <div class="profile-info-name td"> 所在单位</div>
            <div class="profile-info-value td">
                <span class="editable">${uv.unit}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 编制类别</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.authorizedType}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 人员分类</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.staffType}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 人员状态</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.staffStatus}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 在岗情况</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.onJob}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 人事转否</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.personnelStatus}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 岗位类别</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.postClass}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 岗位子类别</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.subPostClass}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 主岗等级</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.mainPostLevel}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 工龄起算日期</div>
            <div class="profile-info-value td">
                <span class="editable">${cm:formatDate(teacherInfo.workStartTime, "yyyy.MM.dd")}</span>
            </div>
        </div>

        <div class="profile-info-row">
            <div class="profile-info-name td"> 到校时间</div>
            <div class="profile-info-value td">
                <span class="editable">${cm:formatDate(teacherInfo.arriveTime, "yyyy.MM.dd")}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 参加工作时间</div>
            <div class="profile-info-value td">
                <span class="editable">${cm:formatDate(teacherInfo.workTime, "yyyy.MM")}</span>
            </div>
        </div>

        <div class="profile-info-row">
            <div class="profile-info-name td"> 人才/荣誉称号</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.talentTitle}</span>
            </div>
        </div>

    </div>
</div>
<div id="title" class="tab-pane">
    <div class="profile-user-info profile-user-info-striped">

        <div class="profile-info-row">
            <div class="profile-info-name"> 专 技 岗 位</div>
        </div>

        <div class="profile-info-row">
            <div class="profile-info-name td"> 专业技术职务</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.proPost}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 专技职务评定时间</div>
            <div class="profile-info-value td">
                <span class="editable">${cm:formatDate(teacherInfo.proPostTime, _p_proPostTimeFormat)}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 专技职务等级</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.proPostLevel}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 专技职务分级时间</div>
            <div class="profile-info-value td">
                <span class="editable">${cm:formatDate(teacherInfo.proPostLevelTime, "yyyy.MM")}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name"> 管 理 岗 位</div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 管理岗位等级</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.manageLevel}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 管理岗位分级时间</div>
            <div class="profile-info-value td">
                <span class="editable">${cm:formatDate(teacherInfo.manageLevelTime, "yyyy.MM")}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name"> 工 勤 岗 位</div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 工勤岗位等级</div>
            <div class="profile-info-value td">
                <span class="editable">${teacherInfo.officeLevel}</span>
            </div>
        </div>
        <div class="profile-info-row">
            <div class="profile-info-name td"> 工勤岗位分级时间</div>
            <div class="profile-info-value td">
                <span class="editable">${cm:formatDate(teacherInfo.officeLevelTime, "yyyy.MM")}</span>
            </div>
        </div>
    </div>
</div>
