(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage', 'ngCookies'])
        .config(config)
        .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'home/home.html',
                controller: 'homeController'
            })
            .when('/products', {
                templateUrl: 'products/products.html',
                controller: 'productsController'
            })
            .when('/add_product', {
                templateUrl: 'add_product/add_product.html',
                controller: 'add_productController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .when('/product_info/:productIdParam', {
                            templateUrl: 'product_info/product_info.html',
                            controller: 'productInfoController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.marketCurrentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketCurrentUser.token;
            // $scope.login = $localStorage.marketCurrentUser.username;
        }

        if ($localStorage.marketCartId) {
        } else {
            const contextPath = '/market';

            $http({
                url: contextPath + '/api/v1/cart/generate',
                method: 'GET'
            }).then(function (response) {
                $localStorage.marketCartId = response.data.text;
            });
        }

    }
})();

angular.module('app').controller('indexController', function ($scope, $http, $location, $localStorage, $cookies) {
    const contextPath = '/market';

    $scope.init = function () {
//        $scope.addProductPage = contextPath + '/add_product.html';
//        $scope.infoPage = contextPath + '/product_info/';
        $scope.registrationPage = contextPath + '/registration.html';
    };

    $scope.mergeCarts = function () {
        console.log('ready');
        $http({
            url: contextPath + '/api/v1/cart/merge',
            method: 'GET',
            params: {
                'cartName': $localStorage.marketCartId
            }
        }).then(function (response) {
            $localStorage.marketCartId = response.data.text;
            console.log('ready');
        });
    }

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

                        $scope.mergeCarts();

                        $scope.user.username = null;
                        $scope.user.password = null;
                    }
                    $location.path('/');
            }, function errorCallback(response) {
                    alert("неверный логин или пароль");
                    $scope.user.username = null;
                    $scope.user.password = null;
            });
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.marketCurrentUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.cartProducts = null;
        $scope.ordersPage = null;
        $location.path('/');
    };

    $scope.getCartName = function() {
        $http({
            url: contextPath + '/api/v1/cart/generate',
                method: 'GET'
        }).then(function (response) {
                $localStorage.marketCartId = response.data.text;
        });
    };

    $scope.clearUser = function () {
        delete $localStorage.marketCurrentUser;
        $http.defaults.headers.common.Authorization = '';
        $scope.login = '';
        $scope.getCartName();
    };

    $scope.showProfile = function() {
        alert($scope.login);
    };

    $scope.isCartNotEmpty = function () {
        console.log("проверяем");
        if ($scope.isUserLoggedIn) {
            console.log("пользователь есть");
            $http.get(contextPath + '/api/v1/cart')
                .then(function (response) {
                    console.log("получен ответ");
                    console.log(response.data);
                    if (response.data.length > 0) {
                        return true;
                    } else {
                        return false;
                    }
                });
        }
        return false;
    };

    if ($localStorage.marketCurrentUser) {
        $scope.login = $localStorage.marketCurrentUser.username;
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketCurrentUser.token;
    }

    $scope.init();
});