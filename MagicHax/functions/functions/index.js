const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

//////// Begin path helper functions

const getPlayerPath = (uid) => {return "/players/" + uid;}
const getPlayerCurrentGamePath = (uid) => {return getPlayerPath + "/currentGame";}
const getCreaturePath = (uid) => {return "/creatures/" + uid;}
const getCreature1Path = (uid) => {return getCreaturePath(uid) + "/c1/"}
const getCreature2Path = (uid) => {return getCreaturePath(uid) + "/c2/"}
const getCreature3Path = (uid) => {return getCreaturePath(uid) + "/c3/"}
const getCardPath = (uid) => {return "/cards/" + uid;}
const getGamePath = (gameId) => {return "/games/" + gameId;}
const getGamePlayersPath = (gameId, username) => {return getGamePath(gameId) + "/" + username;}
const getJoinableGamesPath = () => {return "/joinableGames/";}

//////// End path helper functions

////////
// Authentication function. Verifies that the user has a valid token.
const authenticate = (req, res, next) => {
  console.log('Check if request is authorized with Firebase ID token');

  if (!req.headers.authorization || !req.headers.authorization.startsWith('Bearer ')) {
    console.error('No Firebase ID token was passed as a Bearer token in the Authorization header.');
    res.status(403).send('Unauthorized');
    return;
  }

  let idToken;
  if (req.headers.authorization && req.headers.authorization.startsWith('Bearer ')) {
    idToken = req.headers.authorization.split('Bearer ')[1];
  } else {
    res.status(403).send('Unauthorized');
    return;
  }
  admin.auth().verifyIdToken(idToken).then((decodedIdToken) => {
    req.user = decodedIdToken;
    return next();
  }).catch((error) => {
    console.error('Error while verifying Firebase ID token:', error);
    res.status(403).send('Unauthorized');
  });
};
//
//////////

const addPlayerToGame = (uid, gameId) => {
  admin.database().ref(getPlayerCurrentGamePath(uid)).set(gameId);
  admin.database().ref(getGamePath(gameId)).set({status: "joining"});
  admin.database().ref(getPlayerPath(uid)).once('value', (snapshot) => {
    var player = snapshot.val();
    admin.database().ref(getCreaturePath(uid)).once('value', (snapshot) => {
      var creatures = snapshot.val();
      admin.database().ref(getGamePlayersPath(gameId, player.username)).set(creatures);
    });
  });
}


const finalizeGame = (gameId, uid) => {
  admin.database().ref(getJoinableGamesPath() + gameId).remove();
  admin.database().ref(getGamePath(gameId)).set({
    status: "playing",
    currentPlayer: uid
  });
}


exports.testFunction = functions.https.onRequest((request, response) => {
  return authenticate(request, response, () => {
    response.send({data: "User ID is:" + request.user.uid});
  });
});


exports.createUserData = functions.auth.user().onCreate((user, context) => {
  var player = {
    username: "",
    online: true,
    currentGame: "None"
  };
  var creature = {
    type1: "",
    type2: "",
    hp: 0,
    spd: 0,
    atk: 0,
    def: 0,
    mag: 0,
    deck: []
  };
  var defaultCards = {
    cards: []
  }
  admin.database().ref(getPlayerPath(user.uid)).set(player);
  admin.database().ref(getCreature1Path(user.uid)).set(creature);
  admin.database().ref(getCreature2Path(user.uid)).set(creature);
  admin.database().ref(getCreature3Path(user.uid)).set(creature);
  admin.database().ref(getCardPath(user.uid)).set(creature);
});


exports.deleteUserData = functions.auth.user().onDelete((user, context) => {
  admin.database().ref(getPlayerPath(user.uid)).remove();
  admin.database().ref(getCreaturePath(user.uid)).remove();
  admin.database().ref(getCardPath(user.uid)).remove();
});


exports.queuePlayer = functions.https.onRequest((request, response) => {
  authenticate(request, response, () => {
    admin.database().ref(getJoinableGamesPath()).once("value", (snapshot) => {
      if (snapshot.exists()) {
        var gameId = randomGame(snapshot.val());
        addPlayerToGame(request.user.uid, gameId);
        finalizeGame(gameId, request.user.uid);
        return gameId;
      } else {
        var ref = admin.database().ref(getJoinableGamesPath()).push()
        ref.set(ref.key());
        addPlayerToGame(request.user.uid, ref.key());
        return ref.key();
      }
    });
  });
});


exports.verifyAndAddCreature = functions.https.onRequest((request, response) => {

});


function randomGame(games) {
  var keys = Object.keys(games)
  return games[keys[ keys.length * Math.random() << 0]];
}