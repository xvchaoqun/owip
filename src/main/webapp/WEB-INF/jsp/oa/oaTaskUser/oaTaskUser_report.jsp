<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
  <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
  <h3>报送情况</h3>
</div>
<div class="modal-body">
  <table class="table table-bordered table-unhover">
    <tr>
      <td width="100">任务名</td>
      <td>${oaTask.name}</td>
    </tr>
    <tr>
      <td>任务对象</td>
      <td>${sysUser.realname}</td>
    </tr>
    <tr>
      <td style="vertical-align: middle">报送内容</td>
      <td>
          ${cm:htmlUnescape(oaTaskUser.content)}
      </td>
    </tr>
    <tr>
      <td style="vertical-align: middle">附件</td>
      <td>
        <c:forEach var="file" items="${oaTaskUserFiles}" varStatus="vs">
          <div class="file">
            ${vs.count}、<a href="${ctx}/res_download?path=${cm:encodeURI(file.filePath)}&filename=${cm:encodeURI(file.fileName)}&sign=${cm:sign(file.filePath)}">${file.fileName}</a>
          </div>
        </c:forEach>
      </td>
    </tr>
    <tr>
      <td style="vertical-align: middle">备注</td>
      <td>
        ${oaTaskUser.remark}
      </td>
    </tr>
  </table>
</div>
<div class="modal-footer">
  <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>