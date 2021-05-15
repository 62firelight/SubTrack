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

module.factory('updateAccAPI', function ($resource){
    return $resource ('api/customers/:id');
});

module.controller('CustomerController', function(registerAPI,$window,signInAPI, $sessionStorage,updateAccAPI) {
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
            this.welcome = "Welcome " + $sessionStorage.customer.firstName + ". The current date is " + (new Date()).toLocaleDateString() + ".";
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

module.factory('updateSubAPI', function($resource){
    return $resource('api/subscriptions/:id');
});

module.factory('subscriptionAPI', function($resource){
   return $resource('api/subscriptions/:username'); 
});

module.factory('deleteAPI', function($resource) {
    return $resource('api/subscriptions/:id');
});

module.factory('categoryAPI', function($resource){
   return $resource('api/categories/:username'); 
});

module.factory('filterAPI', function($resource){
    return $resource('api/categories/:category');
});

module.factory('totalAPI', function($resource){
   return $resource('api/total/:username'); 
});

module.controller('SubscriptionController', function($sessionStorage, addSubscriptionAPI, 
                  subscriptionAPI, deleteAPI, $window, categoryAPI, filterAPI, totalAPI, updateSubAPI){
    let ctrl = this;
    
    console.log("Subscription controller initialized");
    
    if ($sessionStorage.customer) {
        this.subscriptions = subscriptionAPI.query({'username': $sessionStorage.customer.username});
    }
    
    this.addSubscription = function(subscription){
        subscription.customer = $sessionStorage.customer;
        
        addSubscriptionAPI.save(subscription,
        
        function(){
            $window.location = 'home.html';
        },
        
        function(error){
            console.log(error);
        }
        );
        console.log(subscription + " for " + subscription.customer);
    };
    
//    this.getSubscriptions = function(username){
//       this.subscriptions = subscriptionAPI.query({'username': $sessionStorage.customer.username});
//    };
    
    this.deleteSubscription = function(subscription) {
        
        // ask the user before deleting
        if ($window.confirm("Are you sure you want to delete " + subscription.name + "?")) {
            deleteAPI.delete({'id' : subscription.subscriptionId}, function() {
                // get subscriptions again so we don't have to refresh
                ctrl.subscriptions = subscriptionAPI.query({'username': $sessionStorage.customer.username});
            });
        }
            
    };
    
    this.updateSubscription = function(subscription) {
        
    }
    
    this.getConvertedDate = function(date) {   
        //   console.log((new Date(currentValue)).toLocaleDateString());
        return (new Date(date)).toLocaleDateString('en-NZ');
    };
    
    this.daysToToday = function(dateString) {
        
        var date = new Date(dateString);
        var today = new Date();

        // The number of milliseconds in one day
        const ONE_DAY = 1000 * 60 * 60 * 24;
        
        //console.log(ONE_DAY);

        // Calculate the difference in milliseconds
        const differenceMs = Math.abs(date - today);

        //console.log(differenceMs);

        // Convert back to days and return
        return Math.round(differenceMs / ONE_DAY);

    };
    if($sessionStorage.customer){
        this.categories = categoryAPI.query({'username' : $sessionStorage.customer.username});
        this.total = totalAPI.query({'username': $sessionStorage.customer.username});
    }else{
        $sessionStorage.$reset();
    }
    this.filterCat = function(selectedCat){
      this.subscriptions = filterAPI.query({'category': selectedCat});  
    };
});

