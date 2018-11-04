<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>党委常委会</h3>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td>党委常委会</td>
            <td></td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${scCommittees}" var="scCommittee" varStatus="vs">
            <tr>
                <td>${scCommittee.code}</td>
                <td>
                    <button class="linkBtn btn btn-xs btn-primary"
                            data-url="${ctx}#/sc/scCommittee?year=${scCommittee.year}&holdDate=${cm:formatDate(scCommittee.holdDate, "yyyyMMdd")}"
                            data-target="_blank">
                        <i class="fa fa-search"></i> 查看</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>