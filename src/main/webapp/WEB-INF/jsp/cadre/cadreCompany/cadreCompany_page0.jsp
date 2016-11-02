<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>


                <div class="vspace-12"></div>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="cadreCompany:edit">
                    <a class="btn btn-info btn-sm" onclick="_au()"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                </div>
                <h4>&nbsp;</h4>
            <div class="space-4"></div>
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
							<th>兼职起始时间</th>
							<th>兼职单位及职务</th>
                        <shiro:hasPermission name="cadreCompany:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap class="hidden-480">排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cadreCompanys}" var="cadreCompany" varStatus="st">
                        <tr>
								<td>${cm:formatDate(cadreCompany.startTime,'yyyy-MM-dd')}</td>
								<td>${cadreCompany.unit}</td>
                            <shiro:hasPermission name="cadreCompany:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td class="hidden-480">
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreCompany.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${cadreCompany.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="cadreCompany:edit">
                                    <button onclick="_au(${cadreCompany.id})" class="btn btn-default btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="cadreCompany:del">
                                    <button class="btn btn-danger btn-mini btn-xs" onclick="_del(${cadreCompany.id})">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
<script>
    function _au(id) {
        url = "${ctx}/cadreCompany_au?cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }
    function _del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreCompany_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/cadreCompany_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>