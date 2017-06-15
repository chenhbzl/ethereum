angular.module('ethExplorer')
    .controller('transactionInfosCtrl', function ($rootScope, $scope, $location, $routeParams,$q,$http) {
        $scope.init=function(){
           $scope.txId=$routeParams.transactionId;
           if($scope.txId!==undefined) {
                getTransactionInfos()
                    .then(function(result){
                    var number = 0;
                    $http.post('http://127.0.0.1:8080/block/blockNumber').success(function(data){
                    	number=data;
                    });
                    $scope.result = result;

                    if(result.blockHash!==undefined){
                        $scope.blockHash = result.blockHash;
                    }
                    else{
                        $scope.blockHash ='pending';
                    }
                    if(result.blockNumber!==undefined){
                        $scope.blockNumber = result.blockNumber;
                    }
                    else{
                        $scope.blockNumber ='pending';
                    }
                    $scope.from = result.from;
                    $scope.gas = result.gas;
                    $scope.gasPrice = web3.fromWei(result.gasPrice, "ether") + " ETH";
                    $scope.hash = result.hash;
                    $scope.input = result.input; // that's a string
                    $scope.nonce = result.nonce;
                    $scope.to = result.to;
                    $scope.transactionIndex = result.transactionIndex;
                    $scope.ethValue = web3.fromWei(result.value, "ether");
                    $scope.txprice = web3.fromWei(result.gas * result.gasPrice, "ether") + " ETH";
                    if($scope.blockNumber!==undefined){
                        $scope.conf = number - $scope.blockNumber + " Confirmations";
                        if($scope.conf==0){
                            $scope.conf='unconfirmed'; 
                        }
                    }
                       
                    if($scope.blockNumber!==undefined){
                        $http.post('http://127.0.0.1:8080/block/getBlock?blockId='+$scope.blockNumber).success(function(data){
                        	if (data !== undefined){
                        		var newDate = new Date();
                        		newDate.setTime(data.timestamp * 1000);
                        		$scope.time = newDate.toUTCString();
                        	}
                        });
                    }

                });

            }

            else{
                $location.path("/"); // add a trigger to display an error message so user knows he messed up with the TX number
            }

            function getTransactionInfos(){
                var deferred = $q.defer();
                $http.post('http://127.0.0.1:8080/block/ethGetTransactionByHash?txId='+$scope.txId).success(function(result){
                   deferred.resolve(result);
                });
                return deferred.promise;

            }
        };
        $scope.init();
    });
