<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set value="<%=PcsConstants.PCS_POLL_THIRD_STAGE%>" var="PCS_POLL_THIRD_STAGE"/>
<style>
    .tip {
        margin-left: 30px
    }
    /*.tip div {
        margin: 20px 0;

        font-size: 25px;
        color: darkred;
        font-weight: bolder;
        text-align: center;
    }*/
    .tip .count {
        color: darkred;
        font-size: 23px;
        font-weight: bolder;
    }
    .tip ul {
        margin-left: 50px;
    }
    .tip ul li {
        font-size: 22px;
        text-align: left;
    }
    /*.tip div {
        margin: 20px 0;

        font-size: 25px;
        color: darkred;
        font-weight: bolder;
        text-align: center;
    }*/
</style>
  <div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>报送党代会投票结果</h3>
  </div>
  <div class="modal-body">
      <form class="form-horizontal" action="${ctx}/user/pcs/submit" autocomplete="off" disableautocomplete id="modalForm" method="post">
          <input type="hidden" name="isSubmit" value="1">
      </form>
      <div class="tip">
          <span style="font-size: 22px;text-align: left;">已投推荐人中：</span>
          <ul>
              <c:if test="${stage!=PCS_POLL_THIRD_STAGE}">
                  <li>
                      代表数：<span class="count">${prCount}</span>名
                  </li>
              </c:if>
              <li>
                  党委委员数：<span class="count">${dwCount}</span>名
              </li>
              <li>
                  纪委委员数：<span class="count">${jwCount}</span>名
              </li>
          </ul>
          <div style="font-size: 16pt;font-weight: bolder;color:red;margin:10px;">
          <div style="text-indent:2em;margin:50px 10px 10px 10px;">提交之前，请您确认投票结果无需再做修改。</div>
          <div style="text-indent:2em;padding:10px;">为保证信息的安全，在点击确定提交成功后您的账号、密码立即失效。<div></div>
      </div>
  </div>
  <div class="modal-footer">
  <a href="javascript:;" data-dismiss="modal" class="btn btn-default"><i class="fa fa-reply"></i> 返回修改</a>
	  <button id="submitBtn" type="button" class="btn btn-success"
			 data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"><i class="fa fa-check"></i> 确定提交</button>
  </div>
<script>

    $("#submitBtn").click(function(){
        $("#modal").modal("hide");
        $("#modalForm").submit();
        return false;});
    $("#modalForm").validate({

            submitHandler: function (form) {
                 var $btn = $("#submitBtn").button('loading');
                $(form).ajaxSubmit({
                    success:function(ret){
                        if(ret.success){
                            bootbox.alert({
                                closeButton: false,
                                buttons: {
                                    ok: {
                                        label: '确定',
                                        className: 'btn-success'
                                    }
                                },
                                message: '<span style="font-size: 16pt;font-weight: bolder;padding:10px">您已完成投票，感谢您对工作的大力支持！<span>',
                                callback: function () {
                                    _logout();
                                }
                            });
                        }else {
                            $("#modal").modal("hide");
                        }
                    }
                });
            }
        });
</script>