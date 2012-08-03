var RaceView = Backbone.View.extend({
                                        el:$("#race-create-button"),
                                        initialize:function () {
                                            this.model.bind('change', this.render, this);
                                            var templateSource = $("#response-template").html();
                                            this.template = Handlebars.compile(templateSource);
                                        },
                                        render:function () {
                                           alert(JSON.stringify(this.model.toJSON()));
                                        }
                                    });