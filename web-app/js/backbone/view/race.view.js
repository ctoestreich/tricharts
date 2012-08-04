var RaceView = Backbone.View.extend({
                                        el:$("#races-view"),
                                        events:{
                                            "click #refresh-races":"refreshRaces",
                                            "click #add-race":"saveRace"
                                        },
                                        initialize:function () {
                                            this.model.bind("reset", this.render, this);
                                            this.model.bind("change", this.render, this);
                                            this.model.bind("destroy", this.refreshRaces, this);
                                            this.model.bind("create", this.refreshRaces, this);
                                            var templateSource = $("#race-list-template").html();
                                            this.template = Handlebars.compile(templateSource);
                                            this.model.fetch();
                                        },
                                        render:function () {
                                            $("#races").html('');
                                            var model, markUp;
                                            _.each(this.model.models, function (race) {
                                                markUp = this.template(race.toJSON());
                                                $("#races").append(markUp);
                                            }, this);
                                            $(".delete-race").on('click', this.deleteRace);
                                            return this;
                                        },
                                        refreshRaces:function () {
                                            console.log('doing refresh');
                                            this.model.fetch();
                                        },
                                        deleteRace:function () {
                                            var id = $(this).data('id');
                                            if(id) {
                                                var model = app.raceView.model.get({id:id});
                                                model.destroy();
                                            }
                                        },
                                        saveRace:function () {
//                                            var race = new Race({
//                                                         name:'New Race',
//                                                         date:new Date(),
//                                                         raceType:'Triathlon'
//                                                     });
//                                            console.log(race.toJSON());
//                                            app.raceList.create(race);
                                        }
                                    });