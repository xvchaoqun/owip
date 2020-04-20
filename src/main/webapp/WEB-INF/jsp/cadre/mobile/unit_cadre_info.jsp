<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="back-btn">
    <a href="javascript:;" class="hideView"><i class="fa fa-reply"></i> 返回</a>
</div>
<c:set var="backTo" value="${ctx}/m/cadre_search_byUnit?unitId=${param.unitId}"/>
<div class="profile-user-info profile-user-info-striped" style="border:0px; padding-bottom: 20px;">
    <div class="profile-info-row">
        <div class="profile-info-name"> 内 设 机 构</div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 内设机构名称</div>
        <div class="profile-info-value td">
            <span class="editable">${cjBean.unit.name}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 单位类型</div>
        <div class="profile-info-value td">
            <span class="editable">${cm:getMetaType(cjBean.unit.typeId).name}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name"> 正处级干部</div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 职数</div>
        <div class="profile-info-value td">
            <span class="editable">${cjBean.mainNum}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任数</div>
        <div class="profile-info-value td">
            <span class="editable">${cjBean.mainCount}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任干部</div>
        <div class="profile-info-value td">
            <span class="editable"><t:cpc_cadres_m cadrePosts="${cjBean.mains}" backTo="${cm:encodeURI(backTo)}"/></span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 空缺数</div>
        <div class="profile-info-value td">
            <span class="editable">
              <c:if test="${cjBean.mainLack==0}">0</c:if>
              <c:if test="${cjBean.mainLack!=0}">
                  <span style="line-height: 1"
                        class="badge ${cjBean.mainLack>0?'badge-success':'badge-danger'}">${cjBean.mainLack}</span>
              </c:if>
            </span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name"> 副处级干部</div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 职数</div>
        <div class="profile-info-value td">
            <span class="editable">${cjBean.viceNum}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任数</div>
        <div class="profile-info-value td">
            <span class="editable">${cjBean.viceCount}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任干部</div>
        <div class="profile-info-value td">
            <span class="editable"><t:cpc_cadres_m cadrePosts="${cjBean.vices}"  backTo="${cm:encodeURI(backTo)}"/></span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 空缺数</div>
        <div class="profile-info-value td">
            <span class="editable">
              <c:if test="${cjBean.viceLack==0}">0</c:if>
              <c:if test="${cjBean.viceLack!=0}">
                  <span style="line-height: 1"
                        class="badge ${cjBean.viceLack>0?'badge-success':'badge-danger'}">${cjBean.viceLack}</span>
              </c:if>
            </span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name"> ${_p_label_adminLevelNone}干部</div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 职数</div>
        <div class="profile-info-value td">
            <span class="editable">${cjBean.noneNum}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任数</div>
        <div class="profile-info-value td">
            <span class="editable">${cjBean.noneCount}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任干部</div>
        <div class="profile-info-value td">
            <span class="editable">
              <t:cpc_cadres_m cadrePosts="${cjBean.nones}" backTo="${cm:encodeURI(backTo)}"/>
            </span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 空缺数</div>
        <div class="profile-info-value td">
            <span class="editable">
                <c:if test="${cjBean.noneLack==0}">0</c:if>
                <c:if test="${cjBean.noneLack!=0}">
                    <span style="line-height: 1"
                          class="badge ${cjBean.noneLack>0?'badge-success':'badge-danger'}">${cjBean.noneLack}</span>
                </c:if>

            </span>
        </div>
    </div>
    <c:if test="${not empty kjBean}">
    <div class="profile-info-row">
        <div class="profile-info-name"> 正科级干部</div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 职数</div>
        <div class="profile-info-value td">
            <span class="editable">${kjBean.mainNum}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任数</div>
        <div class="profile-info-value td">
            <span class="editable">${kjBean.mainCount}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任干部</div>
        <div class="profile-info-value td">
            <span class="editable"><t:cpc_cadres_m cadrePosts="${kjBean.mains}" backTo="${cm:encodeURI(backTo)}"/></span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 空缺数</div>
        <div class="profile-info-value td">
            <span class="editable">
              <c:if test="${kjBean.mainLack==0}">0</c:if>
              <c:if test="${kjBean.mainLack!=0}">
                  <span style="line-height: 1"
                        class="badge ${kjBean.mainLack>0?'badge-success':'badge-danger'}">${kjBean.mainLack}</span>
              </c:if>
            </span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name"> 副科级干部</div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 职数</div>
        <div class="profile-info-value td">
            <span class="editable">${kjBean.viceNum}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任数</div>
        <div class="profile-info-value td">
            <span class="editable">${kjBean.viceCount}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任干部</div>
        <div class="profile-info-value td">
            <span class="editable"><t:cpc_cadres_m cadrePosts="${kjBean.vices}"  backTo="${cm:encodeURI(backTo)}"/></span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 空缺数</div>
        <div class="profile-info-value td">
            <span class="editable">
              <c:if test="${kjBean.viceLack==0}">0</c:if>
              <c:if test="${kjBean.viceLack!=0}">
                  <span style="line-height: 1"
                        class="badge ${kjBean.viceLack>0?'badge-success':'badge-danger'}">${kjBean.viceLack}</span>
              </c:if>
            </span>
        </div>
    </div>
    </c:if>
</div>
<script>$.adjustLeftFloatDivHeight($(".profile-info-name.td"))</script>
