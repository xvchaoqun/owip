<#if (bksCadreCourses?size>0)>
<p>本科生课程：<#list bksCadreCourses as course>${course.name}<#if course_has_next>、</#if></#list></p>
</#if>
<#if (ssCadreCourses?size>0)>
<p>硕士生课程：<#list ssCadreCourses as course>${course.name}<#if course_has_next>、</#if></#list></p>
</#if>
<#if (bsCadreCourses?size>0)>
<p>博士生课程：<#list bsCadreCourses as course>${course.name}<#if course_has_next>、</#if></#list></p>
</#if>
<#if (cadreRewards?size>0)><p>获奖情况：</p></#if>
<#list cadreRewards as cadreReward>
<p style="text-indent: 2em">${cadreReward.rewardTime?string("yyyy")}年，${cadreReward.name}<#if cadreReward.rank gt 0>(排名第${cadreReward.rank})</#if>${((cadreReward.unit??) || (cadreReward.unit==''))?string('，', '')}${cadreReward.unit!}<#if cadreReward_has_next>；</#if><#if !cadreReward_has_next>。</#if></p>
</#list>