loginApp = angular.module('loginApp', [])

loginApp.controller('loginController', function ($scope) {
$scope.formfield = {};
$scope.submitForm = function (formData) {
 	alert('Form submitted with' + JSON.stringify(formData));
};
});
