<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="row" style="width: 1050px">
    <div class="widget-box transparent">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="hideView btn btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i> 返回</a>
            </h4>
            <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    ${cm:formatDate(crInfo.addDate, "yyyy.MM.dd")} 招聘信息
                </span>
        </div>

        <div class="tabbable">
            <div class="tab-content">
                <div class="tab-pane in active">
                    <c:set var="isPerfect" value="${crApplicant.hasSubmit || cm:perfectCadreInfo(_user.id)}"/>
                    <c:if test="${!isPerfect}">
                        <c:set value="${cm:getHtmlFragment('hf_crs_apply').content}" var="note"/>
                        <c:if test="${not empty note}">
                        <div class="alert alert-warning" style="font-size: 24px;">
                            ${note}
                        </div>
                        </c:if>
                        <div class="alert alert-danger" style="font-size: 24px;margin-bottom: 10px">
                            干部信息完整性校验结果：${isPerfect?"通过":"不通过"}
                        </div>
                        <div class="modal-footer center">
                            <button type="button" onclick="_start('cadreInfoCheck_table')"
                                    class="btn btn-primary btn-lg"><i class="fa fa-hand-o-right"></i> 完善信息
                            </button>
                        </div>
                    </c:if>
                    <c:if test="${isPerfect}">

                        <form class="form-horizontal" action="${ctx}/user/crInfo_apply"
                              data-tip-container="#page-content" autocomplete="off"
                              disableautocomplete id="modalForm" method="post">
                            <input type="hidden" name="id" value="${crApplicant.id}">
                            <input type="hidden" name="infoId" value="${param.infoId}">
                            <input type="hidden" name="hasSubmit">
                            <table class="table table-bordered table-unhover table-condensed">
                                <tr>
                                    <td width="100">姓 名</td>
                                    <td width="150">${bean.realname}</td>
                                    <td width="100">性 别</td>
                                    <td width="150">${GENDER_MAP.get(bean.gender)}</td>
                                    <td width="100">出生年月</td>
                                    <td width="">${cm:formatDate(bean.birth,'yyyy.MM.dd')}</td>
                                </tr>
                                <tr>
                                    <td>民族</td>
                                    <td>${bean.nation}</td>
                                    <td>入党时间</td>
                                    <td>
                                        <c:if test="${bean.dpTypeId>0}">
                                                ${cm:getMetaType(bean.dpTypeId).extraAttr}
                                                <c:if test="${not empty bean.owGrowTime}"><br/>
                                                    （${cm:formatDate(bean.owGrowTime, "yyyy.MM")}）
                                                </c:if>
                                            </c:if>
                                            <c:if test="${empty bean.dpTypeId}">
                                                <c:if test="${not empty bean.owGrowTime}">中共<br/>
                                                    ${cm:formatDate(bean.owGrowTime, "yyyy.MM")}
                                                </c:if>
                                            </c:if>
                                    </td>
                                    <td>参加工作时间</td>
                                    <td>${cm:formatDate(bean.workTime,'yyyy.MM')}</td>
                                </tr>
                                <tr>
                                    <td>专业技术职务及评定时间</td>
                                    <td colspan="2">
                                        ${bean.proPost}${(not empty bean.proPost && not empty bean.proPostTime)?'&nbsp;':''}
                                            ${empty bean.proPostTime?"--":cm:formatDate(bean.proPostTime, "yyyy.MM")}
                                    </td>
                                    <td>熟悉专业有何专长</td>
                                    <td colspan="2">${bean.specialty}</td>
                                </tr>
                                <tr>
                                    <td>全日制教育</td>
                                    <td>
                                        ${bean.edu}<br/>${bean.degree}
                                    </td>
                                    <td>毕业院校系及专业</td>
                                    <td colspan="3">${bean.schoolDepMajor1}${bean.sameSchool?'':'<br/>'}${bean.schoolDepMajor2}</td>
                                </tr>
                                <tr>
                                    <td>在职教育</td>
                                    <td>
                                        ${bean.inEdu}
        <br/>
        ${bean.inDegree}
                                    </td>
                                    <td>毕业院校系及专业</td>
                                    <td colspan="3">${bean.inSchoolDepMajor1}${bean.sameInSchool?'':'<br/>'}${bean.inSchoolDepMajor2}</td>
                                </tr>
                                <tr>
                                    <td>现任岗位或职务</td>
                                    <td colspan="5">${bean.post}
                                    </td>
                                </tr>
                                <tr>
                                    <td>办公电话</td>
                                    <td>${bean.phone}</td>
                                    <td>手 机</td>
                                    <td>${bean.mobile}</td>
                                    <td>电子邮箱</td>
                                    <td>${bean.email}</td>
                                </tr>
                                <tr>
                                    <td><span class="star">*</span>岗位志愿</td>
                                    <td colspan="5">
                                        <div class="input-group">
                                            第一志愿：
                                            <select required name="firstPostId" data-width="273" data-placeholder="请选择" data-rel="select2">
                                                <option></option>
                                                <c:forEach items="${crPosts}" var="post">
                                                    <option value="${post.id}">${post.name}</option>
                                                </c:forEach>
                                            </select>
                                            &nbsp;&nbsp;&nbsp;
                                            第二志愿：
                                            <select name="secondPostId" data-width="273" data-rel="select2" data-placeholder="请选择">
                                                <option></option>
                                                <c:forEach items="${crPosts}" var="post">
                                                    <option value="${post.id}">${post.name}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#modalForm select[name=firstPostId]").val('${crApplicant.firstPostId}');
                                                $("#modalForm select[name=secondPostId]").val('${crApplicant.secondPostId}');
                                            </script>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>${empty cadre?'<span class="star">*</span>':''}年度考核结果</td>
                                    <td colspan="5">
                                        <div class="input-group">
                                            <c:set var="idx" value="0"/>
                                        <c:forEach begin="${crInfo.year-3}" end="${crInfo.year-1}" var="y">
                                           ${y}年：<select ${empty cadre?"required":"disabled"} data-rel="select2" data-width="100"
                                                        name="eva" id="eva_${y}" data-placeholder="请选择">
                                                    <option></option>
                                                    <c:import url="/metaTypes?__code=mc_cadre_eva"/>
                                                </select>
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <script>
                                                $("#modalForm select[id=eva_${y}]").val('${evas.get(idx)}');
                                            </script>
                                            <c:set var="idx" value="${idx+1}"/>
                                        </c:forEach>
                                            </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td><span class="star">*</span>应聘理由（300字以内）</td>
                                    <td colspan="5">
                                        <textarea required name="reason" rows="7" maxlength="300"
                                                  style="width: 100%">${crApplicant.reason}</textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">
                                        <div class="modal-footer center">
                                            <c:if test="${!crApplicant.hasSubmit}">
                                                <div class="pull-left" style="position: absolute; font-size: 16pt">
            您已输入<span id="strCount" style="font-size: 20pt;font-weight: bolder">0</span>个字。</div>

                                                <a href="#" id="saveBtn" data-dismiss="modal" data-submit="0"
                                                   class="btn btn-primary btn-lg"><i
                                                        class="fa fa-save"></i> 暂存</a>
                                            <button type="button" id="submitBtn" data-submit="1" data-loading-text="提交中..."
                                                    class="btn btn-success btn-lg"><i class="fa fa-check"></i> 提交
                                            </button>
                                            </c:if>
                                            <c:if test="${crApplicant.hasSubmit}">
                                            <button type="button" disabled data-loading-text="提交中..."
                                                    class="btn btn-success btn-lg"><i class="fa fa-check"></i> 您已报名
                                            </button>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="footer-margin lower"/>
<style>
    .table.table-unhover > tbody > tr > td:nth-of-type(odd) {
        text-align: center;
        font-size: 18px;
        font-weight: bolder;
        vertical-align: middle;
    }

    textarea {
        text-indent: 32px;
        line-height: 25px;
        /*font-family: "Arial";*/
        font-size: 16px;
        padding: 2px;
        margin: 2px;
        border: none;
        background: #FFFFFF url(/${ctx}img/dot_25.gif);
        resize: none;
    }

</style>
<script>
    <c:if test="${crApplicant.hasSubmit}">
        $("select, input,textarea").prop("disabled",true)
    </c:if>
    <c:if test="${!isPerfect}">
    function _start(to) {
        $.post("${cx}/user/crInfo_start", function (ret) {
            if (ret.success) {
                var hash = "#/user/cadre?cadreId={0}&to=cadreInfoCheck_table&type=1".format(ret.cadreId);
                $("#sidebar").load("/menu?_=" + new Date().getTime(), function () {
                    location.hash = hash;
                });
            }
        })
    }
    </c:if>
    $('#modalForm [data-rel="select2"]').select2();
    $("#saveBtn, #submitBtn").click(function(){
        var hasSubmit = $(this).data("submit");
        $("#modalForm input[name=hasSubmit]").val(hasSubmit);

        if(hasSubmit==1){
            SysMsg.confirm("请再次确认信息无误后提交，提交后不可修改。", "操作确认", function () {
                $("#modalForm").submit();
            });
        }else{
            $("#modalForm").submit();
        }
        return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

            var firstPostId = $("#modalForm select[name=firstPostId]").val();
            var secondPostId = $("#modalForm select[name=secondPostId]").val();
            if(firstPostId==secondPostId){

                SysMsg.warning("第一志愿与第二志愿相同，请重新选择");
                return;
            }
            var $btn = $((hasSubmit==1)?"#submitBtn":"#saveBtn").button('loading');
            var hasSubmit = $("#modalForm input[name=hasSubmit]").val();
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        SysMsg.success((hasSubmit==1)?"提交成功":"保存成功。", function () {
                            if(hasSubmit==1) {
                                $("#jqGrid").trigger("reloadGrid");
                                $.hideView();
                            }
                        });
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('textarea').on('input propertychange', function() {
        var str = $(this).val().replace(/\s/g, "");
        $("#strCount").html(str.length)
      }).trigger("propertychange");
</script>