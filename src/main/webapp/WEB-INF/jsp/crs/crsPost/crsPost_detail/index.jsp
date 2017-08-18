<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box transparent">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>

                </h4>
                <span class="text text-info bolder" style="cursor: auto">
                    招聘岗位：${crsPost.name}
                </span>
                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" class="loadPage"
                               data-load-el="#step-content" data-callback="_menuSelected"
                               data-url="${ctx}/crsPost_detail/step?id=${param.id}&step=1">
                                <i class="green ace-icon fa fa-bullhorn bigger-120"></i> 公告和资格</a>
                        </li>
                        <li>
                            <a href="javascript:;" class="loadPage"
                               data-load-el="#step-content" data-callback="_menuSelected"
                               data-url="${ctx}/crsPost_detail/step?id=${param.id}&step=2">
                                <i class="green ace-icon fa fa-gears bigger-120"></i> 报名和审核</a>
                        </li>
                        <li>
                            <a href="javascript:;" class="loadPage"
                               data-load-el="#step-content" data-callback="_menuSelected"
                               data-url="${ctx}/crsPost_detail/step?id=${param.id}&step=3">
                                <i class="green ace-icon fa fa-users bigger-120"></i> 招聘会</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-12 no-padding-left no-padding-right no-padding-bottom">
                    <div class="tab-content padding-4" id="step-content">
                    <c:import url="${ctx}/crsPost_detail/step?id=${param.id}&step=1"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    function _stepContentReload(){
        $("#step-content li.active .loadPage").click()
    }

    function _menuSelected($aHref){

        var $nav = $aHref.closest(".nav");
        $("li", $nav).removeClass("active");
        $aHref.closest("li").addClass("active");
    }

    $("#upload-file").change(function () {
        //console.log($(this).val())
        if ($(this).val() != "") {
            var $this = $(this);
            var $form = $this.closest("form");
            var $btn = $("button", $form).button('loading');
            var preHtml = $(".swf-file-view").html();
            $(".swf-file-view").html('<img src="${ctx}/img/loading.gif"/>')
            $form.ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //console.log(ret)
                        $(".swf-file-view").load("${ctx}/swf/preview?type=html&path=" + encodeURI(ret.file));

                        $("#modalForm input[name=file]").val(ret.file);
                        $("#modalForm input[name=fileName]").val(ret.fileName);
                    }else{
                        $(".swf-file-view").html(preHtml);
                    }
                    $btn.button('reset');
                    $this.attr("disabled", false);
                }
            });
            $this.attr("disabled", true);
        }
    });
</script>