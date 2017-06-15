angular.module('ethExplorer')
    .controller('addressInfosCtrl', function ($rootScope, $scope, $location, $routeParams, $q,$http) {

        $scope.init=function(){

            $scope.addressId=$routeParams.addressId;
             console.log($routeParams.addressId);
            if($scope.addressId!==undefined) {
                getAddressInfos().then(function(result){
                    $scope.balance = result.balance;
                    $scope.balanceInEther = result.balanceInEther;
                });
            }


            function getAddressInfos(){
                var deferred = $q.defer();
                console.log(deferred);
                $http.post('http://127.0.0.1:8080/block/getBalance?addressId='+$scope.addressId).success(function(result){
                    console.log(result);
                    deferred.resolve({
                        balance: result,
                        balanceInEther: web3.fromWei(result, 'ether')
                    });
                });
                return deferred.promise;
            }


        };

        $scope.init();

    });