<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/12/7
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row" style="width: 850px">
<c:if test="${memberOutflow.status==MEMBER_OUTFLOW_STATUS_BACK}">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">
            <i class="ace-icon fa fa-times"></i>
        </button>
        <strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if test="${not empty memberOutflow.remark}">: ${memberOutflow.remark}</c:if>
        <br>
    </div>
</c:if>
<div class="widget-box" >
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 党员流出申请</h4>

        <%--<div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>--%>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/user/memberOutflow_au" id="modalForm" method="post">
                <input type="hidden" name="id" value="${memberOutflow.id}">
                        <div class="form-group">
                            <label class="col-xs-5 control-label">组织关系状态</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2" name="orStatus" data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="${OR_STATUS_MAP}" var="_status">
                                        <option value="${_status.key}">${_status.value}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#modalForm select[name=orStatus]").val(${memberOutflow.orStatus});
                                </script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">原职业</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2" name="originalJob" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_job"/>
                                </select>
                                <script type="text/javascript">
                                    $("#modalForm select[name=originalJob]").val(${memberOutflow.originalJob});
                                </script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">外出流向</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2" name="direction" data-placeholder="请选择">
                                    <option></option>
                                    <c:import url="/metaTypes?__code=mc_flow_direction"/>
                                </select>
                                <script type="text/javascript">
                                    $("#modalForm select[name=direction]").val(${memberOutflow.direction});
                                </script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">流出时间</label>
                            <div class="col-xs-6">
                                <div class="input-group" style="width: 200px">
                                    <input required class="form-control date-picker" name="_flowTime" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberOutflow.flowTime,'yyyy-MM-dd')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">流出省份</label>
                            <div class="col-xs-6">
                                <select required class="loc_province" name="province" style="width:120px;" data-placeholder="请选择">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">流出原因</label>
                            <div class="col-xs-6" style="width: 300px">
                                <textarea required class="form-control limited" type="text" name="reason" rows="5">${memberOutflow.reason}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-5 control-label">是否持有《中国共产党流动党员活动证》</label>
                            <div class="col-xs-6">
                                <label>
                                    <input name="hasPapers" ${memberOutflow.hasPapers?"checked":""}  type="checkbox" />
                                    <span class="lbl"></span>
                                </label>
                            </div>
                        </div>

                <div class="clearfix form-actions center">
                    <button class="btn btn-info" id="submitBtn" type="button" data-loading-text="提交中..." autocomplete="off">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        提交
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
<script type="text/javascript" src="${ctx}/extend/js/location.js"></script>
<script>
    $("#modalForm :checkbox").bootstrapSwitch();
    showLocation("${memberOutflow.province}");

    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
    $("#submitBtn").click(function(){
        var $btn = $(this).button('loading');
        $("#modalForm").submit();
        setTimeout(function () { $btn.button('reset'); },1000);
        return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

            if(!$("#branchDiv").is(":hidden")){
                if($('#modalForm select[name=branchId]').val()=='') {
                    bootbox.alert('请选择支部。');
                    return;
                }
            }
            $(form).ajaxSubmit({
                success:function(ret){
                    $("#submitBtn").button("reset");
                    if(ret.success){
                        bootbox.alert('提交成功。',function(){
                            $.hashchange();
                        });
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>
