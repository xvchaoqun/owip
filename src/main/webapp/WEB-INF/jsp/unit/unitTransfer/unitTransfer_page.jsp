<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

                <div class="buttons pull-right">
                    <shiro:hasPermission name="unitTransfer:edit">
                    <a class="btn btn-info btn-sm" onclick="_au()"><i class="fa fa-plus"></i> 添加</a>
                    </shiro:hasPermission>
                </div>
            <h4>&nbsp;</h4>
            <div class="space-4"></div>
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
							<th>文件主题</th>
							<th>文件具体内容</th>
							<th>日期</th>
                        <shiro:hasPermission name="unitTransfer:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${unitTransfers}" var="unitTransfer" varStatus="st">
                        <tr>
								<td nowrap>${unitTransfer.subject}</td>
								<td nowrap>${unitTransfer.content}</td>
								<td nowrap>${cm:formatDate(unitTransfer.pubTime,'yyyy-MM-dd')}</td>
                            <shiro:hasPermission name="unitTransfer:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${unitTransfer.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${unitTransfer.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="unitTransfer:edit">
                                    <button onclick="_au(${unitTransfer.id})" class="editBtn btn btn-default btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>

                                        <button onclick="unitTransfer_addDispatchs(${unitTransfer.id})" class="btn btn-primary btn-mini btn-xs">
                                            <i class="fa fa-file-o"></i> 相关发文
                                        </button>
                                     <shiro:hasPermission name="unitTransfer:del">
                                    <button class="btn btn-danger btn-mini btn-xs" onclick="_del(${unitTransfer.id})">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

<script>
    var _id;
    function unitTransfer_addDispatchs(id){
        _id = id;
        loadModal("${ctx}/unitTransfer_addDispatchs?id="+id, 1000);
    }
    function closeSwfPreview(){
            unitTransfer_addDispatchs(_id);
    };

    function _au(id) {
        url = "${ctx}/unitTransfer_au?unitId=${param.unitId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function _del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/unitTransfer_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    function _reload(){
        $("#modal").modal('hide');
        $("#view-box .tab-content").load("${ctx}/unitTransfer_page?${cm:encodeQueryString(pageContext.request.queryString)}");
    }

    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

</script>