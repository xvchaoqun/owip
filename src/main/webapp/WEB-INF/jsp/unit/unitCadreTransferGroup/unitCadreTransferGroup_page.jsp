<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
                <div class="buttons pull-right">
                    <shiro:hasPermission name="unitCadreTransferGroup:edit">
                    <a class="btn btn-info btn-sm" onclick="_au()"><i class="fa fa-plus"></i> 添加项目</a>
                    </shiro:hasPermission>
                </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
                <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
							<th>项目名称</th>
                        <shiro:hasPermission name="unitCadreTransferGroup:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${unitCadreTransferGroups}" var="unitCadreTransferGroup" varStatus="st">
                        <tr>

								<td nowrap>${unitCadreTransferGroup.name}</td>
                            <shiro:hasPermission name="unitCadreTransferGroup:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${unitCadreTransferGroup.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${unitCadreTransferGroup.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="unitCadreTransferGroup:edit">
                                    <button onclick="_au(${unitCadreTransferGroup.id})" class="btn btn-mini">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                    <button onclick="unitCadreTransfer_page(${unitCadreTransferGroup.id})" class="btn btn-mini btn-warning">
                                        <i class="fa fa-th-list"></i> 编辑任职列表
                                    </button>
                                     <shiro:hasPermission name="unitCadreTransferGroup:del">
                                    <button class="btn btn-danger btn-mini" onclick="_del(${unitCadreTransferGroup.id})">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                                
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:if test="${!empty commonList && commonList.pageNum>1 }">
                    <div class="row my_paginate_row">
                        <div class="col-xs-6">第${commonList.startPos}-${commonList.endPos}条&nbsp;&nbsp;共${commonList.recNum}条记录</div>
                        <div class="col-xs-6">
                            <div class="my_paginate">
                                <ul class="pagination">
                                    <wo:page commonList="${commonList}" uri="${ctx}/unitCadreTransferGroup_page" target="#view-box .tab-content" pageNum="5"
                                             model="3"/>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:if>
<script>

    function unitCadreTransfer_page(groupId){
        var url = "${ctx}/unitCadreTransfer_page?groupId="+groupId;
        loadModal(url, 1000);
    }

    function _au(id) {
        var url = "${ctx}/unitCadreTransferGroup_au?unitId=${param.unitId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function _del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/unitCadreTransferGroup_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/unitCadreTransferGroup_page?${pageContext.request.queryString}");
    }
    
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>