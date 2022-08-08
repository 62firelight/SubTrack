/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
"use strict"

var addSubApi = 'api/subscriptions';
var updateSubApi = ({id}) => `api/subscriptions/${id}`;
var subApi = ({username}) => `api/subscriptions/${username}`;
var deleteSubApi = ({id}) => `api/subscriptions/${id}`;
var categoryApi = ({username}) => `api/categories/${username}`;
var sortApi = ({username}) => `api/sort/${username}`;
var filterByCategoryApi = ({category, username}) => `api/categories/${category}/${username}`;
var totalApi = ({username}) => `api/total/${username}`;

const app = Vue.createApp({

    data() {
        return {
            welcome: `Welcome. The current date is ${(new Date()).toLocaleDateString()}.`,
            categories: new Array(),
            subscriptions: new Array(),
            subscription: new Object()
        }
    },

    computed: {
        signedIn() {
            return this.customer != null;
        },
        ...Vuex.mapState({
                customer: 'customer'
        })
    },

    mounted() {
        if (this.signedIn) {
            this.getSubs();
            this.getCategories();
        }
    },

    methods: {

        addSub() {
            this.subscription.customer = this.customer;

            axios.post(addSubApi, this.subscription)
                    .then(response => {
                        window.location = 'home.html';
                    })
                    .catch(error => {
                        console.log(error);
                        alert('An error has occurred - check the console for details');
                    });
        },

        getSubs() {
            axios.get(subApi({'username': this.customer.username}))
                    .then(response => {
                        this.subscriptions = response.data;
                    })
                    .catch(error => {
                        console.log(error);
                        alert('An error has occurred - check the console for details');
                    });
        },

        deleteSub(subscription) {
            if (window.confirm("Are you sure you want to delete " + subscription.name + "?")) {
                axios.delete(deleteSubApi({'id': subscription.subscriptionId}))
                        .then(response => {
                            this.getSubs();
                            this.getCategories();
                            // TODO: refresh total
                        })
                        .catch(error => {
                            console.log(error);
                            alert('An error has occurred - check the console for details');
                        });
            }

        },

        updateSub(subscription) {

        },

        renewSub(subscription) {
            alert('renew sub');
        },

        getSubStatus(subscription) {
            // calculate number of days remaining
            var numberOfDays = this.daysToToday(subscription.dueDate);

            // decide whether to add an 's' or not
            var plurality = numberOfDays > 1 ? 's' : '';

            // declare and initialize status variables
            var status = "error";
            var statusElement = document.getElementById("sub-" + subscription.subscriptionId);
            var renewElement = document.getElementById("renew-" + subscription.subscriptionId);

            // 3 days before reminder
            var reminderThreshold = 3;

            if (numberOfDays > 0) {
                status = "(in " + numberOfDays + " day" + plurality + ")";

                // check for null to avoid errors
                if (renewElement != null) {
                    // hide the Renew button for non-expired subs
                    renewElement.style.display = "none";
                }

                if (numberOfDays > reminderThreshold) {
                    //statusElement.style.color = "green";
                } else {
                    statusElement.style.color = "orange"; // dark orange
                }
            } else {
                status = "(expired)";
                
                if (statusElement != null) {
                    statusElement.style.color = "red";
                }
            }

            return status;
        },

        getConvertedDate(date) {
            return (new Date(date)).toLocaleDateString('en-NZ');
        },

        daysToToday(dateString) {
            var date = new Date(dateString);
            var today = new Date();

            // The number of milliseconds in one day
            const ONE_DAY = 1000 * 60 * 60 * 24;

            // Calculate the difference in milliseconds
            const differenceMs = date - today;

            // Convert to days
            const numberOfDays = Math.round(differenceMs / ONE_DAY)

            return numberOfDays;
        },

        getCategories() {
            axios.get(categoryApi({'username': this.customer.username}))
                    .then(response => {
                        this.categories = response.data;
                    })
                    .catch(error => {
                        console.log(error);
                        alert('An error has occurred - check the console for details');
                    });
        },

        filterByCat(selectedCat) {
            axios.get(filterByCategoryApi({'category': selectedCat, 'username': this.customer.username}))
                    .then(response => {
                        this.subscriptions = response.data;
                    })
                    .catch(error => {
                        console.log(error);
                        alert('An error has occurred - check the console for details');
                    });
        },

        sort() {

        }

    }

});

import { dataStore } from './data-store.js';
app.use(dataStore);

import { NavigationMenu } from './navigation.js';
app.component('navigation', NavigationMenu);

app.mount("#content");


