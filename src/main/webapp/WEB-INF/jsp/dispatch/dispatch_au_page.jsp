<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row dispatch_cadres" style="width: 1300px">
    <div class="dispatch">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    任免文件预览
                    <div class="buttons pull-right ">
                        <a href="javascript:;" class="closeView btn btn-mini btn-xs btn-primary"
                           style="margin-right: 10px; top: -5px;">
                            <i class="ace-icon fa fa-upload"></i>
                            上传任免文件</a>
                    <a href="javascript:;" class="closeView btn btn-mini btn-xs btn-success"
                       style="margin-right: 10px; top: -5px;">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <div id="dispatch-file-view">
                        <c:import url="/swf_preview?id=${dispatch.id}&way=3&type=file"/>
                    </div>
                </div></div></div>
    </div>
    <div class="cadres" style="width:500px;">
        <div id="dispatch-cadres-view">
            <c:import url="/dispatch_au?id=${dispatch.id}"/>
        </div>
    </div>
</div>
<script>
    register_date($('.date-picker'));
    register_dispatch_select($('.dispatch_cadres select[name=dispatchTypeId]'),
            $(".dispatch_cadres input[name=year]"), $(".dispatch_cadres select[name=dispatchId]"));
</script>