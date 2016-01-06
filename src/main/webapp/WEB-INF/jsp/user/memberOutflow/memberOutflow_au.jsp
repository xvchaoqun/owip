<%@ page import="sys.constants.SystemConstants" %>
<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/12/7
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="USER_TYPE_JZG" value="<%=SystemConstants.USER_TYPE_JZG%>"/>
<c:set var="OR_STATUS_MAP" value="<%=SystemConstants.OR_STATUS_MAP%>"/>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 党员流出申请</h4>

        <div class="widget-toolbar">
            <a href="#" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/user/memberOutflow_au" id="modalForm" method="post">
                <input type="hidden" name="id" value="${memberOutflow.id}">
                <div class="row">
                    <div class="col-xs-6">
                        <div class="form-group">
                            <label class="col-sm-3 control-label no-padding-right"> ${(sysUser.type==USER_TYPE_JZG)?"教工号":"学号"}</label>
                            <div class="col-sm-6">
                                <input readonly disabled type="text" value="${sysUser.code}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">分党委</label>
                            <div class="col-xs-6">
                                <select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                                        name="partyId" data-placeholder="请选择">
                                    <option value="${party.id}">${party.name}</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                            <label class="col-xs-3 control-label">党支部</label>
                            <div class="col-xs-6">
                                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                                        name="branchId" data-placeholder="请选择">
                                    <option value="${branch.id}">${branch.name}</option>
                                </select>
                            </div>
                        </div>
                        <script>
                            register_party_branch_select($("#modalForm"), "branchDiv",
                                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
                        </script>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">原职业</label>
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
                            <label class="col-xs-3 control-label">外出流向</label>
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
                            <label class="col-xs-3 control-label">流出时间</label>
                            <div class="col-xs-6">
                                <div class="input-group" data-width="200px">
                                    <input required class="form-control date-picker" name="_flowTime" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberOutflow.flowTime,'yyyy-MM-dd')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6">


                        <div class="form-group">
                            <label class="col-xs-3 control-label">流出省份</label>
                            <div class="col-xs-6">
                                <select required class="loc_province" name="province" style="width:120px;" data-placeholder="请选择">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">流出原因</label>
                            <div class="col-xs-6">
                                <textarea required class="form-control limited" type="text" name="reason" rows="5">${memberOutflow.reason}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">是否持有《中国共产党流动党员活动证》</label>
                            <div class="col-xs-6">
                                <label>
                                    <input name="hasPapers" ${memberOutflow.hasPapers?"checked":""} class="ace ace-switch ace-switch-5" type="checkbox" />
                                    <span class="lbl"></span>
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label">组织关系状态</label>
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
                    </div></div>

                <div class="clearfix form-actions center">
                    <button class="btn btn-info" type="submit">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        提交
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/extend/js/location.js"></script>
<script>

    showLocation("${memberOutflow.province}");

    $('textarea.limited').inputlimiter();
    register_date($('.date-picker'));
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
                    if(ret.success){
                        bootbox.alert('提交成功。',function(){
                            _reload();
                        });
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>
