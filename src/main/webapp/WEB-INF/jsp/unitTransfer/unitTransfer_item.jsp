<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${unitTransfer.submit}-变更记录</h3>
</div>
<div class="modal-body">
    <shiro:hasPermission name="unitTransferItem:edit">

        <form class="form-inline" action="${ctx}/unitTransferItem_au" id="modalForm" method="post">
            <div class="form-group">
                <input type="hidden" name="unitTransferId" value="${unitTransfer.id}">
                <select data-rel="select2-ajax" required data-ajax--url="${ctx}/unit_selects"
                        name="unitId" data-placeholder="请选择关联单位">
                    <option></option>
                </select>
            </div>
            <input type="submit" class="btn btn-sm btn-primary" value="添加"/>
        </form>
        <div class="space-10"></div>
    </shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/dispatchUnit_relate?id=${dispatchUnit.id}"
         data-url-del="${ctx}/unitTransferItem_del"
         data-url-co="${ctx}/unitTransferItem_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-striped table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th class="col-xs-10">关联单位</th>
                    <shiro:hasPermission name="unitTransferItem:changeOrder">
                        <c:if test="${!_query && commonList.recNum>1}">
                            <th nowrap>排序</th>
                        </c:if>
                    </shiro:hasPermission>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${unitTransferItems}" var="unitTransferItem" varStatus="st">
                    <tr>
                        <td nowrap>${unitMap.get(unitTransferItem.unitId).name} -${unitTransferItem.sortOrder}</td>
                        <shiro:hasPermission name="unitTransferItem:changeOrder">
                            <c:if test="${!_query && commonList.recNum>1}">
                                <td nowrap>
                                    <a href="#" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${unitTransferItem.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="#" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${unitTransferItem.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>
                                </td>
                            </c:if>
                        </shiro:hasPermission>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="unitTransferItem:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${unitTransferItem.id}">
                                        <i class="fa fa-times"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                            <div class="hidden-md hidden-lg">
                                <div class="inline pos-rel">
                                    <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                                        <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
                                    </button>

                                    <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                                        <shiro:hasPermission name="unitTransferItem:del">
                                            <li>
                                                <a href="#" data-id="${unitTransferItem.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
            <div class="text-center">
                <div class="pagination pagination-centered">
                    <c:if test="${!empty commonList && commonList.pageNum>1 }">
                        <wo:page commonList="${commonList}" uri="${ctx}/unitTransferItem_page" target="#modal .modal-content" pageNum="5"
                                 model="3"/>
                    </c:if>
                </div>
            </div>
        </c:if>
        <c:if test="${commonList.recNum==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">暂无记录</h4>
            </div>
        </c:if>
    </div>
</div>
<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        pop_reload($(".popTableDiv"));
                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        },errorPlacement:function(error, element){

        },invalidHandler:function(form, validator){
            //var errors = validator.numberOfInvalids();
            toastr.error("请选择关联单位", '错误');
        }
    });

    $('#modal [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 200,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
</script>