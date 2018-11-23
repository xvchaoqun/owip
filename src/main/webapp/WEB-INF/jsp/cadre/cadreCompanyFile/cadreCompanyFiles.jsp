<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${param.type==1?'校领导兼职管理文件':'中层干部兼职管理文件'}</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-center table-condensed table-unhover2">
        <thead>
        <tr>
            <td width="60">序号</td>
            <td>文件名</td>
            <td width="180">查看</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cadreCompanyFiles}" var="item" varStatus="vs">
            <tr>
                <td>${vs.count}</td>
                <td style="text-align: left">${item.dwf.fileName}sdfsdfsdfsdfsdfsdfsdfsdf</td>
                <td style="text-align: left">
                    <c:if test="${not empty item.dwf.pdfFilePath}">
                    <button href="javascript:void(0)" data-url="${ctx}/swf/preview?type=url&path=${item.dwf.pdfFilePath}&filename=${item.dwf.fileName}"
                            title="PDF文件预览" class="openUrl btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>

                    <button data-url="${ctx}/attach/download?path=${item.dwf.pdfFilePath}&filename=${item.dwf.fileName}" title="下载PDF文件"
                            class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>
                    </c:if>
                    <c:if test="${not empty item.dwf.wordFilePath}">
                    <button data-url="${ctx}/attach/download?path=${item.dwf.wordFilePath}&filename=${item.dwf.fileName}"
                            title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>