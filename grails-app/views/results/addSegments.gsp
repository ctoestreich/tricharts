<%@ page import="com.tgid.tri.common.SegmentComparator; com.tgid.tri.race.SegmentType; com.tgid.tri.race.Segment; com.tgid.tri.auth.User" %>
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

<h3>Add segments to ${raceInstance}</h3>
<br/>
<g:form controller="results" action="addSegments" name="addSegmentsForm" id="addSegmentsForm">
  <g:hiddenField name="segments" id="segments" value=""/>
  <g:hiddenField name="raceId" value="${raceInstance.id}"/>

  <div class="row-fluid">
    <div class="span6">
      <div class="row">
      <h5>Segments In Race (drag right to remove)</h5>
      <ul id="segmentOptIn" class="connectedSortable"><br/></ul>
      </div>
      <br />
            <div class="row">
      <p><a href="#" id="saveSegments" class="btn">Save Segments</a></p>
      </div>
    </div>
    <div class="span6">
      <h5>Available Segments (drag left to add)</h5>

      <ul class="nav nav-tabs" id="myTab">
        <li class="active"><a href="#swim">Swim</a></li>
        <li><a href="#bike">Bike</a></li>
        <li><a href="#run">Run</a></li>
        <li><a href="#transition">Transition</a></li>
      </ul>

      <div class="tab-content"><div class="tab-pane active" id="swim">
        <ul id="segmentSwim" class="connectedSortable">
          <g:each in="${com.tgid.tri.race.Segment.findAllBySegmentType(SegmentType.Swim).unique(new SegmentComparator()).sort {a, b -> a.distance <=> b.distance}}" var="segment">
            <li class="well" data-id="${segment.id}">${segment}</li>
          </g:each>
        </ul>
      </div>

        <div class="tab-pane" id="bike">
          <ul id="segmentBike" class="connectedSortable">
            <g:each in="${com.tgid.tri.race.Segment.findAllBySegmentType(SegmentType.Bike).unique(new SegmentComparator()).sort {a, b -> a.distance <=> b.distance}}" var="segment">
              <li class="well" data-id="${segment.id}">${segment}</li>
            </g:each>
          </ul>
        </div>

        <div class="tab-pane" id="run">
          <ul id="segmentRun" class="connectedSortable">
            <g:each in="${com.tgid.tri.race.Segment.findAllBySegmentType(SegmentType.Run).unique(new SegmentComparator()).sort {a, b -> a.distance <=> b.distance}}" var="segment">
              <li class="well" data-id="${segment.id}">${segment}</li>
            </g:each>
          </ul>
        </div>

        <div class="tab-pane" id="transition">
          <ul id="segmentTransition" class="connectedSortable">
            <g:each in="${com.tgid.tri.race.Segment.where {
              segmentType == SegmentType.T1 || segmentType == SegmentType.T2
            }.list().unique(new SegmentComparator()).sort {a, b -> a.toString() <=> b.toString()}}" var="segment">
              <li class="well" data-id="${segment.id}">${segment}</li>
            </g:each>
          </ul>
        </div></div>
      <script>
        $(function () {

          $('#myTab a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
          });
          $('#myTab a:first').tab('show');
        });
      </script>
    </div>
  </div>
</g:form>

<script>
  $(function () {
    $("#segmentOptIn, #segmentSwim, #segmentBike, #segmentRun, #segmentTransition").sortable({
                                                                                               connectWith:".connectedSortable"
                                                                                             }).disableSelection();

    $('#saveSegments').on('click', function () {
      var ids = [];
      $('#segmentOptIn li').each(function (i, o) {
        ids.push($(o).data('id'));
      });
      $('#segments').val(ids);
      console.log($('#segments').val());
      if(ids.length > 0) {
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