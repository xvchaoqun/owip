<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>导出带分党委党支部编号的表</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" autocomplete="off"
          action="${ctx}/branchPbCodeExport" method="post"
          disableautocomplete id="modalForm" enctype="multipart/form-data">
        <div class="form-group">
            <label class="col-xs-5 control-label"><span class="star">*</span>Excel文件</label>
            <div class="col-xs-6">
                <input type="file" name="xlsx" required extension="xlsx"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-5 control-label">读取第几个sheet</label>
            <div class="col-xs-6">
                <input class="form-control num"
                       data-rule-min="1"
                       type="text" name="sheetNo" style="width: 100px">
                <span class="help-block">注：留空将读取第1个sheet</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-5 control-label"><span class="star">*</span>依据字段"党支部名称"所在列数</label>
            <div class="col-xs-6">
                <input required class="form-control num"
                       data-rule-min="1"
                       type="text" name="branchNameCol" style="width: 100px">
            </div>
        </div>
    </form>
    <div class="well" style="margin-bottom: 0">
        <ul>
            <li>仅处理第一个sheet</li>
            <li>党支部编号默认将插入倒数第二列，对应的分党委的编码插入倒数第一列</li>
            <li>根据以上条件，系统如果仍不能确定，则对应的行的编号留空</li>
        </ul>
    </div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口"> 确定
    </button>
</div>
<script>

    $(".modal-body :checkbox").bootstrapSwitch();

    $.fileInput($('#modalForm input[type=file]'), {
        allowExt: ['xlsx']
    })

    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });

    $("#modalForm").validate({
        messages: {
            "xlsx": {
                required: "请选择文件",
                extension: "请上传 xlsx格式的文件"
            }
        },
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                dataType: "json",
                success: function (ret) {
                    if(ret.success){

                        var url = ("${ctx}/attach_download?path={0}&filename={1}")
                            .format(ret.file, ret.filename)
                        //console.log("url=" + url)
                        $btn.download(url);
                    }else{
                        $btn.button('reset');
                    }
                }
            });
        }
    });

</script>