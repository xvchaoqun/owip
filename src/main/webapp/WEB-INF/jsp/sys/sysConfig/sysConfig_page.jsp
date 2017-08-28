<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="page-header">

            <h1>
                <i class="fa fa-soundcloud"></i> 缓存管理
            </h1>
        </div>
        <div class="buttons">
            <button class="btn btn-success confirm btn-sm" data-url="${ctx}/cache/clear" data-callback="_reload" data-msg="确定清空系统缓存？">清空缓存
            </button>

            <button class="btn btn-success confirm btn-sm" data-url="${ctx}/cache/flush_metadata_JSON"
                    data-msg="重新生成元数据资源文件？">
                重新生成元数据资源文件（metadata.js）
            </button>

            <button class="btn btn-success confirm btn-sm" data-url="${ctx}/cache/flush_location_JSON"
                    data-msg="重新生成省地市资源文件？">
                重新生成省地市资源文件（location.js）
            </button>
        </div>
        <br/>

        <div class="page-header">
            <h1>
                <i class="fa fa-gears"></i> 系统设置
            </h1>
        </div>
        <form class="form-horizontal" action="${ctx}/sysConfig_au" id="configForm" method="post">

            <%-- <div class="form-group">
                 <label class="col-xs-3 control-label">xss不解析的请求</label>
                 <div class="col-xs-6">
                     <textarea class="form-control" name="xssIgnoreUri">${sysConfig.xssIgnoreUri}</textarea>
                 </div>
             </div>
             <div class="form-group">
                 <label class="col-xs-3 control-label">上传文件大小上限（单位M）</label>
                 <div class="col-xs-6">
                     <input  class="form-control" type="text" name="uploadMaxSize" value="${sysConfig.uploadMaxSize}">
                 </div>
             </div>
             <div class="form-group">
                 <label class="col-xs-3 control-label">短信发送接口</label>
                 <div class="col-xs-6">
                     <input  class="form-control" type="text" name="shortMsgUrl" value="${sysConfig.shortMsgUrl}">
                 </div>
             </div>--%>
            <div class="form-group">
                <label class="col-xs-3 control-label">学校名称</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="schoolName" value="${sysConfig.schoolName}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">版权说明</label>

                <div class="col-xs-6">
                    <textarea class="form-control" name="siteCopyright">${sysConfig.siteCopyright}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">首页地址</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="siteHome" value="${sysConfig.siteHome}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">平台关键字</label>

                <div class="col-xs-6">

                    <input class="form-control" type="text" name="siteKeywords" value="${sysConfig.siteKeywords}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">平台描述</label>

                <div class="col-xs-6">
                    <textarea class="form-control" name="siteDescription">${sysConfig.siteDescription}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">平台名称</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="siteName" value="${sysConfig.siteName}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">平台简称</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="siteShortName" value="${sysConfig.siteShortName}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">移动端平台名称</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="mobilePlantformName"
                           value="${sysConfig.mobilePlantformName}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">登录页LOGO（分辨率269*58，PNG格式）</label>

                <div class="col-xs-6 logo">
                    <input type="file" name="_logo" id="_logo"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">后台LOGO（分辨率269*58，PNG格式）</label>

                <div class="col-xs-6 logoWhite">
                    <input type="file" name="_logoWhite" id="_logoWhite"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">登录页背景（分辨率1920*890，JPG格式）</label>

                <div class="col-xs-6 loginBg">
                    <input type="file" name="_loginBg" id="_loginBg"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">登录页公告</label>

                <div class="col-xs-6">
                    <div style="float: left">
                    <textarea id="loginMsg">
                        ${sysConfig.loginMsg}
                    </textarea>
                        <input type="hidden" name="loginMsg">
                    </div>
                    <div style="float:left; margin-left: 10px; padding-top: 30px">
                        <a href="javascript:;" class="popupBtn btn btn-warning btn-xs"
                           data-width="750"
                           data-url="${ctx}/sysConfigLoginMsg_au"><i class="fa fa-save"></i> 保存为常用公告</a>

                        <div class="space-4"></div>

                        <div id="sysConfigLoginMsg">

                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">是否显示登录页公告</label>

                <div class="col-xs-6">
                    <input type="checkbox" class="big"
                           name="displayLoginMsg" ${(sysConfig.displayLoginMsg)?"checked":""}/>
                </div>
            </div>
        </form>

        <div class="clearfix form-actions center">
            <button class="hashchange btn btn-default" type="button">
                <i class="ace-icon fa fa-reply bigger-110"></i>
                重置
            </button>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button id="submitBtn" class="btn btn-info" type="button">
                <i class="ace-icon fa fa-check bigger-110"></i>
                更新
            </button>
        </div>


    </div>
</div>
<style>
    .ace-file-multiple .ace-file-container .ace-file-name.large:after {
        display: none;
    }

    .ace-file-multiple .ace-file-container .ace-file-name.large {
        border: none;
    }

    .logo .ace-file-input, .logoWhite .ace-file-input {
        width: 274px;
    }

    .loginBg .ace-file-input {
        width: 405px;
    }

</style>
<script type="text/javascript" src="${ctx}/extend/ke4/kindeditor-all-min.js"></script>
<script>
    $.fileInput($("#_logo"), {
        style: 'well',
        btn_choose: '更换登录页LOGO',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 269,
        previewHeight: 58,
        allowExt: ['png'],
        allowMime: ['image/png']
    });
    <c:if test="${not empty sysConfig.logo}">
    $("#_logo").find('button[type=reset]').on(ace.click_event, function () {
        //$('#user-profile input[type=file]').ace_file_input('reset_input');
        $("#_logo").ace_file_input('show_file_list', [{
            type: 'image',
            name: '${ctx}/pic?path=${cm:encodeURI(sysConfig.logo)}&_=<%=new Date().getTime()%>'
        }]);
    });
    $("#_logo").ace_file_input('show_file_list', [{
        type: 'image',
        name: '${ctx}/pic?path=${cm:encodeURI(sysConfig.logo)}&_=<%=new Date().getTime()%>'
    }]);
    </c:if>

    $.fileInput($("#_logoWhite"), {
        style: 'well',
        btn_choose: '更换内页LOGO',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 269,
        previewHeight: 58,
        allowExt: ['png'],
        allowMime: ['image/png']
    });
    <c:if test="${not empty sysConfig.logoWhite}">
    $("#_logoWhite").find('button[type=reset]').on(ace.click_event, function () {
        //$('#user-profile input[type=file]').ace_file_input('reset_input');
        $("#_logoWhite").ace_file_input('show_file_list', [{
            type: 'image',
            name: '${ctx}/pic?path=${cm:encodeURI(sysConfig.logoWhite)}&_=<%=new Date().getTime()%>'
        }]);
    });
    $("#_logoWhite").ace_file_input('show_file_list', [{
        type: 'image',
        name: '${ctx}/pic?path=${cm:encodeURI(sysConfig.logoWhite)}&_=<%=new Date().getTime()%>'
    }]);
    </c:if>

    $.fileInput($("#_loginBg"), {
        style: 'well',
        btn_choose: '更换登录页背景',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 400,
        previewHeight: 200,
        allowExt: ['jpg'],
        allowMime: ['image/jpg', 'image/jpeg']
    });
    <c:if test="${not empty sysConfig.loginBg}">
    $("#_loginBg").find('button[type=reset]').on(ace.click_event, function () {
        //$('#user-profile input[type=file]').ace_file_input('reset_input');
        $("#_loginBg").ace_file_input('show_file_list', [{
            type: 'image',
            name: '${ctx}/pic?path=${cm:encodeURI(cm:getShortPic(sysConfig.loginBg))}&_=<%=new Date().getTime()%>'
        }]);
    });
    $("#_loginBg").ace_file_input('show_file_list', [{
        type: 'image',
        name: '${ctx}/pic?path=${cm:encodeURI(cm:getShortPic(sysConfig.loginBg))}&_=<%=new Date().getTime()%>'
    }]);
    </c:if>

    var ke = KindEditor.create('#loginMsg', {
        filterMode: false,
        allowFileManager: true,
        items: [
            'source', '|', 'undo', 'redo', '|', 'preview', 'cut', 'copy', 'paste',
            'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
            'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
            'superscript', 'clearhtml', 'quickformat', 'selectall',
            'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
            'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
            'flash', 'media', 'insertfile', 'table', 'hr',
            'anchor', 'link', 'unlink', '|', 'fullscreen'
        ],
        uploadJson: '${ctx}/ke/upload_json',
        fileManagerJson: '${ctx}/ke/file_manager_json',
        height: '350px',
        width: '400px',
        minWidth: 400,
        cssPath: '${ctx}/assets/css/font-awesome.css'
    });

    $("#submitBtn").click(function () {
        $("#configForm").submit();
        return false;
    })
    $("#configForm").validate({
        submitHandler: function (form) {

            $("input[name=loginMsg]", form).val(ke.html());
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        SysMsg.info("更新成功");
                        $.hashchange();
                    }
                }
            });
        }
    });

    function _reload(){
        $.hashchange();
    }
    function _reloadLoginMsg() {
        $("#sysConfigLoginMsg").load("${ctx}/sysConfigLoginMsg?_=" + new Date().getTime());
    }
    _reloadLoginMsg();

    $("#configForm :checkbox").bootstrapSwitch();
    $('#configForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>