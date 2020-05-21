<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <jsp:include page="menu.jsp"/>
        <div class="space-4"></div>
        <form class="form-horizontal" action="${ctx}/sysConfig_au" id="configForm" method="post">

            <div class="form-group">
                <label class="col-xs-3 control-label">所在城市</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="city" value="${sysConfig.city}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">学校名称</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="schoolName" value="${sysConfig.schoolName}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">学校简称</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="schoolShortName" value="${sysConfig.schoolShortName}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">学校门户网址</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="schoolLoginUrl" value="${sysConfig.schoolLoginUrl}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">版权说明</label>

                <div class="col-xs-6">
                    <textarea class="form-control" name="siteCopyright">${sysConfig.siteCopyright}</textarea>
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
                <label class="col-xs-3 control-label">移动端平台Title</label>

                <div class="col-xs-6">
                    <input class="form-control" type="text" name="mobileTitle"
                           value="${sysConfig.mobileTitle}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">favicon.ico, ICO格式（48*48）</label>

                <div class="col-xs-6">
                    <input type="file" name="_favicon" id="_favicon"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">登录页LOGO（heigh<=70px，PNG格式）</label>

                <div class="col-xs-6">
                    <input type="file" name="_logo" id="_logo"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">后台LOGO（heigh<=70px，透明PNG格式）</label>

                <div class="col-xs-6">
                    <input type="file" name="_logoWhite" id="_logoWhite"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">登录页顶部背景图片（1102*109，JPG格式）</label>

                <div class="col-xs-6">
                    <input type="file" name="_loginTop" id="_loginTop"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">登录页顶部背景色</label>

                <div class="col-xs-6">
                    <input style="width: 100px;background-color: ${sysConfig.loginTopBgColor}" class="form-control"
                           type="text" name="loginTopBgColor"
                           value="${sysConfig.loginTopBgColor}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">登录页背景（1920*890，JPG格式）</label>

                <div class="col-xs-6">
                    <input type="file" name="_loginBg" id="_loginBg"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">线上民主推荐登录页背景图片（840*383，PNG格式）</label>

                <div class="col-xs-6">
                    <input type="file" name="_drLoginBg" id="_drLoginBg"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">iphone桌面图标, ICO格式</label>

                <div class="col-xs-6">
                    <input type="file" name="_appleIcon" id="_appleIcon"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">iphone桌面图标，PNG格式</label>

                <div class="col-xs-6">
                    <input type="file" name="_screenIcon" id="_screenIcon"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">二维码LOGO（90*90，PNG格式）</label>

                <div class="col-xs-6">
                    <input type="file" name="_qrLogo" id="_qrLogo"/>
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
            <div class="form-group">
                <label class="col-xs-3 control-label">账号登录超时（分钟）</label>

                <div class="col-xs-6">
                    <input class="form-control digits" type="text" name="loginTimeout"
                           value="${sysConfig.loginTimeout}">
                    <span class="help-block"><span class="star">*</span> 留空则使用系统默认的时间（${cm:stripTrailingZeros(_global_session_timeout/(60*1000))}分钟）</span>
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
<script>
    $.fileInput($("#_favicon"), {
        style: 'well',
        btn_choose: '网站标志, ICO格式',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 48,
        previewHeight: 48,
        allowExt: ['ico'],
        allowMime: ['image/x-icon'],
        value: '${ctx}/favicon.ico?_=<%=new Date().getTime()%>'
    });

    $.fileInput($("#_logo"), {
        style: 'well',
        btn_choose: '更换登录页LOGO',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        //previewWidth: 269,
        previewHeight: 58,
        allowExt: ['png'],
        allowMime: ['image/png'],
        value: '${ctx}/pic?path=${cm:encodeURI(sysConfig.logo)}&_=<%=new Date().getTime()%>'
    });

    $.fileInput($("#_logoWhite"), {
        style: 'well',
        btn_choose: '更换内页LOGO',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        //previewWidth: 269,
        previewHeight: 58,
        allowExt: ['png'],
        allowMime: ['image/png'],
        value: '${ctx}/pic?path=${cm:encodeURI(sysConfig.logoWhite)}&_=<%=new Date().getTime()%>'
    });

    $.fileInput($("#_loginTop"), {
        style: 'well',
        btn_choose: '更换登录页顶部背景',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 500,
        previewHeight: 50,
        allowExt: ['jpg'],
        allowMime: ['image/jpg', 'image/jpeg'],
        value: '${ctx}/pic?path=${cm:encodeURI(sysConfig.loginTop)}&_=<%=new Date().getTime()%>'
    });

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
        allowMime: ['image/jpg', 'image/jpeg'],
        value: '${ctx}/pic?path=${cm:encodeURI(cm:getShortPic(sysConfig.loginBg))}&_=<%=new Date().getTime()%>'
    });
    $.fileInput($("#_drLoginBg"), {
        style: 'well',
        btn_choose: '更换线上民主推荐登录页背景',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 400,
        previewHeight: 200,
        allowExt: ['png'],
        allowMime: ['image/png'],
        value: '${ctx}/pic?path=${cm:encodeURI(sysConfig.drLoginBg)}&_=<%=new Date().getTime()%>'
    });
    $.fileInput($("#_appleIcon"), {
        style: 'well',
        btn_choose: 'iphone桌面图标, ICO格式',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 64,
        previewHeight: 64,
        allowExt: ['ico'],
        allowMime: ['image/x-icon'],
        value: '${ctx}/img/favicon64.ico?_=<%=new Date().getTime()%>'
    });

    $.fileInput($("#_screenIcon"), {
        style: 'well',
        btn_choose: 'iphone桌面图标, PNG格式',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 64,
        previewHeight: 64,
        allowExt: ['png'],
        allowMime: ['image/png'],
        value: '${ctx}/pic?path=${cm:encodeURI(sysConfig.screenIcon)}&_=<%=new Date().getTime()%>'
    });
    $.fileInput($("#_qrLogo"), {
        style: 'well',
        btn_choose: '二维码LOGO, PNG格式',
        btn_change: null,
        no_icon: 'ace-icon fa fa-picture-o',
        thumbnail: 'large',
        droppable: true,
        previewWidth: 90,
        previewHeight: 90,
        allowExt: ['png'],
        allowMime: ['image/png'],
        value: '${ctx}/pic?path=${cm:encodeURI(sysConfig.qrLogo)}&_=<%=new Date().getTime()%>'
    });

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

    function _reload() {
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