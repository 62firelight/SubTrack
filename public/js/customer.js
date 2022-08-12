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
            customer: new Object(),
            updateMessage: new String()
        }
    },

    computed: Vuex.mapState({
        customerToUpdate: 'customer'
    }),

    mounted() {

    },

    methods: {

        registerCustomer(customer) {
            axios.post(registerApi, customer)
                    .then(response => {
                        dataStore.commit('signIn', this.customer);
                        window.location = 'home.html';
//                        alert('Customer registered');
                    })
                    .catch(error => {
                        console.log(error);
                        alert('Failed to register account.\n\n' + error.response.data);
                    });
        },

        signIn() {
            axios.get(signInApi({'username': this.customer.username}))
                    .then(response => {
                        this.customer = response.data;
                        dataStore.commit('signIn', this.customer);
                        window.location = 'home.html';
                    })
                    .catch(error => {
                        console.log(error);
                        alert('Wrong username and/or password.');
                    })
        },

        updateCustomer(customer) {
            axios.put(updateAccApi({'username': customer.username}), customer)
                    .then(response => {
                        // update customer stored in session storage
                        dataStore.commit('signIn', customer);
                    })
                    .catch(error => {
                        console.log(error);
                        alert('Failed to update account.');
                    })
        },

        deleteCustomer(customer) {
            axios.delete(deleteAccApi({'username': customer.username}))
                    .then(response => {
                        sessionStorage.clear();
                        window.location = 'home.html';
                    })
                    .catch(error => {
                        console.log(error);
                        alert('Failed to delete account.');
                    })
        },

        openDeleteDialog(customer) {
            const wantToDelete = window.confirm(`Are you sure you want to delete your account?`);

            if (wantToDelete) {
                this.deleteCustomer(customer);
            } else {
                // do nothing
            }
        }

    }

});

import { dataStore } from './data-store.js';
app.use(dataStore);

import { NavigationMenu } from './navigation.js';
app.component('navigation', NavigationMenu);

app.mount("#content");
