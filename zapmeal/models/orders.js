const mongoose = require('mongoose');
const Schema = mongoose.Schema;

var OrderSchema = new Schema({
	vendorId: { type:  mongoose.Schema.Types.ObjectId,  ref: 'User'},
	
    foodId: {   type:  mongoose.Schema.Types.ObjectId,   ref: 'Food' },
	
	userId: {type:  mongoose.Schema.Types.ObjectId,  ref: 'User'},
	
	orderFoodQuantity: {type: String, default: ''},
	
	orderFoodPrice: {type: String, default: ''},
	
    dateOrderPlaced:   {type: Date, default: '' },
	
    orderStatus:   {type: String, required:true, enum:['Served', 'Delivered', 'Cancel', 'Available'], default: 'Available'},
	
	isOrderComplete:   {type: Boolean, default: 'false' }
});

var Order = mongoose.model('Order', OrderSchema);

module.exports = Order;



