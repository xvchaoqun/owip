<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="space-4"></div>
<div class="row" style="width: 1490px">
    <div style="width: 480px;float: left;margin-right: 25px">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">
                    招聘会照片和录音
                    <div class="pull-right" style="margin-right: 10px">
                        <div style="float: left; margin-right: 10px">
                            <form action="${ctx}/crsPostFile_batchUpload"
                                  enctype="multipart/form-data" method="post"
                                  class="btn-upload-form">
                                <button type="button"
                                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                        class="btn btn-xs btn-warning">
                                    <i class="ace-icon fa fa-file-image-o"></i>
                                    上传图片
                                </button>
                                <input type="hidden" name="postId" value="${param.postId}">
                                <input type="hidden" name="type" value="${CRS_POST_FILE_TYPE_PIC}">
                                <input type="file" name="_files" data-allow-ext="jpg|jpeg|gif|bmp|png"  multiple="multiple" id="upload-image">
                            </form>
                        </div>
                        <div style="float: left; margin-right: 10px">
                            <form action="${ctx}/crsPostFile_batchUpload"
                                  enctype="multipart/form-data" method="post"
                                  class="btn-upload-form">
                                <button type="button"
                                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                        class="btn btn-xs btn-success">
                                    <i class="ace-icon fa fa-file-audio-o"></i>
                                    上传音频
                                </button>
                                <input type="hidden" name="postId" value="${param.postId}">
                                <input type="hidden" name="type" value="${CRS_POST_FILE_TYPE_AUDIO}">
                                <input type="file" name="_files" data-allow-ext="mp3|wma|wav" multiple="multiple" id="upload-audio">
                            </form>
                        </div>
                        <button type="button" onclick="_delAllFile(this)" class="btn btn-xs btn-danger">
                            <i class="ace-icon fa fa-trash-o"></i>
                            删除文件
                        </button>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" style="min-height: 500px;padding-right: 2px;">
                    <div id="images">
                        <c:forEach items="${images}" var="image">
                            <div class="image">
                                <input type="checkbox" value="${image.id}">
                                <a class="various" rel="group" title="${cm:encodeURI(image.fileName)}"
                                   data-path="${cm:sign(image.file)}"
                                   data-fancybox-type="image" href="${ctx}/pic?path=${cm:encodeURI(image.file)}">
                                    <img src="${ctx}/pic?path=${cm:encodeURI(cm:getShortPic(image.file))}"></a>
                                <a class="confirm del"
                                   data-url="${ctx}/crsPostFile_del?id=${image.id}"
                                   data-msg="确定删除该图片？"
                                   data-callback="_delFile"><i class="fa fa-times red fa-lg"></i></a>
                            </div>
                        </c:forEach>
                    </div>
                    <div style="clear: both"/>
                    <div id="audios" style="margin-top: 20px">
                        <c:forEach items="${audios}" var="audio" varStatus="vs">
                            <div class="audio">
                                <input type="checkbox" value="${audio.id}">
                                <a href="${ctx}/attach_download?path=${cm:sign(audio.file)}&filename=${cm:encodeURI(image.fileName)}">
                                    <i class="fa fa-download"></i> 音频${vs.count}</a>
                                <a class="confirm del"
                                   data-url="${ctx}/crsPostFile_del?id=${audio.id}"
                                   data-msg="确定删除该音频？"
                                   data-callback="_delFile"><i class="fa fa-times red fa-lg"></i></a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div style="width: 480px; float:left;;margin: 0 25px 15px 0">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">
                    专家组推荐情况
                    <div class="pull-right" style="margin-right: 10px">
                        <button type="button"
                                data-load-el="#stat-content"
                                data-url="${ctx}/crsPost_detail/step3_stat?postId=${param.postId}&isUpdate=1"
                                class="loadPage btn btn-xs btn-success">
                            <i class="ace-icon fa fa-edit"></i>
                            编辑
                        </button>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" style="min-height: 500px" id="stat-content">
                <c:import url="/crsPost_detail/step3_stat?postId=${param.postId}&isUpdate=0"/>
                </div>
            </div>
        </div>
    </div>
    <div style="width: 480px;float: left">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">
                    会议备忘
                    <c:if test="${not empty crsPost.meetingSummary}">
                        <div class="pull-right" style="margin-right: 10px">
                            <button type="button"
                                    data-load-el="#meetingSummary-content"
                                    data-url="${ctx}/crsPost_meetingSummary?id=${param.postId}"
                                    class="loadPage btn btn-xs btn-success">
                                <i class="ace-icon fa fa-edit"></i>
                                编辑
                            </button>
                        </div>
                    </c:if>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" id="meetingSummary-content" style="min-height: 500px">
                    ${cm:htmlUnescape(crsPost.meetingSummary)}
                    <c:if test="${empty crsPost.meetingSummary}">
                        <c:import url="${ctx}/crsPost_meetingSummary?id=${param.postId}"/>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <button ${crsPost.meetingStatus?'disabled':''} data-msg="确定此次招聘会完成？"
            data-callback="_finish"
            data-url="${ctx}/crsPost_detail/step3_finish?postId=${param.postId}"
            class="confirm btn btn-block btn-${crsPost.meetingStatus?'success':'primary'} btn-lg">${crsPost.meetingStatus?'招聘会已经完成':'招聘会完成'}</button>
</div>
<script type="text/template" id="image-tpl">
    <div class="image">
        <input type="checkbox" value="{{=record.id}}">
        <a class="various" rel="group" title="{{=record.fileName}}" data-path="{{=record.file}}"
           data-fancybox-type="image" href="${ctx}/pic?path={{=record.file}}">
            <img src="${ctx}/pic?path={{=record.file}}"></a>
        <a class="confirm del"
           data-url="${ctx}/crsPostFile_del?id={{=record.id}}"
           data-msg="确定删除该图片"
           data-callback="_delFile"><i class="fa fa-times red fa-lg"></i></a>
    </div>
</script>
<script type="text/template" id="audio-tpl">
    <div class="audio">
        <input type="checkbox" value="{{=record.id}}">
    <a href="${ctx}/attach_download?path={{=record.file}}&filename={{=record.fileName}}">
       <i class="fa fa-download"></i> {{=name}}</a>
        <a class="confirm del"
           data-url="${ctx}/crsPostFile_del?id={{=record.id}}"
           data-msg="确定删除该音频？"
           data-callback="_delFile"><i class="fa fa-times red fa-lg"></i></a>
    </div>
</script>
<style>
    .image {
        float: left;
        margin-right: 10px;
        border: dotted 1px;
        margin-bottom: 10px;
        height: 70px;
        width: 105px
    }

    .image .various {
        height: 100px;
        width: 100px
    }

    .image img {
        width: 100%;
        height: 100%;

    }

    .image input[type=checkbox] {
        display: none;
        cursor: pointer;
        position: absolute;
        margin: 10px 10px;
        width: 15px;
        height: 15px;
    }
    .image input[type=checkbox]:checked{
        display:block;
    }
    .image .del {
        display: none;
        cursor: pointer;
        position: relative;
        top: -65px;
        right: -80px;
    }

    .audio {
        float: left;
        margin-right: 10px;
        border: dotted 1px;
        /*padding: 20px;*/
        height: 100px;
        width: 150px;
        margin-bottom: 10px
    }
    .audio a{
        line-height: 100px;
        text-align: center;
        vertical-align: middle;
        width: 100%;
        display: table;
    }

    .audio input[type=checkbox] {
        display: none;
        cursor: pointer;
        position: absolute;
        margin: 10px 10px;
        width: 15px;
        height: 15px;
    }
    .audio input[type=checkbox]:checked{
        display:block;
    }
    .audio .del {
        display: none;
        cursor: pointer;
        position: relative;
        top: -130px;
        right: -120px
    }
</style>
<script>
    function _finish(){
        $.hideView("${ctx}/crsPost?status=2");
    }
    var audioCount = ${fn:length(audios)};
    $(document).on("mouseover", ".image, .audio", function(){
        $(".del", this).show();
        $("input[type=checkbox]", this).show();
    })

    $(document).on("mouseout", ".image, .audio", function(){
        $(".del", this).hide();
        $("input[type=checkbox]", this).not(":checked").hide();
    })

    $("#upload-image, #upload-audio").change(function () {
        //console.log($(this).val())
        var $this = $(this);
        if ($this.val() != "") {

            var $form = $this.closest("form");

            var allowExt = $this.data("allow-ext");
            var hasNotAllowExt = false;
            $.each(this.files, function (i, file) {
                //console.log(file)
                var re = new RegExp("\\.("+allowExt+")$", "i")
                if(!re.test(file.name)){
                    $.tip({msg:"请上传 "+allowExt+" 类型的文件", $target:$this, my:"bottom center", at:"top center"})
                    $this.val('');
                    hasNotAllowExt = true;
                    return false;
                }
            });
            if(hasNotAllowExt){
                return;
            }

            var $btn = $("button", $form).button('loading');
            $form.ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.each(ret.records, function(i, record){
                            if (record.type == '${CRS_POST_FILE_TYPE_PIC}') {
                                $("#images").append(_.template($("#image-tpl").html().NoMultiSpace())
                                ({record: record}));
                            } else if (record.type == '${CRS_POST_FILE_TYPE_AUDIO}') {
                                audioCount++;
                                $("#audios").append(_.template($("#audio-tpl").html().NoMultiSpace())
                                ({record: record, name: '音频' + audioCount}));
                            }
                        });
                    } else {

                    }
                    $btn.button('reset');
                    $this.attr("disabled", false);
                }
            });
            $this.attr("disabled", true);
        }
    });

    function _delAllFile(btn) {

        var ids = [];
        $("input[type=checkbox]:checked", ".image, .audio").each(function(){
            ids.push($(this).val())
        });
        if(ids.length==0){
            $.tip({msg:"请选择要删除的文件。", $target:$(btn), my:"bottom center", at:"top center"})
            return;
        }
        bootbox.confirm({
            className: "confirm-modal",
            buttons: {
                confirm: {
                    label: '<i class="fa fa-check"></i> 确认',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<i class="fa fa-reply"></i> 返回',
                    className: 'btn-default btn-show'
                }
            },
            message: "确定删除这{0}个文件？".format(ids.length),
            callback: function (result) {
                if (result) {

                    $.post("${ctx}/crsPostFile_batchDel", {ids:ids},function(ret){
                        if(ret.success){
                            $("input[type=checkbox]:checked").closest(".image, .audio").remove();
                        }
                    })
                }
            },
            title: '提交确认'
        });
    }

    function _delFile(btn) {
        $(btn).closest(".image, .audio").remove();
    }

    $.register.fancybox();

    $.register.datetime($('.datetime-picker'));

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        //$("#modal").modal('hide');
                        _stepContentReload()
                    }
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>