<%@ page import="com.tgid.tri.race.Segment; com.tgid.tri.auth.User" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>Add A Race</title>
  <r:require modules="dashboard,results,jquery-ui"/>
  <gvisualization:apiImport/>
  <style>
  .connectedSortable {
    list-style-type: none;
    margin: 0;
    padding: 0;
    float: left;
    margin-right: 10px;
    background: #eee;
    padding: 5px;
    width: 100%;
  }

  .connectedSortable li {
    margin: 0 5px 5px 5px;
    padding: 5px;
    font-size: 1.2em;
    width: 90%;
  }
  </style>
</head>

<body>

<div class="page-header">
  <h1>Add A Race&nbsp;<small>races are fun</small></h1>
</div>

<div class="row-fluid">
  <g:if test="${flash.message}">
    <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
  </g:if>

  <g:hasErrors bean="${raceInstance}">
    <bootstrap:alert class="alert-error">
      <ul>
        <g:eachError bean="${raceInstance}" var="error">
          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
      </ul>
    </bootstrap:alert>
  </g:hasErrors>
</div>

<div class="row-fluid">
  <h3>Add segments to ${raceInstance}</h3>
  <br />
  <g:form controller="dashboard" action="addSegments" name="addSegmentsForm" id="addSegmentsForm">
    <g:hiddenField name="segments" id="segments" value="" />
    <g:hiddenField name="raceId" value="${raceInstance.id}" />
  <fieldset>
      <div class="span4">
        <h5>Segments In Race (drag right to remove)</h5>
        <ul id="segmentOptIn" class="connectedSortable">

        </ul>
      </div>
      <div class="span3"><p align="center"><a href="#" id="saveSegments" class="btn">Save Segments</a></p></div>
      <div class="span4">
        <h5>Available Segments (drag left to add)</h5>
        <ul id="segmentOptOut" class="connectedSortable">
          <g:each in="${com.tgid.tri.race.Segment.list().sort{a,b-> a.segmentOrder <=> b.segmentOrder}}" var="segment">
            <li class="well" data-id="${segment.id}">${segment}</li>
          </g:each>
        </ul>
      </div>
  </fieldset>
    </g:form>
</div>
<script>
  $(function () {
    $("#segmentOptIn, #segmentOptOut").sortable({
                                           connectWith:".connectedSortable"
                                         }).disableSelection();

    $('#saveSegments').on('click',function(){
      var ids = [];
      $('#segmentOptIn li').each(function(i,o){
        ids.push($(o).data('id'));
      });
      $('#segments').val(ids);
      console.log($('#segments').val());
      if(ids.length > 0){
        $('#addSegmentsForm').submit()
      } else {
        $('#segmentsAlert').modal();
      }
    });
  });
</script>

<div class="modal hide" id="segmentsAlert">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">×</button>
    <h3>Add Segments</h3>
  </div>
  <div class="modal-body">
    <p>Please add one (1) or more segments to the race to save.</p>
  </div>
  <div class="modal-footer">
    <a href="javascript:void(0);" class="btn" data-dismiss="modal">Close</a>
  </div>
</div>
</body>
</html>