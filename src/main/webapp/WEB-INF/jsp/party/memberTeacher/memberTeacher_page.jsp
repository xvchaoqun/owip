<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="tabbable">
            <jsp:include page="/WEB-INF/jsp/party/member/member_menu.jsp"/>

            <div class="tab-content">
                <div id="home4" class="tab-pane in active">

        <div class="myTableDiv"
             data-url-au="${ctx}/memberTeacher_au"
             data-url-page="${ctx}/memberTeacher_page"
             data-url-del="${ctx}/memberTeacher_del"
             data-url-bd="${ctx}/memberTeacher_batchDel"
             data-url-co="${ctx}/memberTeacher_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <input type="hidden" name="cls" value="${cls}">

                <select  class="form-control" data-rel="select2-ajax"
                         data-ajax-url="${ctx}/member_selects?type=${MEMBER_TYPE_TEACHER}&status=${MEMBER_STATUS_NORMAL}"
                         name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option value="${sysUser.id}">${sysUser.realname}</option>
                </select>
                <a class="searchBtn btn btn-sm"><i class="fa fa-search"></i> 查找</a>
                <c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
                <c:if test="${_query}">
                    <button type="button" class="resetBtn btn btn-warning btn-sm" data-querystr="cls=${cls}">
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
							<th>工作证号</th>
							<th>性别</th>
							<th>年龄</th>
							<th>最高学历</th>
							<th>岗位类别</th>
							<th>专业技术职务</th>
							<th>所属组织机构</th>
							<th>入党时间</th>
							<th>联系手机</th>
                            <c:if test="${cls>=3}">
                            <th>退休时间</th>
                            <th>是否离休</th>
                            </c:if>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${memberTeachers}" var="memberTeacher" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${memberTeacher.userId}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                            <td>
                                <a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId=${memberTeacher.userId}">
                            ${memberTeacher.realname}
                            </a>
                            </td>
                            <td>${memberTeacher.code}</td>
                            <td>${GENDER_MAP.get(memberTeacher.gender)}</td>
                            <td>${cm:intervalYearsUntilNow(memberTeacher.birth)}</td>
                            <td>${memberTeacher.education}</td>
                            <td>${memberTeacher.postClass}</td>
                            <td>${memberTeacher.proPost}</td>
                            <td>${partyMap.get(memberTeacher.partyId).name}
                                <c:if test="${not empty memberTeacher.branchId}">
                                    -${branchMap.get(memberTeacher.branchId).name}
                                </c:if></td>
							<td>${cm:formatDate(memberTeacher.growTime,'yyyy-MM-dd')}</td>
							<td>${memberTeacher.mobile}</td>
                            <c:if test="${cls>=3}">
                                <td>${cm:formatDate(memberTeacher.retireTime,'yyyy-MM-dd')}</td>
                                <td>${memberTeacher.isHonorRetire?"是":"否"}</td>
                            </c:if>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">

                                    <button class="openView btn btn-mini"
                                            data-url="${ctx}/member_au?userId=${memberTeacher.userId}">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                    <c:if test="${cls==4}">
                                        <c:set var="retireApply" value="${cm:getRetireApply(memberTeacher.userId)}"/>
                                        <c:if test="${retireApply.status!=0}">
                                        <button onclick="_retireApply(${memberTeacher.userId})" class="btn btn-mini">
                                            <i class="fa fa-edit"></i> 提交党员退休
                                        </button>
                                        </c:if>
                                        <c:if test="${retireApply.status==0}">
                                            <button onclick="_retireApply(${memberTeacher.userId})" class="btn btn-success btn-mini">
                                                <i class="fa fa-check"></i> 审核党员退休
                                            </button>
                                        </c:if>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <wo:page commonList="${commonList}" uri="${ctx}/memberTeacher_page" target="#page-content" pageNum="5"
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

    function _retireApply(userId){

        loadModal("${ctx}/retireApply?userId="+userId);
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#searchForm select[name=userId]'));
</script>