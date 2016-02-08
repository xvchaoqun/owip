<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>关联任免文件</h3>
</div>
<div class="modal-body">
            <c:if test="${fn:length(dispatchCadres)>0}">
                <table class="table table-actived table-striped table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>年份</th>
                            <th>类别</th>
							<th>发文号</th>
							<th>任免方式</th>
							<th>任免程序</th>
                            <th>职务</th>
                            <th>职务属性</th>
							<th>行政级别</th>
							<th>所属单位</th>
                        <th>任免文件</th>
                        <th>上会ppt</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${dispatchCadres}" var="dispatchCadre" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox"
                                           value="${dispatchCadre.id}" class="ace"
                                            <c:if test="${cadreDispatchIdSet.contains(dispatchCadre.id)}">checked="checked" </c:if>
                                            >
                                    <span class="lbl"></span>
                                </label>
                            </td>
                                <c:set value="${dispatchMap.get(dispatchCadre.dispatchId)}" var="dispatch"/>
								<td nowrap>${dispatch.year}</td>
								<td nowrap>${dispatchTypeMap.get(dispatch.dispatchTypeId).name}</td>
								<td nowrap>${dispatch.code}</td>
								<td nowrap>${wayMap.get(dispatchCadre.wayId).name}</td>
								<td nowrap>${procedureMap.get(dispatchCadre.procedureId).name}</td>
                                <td nowrap>${dispatchCadre.post}</td>
                                <td nowrap>${postMap.get(dispatchCadre.postId).name}</td>
								<td nowrap>${adminLevelMap.get(dispatchCadre.adminLevelId).name}</td>
								<td nowrap>${unitMap.get(dispatchCadre.unitId).name}</td>
                                <td nowrap><c:if test="${not empty dispatch.fileName}">
                                    <a href="/dispatch_download?id=${dispatch.id}&type=file" target="_blank">下载</a>
                                    <a href="javascript:void(0)" onclick="swf_preview(${dispatch.id}, 'file')">预览</a>
                                </c:if>
                                </td>
                                <td nowrap><c:if test="${not empty dispatch.pptName}"><a href="/dispatch_download?id=${dispatch.id}&type=ppt" target="_blank">下载</a>
                                    <a href="javascript:void(0)" onclick="swf_preview(${dispatch.id}, 'ppt')">预览</a>
                                </c:if>
                                </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${fn:length(dispatchCadres)==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" onclick="addDispatch('${param.type}')" class="btn btn-primary" value="保存"/>
</div>
<script>
    function swf_preview(id, type){
        loadModal("${ctx}/swf_preview?way=2&id="+id + "&type=" + type);
    }

    function addDispatch(type){

        if(type=="checkbox"){
            var ids = $.map($("#modal .table td :checkbox:checked"),function(item, index){
                return $(item).val();
            });

            $.post("${ctx}/cadreMainWork_addDispatchs",{id:'${param.id}',ids:ids},function(ret){
                if(ret.success) {
                    $("#modal").modal('hide');
                    SysMsg.success('操作成功。', '成功');
                }
            });
        }else if(type="radio"){

            var ids = $.map($("#modal .table td :checkbox:checked"),function(item, index){
                return $(item).val();
            });
            if(ids.length>1){
                SysMsg.warning("只能选择一个发文");
                return;
            }else{

                $.post("${ctx}/cadreMainWork_addDispatch",{id:'${param.id}',dispatchCadreId:ids[0]},function(ret){
                    if(ret.success) {
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                });
            }

        }
    }
</script>
</div>