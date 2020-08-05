<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="space-4"></div>
<div class="row" style="width: 1450px">
<div style="margin: 0px; width: 500px;float: left">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="widget-title">
                会议材料清单
            </h4>
        </div>
        <div class="widget-body">
            <div class="widget-main">

                <table class="table table-bordered table-center table-unhover2">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th width="200">材料名称</th>
                        <th>原始模板</th>
                        <th>系统生成</th>
                        <th>正式使用</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>招聘会议程（组长）</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>招聘会议程（专家组成员）</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>应聘报名统计表</td>
                        <td colspan="3">
                            <a href="${ctx}/crsApplicant_data?cls=2&postId=${param.id}&export=1">
                                <i class="fa fa-file-excel-o"></i> 下载报名汇总表</a>
                        </td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>应聘报名表</td>
                        <td colspan="3">
                            <a href="${ctx}/crsApplicant_export?postId=${param.id}">
                                <i class="fa fa-file-word-o"></i> 下载应聘人报名表</a>
                        </td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>专家组推荐票</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>6</td>
                        <td>专家组推荐汇总表</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td>招聘会会标</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
<div style="margin: 0px 10px; width: 400px;float: left">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="widget-title">
                应聘人PPT
            </h4>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                上传通道：${crsPost.pptUploadClosed?"已关闭":"已开启"}&nbsp;
                <c:if test="${crsPost.pptUploadClosed}">
                <button class="confirm btn btn-xs btn-success"
                             data-msg="确定开启上传PPT？"
                             data-callback="_stepContentReload"
                             data-url="${ctx}/crsPost_pptUploadClosed?id=${crsPost.id}&closed=0"><i class="fa fa-check"></i> 开启</button>
                </c:if>
              <c:if test="${!crsPost.pptUploadClosed}">
                <button class="confirm btn btn-xs btn-danger"
                        data-msg="确定关闭上传PPT？"
                        data-callback="_stepContentReload"
                        data-url="${ctx}/crsPost_pptUploadClosed?id=${crsPost.id}&closed=1"><i class="fa fa-close"></i> 关闭</button>
              </c:if>
                <div class="space-4"></div>
                <table class="table table-bordered table-center table-unhover2">
                    <thead>
                    <tr>
                        <th width="50">序号</th>
                        <th width="100">应聘人姓名</th>
                        <th>答辩PPT</th>
                        <th width="80">短信催交记录</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${crsApplicants}" var="crsApplicant" varStatus="vs">
                    <tr>
                        <td>${vs.count}</td>
                        <td>${crsApplicant.user.realname}</td>
                        <td>
                            <c:if test="${not empty crsApplicant.ppt}">
                            <button class='downloadBtn btn btn-xs btn-success' style="float: left"
                                    data-url='${ctx}/user/crsPost_download_ppt?id=${crsApplicant.id}'>
                                <i class="fa fa-download"></i>
                                下载</button>
                            </c:if>

                            <form action="${ctx}/user/crsPost_apply_ppt"
                                  enctype="multipart/form-data" method="post"
                                  class="btn-upload-form">
                                <input type="hidden" name="userId" value="${crsApplicant.userId}">
                                <input type="hidden" name="postId" value="${crsApplicant.postId}">
                                <button type="button"
                                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                        class="hideView btn btn-xs btn-primary">
                                    <i class="ace-icon fa fa-upload"></i>
                                    上传
                                </button>
                                <input type="file" name="ppt" class="upload-file"/>
                            </form>
                        </td>
                        <td>
                            <button type="button"
                                    data-url="${ctx}/crsPost_detail/step3_shortMsg_list?postId=${crsApplicant.postId}&userId=${crsApplicant.userId}&tplKey=ct_crs_ppt_msg,ct_crs_ppt_urge_msg"
                                    class="popupBtn btn btn-xs btn-info">
                                <i class="ace-icon fa fa-history"></i>
                                查看
                            </button>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
    <div style="width: 500px;float: left">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="widget-title">
                    会务材料清单
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    ${cm:getHtmlFragment("hf_crs_material").content}
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(".upload-file").on('click', function() {
        $(this).val('');
    });
    $(".upload-file").change(function () {
        var $this = $(this);
        if ($this.val() != "") {
            var $form = $this.closest("form");
            var $btn = $("button", $form).button('loading');
            $form.ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        _stepContentReload()
                    }
                    $btn.button('reset');
                    $this.removeAttr("disabled");
                }
            });
            $this.attr("disabled", "disabled");
        }
    });
</script>