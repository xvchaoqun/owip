<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cm:getUserById(cadreMap.get(cadreLeader.cadreId).userId).realname}-联系单位</h3>
</div>
<div class="modal-body">
<shiro:hasPermission name="cadreLeaderUnit:edit">

    <form class="form-inline" action="${ctx}/cadreLeaderUnit_au" id="modalForm" method="post">
        <div class="form-group">
            <input type="hidden" name="leaderId" value="${cadreLeader.id}">
            <select data-rel="select2-ajax" required data-ajax-url="${ctx}/unit_selects?status=1"
                    name="unitId" data-placeholder="请选择单位">
                <option></option>
            </select>
            <select data-rel="select2" name="typeId" data-placeholder="请选择类别">
                <option></option>
                <c:forEach var="metaType" items="${leaderUnitTypeMap}">
                    <option value="${metaType.value.id}">${metaType.value.name}</option>
                </c:forEach>
            </select>
        </div>
        <input type="submit" class="btn btn-sm btn-primary" value="添加"/>
    </form>
    <div class="space-10"></div>
</shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/cadreLeader_unit?id=${cadreLeader.id}"
         data-url-del="${ctx}/cadreLeaderUnit_del">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">联系单位</th>
                    <th class="col-xs-5">类别</th>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cadreLeaderUnits}" var="cadreLeaderUnit" varStatus="st">
                    <tr>
                        <td nowrap>${unitMap.get(cadreLeaderUnit.unitId).name}</td>
                        <td nowrap>${leaderUnitTypeMap.get(cadreLeaderUnit.typeId).name}</td>
                         
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="cadreLeaderUnit:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${cadreLeaderUnit.id}">
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
                                        <shiro:hasPermission name="cadreLeaderUnit:del">
                                            <li>
                                                <a href="javascript:;" data-id="${cadreLeaderUnit.id}" class="delBtn tooltip-error" data-rel="tooltip" title="删除">
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
                        <wo:page commonList="${commonList}" uri="${ctx}/cadreLeaderUnit_page" target="#modal .modal-content" pageNum="5"
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
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        pop_reload($(".popTableDiv"));
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        },errorPlacement:function(error, element){

        },invalidHandler:function(form, validator){
            //var errors = validator.numberOfInvalids();
            SysMsg.warning("请选择单位和类别", '错误');
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