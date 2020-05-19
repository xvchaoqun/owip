<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
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
            <c:set value="${cm:getHtmlFragment('hf_crs_apply').content}" var="note"/>
            <c:if test="${not empty note}">
            <div class="alert alert-warning" style="font-size: 24px;">
                ${note}
            </div>
            </c:if>
            <c:set var="isPerfect" value="${crsApplicant.status== CRS_APPLICANT_STATUS_SUBMIT
            || cm:perfectCadreInfo(_user.id)}"/>
            <c:if test="${!isPerfect}">
            <div class="alert alert-warning" style="font-size: 24px;margin-bottom: 0">
                干部信息完整性校验结果：${isPerfect?"通过":"不通过"}
            </div>
            <div class="modal-footer center">
                <button type="button" onclick="_start('cadreInfoCheck_table')"
                        class="btn btn-primary btn-lg"><i class="fa fa-hand-o-right"></i> 完善信息</button>
            </div>
            </c:if>
            <c:if test="${isPerfect}">
                <table class="table table-condensed table-bordered table-unhover2 table-center" style="margin: 20px 0">
                    <tbody>
                    <tr>
                        <td class="title">应聘材料1：管理工作经历</td>
                        <td>
                        ${empty crsApplicant.career?'<span class="text-danger">未填写</span>':'已保存'}
                        </td>
                        <td>
                            <c:if test="${canApply && empty crsApplicant.career}">
                                <button class="openView btn btn-success btn-sm"
                                        data-url="/user/crsPost_apply?postId=${crsPost.id}&cls=1"><i class="fa fa-hand-o-right"></i>
                                    进入填写</button>
                            </c:if>
                            <c:if test="${not empty crsApplicant.career}">
                                <button class="openView btn btn-primary btn-sm"
                                        data-url="/user/crsPost_apply?postId=${crsPost.id}&cls=1"><i class="fa fa-${canApply?"edit":"search"}"></i>
                                        ${canModify?"修   改":"查   看"}</button>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">应聘材料2：工作设想和预期目标</td>
                        <td>
                                ${empty crsApplicant.report?'<span class="text-danger">未填写</span>':'已保存'}
                        </td>
                        <td>
                            <c:if test="${canApply && empty crsApplicant.report}">
                                <button class="openView btn btn-success btn-sm"
                                        data-url="/user/crsPost_apply?postId=${crsPost.id}&cls=2"><i class="fa fa-hand-o-right"></i>
                                    进入填写</button>
                            </c:if>
                            <c:if test="${not empty crsApplicant.report}">
                                <button class="openView btn btn-primary btn-sm"
                                        data-url="/user/crsPost_apply?postId=${crsPost.id}&cls=2"><i class="fa fa-${canApply?"edit":"search"}"></i>
                                        ${canModify?"修   改":"查   看"}</button>
                            </c:if>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <div class="modal-footer center">
                <c:if test="${empty crsApplicant}">
                    <button type="button" class="btn btn-primary btn-lg"
                            onclick="SysMsg.info('请填写报名材料后预览')">
                        <i class="fa fa-search"></i> 预览干部应聘报名表
                    </button>
                </c:if>
                <c:if test="${not empty crsApplicant}">
                    <button type="button" class="openView btn btn-primary btn-lg"
                            data-hide-el="#body-content-view" data-load-el="#body-content-view2"
                            data-url="${ctx}/user/crsApplicant_preview?applicantId=${crsApplicant.id}&loadEl=body-content-view&hideEl=body-content-view2">
                        <i class="fa fa-search"></i> 预览干部应聘报名表
                    </button>
                </c:if>
                <c:if test="${crsApplicant.status!=CRS_APPLICANT_STATUS_SUBMIT}">
                    <button type="button"
                        ${(!canApply || empty crsApplicant.report||empty crsApplicant.career)?"disabled":""}
                            id="submitBtn" data-loading-text="提交中..."  data-success-text="已提交成功" autocomplete="off"
                           class="btn btn-success btn-lg"><i class="fa fa-angle-double-right"></i> 应聘材料全部保存后，点击此处报名
                        <i class="fa fa-angle-double-left"></i> </button>
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
    .table .title{
        padding: 15px;
        margin: 10px 8px;
        font-size: 30px;
        font-weight: bolder;
        text-align: left;
        color: #2679b5;
    }
</style>
<script>
    <c:if test="${!isPerfect}">
    function _start(to) {

        $.post("${cx}/user/crsPost_start", function (ret) {
            if (ret.success) {
                var hash;
                hash = "#/user/cadre?cadreId={0}&to=cadreInfoCheck_table&type=1".format(ret.cadreId);
                /*if (ret.hasDirectModifyCadreAuth)
                    hash = "#/user/cadre?cadreId={0}&to={1}&type=1".format(ret.cadreId, $.trim(to));
                else {
                    hash = "#/cadre_view?cadreId={0}&to={1}".format(ret.cadreId, $.trim(to));
                    <shiro:hasAnyRoles name="${ROLE_CADRE_CJ},${ROLE_CADRE_DP},${ROLE_CADRE_KJ}">
                    hash = "#/modifyBaseApply";
                    </shiro:hasAnyRoles>
                }*/

                $("#sidebar").load("/menu?_=" + new Date().getTime(),function(){
                    location.hash = hash;
                });
            }
        })
    }
    </c:if>
    $("#submitBtn").click(function(){
        $.post("${ctx}/user/crsPost_apply", {id:'${crsApplicant.id}', postId:'${crsPost.id}', cls:3}, function(ret){
            if(ret.success){
                SysMsg.success("报名成功。",function(){
                    $.hideView();
                });
            }
        })
    });

</script>