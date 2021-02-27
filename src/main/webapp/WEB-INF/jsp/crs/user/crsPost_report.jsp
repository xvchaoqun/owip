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
            <c:set value="${cm:getHtmlFragment('hf_crs_apply_report').content}" var="note"/>
            <c:if test="${not empty note}">
                <div class="alert alert-warning" style="font-size: 24px;">
                        ${note}
                </div>
            </c:if>
            <div class="page-header center" style="margin-top: 10px">
                <h1 style="font-size: 30px; font-weight: bolder">
                    *工作设想和预期目标
                </h1>
            </div>
            <form class="form-horizontal" action="${ctx}/user/crsPost_apply" id="applyForm" method="post">
                <input type="hidden" name="postId" value="${crsPost.id}">
                <input type="hidden" name="id" value="${crsApplicant.id}">
                <input type="hidden" name="cls" value="2"/>
                 <textarea data-my="center" class="canEnter" data-at="center center" required placeholder="请在此输入您的工作设想和预期目标（报名截止前可修改）"
                           name="content" rows="18" style="width:1026px">${crsApplicant.report}</textarea>
            </form>
            <div class="modal-footer center" >
                <div class="pull-left" style="position: absolute; font-size: 16pt">
                    您已输入<span id="strCount" style="font-size: 20pt;font-weight: bolder">0</span>个字。</div>
                <button type="button" ${canModify?"":"disabled"} id="submitBtn" data-loading-text="提交中..."  data-success-text="已保存成功" autocomplete="off"
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
    $('textarea').on('input propertychange', function() {
        var str = $(this).val().replace(/\s/g, "");
        $("#strCount").html(str.length)
    }).trigger("propertychange");
    $("#submitBtn").click(function(){$("#applyForm").submit();return false;});
    $("#applyForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        SysMsg.success("保存成功。",function(){
                            $.openView("${ctx}/user/crsPost_apply?postId=${crsPost.id}")
                        })
                    }
                }
            });
        }
    });
</script>