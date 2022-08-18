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

import AccFormComp from './acc-form-comp.js';

const app = Vue.createApp({
    
    components: {
        AccFormComp
    },

    data() {
        return {
            customer: new Object(),
            updateMessage: 'Successfully updated account.'
        }
    },

    computed: Vuex.mapState({
        customerToUpdate: 'customer'
    }),

    mounted() {
        if (this.customerToUpdate !== undefined) {
            // make a deep copy of the customer in session storage
            this.customer = JSON.parse(JSON.stringify(this.customerToUpdate));
        }
    },

    methods: {

        registerCustomer(customer) {
            axios.post(registerApi, customer)
                    .then(response => {
                        this.signIn();
                        window.location = 'home.html';
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

        updateCustomer(username, customer) {
            axios.put(updateAccApi({'username': username}), customer)
                    .then(response => {                      
                        // update customer stored in session storage
                        dataStore.commit('signIn', customer);
                        alert(this.updateMessage);
                    })
                    .catch(error => {
                        console.log(error);
                        alert('Failed to update account.\n\n' + error.response.data);
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
            }
        },
        
        submitAccount(submission) {
            console.log(submission);
            
            if (submission.deleting == true) {
                this.openDeleteDialog(submission.customer);
            } else if (submission.updating == false) {
                this.customer = submission.customer;
                this.registerCustomer(submission.customer);
            } else {
                this.updateCustomer(this.customerToUpdate.username, submission.customer);
            }
        },

    }

});

import { dataStore } from './data-store.js';
app.use(dataStore);

import { NavigationMenu } from './navigation.js';
app.component('navigation', NavigationMenu);

app.mount("#content");
