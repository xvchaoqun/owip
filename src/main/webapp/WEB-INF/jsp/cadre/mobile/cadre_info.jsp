<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
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
                <img src="${ctx}/m/avatar?path=${uv.avatar}&_t=<%=new Date().getTime()%>" class="avatar">
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
          <div class="profile-info-name td"> 现任职务</div>
          <div class="profile-info-value td">
            <span class="editable">${mainCadrePost.post}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 行政级别</div>
          <div class="profile-info-value td">
            <span class="editable">${adminLevelMap.get(cadreAdminLevel.adminLevelId).name}</span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 政治面貌 </div>

          <div class="profile-info-value td">
            <span class="editable">
              ${cadre.cadreDpType>0?democraticPartyMap.get(cadre.dpTypeId).name:(cadre.cadreDpType==0)?'中共党员':''}
            </span>
          </div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 党派加入时间</div>

          <div class="profile-info-value td">
            <span class="editable">
              ${cm:formatDate(cadre.cadreGrowTime,'yyyy-MM-dd')}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 身份证号码</div>

          <div class="profile-info-value td">
            <span class="editable">
              ${extJzg.sfzh}
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
              ${eduTypeMap.get(highEdu.eduId).name}
            </span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 学习方式</div>
          <div class="profile-info-value td">
            <span class="editable">
              ${learnStyleMap.get(highEdu.learnStyle).name}
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
      </div>
    </div>
    <div id="pi" class="tab-pane">
      <div class="profile-user-info profile-user-info-striped">
        <div class="profile-info-row">
          <div class="profile-info-name td"> 所在单位</div>
          <div class="profile-info-value td">
            <span class="editable">${extJzg.dwmc}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 编制类别</div>
          <div class="profile-info-value td">
            <span class="editable">${extJzg.bzlx}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 人员分类</div>
          <div class="profile-info-value td">
            <span class="editable">${extJzg.rylx}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 人员状态</div>
          <div class="profile-info-value td">
            <span class="editable">${extJzg.ryzt}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 在岗情况</div>
          <div class="profile-info-value td">
            <span class="editable">${extJzg.sfzg}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 人事转否</div>
          <div class="profile-info-value td">
            <span class="editable">${extJzg.rszf}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 岗位类别</div>
          <div class="profile-info-value td">
            <span class="editable">${teacherInfo.postClass}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 岗位子类别</div>
          <div class="profile-info-value td">
            <span class="editable">${extJzg.gwzlbmc}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 主岗等级</div>
          <div class="profile-info-value td">
            <span class="editable">${extJzg.zgdjmmc}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 工龄起算日期</div>
          <div class="profile-info-value td">
            <span class="editable">${fn:substringBefore(extJzg.glqsrq, ' ')}</span>
          </div>
        </div>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 间断工龄</div>
          <div class="profile-info-value td">
            <span class="editable">${extJzg.jdgl}</span>
          </div>
        </div>--%>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 到校时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(teacherInfo.arriveTime, "yyyy-MM-dd")}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 参加工作时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(teacherInfo.workTime, "yyyy-MM-dd")}</span>
          </div>
        </div>
        <%--<div class="profile-info-row">
          <div class="profile-info-name td"> 转正定级时间</div>
          <div class="profile-info-value td">
            <span class="editable">${fn:substringBefore(extJzg.zzdjsj, ' ')}</span>
          </div>
        </div>--%>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 人才/荣誉称号 </div>
          <div class="profile-info-value td">
            <span class="editable">${teacherInfo.talentTitle}</span>
          </div>
        </div>

      </div>
    </div>
    <div id="title" class="tab-pane">
      <div class="profile-user-info profile-user-info-striped">

        <div class="profile-info-row">
          <div class="profile-info-name"> 专 技 岗 位</div>
        </div>

        <div class="profile-info-row">
          <div class="profile-info-name td"> 专业技术职务</div>
          <div class="profile-info-value td">
            <span class="editable">${teacherInfo.proPost}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 专技职务评定时间</div>
          <div class="profile-info-value td">
            <span class="editable">${fn:substringBefore(extJzg.zyjszwpdsj, ' ')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 专技职务等级</div>
          <div class="profile-info-value td">
            <span class="editable">${teacherInfo.proPostLevel}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 专技职务分级时间</div>
          <div class="profile-info-value td">
            <span class="editable">${fn:substringBefore(extJzg.zjgwfjsj, ' ')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 管 理 岗 位</div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 管理岗位等级</div>
          <div class="profile-info-value td">
            <span class="editable">${teacherInfo.manageLevel}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 管理岗位分级时间</div>
          <div class="profile-info-value td">
            <span class="editable">${fn:substringBefore(extJzg.glgwfjsj, ' ')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name"> 工 勤 岗 位</div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 工勤岗位等级</div>
          <div class="profile-info-value td">
            <span class="editable">${extJzg.gqgwdjmc}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 工勤岗位分级时间</div>
          <div class="profile-info-value td">
            <span class="editable">${fn:substringBefore(extJzg.gqgwfjsj, ' ')}</span>
          </div>
        </div>
        </div>
    </div>
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
            <span class="editable">${unitTypeMap.get(unitMap.get(mainCadrePost.unitId).typeId).name}</span>
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
            <span class="editable">${cm:formatDate(mainCadrePost.dispatchCadreRelateBean.last.workTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 现职务始任时间</div>
          <div class="profile-info-value td">
            <span class="editable">${cm:formatDate(mainCadrePost.dispatchCadreRelateBean.first.workTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
        <div class="profile-info-row">
          <div class="profile-info-name td"> 行政级别</div>
          <div class="profile-info-value td">
            <span class="editable">${adminLevelMap.get(cadreAdminLevel.adminLevelId).name}</span>
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
            <span class="editable">${cm:formatDate(subCadrePost1.dispatchCadreRelateBean.last.workTime,'yyyy-MM-dd')}</span>
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
            <span class="editable">${cm:formatDate(subCadrePost2.dispatchCadreRelateBean.last.workTime,'yyyy-MM-dd')}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
