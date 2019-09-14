var express = require('express');
var toDoController = require('./controller/todoController')

var app = express();

app.set('view engine', 'ejs');

app.use(express.static('./public'));
app.use(express.static(__dirname + '/assets'));

app.get('/', function (req, res) {
	res.send('HomePage')
})

app.get('/home', function (req, res) {
	res.send('HomePage')
})
app.get('/contact', function (req, res) {
	res.render('profile', { name: 'to contact page. This page is not yet build' })
})
app.get('/contact/:id', function (req, res) {
	console.log(req.params.id)
	res.render('profile', { name: req.params.id })
})

toDoController(app);
app.listen(3019);
