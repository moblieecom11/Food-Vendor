const mongoose = require('mongoose');
const Schema = mongoose.Schema;

var PaymentSchema = new Schema({
		
	invoiceId: {type:  mongoose.Schema.Types.ObjectId,  ref: 'Invoice'},
	
	invoiceNumber: {type: String,  default: ''},
	
	paymentDate: {type: Date, default: ''},
	
    paymentAmount:   {type: Number, default: '' },
	
	paymentReference:   {type: String, default: '' }
	
});

var Payment = mongoose.model('Payment', PaymentSchema);

module.exports = Payment;

