<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
  <div class="col-xs-12">
      <div class="widget-box" style="width: 500px">
        <div class="widget-header">
          <h4 class="widget-title">
            基本设置
          </h4>
        </div>
        <div class="widget-body">
          <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/pcsProposalTime_au" id="timeForm"
                  method="post">
              <div class="form-group">
                <label class="col-xs-4 control-label">提交提案截止时间</label>
                <div class="col-xs-6">
                  <div class="input-group">
                    <input class="form-control datetime-picker" type="text"  name="proposalSubmitTime"
                           value="${cm:formatDate(pcsConfig.proposalSubmitTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-xs-4 control-label">征集附议人截止时间</label>
                <div class="col-xs-6">
                  <div class="input-group">
                    <input class="form-control datetime-picker" type="text"  name="proposalSupportTime"
                           value="${cm:formatDate(pcsConfig.proposalSupportTime, "yyyy-MM-dd HH:mm")}">
							<span class="input-group-addon">
                            <i class="fa fa-calendar bigger-110"></i>
                        </span>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label class="col-xs-4 control-label">立案附议人数</label>
                <div class="col-xs-6">
                  <input class="form-control num" type="text" maxlength="4"
                         name="proposalSupportCount" value="${pcsConfig.proposalSupportCount}">
                </div>
              </div>

              <div class="modal-footer center">
                <%--<a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>--%>
                <input type="submit" class="btn btn-primary" value="更新"/>
              </div>
            </form>
          </div>
        </div>
      </div>

    <div class="widget-box myTableDiv"  style="width:800px">
      <div class="widget-header">
        <h4 class="widget-title">
          提案类型
          <div class="pull-right" style="margin-right: 10px">
            <a class="popupBtn btn btn-success btn-xs" data-url="${ctx}/pcsProposalType_au"><i
                    class="fa fa-plus"></i> 添加</a>
          </div>
        </h4>
      </div>
      <div class="widget-body">
        <div class="widget-main" id="qualification-content">
          <table class="table table-bordered table-striped">
            <thead>
            <tr>
              <th width="30">序号</th>
              <th width="200">类型名称</th>
              <th width="100">排序</th>
              <th width="100"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${prTypes}" var="record" varStatus="vs">
              <tr>
                <td>${vs.count}</td>
                <td style="text-align: left">${record.name}</td>
                <td nowrap>
                  <a href="javascript:;" data-url="${ctx}/pcsProposalType_changeOrder"
                     data-callback="_reload"
                     <c:if test="${st.first}">style="visibility: hidden"</c:if>
                     class="changeOrderBtn" data-id="${record.id}" data-direction="1" title="上升">
                    <i class="fa fa-arrow-up"></i></a>
                  <input type="text" value="1"
                         class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                         title="修改操作步长">
                  <a href="javascript:;" data-url="${ctx}/pcsProposalType_changeOrder"
                     data-callback="_reload"
                     <c:if test="${st.last}">style="visibility: hidden"</c:if>
                     class="changeOrderBtn" data-id="${record.id}" data-direction="-1" title="下降"><i
                          class="fa fa-arrow-down"></i></a></td>
                </td>
                <td>
                  <a class="popupBtn btn btn-primary btn-xs"
                     data-url="${ctx}/pcsProposalType_au?id=${record.id}"><i class="fa fa-edit"></i>
                    修改</a>
                  <button class="confirm btn btn-danger btn-xs"
                          data-url="${ctx}/pcsProposalType_del?id=${record.id}"
                          data-title="删除"
                          data-msg="确定删除该类型？" data-callback="_reload"
                          ><i class="fa fa-times"></i> 删除
                  </button>
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
  .table tr th, .table tr td {
    white-space: nowrap;
    text-align: center;
  }
</style>
<script>
  $.register.datetime($('.datetime-picker'));
  $("#timeForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success: function (ret) {
          if (ret.success) {
            SysMsg.info("更新成功。")
            _reload()
          }
        }
      });
    }
  });

  function _reload() {
    $.hashchange();
  }
</script>