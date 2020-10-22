const mongoose = require('mongoose');
const Schema = mongoose.Schema;

var DeliverySchema = new Schema({

    orderId: {  type:  mongoose.Schema.Types.ObjectId,  ref: 'Order'},
	
	paymentId: {type:  mongoose.Schema.Types.ObjectId,  ref: 'Invoice'},
	
	deliveryTrackingNumber: {type: String, default: ''},
	
    deliveryTime:   {type: Date, default: '' },
	
	pickUpTime:   {type: Date, default: '' },
	
	pickUpLocation:{ type:  mongoose.Schema.Types.ObjectId,  ref: 'User'
			},

	
	deliveryLocation: {type:  mongoose.Schema.Types.ObjectId,  ref: 'User'
			},
			
	deliveredTo: {
	                    firstName: {type: String, default: ''},
						
	                    lastName: {type: String, default: ''},
						
						phoneNumber: {type: String, default: ''},
						
						address: {type: String, default: ''},
						
						userId: {type: String, default: ''}				
			},		    


  deliveryStatus:   {type: String, required:true, enum:['Accepted', 'On Transit', 'Delivered', 'New Delivery'], default: 'New Delivery'}
	
	
});

var Delivery = mongoose.model('Delivery', DeliverySchema);

module.exports = Delivery;