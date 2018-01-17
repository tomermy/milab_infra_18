const bodyParser = require('body-parser');

const mongoClient = require('mongodb').MongoClient;
const MONGO_DB_URL = "mongodb://mongo_writer:Write1992@ds259897.mlab.com:59897/songs_db";
const mongoObjectId = require('mongodb').mongoObjectId;

const express = require('express')
const app = express()
const port = process.env.PORT || 3000;
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
	extended: true
}));

let mongoDB;

// Connect to Database and start listening
mongoClient.connect(MONGO_DB_URL, (err, database) => {
	if (err) {
		return console.log(err);
	}

	console.log("Successfully connected to mongo database");
	mongoDB = database.db("songs_db");
	mongoDB.collection('songs').createIndex({
		album: 1
	});
	mongoDB.collection('songs').createIndex({
		artist: 1
	});
	mongoDB.collection('songs').createIndex({
		name: 1
	});

	app.listen(process.env.PORT || 3000, () => {
		console.log('App is now listening')
	})
})

// CREATE ENDPOINT
app.post('/', (req, res) => {
	if (!req.body.songName || !req.body.albumName || !req.body.artistName || !req.body.genre) {
		res.status(400).send({
			error: "Missing song params"
		});
	} else {
		let status = createNewSong(req.body.songName,
			req.body.albumName, req.body.artistname, req.body.genre, res);
	}
});

// READ ENDPOINT
app.get('/', (req, res) => {
	if (req.query.songName) {
		fetchSongDocument(req.query.songName, res);
	} else if (req.query.albumName) {
		fetchAlbumDocument(req.query.albumName, res);
	} else if (req.query.artistname) {
		fetchArtistDocument(req.query.artistname, res);
	} else {
		res.status(400).send({
			message: "Missing query params"
		});
	}
});

// UPDATE ENDPOINT
app.put('/', (req, res) => {
	updateSongDocument(req.body.id, req.body.songName,
		req.body.albumName, req.body.artistName, req.body.genre, res);
});

// DELETE ENDPOINT
app.delete('/:id', (req, res) => {
	let songID = req.params.id;
	deleteSong(songID, res);
});

function createNewSong(song, album, artist, genre, res) {
	let songJSON = {
		name: song,
		album: album,
		artist: artist,
		genre: genre
	};
	mongoDB.collection('songs').insert([songJSON], (err, result) => {
		if (err) {
			res.send("Error adding new song to database")
		} else {
			res.send("New song was added!");
		}
	});
}

function deleteSong(delId, res) {
	mongoDB.collection('songs').deleteOne({
		_id: mongoObjectId(delId)
	}, (err, result) => {
		res.send("Deleted song with id: " + delId);
	});
}

function updateSongDocument(id, song, album, artist, genre, res) {
	let songJSON = {
		name: song,
		album: album,
		artist: artist,
		genre: genre,
	};
	mongoDB.collection('songs').updateOne({
			_id: mongoObjectId(id)
		}, {
			$set: songJSON
		},
		(err, numUpdated) => {
			if (err) {
				res.send("Error updating request song");
			} else if (numUpdated) {
				res.send("Song was updated successfully");
			} else {
				res.send("Couldn't find song");
			}
		});
}

function printSongDetails(songJSON, res) {
	res.write(`{`);
	res.write(`	ID: ${songJSON._id}\n`);
	res.write(`	Name: ${songJSON.name}\n`);
	res.write(`	Album: ${songJSON.album}\n`);
	res.write(`	Artist: ${songJSON.artist}\n`);
	res.write(`	Genre: ${songJSON.genre}\n`);
	res.write(`}`);
}

function fetchSongDocument(song, res) {
	mongoDB.collection('songs').findOne({
		name: song
	}, function(err, document) {
		if (err || !document) {
			res.send("Can't find the song: " + song);
		} else {
			printSongDetails(document, res);
			res.end();
		}
	});
}

function fetchAlbumDocument(album, res) {
	mongoDB.collection('songs').find({
		album: album
	}).toArray((err, results) => {
		if (err || !results[0]) {
			res.send("Error looking for songs");
		} else {
			res.write(`Songs Results:\n`);
			results.forEach(doc => 
				printSongDetails(doc, res));
			res.end();
		}
	});
}

function fetchArtistDocument(artist, res) {
	mongoDB.collection('songs').find({
		artist: artist
	}).toArray((err, results) => {
		if (err || !results[0]) {
			res.send("Error looking for songs");
		} else {
			res.write(`Songs Results:\n`);
			results.forEach(doc => printSongDetails(doc, res));
			res.end();
		}
	});
}