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
            currentDate: new Date(),
            welcome: new String(),
            categories: new Array(),
            subscriptions: new Array(),
            subscription: new Object(),
            reminderThreshold: 3,
            total: new Object(),

            minDate: `${new Date().getFullYear()}-01-01`,
            defaultDate: new Date().toISOString().slice(0, 10),
            maxDate: `${new Date().getFullYear()}-12-31`
        }
    },

    computed: {
        signedIn() {
            return this.customer != null;
        },
        ...Vuex.mapState({
                customer: 'customer',
                subToUpdate: 'subToUpdate'
        })
    },

    mounted() {
        if (this.signedIn) {
            // set current date
            this.welcome = `Welcome ${this.customer.username}. The current date is ${this.currentDate.toLocaleDateString()}.`;

            this.getSubs();
            this.getCategories();
            this.getTotal();

            // set a default date when adding subscription
            if (this.subscription.dueDate === undefined) {
                this.subscription.dueDate = this.defaultDate;
            }
        } else {
            this.welcome = 'Welcome. Hover over the Menu and click "Sign In" to get started.';
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

        updateSub(subscription) {
            subscription.issueDate = subscription.dueDate;
            
            axios.put(updateSubApi({'id': subscription.subscriptionId}), subscription)
                    .then(response => {
                        this.getSubs();
                        window.location = 'home.html';
                    })
                    .catch(error => {
                        console.log(error);
                        alert('An error has occurred - check the console for details');
                    });
        },

        deleteSub(subscription) {
            axios.delete(deleteSubApi({'id': subscription.subscriptionId}))
                    .then(response => {
                        this.getSubs();
                        this.getCategories();
                        this.getTotal();
                    })
                    .catch(error => {
                        console.log(error);
                        alert('An error has occurred - check the console for details');
                    });

        },

        redirectToUpdate(subscription) {
            dataStore.commit('updateSub', subscription);
            window.location = 'updatesub.html';
//            window.location = 'subscription.html';
        },

        openDeleteDialog(subscription) {
            const wantToDelete = window.confirm("Are you sure you want to delete " + subscription.name + "?");

            if (wantToDelete) {
                this.deleteSub(subscription);
            } else {
                // do nothing
            }
        },

        /** 
         * 
         * Ensures that the subscription will renew in a cyclic manner using
         * the issue date (AKA the original due date).
         * 
         * For example, a subscription that expires on the due date 15/8/2021
         * and then gets renewed 12 times will have a new due date of 15/8/2022.
         * 
         */
        renewSub(subscription) {
            let issueDate = new Date(subscription.issueDate);
            let daysElapsed = Math.abs(this.daysToToday(subscription.issueDate));
            
//            alert(`${daysElapsed} day(s) have passed since ${issueDate.toLocaleString()}.`);
            
            // find the right amount of days to add to the due date
            let i = 1;
            while (daysElapsed >= parseInt((365/12) * i)) {
                i++;
            }
            
            // ensure that we are not under-adding to the due date
            if (i > 1 && daysElapsed / parseInt((365/12) * (i - 1)) < 1){
                i--;  
            }
            
            let daysToAdd = parseInt((365/12) * i);
//            if (daysToAdd / 365 > 1 && this.daysToToday(subscription.issueDate) > 365) {
//                daysToAdd %= 365;
//            }
//            alert(`Adding ${daysToAdd} days...`);
            
            // calculate new due date by adding days to the old due date
            let newDueDate = new Date(subscription.dueDate);
            newDueDate.setDate(newDueDate.getDate() + daysToAdd)
            let newDueDateString = newDueDate.toISOString();
            subscription.dueDate = newDueDateString;
            
            this.updateSub(subscription);
        },

        getSubStatus(subscription) {
            // calculate number of days remaining
            let numberOfDays = this.daysToToday(subscription.dueDate);

            // decide whether to add an 's' or not
            let plurality = numberOfDays > 1 ? 's' : '';

            // initialize status string
            let status = "error";

            if (numberOfDays > 0) {
                status = "(in " + numberOfDays + " day" + plurality + ")";
            } else {
                status = "(expired)";
            }

            return status;
        },

        getDateColor(subscription) {
            let numberOfDays = this.daysToToday(subscription.dueDate);
            return ((numberOfDays <= this.reminderThreshold) ? (numberOfDays <= 0 ? 'red' : 'orange') : 'white');
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

        getTotal() {
            axios.get(totalApi({'username': this.customer.username}))
                    .then(response => {
                        this.total = response.data;
                    })
                    .catch(error => {
                        console.log(error);
                        alert('An error has occurred - check the console for details');
                    });
        },

        sort() {
            axios.get(sortApi({'username': this.customer.username}))
                    .then(response => {
                        this.subscriptions = response.data;
                    })
                    .catch(error => {
                        console.log(error);
                        alert('An error has occurred - check the console for details');
                    });
        }

    },

    mixins: [NumberFormatter]
});

import { dataStore } from './data-store.js';
app.use(dataStore);

import { NavigationMenu } from './navigation.js';
app.component('navigation', NavigationMenu);

import { NumberFormatter } from './number-formatter.js';

app.mount("#content");