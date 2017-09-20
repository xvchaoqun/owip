<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="tabbable">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
        <div style="margin-bottom: 8px">

            <div class="buttons">
                <a href="javascript:;" class="hideView btn btn-sm btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回
                </a>
                <button id="print_proof" class="btn btn-info btn-sm" style="margin-left: 50px"><i class="fa fa-print"></i>  打印证明</button>
                <a href="${ctx}/${param.type=='user'?'user/':''}abroad/passport_lostProof_download?id=${passport.id}" target="_blank"
                   class="btn btn-primary btn-sm"><i class="fa fa-download"></i> 下载</a>
            </div>
        </div>
    </ul>

    <div class="tab-content">
        <div id="home4" class="tab-pane in active">
            丢失日期：${cm:formatDate(passport.lostTime, "yyyy-MM-dd")}
            <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px;width: 595px">
                <img src="${ctx}/pic?path=${cm:encodeURI(passport.lostProof)}" style="max-width: 595px"/>
                </div>
            </div>
        </div>
    </div>

<script>
    $("#print_proof").click(function () {
        $.print('${ctx}/pic?path=${cm:encodeURI(passport.lostProof)}');
    });
    $("#updateLostProof").click(function(){
        $.loadModal("${ctx}/abroad/updateLostProof?id=${passport.id}")
    });
</script>