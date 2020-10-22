const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const cors = require('./cors');
var async = require('async');

const Delivery = require('../models/delivery');
const User = require('../models/users');
var authenticate = require('../authenticate');

const deliveryRouter = express.Router();
deliveryRouter.use(bodyParser.json());


deliveryRouter.options('/',cors.corsWithOptions, (req, res) => { res.sendStatus(200); })

deliveryRouter.get('/',cors.corsWithOptions,authenticate.verifyOrdinaryUser,(req,res,next) => {
     if(req.user.isFoodVendor) {
        Delivery.find({vendorId: req.user._id})
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
   	else if(!req.user.isFoodVendor) {
        Delivery.find({userId: req.user._id})
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


    deliveryRouter.get('/:orderId',cors.corsWithOptions,authenticate.verifyOrdinaryUser,(req,res,next) => {

        Delivery.findById(req.params.orderId)
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


deliveryRouter.get('/search',cors.corsWithOptions,authenticate.verifyOrdinaryUser,(req,res,next) => {
    const { search_field, search_value } = req.query;

    const queryObj = {};

 if (search_field !== null && search_value !== null) {
      queryObj[search_field] = search_value;
      console.log('::queryObj inside IF:::', queryObj);

   }

 console.log('::queryObj out Side:::', queryObj);

 Delivery.find(queryObj)
       .exec((err, items)=>{
        if (err) return next(err);
     
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(items); 
           
     })
})


deliveryRouter.post('/',cors.corsWithOptions,authenticate.verifyOrdinaryUser,(req, res, next) => {
    Delivery.findOne({vendorId: req.body.vendorId}&&{foodId:req.body.foodId},(err, food)=>{
		    if(err) return next(err);
		
		  if(!food){
			    req.body.userId = req.user._id;
			    Order.create(req.body)
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
		     res.send("duplicate Order");
			}
	      
	   });
	})
	 
    deliveryRouter.put('/:orderId',cors.corsWithOptions, authenticate.verifyOrdinaryUser, authenticate.verifyAdmin,(req, res, next) => {
    Delivery.findOne({userId: req.user._id},(err, order)=>{
        if(err) return next(err);

    if (order){
        Delivery.findByIdAndUpdate(req.params.orderId,
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

deliveryRouter.delete('/:orderId',cors.corsWithOptions, authenticate.verifyOrdinaryUser, authenticate.verifyAdmin, (req, res, next) => {
    Delivery.findOne({userId: req.user._id},(err, order)=>{
        if(err) return next(err);

      if (order){
        Order.findOne({_id: req.params.orderId}, (err, item))
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

      }
      else{
        res.satusCode= 403;
        res.setHeader('Content-Type', 'application/json');
        res.send("you not authorize to perform this operation");
      }
	})                       
});

module.exports= deliveryRouter;
