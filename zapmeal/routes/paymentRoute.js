const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const cors = require('./cors');
var async = require('async');

const Payment = require('../models/payments');
const Delivery = require('../models/delivery');
const Invoice = require('../models/invoice');
const User = require('../models/users');
var authenticate = require('../authenticate');

const paymentRouter = express.Router();
paymentRouter.use(bodyParser.json());


paymentRouter.options('/',cors.corsWithOptions, (req, res) => { res.sendStatus(200); })

paymentRouter.get('/',cors.corsWithOptions, authenticate,(req,res,next) => {
     if(req.user.isFoodVendor) {
        Payment.find({vendorId: req.user._id})
          .populate('invoiceId')
	        .exec((err, items)=>{
			     if (err) return next(err);
			  
              res.statusCode = 200;
              res.setHeader('Content-Type', 'application/json');
              res.json(items); 
                })	
    	}

   	 else if(!req.user.isFoodVendor) {
        Payment.find({userId: req.user._id})
          .populate('invoiceId')
	        .exec((err, items)=>{
		    	if (err) return next(err);
				
              res.statusCode = 200;
              res.setHeader('Content-Type', 'application/json');
              res.json(items); 
          })	
	}
  else{
        res.satusCode= 403;
		     res.setHeader('Content-Type', 'application/json');
		     res.send("your not  authorise");
  }
})

paymentRouter.get('/:paymentId',cors.corsWithOptions,authenticate,(req,res,next) => {

    Payment.findById(req.params.orderId)
         .populate('invoiceId')
	       .exec((err, items)=>{
			   if (err) return next(err);
			  
            res.statusCode = 200;
            res.setHeader('Content-Type', 'application/json');
            res.json(items); 
              })
      })


paymentRouter.get('/search',cors.corsWithOptions,authenticate,(req,res,next) => {
    const { search_field, search_value } = req.query;

    const queryObj = {};

 if (search_field !== null && search_value !== null) {
      queryObj[search_field] = search_value;
      console.log('::queryObj inside IF:::', queryObj);

   }

 console.log('::queryObj out Side:::', queryObj);

 Payment.find(queryObj)
       .exec((err, items)=>{
        if (err) return next(err);
     
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(items); 
           
     })
});


paymentRouter.post('/',cors.corsWithOptions,authenticate,(req, res, next) => {
    async.waterfall([
        function(callback){
            try{
                req.body.paymentDate= new Date()
                Payment.create(req.body)
                  .then ((items)=>{
                     items.save()
                      .then((items)=>{
                        callback(null, items.invoiceId)
                     })
              }, (err) => next(err))
                 .catch((err) => next(err));
              
               } catch(e){
                callback(e);
             }
        },

        function(invoiceId, callback){
            try{
                Invoice.findByIdAndUpdate(invoiceId,
                  {$set:{invoiceStatus: "Paid"}
                  }, { new: true })
                .then((items) => {
                  callback(null, items._id, )
                  console.log('invoice',items)
                     }, (err) => next(err))
                     .catch((err) => next(err));

                } catch(e){
                  callback(e);
                }
        },
        function(invoice, callback){
          
              req.body.voiceId = invoice._id
              req.body.orderId = invoice.orderId

          req.body.pickUpLocation= invoice.userId
            req.body.deliveryLocation = invoice.vendorId
            
          Delivery.create(req.body)
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

              console.log('Payment is  successful');
              res.satusCode= 200;
              res.setHeader('Content-Type', 'application/json');
              res.json(results)		
          }
          )
})

module.exports= paymentRouter;