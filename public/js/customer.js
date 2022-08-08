/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
"use strict"

var registerApi = '/api/register';
var signInApi = ({username}) => `/api/customers/${username}`;
var updateAccApi = ({username}) => `/api/customers/${username}`;
var deleteAccApi = ({username}) => `/api/customers/${username}`;

const app = Vue.createApp({
   
    data() {
        return {
            
        }
    },
    
    mounted() {
//        alert('loaded');
    },
    
    methods: {
        
        registerCustomer(customer) {
            alert('customer registered');
        },
        
        signIn() {
            
        },
        
        checkSignIn() {
            
        },
        
        signOut() {
            
        },
        
        deleteCustomer(customer) {
            
        },
        
        updateCustomer(customer) {
            
        }
        
    }
    
});

import { NavigationMenu } from './navigation.js';
app.component('navigation', NavigationMenu);

app.mount("#content");
