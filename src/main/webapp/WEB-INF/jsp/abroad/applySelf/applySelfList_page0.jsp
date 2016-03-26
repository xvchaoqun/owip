<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li  class="<c:if test="${status==0}">active</c:if>">
                        <a href="?status=0"><i class="fa fa-circle-o"></i> 待审批</a>
                    </li>
                    <li  class="<c:if test="${status==1}">active</c:if>">
                        <a href="?status=1"><i class="fa fa-check"></i> 已审批</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
        <div class="myTableDiv"
             data-url-page="${ctx}/applySelfList_page"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="space-4"></div>
            <c:if test="${commonList.recNum>0}">
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>

							<th>申请日期</th>
                            <th>工作证号</th>
                            <th>姓名</th>
                            <th>所在单位及职务</th>
							<th>出行时间</th>
							<th>回国时间</th>
							<th>出行天数</th>
							<th>前往国家或地区</th>
							<th>因私出国（境）事由</th>
							<th>组织部初审</th>
                        <c:forEach items="${approverTypeMap}" var="type">
                            <th>${type.value.name}审批</th>
                        </c:forEach>
							<th>组织部终审</th>

                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${applySelfs}" var="applySelf" varStatus="st">
                        <c:set var="cadre" value="${cadreMap.get(applySelf.cadreId)}"/>
                        <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>
                        <tr>

								<td>${cm:formatDate(applySelf.applyDate,'yyyy-MM-dd')}</td>
                            <td>${sysUser.code}</td>
                            <td>${sysUser.realname}</td>
                            <td>${cadre.title}</td>
								<td>${cm:formatDate(applySelf.startDate,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(applySelf.endDate,'yyyy-MM-dd')}</td>
                            <td>${cm:getDayCountBetweenDate(applySelf.startDate, applySelf.endDate)}</td>
								<td>${applySelf.toCountry}</td>
								<td>${fn:replace(applySelf.reason, '+++', ',')}</td>
                                <wo:approvalTd applySelfId="${applySelf.id}" view="false"/>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <button data-url="${ctx}/applySelf_view?id=${applySelf.id}"
                                            class="openView btn btn-success btn-mini btn-xs">
                                        <i class="fa fa-info-circle"></i> 详情
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <wo:page commonList="${commonList}" uri="${ctx}/applySelfList_page" target="#page-content" pageNum="5"
                         model="3"/>
            </c:if>
            <c:if test="${commonList.recNum==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </div>
    </div>
                </div></div></div>
    <div id="item-content">
    </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    $(".approvalBtn").click(function(){

        loadModal("${ctx}/applySelf_approval?applySelfId="+ $(this).data("id") +"&approvalTypeId="+ $(this).data("approvaltypeid"));
    });

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('[data-rel="select2-ajax"]'));
</script>