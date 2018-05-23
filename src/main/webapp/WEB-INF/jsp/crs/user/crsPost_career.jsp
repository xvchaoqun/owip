<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row" style="width: 1050px">
  <div class="tabbable">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
      <div style="margin-bottom: 8px;text-align: center;font-size: 16px;font-weight: bolder">
        应聘岗位：${crsPost.name}
        <div class="buttons" style="position: absolute; top:5px;">
          <a href="javascript:;" class="openView btn btn-xs btn-success"
             data-url="${ctx}/user/crsPost_apply?postId=${crsPost.id}">
            <i class="ace-icon fa fa-backward"></i>
            返回
          </a>
        </div>
      </div>
    </ul>
    <div class="tab-content">
      <div class="tab-pane in active">
        <c:set value="${cm:getHtmlFragment('hf_crs_apply_career').content}" var="note"/>
        <c:if test="${not empty note}">
          <div class="alert alert-warning" style="font-size: 24px;padding: 30px;">
              ${note}
          </div>
        </c:if>

        <div class="page-header center" style="margin-top: 10px">
          <h1 style="font-size: 30px; font-weight: bolder">
            管理工作经历
          </h1>
        </div>
        <form class="form-horizontal" action="${ctx}/user/crsPost_apply" id="applyForm" method="post">
          <input type="hidden" name="postId" value="${crsPost.id}">
          <input type="hidden" name="id" value="${crsApplicant.id}">
          <input type="hidden" name="cls" value="1"/>
                 <textarea data-my="center" data-at="center center" required placeholder="请在此输入您的管理工作经历（报名截止前可修改）"
                           name="content" class="limited" rows="18" maxlength="1100" style="width:1026px">${crsApplicant.career}</textarea>
        </form>
        <div class="modal-footer center" >
          <button type="button" ${canApply?"":"disabled"} id="submitBtn" data-loading-text="提交中..."  data-success-text="已保存成功" autocomplete="off"
                  class="btn btn-success btn-lg"><i class="fa fa-check"></i> 保存</button>
        </div>
      </div>
    </div>
  </div>
</div>
<style>
  .modal-footer:before{
    padding: 2px;
  }
  .modal-footer #saveBtn{
    margin-right: 10px;
  }
  #applyForm textarea {
    text-indent: 32px;
    line-height: 25px;
    /*font-family: "Arial";*/
    font-size: 16px;
    padding: 2px;
    margin: 10px;
    border: none;
    background: #FFFFFF url(${ctx}/img/dot_25.gif);
    resize: none;
  }
</style>
<script>
  $('textarea.limited').inputlimiter();
  $("#submitBtn").click(function(){$("#applyForm").submit();return false;});
  $("#applyForm").validate({
    submitHandler: function (form) {
      $(form).ajaxSubmit({
        success:function(ret){
          if(ret.success){
            SysMsg.success("保存成功。",function(){
              $.loadView("${ctx}/user/crsPost_apply?postId=${crsPost.id}")
            })
          }
        }
      });
    }
  });
</script>