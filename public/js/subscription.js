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
var totalApi =  ({username}) => `api/total/${username}`;

const app = Vue.createApp({
   
    data() {
        return {
            
        }
    },
    
    mounted() {
        
    },
    
    methods: {
        
        addSub(subscription) {
            
        },
        
        getSubs(username) {
            
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

app.mount("#content");

