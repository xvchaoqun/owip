<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>导出带工号的表</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" autocomplete="off"
          action="${ctx}/sysUser_filterExport" method="post"
          disableautocomplete id="modalForm" enctype="multipart/form-data">
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>Excel文件</label>
            <div class="col-xs-6">
                <input type="file" name="xlsx" required extension="xlsx"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">读取第几个sheet</label>
            <div class="col-xs-6">
                <input class="form-control num"
                       data-rule-min="1"
                       type="text" name="sheetNo" style="width: 100px">
                <span class="help-block">注：留空将读取第1个sheet</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>依据字段</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input checked type="radio" name="colType" id="colType0" value="0">
                        <label for="colType0">
                            身份证号
                        </label>
                    </div>
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input type="radio" name="colType" id="colType1" value="1">
                        <label for="colType1">
                            姓名
                        </label>
                    </div>
                </div>
                <c:if test="${_p_hasPartyModule}">
                <span class="help-block">注：按身份证号查找时，按教职工、研究生、本科生的顺序读取第一个账号（如账号类型重复则忽略）</span>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>依据字段所在列数</label>
            <div class="col-xs-6">
                <input required class="form-control num"
                       data-rule-min="1"
                       type="text" name="col" style="width: 100px">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">工号插入列数</label>
            <div class="col-xs-6">
                <input class="form-control num"
                       data-rule-min="1"
                       type="text" name="addCol" style="width: 100px">
                <span class="help-block">注：留空将插入最后一列</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">身份</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input type="radio" name="roleType" id="roleType1" value="1">
                        <label for="roleType1">
                            干部
                        </label>
                    </div>
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input checked type="radio" name="roleType" id="roleType0" value="0">
                        <label for="roleType0">
                            混合
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${_p_hasPartyModule}">
        <div class="form-group">
            <label class="col-xs-4 control-label">类别</label>
            <div class="col-xs-8">
                <div class="input-group">
                    <c:forEach var="userType" items="${USER_TYPE_MAP}">
                        <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                            <input type="radio" name="type" id="type${userType.key}" value="${userType.key}">
                            <label for="type${userType.key}">
                                    ${userType.value}
                            </label>
                        </div>
                    </c:forEach>
                    <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                        <input checked type="radio" name="type" id="type0" value="0">
                        <label for="type0">
                            混合
                        </label>
                    </div>
                </div>
                <span class="help-block">注：身份是干部时，此选项无效</span>
            </div>
        </div>
        </c:if>
    </form>
    <div class="well" style="margin-bottom: 0">
        <ul>
            <li>仅处理第一个sheet</li>
            <li>工号将插入最后一列</li>
            <li>根据以上条件，系统如果仍不能确定唯一性，则对应的行的工号留空</li>
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