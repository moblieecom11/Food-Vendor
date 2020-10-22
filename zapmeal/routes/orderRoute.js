const express = require('express');
const bodyParser = require('body-parser');
const cors = require('./cors');
var async = require('async');

const Order = require('../models/orders');
const User = require('../models/users');
const Invoice = require('../models/invoice');
var authenticate = require('../authenticate');

const orderRouter = express.Router();
orderRouter.use(bodyParser.json());


orderRouter.options('/',cors.corsWithOptions, (req, res) => { res.sendStatus(200); })

orderRouter.get('/',cors.corsWithOptions,authenticate,(req,res,next) => {
     if(req.user.isFoodVendor) {
        Order.find({vendorId: req.user._id, orderStatus: "Available", isOrderComplete: false})
            .populate('vendorId')
            .populate('foodId')
            .populate('userId')
	        .exec((err, items)=>{
			if (err) return next(err);
			  
			  res.statusCode = 200;
			  res.setHeader('Content-Type', 'application/json');
			  res.json(items); 
          })	
	}
   	else if(!req.user.isFoodVendor){
        Order.find({userId:req.user._id}&& !isOrderComplete)
            .populate('foodId')
            .populate('userId')
	        .exec((err, items)=>{
			if (err) return next(err);
				
				res.statusCode = 200;
				res.setHeader('Content-Type', 'application/json');
				res.json(items); 
          
		   }); 
			
    }
})


orderRouter.get('/:orderId',cors.corsWithOptions,authenticate,(req,res,next) => {

    Order.findById(req.params.orderId)
            .populate('vendorId')
            .populate('foodId')
            .populate('userId')
	        .exec((err, items)=>{
			if (err) return next(err);
			  
			  res.statusCode = 200;
			  res.setHeader('Content-Type', 'application/json');
			  res.json(items); 
          })
})


orderRouter.get('/search',cors.corsWithOptions,authenticate,(req,res,next) => {
    const { search_field, search_value } = req.query;

    const queryObj = {};

 if (search_field !== null && search_value !== null) {
      queryObj[search_field] = search_value;
      console.log('::queryObj inside IF:::', queryObj);

   }

 console.log('::queryObj out Side:::', queryObj);

 Order.find(queryObj)
       .exec((err, items)=>{
        if (err) return next(err);
     
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(items); 
           
     })
})


orderRouter.post('/',cors.corsWithOptions,authenticate,(req, res, next) => {
    async.waterfall([
        function(callback){
            try{
             req.body.userId = req.user._id;
             Order.create(req.body)
              .then ((items)=>{
                 items.save()
                 .then((items)=>{
                     callback(null, items.id)
                 })
          }, (err) => next(err))
              .catch((err) => next(err));
              
             } catch(e){
                callback(e);
            }
        },
        function(orderId, callback){
          
          req.body.orderId= orderId;
          req.body.invoiceIssueDate= new Date();
          req.body.userId = req.user._id;
          req.body.invoiceDetails = 'invoice issue to customer'
          Invoice.create(req.body)
            .then ((items)=>{
                 items.save()
                 .then((items)=>{
                     callback(null, items)
                 })
          }, (err) => next(err))
              .catch((err) => next(err));
           
        },
        ], function(err, results){
              if(err) return next(err);
              console.log('invoice generated');
              res.satusCode= 200;
              res.setHeader('Content-Type', 'application/json');
              res.json(results)		
          })
});
	 
 orderRouter.put('/:orderId',cors.corsWithOptions, authenticate,(req, res, next) => {
    Order.findOne({userId: req.user._id},(err, order)=>{
        if(err) return next(err);

    if (order){
        Order.findByIdAndUpdate(req.params.orderId,
        {$set:req.body
     }, { new: true })
     .then((response) => {
       res.statusCode = 200;
       res.setHeader('Content-Type', 'application/json');
       res.json(response);
    }, (err) => next(err))
     .catch((err) => next(err));
}
else{
    res.satusCode= 403;
    res.setHeader('Content-Type', 'application/json');
    res.send("you not authorize to perform this operation");
}
})
})

orderRouter.delete('/:orderId',cors.corsWithOptions, authenticate, (req, res, next) => {

        Order.findByIdAndDelete({_id: req.params.orderId})
        .then((item)=>{
            console.log('item deleted', item);
            res.statusCode = 200;
           res.setHeader('Content-Type', 'application/json');
            res.json(item);  
        })
     .catch((err)=>{
         return next(err);
     })                    
});

module.exports= orderRouter;
