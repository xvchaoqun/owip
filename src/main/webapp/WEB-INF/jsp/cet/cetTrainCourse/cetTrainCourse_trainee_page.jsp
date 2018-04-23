<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3 style="margin: 0">签到页面-${cetTrainCourse.cetCourse.name}</h3>
</div>
<div class="modal-body popup-jqgrid" style="padding-top: 0">
    <form class="form-inline search-form" id="popup_searchForm" style="padding-bottom: 0;float: left">
        <input type="hidden" name="trainCourseId" value="${cetTrainCourse.id}">
        <div class="form-group">
            <label>姓名</label>
            <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?types=${USER_TYPE_JZG}"
                    name="userId" data-placeholder="请输入账号或姓名或教工号">
                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
            </select>
        </div>
        <div class="form-group">
            <c:set var="_query" value="${not empty param.userId}"/>
            <button type="button" data-url="${ctx}/cet/cetTrainCourse_trainee"
                    data-target="#modal .modal-content" data-form="#popup_searchForm"
                    class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
            </button>
            <c:if test="${_query}">
                <button type="button"
                        data-url="${ctx}/cet/cetTrainCourse_trainee"
                        data-querystr="trainCourseId=${param.trainCourseId}"
                        data-target="#modal .modal-content"
                        class="resetBtn btn btn-warning btn-sm">
                    <i class="fa fa-reply"></i> 重置
                </button>
            </c:if>

            <button type="button" id="signBtn" data-url="${ctx}/cet/cetTraineeCourse_sign"
                    data-title="签到"
                    data-msg="已选{0}位参训人员，确定签到？（已上课）"
                    data-grid-id="#jqGrid_popup"
                    data-querystr="sign=1"
                    data-callback="_popupReload"
                    class="jqBatchBtn btn btn-success btn-sm">
                <i class="fa fa-check-circle-o"></i> 签到
            </button>
            <button type="button" id="unSignBtn" data-url="${ctx}/cet/cetTraineeCourse_sign"
                    data-title="还原"
                    data-msg="已选{0}位参训人员，确定还原？（未上课）"
                    data-grid-id="#jqGrid_popup"
                    data-querystr="sign=0"
                    data-callback="_popupReload"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-times-circle-o"></i> 还原
            </button>
        </div>
    </form>
    <form class="form-inline search-form" id="popup_uploadForm"
          action="${ctx}/cet/cetTraineeCourse_sign_import" method="post" style="padding-bottom: 0;">
        <input type="hidden" name="trainCourseId" value="${cetTrainCourse.id}">
        <div class="form-group" style="width: 200px;margin-left: 40px;">
            <div style="margin: 5px;">
            <input class="form-control" type="file" name="xlsx" extension="xlsx"/>
            </div>
        </div>
        <div class="form-group">
        <button id="importBtn" type="button" class="btn btn-primary btn-sm">
            <i class="fa fa-upload"></i> 导入
        </button>
            (<a href="${ctx}/attach?code=sample_cet_sign_import" target="_blank">导入样表.xlsx</a>)
        </div>
    </form>
    <table id="jqGrid_popup" class="table-striped"></table>
    <div id="jqGridPager_popup"></div>
</div>
<jsp:include page="../cetTrainee/cetTrainee_colModel.jsp?type=sign"/>
<script>
    function _popupReload(){
        $("#jqGrid_popup").trigger("reloadGrid");
        $("#jqGrid2").trigger("reloadGrid");
    }
    $.register.user_select($("#popup_searchForm select[name=userId]"));
    $("#jqGrid_popup").jqGrid({
        height: 390,
        width: 950,
        rowNum: 10,
        ondblClickRow: function () {
        },
        pager: "jqGridPager_popup",
        url: "${ctx}/cet/cetTrainCourse_trainee_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: colModel,
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

            $("#signBtn").prop("disabled", isFinished);
            $("#unSignBtn").prop("disabled", !isFinished);
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
                            _popupReload();
                            SysMsg.success(result.format(ret.total, ret.successCount), '成功');
                            $('#popup_uploadForm input[type=file]').ace_file_input('reset_input');
                        }
                    }
                }
            });
        }
    });

</script>