var Race = Backbone.Model.extend({
                                     //url:"/tricharts/race/backbone"
//                                     defaults:function () {
//                                         return {
//                                             date:new Date(),
//                                             name:''
//                                         };
//                                     }
                                 });

var RaceList = Backbone.Collection.extend({
                                              model: Race,
                                              url:'/tricharts/race/backbone'
                                          });