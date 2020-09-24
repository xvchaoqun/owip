<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>上传签字文件</h3>
</div>
<div class="modal-body">
    <div class="popTableDiv"
         data-url-page="${ctx}/oa/oaGridParty_files?id=${oaGridParty.id}">
        <table class="table table-actived table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>签字文件</th>
                <th width="100">
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${filePaths}" var="filePath" varStatus="vs">
                <tr>
                    <td nowrap>
                                <c:if test="${fn:endsWith(fn:toLowerCase(filePath), '.pdf')}">
                                    <a href="${ctx}/pdf?path=${cm:sign(filePath)}" target="_blank" style="font-size: 14px;font-weight: normal">${fileNames.get(vs.index)}</a>
                                </c:if>
                                <c:if test="${!fn:endsWith(fn:toLowerCase(filePath), '.pdf')}">
                                    <a href="${ctx}/pic?path=${cm:sign(filePath)}" target="_blank" style="font-size: 14px;font-weight: normal">${fileNames.get(vs.index)}</a>
                                </c:if>
                    </td>
                    <td nowrap>
                        <div class="hidden-sm hidden-xs action-buttons">
                            <c:if test="${oaGridParty.status!=OA_GRID_PARTY_REPORT}">
                                <button class="confirm btn btn-danger btn-xs"
                                        data-title="删除"
                                        data-msg="确定删除？"
                                        data-callback="_pop_reload"
                                        data-url="${ctx}/oa/oaGridPartyFile_del?id=${oaGridParty.id}&fileName=${fileNames.get(vs.index)}&filePath=${filePath}">
                                    <i class="fa fa-times"></i> 删除
                                </button>
                            </c:if>
                            <button class="downloadBtn btn btn-xs btn-info" data-type="download"
                               data-url="${ctx}/attach_download?path=${cm:sign(filePath)}&filename=${fileNames.get(vs.index)}">
                                <i class="fa fa-download"></i> 下载</button>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${fn:length(filePaths)==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">暂无文件</h4>
            </div>
        </c:if>
    </div>
</div>
<script>

    function _pop_reload(){
        pop_reload();
        $(window).triggerHandler('resize.jqGrid');
    }
</script>