<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
        <div class="myTableDiv"
             data-url-au="${ctx}/user/applySelf_au"
             data-url-page="${ctx}/user/applySelf_page"
             data-url-del="${ctx}/user/applySelf_del"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <mytag:sort-form css="form-inline hidden-sm hidden-xs" id="searchForm">
                <div class="vspace-12"></div>
                <div class="buttons">
                    <a class="openView btn btn-success btn-sm" data-url="${ctx}/user/applySelf_au"><i class="fa fa-plus"></i> 申请因私出国（境）</a>
                    <a id="note" class="btn btn-info btn-sm"><i class="fa fa-info-circle"></i> 申请说明</a>
                </div>
            </mytag:sort-form>
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
							<th>编号</th>
							<th>申请日期</th>
							<th>出行时间</th>
							<th>出发时间</th>
							<th>返回时间</th>
							<th>出行天数</th>
							<th>前往国家或地区</th>
							<th>事由</th>
                            <th>组织部初审</th>
                            <c:forEach items="${approverTypeMap}" var="type">
                                <th>${type.value.name}审批</th>
                            </c:forEach>
                            <th>组织部终审</th>
                        <shiro:hasPermission name="applySelf:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${applySelfs}" var="applySelf" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(applySelf.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>
                            <td>S${applySelf.id}</td>
								<td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
								<td>${APPLY_SELF_DATE_TYPE_MAP.get(applySelf.type)}</td>
								<td>${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}</td>
								<td>${cm:getDayCountBetweenDate(applySelf.startDate, applySelf.endDate)}</td>
								<td>${applySelf.toCountry}</td>
								<td>${fn:replace(applySelf.reason, '+++', ',')}</td>
                                <wo:approvalTd applySelfId="${applySelf.id}" view="true"/>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <button class="openView btn btn-success btn-mini"
                                            data-url="${ctx}/user/applySelf_view?id=${applySelf.id}">
                                        <i class="fa fa-info-circle"></i> 详情
                                    </button>
                                    <c:set var="firstStatus" value="${cm:getAdminFirstTrialStatus(applySelf.id)}"/>
                                    <c:if test="${firstStatus==null || firstStatus==0}"> <!--没有经过审批获审批不通过，可以重新提交-->
                                        <button class="openView btn btn-primary btn-mini"
                                                data-url="${ctx}/user/applySelf_au?id=${applySelf.id}&edit=1">
                                            <i class="fa fa-edit"></i> 修改提交
                                        </button>
                                    </c:if>
                                    <c:if test="${firstStatus==null}"> <!--没有经过审批才可以删除-->
                                        <button class="delBtn btn btn-danger btn-mini" data-id="${applySelf.id}">
                                            <i class="fa fa-times"></i> 删除
                                        </button>
                                    </c:if>

                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <wo:page commonList="${commonList}" uri="${ctx}/applySelf_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
        </div>
        <div id="item-content">

        </div>
    </div>
</div>
<script>

    $("#note").click(function(){
        loadModal("${ctx}/user/applySelf_note", 800);
    });
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>