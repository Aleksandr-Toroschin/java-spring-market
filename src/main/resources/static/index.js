angular.module('app', []).controller('indexController', function ($scope, $http) {
    // const contextPath = 'http://localhost:8189/market';
    const contextPath = '/market';

    $scope.init = function (page) {
        $scope.addProductPage = contextPath + '/add_product.html';
        $scope.infoPage = contextPath + '/api/v1/products/';
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
        $scope.showCart();
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

    $scope.clearCart = function () {
        $http({
            url: contextPath + '/api/v1/cart/clear',
            method: 'DELETE'
        }).then(function (response) {
            $scope.showCart();
        });
    };

    $scope.init(1);
});