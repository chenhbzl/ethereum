angular.module('ethExplorer')
    .controller('mainCtrl', function ($rootScope, $scope, $location,$http) {
        console.log("开始执行");
        $scope.processRequest= function(){
            var requestStr = $scope.ethRequest.split('0x').join('');

            if(requestStr.length == 40)
                return goToAddrInfos(requestStr)
            else if(requestStr.length == 64){
                if(/[0-9a-zA-Z]{64}?/.test(requestStr))
                    return goToTxInfos(requestStr)
                else if(/[0-9]{1,7}?/.test(requestStr))
                    return goToBlockInfos(requestStr)
            }else if(parseInt(requestStr) > 0)
                return goToBlockInfos(parseInt(requestStr))

            alert('Don\'t know how to handle '+ requestStr)
        };


        function goToBlockInfos(requestStr){
            $location.path('/block/'+requestStr);
        }

        function goToAddrInfos(requestStr){
            $location.path('/address/'+requestStr);
        }

        function goToTxInfos (requestStr){
            $location.path('/transaction/'+requestStr);
        }

    })
    .controller('mainCtrlInit',function($rootScope, $scope, $location,$http,$q,$interval){
        $scope.toggleMenu=function(index) {
            $(".closediv").removeClass("opendiv");
            $(".div"+index).addClass("opendiv").slideToggle("slow");
        };
        $scope.blockNum = web3.eth.blockNumber;
        //获取全部accounts
        // datainit();
        /**
        getAllAddress().then(function(result){
            var address_data=result;
            accountinit(address_data);
        });
        **/
        getAllTrancation().then(function(result){
            $scope.transactions=result;
        });
        function getAllTrancation(){
            var deferred = $q.defer();//声明承诺
            $http.post('http://127.0.0.1:8080/etf/getTransactions')
                .success(function(data){
                    deferred.resolve(data.allAddress);//请求成功
                }).error(function(data){
                    console.log(data);
                });
            return deferred.promise;   // 返回承诺，这里返回的不是数据，而是API
        }
        function accountinit(address_data){
            var result=address_data;//web3.eth.accounts;
            var accounts=new Array();
            for(i in result){
                var balance = web3.eth.getBalance(result[i]);
                var balanceInEther=web3.fromWei(balance, 'ether');
                var account=new Object();
                account.addressId=result[i];
                account.balance=balance.toNumber();
                account.balanceInEther=balanceInEther.toNumber();
                accounts.push(account);
            }
            $scope.accounts=accounts;
        }
        //实时刷新区块数据
        $interval(function () {
            var deferred = $q.defer();//声明承诺
            $http.post('http://127.0.0.1:8080/etf/getBlockCount')
                .success(function(data){
                    console.log("获取区块数据请求成功"+data.blockNumber);
                    var blockstart=Number(data.blockNumber);
                    datainit(blockstart);
                }).error(function(data){
                    console.log(data);
                    console.log("获取区块数据请求失败");
                });
        }, 1200000000000);
        //添加address
//        datainit(1);
        function  datainit(blockstart){
            var data=new Array();
            var transactions=new Array();
            var blockend=web3.eth.blockNumber;
            console.log("-----"+blockstart+"========"+blockend);
            for(var i=blockstart;i<blockend;i++){
                var blockinfo= web3.eth.getBlock(i);
                //console.log(i+"----"+blockinfo.miner);
                // accountinit(blockinfo.miner);
                var  block={
                    blockId:blockinfo.number,
                    address:blockinfo.miner,//地址信息
                    transactionArr:blockinfo.transactions.join()//交易地址
                };
                data.push(blockinfo.miner);
                if(block.transactionArr.length>0){
                    console.log("transactionArr="+block.transactionArr);
                    transactions.push(block);
                }
            }
            var address_data=unique(data);
            //console.log(address_data);
            if(blockend>blockstart){
                addAddressJSON(address_data,transactions,blockend);
            }
        }
        function replaceTrans(transactionArr){

        }
        //数组去重
        function unique(arr) {
            var result = [], hash = {};
            for (var i = 0, elem; (elem = arr[i]) != null; i++) {
                if (!hash[elem]) {
                    result.push(elem);
                    hash[elem] = true;
                }
            }
            return result;
        }
        //将查询到的地址存入JSON文件保存到本地
        function addAddressJSON(address_data,transactions,blockend){
            if(address_data.length>0){
                var transactionsstr="";
                var url="";
                if(transactions.length>0 && transactions.length>12){
                    var n=transactions.length/12;
                    var m=transactions.length%12;
                   // console.log(n+"==="+m);
                    var h=1;
                            for(var i=0;i<n;i++){
                                var transactionsstr01=JSON.stringify(transactions.slice(12*i,12*(i+1)));
                                var uri='http://127.0.0.1:8080/etf/addTransaction?transactionsstr='+transactionsstr01;
                                $http.post(uri).success(function(){
                                    console.log("transactionsstr保存成功===整除");
                                })
                            }
                    if(m>0){
                        var transactionsstr02=JSON.stringify(transactions.slice(12*n,transactions.length));
                        var uri='http://127.0.0.1:8080/etf/addTransaction?transactionsstr='+transactionsstr02;
                        $http.post(uri).success(function(){
                            console.log("transactionsstr保存成功==除余");
                        })
                    }
                }else if(transactions.length>0 && transactions.length<12){
                        transactionsstr=JSON.stringify(transactions);
                         var url='http://127.0.0.1:8080/etf/addAddress?address_data='+address_data+'&blockend='+blockend+'&transactionsstr='+transactionsstr;
                        $http.post(url).success(function(){
                            console.log("address保存成功");
                        }).error(function(data) {
                            console.log("address保存失败");
                        });
                    }
                        var  url='http://127.0.0.1:8080/etf/addAddress?address_data='+address_data+'&blockend='+blockend;
                        $http.post(url).success(function(){
                            console.log("address保存成功");
                        }).error(function(data) {
                            console.log("address保存失败");
                        });
            }
        }
    });
