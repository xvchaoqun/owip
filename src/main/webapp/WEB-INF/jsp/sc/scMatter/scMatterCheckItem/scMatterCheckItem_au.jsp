<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    核查情况表预览
                    <div style="position: absolute; left:150px;top:8px;">
                        <form action="${ctx}/sc/scMatterCheckItem_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <button type="button"
                                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                    class="hideView btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传核查情况表
                            </button>
                            <input type="file" name="file" id="upload-file"/>
                        </form>
                    </div>
                    <div class="buttons pull-right ">

                        <a href="javascript:;" class="hideView btn btn-xs btn-success"
                           style="margin-right: 10px; top: -5px;">
                            <i class="ace-icon fa fa-backward"></i>
                            返回</a>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <div id="dispatch-file-view">
                        <c:import url="${ctx}/swf/preview?type=html&path=${scMatterCheckItem.checkFile}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="au">
        <div id="dispatch-cadres-view">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        ${scMatterCheck!=null?"修改":"添加"}核查情况
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal" action="${ctx}/sc/scMatterCheckItem_au" id="modalForm" method="post"
                              enctype="multipart/form-data">
                            <div class="row">
                                <input type="hidden" name="id" value="${scMatterCheckItem.id}">
                                <input type="hidden" name="checkFile" value="${scMatterCheckItem.checkFile}">
                                <div class="form-group">
                                    <label class="col-xs-5 control-label">对比日期</label>
                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" name="compareDate" type="text"
                                                   data-date-format="yyyy-mm-dd"
                                                   value="${cm:formatDate(scMatterCheckItem.compareDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-5 control-label">干部监督机构核查结果</label>
                                    <div class="col-xs-6">
                                        <select data-rel="select2" id="_resultType" data-placeholder="请选择" data-width="240">
                                            <option></option>
                                            <c:import url="/metaTypes?__code=mc_sc_matter_check_result_type"/>
                                        </select>
                                        <div class="space-4"></div>
                                        <input class="form-control" type="text" name="resultType"
                                               value="${scMatterCheckItem.resultType}">

                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-5 control-label">本人说明材料</label>
                                    <div class="col-xs-6">
                                        <input class="form-control" type="file" name="_selfFile"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-5 control-label">认定结果</label>
                                    <div class="col-xs-6">
                                        <select required data-rel="select2"
                                                name="confirmType" data-placeholder="请选择" data-width="120">
                                            <option></option>
                                            <c:forEach items="${SC_MATTER_CHECK_ITEM_CONFIRM_TYPE_MAP}" var="_type">
                                                <option value="${_type.key}">${_type.value}</option>
                                            </c:forEach>
                                        </select>
                                        <script>
                                            $("#modalForm select[name=confirmType]").val('${scMatterCheckItem.confirmType}')
                                        </script>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-5 control-label">认定日期</label>
                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" name="confirmDate" type="text"
                                                   data-date-format="yyyy-mm-dd"
                                                   value="${cm:formatDate(scMatterCheckItem.confirmDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-5 control-label">干部管理机构处理意见</label>
                                    <div class="col-xs-6">
                                        <select data-rel="select2" id="_handleType" data-placeholder="请选择" data-width="240">
                                            <option></option>
                                            <c:import url="/metaTypes?__code=mc_sc_matter_check_handle_type"/>
                                        </select>
                                        <div class="space-4"></div>
                                        <input class="form-control" type="text" name="handleType"
                                               value="${scMatterCheckItem.handleType}">
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <button class="btn btn-success btn-sm" type="submit">
                                    <i class="ace-icon fa fa-check "></i>
                                   提交
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    $("#_resultType").change(function(){
        //console.log($(this).select2("data"))
        $("#modalForm input[name=resultType]").val($(this).select2("data")[0].text);
    });
    $("#_handleType").change(function(){
        //console.log($(this).select2("data"))
        $("#modalForm input[name=handleType]").val($(this).select2("data")[0].text);
    });

    $.fileInput($("#modalForm input[name=_selfFile]"),{
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
    register_date($('.date-picker'));
    $('#modalForm [data-rel="select2"]').select2();
    $("#upload-file").change(function () {
        if ($("#upload-file").val() != "") {
            var $this = $(this);
            var $form = $this.closest("form");
            var $btn = $("button", $form).button('loading');
            var viewHtml = $("#dispatch-file-view").html()
            $("#dispatch-file-view").html('<img src="${ctx}/img/loading.gif"/>')
            $form.ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //console.log(ret)
                        $("#dispatch-file-view").load("${ctx}/swf/preview?type=html&path=" + encodeURI(ret.file));
                        $("#modalForm input[name=checkFile]").val(ret.file);
                    } else {
                        $("#dispatch-file-view").html(viewHtml)
                    }
                    $btn.button('reset');
                    $this.removeAttr("disabled");
                }
            });
            $this.attr("disabled", "disabled");
        }
    });

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {

            if($.trim($("#modalForm input[name=checkFile]").val())==""){
                SysMsg.warning("请上传核查情况表");
                return;
            }
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.hideView();
                    }
                }
            });
        }
    });
</script>