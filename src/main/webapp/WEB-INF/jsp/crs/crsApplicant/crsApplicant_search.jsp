<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>查询账号报名情况</h3>
</div>
<div class="modal-body">
    <form>
        <div class="form-group">
            <label class="col-xs-3 control-label" style="text-align: right; line-height: 34px">教职工</label>

            <div class="col-xs-6">
                <select data-rel="select2-ajax"
                        data-ajax-url="${ctx}/sysUser_selects?type=${USER_TYPE_JZG}"
                        data-width="350"
                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option></option>
                </select>
            </div>
        </div>
    </form>
    <br/> <br/>

    <div class="row" id="result">

    </div>

</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
    <input type="button" id="search" class="btn btn-primary" value="查询"/>
</div>
<style>
    small {
        padding-top: 10px;
    }
</style>
<script type="text/template" id="result_tpl">
    <div class="col-xs-12">
        {{if(ret.hasApplyPosts.length==0){}}
        <blockquote>
            <small>
                没有报名任何岗位
            </small>
        </blockquote>
        {{}else{}}
        报名岗位：
        <blockquote>
            {{_.each(ret.hasApplyPosts, function(p, idx){ }}
            <small>
                <span>
                    {{=p.postName}}{{if(p.isQuit){}}（已退出报名）{{}}}&nbsp;&nbsp;
                    <a class="openView btn btn-success btn-xs"
                       href="javascript:;" data-url="${ctx}/crsPost_detail?id={{=p.postId}}">
                        <i class="fa fa-search"></i> 前往查看</a>
                </span>
            </small>
            {{});}}
        </blockquote>
        {{}}}
    </div>
</script>
<script>
    $.register.user_select($('#modal select[name=userId]'));
    $("#modal #search").click(function () {
        var userId = $("#modal select[name=userId]").val();
        if (userId == '') return;
        $.post("${ctx}/crsApplicant_search", {userId: userId}, function (ret) {
            if (ret.success) {
                $("#result").html(_.template($("#result_tpl").html())({ret: ret}));
            }
        });
    })
</script>