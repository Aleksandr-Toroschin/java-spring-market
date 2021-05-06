angular.module('app', []).controller('addController', function ($scope, $http) {
    // const contextPath = 'http://localhost:8189/market';
    const contextPath = '/market';

    $scope.init = function () {
        $scope.title = "";
        $scope.cost = 0;
        $scope.mainPage = contextPath;
    };

    $scope.addProduct = function() {
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
            .then(function successCallback(response) {
                location.replace(contextPath);
            }, function errorCallback(response) {
                alert('Error!');
            });
    };

    $scope.init();
});