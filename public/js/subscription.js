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
var filterApi = ({category}, {username}) => `api/categories/${category}/${username}`;
var totalApi = ({username}) => `api/total/${username}`;

const app = Vue.createApp({

    data() {
        return {
            welcome: `Welcome. The current date is ${(new Date()).toLocaleDateString()}.`,
            subscriptions: new Array()
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
        }
    },

    methods: {

        addSub(subscription) {

        },

        getSubs() {
            axios.get(subApi({'username': this.customer.username}))
                    .then(response => {
                        this.subscriptions = response.data;
                    })
                    .catch(error => {
                        console.log(error);
                        alert('An error has occurred - check the console for details');
                    })
        },

        deleteSub(subscription) {

        },

        updateSub(subscription) {

        },

        renewSub(subscription) {

        },

        getSubStatus(subscription) {

        },

        getConvertedDate(date) {

        },

        daysToToday(dateString) {

        },

        filterCat(selectedCat) {

        },

        selectAll() {

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


