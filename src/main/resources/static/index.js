angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.init = function () {
        $scope.addPage = contextPath + '/add_product.html';
        $scope.infoPage = contextPath + '/api/v1/products/';
        $http.get(contextPath + '/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
            });
        $scope.cartProducts = null;
    };

    $scope.showCart = function () {
        $http.get(contextPath + '/api/v1/cart')
            .then(function (response) {
                $scope.cartProducts = response.data;
                $scope.sum = 0;
                for (let product of $scope.cartProducts) {
                    $scope.sum = $scope.sum + product.cost;
                }
            });
    };

    $scope.addProductInCart = function (id) {
        $http.post(contextPath + '/api/v1/cart/' + id)
            .then(function (response) {
                $scope.showCart();
                console.log("Ok");
            });
    };

    $scope.deleteProductFromCart = function (id) {
        $http({
            url: contextPath + '/api/v1/cart',
            method: 'DELETE',
            params: {
                id: id
            }
        }).then(function (response) {
            $scope.showCart();
        });
    };

    $scope.deleteAllFromCart = function () {
        $http({
            url: contextPath + '/api/v1/cart/del',
            method: 'DELETE'
        }).then(function (response) {
            $scope.showCart();
        });
    };

    $scope.init();
    $scope.showCart();
});