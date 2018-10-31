<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    文件签发稿预览
                    <div style="position: absolute; left:180px;top:8px;">
                        <form action="${ctx}/sc/scDispatch_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <button type="button"
                                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                    class="hideView btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传文件签发稿
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
                        <c:import url="${ctx}/swf/preview?type=html&path=${scDispatch.filePath}"/>
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
                        签发稿信息
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal" action="${ctx}/sc/scDispatch_au"
                              id="modalForm" method="post">
                            <input type="hidden" name="id" value="${scDispatch.id}">
                            <input type="hidden" name="filePath" value="${scDispatch.filePath}">

                            <div class="form-group">
                                <label class="col-xs-3 control-label">年度</label>
                                <div class="col-xs-7">
                                    <div class="input-group">
                                        <input required class="form-control date-picker" placeholder="请选择年份"
                                               name="year"
                                               type="text"
                                               data-date-format="yyyy" data-date-min-view-mode="2"
                                               value="${scDispatch.year}"/>
                                                    <span class="input-group-addon"> <i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">发文类型</label>
                                <div class="col-xs-7">
                                    <c:set var="dispatchType"
                                           value="${dispatchTypeMap.get(scDispatch.dispatchTypeId)}"/>
                                    <select required data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/dispatchType_selects"
                                            name="dispatchTypeId" data-placeholder="请选择发文类型"
                                            data-width="272">
                                        <option value="${dispatchType.id}">${dispatchType.name}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">发文号</label>

                                <div class="col-xs-7">
                                    <input required class="form-control digits" type="text" name="code"
                                           autocomplete="off" disableautocomplete
                                           value="${scDispatch.code}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">标题</label>

                                <div class="col-xs-7">
                                    <input required class="form-control" type="text" name="title"
                                           autocomplete="off" disableautocomplete
                                           value="${scDispatch.title}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label"
                                       style="white-space: nowrap">党委常委会</label>

                                <div class="col-xs-9">
                                    <select data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/sc/scCommittee_selects"
                                            data-width="272" name="committeeId"
                                            data-placeholder="请选择或输入日期(YYYYMMDD)">
                                        <option></option>
                                    </select>

                                    <button type="button" class="btn btn-success btn-sm"
                                            onclick="_selectCommittee()"><i
                                            class="fa fa-plus"></i> 选择
                                    </button>
                                    <div style="padding-top: 10px;">
                                        <div id="itemList" class="itemList">

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">党委常委会日期</label>

                                <div class="col-xs-7">
                                    <div class="input-group">
                                        <input class="form-control date-picker" name="meetingTime"
                                               type="text"
                                               data-date-format="yyyy-mm-dd"
                                               value="${cm:formatDate(scDispatch.meetingTime,'yyyy-MM-dd')}"/>
                                                    <span class="input-group-addon"> <i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">起草日期</label>

                                <div class="col-xs-7">
                                    <div class="input-group">
                                        <input required class="form-control date-picker" name="pubTime"
                                               type="text"
                                               data-date-format="yyyy-mm-dd"
                                               value="${cm:formatDate(scDispatch.pubTime,'yyyy-MM-dd')}"/>
                                                    <span class="input-group-addon"> <i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">文件签发稿（WORD版）</label>

                                <div class="col-xs-7">
                                    <input class="form-control" type="file" name="_wordFilePath"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">备注</label>

                                <div class="col-xs-7">
											<textarea class="form-control limited"
                                                      name="remark">${scDispatch.remark}</textarea>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="smaller">
                        任免对象
                        <div class="buttons pull-right ">
                            <button style="margin-right: 10px;top: -5px;"
                                    class="btn btn-success btn-xs" type="button"
                                    onclick="_selectUsers()">
                                <i class="fa fa-plus-circle"></i> 从“干部选拔任用表决”中选择
                            </button>
                            <%--<button style="margin-right: 10px; top: -5px;"
                                    class="btn btn-primary btn-xs" type="button"
                                    onclick="_download()">
                                <i class="fa fa-download"></i> 导出已选择的任免对象
                            </button>--%>
                        </div>
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main" style="margin-bottom: 10px">
                        <table id="jqGrid1" data-width-reduce="30"
                               class="table-striped"></table>
                        <div class="space-4"></div>
                        <table id="jqGrid2" data-width-reduce="30"
                               class="table-striped"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="clearfix form-actions center" style="margin-top: 0">
    <button class="btn btn-success" type="button" id="submitBtn">
        <i class="ace-icon fa fa-save bigger-110"></i>
        提交
    </button>
</div>
<script type="text/template" id="itemListTpl">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td>年份</td>
            <td>党委常委会编号</td>
            <td width="60"></td>
        </tr>
        </thead>
        <tbody>
        {{_.each(items, function(item, idx){ }}
        <tr data-item-id="{{=item.id}}" data-year="{{=item.realname}}" data-hold-date="{{=item.holdDate}}">
            <td>{{=item.year}}</td>
            <td>党委常委会[{{=$.date(item.holdDate,'yyyyMMdd')}}]号</td>
            <td>
                <a href="javasciprt:;" class="del">移除</a>
            </td>
        </tr>
        {{});}}
        </tbody>
    </table>
</script>
<script>
    var selectedItems = ${cm:toJSONArrayWithFilter(itemList, "id,year,holdDate")};
    $("#itemList").append(_.template($("#itemListTpl").html())({items: selectedItems}));

    var $committeeId = $("#modalForm select[name=committeeId]");
    function _selectCommittee() {
        var committeeId = $.trim($committeeId.val());
        if (committeeId == '') {
            $.tip({
                $target: $committeeId.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "请选择党委常委会。"
            });
            return;
        }
        var hasSelected = false;
        $.each(selectedItems, function (i, item) {
            if (item.id == committeeId) {
                hasSelected = true;
                return false;
            }
        })
        if (hasSelected) {
            $.tip({
                $target: $committeeId.closest("div").find(".select2-container"),
                at: 'top center', my: 'bottom center', type: 'success',
                msg: "您已经选择了该党委常委会。"
            });
            return;
        }

        var year = $committeeId.select2("data")[0]['year'] || '';
        var holdDate = $committeeId.select2("data")[0]['holdDate'] || '';
        var item = {id: committeeId, year: year, holdDate: holdDate};

        //console.log(item)
        selectedItems.push(item);
        if(selectedItems.length==1){
            $("#modalForm input[name=meetingTime]").val($.date(selectedItems[0].holdDate, "yyyy-MM-dd"))
        }else{
            $("#modalForm input[name=meetingTime]").val('')
        }
        $("#itemList").empty().append(_.template($("#itemListTpl").html())({items: selectedItems}));
    }
    $(document).off("click", "#itemList .del")
            .on('click', "#itemList .del", function () {
                var $tr = $(this).closest("tr");
                var id = $tr.data("item-id");
                //console.log("userId=" + userId)
                $.each(selectedItems, function (i, item) {
                    if (item.id == id) {
                        selectedItems.splice(i, 1);
                        return false;
                    }
                })
                if(selectedItems.length==1){
                    $("#modalForm input[name=meetingTime]").val($.date(selectedItems[0].holdDate, "yyyy-MM-dd"))
                }else{
                    $("#modalForm input[name=meetingTime]").val('')
                }
                $(this).closest("tr").remove();
            });

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
                        $("#dispatch-file-view").load("${ctx}/swf/preview?type=html&path=" + encodeURI(ret.filePath));

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
    //$.register.dispatchType_select($('#modalForm select[name=dispatchTypeId]'), $("#modalForm input[name=year]"));

    // 选择年份，然后选择发文类型、党委常委会
    {
        var $year = $("#modalForm input[name=year]");
        var $select =$('#modalForm select[name=dispatchTypeId], #modalForm select[name=committeeId]');
        $year.on("change", function () {
            $select.val(null).trigger("change");
            $("#itemList").html('');
        });
        var t = $select.select2({
            language: {
                noResults: function (term) {
                    return "请先选择年份";
                }
            },
            templateResult: $.register.formatState,
            ajax: {
                dataType: 'json',
                delay: 300,
                data: function (params) {
                    return {
                        year: $year.val() || -1,
                        searchStr: params.term,
                        pageSize: 10,
                        pageNo: params.page
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;
                    return {
                        results: data.options, pagination: {
                            more: (params.page * 10) < data.totalCount
                        }
                    };
                },
                cache: true
            }
        });
    }


    $.fileInput($("#modalForm input[name=_wordFilePath]"), {
        no_file: '请上传WORD文件 ...',
        allowExt: ['doc', 'docx']
    });
    $.fileInput($("#modalForm input[name=_pdfFilePath]"), {
        no_file: '请上传PDF文件 ...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });

    function _selectUsers() {
        if (selectedItems.length == 0) {
            $.tip({
                //$target: $committeeId.closest("div").find(".select2-container"),
                $target: $committeeId.closest("div").find(".btn"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请先选择党委常委会。"
            });
            return;
        }

        var committeeIds = $.map(selectedItems, function (item) {
            return item.id;
        }).join(",");

        $.loadModal("${ctx}/sc/scDispatch_users?committeeIds[]=" + committeeIds, 1200);
    }

    /*function _download() {

     if ($('#modalForm').valid()) {
     var voteIds = [];
     voteIds.push($("#jqGrid1").jqGrid("getDataIDs"));
     voteIds.push($("#jqGrid2").jqGrid("getDataIDs"));

     if (voteIds.length == 0) {
     SysMsg.info("请选择任免对象。");
     return;
     }

     location.href = "${ctx}/sc/scDispatchUser_export?voteIds[]=" + voteIds;
     }
     }*/

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var committeeIds = $.map(selectedItems, function (item) {
                return item.id;
            });
            var voteIds = [];
            voteIds.push.apply(voteIds, $("#jqGrid1").jqGrid("getDataIDs"));
            voteIds.push.apply(voteIds, $("#jqGrid2").jqGrid("getDataIDs"));
            //console.log(voteIds);
            //return;
            $(form).ajaxSubmit({
                data: {committeeIds: committeeIds, voteIds: voteIds},
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
    var votes1 = [];
    var votes2 = [];
    $.each(${cm:toJSONArray(votes)}, function (i, vote) {
        //console.log("vote.type=" + vote.type);
        if (vote.type == 1) {
            votes1.push(vote);
        } else if (vote.type == 2) {
            votes2.push(vote);
        }
    });
    var _colModel = [
        {
            label: '移除', name: '_remove', width: 90, formatter: function (cellvalue, options, rowObject) {
            //console.log(options)
            return '<button class="delRowBtn btn btn-danger btn-xs" type="button" data-id="{0}"  data-gid="{1}"><i class="fa fa-minus-circle"></i> 移除</button>'
                    .format(rowObject.id, options.gid)
        }
        },
        {label: '工作证号', name: 'user.code', width: 90},
        {label: '姓名', name: 'user.realname', width: 90},
        {label: '原任职务', name: 'originalPost', width: 250, align: 'left'},
        {label: '职务', name: 'post', width: 250, align: 'left'}, {hidden: true, key: true, name: 'id'}
    ];
    $("#jqGrid1").jqGrid({
        caption: '<span><i class="ace-icon fa fa-check-circle-o"></i> 任命</span><div class="pull-right red">（注：可拖动排序）</div>',
        hidegrid: false,
        pager: null,
        responsive: false,
        rownumbers: true,
        multiselect: false,
        height: 170,
        width: 480,
        datatype: "local",
        rowNum: votes1.length,
        data: votes1,
        //党委常委会日期、 类别、 工作证号、姓名、 原任职务、 职务
        colModel: _colModel
    });
    $("#jqGrid1").jqGrid('sortableRows')
    $("#jqGrid2").jqGrid({
        caption: '<span><i class="ace-icon fa fa-times-circle-o"></i> 免职</span><div class="pull-right red">（注：可拖动排序）</div>',
        hidegrid: false,
        pager: null,
        responsive: false,
        rownumbers: true,
        multiselect: false,
        height: 170,
        width: 480,
        datatype: "local",
        rowNum: votes2.length,
        data: votes2,
        //党委常委会日期、 类别、 工作证号、姓名、 原任职务、 职务
        colModel: _colModel
    });
    $("#jqGrid2").jqGrid('sortableRows')
    //$(window).triggerHandler('resize.jqGrid4');

    $(document).on("click", ".delRowBtn", function () {

        var $jqGrid = $("#" + $(this).data("gid"));
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
    $('#modalForm [data-rel="select2"]').select2();
    //$.register.ajax_select($('#modalForm select[name=committeeId]'), {maximumInputLength: 8})
</script>