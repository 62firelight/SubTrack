export default {
    data() {
        return {
            dueDateText: 'The date that the subscription expires on.'
        }
    },

    props: {
        subscription: Object,
        subscriptions: Array,
        categories: Array,
        updating: Boolean
    },

    emits: [
        'submission'
    ],

    methods: {
        submitSub() {
            this.$emit('submission', {
                subscription: this.subscription,
                updating: this.updating
            });
        }
    },

    template: `    
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">    
<form @submit.prevent="submitSub()" class="login-form">
    <h2> {{ updating == true ? 'Update' : 'Add' }} Subscription </h2>
        
    <label for="name"><strong>Name</strong></label>
        
    <input type="text" v-model="subscription.name" list="nameList" required>
    <datalist id="nameList">
        <option v-for="subscription in subscriptions">{{subscription.name}}</option>
    </datalist>
    <br>

    <label for="category"><strong>Category</strong></label>
    <input type="text" v-model="subscription.category" list="categoryList" required>
    <datalist id="categoryList">
        <option v-for="category in categories">{{category}}</option>
    </datalist>
    <br>

    <label for="subscriptionPrice"><strong>Price</strong> </label>
    <input type="number" step=".01" v-model="subscription.subscriptionPrice" list="priceList" required>
    <datalist id="priceList">
        <option v-for="subscription in subscriptions">{{subscription.subscriptionPrice}}</option>
    </datalist>
    <br>

    <label for="description"><strong>Description</strong> <em>(optional)</em> </label>
    <input type="text" v-model="subscription.description" list="descriptionList">
    <datalist id="descriptionList">
        <option v-for="subscription in subscriptions">{{subscription.description}}</option>
    </datalist>
    <br>

    <label for="companyName"><strong>Company</strong> <em>(optional)</em></label> 
    <input type="text" v-model="subscription.companyName" list="companyList">
    <datalist id="companyList">
        <option v-for="subscription in subscriptions">{{subscription.companyName}}</option>
    </datalist>
    <br>

    <label for="dueDate"><strong>Due Date</strong>
    <i class="fa fa-question-circle reminder-help" :title="dueDateText"></i> </label>
    <input type="date" v-model="subscription.dueDate" name="trip-start"
           min="2020-01-01" max="2030-12-31" list="dateList" required>
    <datalist id="dateList">
        <option v-for="subscription in subscriptions">{{subscription.dueDate}}</option>
    </datalist>

    <br><br>
    <button class="raise" type="submit">{{ updating == true ? 'Update' : 'Add' }} Subscription</button>
</form>
  `
}