<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="MEMBER_INFLOW_OUT_STATUS_BACK" value="<%=MemberConstants.MEMBER_INFLOW_OUT_STATUS_BACK%>"/>
<div style="width: 850px">
<c:if test="${memberInflow.outStatus==MEMBER_INFLOW_OUT_STATUS_BACK}">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">
            <i class="ace-icon fa fa-times"></i>
        </button>
        <strong><i class="ace-icon fa fa-times"></i>返回修改</strong><c:if test="${not empty memberInflow.outReason}">: ${memberInflow.outReason}</c:if>
        <br>
    </div>
</c:if>
<div class="widget-box" >
    <div class="widget-header">
        <h4 class="widget-title"><i class="fa fa-paw blue"></i> 流入党员转出申请</h4>

        <%--<div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-up"></i>
            </a>
        </div>--%>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/user/memberInflowOut" autocomplete="off" disableautocomplete id="modalForm" method="post">
                <input type="hidden" name="id" value="${memberInflow.id}">
                        <div class="form-group">
                            <label class="col-xs-3 control-label"><span class="star">*</span>转出单位</label>
                            <div class="col-xs-6" style="width: 300px">
                                <textarea required class="form-control"
                                          type="text" name="outUnit">${memberInflow.outUnit}</textarea>
                            </div>
                        </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>转出地</label>
                    <div class="col-xs-6">
                        <select required class="loc_province" name="outLocation" style="width:120px;" data-placeholder="请选择">
                        </select>
                    </div>
                </div>
                        <div class="form-group">
                            <label class="col-xs-3 control-label"><span class="star">*</span>转出时间</label>
                            <div class="col-xs-6">
                                <div class="input-group" style="width: 200px">
                                    <input required class="form-control date-picker" name="_outTime" type="text"
                                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberInflow.outTime,'yyyy-MM-dd')}" />
                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                </div>
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
</div>

<script>
    $.showLocation("${memberInflow.outLocation}");
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        SysMsg.success('提交成功。',function(){
                            $.hashchange();
                        });
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
</script>
