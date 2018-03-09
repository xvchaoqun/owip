<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>包含课程</h3>
</div>
<div class="modal-body">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    添加课程
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/cet/cetColumnCourse_au" id="modalForm" method="post">
                        <input type="hidden" name="columnId" value="${param.columnId}">
                        <div class="form-group">
                            <label class="col-xs-3 control-label">包含课程</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/cet/cetCourse_selects?isOnline=${cetColumn.isOnline?1:0}"
                                        name="courseId" data-placeholder="请输入课程名称">
                                    <option></option>
                                </select></div>
                        </div>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button class="btn btn-info btn-sm" type="submit">
                                    <i class="ace-icon fa fa-check "></i>
                                    确定
                                </button>
                                &nbsp; &nbsp; &nbsp;
                                <button class="btn btn-default btn-sm" type="reset">
                                    <i class="ace-icon fa fa-undo"></i>
                                    重置
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="space-10"></div>
    <div class="popTableDiv">
        <table id="jqGrid_popup" class="jqGrid table-striped"></table>
        <div id="jqGridPager_popup"></div>
    </div>
</div>
<script>

    function _callback_popup(){
        $("#jqGrid_popup").trigger("reloadGrid");
        $("#jqGrid").trigger("reloadGrid");

    }

    $('#modalForm [data-rel="select2"]').select2();
    $("#modal button[type=submit]").click(function(){$("#modalForm").submit(); return false;})
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal button[type=reset]").click();
                        _callback_popup();
                    }
                }
            });
        }
    });
    register_ajax_select($('#modal [data-rel="select2-ajax"]'));

    $("#jqGrid_popup").jqGrid({
        pager:"#jqGridPager_popup",
        url: '${ctx}/cet/cetColumnCourse_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '课程',name: 'courseName',width:320},
            {
                label: '排序', align: 'center', index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid_popup', url: "${ctx}/cet/cetColumnCourse_changeOrder"}
            },
            { label: '操作',name: '_op',formatter: function(cellvalue, options, rowObject){

                return ('<button class="confirm btn btn-xs btn-danger" ' +
                        'data-url="${ctx}/cet/cetColumnCourse_batchDel?ids[]={0}" data-msg="确定删除？" '
                        +'data-callback="_callback_popup"><i class="fa fa-times"></i> 删除</button>').format(rowObject.id)
            }},
        ]
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");
</script>