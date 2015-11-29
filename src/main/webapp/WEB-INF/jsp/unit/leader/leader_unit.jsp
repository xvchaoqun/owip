<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cm:getUserById(cadreMap.get(leader.cadreId).userId).realname}-联系单位</h3>
</div>
<div class="modal-body">
<shiro:hasPermission name="leaderUnit:edit">

    <form class="form-inline" action="${ctx}/leaderUnit_au" id="modalForm" method="post">
        <div class="form-group">
            <input type="hidden" name="leaderId" value="${leader.id}">
            <select data-rel="select2-ajax" required data-ajax--url="${ctx}/unit_selects?status=1"
                    name="unitId" data-placeholder="请选择单位">
                <option></option>
            </select>
            <select data-rel="select2" name="typeId" data-placeholder="请选择类别">
                <option></option>
                <c:forEach var="metaType" items="${metaTypeMap}">
                    <option value="${metaType.value.id}">${metaType.value.name}</option>
                </c:forEach>
            </select>
        </div>
        <input type="submit" class="btn btn-sm btn-primary" value="添加"/>
    </form>
    <div class="space-10"></div>
</shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/leader_unit?id=${leader.id}"
         data-url-del="${ctx}/leaderUnit_del">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-striped table-bordered table-hover table-condensed">
                <thead>
                <tr>
                    <th class="col-xs-5">联系单位</th>
                    <th class="col-xs-5">类别</th>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${leaderUnits}" var="leaderUnit" varStatus="st">
                    <tr>
                        <td nowrap>${unitMap.get(leaderUnit.unitId).name}</td>
                        <td nowrap>${metaTypeMap.get(leaderUnit.typeId).name}</td>
                         
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="leaderUnit:del">
                                    <button class="delBtn btn btn-danger btn-mini" data-id="${leaderUnit.id}">
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
                                        <shiro:hasPermission name="leaderUnit:del">
                                            <li>
                                                <a href="#" data-id="${leaderUnit.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                        <wo:page commonList="${commonList}" uri="${ctx}/leaderUnit_page" target="#modal .modal-content" pageNum="5"
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
            toastr.warning("请选择单位和类别", '错误');
        }
    });
    $('[data-rel="select2"]').select2();
    $('#modal [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 300,
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