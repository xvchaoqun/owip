<#if (cadreResearchDirects?size>0)>
<p>主持科研项目：</p>
</#if>
<#list cadreResearchDirects as cadreResearch>
<p style="text-indent: 2em">${cadreResearch_index+1}、${cadreResearch.name}</p>
</#list>
<#if (cadreResearchIns?size>0)>
<p>参与科研项目：</p>
</#if>
<#list cadreResearchIns as cadreResearch>
<p style="text-indent: 2em">${cadreResearch_index+1}、${cadreResearch.name}</p>
</#list>
<#if (cadreBooks?size>0)>
<p>出版著作：</p>
</#if>
<#list cadreBooks as cadreBook>
<p style="text-indent: 2em">${cadreBook_index+1}、${cadreBook.name}</p>
</#list>
<#if (cadrePapers?size>0)>
<p>发表论文：</p>
</#if>
<#list cadrePapers as cadrePaper>
<p style="text-indent: 2em">${cadrePaper_index+1}、${cadrePaper.name}</p>
</#list>
<#if (cadreRewards?size>0)><p>获奖情况：</p></#if>
<#list cadreRewards as cadreReward>
<p style="text-indent: 2em">${cadreReward.rewardTime?string("yyyy")}年&nbsp;${cadreReward.name}<#if cadreReward.rank gt 0>(排名第${cadreReward.rank})</#if>${((cadreReward.unit??) || (cadreReward.unit==''))?string('，', '')}${cadreReward.unit!}</p>
</#list>