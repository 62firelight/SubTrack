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
<ul>
    <div class="nav-buttons">
        <li><a href="home.html" class="logo">SubTrek</a></li>

        <li><a href="home.html">{{signedIn ? 'My Subscriptions' : 'Home'}}</a></li>
        <li><a href="account.html" v-if="signedIn">My Account</a></li>
        <li><a href="home.html" @click.prevent="openSignOutDialog()" v-if="signedIn">Sign Out</a></li>
        <li><a href="register.html" v-if="!signedIn">Register</a></li>
        <li><a href="signin.html" v-if="!signedIn">Sign In</a></li>
    </div>
</ul>
`,

//    template: `
//<h1>SubTrek</h1>
//<div class="dropdown" id="menu">
//    <button class="dropbtn">Menu</button>
//    <div class="dropdown-content">
//        <a href="home.html">Home</a>
//        <a href="account.html" v-if="signedIn">My Account</a>
//        <a href="home.html" v-if="signedIn" @click="signOut()">Sign Out</a>
//        <a href="signin.html" v-if="!signedIn">Sign In</a>
//    </div>
//</div>
//<hr>
//`,

    methods: {
        signOut() {
            sessionStorage.clear();
            window.location = ".";
        },

        openSignOutDialog() {
            const wantToSignOut = window.confirm("Are you sure you want to sign out?");

            if (wantToSignOut) {
                this.signOut();
            }
        }
    }
};


