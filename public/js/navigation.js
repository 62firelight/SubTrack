/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
"use strict"

export const NavigationMenu = {
    computed: {
        
    },
    
    template : `
<div class="dropdown" id="menu">
    <button class="dropbtn">Menu</button>
    <div class="dropdown-content">
        <a href="home.html">Home</a>
        
        <a href="signin.html">Sign In</a>
    </div>
</div>
`,
    
    methods: {
        
    }
};

//<a href="updateacc.html" v-if="signedIn">My Account</a>
//        <a href="home.html" v-if="signedIn" @click.prevent="signOut()//">Sign Out</a>
