/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
export const NumberFormatter = {
    
    methods: {
        
        formatCurrency(value) {
            return Intl.NumberFormat('en-NZ', {style: "currency", currency: 'NZD'}).format(value);
        },
        
        formatNumber(value, decimalPlaces) {
            // cast to number in case value is a string
            let number = Number(value);
            
            return number.toFixed(decimalPlaces);
        }
    }
    
}

