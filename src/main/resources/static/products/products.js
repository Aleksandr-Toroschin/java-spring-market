angular.module('app').controller('productsController', function ($scope, $http, $location, $localStorage) {
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

    $scope.addProductInCart = function (id) {
        $http.post(contextPath + '/api/v1/cart/' + id)
            .then(function () {
                console.log("Ok");
            });
    };

    $scope.isCartNotEmpty = function () {
        $http.get(contextPath + '/api/v1/cart/count')
            .then(function (response) {
                console.log(response.data);
                if (response.data>0) {
                    return true;
                } else {
                    return false;
                }
            });
    };

    $scope.showProducts(1);
});