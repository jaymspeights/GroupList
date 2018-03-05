var crypto = require('crypto');
var fs = require('fs');

var privateKey = fs.readFileSync('ssl-cert/privkey.pem', 'utf8');
var certificate = fs.readFileSync('ssl-cert/fullchain.pem', 'utf8');

var credentials = { key: privateKey, cert: certificate };
var https = require('https');


var httpsServer = https.createServer(credentials);
httpServer.listen(50075);

var WebSocketServer = require('ws').Server;
var wss = new WebSocketServer({
    server: httpsServer
});

var MongoClient = require('mongodb').MongoClient;

var db_uri = fs.readFileSync('db_uri', 'utf8').replace('\n', '');
MongoClient.connect(db_uri, function (err, client) {
  if (err) throw err;
  var db = client.db('GroupList');
  var users = db.collection('users');;
  wss.on('connection', function (ws) {
    console.log('connection');
    ws.on('message', function (data) {
      try {
        var message = JSON.parse(data);
      } catch (e) {
        console.log(data);
        console.log(e.stack);
        return;
      }
      switch (message.header) {
        case 'create':
          createUser(message.username, message.password)
            .then(user => console.log('Success'))
            .catch(error => console.log(error));
          break;
        case 'login':
          validateUser(message.username, message.password)
            .then(function (user) {
              var data = 'listAll\n';
              for (var i = 0; i < user.lists.length; i++) {
                data += user.lists[i].id+'_'+user.lists[i].name;
                if (i!==user.lists.length-1)
                  data+='\n'
              }
              ws.send(data);
            })
            .catch(res => ws.send("error\n"+res.error));
          break;
        default:
          console.log(message);
      }
    });
  });

  function validateUser(username, password) {
    return new Promise(function (resolve, reject) {
      users.findOne({username:username}, function (err, user) {
        if (err) throw err;
        if (user !== null && user.password === sha512(password, user.salt))
          resolve(user);
        else
          reject({error:'Invalid Password!'});
      });
    });
  }

  function createUser(username, password) {
    return new Promise(function (resolve, reject) {
      users.findOne({username:username}, (err, user) => {
        if (err) throw err;
        if (user === null) {
          var salt = genSalt();
          var hash = sha512(password, salt);
          var user = {username:username, password:hash, salt:salt};
          users.insertOne(user, function (err) {
            if (err) throw err;
            resolve(user);
          });
        } else {
          reject({error:'Username Taken!'});
        }
      });
    });
  }
});

function genSalt () {
  return crypto.randomBytes(8).toString('hex');
}

function sha512 (password, salt) {
  var hash = crypto.createHmac('sha512', salt);
  hash.update(password);
  return hash.digest('hex');
}
