angular.module('app').controller('add_productController', function ($scope, $http, $location, $localStorage) {
    // const contextPath = 'http://localhost:8189/market';
    const contextPath = '/market';

    $scope.init = function () {
        $scope.title = "";
        $scope.cost = 0;
        $scope.mainPage = contextPath;
    };

    $scope.addProduct = function() {
        $http.post(contextPath + '/api/v1/products/add', $scope.newProduct)
            .then(function successCallback(response) {
                location.replace(contextPath);
            }, function errorCallback(response) {
                alert('Error!');
            });
    };

    // if ($localStorage.marketCurrentUser) {
    //     $scope.login = $localStorage.marketCurrentUser.username;
    //     $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketCurrentUser.token;
    // }

    $scope.init();
});