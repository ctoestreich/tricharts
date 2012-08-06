<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>User Dashboard</title>
  <r:require module="raceMvc"/>
</head>

<body>
<div class="row-fluid" id="races-view">
  <div class="well">
    <div class="btn-group-wrap">
      <div class="btn-group">
        <a class="btn" href="#" id="refresh-races"><i class="icon-search"></i> Refresh Races</a>
        <a class="btn" href="#" id="add-race"><i class="icon-tag"></i> Create Race</a>
      </div>
    </div>
  </div>
</div>

<div class="row-fluid" style="display: none;" id="createRaceDiv">

  <form id="createRaceForm" class="form-horizontal well">
    <fieldset>
      <legend>New Race</legend>
      <f:with bean="race">
        <f:field property="name"/>
        <f:field property="date"/>
      </f:with>
      <div class="control-group">
        <button class="btn" id="save-race">Save</button>
      </div>
    </fieldset>
  </form>
</div>

<table class="table table-bordered">
  <thead>
  %{--<th>ID</th>--}%
  <th>Name</th>
  <th>Date</th>
  <th>Race Type</th>
  <th>Admin</th>
  </thead>
  <tbody id="races">

  </tbody>

</table>


<script id="race-list-template" type="text/x-handlebars-template">
  <tr>
    %{--<td>{{id}}</td>--}%
    <td>{{name}}</td>
    <td>{{date}}</td>
    <td>{{raceType.name}} {{raceCategoryType.name}}</td>
    <td>
      <div class="btn-group">
        <button class="btn btn-info">Action</button>
        <button class="btn btn-info dropdown-toggle" data-toggle="dropdown">
          <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
          <li><a href="#">Edit</a></li>
          <li class="divider"></li>
          <li><a href="#" class='delete-race' data-id="{{id}}">Delete</a></li>
        </ul>
      </div></td>
  </tr>
</script>

<script id="race-create-template" type="text/x-handlebars-template">

</script>

<script language="JavaScript">
  var app = {
    initialize:function () {
      this.createDialogs();
    },
    createDialogs:function () {
      $(".dialog").modal({
                           show:false
                         });
    }
  };

  $(function () {
    app.initialize();
    app.raceList = new RaceList();
    app.raceView = new RaceView({model:app.raceList});
  });

  function clearForm(oForm) {
    var elements = oForm.elements;
    oForm.reset();
    for(i=0; i<elements.length; i++) {
      field_type = elements[i].type.toLowerCase();
      switch(field_type) {
        case "text":
        case "password":
        case "textarea":
        case "hidden":
          elements[i].value = "";
          break;
        case "radio":
        case "checkbox":
          if (elements[i].checked) {
            elements[i].checked = false;
          }
          break;
        case "select-one":
        case "select-multi":
          elements[i].selectedIndex = -1;
          break;
        default:
          break;
      }
    }
  }
</script>
</body>
</html>