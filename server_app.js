const express = require('express')
const fs = require('fs')

const app = express()
const port = process.env.PORT || 3000;

app.get('/getTime', (req, res, next) => {
	fetchTimeForZone((err, timeJSON) => {
		if (err) return res.error(err);
		return res.json(timeJSON);
	});
});

function fetchTimeForZone(cb) {
	let date = new Date();

	// epoch timestamp
	let currentTime = date.getTime();

	return cb(null, {
		data: {
			time: currentTime,
			zone: "place_holder"
		}
	});
}

app.listen(port, () => console.log('Server app listening on port!'))