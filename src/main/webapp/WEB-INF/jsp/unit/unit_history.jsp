<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${unit.name}-历史单位</h3>
</div>
<div class="modal-body">
<shiro:hasPermission name="historyUnit:edit">

    <form class="form-inline" action="${ctx}/historyUnit_au" id="modalForm" method="post">
        <div class="form-group">
            <input type="hidden" name="unitId" value="${unit.id}">
            <select data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects?status=2"
                    name="oldUnitId" data-placeholder="请选择历史单位">
                <option></option>
            </select>
        </div>
        <input type="button" id="submitBtn" class="btn btn-sm btn-primary" value="添加"/>
    </form>
    <div class="space-10"></div>
</shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/unit_history?id=${unit.id}"
         data-url-del="${ctx}/historyUnit_del"
         data-url-co="${ctx}/historyUnit_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-10">历史单位</th>
                    <shiro:hasPermission name="historyUnit:changeOrder">
                        <c:if test="${!_query && commonList.recNum>1}">
                            <th nowrap>排序</th>
                        </c:if>
                    </shiro:hasPermission>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${historyUnits}" var="historyUnit" varStatus="st">
                    <tr>
                        <td nowrap>${unitMap.get(historyUnit.oldUnitId).name}</td>
                         <shiro:hasPermission name="historyUnit:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="javascript:;" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${historyUnit.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="javascript:;" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${historyUnit.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                        </shiro:hasPermission>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="historyUnit:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${historyUnit.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                            <div class="hidden-md hidden-lg">
                                <div class="inline pos-rel">
                                    <button class="btn btn-xser btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                        <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                    </button>

                                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                        <shiro:hasPermission name="historyUnit:del">
                                            <li>
                                                <a href="javascript:;" data-id="${historyUnit.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                    <c:if test="${!empty commonList && commonList.pageNum>1 }">
                        <wo:page commonList="${commonList}" uri="${ctx}/historyUnit" target="#modal .modal-content" pageNum="5"
                                 model="3"/>
                    </c:if>
        </c:if>
        <c:if test="${commonList.recNum==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">暂无记录</h4>
            </div>
        </c:if>
    </div>
</div>
<script>
    $("#submitBtn", "#modalForm").click(function(){$("#modalForm").submit();return false;})
    $("#modal form").validate({
        submitHandler: function (form) {
            var oldUnitId = $.trim($("select[name=oldUnitId]", form).val());
            if(oldUnitId==''){
                $.tip({$target:$("select[name=oldUnitId]", form).closest("div").find(".select2-container"),
                    at:"top center",my:"bottom center", msg:"请选择历史单位"});
                return;
            }

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        pop_reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $.register.ajax_select($('#modal [data-rel="select2-ajax"]'))
</script>