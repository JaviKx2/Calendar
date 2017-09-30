var createApp = angular.module('createApp', [])
 
createApp.controller('createCtrl', function ($scope, $http) {
	$scope.formData = {};
	$scope.mensaje = "Poh aqui estoy";
	$scope.incorrectas = false;
	$scope.fechasIncorrectas = function(){
		var startdate = $scope.formData.startdate;
		var starttime = $scope.formData.starttime;
		var endtime = $scope.formData.endtime;
		var enddate = $scope.formData.enddate;
		
		if(typeof startdate === 'undefined'
			|| typeof enddate === 'undefined'){
			$scope.incorrectas = false;
		}else{
			if(moment(startdate) < moment(enddate)){
				if(typeof starttime === 'undefined'
					|| typeof endtime === 'undefined'){
					$scope.incorrectas = false;
				}else{
					if(moment(starttime, "HH:mm") < moment(endtime, "HH:mm")){
						$scope.incorrectas = false;										
					}else{
						$scope.incorrectas = true;		
					}
				}
			}else{
				$scope.incorrectas = true;		
			}
		}
		if($scope.incorrectas){
			$scope.mensaje = "La fecha inicial no puede ser superior a la final.\n" +
			"Revisa la fecha o la hora.";		
		}else{
			$scope.mensaje = "Fechas correctas.";
		}
	}
	
	$scope.updateStartDate = function() {
	    $scope.formData.startdate = moment($scope.startdate).format("YYYY-MM-DD");
	    $scope.resultados = JSON.stringify($scope.formData);
	    $scope.fechasIncorrectas();
	}
	$scope.updateEndDate = function() {
		$scope.formData.enddate = moment($scope.enddate).format("YYYY-MM-DD");
		$scope.resultados = JSON.stringify($scope.formData);
		$scope.fechasIncorrectas();
	}
	$scope.updateStartTime = function() {
		$scope.formData.starttime = moment($scope.starttime).format("HH:mm");
		$scope.resultados = JSON.stringify($scope.formData);
		$scope.fechasIncorrectas();
	}
	$scope.updateEndTime = function() {
		$scope.formData.endtime = moment($scope.endtime).format("HH:mm");
		$scope.resultados = JSON.stringify($scope.formData);
		$scope.fechasIncorrectas();
	}
	$scope.saveEvent = function(){

		$http.post("http://localhost:8080/PI_Project/rest/events", $scope.resultados)
		.success(function() {
	    	alert("Evento guardado con éxito.");
	    });

	}
});
