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

import { dataStore } from './data-store.js';

const app = Vue.createApp({

    data() {
        return {
            customer: new Object()
        }
    },

    mounted() {
//        alert('loaded');
    },

    methods: {

        registerCustomer(customer) {
            axios.post(registerApi, customer)
                    .then(response => {
                        dataStore.commit('signIn', this.customer);
                        window.location = 'home.html';
                        alert('Customer registered');
                    })
                    .catch(error => {
                        console.log(error);
                        alert('An error has occurred - check the console for details');
                    });
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
