const mongoose = require('mongoose');
const Schema = mongoose.Schema;

var FoodSchema = new Schema({
	
	vendorId: { type: mongoose.Schema.Types.ObjectId,  ref:'User'},
	
	foodName: {type: String, default: ''},
	
	foodPrice: {type: String, default: ''},
	
	foodType: {type: String, default: ''},
	
    foodDescription:   {type: String, default: '' },
	
    foodPriceDescription:   {type: String, default: ''},
	
	otherFoodDetail:   {type: String, default: ''},
	
	foodImage:{type:String, default:''},
	
	foodStatus:{type:String, default:'', enum:[ 'Available', 'Not Available'], default: 'Available'},
	
	foodRating:{
		    userId:{type:String, default:''},
			Rating:{type:Number, default:''},
			comment:{type:String, default:''}
		}
	
});

var Food = mongoose.model('Food', FoodSchema);

module.exports = Food;