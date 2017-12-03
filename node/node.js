let rl = require('readline');

let read = rl.createInterface({
  input: process.stdin,
  output: process.stdout
});

read.question("Insert a word to be rotated: ", text => {
  read.close();
  console.log(text);
  for (var i = 0; i < text.length - 1; i++) {
		text = text[text.length - 1] + text.substring(0, text.length - 1);	
  		console.log(text);
  	}
});