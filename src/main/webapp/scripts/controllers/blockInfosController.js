angular.module('ethExplorer')
    .controller('blockInfosCtrl', function ($rootScope, $scope, $location, $routeParams, $q,$http) {
        $scope.init = function() {
            $scope.blockId = $routeParams.blockId;
            if ($scope.blockId !== undefined) {
                getBlockInfos($scope.blockId)
                    .then(function(result) {
                    	$http.post('http://127.0.0.1:8080/block/blockNumber').success(function(data){
                    		blockNumber=data;
                        });
                        $scope.result = result;

                        if (result.hash !== undefined) {
                            $scope.hash = result.hash;
                        } else{
                            $scope.hash ='pending';
                        }
                        if (result.miner !== undefined){
                            $scope.miner = result.miner;
                        } else{
                            $scope.miner ='pending';
                        }

                        $scope.gasLimit = result.gasLimit;
                        $scope.gasUsed = result.gasUsed;
                        $scope.nonce = result.nonce;
                        $scope.difficulty = ("" + result.difficulty).replace(/['"]+/g, '');
                        $scope.gasLimit = result.gasLimit; // that's a string
                        $scope.nonce = result.nonce;
                        $scope.number = result.number;
                        $scope.parentHash = result.parentHash;
                        $scope.blockNumber = result.number;
                        $scope.timestamp = new Date(result.timestamp * 1000).toUTCString();
                        $scope.extraData = result.extraData.slice(2);
                        $scope.dataFromHex = hex2a(result.extraData.slice(2));
                        $scope.size = result.size;
                        $scope.firstBlock = false;
                        $scope.lastBlock = false;

                        if ($scope.blockNumber != undefined){
                            $scope.conf = number - $scope.blockNumber + " Confirmations";
                            if (number == $scope.blockNumber){
                                $scope.conf = 'Unconfirmed';
                                $scope.lastBlock = true;
                            }
                            if ($scope.blockNumber == 0) {
                                $scope.firstBlock = true;
                            }
                        }

                        if ($scope.blockNumber != undefined){
                            $http.post('http://127.0.0.1:8080/block/getBlock?blockId='+$scope.blockNumber).success(function(data){
                            	if (data !== undefined){
                            		var newDate = new Date();
                            		newDate.setTime(data.timestamp * 1000);
                            		$scope.time = newDate.toUTCString();
                            	}
                            });
                        }
                    });

            } else {
                $location.path("/");
            }


            function getBlockInfos(blockId){
                var deferred = $q.defer();
                //获取区块信息
                $http.post('http://127.0.0.1:8080/block/getBlock?blockId='+blockId).success(function(data){
                    deferred.resolve(data);//请求成功
                }).error(function(data){
                    console.log(data);
                });
                return deferred.promise;
            }
        };

        $scope.init();

        // parse transactions
        $scope.transactions = [];

        $http.post('http://127.0.0.1:8080/block/getBlockTransactionCount?blockId='+$scope.blockId).success(function(result){
            var txCount = result;
            for (var blockIdx = 0; blockIdx < txCount; blockIdx++) {
            	console.log("Result: ", $scope.blockId+"-------"+blockIdx);
            	$http.post('http://127.0.0.1:8080/block/getTransactionFromBlock?blockId='+$scope.blockId+"&blockIdx="+blockIdx).success(function(result){
	                console.log("Result: ", result.hash);
	                $http.post('http://127.0.0.1:8080/block/getTransactionReceipt?hash='+result.hash).success(function(receipt){
                        var transaction = {
                            id: receipt.transactionHash,
                            hash: receipt.transactionHash,
                            from: receipt.from,
                            to: receipt.to,
                            gas: receipt.gasUsed,
                            input: web3.toUtf8(result.input),//需要web3.fromUtf8('投票', 32)存入区块链
                            value: parseInt(result.value),
                            contractAddress: receipt.contractAddress
                        };
                        $scope.transactions.push(transaction);
                    });
                });
            }
        });

        function hex2a(hexx) {
            var hex = hexx.toString();//force conversion
            var str = '';
            for (var i = 0; i < hex.length; i += 2)
                str += String.fromCharCode(parseInt(hex.substr(i, 2), 16));
            return str;
        }
});
