<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=ScConstants.SC_RECORD_STATUS_MAP%>" var="SC_RECORD_STATUS_MAP"/>
<div id="postListDiv">
    <c:if test="${commonList.recNum>0}">
        <table class="table table-center table-striped table-bordered table-hover2">
            <thead>
            <tr>
                <th nowrap>年份</th>
                <th nowrap>选任岗位</th>
                <th nowrap>分管工作</th>
                <th nowrap>所属纪实</th>
                <th nowrap>纪实状态</th>
                <th nowrap>选任启动日期</th>
                <%--<th nowrap>选拔任用方式</th>--%>
                <th style="width: 80px"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${records}" var="record" varStatus="st">
                <tr>
                    <td>${record.year}</td>
                    <td>${record.postName}</td>
                    <td>${record.job}</td>
                    <td>${record.code}</td>
                    <td>${SC_RECORD_STATUS_MAP.get(record.status)}</td>
                    <td>${cm:formatDate(record.holdDate, "yyyy.MM.dd")}</td>
                    <%--<td>${scRecord.scType}</td>--%>
                    <td nowrap>
                        <div class="hidden-sm hidden-xs action-buttons">
                            <shiro:hasPermission name="drVoterTypeTpl:edit">
                                <button onclick="_drOffline_selectPost(${record.id}, '${record.postName}')"
                                class="btn btn-success btn-xs">
                                    <i class="fa fa-check"></i> 选择
                                </button>
                            </shiro:hasPermission>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${!empty commonList && commonList.pageNum>1 }">
            <wo:page commonList="${commonList}" uri="${ctx}/drOffline_selectPost" target="#postListDiv"
                     op="replace"
                     pageNum="5"
                     model="3"/>
        </c:if>
    </c:if>
    <c:if test="${commonList.recNum==0}">
        <div class="well well-lg center">
            <h4 class="green lighter">暂无记录</h4>
        </div>
    </c:if>
</div>