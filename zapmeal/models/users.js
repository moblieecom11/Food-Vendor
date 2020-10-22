const mongoose = require('mongoose');
const Schema = mongoose.Schema

var UserSchema =new Schema({
    userId: { type: String,  required: true, unique:true},
    
	firstName: { type: String,  default: ''},
	
    lastName: {   type: String,   default: '' },
	
	verifiedEmail: {type: String, unique: true, required:true},
	
    isFoodVendor:   {type: Boolean, default: false },
	
    foodVendorName:   {type: String, default: ''},
	
	verificationCode:   {type: String, default: false},
	
	profilePicture:{type:String, default:''},	
   
	 contactAddress:{
              address: {type: String, required: true},
              phoneNumber: {type: String, unique: true},
              latitude:{type:String, default:''},
             longtitude:{type:String, default:'' },
   },
	
	Date:{type:Date, default:'' },
});

var User = mongoose.model('User', UserSchema);

module.exports = User;