angular.module('ethExplorer')
    .controller('blindauctionCtrl', function ($rootScope, $scope, $location, $routeParams,$http) {
        $scope.init=function(){
        	  //部署合约
        	  $scope.deploy = function(){
        		  var biddingEnd = $("#biddingEnd").val();
        		  var revealEnd = $("#revealEnd").val();
        		  var beneficiary = $("#beneficiary").val();
        		  $http.post('http://127.0.0.1:8080/blind/deploy?biddingEnd='+biddingEnd+'&revealEnd='+revealEnd+'&beneficiary='+beneficiary).success(function(data){
                  	if (data !== undefined){
                  		$("#deployResult").html('部署成功');
                  		$("#contractAddress").html(data.result);
                  	}
                  });
        	  };
        	  
        	  $scope.getBalance = function(){
        		  var contractAddress = $("#contractAddress").html();
        		  $http.post('http://127.0.0.1:8080/blind/getBalance?contractAddress='+contractAddress).success(function(data){
        			  if (data !== undefined){
        				  $("#balanceResult").html(data.result);
                    	}
                  });
        	  };
        	  
        	  //出价
        	  $scope.bids = function(){
        		  var bidderAddress = $("#bidderAddress").val();
        		  var bid = $("#bid").val();
        		  var fake = $("#fake").val();
        		  var unencrypted = $("#unencrypted").val();
        		  var contractAddress = $("#contractAddress").html();
        		  $http.post('http://127.0.0.1:8080/blind/bids?bidderAddress='+bidderAddress+'&bid='+bid+'&fake='+fake+'&unencrypted='+unencrypted+'&contractAddress='+contractAddress).success(function(data){
        			  if (data !== undefined){
                    		$("#bidsResult").html('出价成功');
                    	}
                  });
        	  };
        	  
        	  $scope.reveal = function(){
        		  var bids = $("#bids").val();
        		  var fakes = $("#fakes").val();
        		  var unencrypteds = $("#unencrypteds").val();
        		  var contractAddress = $("#contractAddress").html();
        		  $http.post('http://127.0.0.1:8080/blind/reveal?bids='+bids+'&fakes='+fakes+'&fake='+fake+'&unencrypteds='+unencrypteds+'&contractAddress='+contractAddress).success(function(data){
        			  if (data !== undefined){
                    		$("#revealResult").html('整理除了最高价值外的所有正常出价成功');
                    	}
                  });
        	  };
        	  
        	  $scope.withdraw = function(){
        		  var contractAddress = $("#contractAddress").html();
        		  $http.post('http://127.0.0.1:8080/blind/withdraw?contractAddress='+contractAddress).success(function(data){
        			  if (data !== undefined){
                    		$("#withdrawResult").html('退款成功');
                    	}
                  });
        	  };
        	  
        	  $scope.auctionEnd = function(){
        		  var contractAddress = $("#contractAddress").html();
        		  $http.post('http://127.0.0.1:8080/blind/auctionEnd?contractAddress='+contractAddress).success(function(data){
        			  if (data !== undefined){
                    		$("#auctionResult").html('受益者收到成功');
                    	}
                  });
        	  };
        	  
        	  $scope.getBalance_ = function(){
        		  var contractAddress = $("#contractAddress").html();
        		  $http.post('http://127.0.0.1:8080/blind/getBalance?contractAddress='+contractAddress).success(function(data){
        			  if (data !== undefined){
        				  $("#balanceResult_").html(data.result);
                    	}
                  });
        	  };
        };
        $scope.init();
    });

