var viewsApp = angular.module('viewsApp', ['ngRoute']);

viewsApp.config(function($routeProvider) {
	$routeProvider
		.when('/', { templateUrl : 'day_view.html', controller  : 'mainController' })
		.when('/day', { templateUrl : 'day_view.html', controller  : 'dayController' })
		.when('/week', { templateUrl : 'week_view.html',  controller  : 'weekController' })
		.when('/month', { templateUrl : 'month_view.html', controller  : 'monthController' })		
		.when('/4days', { templateUrl : '4days_view.html', controller  : '4daysController' })
		.when('/agenda', { templateUrl : 'agenda_view.html', controller  : 'agendaController' });
});

viewsApp.factory("viewsFactory", function(){
	return{
        dayFw : function(date){
        	return moment(date).add(1, 'days');
        },
        weekFw : function(date){
            return moment(date).add(1, 'weeks');
        },
        monthFw : function(date){
            return moment(date).add(1, 'months');
        },
        dayBw : function(date){
        	return moment(date).add(-1, 'days');
        },
        weekBw : function(date){
            return moment(date).add(-1, 'weeks');
        },
        monthBw : function(date){
            return moment(date).add(-1, 'months');
        },
        cmpDates : function(startdate, enddate){
        	var iguales = false;
        	if(startdate.diff(enddate) == 0) iguales = true;
        	return iguales;
        }, 
        startOfWeek : function(date){
        	return moment(date).startOf('week').format('DD');
        }, 
        endOfWeek : function(date){
        	return moment(date).endOf('week').format('DD');
        }, 
        rellenarDias : function(date){
    		var prevDate = moment(date);
    		var wkDays_aux = [];
    		date = moment(moment(date).startOf('week'));
    		for(var i=0; i<7; i++){
    			date = moment(date).add(1, 'days');		
    			wkDays_aux.push(moment(date).format('DD'));
    		};
    		
    		date = moment(prevDate);
    		return wkDays_aux;
    	},
        
        cleanArray : function(events){
        	  var events_new = new Array();
        	  for( var i = 0, j = events.length; i < j; i++ ){
        	      if ( events[ i ] ){
        	        events_new.push( events[ i ] );
        	    }
        	  }
        	  return events_new;
        },
    	
    	url: function(view){
    		var url;
            if(view == 0) url = "day";
            else if(view == 1) url = "week";
            else if(view == 2) url = "month";
            else if(view == 3) url = "4days";
            else if(view == 4) url = "agenda";
            return url;
    	}
        
	};
});

viewsApp.controller('mainController', function($scope, $http, $location, viewsFactory) {
	$http.get("http://localhost:8080/PI_Project/rest/events")
    .success(function(response) {
    	$scope.events = response;
    });
	
	$scope.date = moment().format('YYYY[-]MM[-]DD');
	$scope.deleteEvent = function (id, view){
	  $http.delete('http://localhost:8080/PI_Project/rest/events/' + id)
	  .success(function (data, status) {
		  console.log(data);
            alert("Evento eliminado satisfactoriamente.");
            
            $location.url(viewsFactory.url(view));       
            $scope.events = viewsFactory.cleanArray($scope.events);
            for(var i=0; i<$scope.events.length; i++){
            	if(id == $scope.events[i].id){
            		delete $scope.events[i];
            	}
            }
       });
	};
});

viewsApp.controller('dayController', function($scope) {
});

viewsApp.controller('weekController', function($scope, viewsFactory) {
	$scope.weekDays = ["lun", "mar", "miér", "jue", "vie", "sáb", "dom"];
	$scope.hours = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23];
	
	$scope.month = moment($scope.date).locale('es').format('MMMM');
	$scope.year = moment($scope.date).locale('es').format('YYYY');
	$scope.startDay = viewsFactory.startOfWeek($scope.date);
	$scope.endDay = viewsFactory.endOfWeek($scope.date);
	$scope.wkDays = viewsFactory.rellenarDias($scope.date);	
	
	$scope.weekToday = function(){
		$scope.date = moment().format('YYYY[-]MM[-]DD');
		$scope.startDay = viewsFactory.startOfWeek($scope.date);
		$scope.endDay = viewsFactory.endOfWeek($scope.date);
		$scope.month = moment($scope.date).locale('es').format('MMMM');
		$scope.year = moment($scope.date).locale('es').format('YYYY');
		$scope.wkDays = viewsFactory.rellenarDias($scope.date);	
	};
	$scope.weekFw = function(){
		$scope.date = viewsFactory.weekFw($scope.date);
		$scope.startDay = viewsFactory.startOfWeek($scope.date);
		$scope.endDay = viewsFactory.endOfWeek($scope.date);
		$scope.month = moment($scope.date).locale('es').format('MMMM');
		$scope.year = moment($scope.date).locale('es').format('YYYY');
		$scope.wkDays = viewsFactory.rellenarDias($scope.date);		
	};
	$scope.weekBw = function(){
		$scope.date = viewsFactory.weekBw($scope.date);
		$scope.startDay = viewsFactory.startOfWeek($scope.date);
		$scope.endDay = viewsFactory.endOfWeek($scope.date);
		$scope.month = moment($scope.date).locale('es').format('MMMM');
		$scope.year = moment($scope.date).locale('es').format('YYYY');
		$scope.wkDays = viewsFactory.rellenarDias($scope.date);	
	};
	$scope.cmpDayAndHour = function(startdate, starttime, day, hour){
		var iguales = false;
		var d = moment(startdate).format('DD');
		var m = moment(startdate).format('MM');
		var y = moment(startdate).format('YYYY');
		var h = moment(starttime, "HH:mm").format('HH');
		if(h == parseInt(hour) 
				&& d == day 
				&& m == moment($scope.date).format('MM') 
				&& y == moment($scope.date).format('YYYY')){
			iguales = true;
		}

		return iguales;
	};
	
});

viewsApp.controller('monthController', function($scope, viewsFactory) {
	$scope.monthRows = [0,1,2,3,4,5];
	$scope.monthColumns = [0,1,2,3,4,5,6]; 
	
	$scope.month = moment($scope.date).locale('es').format('MMMM');
	$scope.year = moment($scope.date).locale('es').format('YYYY');
	$scope.startDay = moment($scope.date).startOf('month').format('E');
	$scope.endDay = moment($scope.date).endOf('month').format('DD');
	
	$scope.monthToday = function(){
		$scope.date = moment().format('YYYY[-]MM[-]DD');
		$scope.month = moment($scope.date).locale('es').format('MMMM');
		$scope.year = moment($scope.date).locale('es').format('YYYY');
		$scope.startDay = moment($scope.date).startOf('month').format('E');
		$scope.endDay = moment($scope.date).endOf('month').format('DD');
	};
	$scope.monthFw = function(){
		$scope.date = viewsFactory.monthFw($scope.date);
		$scope.month = moment($scope.date).format('MMMM');
		$scope.year = moment($scope.date).format('YYYY');
		$scope.startDay = moment($scope.date).startOf('month').format('E');
		$scope.endDay = moment($scope.date).endOf('month').format('DD');
	};
	$scope.monthBw = function(){
		$scope.date = viewsFactory.monthBw($scope.date);
		$scope.month = moment($scope.date).format('MMMM');
		$scope.year = moment($scope.date).format('YYYY');
		$scope.startDay = moment($scope.date).startOf('month').format('E');
		$scope.endDay = moment($scope.date).endOf('month').format('DD');
	};
	$scope.cmp = function(startdate, day){
		var iguales = false;
		var d = moment(startdate).format('DD');
		var m = moment(startdate).format('MM');
		var y = moment(startdate).format('YYYY');
		if(d == day && m == moment($scope.date).format('MM') && y == moment($scope.date).format('YYYY')){
			iguales = true;
		}
		return iguales;
	};

});

viewsApp.controller('4daysController', function($scope) {
	
});

viewsApp.controller('agendaController', function($scope) {
	
});
