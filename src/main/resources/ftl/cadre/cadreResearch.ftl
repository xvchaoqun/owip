<#if (cadreResearchDirects?size>0)>
<p>主持科研项目：</p>
</#if>
<#list cadreResearchDirects as cadreResearch>
<p style="text-indent: 2em"><#if (cadreResearchDirects?size>1)>${cadreResearch_index+1}、</#if>《${cadreResearch.name!}》，${cadreResearch.type!}，${cadreResearch.unit!}<#if cadreResearch_has_next>；</#if><#if !cadreResearch_has_next>。</#if></p>
</#list>
<#if (cadreResearchIns?size>0)>
<p>参与科研项目：</p>
</#if>
<#list cadreResearchIns as cadreResearch>
<p style="text-indent: 2em"><#if (cadreResearchIns?size>1)>${cadreResearch_index+1}、</#if>《${cadreResearch.name!}》，${cadreResearch.type!}，${cadreResearch.unit!}<#if cadreResearch_has_next>；</#if><#if !cadreResearch_has_next>。</#if></p>
</#list>
<#if (cadreBooks?size>0)>
<p>出版著作：</p>
</#if>
<#list cadreBooks as cadreBook>
<p style="text-indent: 2em"><#if (cadreBooks?size>1)>${cadreBook_index+1}、</#if>《${cadreBook.name!}》，${cadreBook.publisher!}，${cadreBook.typeStr!}。</p>
</#list>
<#if (cadrePapers?size>0)>
<p>发表论文：</p>
</#if>
<#list cadrePapers as cadrePaper>
<p style="text-indent: 2em"><#if (cadrePapers?size>1)>${cadrePaper_index+1}、</#if>《${cadrePaper.name!}》，${cadrePaper.press!}<#if cadrePaper_has_next>；</#if><#if !cadrePaper_has_next>。</#if></p>
</#list>
<#if (cadreRewards?size>0)><p>获奖情况：</p></#if>
<#list cadreRewards as cadreReward>
<p style="text-indent: 2em">${rewardOnlyYear?string(cadreReward.rewardTime?string("yyyy年"),cadreReward.rewardTime?string("yyyy年MM月"))}，荣获“${cadreReward.name}”<#if cadreReward.rank?? && cadreReward.rank gt 0>(排名第${cadreReward.rank})</#if>${((cadreReward.unit??) || (cadreReward.unit!=''))?string('，', '')}${cadreReward.unit!}<#if cadreReward_has_next>；</#if><#if !cadreReward_has_next>。</#if></p>
</#list>