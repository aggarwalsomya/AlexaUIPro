/*
 * GET users listing.
 */

exports.list = function(req, res) {
	res.send("respond with a resource");
};

exports.signup = function(req, res) {
	res.render("signup");
};

exports.signin = function(req, res) {
	res.render("signin");
};