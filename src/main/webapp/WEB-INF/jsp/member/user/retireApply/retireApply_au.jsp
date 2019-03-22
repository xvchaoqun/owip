<%--
  Created by IntelliJ IDEA.
  User: fafa
  Date: 2015/12/7
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 党员退休信息填报</h4>

        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/user/retireApply_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
                <input type="hidden" name="userId" value="${param.userId}">
                <div class="form-group">
                    <label class="col-sm-3 control-label no-padding-right"><span class="star">*</span>请选择退休后所在的组织结构类别</label>
                    <div class="col-sm-9">
                        <select required name="classId" data-rel="select2" data-placeholder="请选择">
                            <option></option>
                            <c:import url="/metaTypes?__code=mc_party_class"/>
                        </select>
                    </div>
                </div>
                <div class="form-group"  id="party" style="display: none;" >
                    <div class="col-sm-offset-3 col-sm-9">
                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?del=0"
                                name="partyId" data-placeholder="请输入${_p_partyName}名称">
                            <option></option>
                        </select>
                    </div>
                </div>
                <div class="form-group" id="branch" style="display: none;" >
                    <div class="col-sm-offset-3 col-sm-9">
                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects?del=0"
                                name="branchId" data-placeholder="请输入支部名称">
                            <option></option>
                        </select>
                    </div>
                </div>

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
<script>
    $.register.class_party_branch_select($("#modalForm"), "party", "branch",
            '${cm:getMetaTypeByCode("mt_direct_branch").id}', '${party.id}');

    $("#modalForm").validate({
        submitHandler: function (form) {

            if(!$("#party").is(":hidden")){
                if($('#modalForm select[name=partyId]').val()=='') {
                    bootbox.alert('请选择${_p_partyName}。');
                    return;
                }
            }
            if(!$("#branch").is(":hidden")){
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
