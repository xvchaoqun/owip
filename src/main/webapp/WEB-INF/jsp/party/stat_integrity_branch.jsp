<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${integrityMap['branches']}" var="branches" />
<c:set value="${integrityMap['memberMap']}" var="memberMap"/>
<div class="row">
    <div class="col-xs-12">
        <div class="tabbable">
            <div class="tab-content" style="padding: 5px 4px 0px">
                <c:if test="${cls==2 && fn:length(partys)>1}">
                    <label class="selectFont">${_p_partyName}名称</label>
                    <select name="partyId" data-rel="select2" data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${partys}" var="party">
                            <option value="${party.id}">${party.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("select[name=partyId]").val('${partyId}');
                    </script>
                </c:if>
                <div id="statTable">
                <table border=0 cellpadding=0 cellspacing=0 style='border-collapse:collapse;table-layout:fixed;'>
                    <tr height=41 style='mso-height-source:userset;height:31.15pt'>
                        <td colspan=14 height=41 class=xl97>信息完整性汇总表</td>
                    </tr>
                    <tr class=xl66>
                        <td height=46 class=xl70 width=150 style='height:30pt;width:100pt'>
                            党支部名称
                        </td>
                        <td height=46 class=xl70 width=150 style='height:30pt;width:100pt'>
                            党支部
                        </td>
                        <td height=46 class=xl70 width=150 style='height:30pt;width:100pt'>
                            在职党员信息
                        </td>
                        <td height=46 class=xl70 width=150 style='height:30pt;width:100pt'>
                            学生党员信息
                        </td>
                        <td height=46 class=xl70 width=150 style='height:30pt;width:100pt'>
                            离退党员信息
                        </td>
                    </tr>

                    <c:forEach items="${branches}" var="branch">
                        <c:set value="${memberMap[branch.id]}" var="memberCount" />
                        <tr class=xl66>
                            <td height=46 class=xl70 width=150 style='height:30pt;width:210pt'>
                                ${empty branch.shortName?branch.name:branch.shortName}
                            </td>
                            <td height=46 class="xl70 ${branch.integrity == '1.00'?'':'notExist'}" width=150 style='height:30pt;width:100pt'>
                                ${branch.integrity == '1.00'?"0":"1"}
                            </td>
                            <td height=46 class="xl70 ${memberCount['teacher']>0?'notExist':''}" width=150 style='height:30pt;width:100pt'>
                                ${memberCount['teacher']}
                            </td>
                            <td height=46 class="xl70 ${memberCount['student']>0?'notExist':''}" width=150 style='height:30pt;width:100pt'>
                                ${memberCount['student']}
                            </td>
                            <td height=46 class="xl70 ${memberCount['retire']>0?'notExist':''}" width=150 style='height:30pt;width:100pt'>
                                ${memberCount['retire']}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                </div>
            </div>
        </div>
    </div>
</div>
<style>

    .selectFont{
        font-size: 16px;
        font-family: "Open Sans";
    }

    tr {
        mso-height-source: auto;
        mso-ruby-visibility: none;
    }

    col {
        mso-width-source: auto;
        mso-ruby-visibility: none;
    }

    br {
        mso-data-placement: same-cell;
    }

    td {
        mso-style-parent: style0;
        padding-top: 1px;
        padding-right: 1px;
        padding-left: 1px;
        mso-ignore: padding;
        color: black;
        font-size: 11.0pt;
        font-weight: 400;
        font-style: normal;
        text-decoration: none;
        font-family: 宋体;
        mso-generic-font-family: auto;
        mso-font-charset: 134;
        mso-number-format: General;
        text-align: center;
        vertical-align: bottom;
        border: none;
        mso-background-source: auto;
        mso-pattern: auto;
        mso-protection: locked visible;
        white-space: nowrap;
        mso-rotate: 0;
    }

    .xl66 {
        mso-style-parent: style0;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    .xl70 {
        mso-style-parent: style0;
        color: windowtext;
        font-weight: 700;
        text-align: center;
        vertical-align: middle;
        border: 1pt solid windowtext;
        white-space: normal;
    }

    .xl97 {
        mso-style-parent: style0;
        color: windowtext;
        font-size: 18.0pt;
        font-family: 华文中宋;
        mso-generic-font-family: auto;
        mso-font-charset: 134;
        text-align: center;
        vertical-align: middle;
        white-space: normal;
    }

    td.notExist{
        background-color: #f2dede!important;
        font-size: 18px;
    }
</style>
<script>

    $('[data-rel="select2"]').select2();

    $("select[name=partyId]").change(function () {

        var value = this.value;
        if (value=='') return;

        //page-content
        $.post("${ctx}/stat_integrity", {partyId: value,cls:${cls}}, function (ret) {

                $("#page-content").html(ret);
        });
    })
</script>
