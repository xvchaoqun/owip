<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="row" style="width: 1050px">
    <div class="alert alert-warning" style="font-size: 24px;padding: 30px;">
        ${cm:getHtmlFragment("hf_crs_collect").content}
    </div>
    <div class="modal-footer center">
        <input type="button" onclick="_start(1)" class="btn btn-success btn-lg" value="开始信息采集" style="margin-right: 25px;"/>
        <input type="button" onclick="_start(2)" class="btn btn-primary btn-lg" value="信息完整性校验"/>
    </div>

</div>
<script>
    function _start(to) {

        $.post("${cx}/user/crsPost_start", function (ret) {
            if (ret.success) {
                var hash;
                if(to==2){
                    hash = "#/user/cadre?cadreId={0}&to=cadreInfoCheck_table&type=1".format(ret.cadreId);
                }else{
                    if (ret.hasDirectModifyCadreAuth)
                        hash = "#/user/cadre?cadreId={0}&type=1".format(ret.cadreId);
                    else {
                        hash = "#/cadre_view?cadreId={0}&to={1}".format(ret.cadreId, $.trim(to));
                        <shiro:hasPermission name="userModifyCadre:menu">
                        hash = "#/modifyBaseApply?admin=0";
                        </shiro:hasPermission>
                    }
                }

                $("#sidebar").load("/menu?_=" + new Date().getTime(),function(){
                    location.hash = hash;
                });
            }
        })
    }
</script>