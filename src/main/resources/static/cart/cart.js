angular.module('app').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = '/market';

    $scope.showCart = function () {
        $http({
            url: contextPath + '/api/v1/cart',
            method: 'GET',
            params: {
                cartName: $localStorage.marketCartId
            }
        }).then(function (response) {
                $scope.cartProducts = response.data;
                $scope.sum = $scope.cartProducts.sum;
                // for (let product of $scope.cartProducts) {
                //     $scope.sum = $scope.sum + product.cost;
                // }
            });
    };

    $scope.deleteProductFromCart = function (id) {
        $http({
            url: contextPath + '/api/v1/cart',
            method: 'DELETE',
            params: {
                prodId: id,
                cartName: $localStorage.marketCartId
            }
        }).then(function () {
            $scope.showCart();
        });
    };

    $scope.clearCart = function () {
        $http({
            url: contextPath + '/api/v1/cart/clear',
            method: 'DELETE',
            params: {
                cartName: $localStorage.marketCartId
            }
        }).then(function () {
            $scope.cartProducts = null;
            $scope.sum = null;
            // $scope.showCart();
        });
    };

    $scope.saveOrder = function() {
        $scope.newOrder.cartName = $localStorage.marketCartId;
        $http.post(contextPath + '/api/v1/orders', $scope.newOrder)
            .then(function successCallback(response) {
                    console.log("Заказ сохранен");
                    $scope.clearCart();
                }, function errorCallback(response, request) {
                    alert("какие-то ошибки в запросе ");
                }
            );
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.marketCurrentUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.showCart();
});