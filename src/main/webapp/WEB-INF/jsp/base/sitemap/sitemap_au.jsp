<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3><c:if test="${not empty param.fid}"><span class="text-error">${parent.title}</span>-</c:if>
        <c:if test="${not empty param.id}"><span class="text-error">${sitemap.title}</span>-</c:if></h3>
</div>
<div class="modal-body">
    <form:form method="post" autocomplete="off" id="modalForm" commandName="sitemap" class="form-horizontal">
        <form:hidden path="id"/>
        <%--<form:hidden path="fids"/>--%>

        <c:if test="${not empty parent}">
            <div class="form-group">
                <label class="col-xs-3 control-label">
                    父节点
                </label>

                <div class="col-xs-6 ">
                    <select name="fid" data-width="275" data-ajax-url="${ctx}/sitemap_selects">
                        <option value="${parent.id}">${parent.title}</option>
                    </select>
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-3 control-label">
                <c:if test="${param.fid>0}">子</c:if>节点名称
            </label>

            <div class="col-xs-6 ">
                <form:input path="title" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">
                关联资源
            </label>

            <div class="col-xs-6 ">
                <select name="resourceId" data-width="275" data-ajax-url="${ctx}/sysResource_selects?isMobile=0">
                    <option value="${sysResource.id}">${sysResource.name}(${sysResource.permission})</option>
                </select>
            </div>
        </div>
        <div class="form-group menuNeeded">
            <label class="col-xs-3 control-label">排序</label>

            <div class="col-xs-6 ">
                <form:input path="sortOrder" class="form-control"/>
            </div>
        </div>
        <div class="form-group menuNeeded">
            <label class="col-xs-3 control-label">URL路径</label>

            <div class="col-xs-6 ">
                <form:input path="url" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>

            <div class="col-xs-6">
                <form:textarea path="remark" class="form-control limited"/>
            </div>
        </div>
    </form:form>
</div>
<div class="modal-footer">
    <a href="javascript:;" class="btn btn-default" data-dismiss="modal">取消</a> <input type="submit" class="btn btn-primary"
                                                                           value="确定"/>
</div>

<script>

    $.register.ajax_select($("#modal select[name=resourceId]"),
            {dropdownCssClass: "bigdrop", placeholder: "请选择资源"})

    $.register.ajax_select($("#modal select[name=fid]"),
            {dropdownCssClass: "bigdrop", placeholder: "请选择上级对象"})

    $("#typeSelect").select2({theme: "classic", width: '180px', allowClear: true})
    /*.on("select2:select",function(e){

     if($(this).val()=="function"){
     $(".menuNeeded").hide();
     }else{
     $(".menuNeeded").show();
     }
     });*/
    function format(state) {
        if (!state.id) return state.text; // optgroup
        return $("<span><i class='" + state.id.toLowerCase() + "'></i> " + state.text + "</span>");
    }
    $("#menuCssSelect").select2({
        templateResult: format,
        templateSelection: format,
        theme: "classic", width: '250px'
    }).change(function () {
        $("#modalForm input[name=menuCss]").val($(this).val());
    });
    $(function () {

        //$('#modalForm input[type=checkbox],input[type=radio],input[type=file]').uniform();

        $("#modal input[type=submit]").click(function () {
            $("#modal form").submit();
            return false;
        });
        $("#modal form").validate({
            rules: {
                name: {
                    required: true
                },
                type: {
                    required: true
                },
                sortOrder: {
                    digits: true
                },
                permission: {
                    required: true
                }
            },
            submitHandler: function (form) {

                $(form).ajaxSubmit({
                    success: function (data) {
                        if (data.success) {
                            _reload();
                            //SysMsg.success('操作成功。', '成功');
                        }
                    }, error: function (ret) {

                        SysMsg.success("系统异常，请稍后再试。");
                    }
                });
            }
        });
    })
</script>