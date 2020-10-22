const express = require('express');
var router = express.Router();
const bodyParser = require('body-parser');
var cors= require ('./cors');
router.use(bodyParser.json());

var User = require('../models/users');
var authenticate = require('../authenticate');


router.options('*', cors.corsWithOptions, (reg, res)=>{res.sendStatus(200);})
router.get('/', cors.corsWithOptions, authenticate, (req, res, next)=>{
  console.log("user infor", req.user)
	 User.find({})
    .then((users) => {
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(users);
    }) 
    .catch((err) =>{
		res.statusCode=500;
		res.setHeader('Content-Type', 'application/json');
		res.json({err: err});
	});
	
});

router.options('*', cors.corsWithOptions, (reg, res)=>{res.sendStatus(200);})
router.delete('/:userId', cors.corsWithOptions,authenticate, (req, res, next)=> {
	 User.findByIdAndRemove(req.params.userId)
    .then((users) => {
        res.statusCode = 200;
        res.setHeader('Content-Type', 'application/json');
        res.json(users);
    }) 
    .catch((err) =>{
		res.statusCode=500;
		res.setHeader('Content-Type', 'application/json');
		res.json({err: err});
	});
});

router.post('/signup',cors.corsWithOptions, (req, res, next) => {
          User.find({verifiedEmail: req.body.verifiedEmail}, (err, user)=>{
            if(err) return next(err);

            if(user){
              const token = req.header('Authorization').replace('Bearer', '').trim()
             /* admin.auth().verifyIdToken(token)
                .then(function(decodedToken) {
                  req.body.userId= decodedToken.uid;
                  req.body.verifiedEmail = decodedToken.email
                      return next();
                }).catch(function(error) {
                  return next(error);
                });*/
                console.log('token', token)
                req.body.Date = new Date()
                User.create(req.body)
                .then((user)=>{
                  user.save()
                   .then((user)=>{
                      res.statusCode = 200;
                      res.setHeader('Content-Type', 'application/json');
                      res.json({success: true, status: 'Registration Successful!'});
                  })
                  }).catch((err)=>{
                    return next(err);
                 });
            }

            else{
                res.satusCode= 403;
                res.setHeader('Content-Type', 'application/json');
                res.send("user already exist");
             }
          })
        })

router.post('/login',cors.corsWithOptions, (req, res, next) => {

 
});


router.get('/logout', cors.corsWithOptions, (req, res, next) => {
   req.logout();
   res.redirect('/');
   
});



router.get('/checkJWTToken', cors.corsWithOptions,(reg,res) =>{
	passport.authenticate('jwt', {session: false}, (err, user, info) =>{
	if(err)
		return next(err);
	
	if (!user){
		res.statusCode=401;
		res.setHeader('Content-Type', 'application/json');
	return res.json( {success: false, status:'JWT invalid', err:info});
	}
	else{
		res.statusCode=200;
		res.setHeader('Content-Type', 'application/json');
	    return res.json({status:'JWT valid', success: true, user:user});
		
	}
  })(reg, res);
});

module.exports = router;