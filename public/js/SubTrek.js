/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

"use strict";

// create a new module, and load the other pluggable modules
var module = angular.module('SubTrek',['ngResource','ngStorage']);

module.factory('registerAPI', function($resource){
    return $resource('api/register');
});

module.factory('signInAPI', function ($resource){
    return $resource ('api/customers/:username');
});

module.controller('CustomerController', function(registerAPI,$window,signInAPI, $sessionStorage) {
    this.registerCustomer = function (customer) {
        registerAPI.save(null, customer,
        
        // success callback
        function () {
            $window.location = 'signin.html';
        },

        // error callback
        function (error) {
            console.log(error);
            }
        );
        console.log(customer);
    };   
    this.signInMessage = "Please sign in to continue.";
    // alias 'this' so that we can access it inside callback functions
    let ctrl = this;

    this.signIn = function (username, password) {

    // get customer from web service
    signInAPI.get({'username': username},
        // success callback
        function (customer) {
            // also store the retrieved customer
            $sessionStorage.customer = customer;

            // redirect to home
            $window.location = 'home.html';
        },
        // fail callback
        function () {
            ctrl.signInMessage = 'Sign in failed. Please try again.';
        }
        );

    };
    
    this.checkSignIn = function () {
    // has the customer been added to the session?
        if ($sessionStorage.customer) {
            this.signedIn = true;
            this.welcome = "Welcome " + $sessionStorage.customer.firstName;
        }else {
            this.signedIn = false;
        }
    };  
    
    this.signOut = function(){
        $sessionStorage.$reset();
        $window.location = 'home.html';
    }
});


module.factory('addSubscriptionAPI', function($resource){
    return $resource('api/subscriptions');
});

module.factory('subscriptionAPI', function($resource){
   return $resource('api/subscriptions/:username'); 
});

module.controller('SubscriptionController', function(addSubscriptionAPI, subscriptionAPI, $window){
    let ctrl = this;
    
    alert('in subscription controller');
    
    this.addSubscription = function(username, subscription){
        addSubscriptionAPI.save({'username': username}, subscription,
        
        function(){
            $window.location = 'home.html';
        },
        
        function(error){
            console.log(error);
        }
        );
        console.log(subscription + " for " + username);
    };
    
    this.getSubscriptions = function(username){
       this.subscriptions = subscriptionAPI.query({'username': username});
    };
});