var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
const mongoose = require('mongoose');
var config = require('./config');

//Importing Available Route
var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var foodRouter = require('./routes/foodRoute');
var orderRoute = require('./routes/orderRoute');
var invoiceRoute = require('./routes/invoiceRoute');
var paymentRoute = require('./routes/paymentRoute');

var app = express();

//mongo-server.
const url = config.mongoUrl;
const connect = mongoose.connect(url,{useNewUrlParser: true});
connect.then((db) => {
    console.log("Connected correctly to server"+ url);
}, (err) => { console.log(err); });


//app.use constant
app.use(express.static(path.join(__dirname, 'public')));

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/food', foodRouter);
app.use('/order', orderRoute);
app.use('/invoice', invoiceRoute);
app.use('/payment', paymentRoute);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
