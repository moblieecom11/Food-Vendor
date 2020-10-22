const mongoose = require('mongoose');
const Schema = mongoose.Schema;

var InvoiceSchema = new Schema({

    invoiceNumber: {type: String,  default: ''},
	
	vendorId: {type:  mongoose.Schema.Types.ObjectId,  ref: 'User'},
	
    foodId: {  type:  mongoose.Schema.Types.ObjectId,  ref: 'Food'},
	
	userId: {type:  mongoose.Schema.Types.ObjectId,  ref: 'User'},
	
	orderId: { type: String,  default: ''},
	
	invoiceIssueDate: {type: Date, default: ''},
	
    invoiceDetails:   {type: String, default: '' },
	
    invoiceStatus:   {type: String, required:true, enum:['Issue', 'Paid'], default: 'Issue'}
	
});

var Invoice = mongoose.model('Invoice', InvoiceSchema);

module.exports = Invoice;