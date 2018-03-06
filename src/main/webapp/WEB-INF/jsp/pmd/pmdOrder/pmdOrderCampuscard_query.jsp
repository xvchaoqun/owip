<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>查询订单</h3>
</div>
<div class="modal-body">
  <table class="table table-bordered table-unhover">
    <tr>
      <td class="bg-right" width="130">订单号</td>
      <td>${param.sn}</td>
    </tr>
    <tr>
      <td class="bg-right">缴费账号</td>
      <td>${param.code}</td>
    </tr>
    <tr>
      <td class="bg-right">接口返回</td>
      <td style="word-break: break-all">${ret}</td>
    </tr>
  </table>
</div>
<div class="modal-footer">
  <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>