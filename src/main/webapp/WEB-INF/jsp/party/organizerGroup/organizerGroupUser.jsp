<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="OW_ORGANIZER_TYPE_SCHOOL" value="<%=OwConstants.OW_ORGANIZER_TYPE_SCHOOL%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${organizerGroup.name}-成员</h3>
</div>
<div class="modal-body">
<shiro:hasPermission name="organizerGroup:edit">
    <form class="form-inline" action="${ctx}/organizerGroupUser" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <div class="form-group">
            <input type="hidden" name="groupId" value="${organizerGroup.id}">
            <select data-rel="select2-ajax"
                    data-ajax-url="${ctx}/organizer_selects?type=${OW_ORGANIZER_TYPE_SCHOOL}"
                    name="userId" data-placeholder="请选择成员">
                <option></option>
            </select>
        </div>
        <input type="button" id="submitBtn" class="btn btn-sm btn-primary" value="添加"/>
    </form>
    <div class="space-10"></div>
</shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/organizerGroupUser?groupId=${organizerGroup.id}"
         data-url-del="${ctx}/organizerGroupUser_del"
         data-url-co="${ctx}/organizerGroupUser_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">姓名</th>
                    <th class="col-xs-5">工号</th>
                    <c:if test="${commonList.recNum>1}">
                        <th style="width: 50px">排序</th>
                    </c:if>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${records}" var="groupUser" varStatus="st">
                    <tr>
                        <c:set var="user" value="${cm:getUserById(groupUser.userId)}"/>
                        <td nowrap><span>${user.realname}</span></td>
                        <td nowrap><span>${user.code}</span></td>
                        <c:if test="${commonList.recNum>1}">
                            <td nowrap>
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${groupUser.id}"
                                   data-callback="_updateGroup" data-direction="1" title="上升"><i
                                        class="fa fa-arrow-up"></i></a>
                                <input type="text" value="1"
                                       class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                       title="修改操作步长">
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-callback="_updateGroup" data-id="${groupUser.id}" data-direction="-1"
                                   title="下降"><i class="fa fa-arrow-down"></i></a></td>
                            </td>
                        </c:if>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="organizerGroup:del">
                                    <button class="delBtn btn btn-danger btn-xs"
                                            data-callback="_updateGroup"
                                            data-id="${groupUser.id}">
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
                                        <shiro:hasPermission name="organizerGroup:del">
                                            <li>
                                                <a href="javascript:;" data-id="${groupUser.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                        <wo:page commonList="${commonList}" uri="${ctx}/organizerGroupUser" target="#modal .modal-content" pageNum="5"
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
    function _updateGroup(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#submitBtn", "#modalForm").click(function(){$("#modalForm").submit();return false;})
    $("#modalForm").validate({
        submitHandler: function (form) {
            var userId = $.trim($("select[name=userId]", form).val());
            //console.log(unitId)
            if(userId==''){
                $.tip({$target:$("#submitBtn", form), at:"top center",my:"bottom center", msg:"请选择成员"});
                return;
            }
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        pop_reload();
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $.register.user_select($('#modal [data-rel="select2-ajax"]'))
</script>