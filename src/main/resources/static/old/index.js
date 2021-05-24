angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $location, $localStorage) {
    const contextPath = '/market';

    $scope.init = function () {
        $scope.addProductPage = contextPath + '/add_product.html';
        $scope.infoPage = contextPath + '/api/v1/products/';
        $scope.registrationPage = contextPath + '/registration.html';
    };

    $scope.showProducts = function (page) {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                p: page
            }
        }).then(function (response) {
            $scope.productsPage = response.data;

            let minPageIndex = page - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = page + 2;
            if (maxPageIndex > $scope.productsPage.totalPages) {
                maxPageIndex = $scope.productsPage.totalPages;
            }

            $scope.paginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.showCart = function () {
        $http.get(contextPath + '/api/v1/cart')
            .then(function (response) {
                $scope.cartProducts = response.data;
                $scope.sum = $scope.cartProducts.sum;
                // for (let product of $scope.cartProducts) {
                //     $scope.sum = $scope.sum + product.cost;
                // }
            });
    };

    $scope.showOrders = function () {
        location.replace(contextPath + '/orders.html');
    };

    $scope.addProductInCart = function (id) {
        $http.post(contextPath + '/api/v1/cart/' + id)
            .then(function () {
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
        }).then(function () {
            $scope.showCart();
        });
    };

    $scope.clearCart = function () {
        $http({
            url: contextPath + '/api/v1/cart/clear',
            method: 'DELETE'
        }).then(function () {
            $scope.cartProducts = null;
            $scope.sum = null;
            // $scope.showCart();
        });
    };

    $scope.tryToAuth = function () {
        $scope.login = $scope.user.username;
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $localStorage.marketCurrentUser = {
                            username: $scope.user.username,
                            token: response.data.token
                        };
                        $scope.user.username = null;
                        $scope.user.password = null;
                    }
                }, function errorCallback(response) {
                    alert("неверный логин или пароль");
                    $scope.user.username = null;
                    $scope.user.password = null;
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

    $scope.tryToLogout = function () {
        $scope.clearCart();
        $scope.clearUser();
    };

    $scope.clearUser = function () {
        delete $localStorage.marketCurrentUser;
        $http.defaults.headers.common.Authorization = '';
        $scope.login = '';
    };

    $scope.showProfile = function() {
        alert($scope.login);
    };

    $scope.saveOrder = function() {
        $http.post(contextPath + '/api/v1/orders', $scope.newOrder)
        .then(function successCallback(response) {
                console.log("Заказ сохранен");
                $scope.clearCart();
            }, function errorCallback(response, request) {
                alert("какие-то ошибки в запросе ");
            }
            );
    };

    if ($localStorage.marketCurrentUser) {
        $scope.login = $localStorage.marketCurrentUser.username;
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketCurrentUser.token;
    }

    $scope.init();
    $scope.showProducts(1);
    $scope.showCart();
});