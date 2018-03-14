<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>         
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>推荐表格下载</h3>
  </div>
  <div class="modal-body">
    <div style="font-size: 16px;text-indent: 2em">我校处级干部竞争上岗采用个人报名、组织推荐、干部推荐、群众推荐等多种应聘方式。如您需要推荐表，请下载。</div>
    <div style="padding: 30px; text-align: center"><a class="btn btn-primary" href="${ctx}/attach?code=crs_recommend_form">
      <i class="fa fa-download"></i> 处级干部推荐表.doc</a></div>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-info">关闭</a></div>