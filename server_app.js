const express = require('express')
const fs = require('fs')
const app = express()
const port = process.env.PORT || 3000;

app.get('/getTime', (req, res) => {
	let date = new Date();
	let currentTime = date.toTimeString();
	
	res.send('current time is: ' + currentTime);
})

app.get('/getFile', (req, res) => {
	let ERROR_CODE = -1
	let name = req.query.filename || ERROR_CODE;
	if (name == ERROR_CODE) {
		res.status(400).send("query param is missing");
		return;
	}

	let filename = name + ".txt";
	fs.readFile(filename, (err, content) => {
	if (err) {
			res.send("Can't find the file: " + filename + " on our server");
			return;
		}

	res.send(content.toString());	
	})
})	

app.listen(port, () => console.log('Server app listening on port!'))