var express = require('express');
var router = express.Router();
const bodyParser = require('body-parser');
router.use(bodyParser.json());

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports = router;


const PORT = process.env.PORT || 8080;
app.listen(process.env.PORT || 8080, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});

contactAdress:{
    address:req.address,
    phoneNumber: req.phoneNumber,
    latitude: req.latitude,
    longtitude: req.longtitude
   },


   const userKey = datastore.key('user');
const entity = {
  key: userKey,
  data: user,
};
 datastore.save(entity);

 getVisits()
 .then((entities)=>{
  const visits= entities.map((entity)=>{
    `Date: ${entity.Dare}, firstName: ${entity.firstName}`
   });
  res.statusCode = 200;
  res.setHeader('Content-Type', 'application/json');
  res.json(response);
}, (err) => next(err))
.catch((err) => next(err));

getVisits()
.then((response) => {
  res.statusCode = 200;
  res.setHeader('Content-Type', 'application/json');
  res.json(response.firstName);
  }, (err) => next(err))
  .catch((err) => next(err));


  const express = require('express');
var router = express.Router();
const bodyParser = require('body-parser');
router.use(bodyParser.json());
const crypto = require('crypto');

const app = express();
app.enable('trust proxy');

// By default, the client will authenticate using the service account file
// specified by the GOOGLE_APPLICATION_CREDENTIALS environment variable and use
// the project specified by the GOOGLE_CLOUD_PROJECT environment variable. See
// https://github.com/GoogleCloudPlatform/google-cloud-node/blob/master/docs/authentication.md
// These environment variables are set automatically on Google App Engine
const {Datastore} = require('@google-cloud/datastore');

// Instantiate a datastore client
const datastore = new Datastore();


const getVisits = () => {
  const query = datastore
    .createQuery('user')
    .order('Date', {descending: true})
    .limit(10);

  return datastore.runQuery(query);
};

router.post('/', (req, res, next)=>{
    
  
  const user = {
  firstName: req.body.firstName,
  lastName: req.body.lastName,
  verifiedEmail: req.body.verifiedEmail,
  isFoodVendor:req.body.isFoodVendor,
  Date: new Date()
};
console.log("request body",user)
const userKey = datastore.key('user');
const entity = {
  key: userKey,
  data: user,
};
 datastore.save(entity)
     .then((entities)=>{
      res.statusCode = 200;
      res.setHeader('Content-Type', 'application/json');
      res.json(entities);
   }, (err) => next(err))
    .catch((err) => next(err));
     
});


/* GET users listing. */
router.get('/', async(req, res, next)=> {
  try {
    const [entities] = await getVisits();
    const visits = entities.map(
      (entity) => `Time: ${entity.Date}, AddrHash: ${entity.firstName}`
    );
    res
      .status(200)
      .set('Content-Type', 'text/plain')
      .send(`Last 10 visits:\n${visits.join('\n')}`)
      .end();
  } catch (error) {
    next(error);
  }
});

module.exports = router;
