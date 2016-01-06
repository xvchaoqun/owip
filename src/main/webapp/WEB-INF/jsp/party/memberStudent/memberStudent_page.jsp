<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_POLITICAL_STATUS_GROW" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_GROW%>"/>
<c:set var="MEMBER_POLITICAL_STATUS_POSITIVE" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_POSITIVE%>"/>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="tabbable">
            <jsp:include page="/WEB-INF/jsp/party/member/member_menu.jsp"/>

            <div class="tab-content">
                <div id="home4" class="tab-pane in active">

        <div class="myTableDiv"
             data-url-au="${ctx}/memberStudent_au"
             data-url-page="${ctx}/memberStudent_page"
             data-url-del="${ctx}/memberStudent_del"
             data-url-bd="${ctx}/memberStudent_batchDel"
             data-url-co="${ctx}/memberStudent_changeOrder"
             data-querystr="${pageContext.request.queryString}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <input type="hidden" name="cls" value="${cls}">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" onclick="_reset()" class="btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <a href="javascript:;" class="openView btn btn-info btn-sm" data-url="${ctx}/member_au">
                        <i class="fa fa-plus"></i> 添加党员</a>
                    <c:if test="${commonList.recNum>0}">
                    <a class="exportBtn btn btn-success btn-sm tooltip-success"
                       data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i class="fa fa-download"></i> 导出</a>
                    </c:if>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
                            <th>姓名</th>
							<th>学生证号</th>
							<th>性别</th>
							<th>年龄</th>
							<th>学生类别</th>
							<th>年级</th>
							<th>所属组织机构</th>
							<th>入党时间</th>
							<th>转正时间</th>
                            <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberStudents}" var="memberStudent" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberStudent.userId}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>
								<a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId=${memberStudent.userId}">
								${memberStudent.realname}</a></td>
								<td>${memberStudent.code}</td>
								<td>${GENDER_MALE_MAP.get(memberStudent.gender)}</td>
								<td>${cm:intervalYearsUntilNow(memberStudent.birth)}</td>
								<td>${memberStudent.type}</td>
								<td>${memberStudent.grade}</td>
                                <td>${partyMap.get(memberStudent.partyId).name}
                                    <c:if test="${not empty memberStudent.branchId}">
                                        -${branchMap.get(memberStudent.branchId).name}
                                    </c:if></td>
                                <td>${cm:formatDate(memberStudent.growTime,'yyyy-MM-dd')}</td>
                                <td>
                                    <%--<c:if test="${memberStudent.politicalStatus == MEMBER_POLITICAL_STATUS_GROW}">
                                        未转正
                                    </c:if>
                                    <c:if test="${memberStudent.politicalStatus == MEMBER_POLITICAL_STATUS_POSITIVE}">--%>
                                        ${cm:formatDate(memberStudent.positiveTime,'yyyy-MM-dd')}
                                   <%-- </c:if>--%>
                                </td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">

                                    <button class="openView btn btn-mini"
                                            data-url="${ctx}/member_au?userId=${memberStudent.userId}">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>

                                     <shiro:hasPermission name="memberStudent:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${memberStudent.userId}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                            <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                        </button>

                                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                            <%--<li>
                                            <a href="#" class="tooltip-info" data-rel="tooltip" title="查看">
                                                        <span class="blue">
                                                            <i class="ace-icon fa fa-search-plus bigger-120"></i>
                                                        </span>
                                            </a>
                                        </li>--%>
                                            <shiro:hasPermission name="memberStudent:edit">
                                            <li>
                                                <a href="#" data-id="${memberStudent.userId}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="memberStudent:del">
                                            <li>
                                                <a href="#" data-id="${memberStudent.userId}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
                                                    <span class="red">
                                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                        </ul>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <wo:page commonList="${commonList}" uri="${ctx}/memberStudent_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
                    </div></div></div>
</div>
        <div id="item-content"></div>
    </div>
</div>
<script>

    function _reset(){

        _tunePage(1, "", "${ctx}/memberStudent_page", "#page-content", "", "&cls=${cls}");
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#searchForm select[name=userId]'));
</script>