<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${fLayerType.secondLevel}（${topLayerType.name}）</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/layerType_au" autocomplete="off" disableautocomplete id="modalForm"
          method="post">
        <input type="hidden" name="fid" value="${topLayerType.id}">
        <input type="hidden" name="id" value="${layerType.id}">
         <div class="form-group">
            <label class="col-xs-3 control-label">编号</label>
            <div class="col-xs-6">
                <input class="form-control" type="text" name="code" value="${layerType.code}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>${fLayerType.secondLevel}</label>
            <div class="col-xs-6">
                <input required maxlength="50" class="form-control" type="text" name="name" value="${layerType.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea maxlength="200" class="form-control limited" name="remark">${layerType.remark}</textarea>
            </div>
        </div>
        <div class="modal-footer">
            <input type="button" id="submitBtn" class="btn btn-sm btn-primary" value="${empty layerType?'添加':'编辑'}"/>
        </div>
    </form>
    <div class="space-10"></div>
    <div class="popTableDiv"
         data-url-page="${ctx}/layerType_detail?fid=${topLayerType.id}&popup=1"
         data-url-au="${ctx}/layerType_detail?fid=${topLayerType.id}&popup=1"
         data-url-del="${ctx}/layerType_del"
         data-url-co="${ctx}/layerType_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover table-center">
                <thead>
                <tr>
                    <th class="col-xs-2">编号</th>
                    <th class="col-xs-5">${fLayerType.secondLevel}</th>
                    <c:if test="${commonList.recNum>1}">
                        <th style="width: 50px">排序</th>
                    </c:if>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${layerTypes}" var="layerType" varStatus="st">
                    <tr>
                        <td nowrap>${layerType.code}</td>
                        <td nowrap>${layerType.name}</td>
                        <c:if test="${commonList.recNum>1}">
                            <td nowrap>
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${layerType.id}" data-direction="1" title="上升"><i
                                        class="fa fa-arrow-up"></i></a>
                                <input type="text" value="1"
                                       class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                       title="修改操作步长">
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${layerType.id}" data-direction="-1"
                                   title="下降"><i class="fa fa-arrow-down"></i></a></td>
                            </td>
                        </c:if>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="layerType:edit">
                                    <button class="editBtn btn btn-primary btn-xs"
                                            data-callback="_reloadGrid"
                                            data-id="${layerType.id}">
                                        <i class="fa fa-edit"></i> 编辑
                                    </button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="layerType:del">
                                    <button class="delBtn btn btn-danger btn-xs"
                                            data-callback="_reloadGrid"
                                            data-id="${layerType.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${!empty commonList && commonList.pageNum>1 }">
                <wo:page commonList="${commonList}" uri="${ctx}/layerType_detail?fid=${topLayerType.id}&popup=1"
                         target="#modal .modal-content"
                         pageNum="5"
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
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    })
    function _reloadGrid(){
        $("#jqGrid2").trigger("reloadGrid");
    }
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        pop_reload(function(){
                             _reloadGrid();
                        });
                    }
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $.register.ajax_select($('#modal [data-rel="select2-ajax"]'))
</script>