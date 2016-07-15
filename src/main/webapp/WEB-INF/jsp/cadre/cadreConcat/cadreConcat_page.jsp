<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:if test="${fn:length(cadreConcats)==0}">
                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="cadreConcat:edit">
                    <a class="btn btn-info btn-sm" onclick="_au()"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
    </c:if>
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
							<th>手机号</th>
							<th>办公电话</th>
							<th>家庭电话</th>
							<th>电子邮箱</th>

                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cadreConcats}" var="cadreConcat" varStatus="st">
                        <tr>

								<td>${cadreConcat.mobile}</td>
								<td>${cadreConcat.officePhone}</td>
								<td>${cadreConcat.homePhone}</td>
								<td>${cadreConcat.email}</td>

                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="cadreConcat:edit">
                                    <button onclick="_au()" class=" btn btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                    <%-- <shiro:hasPermission name="cadreConcat:del">
                                    <button class="btn btn-danger btn-mini btn-xs" onclick="_del(${cadreConcat.cadreId})">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                      </shiro:hasPermission>--%>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
<script>

    function _au() {
        loadModal("${ctx}/cadreConcat_au?cadreId=${param.cadreId}");
    }

    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/cadreConcat_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>