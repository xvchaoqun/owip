<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>关联${param.cls=='start'?"始任":"结束"}文件</h3>
</div>
<div class="modal-body">
    <c:if test="${fn:length(dispatchCadres)>0}">
        <table class="table table-actived table-striped table-bordered table-hover">
            <thead>
            <tr>
                <c:if test="${type=='edit'}">
                    <th class="center">
                        <label class="pos-rel">
                            <input type="checkbox" class="ace checkAll">
                            <span class="lbl"></span>
                        </label>
                    </th>
                </c:if>
                <th>年份</th>
                <th>发文号</th>
                <th>任命方式</th>
                <th>姓名</th>
                <th>所在单位及职务</th>
                <th>职务属性</th>
                <th>行政级别</th>
                <th>所属单位</th>
                <th>任免文件</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${dispatchCadres}" var="dispatchCadre" varStatus="st">
                <tr>
                    <c:if test="${type=='edit'}">
                        <td class="center">
                            <c:if test="${!otherDispatchCadreRelateSet.contains(dispatchCadre.id)}">
                                <label class="pos-rel">
                                    <input type="checkbox"
                                           value="${dispatchCadre.id}" class="ace" ${dispatchCadreIdSet.contains(dispatchCadre.id)?"checked":""}>
                                    <span class="lbl"></span>
                                </label>
                            </c:if>
                        </td>
                    </c:if>
                    <c:set value="${dispatchMap.get(dispatchCadre.dispatchId)}" var="dispatch"/>
                    <td nowrap>${dispatch.year}</td>
                    <td nowrap>${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year)}</td>
                    <td nowrap>${wayMap.get(dispatchCadre.wayId).name}</td>
                    <td nowrap>${cm:getUserById(cadreMap.get(dispatchCadre.cadreId).userId).realname}</td>
                    <td nowrap>${dispatchCadre.post}</td>
                    <td nowrap>${postMap.get(dispatchCadre.postId).name}</td>
                    <td nowrap>${adminLevelMap.get(dispatchCadre.adminLevelId).name}</td>
                    <td nowrap>${unitMap.get(dispatchCadre.unitId).name}</td>
                    <td nowrap><c:if test="${not empty dispatch.fileName}">
                        <a href="/dispatch_download?id=${dispatch.id}&type=file" target="_blank">下载</a>
                        <a href="javascript:void(0)" class="openUrl"
                           data-url="${ctx}/swf/preview?type=url&path=${cm:encodeURI(dispatch.file)}&filename=${dispatch.fileName}">预览</a>
                    </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${fn:length(dispatchCadres)==0}">
        <div class="well well-lg center">
            <h4 class="green lighter">没有该干部的${param.cls=='start'?"任命":"免职"}文件</h4>
        </div>
    </c:if>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
    <c:if test="${type=='edit'}">
        <input type="button" onclick="addDispatch()" class="btn btn-primary" value="保存"/>
    </c:if>
    <c:if test="${type!='edit'}">
        <button class="popupBtn btn btn-success"
                data-url="${ctx}/cadreAdminLevel_addDispatchs?id=${param.id}&cadreId=${param.cadreId}&type=edit&cls=${param.cls}"
                data-width="1000"><i class="fa fa-reply"></i>
            重新编辑</button>
    </c:if>
</div>
<script>
    function addDispatch(type){

            var ids = $.map($("#modal .table td :checkbox:checked"),function(item, index){
                return $(item).val();
            });
            if(ids.length>1){
                SysMsg.warning("只能选择一个发文");
                return;
            }else{

                $.post("${ctx}/cadreAdminLevel_addDispatch",{id:'${param.id}',cls:'${param.cls}',dispatchCadreId:ids[0]},function(ret){
                    if(ret.success) {
                        _reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                });
            }
    }
</script>
</div>