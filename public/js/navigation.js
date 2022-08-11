/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
"use strict"

import { dataStore } from './data-store.js';

export const NavigationMenu = {
    
    computed: {
        signedIn() {
            return this.customer != null;
        },
        ...Vuex.mapState({
                customer: 'customer'
        })
    },

    template: `
<div class="dropdown" id="menu">
    <button class="dropbtn">Menu</button>
    <div class="dropdown-content">
        <a href="home.html">Home</a>
        <a href="account.html" v-if="signedIn">My Account</a>
        <a href="home.html" v-if="signedIn" @click="signOut()">Sign Out</a>
        <a href="signin.html" v-if="!signedIn">Sign In</a>
    </div>
</div>
`,

    methods: {
        signOut() {
            sessionStorage.clear();
            window.location = ".";
        }
    }
};


