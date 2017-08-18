<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<div class="row" style="width: 1150px">
    <div style="width: 600px;float: left;margin-right: 25px">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
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
                                    批量上传图片
                                </button>
                                <input type="hidden" name="postId" value="${param.postId}">
                                <input type="hidden" name="type" value="${CRS_POST_FILE_TYPE_PIC}">
                                <input type="file" name="_files"  multiple="multiple" id="upload-image">
                            </form>
                        </div>
                        <div style="float: left">
                            <form action="${ctx}/crsPostFile_batchUpload"
                                  enctype="multipart/form-data" method="post"
                                  class="btn-upload-form">
                                <button type="button"
                                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                        class="btn btn-xs btn-success">
                                    <i class="ace-icon fa fa-file-audio-o"></i>
                                    批量上传音频
                                </button>
                                <input type="hidden" name="postId" value="${param.postId}">
                                <input type="hidden" name="type" value="${CRS_POST_FILE_TYPE_AUDIO}">
                                <input type="file" name="_files" multiple="multiple" id="upload-audio">
                            </form>
                        </div>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" style="height: 500px">
                    <div id="images">
                        <c:forEach items="${images}" var="image">
                            <div class="image">
                                <a class="various" rel="group" title="${cm:encodeURI(image.fileName)}"
                                   data-path="${cm:encodeURI(image.file)}"
                                   data-fancybox-type="image" href="${ctx}/pic?path=${cm:encodeURI(image.file)}">
                                    <img src="${ctx}/pic?path=${cm:encodeURI(image.file)}"></a>
                                <a class="confirm del"
                                   data-url="${ctx}/crsPostFile_del?id=${image.id}"
                                   data-msg="确定删除该图片"
                                   data-callback="_delFile"><i class="fa fa-times red fa-lg"></i></a>
                            </div>
                        </c:forEach>
                    </div>
                    <div style="clear: both"/>
                    <div id="audios" style="margin-top: 20px">
                        <c:forEach items="${audios}" var="audio" varStatus="vs">
                            <div class="audio">
                                <a href="${ctx}/attach/download?path=${cm:encodeURI(audio.file)}&filename=${cm:encodeURI(image.fileName)}">
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
    <div style="width: 523px; float:left; margin-bottom: 15px">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    专家组推荐结果
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
                <div class="widget-main" style="height: 500px" id="stat-content">
                <c:import url="/crsPost_detail/step3_stat?postId=${param.postId}&isUpdate=0"/>
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
        <a class="various" rel="group" title="{{=fileName}}" data-path="{{=file}}"
           data-fancybox-type="image" href="${ctx}/pic?path={{=file}}">
            <img src="${ctx}/pic?path={{=file}}"></a>
        <a class="confirm del"
           data-url="${ctx}/crsPostFile_del?id=${image.id}"
           data-msg="确定删除该图片"
           data-callback="_delFile"><i class="fa fa-times red fa-lg"></i></a>
    </div>
</script>
<script type="text/template" id="audio-tpl">
    <div class="audio">
    <a href="${ctx}/attach/download?path={{=file}}&filename={{=fileName}}">
       <i class="fa fa-download"></i> {{=name}}</a>
        <a class="confirm del"
           data-url="${ctx}/crsPostFile_del?id=${audio.id}"
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
        height: 100px;
        width: 100px
    }

    .image .various {
        height: 100px;
        width: 100px
    }

    .image img {
        width: 100%;
        height: 100%
    }

    .image .del {
        display: none;
        cursor: pointer;
        position: relative;
        top: -100px;
        right: -80px;
    }

    .audio {
        float: left;
        margin-right: 10px;
        border: dotted 1px;
        /*padding: 20px;*/
        height: 63px;
        width: 78px;
        margin-bottom: 10px
    }
    .audio a{
        height: 63px;
        line-height: 63px;
        text-align: center;
        width: 100%;
        display: table;
    }
    .audio .del {
        display: none;
        cursor: pointer;
        position: relative;
        top: -85px;
        right: -55px
    }
</style>
<script>
    function _finish(){
        $.hideView("${ctx}/crsPost?status=2");
    }
    var audioCount = ${fn:length(audios)};
    $(document).on("mouseover", ".image, .audio", function(){
        $(".del", this).show();
    })
    $(document).on("mouseout", ".image, .audio", function(){
        /*console.log($("#modal").is(":visible"))
        if($("#modal").is(":visible"))*/
            $(".del", this).hide();
    })
    $("#upload-image, #upload-audio").change(function () {
        //console.log($(this).val())
        if ($(this).val() != "") {
            var $this = $(this);
            var $form = $this.closest("form");
            var $btn = $("button", $form).button('loading');

            $form.ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $.each(ret.records, function(i, record){
                            if (record.type == '${CRS_POST_FILE_TYPE_PIC}') {
                                $("#images").append(_.template($("#image-tpl").html().NoMultiSpace())
                                ({file: record.file, fileName: record.fileName}));
                            } else if (record.type == '${CRS_POST_FILE_TYPE_AUDIO}') {
                                audioCount++;
                                $("#audios").append(_.template($("#audio-tpl").html().NoMultiSpace())
                                ({file: record.file, fileName: record.fileName, name: '音频' + audioCount}));
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

    function _delFile(btn) {
        $(btn).closest(".image, .audio").remove();
    }

    register_fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}&filename={1}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'), this.title);
    });

    register_datetime($('.datetime-picker'));

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