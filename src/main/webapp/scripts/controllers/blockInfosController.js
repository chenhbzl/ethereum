angular.module('ethExplorer')
    .controller('blockInfosCtrl', function ($rootScope, $scope, $location, $routeParams, $q) {
        $scope.init = function() {
            $scope.blockId = $routeParams.blockId;
            $scope.blockNumber=web3.eth.blockNumber;
            if ($scope.blockId !== undefined) {
                getBlockInfos($scope.blockId)
                    .then(function(result) {
                        var blockNumber = web3.eth.blockNumber;
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
                            var info = web3.eth.getBlock($scope.blockNumber);
                            if (info !== undefined){
                                var newDate = new Date();
                                newDate.setTime(info.timestamp * 1000);
                                $scope.time = newDate.toUTCString();
                            }
                        }
                    });

            } else {
                $location.path("/");
            }


            function getBlockInfos(blockId){
                var deferred = $q.defer();
                //获取区块信息
                web3.eth.getBlock(blockId, function(error, result) {
                    if(!error) {
                        deferred.resolve(result);
                    } else {
                        deferred.reject(error);
                    }
                });
                return deferred.promise;
            }
        };

        $scope.init();

        // parse transactions
        $scope.transactions = []

        web3.eth.getBlockTransactionCount($scope.blockId, function(error, result){//获取一个块内的交易数
            var txCount = result
            for (var blockIdx = 0; blockIdx < txCount; blockIdx++) {
            	console.log("Result: ", $scope.blockId+"-------"+blockIdx);
                web3.eth.getTransactionFromBlock($scope.blockId, blockIdx, function(error, result) {//获取某个区块第几个交易信息
	                console.log("Result: ", result.hash);
                    web3.eth.getTransactionReceipt(result.hash, function(error, receipt) {//根据hash获取某个交易的详细信息
                        var transaction = {
                            id: receipt.transactionHash,
                            hash: receipt.transactionHash,
                            from: receipt.from,
                            to: receipt.to,
                            gas: receipt.gasUsed,
                            input: result.input.slice(2),
                            value: parseInt(result.value),
                            contractAddress: receipt.contractAddress
                        }
                        $scope.$apply(
                            $scope.transactions.push(transaction)
                        );
                    });
                })
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
