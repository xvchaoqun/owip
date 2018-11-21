<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>核查文件</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td>文件</td>
            <td></td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${fn:split(scMatterCheck.files, '<><>')}" var="f" varStatus="vs">
        <tr>
            <td>
                <a href="${ctx}/attach/download?path=${fn:split(f, '^^^^')[1]}&filename=${fn:split(f, '^^^^')[0]}">
                        ${fn:split(f, '^^^^')[0]}</a>
            </td>
            <td>
                <a href="${ctx}/attach/download?path=${fn:split(f, '^^^^')[1]}&filename=${fn:split(f, '^^^^')[0]}">
                    下载</a>
            </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>