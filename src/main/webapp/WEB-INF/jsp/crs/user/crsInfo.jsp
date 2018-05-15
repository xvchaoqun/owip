<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="row" style="width: 1050px">
    <div class="alert alert-warning" style="font-size: 24px;padding: 30px;">
        <div style="padding-bottom: 15px;"><b>个人信息采集说明：</b></div>
        <div style="padding-left: 2em">
        1.在应聘报名之前，应完成个人信息采集工作，信息不完整不可报名。<br/>
        2.点击“开始信息采集”，进入“干部基本信息”页面。依次对“基本信息、学习经历、工作经历、岗位过程信息、社会或学术兼职、培训情况、教学经历、科研情况、其他奖励情况、家庭成员信息、企业社团兼职”等信息进行补充和修改。<br/>
        3.现任中层干部修改和完善信息需党委组织部审核，其他人员可直接修改和完善信息。<br/>
        4.信息采集完成后进行“干部信息完整性校验”，校验通过后进行报名应聘。
        </div>
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
                        <shiro:hasAnyRoles name="${ROLE_CADRE},${ROLE_CADREINSPECT},${ROLE_CADRERESERVE}">
                        hash = "#/modifyBaseApply?admin=0";
                        </shiro:hasAnyRoles>
                    }
                }

                $("#sidebar").load("/menu?_=" + new Date().getTime(),function(){
                    location.hash = hash;
                });
            }
        })
    }
</script>