const Logger = require("./Logger");

class ConsoleLogger extends Logger {

    static request(req) {
        console.log("Unzer request: ", req);
    }

    static requestError(err) {
        console.error("Unzer request error: ", err);
    }
    static response(resp) {
        console.log("Unzer response: ", resp);
    }
    static responseError(err) {
        console.error("Unzer response error: ", err);
    }
}

module.exports = ConsoleLogger;