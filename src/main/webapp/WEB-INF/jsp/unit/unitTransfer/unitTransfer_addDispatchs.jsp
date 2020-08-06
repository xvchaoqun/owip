<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>相关发文</h3>
</div>
<div class="modal-body">
            <c:if test="${fn:length(dispatchUnits)>0}">
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="center">
                            <label class="pos-rel">
                                <input type="checkbox" class="ace checkAll">
                                <span class="lbl"></span>
                            </label>
                        </th>
							<th>年份</th>
                            <th>发文类别</th>
                            <th>发文号</th>
							<th>类型</th>
                            <th>任免文件</th>
                            <th>上会ppt</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${dispatchUnits}" var="dispatchUnit" varStatus="st">
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox"
                                           value="${dispatchUnit.id}" class="ace"
                                            <c:if test="${unitDispatchIdSet.contains(dispatchUnit.id)}">checked="checked" </c:if>
                                            >
                                    <span class="lbl"></span>
                                </label>
                            </td>
                                <c:set value="${cm:getDispatch(dispatchUnit.dispatchId)}" var="dispatch"/>
								<td nowrap>${dispatch.year}</td>
								<td nowrap>${dispatchTypeMap.get(dispatch.typeId).name}</td>
								<td nowrap>${dispatch.code}</td>
                                <td nowrap>${cm:getMetaType(dispatchUnit.typeId).name}</td>
                                <td nowrap><c:if test="${not empty dispatch.fileName}">
                                    <a href="javascript:;" data-type="download"
                                       data-url="${ctx}/attach_download?path=${cm:sign(dispatch.file)}&filename=${dispatch.fileName}"
                                                class="downloadBtn">下载</a>
                                    <a href="javascript:void(0)" class="openUrl"
                                       data-url="${ctx}/pdf_preview?type=url&path=${cm:encodeURI(dispatch.file)}&filename=${dispatch.fileName}">预览</a>
                                </c:if>
                                </td>
                                <td nowrap><c:if test="${not empty dispatch.pptName}">
                                    <a href="javascript:;" data-type="download"
                                       data-url="${ctx}/attach_download?path=${cm:sign(dispatch.ppt)}&filename=${dispatch.pptName}"
                                                class="downloadBtn">下载</a>
                                    <a href="javascript:void(0)" class="openUrl"
                                       data-url="${ctx}/pdf_preview?type=url&path=${cm:encodeURI(dispatch.ppt)}&filename=${dispatch.pptName}">预览</a>
                                </c:if>
                                </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${fn:length(dispatchUnits)==0}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="button" onclick="addDispatch()" class="btn btn-primary" value="保存"/>
</div>
<script>

    function addDispatch(){

            var ids = $.map($("#modal .table td :checkbox:checked"),function(item, index){
                return $(item).val();
            });

            $.post("${ctx}/unitTransfer_addDispatchs",{id:'${param.id}',ids:ids},function(ret){
                if(ret.success) {
                    $("#modal").modal('hide');
                    //SysMsg.success('操作成功。', '成功');
                }
            });
    }
</script>