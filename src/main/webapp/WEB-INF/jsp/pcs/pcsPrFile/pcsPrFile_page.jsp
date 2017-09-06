<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box" style="width: 800px">
            <div class="widget-header">
                <h4 class="smaller">
                    大会材料清单
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" id="qualification-content">
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th width="30">序号</th>
                            <th>材料名称</th>
                            <th width="40">模板</th>
                            <th width="100">报党委组织部</th>
                            <th width="60">预览</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${templates}" var="template" varStatus="vs">
                            <c:set var="file" value="${fileMap.get(template.id)}"/>
                            <tr>
                                <td>${vs.count}</td>
                                <td>${template.name}</td>
                                <td><a href="${ctx}/attach/download?path=${cm:encodeURI(template.filePath)}
                                &filename=${cm:encodeURI(template.fileName)}">下载</a></td>
                                <td>
                                        <a class="popupBtn btn ${not empty file?"btn-success":"btn-primary"} btn-xs"
                                           data-url="${ctx}/pcsPrFile_au?templateId=${template.id}&id=${file.id}"><i class="fa fa-upload"></i> ${not empty file?"重新上传":"上传材料"}</a>
                                </td>
                                <td>
                                    <c:if test="${not empty file}">
                                        <a href="javascript:void(0)" class="popupBtn"
                                           data-url="${ctx}/swf/preview?path=${cm:encodeURI(file.filePath)}&filename=${cm:encodeURI(file.fileName)}">预览</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    .table tr th,.table tr td{
        white-space: nowrap;
        text-align: center;
    }
</style>
<script>
function _reload(){
    $.hashchange();
}
</script>