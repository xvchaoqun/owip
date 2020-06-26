<%@ page import="sys.utils.RequestUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3 style="margin: 0">签到页面-${cetTrainCourse.cetCourse.name}
        (<a href="${ctx}/attach?code=sample_cet_sign_import" target="_blank">下载导入样表.xlsx</a>)
    </h3>
</div>
<div class="modal-body popup-jqgrid">

    <div class="tabbable">
        <ul class="nav nav-tabs" id="myTab">
            <li class="active">
                <a data-toggle="tab" href="#manual" aria-expanded="true">
                    <i class="green ace-icon fa fa-edit bigger-120"></i>
                    后台签到
                </a>
            </li>

            <li class="">
                <a data-toggle="tab" href="#card" aria-expanded="false">
                    <i class="green ace-icon fa fa-id-card bigger-120"></i>
                    刷卡签到
                </a>
            </li>

            <li class="">
                <a data-toggle="tab" href="#code" aria-expanded="false">
                    <i class="green ace-icon fa fa-id-card bigger-120"></i>
                    二维码签到
                </a>
            </li>
        </ul>

        <div class="tab-content" style="padding: 0">
            <div id="manual" class="tab-pane fade active in">

                <form class="form-inline search-form" id="popup_searchForm" style="padding-bottom: 0;float: left">
                    <input type="hidden" name="trainCourseId" value="${cetTrainCourse.id}">
                    <input type="hidden" name="projectId" value="${param.projectId}">
                    <div class="form-group">
                        <select data-rel="select2-ajax" data-width="230"
                                data-ajax-url="${ctx}/cet/cetProjectObj_selects?projectId=${param.projectId}"
                                name="userId" data-placeholder="请输入账号或姓名或教工号">
                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select data-rel="select2" data-width="150"
                                name="isFinished" data-placeholder="请选择是否已签到">
                            <option></option>
                            <option value="0">未签到</option>
                            <option value="1">已签到</option>
                        </select>
                        <script>
                            $("#popup_searchForm select[name=isFinished]").val('${param.isFinished}')
                            $('#popup_searchForm [data-rel="select2"]').select2();
                        </script>
                    </div>
                    <div class="form-group">
                        <c:set var="_query" value="${not empty param.userId||not empty param.isFinished}"/>
                        <button type="button" data-url="${ctx}/cet/cetTrainCourse_trainee"
                                data-target="#modal .modal-content" data-form="#popup_searchForm"
                                class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
                        </button>
                        <c:if test="${_query}">
                            <button type="button"
                                    data-url="${ctx}/cet/cetTrainCourse_trainee"
                                    data-querystr="trainCourseId=${param.trainCourseId}&projectId=${param.projectId}"
                                    data-target="#modal .modal-content"
                                    class="reloadBtn btn btn-warning btn-sm">
                                <i class="fa fa-reply"></i> 重置
                            </button>
                        </c:if>
                        <div class="btn-group">
                            <button id="opBtn" data-toggle="dropdown" class="btn btn-danger btn-sm dropdown-toggle">
                                <i class="fa fa-cog"></i> 签到操作
                                <span class="ace-icon fa fa-caret-down icon-on-right"></span>
                            </button>
                            <ul class="dropdown-menu dropdown-warning">
                                <li>
                                    <a href="javascript:;" id="signBtn" data-url="${ctx}/cet/cetTrainObj_sign"
                                       data-title="签到"
                                       data-msg="已选{0}位参训人员，确定签到？（已上课）"
                                       data-grid-id="#jqGrid_popup"
                                       data-querystr="sign=1"
                                       data-callback="_popupReload"
                                       class="jqBatchBtn"><i class="fa fa-arrow-right"></i> 批量签到</a>
                                </li>
                                <li>
                                    <a href="javascript:;" id="unSignBtn" data-url="${ctx}/cet/cetTrainObj_sign"
                                       data-title="还原"
                                       data-msg="已选{0}位参训人员，确定还原？（上课情况将重置为未上课）"
                                       data-grid-id="#jqGrid_popup"
                                       data-querystr="sign=0"
                                       data-callback="_popupReload"
                                       class="jqBatchBtn"><i class="fa fa-arrow-right"></i> 批量还原（未签到）</a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a href="javascript:;" class="confirm"
                                       data-url="${ctx}/cet/cetTrainObj_sign?trainCourseId=${param.trainCourseId}&sign=1"
                                       data-callback="_popupReload"
                                       data-title="全部签到"
                                       data-msg="确定全部签到？"><i class="fa fa-arrow-right"></i> 全部签到</a>
                                </li>
                                <li>
                                    <a href="javascript:;" class="confirm"
                                       data-url="${ctx}/cet/cetTrainObj_sign?trainCourseId=${param.trainCourseId}&sign=0"
                                       data-callback="_popupReload"
                                       data-title="全部还原"
                                       data-msg="确定全部还原？（上课情况将全部重置为未上课）"><i class="fa fa-arrow-right"></i> 全部还原（未签到）</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </form>
                <form class="form-inline search-form" id="popup_uploadForm"
                      action="${ctx}/cet/cetTrainObj_sign_import" method="post" style="padding-bottom: 0;">
                    <input type="hidden" name="trainCourseId" value="${cetTrainCourse.id}">
                    <div class="form-group" style="width: 200px;margin: 0 10px;">
                        <input class="form-control" type="file" name="xlsx" extension="xlsx"/>
                    </div>
                    <div class="form-group">
                        <button id="importBtn" type="button" class="btn btn-primary btn-sm">
                            <i class="fa fa-upload"></i> 导入签到
                        </button>
                    </div>
                </form>
                <table id="jqGrid_popup" class="table-striped"></table>
                <div id="jqGridPager_popup"></div>

            </div>

            <div id="card" class="tab-pane fade" style="margin: 35px;">
                <h4><i class="fa fa-arrow-circle-right green"></i> 如果当前浏览器是IE内核，请直接点击下面的链接进入签到页面</h4>
                <hr/>
                <div class="well">
                    <a href="${_homeUrl}/cet/sign?id=${cetTrainCourse.id}" target="_blank">
                        ${_homeUrl}/cet/sign?id=${cetTrainCourse.id}
                    </a>
                </div>
                <h4><i class="fa fa-arrow-circle-right red"></i> 如果当前浏览器不是IE内核，请点击下面的链接，复制成功后在IE浏览器中打开</h4>
                <hr/>
                <button class="confirm btn btn-success btn-sm"
                        data-msg="确定刷新？（刷新后原链接作废）"
                        data-callback="_refresh"
                        data-url="${ctx}/cet/updateSignToken?trainCourseId=${cetTrainCourse.id}">
                    <i class="fa fa-refresh"></i> 刷新签到链接</button>
                <div class="space-4"></div>
                <div class="well" style="display: ${empty cetTrainCourse.signToken?'none':''}">
                    <a href="javascript:;" id="copyBtn"  data-clipboard-target="#copyBtn">
                        ${_homeUrl}/cet/sign?id=${cetTrainCourse.id}&token=${cetTrainCourse.signToken}
                    </a>
                    <div>（该链接有效期截止时间：<span id="expire">${cm:formatDateTimeMillis(cetTrainCourse.signTokenExpire, "yyyy-MM-dd HH:mm:ss")}</span>）</div>
                </div>
            </div>
            <div id="code" class="tab-pane fade" style="margin: 35px;">
                <hr/>
                <div class="well">
                    <span>签到地址：</span>
                    <a href="${_homeUrl}/cet/codeSign?id=${cetTrainCourse.id}&cls=1" target="_blank">
                        ${_homeUrl}/cet/codeSign?id=${cetTrainCourse.id}&cls=1
                    </a>
                </div>
                <div class="well">
                    <span>签退地址：</span>
                    <a href="${_homeUrl}/cet/codeSign?id=${cetTrainCourse.id}&cls=0" target="_blank">
                        ${_homeUrl}/cet/codeSign?id=${cetTrainCourse.id}&cls=0
                    </a>
                </div>
                <hr/>
            </div>
        </div>
    </div>


</div>
<style>
    #popup_uploadForm label{
        margin-bottom: 0;
    }
</style>
<script type="text/template" id="failed_tpl">
    {{if(failedXlsRows.length>=1){}}
    <div style="color: red;max-height: 150px; overflow-y: auto; font-size: 14px; text-indent: 0px;">
        <table class="table table-bordered table-condensed table-unhover2 table-center">
            <thead>
            <tr>
                <th colspan="2" style="text-align: center">
                    {{=failedXlsRows.length}}条失败记录
                </th>
            </tr>
            <tr>
                <th style="width: 180px;">工号</th>
                <th>姓名</th>
            </tr>
            </thead>
            <tbody>
            {{_.each(failedXlsRows, function(r, idx){ }}
            <tr>
                <td>{{=r[0]}}</td>
                <td>{{=r[1]}}</td>
            </tr>
            {{});}}
            </tbody>
        </table>
    </div>
    {{}}}
</script>
<script src="${ctx}/extend/js/clipboard.min.js"></script>
<script>

    function _refresh(btn, ret){
        //console.log(ret)
        $("#copyBtn").html("${_homeUrl}/cet/sign?id=${cetTrainCourse.id}&token=" + ret.signToken)
            .closest("div").show();
        $("#expire").html($.date(ret.signTokenExpire, "yyyy-MM-dd HH:mm:ss"))
    }

    var clipboard = new ClipboardJS('#copyBtn');
    clipboard.on('success', function(e) {
        e.clearSelection();
        if($.trim(e.text)!='') {
            $.tip({
                $target: $("#copyBtn"),
                at: 'top right', my: 'bottom left', type: 'success',
                msg: "签到地址已经复制剪贴板，请粘贴至IE浏览器进行访问。"
            })
        }
    });
    clipboard.on('error', function(e) {
        $.tip({
            $target:$("#copyBtn"),
            at: 'top center', my: 'bottom center',
            msg: "复制失败，请使用Ctrl+C复制。"
        })
    });


    function _popupReload(){
        $("#opBtn").closest(".btn-group").removeClass("open");

        $("#jqGrid_popup").trigger("reloadGrid");
        $("#jqGrid2").trigger("reloadGrid");
    }
    $.register.user_select($("#popup_searchForm select[name=userId]"));

    var cetTraineeTypeMap = ${cm:toJSONObject(cetTraineeTypeMap)};
    $("#jqGrid_popup").jqGrid({
        height: 390,
        width: 950,
        rowNum: 10,
        ondblClickRow: function () {
        },
        pager: "jqGridPager_popup",
        url: "${ctx}/cet/cetTrainCourse_trainee_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: [
            {name:'isFinished', hidden:true},
              { label: '上课情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
                  return rowObject.isFinished?'<span class="text-success">已上课</span>':'<span class="text-danger">未上课</span>'
              }},
              {label: '签到时间', name: 'signTime', width: 160},
              {label: '签退时间', name: 'signOutTime', width: 160},
              {label: '签到方式', name: 'signType', width: 80, formatter: function (cellvalue, options, rowObject){
                  if(cellvalue==undefined) return '--'
                  return _cMap.CET_TRAINEE_SIGN_TYPE_MAP[cellvalue];
              } },
              {label: '参训人类别', name: 'traineeTypeId', width: 120, formatter: function (cellvalue, options, rowObject) {
                  return cetTraineeTypeMap[cellvalue].name;
              }},
              {label: '工作证号', name: 'user.code', width: 110, frozen: true},
              {label: '姓名', name: 'user.realname', width: 120, frozen: true},
        ],
        onSelectRow: function (id, status) {
            //saveJqgridSelected("#" + this.id, id, status);
            _popup_onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            //saveJqgridSelected("#" + this.id);
            _popup_onSelectRow(this)
        }
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");

    function _popup_onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");

        if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            var isFinished = (rowData.isFinished == "true");

            if(isFinished){
                $("#signBtn").addClass("disabled");
                $("#unSignBtn").removeClass("disabled");
            }else{
                $("#signBtn").removeClass("disabled");
                $("#unSignBtn").addClass("disabled");
            }
        }
    }


    $.fileInput($('#popup_uploadForm input[type=file]'))
    $("#importBtn").click(function () {
        var $file = $('#popup_uploadForm input[name="xlsx"]');
        var file = $file.val();
        if($.trim(file)==''){
            $.tip({
                $target: $file.closest(".ace-file-input"),
                at: 'top center', my: 'bottom center', type: 'info',
                msg: "请选择导入文件。"
            });
            return;
        }
        $("#popup_uploadForm").submit();
    });

    $("#popup_uploadForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){

                        if(ret && ret.successCount>=0){
                            var result = '导入完成，总共{0}条签到记录，其中成功导入{1}条签到记录';

                            //console.log($("#failed_tpl").html())
                            var failed =  _.template($("#failed_tpl").html().NoMultiSpace())({
                                failedXlsRows: ret.failedXlsRows
                            });


                            _popupReload();
                            SysMsg.success(result.format(ret.total, ret.successCount) + failed, '成功');
                            $('#popup_uploadForm input[type=file]').ace_file_input('reset_input');
                        }
                    }
                }
            });
        }
    });

</script>