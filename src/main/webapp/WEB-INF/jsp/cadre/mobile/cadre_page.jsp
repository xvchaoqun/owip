<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="ace-settings-container" id="ace-settings-container">
    <div class="btn btn-app btn-xs btn-info ace-settings-btn" id="ace-settings-btn">
        <i class="ace-icon fa fa-search bigger-130"></i>
    </div>
    <div class="ace-settings-box clearfix" id="ace-settings-box">
        <div style="height: 35px;margin: 15px 0px;">
            <select data-rel="select2-ajax"
                    data-ajax-url="${ctx}/m/cadre_selects?status=${status}" data-width="300"
                    name="cadreId" data-placeholder="请输入账号或姓名或学工号">
                <option value="${cadre.id}">${sysUser.realname}-${sysUser.code}</option>
            </select>
        </div>
    </div>
</div>
<div id="result"></div>
<script src="/assets/js/ace/ace.settings.js"></script>
<script>

    $("#result").load("${ctx}/m/cadre_info");
    var $select = $.register.ajax_select($('select[name=cadreId]'),{allowClear: false,
        templateResult: $.register.formatState,
        templateSelection: $.register.templateSelection});
    $select.on("change",function(){
        $("#result").load("${ctx}/m/cadre_info?cadreId="+ $(this).val());
        $('#ace-settings-box').toggleClass('open');
    })
</script>