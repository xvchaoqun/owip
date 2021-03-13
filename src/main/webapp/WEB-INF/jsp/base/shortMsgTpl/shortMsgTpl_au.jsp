<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['wx_support']=='true'}" var="_p_wx_support"/>
<c:set value="<%=ContentTplConstants.CONTENT_TPL_TYPE_MSG%>" var="CONTENT_TPL_TYPE_MSG"/>
<c:set value="<%=ContentTplConstants.CONTENT_TPL_WX_TYPE_NEWS%>" var="CONTENT_TPL_WX_TYPE_NEWS"/>
<div style="width: 900px">
    <div class="modal-header">
        <h3><c:if test="${shortMsgTpl!=null}">编辑</c:if><c:if test="${shortMsgTpl==null}">添加</c:if>定向消息模板</h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal"
              data-tip-container="#page-content"
              action="${ctx}/shortMsgTpl_au" autocomplete="off" disableautocomplete
              id="modalForm" method="post">
            <input type="hidden" name="id" value="${shortMsgTpl.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>模板名称</label>
                <div class="col-xs-8">
                    <input required class="form-control" type="text" name="name" value="${shortMsgTpl.name}">
                </div>
            </div>
            <c:if test="${!_p_wx_support}"><input type="hidden" name="type" value="<%=ContentTplConstants.CONTENT_TPL_TYPE_MSG%>"></c:if>
            <c:if test="${_p_wx_support}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>类型</label>
                <div class="col-xs-8">
                    <div class="input-group">
                        <c:forEach var="type" items="<%=ContentTplConstants.CONTENT_TPL_TYPE_MAP%>" varStatus="vs">
                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                <input required type="radio" name="type" id="type${vs.index}" value="${type.key}">
                                <label for="type${vs.index}">
                                        ${type.value}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="form-group wxMsg">
                <label class="col-xs-3 control-label">微信消息类型</label>
                <div class="col-xs-8">
                    <div class="input-group">
                        <c:forEach var="type" items="<%=ContentTplConstants.CONTENT_TPL_WX_TYPE_MAP%>" varStatus="vs">
                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                <input required type="radio" name="wxMsgType"
                                       id="wxMsgType${vs.index}" value="${type.key}">
                                <label for="wxMsgType${vs.index}">
                                        ${type.value}
                                </label>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="form-group wxMsg">
                <label class="col-xs-3 control-label">微信标题</label>
                <div class="col-xs-8">
                    <input class="form-control" type="text" name="wxTitle" value="${shortMsgTpl.wxTitle}">
                </div>
            </div>
            <div class="form-group wxMsg wxPic" style="display: none">
                <label class="col-xs-3 control-label">微信图片地址</label>
                <div class="col-xs-8">
                    <input class="form-control url" type="text" name="wxPic" value="${shortMsgTpl.wxPic}">
                </div>
            </div>
            <div class="form-group wxMsg">
                <label class="col-xs-3 control-label">微信跳转地址</label>
                <div class="col-xs-8">
                    <input class="form-control" type="text" name="wxUrl" value="${shortMsgTpl.wxUrl}">
                    <span class="help-block">最终跳转地址为：${_p_siteHome}+微信跳转地址；如无需跳转请留空。</span>
                </div>
            </div>
            </c:if>
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>发送内容</label>
                <div class="col-xs-8">
                    <textarea required class="form-control limited" type="text"
                              name="content" rows="8" maxlength="500">${shortMsgTpl.content}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注</label>
                <div class="col-xs-8">
                    <textarea class="form-control limited" type="text"
                              name="remark" rows="6">${shortMsgTpl.remark}</textarea>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer center">
        <a href="javascript:;" class="hideView btn btn-default"><i class="fa fa-times-circle-o"></i> 取消</a>
        &nbsp;&nbsp;
        <button id="submitBtn" type="button" class="btn btn-primary"
                data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
            <i class="fa fa-check-circle-o"></i> 确定
        </button>
    </div>
</div>
<script>
    $("#modalForm input[name=type]").click(function () {
        //console.log("type="+$(this).val())
        if ($(this).val() != '${CONTENT_TPL_TYPE_MSG}') {
            $(".wxMsg").not(".wxPic").show();
            $("#modalForm input[name=wxMsgType]").requireField(true);
            $("#modalForm input[name=wxTitle]").requireField(true);
        } else {
            $(".wxMsg").not(".wxPic").hide();
            $("#modalForm input[name=wxMsgType]").requireField(false);
            $("#modalForm input[name=wxTitle]").requireField(false);
        }
    });
    $("#modalForm input[name=wxMsgType]").click(function () {
        //console.log("wxMsgType="+$(this).val())
        if ($(this).val() == '${CONTENT_TPL_WX_TYPE_NEWS}') {
            $(".wxPic").show();
            //$("#modalForm input[name=wxPic]").requireField(true);
            $("#modalForm input[name=wxTitle]").requireField(true).closest(".wxMsg").show();
        } else {
            $(".wxPic").hide();
            //$("#modalForm input[name=wxPic]").requireField(false);
            $("#modalForm input[name=wxTitle]").requireField(false).closest(".wxMsg").hide();
        }
    });
    <c:if test="${not empty shortMsgTpl}">
        $("#modalForm input[name=type][value=${shortMsgTpl.type}]").click();
        <c:if test="${shortMsgTpl.type!=CONTENT_TPL_TYPE_MSG && not empty shortMsgTpl.wxMsgType}">
        $("#modalForm input[name=wxMsgType][value=${shortMsgTpl.wxMsgType}]").click();
        </c:if>
    </c:if>
    $('textarea.limited').inputlimiter();
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.hideView()
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>