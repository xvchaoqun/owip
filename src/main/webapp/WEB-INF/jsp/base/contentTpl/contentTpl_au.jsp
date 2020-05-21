<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${_pMap['wx_support']=='true'}" var="_p_wx_support"/>
<c:set value="<%=ContentTplConstants.CONTENT_TPL_CONTENT_TYPE_STRING%>" var="CONTENT_TPL_CONTENT_TYPE_STRING"/>
<c:set value="<%=ContentTplConstants.CONTENT_TPL_CONTENT_TYPE_HTML%>" var="CONTENT_TPL_CONTENT_TYPE_HTML"/>
<c:set value="<%=ContentTplConstants.CONTENT_TPL_TYPE_MSG%>" var="CONTENT_TPL_TYPE_MSG"/>
<c:set value="<%=ContentTplConstants.CONTENT_TPL_WX_TYPE_NEWS%>" var="CONTENT_TPL_WX_TYPE_NEWS"/>
<div style="width: 900px">
    <div class="modal-header no-padding">
        <h3>${contentTpl!=null?'编辑':'添加'}消息模板</h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" action="${ctx}/contentTpl_au"
              data-tip-container="#page-content"
              autocomplete="off" disableautocomplete
              id="modalForm" method="post">
            <input type="hidden" name="id" value="${contentTpl.id}">
            <input type="hidden" name="contentType" value="${contentType}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>模板名称</label>
                <div class="col-xs-8">
                    <input required class="form-control" type="text" name="name" value="${contentTpl.name}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">模板代码</label>
                <div class="col-xs-8">
                    <input class="form-control" type="text" name="code" value="${contentTpl.code}">
                    <span class="help-block">*留空自动生成</span>
                </div>
            </div>
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
                    <input class="form-control" type="text" name="wxTitle" value="${contentTpl.wxTitle}">
                </div>
            </div>
            <div class="form-group wxMsg wxPic" style="display: none">
                <label class="col-xs-3 control-label">微信图片地址</label>
                <div class="col-xs-8">
                    <input class="form-control url" type="text" name="wxPic" value="${contentTpl.wxPic}">
                </div>
            </div>
            <div class="form-group wxMsg">
                <label class="col-xs-3 control-label">微信跳转地址</label>
                <div class="col-xs-8">
                    <input class="form-control" type="text" name="wxUrl" value="${contentTpl.wxUrl}">
                    <span class="help-block">最终跳转地址为：${_p_siteHome}+微信跳转地址；如无需跳转请留空。</span>
                </div>
            </div>
                </c:if>
            <c:if test="${contentType==CONTENT_TPL_CONTENT_TYPE_STRING}">
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>模板内容</label>
                    <div class="col-xs-8">
                        <textarea required class="form-control" name="content" rows="8">${contentTpl.content}</textarea>
                    </div>
                </div>
            </c:if>
            <%--<div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>模板引擎</label>
                <div class="col-xs-6">
                    <select required name="engine" data-rel="select2" data-width="200"
                            data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="<%=ContentTplConstants.CONTENT_TPL_ENGINE_MAP%>" var="type">
                            <option value="${type.key}">${type.value}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=engine]").val('${contentTpl.engine}');
                    </script>
                </div>
            </div>--%>
            <div class="form-group">
                <label class="col-xs-3 control-label">备注</label>
                <div class="col-xs-8">
                    <textarea class="form-control" name="remark">${contentTpl.remark}</textarea>
                </div>
            </div>
            <c:if test="${contentType==CONTENT_TPL_CONTENT_TYPE_HTML}">
                <div style="margin-left: auto; margin-right: auto;max-width: 600px; margin-top: 10px;">
            <textarea id="content">
                    ${contentTpl.content}
            </textarea>
                    <input type="hidden" name="content">
                </div>
            </c:if>
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
    <c:if test="${not empty contentTpl}">
        $("#modalForm input[name=type][value=${contentTpl.type}]").click();
        <c:if test="${contentTpl.type!=CONTENT_TPL_TYPE_MSG && not empty contentTpl.wxMsgType}">
        $("#modalForm input[name=wxMsgType][value=${contentTpl.wxMsgType}]").click();
        </c:if>
    </c:if>
    <c:if test="${contentType==CONTENT_TPL_CONTENT_TYPE_HTML}">
    var ke = KindEditor.create('#content', {
        allowFileManager: true,
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '300px',
        width: '700px'
    });
    </c:if>

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            <c:if test="${contentType==CONTENT_TPL_CONTENT_TYPE_HTML}">
            $("#modalForm input[name=content]").val(ke.html());
            </c:if>
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.hideView()
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });

    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>