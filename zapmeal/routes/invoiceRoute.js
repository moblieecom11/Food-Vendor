const express = require('express');
const bodyParser = require('body-parser');
const cors = require('./cors');


const Invoice = require('../models/invoice');
const User = require('../models/users');
var authenticate = require('../authenticate');

const invoiceRouter = express.Router();
invoiceRouter.use(bodyParser.json());


invoiceRouter.options('/',cors.corsWithOptions, (req, res) => { res.sendStatus(200); })

invoiceRouter.get('/',cors.corsWithOptions,authenticate,(req,res,next) => {
	console.log("user infor", req.user)
     if(req.user.isFoodVendor) {
        Invoice.find({vendorId: req.user._id})
	        .populate('vendorId')
	        .exec((err, items)=>{
			if (err) return next(err);
			  
			  res.statusCode = 200;
			  res.setHeader('Content-Type', 'application/json');
			  res.json(items); 
          })	
	}
   	else if(!req.user.isFoodVendor) {
        Invoice.find({userId: req.user._id, invoiceStatus:"Issue"})
	        .populate('userId')
	        .exec((err, items)=>{
			if (err) return next(err);
				
				res.statusCode = 200;
				res.setHeader('Content-Type', 'application/json');
				res.json(items); 
          
		   }); 
			
	}
	else {
		     res.satusCode= 403;
		     res.setHeader('Content-Type', 'application/json');
		     res.send("your not  authorise"); 
          }
})


invoiceRouter.get('/:invoiceId',cors.corsWithOptions,authenticate,(req,res,next) => {

    Invoice.findById(req.params.invoiceId)
	        .populate('vendorId')
			.populate('userId')
			.populate('orderId')
			.populate('foodId')
	        .exec((err, items)=>{
			if (err) return next(err);
			  
			  res.statusCode = 200;
			  res.setHeader('Content-Type', 'application/json');
			  res.json(items); 
          })
})


invoiceRouter.get('/search',cors.corsWithOptions,authenticate,(req,res,next) => {
    const { search_field, search_value } = req.query;

    const queryObj = {};

 if (search_field !== null && search_value !== null) {
      queryObj[search_field] = search_value;
      console.log('::queryObj inside IF:::', queryObj);

   }

 console.log('::queryObj out Side:::', queryObj);

 Invoice.find(queryObj)
       .exec((err, items)=>{
        if (err) return next(err);
     
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(items); 
           
     })
})


/*invoiceRouter.post('/',cors.corsWithOptions,authenticate,(req, res, next) => {
    Invoice.findOne({vendorId: req.user.vendorId}&&{foodName:req.body.foodName}&&{foodType:req.body.foodType},(err, food)=>{
		    if(err) return next(err);
		
		  if(!food){
			    req.body.vendoId = req.user._id;
			    Invoice.create(req.body)
			   .then ((items)=>{
					   items.save()
					   .then((resp)=>{
					    console.log('new food has been posted');
					   res.satusCode= 200;
					   res.setHeader('Content-Type', 'application/json');
					  res.json(resp)		
				  })
			   }).catch((err)=>{
				  return next(err);
				  });
		}
		else{
			 res.satusCode= 403;
		     res.setHeader('Content-Type', 'application/json');
		     res.send("duplicate food type");
			}
	      
	   });
	})*/
	 
invoiceRouter.put('/:invoiceId',cors.corsWithOptions, authenticate,(req, res, next) => {
    
    if (req.user.iFoodVendor){
        Invoice.findByIdAndUpdate(req.params.foodId,
        {$set:req.body
     }, { new: true })
     .then((response) => {
       res.statusCode = 200;
       res.setHeader('Content-Type', 'application/json');
       res.json(response);
    }, (err) => next(err))
     .catch((err) => next(err));
}
})

invoiceRouter.delete('/:invoiceId',cors.corsWithOptions, authenticate, (req, res, next) => {

	Invoice.findOne({foodId: req.params.foodId}, (err, item))
         if (err) return next(err);

		  console.log(item);
		  var index = item.indexOf(req.params.foodId);
		  if (index>=0){
			  item.splice(index,1);
			  item.save()
			  .then((item)=>{
				  item.findById(item._id)
				  .populate('user')
	                 .populate('zone')
                	.populate('customer')
	              .populate('delivery.user')
				  .then((item)=>{
					  console.og('item deleted', item);
					  res.statusCode = 200;
                res.setHeader('Content-Type', 'application/json');
                res.json(item);  
				  })
			  })
               .catch((err)=>{
				   return next(err);
			   })                
		  }
		  else{
			  res.statusCode= 404;
			  res.detHeader('Content-Type', 'text/plain');
			  res.end('item' + req.params._id + 'not available');
		  }                          
});

module.exports= invoiceRouter;


