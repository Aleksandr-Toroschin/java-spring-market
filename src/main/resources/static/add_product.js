angular.module('app', []).controller('addController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.init = function () {
        $scope.title = "";
        $scope.cost = 0;
        $scope.mainPage = contextPath;
    };

    $scope.addProduct = function() {
        var title = document.getElementById("titleField").value;
        var cost = document.getElementById("costField").value;
        const body = {"title": title, "cost": cost};

        $http.post(contextPath + '/api/v1/products', body)
            .then(function (response) {
                location.replace(contextPath);
            });
    };

    $scope.init();
});