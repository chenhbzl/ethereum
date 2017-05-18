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
            $http.post('http://127.0.0.1:8080/etf/getAllAccount')
                .success(function(data){
                    deferred.resolve(data.allAddress);//请求成功
                }).error(function(data){
                    console.log(data);
                });
            return deferred.promise;   // 返回承诺，这里返回的不是数据，而是API
        }
        
        $scope.init=function(){
        	$scope.unlockAccount= function(){
        		var address = $("#address").val();
        		var password = $("#password").val();
                $http.post('http://127.0.0.1:8080/etf/unlockAccount?address='+address+'&password='+password)
                    .success(function(data){
                    	if(data.result){
                    		$("#unlockAccountResult").html("解锁成功");
                    	}else{
                    		$("#unlockAccountResult").html("解锁失败");
                    	}
                    }).error(function(data){
                        console.log(data);
                    });
        	};
        	
        	$scope.newAccount= function(){
        		var newPassword = $("#newPassword").val();
                $http.post('http://127.0.0.1:8080/etf/newAccount?newPassword='+newPassword)
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
        };
        $scope.init();
    });
