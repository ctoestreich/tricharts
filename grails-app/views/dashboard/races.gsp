<!doctype html>
<html>
<head>
  <meta name="layout" content="bootstrap"/>
  <title>User Dashboard</title>

</head>

<body>
<div id="races"></div>
<script id="race-list-template" type="text/x-handlebars-template">
  <div class="row">
    {{id}}<BR>{{name}}
  </div>
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
    App.raceView = new RaceView({collection:new RaceList()});
  });
</script>
</body>
</html>