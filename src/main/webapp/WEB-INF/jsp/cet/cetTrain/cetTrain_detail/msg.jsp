<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="space-4"></div>
<div style="width: 500px; float:left;margin-right: 25px">
  <div class="widget-box">
    <div class="widget-header">
      <h4 class="smaller">
        短信通知
      </h4>
    </div>
    <div class="widget-body">
      <div class="widget-main">

        <div id="accordion" class="accordion-style1 panel-group">
          <c:forEach items="${tplList}" var="tpl" varStatus="vs">

            <div class="panel panel-default">
              <div class="buttons">
                <button type="button"
                        data-width="650"
                        data-url="${ctx}/cet/cetTrain_detail/msg_au?tplKey=${tpl.code}"
                        class="popupBtn btn btn-xs btn-primary">
                  <i class="ace-icon fa fa-edit"></i>
                  编辑
                </button>
                <button type="button"
                        data-url="${ctx}/cet/cetTrain_detail/msg_send?trainId=${cetTrain.id}&tplKey=${tpl.code}"
                        class="popupBtn btn btn-xs btn-warning">
                  <i class="ace-icon fa fa-send"></i>
                  发送
                </button>
                <button type="button"
                        data-url="${ctx}/cet/cetTrain_detail/msg_list?trainId=${cetTrain.id}&tplKey=${tpl.code}"
                        class="popupBtn btn btn-xs btn-info">
                  <i class="ace-icon fa fa-history"></i>
                  记录
                </button>
              </div>
              <div class="panel-heading">
                <h4 class="panel-title">
                  <a class="accordion-toggle ${vs.first?'':'collapsed'}" data-toggle="collapse" data-parent="#accordion"
                     href="#collapse${tpl.id}">
                    <i class="ace-icon fa fa-angle-${vs.first?'down':'right'} bigger-110"
                       data-icon-hide="ace-icon fa fa-angle-down"
                       data-icon-show="ace-icon fa fa-angle-right"></i>
                      ${CONTENT_TPL_CET_MSG_MAP.get(tpl.code)}
                  </a>
                </h4>
              </div>
              <div class="panel-collapse collapse ${vs.first?'in':''}" id="collapse${tpl.id}">
                <div class="panel-body">
                    ${tpl.content}
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>
  </div>
</div>
<style>
    .panel .buttons{
        top: 6px;
        right: 10px;
        position: relative;
        overflow: hidden;
        z-index: 999;
        float: right!important;
    }
</style>