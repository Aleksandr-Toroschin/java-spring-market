angular.module('app').controller('productInfoController', function ($scope, $http, $localStorage, $routeParams) {
    const contextPath = '/market';

    $scope.showProduct = function () {
        $http({
            url: contextPath + '/api/v1/products/' + $routeParams.productIdParam,
            method: 'GET',
        }).then(function (response) {
            $scope.prod = response.data;
            $http({
                url: contextPath + '/api/v1/products/remarks/' + $routeParams.productIdParam,
                method: 'GET',
            }).then(function (response) {
                console.log(response.data);
                $scope.remarks = response.data;
            });
        });
    };

    $scope.addRemark = function() {
        $http({
            url: contextPath + '/api/v1/products/addremark',
            method: 'POST',
            params: {
                prodId: $scope.prod.id,
                content: $scope.newRemark.content
            }
        }).then(function () {
            $scope.newRemark = null;
            $scope.showProduct();
        });
    };

    $scope.showProduct();
});