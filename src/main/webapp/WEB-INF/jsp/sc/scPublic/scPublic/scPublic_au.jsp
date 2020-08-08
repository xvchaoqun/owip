<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">
                    公示预览
                    <div style="position: absolute; left:130px;top:0px;">
                        <form action="${ctx}/sc/scPublic_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <button type="button"
                                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                    class="hideView btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传公示
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
                        <c:import url="${ctx}/pdf_preview?type=html&path=${cm:sign(scPublic.pdfFilePath)}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="au">
        <div id="dispatch-cadres-view">
            <div class="widget-box" id="dispatch-widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        公示信息
                    </h4>
                    <span class="red" style="padding-left: 100px;">（点击收起/展开）</span>
                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal" action="${ctx}/sc/scPublic_process"
                              autocomplete="off" disableautocomplete id="modalForm" method="post">
                            <input type="hidden" name="id" value="${scPublic.id}">
                            <input type="hidden" name="pdfFilePath" value="${cm:sign(scPublic.pdfFilePath)}">

                            <div class="form-group">
                                <label class="col-xs-3 control-label"><span class="star">*</span>年度</label>
                                <div class="col-xs-7">
                                    <div class="input-group">
                                        <input required class="form-control date-picker" placeholder="请选择年份"
                                               name="year"
                                               type="text"
                                               data-date-format="yyyy" data-date-min-view-mode="2"
                                               value="${empty scPublic.year?_thisYear:scPublic.year}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label"
                                       style="white-space: nowrap"><span class="star">*</span>党委常委会</label>
                                <div class="col-xs-9">
                                    <select required name="committeeId" data-rel="select2"
                                            data-width="272"
                                            data-placeholder="请选择">
                                        <option></option>
                                        <c:forEach var="scCommittee" items="${scCommittees}">
                                            <option value="${scCommittee.id}">
                                                党委常委会[${cm:formatDate(scCommittee.holdDate, "yyyyMMdd")}]号
                                            </option>
                                        </c:forEach>
                                    </select>
                                    <script>
                                        $("#modalForm select[name=committeeId]").val("${scPublic.committeeId}");
                                    </script>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label"><span class="star">*</span>公示发布时间</label>

                                <div class="col-xs-7">
                                    <div class="input-group">
                                        <input required class="form-control date-picker"
                                               name="publishDate"
                                               type="text"
                                               data-date-format="yyyy-mm-dd"
                                               value="${empty scPublic.publishDate?_today:(cm:formatDate(scPublic.publishDate,'yyyy-MM-dd'))}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label"><span class="star">*</span>公示开始时间</label>
                                <div class="col-xs-7">
                                    <div class="input-group">
                                        <input class="form-control datetime-picker required" type="text"  name="publicStartDate"
                                               value="${empty scPublic.publicStartDate?_todayMinute:(cm:formatDate(scPublic.publicStartDate,'yyyy-MM-dd HH:mm'))}">
                                                <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label"><span class="star">*</span>公示结束时间</label>
                                <div class="col-xs-7">
                                    <div class="input-group">
                                        <input class="form-control datetime-picker required" type="text"  name="publicEndDate"
                                               value="${empty scPublic.publicEndDate?_todayMinute:(cm:formatDate(scPublic.publicEndDate,'yyyy-MM-dd HH:mm'))}">
                                                <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">上传公示（WORD版）</label>

                                <div class="col-xs-7 label-text">
                                    <input class="form-control" type="file" name="_wordFilePath"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">备注</label>
                                <div class="col-xs-7">
											<textarea class="form-control limited"
                                                      name="remark">${scPublic.remark}</textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="cadres">
                <div class="widget-box">
                    <div class="widget-header">
                        <h4 class="widget-title">
                            公示对象
                            <div class="buttons pull-right" style="right:45px;">
                                <button id="selectUsersBtn"
                                        class="btn btn-success btn-xs" type="button"
                                        onclick="_selectUsers()">
                                    <i class="fa fa-plus-circle"></i> 从“干部选拔任用表决”中选择
                                </button>
                            </div>
                        </h4>
                        <div class="widget-toolbar">
                            <a href="#" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-down"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main" style="padding:5px">
                            <table id="jqGrid2" data-width-reduce="20"
                                   class="table-striped"></table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="clearfix form-actions center">
                <button class="btn btn-info" type="button" onclick="_preview()">
                    <i class="ace-icon fa fa-search bigger-110"></i>
                    预览
                </button>
                &nbsp;
                <button class="btn btn-primary" type="button" onclick="_download(this)">
                    <i class="ace-icon fa fa-download bigger-110"></i>
                    下载公示
                </button>
                &nbsp;
                <button class="btn btn-success" type="button"
                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"
                        id="submitBtn">
                    <i class="ace-icon fa fa-save bigger-110"></i>
                    提交
                </button>
            </div>
        </div>
    </div>
</div>
<script>

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
                        $("#dispatch-file-view").load("${ctx}/pdf_preview?type=html&path=" + ret.filePath);

                        $("#modalForm input[name=pdfFilePath]").val(ret.filePath);
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

    var $committeeId = $("#modalForm select[name=committeeId]");
    $committeeId.change(function () {
        //var committeeId = $committeeId.val();
        $("#jqGrid2").jqGrid("clearGridData");
    });

    function _selectUsers() {
        event.stopPropagation();

        if ($.trim($committeeId.val()) == '') {
            $.tip({
                $target: $committeeId.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请先选择党委常委会。"
            });
            return;
        }

        $.loadModal("${ctx}/sc/scPublic_users?committeeId=" + $committeeId.val(), 900);
    }

    function _preview() {

        if ($('#modalForm').valid()) {
            var voteIds = $("#jqGrid2").jqGrid("getDataIDs");
            if (voteIds.length == 0) {
                $.tip({
                    $target: $("#selectUsersBtn"),
                    at: 'top center', my: 'bottom center', type: 'info',
                    msg: "请选择公示对象。"
                });
                return;
            }

            $.loadModal("${ctx}/sc/scPublic_process?export=0&voteIds[]=" + voteIds + "&" + $("#modalForm").serialize(), 700);
        }
    }

    function _download(btn) {

        if ($('#modalForm').valid()) {
            var voteIds = $("#jqGrid2").jqGrid("getDataIDs");
            if (voteIds.length == 0) {
                $.tip({
                    $target: $("#selectUsersBtn"),
                    at: 'top center', my: 'bottom center', type: 'info',
                    msg: "请选择公示对象。"
                });
                return;
            }

            var url = "${ctx}/sc/scPublic_process?export=1&voteIds[]=" + voteIds + "&" + $("#modalForm").serialize();
            $(btn).download(url);
        }
    }

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');

            var voteIds = $("#jqGrid2").jqGrid("getDataIDs")
            $(form).ajaxSubmit({
                data: {voteIds: voteIds},
                success: function (ret) {
                    if (ret.success) {
                        //$("#modal").modal('hide');
                        $.hideView()
                        $("#jqGrid").trigger("reloadGrid");
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    var votes = ${cm:toJSONArray(votes)};
    $("#jqGrid2").jqGrid({
        pager: null,
        responsive: false,
        rownumbers: true,
        multiselect: false,
        height: 220,
        width: 480,
        datatype: "local",
        rowNum: votes.length,
        data: votes,
        //党委常委会日期、 类别、 工作证号、姓名、 原任职务、 职务
        colModel: [
            {
                label: '移除', name: '_remove', width: 90, formatter: function (cellvalue, options, rowObject) {
                //console.log(options)
                return '<button class="delRowBtn btn btn-danger btn-xs" type="button" data-id="{0}"><i class="fa fa-minus-circle"></i> 移除</button>'
                        .format(rowObject.id)
            }
            },
            {label: '工作证号', name: 'user.code'},
            {label: '姓名', name: 'user.realname'},
            {label: '原任职务', name: 'originalPost', width: 280, align: 'left'},
            {label: '拟任职务', name: 'post', width: 280, align: 'left'}
        ],
        gridComplete: function () {
            //$("#modalForm input, .panel input, .panel select").prop("disabled", true);
        }
    });
    $("#jqGrid2").jqGrid('sortableRows')
    //$(window).triggerHandler('resize.jqGrid4');

    $(document).on("click", ".delRowBtn", function () {

        var $jqGrid = $("#jqGrid2");
        var $count = $(this).closest(".panel").find(".tip .count");
        // console.log($(this).data("gid"))
        $jqGrid.delRowData($(this).data("id"));
        $count.html($jqGrid.jqGrid("getDataIDs").length);
    })

    $.fileInput($("#modalForm input[name=_wordFilePath]"), {
        allowExt: ['doc', 'docx']
    });
    $.fileInput($("#modalForm input[name=_pdfFilePath]"), {
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
    $.register.date($('.date-picker'));
    $.register.datetime($('.datetime-picker'));
    $('#modalForm [data-rel="select2"]').select2();
</script>