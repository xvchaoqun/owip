<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/crsPost_detail/step3_stat" id="statForm" method="post">
        <div class="title">
            <div class="first">${_school}${crsPost.name}人选</div>
            <div class="sencond">专家组推荐意见汇总表</div>
        </div>
        <div style="margin-bottom: 10px;text-indent: 2em">
            招聘会专家组共<span class="num"><input type="text" disabled value="${expertCount}"></span>人，
            发出推荐票<span class="num"><input type="text" name="statGiveCount" class="num" value="${crsPost.statGiveCount}"
                                          maxlength="3"></span>张，
            收回<span class="num"><input type="text" name="statBackCount" class="num" value="${crsPost.statBackCount}"
                                       maxlength="3"></span>张，推荐结果如下：
        </div>
        <table class="table table-bordered table-unhover2 ">
            <thead class="multi">
            <tr>
                <th rowspan="2">招聘岗位</th>
                <th rowspan="2">应聘人员</th>
                <th colspan="2">推荐意见汇总</th>
            </tr>
            <tr>
                <th>1</th>
                <th>2</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${crsApplicants}" var="applicant" varStatus="vs">
                <tr class="applicant" data-applicant-id="${applicant.id}">
                    <c:if test="${vs.first}">
                        <td class="center" rowspan="${fn:length(crsApplicants)}">${crsPost.name}</td>
                    </c:if>
                    <td class="center">${applicant.user.realname}</td>
                    <td style="width: 40px">
                        <span class="num"><input type="text" class="first num" value="${applicant.recommendFirstCount}"
                                                 maxlength="3"></span>
                    </td>
                    <td style="width: 40px">
                        <span class="num"><input type="text" class="second num"
                                                 value="${applicant.recommendSecondCount}"
                                                 maxlength="3"></span>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td class="center">
                    排名第一应聘人
                </td>
                <td colspan="3">
                    <c:if test="${param.isUpdate!=1}">
                        <div style="width:50%;text-align: center" >
                        ${cm:getUserById(firstUserId).realname}
                        </div>
                    </c:if>
                    <c:if test="${param.isUpdate==1}">
                        <select data-rel="select2" name="firstUserId" data-placeholder="请选择">
                            <option></option>
                            <c:forEach items="${crsApplicants}" var="applicant" varStatus="vs">
                                <option value="${applicant.user.id}">${applicant.user.realname}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#statForm select[name=firstUserId]").val('${firstUserId}');
                        </script>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="center">
                    排名第二应聘人
                </td>
                <td colspan="3">
                    <c:if test="${param.isUpdate!=1}">
                        <div style="width:50%;text-align: center" >
                        ${cm:getUserById(secondUserId).realname}
                        </div>
                    </c:if>
                    <c:if test="${param.isUpdate==1}">
                        <select data-rel="select2" name="secondUserId" data-placeholder="请选择">
                            <option></option>
                            <c:forEach items="${crsApplicants}" var="applicant" varStatus="vs">
                                <option value="${applicant.user.id}">${applicant.user.realname}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#statForm select[name=secondUserId]").val('${secondUserId}');
                        </script>
                    </c:if>
                </td>
            </tr>

            <tr>
                <td class="center">
                    专家组推荐意见汇总表
                </td>
                <td colspan="3" ${param.isUpdate!=1?'align="center"':''}>
                    <c:if test="${param.isUpdate!=1 && not empty crsPost.statFile}">
                        <t:preview filePath="${crsPost.statFile}" fileName="${crsPost.statFileName}" label="预览"/>
                    </c:if>
                    <c:if test="${param.isUpdate==1}">
                        <input class="form-control" type="file" name="statFile"/>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    记录日期：
                <span class="num date">
                <input name="statDate" class="date-picker" type="text" name="endTime"
                       data-date-format="yyyy-mm-dd"
                       value="${crsPost.statDate==null?_today:cm:formatDate(crsPost.statDate, "yyyy-MM-dd")}">
               </span>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<c:if test="${param.isUpdate==1}">
    <div class="modal-footer center">
        <a href="javascript:;" onclick="_stepContentReload()" class="btn btn-default">取消</a>
        <input type="button" id="statFormSubmitBtn" class="btn btn-primary" value="确定"/>
    </div>
</c:if>
<script>
    <c:if test="${param.isUpdate!=1}">
    $("#statForm input").prop("disabled", true);
    </c:if>
    $('#statForm [data-rel="select2"]').select2();
    $.register.date($('.date-picker'));
    $.fileInput($('#statForm input[type=file]'), {
        no_file: '请上传pdf文件',
        allowExt: ['pdf']
    })
    $("#statFormSubmitBtn").click(function () {
        $("#statForm").submit();
        return false;
    })
    $("#statForm").validate({
        submitHandler: function (form) {

            var jsonResult = {};
            jsonResult.postId =${param.postId};
            jsonResult.statExpertCount = $("input[name=statExpertCount]", form).val();
            jsonResult.statGiveCount = $("input[name=statGiveCount]", form).val();
            jsonResult.statBackCount = $("input[name=statBackCount]", form).val();
            jsonResult.firstUserId = $("select[name=firstUserId]", form).val();
            jsonResult.secondUserId = $("select[name=secondUserId]", form).val();
            jsonResult.statDate = $("input[name=statDate]", form).val();
            jsonResult.applicatStatBeans = [];
            $("tr.applicant", form).each(function () {
                var applicant = {};
                applicant.applicantId = $(this).data("applicant-id")
                applicant.firstCount = $(".first", this).val();
                applicant.secondCount = $(".second", this).val();
                jsonResult.applicatStatBeans.push(applicant);
            })
            console.log(JSON.stringify(jsonResult))
            // return;
            $(form).ajaxSubmit({
                data: {jsonResult: $.base64.encode(JSON.stringify(jsonResult))},
                success: function (ret) {
                    if (ret.success) {
                        //$("#modal").modal('hide');
                        _stepContentReload()
                    }
                }
            });
        }
    });
</script>
<style>
    .table th, td.center {
        text-align: center;
        vertical-align: middle !important;
    }

    span.num {
        display: inline;
        width: 40px;
        text-align: center
    }

    span.num input[type=text] {
        width: 35px;
        height: 20px;
        border: none;
        border-bottom: 1px solid #000;
        text-align: center;
    }

    span.num.date {
        width: 130px;
    }

    span.num.date input[type=text] {
        width: 130px;
    }

    .title {
        margin-top: 10px;
        margin-bottom: 20px;
    }

    .title .first {
        font-size: 18pt;
        font-weight: bolder;
        text-align: center
    }

    .title .sencond {
        font-size: 16pt;
        font-weight: bolder;
        text-align: center
    }

    input[disabled] {
        background-color: transparent !important;
    }
</style>