'use strict';

angular.module('ethExplorer', ['ngRoute','ui.bootstrap'])

.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/', {
                templateUrl: 'views/main.html',
                controller: 'mainCtrlInit'
            }).
            when('/block/:blockId', {
                templateUrl: 'views/blockInfos.html',
                controller: 'blockInfosCtrl'
            }).
            when('/transaction/:transactionId', {
                templateUrl: 'views/transactionInfos.html',
                controller: 'transactionInfosCtrl'
            }).
            when('/address/:addressId', {
                templateUrl: 'views/addressInfos.html',
                controller: 'addressInfosCtrl'
            }).
            when('/sign', {
                templateUrl: 'views/sign.html',
                controller: 'signCtrl'
            }).
            when('/conference', {
                templateUrl: 'views/conference.html',
                controller: 'conferenceCtrl'
            }).
            when('/smartsponsor', {
                templateUrl: 'views/smartsponsor.html',
                controller: 'smartsponsorCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }])
    .run(function($rootScope,$interval) {
        var Web3 = require('web3');
        var web3 = new Web3();
        web3.setProvider(new web3.providers.HttpProvider('http://192.168.9.44:8545'));
        $rootScope.web3=web3;
        function sleepFor( sleepDuration ){
            var now = new Date().getTime();
            while(new Date().getTime() < now + sleepDuration){ /* do nothing */ } 
        }
        var connected = false;
        if(!web3.isConnected()) {
            $('#connectwarning').modal({keyboard: false, backdrop: 'static'})
            $('#connectwarning').modal('show')
        }
    });
