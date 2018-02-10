<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;"><c:if test="${scPublic!=null}">编辑</c:if><c:if
                            test="${scPublic==null}">添加</c:if>干部任前公示</a>
                </li>
            </ul>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main">
            <div class="tab-content" style="padding-bottom: 0;padding-top: 0">
                <form class="form-horizontal" action="${ctx}/sc/scPublic_process"
                      id="modalForm" method="post">
                    <div class="row dispatch_cadres" style="width: 1450px;padding-bottom: 0;padding-top: 0">
                        <div class="dispatch" style="width: 450px;margin-right: 0">
                            <div class="widget-box" style="width: 430px;">
                                <div class="widget-header">
                                    <h4 class="smaller">
                                        公示信息
                                    </h4>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main" style="margin-bottom: 10px; ">
                                        <input type="hidden" name="id" value="${scPublic.id}">

                                        <div class="form-group">
                                            <label class="col-xs-3 control-label"
                                                   style="white-space: nowrap">党委常委会</label>

                                            <div class="col-xs-9">
                                                <select required name="committeeId" data-rel="select2"
                                                        data-width="230"
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
                                            <label class="col-xs-3 control-label">公示时间</label>

                                            <div class="row col-xs-9">

                                                <input required class="date-picker"
                                                       style="width: 104px;margin-left: 12px;"
                                                       name="publicStartDate"
                                                       type="text"
                                                       data-date-format="yyyy-mm-dd"
                                                       value="${empty scPublic.publishDate?today:(cm:formatDate(scPublic.publicStartDate,'yyyy-MM-dd'))}"/>
                                                至
                                                <input required class="date-picker" style="width: 104px;"
                                                       name="publicEndDate"
                                                       type="text"
                                                       data-date-format="yyyy-mm-dd"
                                                       value="${empty scPublic.publishDate?today:(cm:formatDate(scPublic.publicEndDate,'yyyy-MM-dd'))}"/>

                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">发布时间</label>

                                            <div class="col-xs-6">
                                                <div class="input-group" style="width: 230px;">
                                                    <input required class="form-control date-picker"
                                                           name="publishDate"
                                                           type="text"
                                                           data-date-format="yyyy-mm-dd"
                                                           value="${empty scPublic.publishDate?today:(cm:formatDate(scPublic.publishDate,'yyyy-MM-dd'))}"/>
                                            <span class="input-group-addon"> <i
                                                    class="fa fa-calendar bigger-110"></i></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">备注</label>

                                            <div class="col-xs-7">
											<textarea class="form-control limited" style="width: 230px;"
                                                      name="remark">${scPublic.remark}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="widget-box" style="width: 430px;">
                                <div class="widget-header">
                                    <h4 class="smaller">
                                        上传最终版公示
                                    </h4>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main" style="margin-bottom: 10px; ">
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">年度</label>

                                            <div class="col-xs-7">
                                                <div class="input-group">
                                                    <input class="form-control date-picker" placeholder="请选择年份"
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
                                            <label class="col-xs-3 control-label">公示编号</label>

                                            <div class="col-xs-7">
                                                <input class="form-control num" type="text" name="num"
                                                       value="${scPublic.num}">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">WORD</label>

                                            <div class="col-xs-7">
                                                <input class="form-control" type="file" name="_wordFilePath"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-xs-3 control-label">PDF</label>

                                            <div class="col-xs-7">
                                                <input class="form-control" type="file" name="_pdfFilePath"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="cadres">
                            <div class="widget-box" style="width: 950px;">
                                <div class="widget-header">
                                    <h4 class="smaller">
                                        公示对象
                                        <div class="buttons pull-right ">
                                            <button style="margin-right: 10px; top: -5px;"
                                                    class="btn btn-success btn-xs" type="button"
                                                    onclick="_selectUsers()">
                                                <i class="fa fa-plus-circle"></i> 从“干部选拔任用表决”中选择
                                            </button>
                                        </div>
                                    </h4>
                                </div>
                                <div class="widget-body">
                                    <div class="widget-main" style="margin-bottom: 10px">
                                        <table id="jqGrid2" data-width-reduce="30"
                                               class="table-striped"></table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="clearfix form-actions center">
        <button class="btn btn-info" type="button" onclick="_preview()">
            <i class="ace-icon fa fa-search bigger-110"></i>
            预览
        </button>
        &nbsp;
        <button class="btn btn-primary" type="button" onclick="_download()">
            <i class="ace-icon fa fa-download bigger-110"></i>
            下载公示
        </button>
        &nbsp;
        <button class="btn btn-success" type="button" id="submitBtn">
            <i class="ace-icon fa fa-save bigger-110"></i>
            提交
        </button>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    var $committeeId = $("#modalForm select[name=committeeId]");
    $committeeId.change(function () {
        //var committeeId = $committeeId.val();
        $("#jqGrid2").jqGrid("clearGridData");
    });

    function _selectUsers() {
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
                SysMsg.info("请选择公示对象。");
                return;
            }

            $.loadModal("${ctx}/sc/scPublic_process?export=0&voteIds[]=" + voteIds + "&" + $("#modalForm").serialize(), 700);
        }
    }

    function _download() {

        if ($('#modalForm').valid()) {
            var voteIds = $("#jqGrid2").jqGrid("getDataIDs");
            if (voteIds.length == 0) {
                SysMsg.info("请选择公示对象。");
                return;
            }

            location.href = "${ctx}/sc/scPublic_process?export=1&voteIds[]=" + voteIds + "&" + $("#modalForm").serialize();
        }
    }

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var voteIds = $("#jqGrid2").jqGrid("getDataIDs")
            $(form).ajaxSubmit({
                data: {voteIds: voteIds},
                success: function (ret) {
                    if (ret.success) {
                        //$("#modal").modal('hide');
                        $.hideView()
                        $("#jqGrid").trigger("reloadGrid");
                    }
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
        height: 390,
        width: 925,
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
            {label: '职务', name: 'post', width: 280, align: 'left'}
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
    register_date($('.date-picker'));
    $('#modalForm [data-rel="select2"]').select2();
</script>