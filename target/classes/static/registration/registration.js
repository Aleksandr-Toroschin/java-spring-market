angular.module('app').controller('registrationController', function ($scope, $http) {
    const contextPath = '/market';

    $scope.init = function () {
        $scope.mainPage = contextPath;
        $scope.regSuccess = false;
    };

    $scope.newUserRegistration = function() {
        $http.post(contextPath + '/api/v1/users/registration', $scope.newUser)
            .then(function successCallback(response) {
                $scope.regSuccess = true;
                console.log(response.data);
                // location.replace(contextPath);
            }, function errorCallback(response) {
                $scope.regSuccess = false;
                alert(response.data.messages);
                $scope.newUser = null;
            });
    };

    $scope.registered = function () {
        return $scope.regSuccess;
    };

    $scope.init();
});