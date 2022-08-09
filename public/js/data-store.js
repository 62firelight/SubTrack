/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
"use strict"

export const dataStore = Vuex.createStore({
    
    state() {
        // signed in customer
        customer: null;
        
        // subscription to update
        subToUpdate: null;
        
        // basic access authentication token
        authToken: null;
    },
    
    mutations: {
        
        // user signs in
        signIn(state, customer) {
            state.customer = customer;
        },
        
        // user updates a subscription
        updateSub(state, subscription) {
            state.subToUpdate = subscription;
        },
        
        // store basic access token 
        authToken(state, token) {
            state.authToken = token;
        }
    },
    
    plugins: [window.createPersistedState({storage: window.sessionStorage})] 
});

