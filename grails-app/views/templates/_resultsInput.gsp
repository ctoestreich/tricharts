<h4>${segmentResult?.raceSegment?.segment}</h4>
<input type="hidden" name="${prefix}raceSegment.id" value="${segmentResult?.raceSegment?.id}" />
<div class="control-group ">
  <label class="control-label" for="${prefix}duration">${name} Duration</label>
  <div class="controls">
    <input type="hidden" name="${prefix}duration" value="struct" />
    <label for="${prefix}duration_hours"><input type="text" name="${prefix}duration_hours" id="duration_hours" value="0" size="1"/>&nbsp;hours </label>
    <label for="${prefix}duration_minutes"><input type="text" name="${prefix}duration_minutes" id="duration_minutes" value="0" size="1"/>&nbsp;minutes </label>
    <label for="${prefix}duration_seconds"><input type="text" name="${prefix}duration_seconds" id="duration_seconds" value="0" size="1"/>&nbsp;seconds </label>
  </div>
</div>
<div class="control-group">
  <label class="control-label" for="${prefix}placeAgeGroup">${name} Place Age Group</label>
  <div class="controls">
    <input type="number" name="${prefix}placeAgeGroup" value="" />
  </div>
</div>
<div class="control-group ">
  <label class="control-label" for="${prefix}placeOverall">${name} Place Overall</label>
  <div class="controls">
    <input type="number" name="${prefix}placeOverall" value="" id="placeOverall" />
  </div>
</div>