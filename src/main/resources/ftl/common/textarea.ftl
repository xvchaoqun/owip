<#list dataList as row>
<w:p wsp:rsidR="00220FDA" wsp:rsidRDefault="001554C7" wsp:rsidP="00220FDA">
    <w:pPr>
        <w:spacing w:line="440" w:line-rule="exact"/>
        <w:ind w:first-line-chars="200" w:first-line="480"/>
        <w:outlineLvl w:val="0"/>
        <w:rPr>
            <w:rFonts w:ascii="宋体" w:h-ansi="宋体"/>
            <wx:font wx:val="宋体"/>
            <w:b-cs/>
        </w:rPr>
    </w:pPr>
    <w:r>
        <w:rPr>
            <w:rFonts w:ascii="宋体" w:h-ansi="宋体" w:hint="fareast"/>
            <wx:font wx:val="宋体"/>
            <w:b-cs/>
        </w:rPr>
        <w:t>${row}</w:t>
    </w:r>
</w:p>
</#list>