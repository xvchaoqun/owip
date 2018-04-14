<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<div class="profile-user-info profile-user-info-striped" style="border:0px; padding-top:15px; padding-bottom: 20px;">

    <div class="profile-info-row">
        <div class="profile-info-name"> 内 设 机 构</div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 内设机构名称</div>
        <div class="profile-info-value td">
            <span class="editable">${bean.unit.name}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 单位类型</div>
        <div class="profile-info-value td">
            <span class="editable">${cm:getMetaType(bean.unit.typeId).name}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name"> 正处级干部</div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 职数</div>
        <div class="profile-info-value td">
            <span class="editable">${bean.mainNum}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任数</div>
        <div class="profile-info-value td">
            <span class="editable">${bean.mainCount}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任干部</div>
        <div class="profile-info-value td">
            <span class="editable"><t:cpc_cadres_m cadrePosts="${bean.mains}"/></span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 空缺数</div>
        <div class="profile-info-value td">
            <span class="editable">
              <c:if test="${bean.mainLack==0}">0</c:if>
              <c:if test="${bean.mainLack!=0}">
                  <span style="line-height: 1"
                        class="badge ${bean.mainLack>0?'badge-success':'badge-danger'}">${bean.mainLack}</span>
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
            <span class="editable">${bean.viceNum}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任数</div>
        <div class="profile-info-value td">
            <span class="editable">${bean.viceCount}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任干部</div>
        <div class="profile-info-value td">
            <span class="editable"><t:cpc_cadres_m cadrePosts="${bean.vices}"/></span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 空缺数</div>
        <div class="profile-info-value td">
            <span class="editable">
              <c:if test="${bean.viceLack==0}">0</c:if>
              <c:if test="${bean.viceLack!=0}">
                  <span style="line-height: 1"
                        class="badge ${bean.viceLack>0?'badge-success':'badge-danger'}">${bean.viceLack}</span>
              </c:if>
            </span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name"> 无行政级别干部</div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 职数</div>
        <div class="profile-info-value td">
            <span class="editable">${bean.noneNum}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任数</div>
        <div class="profile-info-value td">
            <span class="editable">${bean.noneCount}</span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 现任干部</div>
        <div class="profile-info-value td">
            <span class="editable">
              <t:cpc_cadres_m cadrePosts="${bean.nones}"/>
            </span>
        </div>
    </div>
    <div class="profile-info-row">
        <div class="profile-info-name td"> 空缺数</div>
        <div class="profile-info-value td">
            <span class="editable">
                <c:if test="${bean.noneLack==0}">0</c:if>
                <c:if test="${bean.noneLack!=0}">
                    <span style="line-height: 1"
                          class="badge ${bean.noneLack>0?'badge-success':'badge-danger'}">${bean.noneLack}</span>
                </c:if>

            </span>
        </div>
    </div>
</div>
<script>
    _adjustHeight();
</script>
