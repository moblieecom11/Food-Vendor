const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const cors = require('./cors');

const Food = require('../models/foods');
const User = require('../models/users');
var authenticate = require('../authenticate');

const foodRouter = express.Router();
foodRouter.use(bodyParser.json());


foodRouter.options(cors.corsWithOptions, (req, res) => { res.sendStatus(200); })

foodRouter.get('/',cors.corsWithOptions,authenticate,(req,res,next) => {
     if(req.user.isFoodVendor) {
       Food.find({vendorId: req.user._id})
	        .populate('vendorId')
	        .exec((err, items)=>{
			if (err) return next(err);
			  
			  res.statusCode = 200;
			  res.setHeader('Content-Type', 'application/json');
			  res.json(items); 
          })	
        }
	 else {
        Food.find({})
	        .populate('vendorId')
	        .exec((err, items)=>{
				if (err) return next(err);
				
				res.statusCode = 200;
				res.setHeader('Content-Type', 'application/json');
				res.json(items); 
          })
		}
})


foodRouter.get('/:foodId',cors.corsWithOptions,authenticate,(req,res,next) => {

       Food.findById(req.params.foodId)
	        .populate('vendorId')
	        .exec((err, items)=>{
			if (err) return next(err);
			  
			  res.statusCode = 200;
			  res.setHeader('Content-Type', 'application/json');
			  res.json(items); 
          })
})


foodRouter.get('/search',cors.corsWithOptions,authenticate,(req,res,next) => {
    const { search_field, search_value } = req.query;

    const queryObj = {};

 if (search_field !== null && search_value !== null) {
      queryObj[search_field] = search_value;
      console.log('::queryObj inside IF:::', queryObj);

   }

 console.log('::queryObj out Side:::', queryObj);

  Food.find(queryObj)
       .exec((err, items)=>{
        if (err) return next(err);
     
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(items); 
           
     })
})


foodRouter.post('/',cors.corsWithOptions,authenticate,(req, res, next) => {
    Food.findOne({vendorId: req.user._id}&&{foodName:req.body.foodName}&&{foodType:req.body.foodType},(err, food)=>{
		    if(err) return next(err);
		
		  if(!food){
			    req.body.vendorId = req.user._id;
			    Food.create(req.body)
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
	})
	 
foodRouter.put('/:foodId',cors.corsWithOptions, authenticate,(req, res, next) => {
    Food.findOne({vendorId: req.user._id},(err, food)=>{
        if(err) return next(err);

    if (food){
        Food.findByIdAndUpdate(req.params.foodId,
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
    res.send("your not authorize to perform this operation");
}
})
})

foodRouter.delete('/:foodId',cors.corsWithOptions, authenticate, (req, res, next) => {
    Food.findOne({userId: req.user._id},(err, order)=>{
        if(err) return next(err);

     if (!order){
    	Food.findOne({_id: req.user._id}, (err, item)=>{
         if (err) return next(err);
		  console.log(item);
		  var index = item.indexOf(req.params.itemId);
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
		}) 
	 }
      
     else{
        res.satusCode= 403;
        res.setHeader('Content-Type', 'application/json');
        res.send("your not authorize to perform this operation");
     }
    })                         
});

module.exports= foodRouter;


