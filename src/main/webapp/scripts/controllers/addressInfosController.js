angular.module('ethExplorer')
    .controller('addressInfosCtrl', function ($rootScope, $scope, $location, $routeParams, $q) {

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
                var deferred = $q.defer();//////
                console.log(deferred);
                web3.eth.getBalance($scope.addressId,function(error, result) {
                    console.log(result);
                    if(!error) {
                        deferred.resolve({
                            balance: result.toNumber(),
                            balanceInEther: web3.fromWei(result, 'ether').toNumber()
                        });
                    } else {
                        deferred.reject(error);
                    }
                });
                return deferred.promise;
            }


        };

        $scope.init();

    });