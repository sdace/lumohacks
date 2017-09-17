var ObjectID = require('mongodb').ObjectID;

module.exports = function (app, db) {

    app.post('/users/register', (req, res) => {
        // Doesn't check for users that do not fill in these parameters!

        const user = {
            firstname: req.body.firstname,
            lastname: req.body.lastname,
            username: req.body.username,
            pass: req.body.pass,
            goal: "No goal yet!",
            streak: 0
        };

        db.collection('users').insert(user, (err, results) => {
            if (err) {
                res.send({
                    code: 500,
                    body: err
                });
            } else {
                res.send({
                    code: 200,
                    body: "Successfully added the user."
                });
            }
        });
    });

    app.put('/users/newgoal/:username', (req, res) => {
        // Doesn't check for users that do not fill in these parameters!

        const username = req.params.username;
        const goal = req.body.goal;

        // Hopefully, there aren't any users with the same username, otherwise
        // they will all be updated. Perhaps check if a username exists later?
        db.collection('users').update({
            username: username
        }, {
            $set: {
                goal: goal
            }
        }, (err, item) => {
            if (err) {
                res.send({
                    code: 500,
                    body: err
                });
            } else {
                res.send({
                    code: 200,
                    body: "Updated " + username + "'s goal! They now have goal: " + goal
                });
            }
        });
    });

    app.put('/users/newstreak/:username', (req, res) => {
        // Doesn't check for users that do not fill in these parameters!

        const username = req.params.username;
        const streak = req.body.streak;

        // Hopefully, there aren't any users with the same username, otherwise
        // they will all be updated. Perhaps check if a username exists later?
        db.collection('users').update({
            username: username
        }, {
            $set: {
                streak: streak
            }
        }, (err, item) => {
            if (err) {
                res.send({
                    code: 500,
                    body: err
                });
            } else {
                res.send({
                    code: 200,
                    body: "Updated " + username + "'s streak! They now have streak: " + streak
                });
            }
        });
    });

    app.get('/users/login/:username/:pass', (req, res) => {
        // Doesn't check for users that do not fill in these parameters!
        // Shouldn't put the username and the password in a GET request (but whatever)

        const username = req.params.username;
        const pass = req.params.pass;

        db.collection('users').findOne({
            username: username,
            pass: pass
        }, (err, item) => {
            if (err) {
                res.send({
                    code: 500,
                    body: err
                })
            } else {
                res.send({
                    code: 200,
                    body: item
                })
            }
        });
    });

    app.get('/users/loadfriends', (req, res) => {
        // Simply returns all the rows from the users database currently.

        db.collection('users').find().toArray((err, item) => {
            if (err) {
                res.send({
                    code: 500,
                    body: err
                })
            } else {
                res.send({
                    code: 200,
                    body: item
                })
            }
        });
    });

};
