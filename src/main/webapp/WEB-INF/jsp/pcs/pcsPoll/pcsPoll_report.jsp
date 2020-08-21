<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="../constants.jsp" %>
<style>
    .tip {
        margin-left: 30px
    }
    .tip div {
        margin: 20px 0;

        font-size: 25px;
        color: darkred;
        font-weight: bolder;
        text-align: center;
    }
    .tip .count {
        color: darkred;
        font-size: 24px;
        font-weight: bolder;
    }
    .tip ul {
        margin-left: 50px;
    }
    .tip ul li {
        font-size: 25px;
        text-align: left;
    }
    .tip div {
        margin: 20px 0;

        font-size: 25px;
        color: darkred;
        font-weight: bolder;
        text-align: center;
    }
</style>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>报送投票结果</h3>
  </div>
  <div class="modal-body">
      <form class="form-horizontal" action="${ctx}/pcs/pcsPoll_report" autocomplete="off" disableautocomplete id="modalForm" method="post">
          <input type="hidden" name="id" value="${param.id}">

      <div class="tip">
          <div style="margin-left: -50px;font-size: 25px;">
          <span class="star">*</span> 应参会党员数
				<input required style="width: 78px;" class="digits" type="text" data-at="top center" data-my="bottom center"
					   name="expectMemberCount" data-rule-min="1" data-rule-max="${allCount}">
			<span class="star">*</span> 实际参会党员数
				<input required style="width: 78px;" class="digits" type="text" data-at="top center" data-my="bottom center"
					   name="actualMemberCount" data-rule-min="1" data-rule-max="${allCount}">
              </div>
          <ul>
              <li>
                  本支部党员数量：<span class="count">${allCount}</span>人（其中正式党员数量：<span class="count">${positiveCount}</span>人）
              </li>
              <li>
                  生成投票账号数量：<span class="count">${inspectorNum}</span>人
              </li>
              <li>
                  完成投票账号数量：<span class="count">${inspectorFinishNum}</span>人（其中正式党员数量：<span class="count">${positiveFinishNum}</span>人）
              </li>
              <li>
                  已选候选人中，<c:if test="${stage!=PCS_POLL_THIRD_STAGE}"></c:if>党委委员<span class="count">${dwNum}</span>名，纪委委员<span class="count">${jwNum}</span>名，代表<span class="count">${prNum}</span>名
              </li>
          </ul>
          <div>报送后不能修改投票数据，请确认以上信息准确无误后提交</div>
      </div>
          </form>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default"><i class="fa fa-times"></i> 取消</a>
      &nbsp;&nbsp;&nbsp;&nbsp;
	  <button id="submitBtn" type="button" class="btn btn-success"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"><i class="fa fa-check"></i> 报送</button>
  </div>
<script>

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({

            submitHandler: function (form) {
                 var $btn = $("#submitBtn").button('loading');
                $(form).ajaxSubmit({
                    success:function(ret){
                        if(ret.success){
                            $("#modal").modal('hide');
                            $("#jqGrid").trigger("reloadGrid");
                            SysMsg.success('报送成功', '成功');
                        }
                        $btn.button('reset');
                    }
                });
            }
        });
</script>