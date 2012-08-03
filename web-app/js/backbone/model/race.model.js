var Race = Backbone.Model.extend({
                                     //url:"/triharder/race/backbone"
//                                     defaults:function () {
//                                         return {
//                                             date:new Date(),
//                                             name:''
//                                         };
//                                     }
                                 });

var RaceList = Backbone.Collection.extend({
                                              model: Race,
                                              url:'/triharder/race/backbone'
                                          });