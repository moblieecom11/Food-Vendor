var User = require('./models/users');
//const admin = require('firebase-admin');
const decodedToken={
    uid: 1122
}

module.exports = (req, res, next) => {
    const token = req.header('Authorization').replace('Bearer', '').trim();
	/* admin.auth().verifyIdToken(token)
    .then((decodedToken)=> {
      if(decodedToken != null)*/
      console.log('authentication token')
         User.findOne({userId: decodedToken.uid}, (err, user,) => {
            
             if (err){

                 return next(err)
             }
            
            else if (user) {
                req.user=user
                console.log('authentication user',req.user._id)
                return next();
            }

            else{
                res.statusCode= 404;
			    res.setHeader('Content-Type', 'text/plain');
			    res.end('Invalid Login credentials');
            }
        });

  //  }).catch((error)=> {
    //    return next(error);
    //});
}

