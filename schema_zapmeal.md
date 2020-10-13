
var User :{
	
	firstname: { type: String,  default: ''},
	
    lastname: {   type: String,   default: '' },
	
	verified_email: {type: String, default: ''},
	
	address: {type: String, unique: true},
	
	phoneNumber: {type: String, unique: true},
	
    Is_food_vendor:   {type: Boolean, default: false },
	
    food_vendor_name:   {type: Boolean, default: false},
	
	verification_code:   {type: String, default: false},
	
	profile_picture:{type:String, default:''},
	
	latitude:{type:String, default:''},
	
     longtitude:{type:String, default:'' },
	
	Date:{type:Date, default:'' },
}

var Food :{
	
	vendor_id: { type: String,  default: ''},
	
    food_id: {   type: String,   default: '' },
	
	food_name: {type: String, default: ''},
	
	food_price: {type: String, default: ''},
	
	foodType: {type: String, default: ''},

	
    foodDescrption:   {type: String, default: '' },

	
    foodPriceDescription:   {type: String, default: ''},

	
	otherFoodDetail:   {type: String, default: ''},

	
	foodImage:{type:String, default:''},

	
	latitude:{type:String, default:''},
	
     longtitude:{type:String, default:'' },
	
}

var Order :{

    order_id: { type: String,  default: ''},
	
	vendor_id: { type: String,  default: ''},
	
    food_id: {   type: String,   default: '' },
	
	user_id: {type: String, default: ''},
	
	order_food_quantity: {type: String, default: ''},
	
	order_food_price: {type: String, default: ''},
	
    date_order_placed:   {type: String, default: '' },
	
    order_status:   {type: String, required:true, enum:['Served', 'Delivered', 'Cancel', 'Available'], default: 'Available'},
	
}

var Invoice :{

    invoice_number: {type: String, default: ''},
	
	vendor_id: { type: String,  default: ''},
	
    food_id: {   type: String,   default: '' },
	
	user_id: {type: String, default: ''},
	
	order_id: { type: String,  default: ''},
	
	invoice_issue_date: {type: String, default: ''},
	
    invoice_details:   {type: String, default: '' },
	
    invoice_status:   {type: String, required:true, enum:['Issue', 'Paid'], default: 'Issue'},
	
}

var Delivery :{

    delivery_id: { type: String,  default: ''},
	
	order_id: { type: String,  default: ''},
	
	invoice_number: {type: String, default: ''},
	
	delivery_tracking_number: {type: String, default: ''},
	
    delivery_date:   {type: Date, default: '' },
	
	pic_up_loacation: {
	                    delivery_latitude: {type: String, default: ''},
						
	                    delivery_longtitude: {type: String, default: ''},
						
						delivery_address: {type: String, default: ''},
			}

	
	delivery_loacation: {
	                    delivery_latitude: {type: String, default: ''},
						
	                    delivery_longtitude: {type: String, default: ''},
						
						delivery_address: {type: String, default: ''},
			}
}


var Payment :{
    
	payment_id: { type: String,  default: ''},
	
	
	invoice_number: {type: String, default: ''},
	
	
	payment_date: {type: date, default: ''},
	
    payment_amount:   {type: number, default: '' },
	
	payment_reference:   {type: number, default: '' },
	
	
}
