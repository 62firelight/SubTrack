export default {
    data() {
        return {
            reminderText: 'When this amount of days remains before a subscription expires, a reminder email will be sent out to your email address.'
        }
    },    
        
    props: {
        customer: Object,
        updating: Boolean
    },

    emits: [
        'submission'
    ],

    methods: {
        submitAccount() {
            this.$emit('submission', {
                customer: this.customer,
                updating: this.updating,
                deleting: false
            });
        },
        
        submitDeletion() {
            this.$emit('submission', {
                customer: this.customer,
                updating: this.updating,
                deleting: true
            });
        }
    },

    template: `
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">    
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

    <label for=“reminderThreshold”><strong>Days For Reminder</strong> 
    <i class="fa fa-question-circle reminder-help" :title="reminderText"></i> </label>
    <input type="number" min="0" max="30" 
    style="text-align: center; margin-bottom: 0.25rem;" 
    placeholder="e.g. 3" v-model="customer.reminderThreshold" required> day(s)
    <br><br>

    <button type="submit" class="raise">{{ updating == true ? 'Update' : 'Create' }} Account</button>
    <br>
    <button class="del" @click.prevent="submitDeletion()">Delete Account</button>
</form>
  `
}