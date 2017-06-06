angular.module('ethExplorer')
    .controller('accountmanagementCtrl',function($rootScope, $scope, $location,$http,$q,$interval){
    	$scope.toggleMenu=function(index) {
            $(".closediv").removeClass("opendiv");
            $(".div"+index).addClass("opendiv").slideToggle("slow");
        };
    	getAllAccount().then(function(result){
            $scope.accounts=result;
        });
        function getAllAccount(){
            var deferred = $q.defer();//声明承诺
            $http.post('http://127.0.0.1:8080/account/getAllAccount')
                .success(function(data){
                    deferred.resolve(data.allAddress);//请求成功
                }).error(function(data){
                    console.log(data);
                });
            return deferred.promise;   // 返回承诺，这里返回的不是数据，而是API
        }
        
        $scope.init=function(){
        	$scope.unlockAccount= function(i){
        		var address = $("#address").val();
        		var passwordId = "#password"+i;
        		var password = $(passwordId).val();
                $http.post('http://127.0.0.1:8080/account/unlockAccount?address='+address+'&password='+password)
                    .success(function(data){
                    	if(data.result){
                    		$("#unlockAccountResult"+i).html("解锁成功");
                    	}else{
                    		$("#unlockAccountResult"+i).html("解锁失败");
                    	}
                    }).error(function(data){
                        console.log(data);
                    });
        	};
        	
        	$scope.newAccount= function(){
        		var phone = $("#phone").val();
        		var address = $("#address").val();
        		var school = $("#school").val();
        		var userName = $("#userName").val();
        		var accountName = $("#accountName").val();
        		var password = $("#addAccountPassword").val();
                $http.post('http://127.0.0.1:8080/account/newAccount?password='+password+'&accountName='+accountName+
                		'&userName='+userName+'&school='+school+'&address='+address+'&phone='+phone)
                    .success(function(data){
                    	if(data.result!=null && data.result.length==42){
                    		$("#newAccountResult").html("添加账户成功");
                    		getAllAccount().then(function(result){
                                $scope.accounts=result;
                            });
                    	}else{
                    		$("#newAccountResult").html("添加账户失败");
                    	}
                    }).error(function(data){
                        console.log(data);
                    });
        	};
        	
        	$scope.sendTransaction= function(){
        		var address1 = $("#address1").val();
        		var address2 = $("#address2").val();
        		var value = $("#value").val();
        		var password = $("#addAccountPassword").val();
        		$("#sendTransactionResult").html();
                $http.post('http://127.0.0.1:8080/account/sendTransaction?address1='+address1+'&address2='+address2+'&value='+value+'&password='+password)
                    .success(function(data){
                    	if(data.result!=null && data.result.length==66){
                    		$("#sendTransactionResult").html("添加账户成功");
                    	}else{
                    		$("#sendTransactionResult").html("添加账户失败");
                    	}
                    }).error(function(data){
                        console.log(data);
                    });
        	};
        };
        $scope.init();
    });
