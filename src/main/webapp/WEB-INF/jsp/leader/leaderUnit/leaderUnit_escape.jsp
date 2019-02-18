<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>未分配校级领导的单位</h3>
</div>
<div class="modal-body">
  <table class="table table-actived table-striped table-bordered table-hover">
    <thead>
    <tr>
      <th>单位名称</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${units}" var="unit" varStatus="st">
      <tr>
        <td nowrap><span class="${unit.status==UNIT_STATUS_HISTORY?'delete':''}">${unit.name}</span></td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
<div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>