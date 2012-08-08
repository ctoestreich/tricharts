<h4>${segmentResult?.raceSegment?.segment}</h4>
<input type="hidden" name="${prefix}raceSegment.id" value="${segmentResult?.raceSegment?.id}" />
<div class="control-group ">
  <label class="control-label" for="${prefix}duration">${name} Duration</label>
  <div class="controls">
    <joda:periodPicker name="${prefix ?: ''}duration" value="${segmentResult?.duration}" fields="hours,minutes,seconds" />
  </div>
</div>
<div class="control-group">
  <label class="control-label" for="${prefix}placeAgeGroup">${name} Place Age Group</label>
  <div class="controls">
    <input type="number" name="${prefix}placeAgeGroup" value="${segmentResult?.placeAgeGroup}" />
  </div>
</div>
<div class="control-group ">
    <label class="control-label" for="${prefix}placeGender">${name} Place Gender</label>
    <div class="controls">
        <input type="number" name="${prefix}placeGender" value="${segmentResult?.placeGender}" id="${prefix}placeGender" />
    </div>
</div>
<div class="control-group ">
  <label class="control-label" for="${prefix}placeOverall">${name} Place Overall</label>
  <div class="controls">
    <input type="number" name="${prefix}placeOverall" value="${segmentResult?.placeOverall}" id="${prefix}placeOverall" />
  </div>
</div>
<g:if test="${isRaceResults}">
    <div class="control-group ">
        <label class="control-label" for="${prefix}participantsAgeGroup">${name} Participants Age Group</label>
        <div class="controls">
            <input type="number" name="${prefix}participantsAgeGroup" value="${segmentResult?.participantsAgeGroup}" id="${prefix}participantsAgeGroup" />
        </div>
    </div>
    <div class="control-group ">
        <label class="control-label" for="${prefix}participantsGender">${name} Participants Gender</label>
        <div class="controls">
            <input type="number" name="${prefix}participantsGender" value="${segmentResult?.participantsGender}" id="${prefix}participantsGender" />
        </div>
    </div>
    <div class="control-group ">
        <label class="control-label" for="${prefix}participantsOverall">${name} Participants Overall</label>
        <div class="controls">
            <input type="number" name="${prefix}participantsOverall" value="${segmentResult?.participantsOverall}" id="${prefix}participantsOverall" />
        </div>
    </div>
</g:if>