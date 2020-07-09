<#if title??>
    <w:p w14:paraId="372C729E" w14:textId="77777777" w:rsidR="00E759EF" w:rsidRPr="00B62C14" w:rsidRDefault="0087435A" w:rsidP="00B943F3">
        <w:pPr>
            <w:spacing w:line="22pt" w:lineRule="exact"/>
            <w:ind w:start="102.05pt" w:hanging="102.05pt"/>
            <w:rPr>
                <w:b/>
            </w:rPr>
        </w:pPr>
        <w:r w:rsidRPr="00B62C14">
            <w:rPr>
                <w:rFonts w:hint="eastAsia"/>
                <w:b/>
            </w:rPr>
            <w:t>${title}：</w:t>
        </w:r>
    </w:p>
</#if>
<#list dataList as row>
<w:p w14:paraId="3F35F6EF" w14:textId="77777777" w:rsidR="00E759EF" w:rsidRPr="00B62C14" w:rsidRDefault="0087435A" w:rsidP="00B943F3">
    <#list row as col>
        <#if col_index==0>
            <#switch col>
                <#case 0>
                    <w:pPr>
                        <w:spacing w:line="22pt" w:lineRule="exact"/>
                        <w:ind w:start="102.05pt" w:hanging="102.05pt"/>
                    </w:pPr>
                    <#break>
                <#case 1>
                    <w:pPr>
                        <#if row_index==0>
                            <w:spacing w:line="440" w:line-rule="exact"/>
                        </#if>
                        <w:ind w:left-chars="350" w:left="105"/>
                        <w:rPr>
                            <w:sz w:val="24"/>
                        </w:rPr>
                    </w:pPr>
                    <#break>
                <#case 2>
                <w:pPr>
                    <w:spacing w:line="22pt" w:lineRule="exact"/>
                    <w:ind w:startChars="850" w:start="204.05pt" w:hanging="102.05pt"/>
                </w:pPr>
                    <#break>
            </#switch>
        </#if>
        <#if col_index!=0>
            <w:pPr>
                <w:spacing w:line="22pt" w:lineRule="exact"/>
                <w:ind w:start="102.05pt" w:hanging="102.05pt"/>
            </w:pPr>
            <w:r>
                <w:t xml:space="preserve">${col}</w:t>
            </w:r>
            <#if col_has_next>
                <w:r w:rsidRPr="00B62C14">
                    <w:rPr>
                        <w:rFonts w:hint="eastAsia"/>
                    </w:rPr>
                    <w:t xml:space="preserve">  </w:t>
                </w:r>
            </#if>
        </#if>
    </#list>
</w:p>
</#list>