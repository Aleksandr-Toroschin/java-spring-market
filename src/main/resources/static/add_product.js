angular.module('app', []).controller('addController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.init = function () {
        $scope.title = "";
        $scope.cost = 0;
        $scope.mainPage = contextPath;
    };

    $scope.addProduct = function() {
        // var title = document.getElementById("titleField").value;
        // var cost = document.getElementById("costField").value;
        // const body = {"title": title, "cost": cost};
        // $http.post(contextPath + '/api/v1/products', $scope.newProduct)
        //     .then(function (response) {
        //         // $scope.newProduct = null;
        //         location.replace(contextPath);
        //     });
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
            .then(function successCallback(response) {
                location.replace(contextPath);
            }, function errorCallback(response) {
                alert('Error!');
            });
    };

    $scope.init();
});