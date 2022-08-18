export default {
    props: {
        customer: Object,
        categories: Array,
        updating: Boolean
    },

    emits: [
        'submission'
    ],

    methods: {
        submitAccount() {
            this.$emit('submission', {
                customer: this.customer,
                updating: this.updating
            });
        }
    },

    template: `    
<form @submit.prevent="submitAccount()" class="login-form">
    <h2>{{ updating == true ? 'Update' : 'Create' }} Account</h2>
        
    <input type="hidden" v-if="updating" v-model="customer.customerId" required>

    <label for=“username”><strong>Username</strong></label>
    <input type="text" maxlength="50" v-model="customer.username" required>
    <br>

    <label for=“password” v-if="!updating"><strong>Password</strong></label>
    <input type="password" maxlength="50" v-if="!updating" v-model="customer.password" required>
    <br v-if="!updating">

    <label for=“emailAddress”><strong>Email</strong></label>
    <input type="email" maxlength="50" v-model="customer.emailAddress" required>
    <br>

    <label for=“reminderThreshold”><strong>Reminder Threshold</strong></label>
    <input type="number" max="30" v-model="customer.reminderThreshold" required>
    <br><br>

    <button type="submit" class="raise">{{ updating == true ? 'Update' : 'Create' }} Account</button>
</form>
  `
}