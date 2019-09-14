var bodyParser = require('body-parser');
var data = [{ item: 'say hello' }, { item: 'say hello again' }, { item: 'say hello twice' }];

var urlEncodedParser = bodyParser.urlencoded({ extended: false });

module.exports = function (app) {

	app.get('/todo', function (req, res) {
		res.render('todo', { data: data });
	})
	app.post('/toDo', urlEncodedParser, function (req, res) {
		data.push(req.body)
		res.json(data)
	})
	app.delete('/toDo/:item', function (req, res) {
		data = data.filter(function (todo) {
			return todo.item.replace(/ /g, '-') !== req.params.item;
		})
		res.json(data)
	})
}