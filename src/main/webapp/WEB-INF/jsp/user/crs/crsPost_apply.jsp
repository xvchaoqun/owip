<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="tabbable">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
        <div style="margin-bottom: 8px">

            <div class="buttons">
                <a href="javascript:;" class="hideView btn btn-sm btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回
                </a>
            </div>
        </div>
    </ul>

    <div class="tab-content">
        <div id="home4" class="tab-pane in active">
            <jsp:include page="crsInfo.jsp"/>
            <div class="well">
            应聘岗位：${crsPost.name}
            </div>
            <form class="form-horizontal" action="${ctx}/user/crsPost_apply"
                  id="applyForm" method="post">
                <input type="hidden" name="postId" value="${crsPost.id}">
            </form>

            <div class="modal-footer center">
                <input type="button" id="submitBtn" data-loading-text="提交中..."  data-success-text="已提交成功" autocomplete="off"
                       class="btn btn-primary btn-lg" value="应聘报名"/>
            </div>
        </div>
    </div>
</div>

<script>
    $("#submitBtn").click(function(){$("#applyForm").submit();return false;});
    $("#applyForm").validate({
        submitHandler: function (form) {

            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){

                        $btn.button("success").addClass("btn-success");
                        $.hashchange();
                    }else{
                        $btn.button('reset');
                    }
                }
            });
        }
    });
</script>