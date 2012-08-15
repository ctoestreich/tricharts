<td class="cls10pct clsCentered clsResultBL">
  <div class="clsBorderBot">${result?.placeAgeGroup ?: '?'}</div>
  <div class="clsBorderBot">${result?.participantsAgeGroup ?: '?'}</div>
  <div class="clsBorderBot"><tri:placement place="${result?.placeAgeGroup}" overall="${result?.participantsAgeGroup}" percentOnly="true" /></div>
</td>
<td class="cls10pct clsCentered clsResultBL">
  <div class="clsBorderBot">${result?.placeGender ?: '?'}</div>
  <div class="clsBorderBot">${result?.participantsGender ?: '?'}</div>
  <div class="clsBorderBot"><tri:placement place="${result?.placeGender}" overall="${result?.participantsGender}" percentOnly="true" /></div>
</td>
<td class="cls10pct clsCentered clsResultBL">
  <div class="clsBorderBot">${result?.placeOverall ?:'?'}</div>
  <div class="clsBorderBot">${result?.participantsOverall ?: '?'}</div>
  <div class="clsBorderBot"><tri:placement place="${result?.placeOverall}" overall="${result?.participantsOverall}" percentOnly="true" /></div>
</td>