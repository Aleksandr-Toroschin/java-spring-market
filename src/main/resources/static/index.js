(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
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
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.marketCurrentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketCurrentUser.token;
            // $scope.login = $localStorage.marketCurrentUser.username;
        }
    }
})();

angular.module('app').controller('indexController', function ($scope, $http, $location, $localStorage) {
    const contextPath = '/market';

    $scope.init = function () {
        $scope.addProductPage = contextPath + '/add_product.html';
        $scope.infoPage = contextPath + '/api/v1/products/';
        $scope.registrationPage = contextPath + '/registration.html';
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

    if ($localStorage.marketCurrentUser) {
        $scope.login = $localStorage.marketCurrentUser.username;
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketCurrentUser.token;
    }

    $scope.init();
});