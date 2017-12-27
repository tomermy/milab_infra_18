const express = require('express')
const fs = require('fs')
const app = express()
const port = process.env.PORT || 3000;

app.get('/files/:filename', (req, res) => {
	let ERROR_CODE = -1

	let name = req.params.filename || ERROR_CODE;
	if (name == ERROR_CODE) {
		res.status(400).send("filename URL param is missing on request");
		return;
	}

	let readStream = fs.createReadStream(name);

	// Wait until the readable stream is valid before piping
	readStream.on('open', _ => {
		readStream.pipe(res);
	});

	// Catch errors when creating the stream
	readStream.on('error', _ => {
		res.status(400).send("error trying to open the file: " + name);
	})
})

app.listen(port, () => console.log('Server app listening on port!'))