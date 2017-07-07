<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="trainEvaResult" value="${tempdata.trainEvaResultMap.get(norm.id)}"/>
<%--<div class="alert alert-block alert-success">
    第${step}/${maxStep}步：
    <c:if test="${step<=maxStep-1}">
    <c:if test="${not empty norm.topNorm}">${norm.topNorm.name} - </c:if>${norm.name}
    </c:if>
    <c:if test="${step==maxStep}">
    评估总分和意见建议
    </c:if>
</div>--%>
<c:if test="${step<=maxStep-1}">
    <c:if test="${not empty norm.topNorm}">
        <div class="alert alert-block alert-success" style="margin-bottom: 0px;font-size: 18px">
                ${cm:toHanStr(norm.topIndex+1)}、${norm.topNorm.name}
        </div>
                <span class="text-primary">
                       <h4>${norm.subIndex+1}. ${norm.name}</h4>
                </span>
    </c:if>
    <c:if test="${empty norm.topNorm}">
        <div class="alert alert-block alert-success">
                ${cm:toHanStr(norm.topIndex+1)}、${norm.name}
        </div>
    </c:if>
</c:if>
<c:if test="${step==maxStep}">
    <div class="alert alert-block alert-success" style="margin-bottom: 0px; font-size: 18px">
            ${cm:toHanStr(fn:length(topNorms)+1)}、意见建议
    </div>
</c:if>
<c:if test="${step<=maxStep-1}">
    <table id="rank-table" class="table table-striped table-bordered ">
        <tbody>
        <c:forEach items="${ranks}" var="rank" varStatus="vs">
            <tr>
                <c:if test="${vs.first}">
                    <td rowspan="${rankNum}" class="title-td">
                        评估等级
                    </td>
                </c:if>
                <td class="rank-name ${trainEvaResult.rankId==rank.id?'selected-td':''}">${rank.name}（${rank.scoreShow}）</td>
                <td style="border-left: none;width: 20px"
                    class="rank-check ${trainEvaResult.rankId==rank.id?'selected-td':''}">
                    <i class="fa fa-check fa-lg green"
                       style="${trainEvaResult.rankId==rank.id?'':'display: none'}"
                       data-rank-id="${rank.id}"></i>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${step==maxStep}">
    <table class="table">
            <%--<tr>
                <td align="right" style="border-top: none; vertical-align: middle;
            width: 80px;white-space: nowrap; padding-right: 0">评估总分：
                </td>
                <td style="border-top: none; vertical-align: middle">
                    <span id="score" class="ui-slider-red"></span>
                </td>
                <td style="border-top: none; vertical-align: middle;width: 20px;">
                    <span id="scoreShow">80</span>
                </td>
            </tr>--%>
        <tr>
            <td colspan="3" style="border-top: none">
                        <textarea id="feedback" placeholder="请在此输入意见或建议" class="form-control limited"
                                  rows="8">${tempdata.feedback}</textarea>
            </td>
        </tr>
    </table>
</c:if>
<div class="clearfix center nowrap">
    <button ${step==1?"disabled":""} class="first-step btn btn-info"
                                     style="padding-left:5px;padding-right: 5px;" type="button">
        <i class="fa fa-fast-backward"></i>
    </button>

    <button ${step==1?"disabled":""} class="last-step btn btn-info"
                                     data-step="${step-1}"
                                     data-last-step="${step}"
                                     type="button">
        上一步
    </button>
    <button style="${step==maxStep?"display:none":""}" class="next-step btn btn-info"
            data-step="${step+1}"
            data-last-step="${step}"
            type="button">
        下一步
    </button>
    <button style="${step==maxStep?"":"display:none"}" onclick="_submit(${tic.id})" class="btn btn-success" type="button">
        完<span style="visibility: hidden">一</span>成
    </button>
    <button ${step==maxStep?"disabled":""} class="max-step btn btn-info"
                                           data-max-step="${maxStep}"
                                           style="padding-left:5px;padding-right: 5px;" type="button">
        <i class="fa fa-fast-forward"></i>
    </button>

</div>
