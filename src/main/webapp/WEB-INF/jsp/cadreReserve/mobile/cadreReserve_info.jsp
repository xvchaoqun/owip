<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="back-btn">
  <c:if test="${empty param.backTo}">
    <a href="javascript:;" class="hideView"><i class="fa fa-reply"></i> 返回</a>
  </c:if>
  <c:if test="${not empty param.backTo}">
    <a href="javascript:;" class="openView" data-open-by="page"
       data-url="${param.backTo}"><i class="fa fa-reply"></i> 返回</a>
  </c:if>
</div>
<div class="tabbable" style="margin-bottom: 10px;">
  <ul class="nav nav-tabs">
    <li class="active">
      <a data-toggle="tab" href="#base"><%--<i class="blue ace-icon fa fa-user"></i> --%>基本信息</a>
    </li>
    <li><a data-toggle="tab" href="#pi">人事信息</a></li>
    <li><a data-toggle="tab" href="#title">职称信息</a></li>
    <li><a data-toggle="tab" href="#post">任职信息</a></li>
  </ul>
  <div class="tab-content" style="padding:16px 0px 10px">
    <div id="base" class="tab-pane in active">
      <div class="profile-user-info profile-user-info-striped" style="border:0px;">
        <div class="profile-info-row">
          <table class="table table-center avatar" style="margin-bottom: 0px;">
            <tr>
              <td rowspan="6" class="avatar">
                <img src="${ctx}/m/avatar?path=${cm:encodeURI(uv.avatar)}&_t=<%=new Date().getTime()%>" class="avatar">
              </td>
            </tr>
            <tr>
              <td class="photo-row-td">
                姓名
              </td>
              <td >
                  ${uv.realname}
              </td>
            </tr>
            <tr>
              <td class="photo-row-td">
                性别
              </td>
              <td>
                ${GENDER_MAP.get(uv.gender)}
              </td>
            </tr>
            <tr>
              <td class="photo-row-td">
                民族
              </td>
              <td>
                ${uv.nation}
              </td>
            </tr>
            <tr>
              <td class="photo-row-td">
                出生日期
              </td>
              <td>
                ${cm:formatDate(uv.birth,'yyyy-MM-dd')}
              </td>
            </tr>
            <tr>
              <td class="photo-row-td">
                年龄
              </td>
              <td style="border-bottom: none">
                <c:if test="${not empty uv.birth}">
                  ${cm:intervalYearsUntilNow (uv.birth)}岁
                </c:if>
              </td>
            </tr>
          </table>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td" id="post1"> 现任职务</div>
          <div class="profile-info-value td">
            <span class="editable">${mainCadrePost.post}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 行政级别</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:getMetaType(cadreAdminLevel.adminLevel).name}</span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 政治面貌 </div>

          <div class="profile-info-value td">
            <span class="editable">
              ${cm:cadreParty(cadre.isOw, cadre.owGrowTime, cadre.owPositiveTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('partyName')}
            </span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 党派加入时间</div>

          <div class="profile-info-value td">
            <span class="editable">
              ${cm:cadreParty(cadre.isOw, cadre.owGrowTime, cadre.owPositiveTime, '中共党员', cadre.dpTypeId, cadre.dpGrowTime, false).get('growTime')}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 身份证号码</div>

          <div class="profile-info-value td">
            <span class="editable">
              ${uv.idcard}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 籍贯</div>

          <div class="profile-info-value td">
            <span class="editable">
              ${uv.nativePlace}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 出生地</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${uv.homeplace}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 学 历 信 息</div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 最高学历</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${cm:getMetaType(highEdu.eduId).name}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 学习方式</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${cm:getMetaType(highEdu.learnStyle).name}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 毕业学校</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${highEdu.school}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 所学专业</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${highEdu.major}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 联 系 方 式</div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 手机号</div>
          <div class="profile-info-value td">
            <span class="editable">
              <a href='tel:${uv.mobile}'>${uv.mobile}</a>
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 邮箱</div>
          <div class="profile-info-value td">
            <span class="editable">
               <a href='mailto:${uv.email}'>${uv.email}</a>
            </span>
          </div>
        </div>
        <c:if test="${not empty cadre}">
          <div class="profile-info-row">
            <div class="profile-info-name">
              <button class="popupBtn btn btn-success btn-block btn-sm" data-direction="bottom"
                      data-url="${ctx}/m/cadreAdform?mobile=1&cadreId=${cadre.id}"><i class="fa fa-search"></i> 干部任免审批表</button>
            </div>
          </div>
        </c:if>

      </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/ext/cadre_info_table.jsp"/>
    <div id="post" class="tab-pane">
      <div class="profile-user-info profile-user-info-striped">

        <div class="profile-info-row">
          <div class="profile-info-name"> 主 职 信 息</div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 任职单位</div>
          <div class="profile-info-value td">
            <span class="editable">${unitMap.get(mainCadrePost.unitId).name}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 单位属性</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:getMetaType(unitMap.get(mainCadrePost.unitId).typeId).name}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 是否双肩挑</div>
          <div class="profile-info-value td">
            <span class="editable">
              <c:if test="${not empty mainCadrePost}">
                ${mainCadrePost.isDouble?"是":"否"}
              </c:if>
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 现任职务</div>
          <div class="profile-info-value td">
            <span class="editable">${mainCadrePost.post}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 任现职时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(mainCadrePost.lpWorkTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 现职务始任时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(mainCadrePost.npWorkTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 行政级别</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:getMetaType(cadreAdminLevel.adminLevel).name}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 任现职级时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(cadreAdminLevel.startDispatch.workTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 任现职级年限</div>
          <div class="profile-info-value td">
            <span class="editable">
              <c:if test="${not empty cadreAdminLevel}">
                <c:set value="${cm:intervalYearsUntilNow(cadreAdminLevel.startDispatch.workTime)}" var="workYear"/>
                ${workYear==0?"未满一年":workYear}
              </c:if>
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 兼 职 信 息 1</div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 兼职单位1</div>
          <div class="profile-info-value td">
            <span class="editable">${unitMap.get(subCadrePost1.unitId).name}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 兼任职务1</div>
          <div class="profile-info-value td">
            <span class="editable">${subCadrePost1.post}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 任兼职时间1</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(subCadrePost1.lpWorkTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 兼 职 信 息 2</div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 兼职单位2</div>
          <div class="profile-info-value td">
            <span class="editable">${unitMap.get(subCadrePost2.unitId).name}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 兼任职务2</div>
          <div class="profile-info-value td">
            <span class="editable">${subCadrePost2.post}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 任兼职时间2</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(subCadrePost2.lpWorkTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script>$.adjustLeftFloatDivHeight($(".profile-info-name.td"));</script>
