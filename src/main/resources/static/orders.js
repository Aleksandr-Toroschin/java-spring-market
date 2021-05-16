angular.module('app', ['ngStorage']).controller('ordersController', function ($scope, $http, $location, $localStorage) {
    const contextPath = '/market';

    $scope.showOrders = function (page) {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'GET',
            params: {
                p: page
            }
        }).then(function (response) {
            $scope.ordersPage = response.data;

            let minPageIndex = page - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = page + 2;
            if (maxPageIndex > $scope.ordersPage.totalPages) {
                maxPageIndex = $scope.ordersPage.totalPages;
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

    if ($localStorage.marketCurrentUser) {
        $scope.login = $localStorage.marketCurrentUser.username;
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketCurrentUser.token;
    }

    $scope.mainPage = contextPath;
    $scope.showOrders(1);
});