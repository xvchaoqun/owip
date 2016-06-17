<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${SYS_CONFIG_MAP.get(type)}</h3>
  </div>
  <div class="modal-body">
    ${notice}
  </div>
  <div class="modal-footer">
  <a href="#" data-dismiss="modal" class="btn btn-info">关闭</a></div>