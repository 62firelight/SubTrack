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
//    template: `
//<div class="dropdown" id="menu">
//    <button class="dropbtn">Menu</button>
//    <div class="dropdown-content">
//        <a href="home.html">Home</a>
//        <a href="account.html" v-if="signedIn">My Account</a>
//        <a href="home.html" v-if="signedIn" @click="signOut()">Sign Out</a>
//        <a href="signin.html" v-if="!signedIn">Sign In</a>
//    </div>
//</div>
//`,

        template: `
<nav class="navbar navbar-expand navbar-dark bg-dark" aria-label="Second navbar example">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">SubTrek</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample02" aria-controls="navbarsExample02" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExample02">
        <ul class="navbar-nav me-auto">
          <li class="nav-item">
            <a class="nav-link" aria-current="page" href="home.html">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" v-if="signedIn" href="account.html">My Account</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" v-if="signedIn" href="#">Sign Out</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" v-if="!signedIn" href="signin.html">Sign In</a>
          </li>
        </ul>
      </div>
    </div>
</nav>
`,

        methods: {
        signOut() {
        sessionStorage.clear();
                window.location = ".";
        }
        }
};


