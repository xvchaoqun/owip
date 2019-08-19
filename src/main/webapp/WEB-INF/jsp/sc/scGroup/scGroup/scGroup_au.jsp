<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">
                    干部小组会议题预览
                    <div style="position: absolute; left:200px;top:0px;">
                        <form action="${ctx}/sc/scGroup_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <button type="button"
                                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                    class="hideView btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传干部小组会议题
                            </button>
                            <input type="file" name="file" id="upload-file"/>
                        </form>
                    </div>
                    <div class="buttons pull-right" style="right:15px;">

                        <a href="javascript:;" class="hideView btn btn-xs btn-success">
                            <i class="ace-icon fa fa-backward"></i>
                            返回</a>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <div id="dispatch-file-view">
                        <c:import url="${ctx}/pdf_preview?type=html&path=${scGroup.filePath}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="au">
        <div id="dispatch-cadres-view">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        ${scGroup!=null?"修改":"添加"}干部小组会
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal" action="${ctx}/sc/scGroup_au" autocomplete="off" disableautocomplete id="modalForm" method="post"
                              enctype="multipart/form-data">
                            <div class="row">
                                <input type="hidden" name="id" value="${scGroup.id}">
                                <input type="hidden" name="filePath" value="${scGroup.filePath}">

                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>年份</label>

                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" placeholder="请选择年份"
                                                   name="year"
                                                   type="text"
                                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                                   value="${empty scGroup?_thisYear:scGroup.year}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>干部小组会日期</label>

                                    <div class="col-xs-6">
                                        <div class="input-group">
                                            <input required class="form-control date-picker" name="holdDate"
                                                   type="text"
                                                   data-date-format="yyyy-mm-dd"
                                                   value="${cm:formatDate(scGroup.holdDate,'yyyy-MM-dd')}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label"><span class="star">*</span>议题数量</label>

                                    <div class="col-xs-6">
                                        <input required class="form-control num" type="text" name="topicNum" value="${scGroup.topicNum}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">议题word版</label>
                                    <div class="col-xs-6">
                                        <input class="form-control" type="file" name="_wordFilePath"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">会议记录</label>
                                    <div class="col-xs-6">
                                        <input class="form-control" type="file" name="_logFile"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">参会人</label>
                                    <div class="col-xs-8">
                                        <div id="tree3" style="height: 200px;" class="noborder">
                                            <div style="height: 200px;line-height: 200px;font-size: 20px">
                                                <i class="fa fa-spinner fa-spin"></i> 加载中，请稍后...
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-4 control-label">列席人</label>

                                    <div class="col-xs-6">
                                        <input class="form-control" type="text" name="attendUsers" value="${scGroup.attendUsers}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-xs-4 control-label">备注</label>

                                    <div class="col-xs-6">
                                        <textarea class="form-control limited"
                                                  name="remark">${scGroup.remark}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <button class="btn btn-info btn-sm" type="submit">
                                    <i class="ace-icon fa fa-check "></i>
                                    ${scGroup!=null?"修改":"添加"}
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
    $.fileInput($("#modalForm input[name=_wordFilePath]"),{
        allowExt: ['doc', 'docx'],
        //allowMime: ['application/pdf']
    });
    $.fileInput($("#modalForm input[name=_logFile]"),{
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });

    $.getJSON("${ctx}/sc/scGroup_selectMembers_tree", {groupId: "${scGroup.id}"}, function (data) {
        var treeData = data.tree.children;
        $("#tree3").dynatree({
            checkbox: true,
            selectMode: 3,
            children: treeData,
            onSelect: function (select, node) {

                node.expand(node.data.isFolder && node.isSelected());
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });
    });

    $.register.user_select($('#modalForm [data-rel="select2-ajax"]'));
    $.register.date($('.date-picker'));
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
                        $("#dispatch-file-view").load("${ctx}/pdf_preview?type=html&path=" + encodeURI(ret.filePath));

                        $("#modalForm input[name=filePath]").val(ret.filePath);
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
            var userIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
                if (!node.data.isFolder && !node.data.hideCheckbox)
                    return node.data.key;
            });
           /* console.log(userIds.length);
            return;*/
            if (userIds.length==0) {
                $.tip({
                    $target: $("#tree3"),
                    at: 'left center', my: 'right center', type: 'info',
                    msg: "请选择参会人"
                });
                return;
            }
            $(form).ajaxSubmit({
                data: {userIds: userIds},
                success: function (ret) {
                    if (ret.success) {
                        $.hideView();
                    }
                }
            });
        }
    });
</script>