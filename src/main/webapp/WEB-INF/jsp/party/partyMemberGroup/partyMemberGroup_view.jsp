<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="buttons pull-right">
    <button class="btn btn-primary btn-mini btn-xs" onclick="_au()">
        <i class="fa fa-users"></i> 添加分党委班子
    </button>
</div>
<h4>&nbsp;</h4>
            <div class="space-4"></div>

                <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>名称</th>
							<th>所属党总支</th>
                        <mytag:sort-th field="tran_time">应换届时间</mytag:sort-th>
                        <mytag:sort-th field="actual_tran_time">实际换届时间</mytag:sort-th>
                        <mytag:sort-th field="appoint_time">任命时间</mytag:sort-th>
							<th>单位发文</th>
                        <%--<shiro:hasPermission name="partyMemberGroup:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <th nowrap>排序</th>
                            </c:if>
                        </shiro:hasPermission>--%>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${partyMemberGroups}" var="partyMemberGroup" varStatus="st">
                        <tr <c:if test="${partyMemberGroup.isPresent}">class="success" </c:if>>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" value="${partyMemberGroup.id}" class="ace">
                                    <span class="lbl"></span>
                                </label>
                            </td>
								<td>
								<c:if test="${partyMemberGroup.isPresent}">
                                    <span class="label label-sm label-primary arrowed-in arrowed-in-right">现任班子</span>
								</c:if>${partyMemberGroup.name}
								</td>
								<td>${partyMap.get(partyMemberGroup.partyId).name}</td>
								<td>${cm:formatDate(partyMemberGroup.tranTime,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(partyMemberGroup.actualTranTime,'yyyy-MM-dd')}</td>
								<td>${cm:formatDate(partyMemberGroup.appointTime,'yyyy-MM-dd')}</td>
								<td >${partyMemberGroup.dispatchUnitId}</td>
                            <%--<shiro:hasPermission name="partyMemberGroup:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${partyMemberGroup.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${partyMemberGroup.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                            </shiro:hasPermission>--%>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="partyMemberGroup:edit">
                                    <button onclick="_au(${partyMemberGroup.id})" class="btn btn-default btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                    <shiro:hasPermission name="partyMember:list">
                                        <button data-id="${partyMemberGroup.id}" class="memberBtn btn btn-primary btn-mini btn-xs">
                                            <i class="fa fa-user"></i> 编辑委员
                                        </button>
                                    </shiro:hasPermission>
                                     <shiro:hasPermission name="partyMemberGroup:del">
                                    <button class="btn btn-danger btn-mini btn-xs" onclick="_del(${partyMemberGroup.id})">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                                <div class="hidden-md hidden-lg">
                                    <div class="inline pos-rel">
                                        <button class="btn btn-mini btn-xser btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
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
                                            <shiro:hasPermission name="partyMemberGroup:edit">
                                            <li>
                                                <a href="#" data-id="${partyMemberGroup.id}" class="editBtn tooltip-success" data-rel="tooltip" title="编辑">
                                                    <span class="green">
                                                        <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                                                    </span>
                                                </a>
                                            </li>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="partyMemberGroup:del">
                                            <li>
                                                <a href="#" data-id="${partyMemberGroup.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
<script>

    function _au(id) {
        url = "${ctx}/partyMemberGroup_au?type=view&partyId=${param.partyId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function _del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/partyMemberGroup_del", {id: id}, function (ret) {
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
        $("#view-box .tab-content").load("${ctx}/partyMemberGroup_view?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
    // 编辑成员
    $(".memberBtn").click(function(){
        loadModal("${ctx}/party_member?id="+$(this).data("id"));
    });

    $('[data-rel="select2"]').select2({width:300});
    $('[data-rel="tooltip"]').tooltip();
</script>