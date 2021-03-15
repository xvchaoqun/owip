<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-body"  style="min-width: 1200px">
<form class="form-horizontal" action="${ctx}/user/modifyBaseApply_au" autocomplete="off" disableautocomplete id="updateForm" method="post" enctype="multipart/form-data">
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main no-padding">
            <div class="tab-content padding-8">
                <jsp:include page="form.jsp"/>
            </div>
        </div>
    </div>
</div>
<!-- /.widget-box -->
    <div class="clearfix form-actions center">
        <c:if test="${not empty mba}">
            您的申请已提交，请等待审核。
            <%--<button class="btn btn-primary" type="button" id="updateBtn">
                <i class="ace-icon fa fa-edit bigger-110"></i>
                修改
            </button>
            &nbsp;--%>
            <a href="javascript:;" class="confirm<%-- btn btn-warning--%>"
                    data-url="${ctx}/user/modifyBaseApply_back?ids=${mba.id}" data-title="撤销申请"
                    data-msg="确定撤销申请吗？" data-callback="_applyBack"><i class="fa fa-reply"></i> 撤销申请
            </a>
            <script>
                $("input, textarea, select", "#updateForm").prop("disabled", true);
            </script>
        </c:if>
        <c:if test="${empty mba}">
            <button id="updateBtn" type="button" class="btn btn-primary"
                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中">
                <i class="ace-icon fa fa-check-circle-o bigger-110"></i> 提交申请
            </button>

        &nbsp; &nbsp; &nbsp;
        <button class="hideView btn btn-default" type="button">
            <i class="ace-icon fa fa-undo bigger-110"></i>
            返回
        </button>
        </c:if>
    </div>
</form>
</div>

<script>
    function _applyBack(){
        $.openView("${ctx}/user/modifyBaseApply_au")
    }
    <c:if test="${not empty mbis}">
    var mbis = ${cm:toJSONArray(mbis)};

    $.each(mbis, function (i, mbi) {
        if(mbi.code=='avatar'){
            $("#_avatarTitle").addClass("text-danger bolder");
            $("#avatarDiv img").attr('src', '${ctx}/avatar?path={0}'.format(mbi.signModifyValue));
        }else {
            var $item = $("[data-code='{0}'][data-table='{1}']".format(mbi.code, mbi.tableName));
            $item.val(mbi.modifyValue);
            $item.closest("td").prev().addClass("text-danger bolder");
        }
    })
    </c:if>
    $("#updateBtn").click(function () {
        $("#updateForm").submit();
        return false;
    })
    $("#updateForm").validate({
        submitHandler: function (form) {

            var codes=[], tables=[], tableIdNames=[], names=[], originals=[], modifys=[], types=[];
            $("*[data-code]").each(function(){
                codes.push($(this).data("code"));
                tables.push($(this).data("table"));
                tableIdNames.push($(this).data("table-id-name"));
                names.push($(this).data("name"));
                var ori = $(this).data("original");
                var mod = $(this).val()
                //console.log(ori + "=" + mod)
                //console.log(base64.encode(ori+"") + "=" + base64.encode(mod+""))
                originals.push($.base64.encode(ori+''));
                modifys.push($.base64.encode(mod+''));
                types.push($(this).data("type"));
            })
            //console.log(codes)
            //console.log(originals)
            //console.log(modifys)
            var $btn = $("#updateBtn").button('loading');
            $(form).ajaxSubmit({
                data:{codes:codes, tables:tables, tableIdNames:tableIdNames,
                    names:names, originals:originals, modifys:modifys, types:types},
                success: function (ret) {
                    if (ret.success) {
                        $("#jqGrid").trigger("reloadGrid");
                        $.hashchange();
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $.register.date($('.date-picker'));
</script>