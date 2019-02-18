<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cm:getUserById(leader.userId).realname}-联系单位</h3>
</div>
<div class="modal-body">
<shiro:hasPermission name="leaderUnit:edit">

    <form class="form-inline" action="${ctx}/leaderUnit_au" id="modalForm" method="post">
        <div class="form-group">
            <input type="hidden" name="userId" value="${leader.userId}">
            <select data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects?status=1"
                    name="unitId" data-placeholder="请选择单位">
                <option></option>
            </select>
            <select data-rel="select2" name="typeId" data-placeholder="请选择类别">
                <option></option>
                <c:import url="/metaTypes?__code=mc_leader_unit"/>
            </select>
        </div>
        <input type="button" id="submitBtn" class="btn btn-sm btn-primary" value="添加"/>
    </form>
    <div class="space-10"></div>
</shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/leader_unit?id=${leader.id}"
         data-url-del="${ctx}/leaderUnit_del"
         data-url-co="${ctx}/leaderUnit_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">联系单位</th>
                    <th class="col-xs-5">类别</th>
                    <c:if test="${commonList.recNum>1}">
                        <th style="width: 50px">排序</th>
                    </c:if>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${leaderUnits}" var="leaderUnit" varStatus="st">
                    <tr>
                        <c:set var="unit" value="${unitMap.get(leaderUnit.unitId)}"/>
                        <td nowrap><span class="${unit.status==UNIT_STATUS_HISTORY?'delete':''}">${unit.name}</span></td>
                        <td nowrap>${cm:getMetaType(leaderUnit.typeId).name}</td>
                        <c:if test="${commonList.recNum>1}">
                            <td nowrap>
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${leaderUnit.id}" data-direction="1" title="上升"><i
                                        class="fa fa-arrow-up"></i></a>
                                <input type="text" value="1"
                                       class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                       title="修改操作步长">
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${leaderUnit.id}" data-direction="-1"
                                   title="下降"><i class="fa fa-arrow-down"></i></a></td>
                            </td>
                        </c:if>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="leaderUnit:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${leaderUnit.id}">
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
                                        <shiro:hasPermission name="leaderUnit:del">
                                            <li>
                                                <a href="javascript:;" data-id="${leaderUnit.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                        <wo:page commonList="${commonList}" uri="${ctx}/leaderUnit_page" target="#modal .modal-content" pageNum="5"
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
    $("#modalForm").validate({
        submitHandler: function (form) {
            var unitId = $.trim($("select[name=unitId]", form).val());
            var typeId = $.trim($("select[name=typeId]", form).val());
            //console.log(unitId)
            if(unitId=='' || typeId==''){
                $.tip({$target:$("#submitBtn", form), at:"top center",my:"bottom center", msg:"请选择单位和类别"});
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
    $('[data-rel="select2"]').select2();
    $.register.ajax_select($('#modal [data-rel="select2-ajax"]'))
</script>