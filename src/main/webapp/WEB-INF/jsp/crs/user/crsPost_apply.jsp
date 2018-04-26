<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row" style="width: 1050px">
<div class="tabbable">
    <c:if test="${param.type!='detail'}">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
        <div style="margin-bottom: 8px;text-align: center;font-size: 16px;font-weight: bolder">
            应聘岗位：${crsPost.name}
            <div class="buttons" style="position: absolute; top:5px;">
                <a href="javascript:;" class="hideView btn btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回
                </a>
            </div>
        </div>
    </ul>
    </c:if>
    <div class="tab-content">
        <div class="tab-pane in active">
            <c:set var="isPerfect" value="${cm:perfectCadreInfo(_user.id)}"/>
            <div class="alert alert-warning" style="font-size: 24px;margin-bottom: 0">
                干部信息完整性校验结果：${isPerfect?"通过":"不通过"}
            </div>
            <c:if test="${!isPerfect}">
            <div class="modal-footer center">
                <button type="button" onclick="_start('cadreInfoCheck_table')"
                        class="btn btn-primary btn-lg"><i class="fa fa-hand-o-right"></i> 完善信息</button>
            </div>
            </c:if>
            <c:if test="${isPerfect}">
                <div class="page-header center" style="margin-top: 10px">
                    <h1 style="font-size: 30px; font-weight: bolder">
                        工作设想和预期目标
                    </h1>
                </div>
            <form class="form-horizontal" action="${ctx}/user/crsPost_apply" id="applyForm" method="post">
                <input type="hidden" name="postId" value="${crsPost.id}">
                <input type="hidden" name="id" value="${crsApplicant.id}">
                <input type="hidden" name="status"/>
                <input type="hidden" name="btn_type"/>
                 <textarea data-my="center" data-at="center center" required placeholder="请在此输入您的工作设想和预期目标（报名截止前可修改）"
                           name="report" class="limited" rows="18" maxlength="1100" style="width:1026px">${crsApplicant.report}</textarea>
            </form>
            <div class="modal-footer center" >
                <c:if test="${param.type!='detail'}">
                <c:if test="${crsApplicant.status!=CRS_APPLICANT_STATUS_SUBMIT}">
                <button type="button" id="saveBtn" data-loading-text="提交中..."  data-success-text="已保存成功" autocomplete="off"
                       class="btn btn-default btn-lg"><i class="fa fa-save"></i> 暂   存</button>
                </c:if>
                <button type="button" id="submitBtn" data-loading-text="提交中..."  data-success-text="已提交成功" autocomplete="off"
                       class="btn btn-success btn-lg"><i class="fa fa-check"></i> 提交报名信息</button>
                    </c:if>
                <c:if test="${param.type=='detail'}">
                    <a href="javascript:;" id="editBtn" data-loading-text="提交中..."  data-success-text="已提交成功" autocomplete="off"
                            class="btn ${crsPost.switchStatus==CRS_POST_ENROLL_STATUS_OPEN?'btn-primary':'btn-default disabled'} btn-lg"><i class="fa fa-edit"></i> 编辑</a>
               </c:if>
            </div>
            </c:if>
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
    <c:if test="${!isPerfect}">
    function _start(to) {

        $.post("${cx}/user/crsPost_start", function (ret) {
            if (ret.success) {
                var hash;
                if (ret.hasDirectModifyCadreAuth)
                    hash = "#/user/cadre?cadreId={0}&to={1}&type=1".format(ret.cadreId, $.trim(to));
                else {
                    hash = "#/cadre_view?cadreId={0}&to={1}".format(ret.cadreId, $.trim(to));
                    <shiro:hasAnyRoles name="${ROLE_CADRE},${ROLE_CADREINSPECT},${ROLE_CADRERESERVE}">
                    hash = "#/modifyBaseApply";
                    </shiro:hasAnyRoles>
                }

                $("#sidebar").load("/menu?_=" + new Date().getTime(),function(){
                    location.hash = hash;
                });
            }
        })
    }
    </c:if>
    $('textarea.limited').inputlimiter();

    $("#saveBtn").click(function(){
        $("#applyForm input[name=status]").val(${CRS_APPLICANT_STATUS_SAVE});
        $("#applyForm input[name=btn_type]").val(0);
        $("#applyForm").submit();
        return false;
    });
    $("#submitBtn").click(function(){
        $("#applyForm input[name=status]").val(${CRS_APPLICANT_STATUS_SUBMIT});
        $("#applyForm input[name=btn_type]").val(1);
        $("#applyForm").submit();
        return false;
    });
    $("#editBtn").click(function(){
        $("#applyForm input[name=status]").val(${CRS_APPLICANT_STATUS_SUBMIT});
        $("#applyForm input[name=btn_type]").val(2);
        $("#applyForm").submit();
        return false;
    });
    $("#applyForm").validate({
        submitHandler: function (form) {

            var btn_type = $("#applyForm input[name=btn_type]").val();
            if(btn_type==1) {
                var $btn = $("#submitBtn").button('loading');
            }
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){

                        if(btn_type==0) {
                            $.tip({
                                $target: $("#saveBtn"),
                                at: 'top center', my: 'bottom center', type: 'success',
                                msg: "填写内容已暂存，请及时填写完整并提交。"
                            });
                        }
                        if(btn_type==2) {
                            $.tip({
                                $target: $("#editBtn"),
                                at: 'top center', my: 'bottom center', type: 'success',
                                msg: "保存成功。"
                            });
                        }

                        if(btn_type==1) {
                            $btn.button("success").addClass("btn-success");
                            $.hashchange();
                        }
                    }else{
                        if(btn_type==1) {
                            $btn.button('reset');
                        }
                    }
                }
            });
        }
    });
</script>