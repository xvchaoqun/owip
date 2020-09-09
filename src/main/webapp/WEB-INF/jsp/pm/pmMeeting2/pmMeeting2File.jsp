<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>附件</h3>
</div>
<div class="modal-body">

    <div class="popTableDiv">
        <table class="table table-bordered table-condensed"
               data-pagination="true" data-side-pagination="client" data-page-size="5">
            <thead>
            <tr>
                <th>序号</th>
                <th>附件</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pmMeeting2Files}" var="file" varStatus="vs">
                <tr>
                    <th>${vs.count}</th>
                    <th>${pmMeeting2FileNames.get(vs.index)}</th>
                    <th>
                        <c:if test="${fn:endsWith(fn:toLowerCase(file), '.pdf')}">
                            <a href="${ctx}/pdf?path=${cm:sign(file)}" target="_blank" style="font-size: 14px;font-weight: normal">预览</a>
                        </c:if>
                        <c:if test="${!fn:endsWith(fn:toLowerCase(file), '.pdf')}">
                            <a href="${ctx}/pic?path=${cm:sign(file)}" target="_blank" style="font-size: 14px;font-weight: normal">预览</a>
                        </c:if>
                        <a href="javascript:;" data-type="download" style="font-size: 14px;font-weight: normal"
                           data-url="${ctx}/attach_download?path=${cm:sign(file)}"
                           class="downloadBtn">下载</a>
                    </th>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>