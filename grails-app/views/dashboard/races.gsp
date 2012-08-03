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
        <a class="btn" href="#"><i class="icon-tag"></i> Create Race</a>
      </div>
    </div>
  </div>
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

<script language="JavaScript">
  var App = {
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
    App.initialize();
    App.raceView = new RaceView({model:new RaceList()});
  });
</script>
</body>
</html>