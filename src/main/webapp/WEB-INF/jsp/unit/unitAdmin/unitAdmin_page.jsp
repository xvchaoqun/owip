<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>成员列表 <div class="buttons pull-right">
        <shiro:hasPermission name="unitCadreTransfer:edit">
            <a class="btn btn-info btn-sm" onclick="unitAdmin_au()"><i class="fa fa-plus"></i> 添加</a>
        </shiro:hasPermission>
    </div></h3>
</div>
<div class="modal-body">
            <c:if test="${fn:length(unitAdmins)>0}">
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
							<th>成员</th>
							<th>职务属性</th>
                        <th nowrap>排序</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${unitAdmins}" var="unitAdmin" varStatus="st">
                        <tr>
								<td>${cm:getUserById(cadreMap.get(unitAdmin.cadreId).userId).realname}</td>
                                <td>${postTypeMap.get(unitAdmin.postId).name}</td>
                                <td>
                                    <a href="#" class="changeOrderBtn" data-id="${unitAdmin.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" class="changeOrderBtn" data-id="${unitAdmin.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            <td>
                                <div class="hidden-sm hidden-xs action-buttons">
                                    <shiro:hasPermission name="unitAdmin:edit">
                                    <button onclick="unitAdmin_au(${unitAdmin.id})" class="editBtn btn btn-default btn-mini btn-xs">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                     </shiro:hasPermission>
                                     <shiro:hasPermission name="unitAdmin:del">
                                    <button class="btn btn-danger btn-mini btn-xs" onclick="unitAdmin_del(${unitAdmin.id})">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                      </shiro:hasPermission>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${fn:length(unitAdmins)==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
<script>

    $("#modal .changeOrderBtn").click(function(){

        var id = $(this).data("id");
        var direction = parseInt($(this).data("direction"));
        var step = $(this).closest("td").find("input").val();
        var addNum = (parseInt(step)||1)*direction;
        $.post("${ctx}/unitAdmin_changeOrder",{id:id, addNum:addNum},function(ret){
            if(ret.success) {
                unitAdmin_page("${param.groupId}");
                SysMsg.success('操作成功。', '成功');
            }
        });
    });

    function unitAdmin_au(id) {
        var url = "${ctx}/unitAdmin_au?groupId=${param.groupId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function unitAdmin_del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/unitAdmin_del", {id: id}, function (ret) {
                    if (ret.success) {
                        unitAdmin_page("${param.groupId}");
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }
        });
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>