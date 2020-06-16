<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="cetUnitTraintDiv">
    <div class="modal-header">
        <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
        <h3>第一步：请选择培训项目</h3>
    </div>
    <div class="modal-body">
        <form class="form-inline search-form" id="searchForm_popup" style="float: left">
            <input type="hidden" name="userId" value="${param.userId}">
            <div class="form-group">
                <label>培训项目名称</label>
                <input class="form-control search-query" name="projectName" type="text" value="${param.projectName}"
                       placeholder="请输入培训项目名称">
            </div>
            <c:set var="_query" value="${not empty param.projectName}"/>
            <div class="form-group">
                <button type="button" data-url="${ctx}/user/cet/cetUnitTrain_list"
                        data-target="#cetUnitTraintDiv" data-form="#searchForm_popup"
                        class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
                </button>
                <c:if test="${_query}">
                    <button type="button"
                            data-url="${ctx}/user/cet/cetUnitTrain_list"
                            data-querystr="id=${param.userId}"
                            data-target="#cetUnitTraintDiv"
                            class="reloadBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
            </div>
        </form>
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th class="center"></th>
                <th nowrap>培训项目名称</th>
                <th nowrap>培训班主办方</th>
                <th nowrap>培训开始时间</th>
                <th nowrap>培训结束时间</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${cetUnitProjects}" var="cetUnitProject" varStatus="st">
                <tr>
                    <td class="center" style="width: 10px">
                        <label class="pos-rel">
                            <input type="radio" name="cetUnitProjectId"
                                   value="${cetUnitProject.id}"
                                   class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                            <span class="lbl"></span>
                        </label>
                    </td>
                    <td nowrap width="350px">${cetUnitProject.projectName}</td>
                    <td nowrap width="200px">${cm:displayParty(cetUnitProject.cetParty.partyId, null)}</td>
                    <td nowrap width="60px">${cm:formatDate(cetUnitProject.startDate, 'yyyy.MM.dd')}</td>
                    <td nowrap width="60px">${cm:formatDate(cetUnitProject.endDate, 'yyyy.MM.dd')}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${!empty commonList && commonList.pageNum>1}">
            <wo:page commonList="${commonList}" uri="${ctx}/user/cet/cetUnitTrain_list?projectName=${param.projectName}&userId=${param.userId}"
                     target="#cetUnitTraintDiv"
                     pageNum="5"
                     model="3"/>
        </c:if>
    </div>
    <shiro:hasPermission name="userCetUnitTrain:edit">
        <div class="modal-footer">
            <button onclick="reRecordTrain()" class="btn btn-primary"> 下一步</button>
        </div>
    </shiro:hasPermission>
</div>
<script>
    function reRecordTrain(){
        var projectId = $("input[type=radio]:checked").val();
        if (projectId == undefined || projectId == null){
            SysMsg.info('请选择培训项目', '提示');
            return;
        }
        //console.log(projectId)
        //$("#cetUnitTraintDiv").modal("hide");
        $.loadModal("${ctx}/user/cet/cetUnitTrain_au?reRecord=1&userId=${userId}&projectId=" + projectId);
    }
</script>